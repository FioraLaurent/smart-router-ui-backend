package com.smartrouter.service;

import com.smartrouter.dto.RealtimeMetrics;
import com.smartrouter.entity.MetricsDetail;
import com.smartrouter.entity.ConversationContent;
import com.smartrouter.repository.MetricsDetailRepository;
import com.smartrouter.repository.ConversationContentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.function.Consumer;
import java.util.concurrent.*;

@Service
public class MetricsCollector {

    private final MetricsDetailRepository metricsRepo;
    private final ConversationContentRepository convRepo;
    private final Queue<MetricsDetail> pendingMetrics = new ConcurrentLinkedQueue<>();
    private final Queue<ConversationContent> pendingContent = new ConcurrentLinkedQueue<>();
    private final List<Consumer<RealtimeMetrics>> subscribers = new CopyOnWriteArrayList<>();
    private final Random random = new Random();

    public MetricsCollector(MetricsDetailRepository metricsRepo,
                            ConversationContentRepository convRepo) {
        this.metricsRepo = metricsRepo;
        this.convRepo = convRepo;
    }

    public void recordRequest(MetricsDetail metrics, ConversationContent content) {
        pendingMetrics.offer(metrics);
        if (content != null) pendingContent.offer(content);
    }

    public void subscribe(Consumer<RealtimeMetrics> consumer) {
        subscribers.add(consumer);
    }

    // 每5秒批量写入 + 推送实时数据
    @Scheduled(fixedRate = 5000)
    @Transactional
    public void flushAndPush() {
        List<MetricsDetail> batchMetrics = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            MetricsDetail m = pendingMetrics.poll();
            if (m == null) break;
            batchMetrics.add(m);
        }

        List<ConversationContent> batchContent = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            ConversationContent c = pendingContent.poll();
            if (c == null) break;
            batchContent.add(c);
        }

        if (!batchMetrics.isEmpty()) {
            metricsRepo.saveAll(batchMetrics);
        }
        if (!batchContent.isEmpty()) {
            convRepo.saveAll(batchContent);
        }

        // 推送实时指标
        RealtimeMetrics realtime = buildRealtimeMetrics();
        subscribers.forEach(s -> {
            try { s.accept(realtime); } catch (Exception ignored) {}
        });
    }

    private RealtimeMetrics buildRealtimeMetrics() {
        long now = System.currentTimeMillis();
        long minuteAgo = now - 60_000;

        List<MetricsDetail> recent = metricsRepo.findAll().stream()
            .filter(m -> m.getTs() != null && m.getTs() >= minuteAgo)
            .toList();

        long count = recent.size();
        double avgLat = recent.stream()
            .filter(m -> m.getLatencyMs() != null)
            .mapToLong(MetricsDetail::getLatencyMs)
            .average().orElse(0);

        List<Long> sortedLat = recent.stream()
            .filter(m -> m.getLatencyMs() != null)
            .mapToLong(MetricsDetail::getLatencyMs)
            .sorted().boxed().toList();

        double p95Lat = sortedLat.isEmpty() ? 0 :
            sortedLat.get((int) (sortedLat.size() * 0.95));

        long fails = recent.stream()
            .filter(m -> "fail".equals(m.getStatus()))
            .count();

        double failRate = count == 0 ? 0 : fails * 100.0 / count;

        long tokens = recent.stream()
            .filter(m -> m.getTotalTokens() != null)
            .mapToLong(MetricsDetail::getTotalTokens)
            .sum();

        long sessions = recent.stream()
            .filter(m -> m.getSessionId() != null)
            .map(MetricsDetail::getSessionId)
            .distinct()
            .count();

        RealtimeMetrics rm = new RealtimeMetrics();
        rm.setTimestamp(now);
        rm.setQps(count * 12L);  // 5s 窗口 → 乘12 估算 QPS
        rm.setP50Latency(avgLat);
        rm.setP95Latency(p95Lat);
        rm.setFailRate(failRate);
        rm.setTotalTokensLastMinute(tokens);
        rm.setActiveSessions(sessions);
        return rm;
    }

    // 每分钟生成模拟数据（演示用）
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void generateMockData() {
        long now = System.currentTimeMillis();
        String[] models = {"gpt-4o-mini", "gpt-4o", "claude-3-haiku", "claude-3-sonnet", "qwen-turbo", "qwen-plus"};
        String[] tiers = {"1", "1", "1", "2", "2", "2", "3"};
        String[] providers = {"openai", "anthropic", "aliyun"};
        String[] users = {"user_001", "user_002", "user_003", "user_004", "user_005"};
        String[] inputs = {
            "你好，今天天气怎么样？",
            "帮我写一封英文邮件给客户",
            "分析这段代码的性能问题",
            "总结一下这篇文档的核心观点",
            "解释一下什么是微服务架构",
            "翻译成日语：我爱你中国",
            "帮我优化这段SQL查询",
            "设计一个高可用的系统架构",
            "今天几号了？",
            "这段Python代码哪里有bug？"
        };
        String[] outputs = {
            "今天天气晴朗，气温25度，适合出行。",
            "Subject: Follow-up on Our Discussion\n\nDear Valued Customer...",
            "这段代码存在以下性能问题：\n1. N+1 查询问题\n2. 缺少索引\n3. 循环中的同步操作",
            "## 核心观点总结\n\n1. 用户体验是产品成功的关键因素\n2. 数据驱动决策可以显著提升转化率",
            "微服务架构是一种将单一应用程序划分为一组小服务的架构风格...",
            "日语翻译：我爱你中国 → 私は中国を愛しています",
            "优化后的SQL查询性能提升了3倍，关键改动包括添加索引和使用批量操作",
            "## 高可用系统架构设计\n\n### 核心技术选型\n- 负载均衡：Nginx / 云 LB\n- 服务注册：Consul / Eureka\n- 熔断降级：Sentinel / Hystrix",
            "今天是2026年9月10日，星期四",
            "这个bug是因为在循环中没有正确关闭资源，建议使用try-with-resources语法"
        };

        int batchSize = 5 + random.nextInt(10);
        List<MetricsDetail> batch = new ArrayList<>();
        List<ConversationContent> convBatch = new ArrayList<>();

        for (int i = 0; i < batchSize; i++) {
            String reqId = UUID.randomUUID().toString().substring(0, 16);
            String session = users[random.nextInt(users.length)];
            int tierIdx = random.nextInt(tiers.length);
            int tier = Integer.parseInt(tiers[tierIdx]);
            String model = models[random.nextInt(models.length)];
            String provider = providers[random.nextInt(providers.length)];
            int promptTok = 100 + random.nextInt(500);
            int completionTok = 200 + random.nextInt(2000);
            int latency = 200 + random.nextInt(tier == 3 ? 5000 : tier == 2 ? 3000 : 1000);
            boolean isFail = random.nextDouble() < 0.05;

            MetricsDetail m = new MetricsDetail();
            m.setRequestId(reqId);
            m.setSessionId(session);
            m.setAgentId("main");
            m.setTier(tier);
            m.setTargetModel(model);
            m.setProvider(provider);
            m.setPromptTokens(promptTok);
            m.setCompletionTokens(isFail ? 0 : completionTok);
            m.setTotalTokens(promptTok + (isFail ? 0 : completionTok));
            m.setCachedTokens(random.nextInt(100));
            m.setLatencyMs(latency);
            m.setQueueWaitMs(random.nextInt(200));
            m.setStatus(isFail ? "fail" : "success");
            m.setFailReason(isFail ? "timeout" : null);
            m.setRetryCount(isFail ? random.nextInt(3) : 0);
            m.setTs(now - random.nextInt(60000));
            batch.add(m);

            ConversationContent c = new ConversationContent();
            c.setRequestId(reqId);
            c.setSessionId(session);
            c.setAgentId("main");
            c.setTier(tier);
            c.setUserMessage(inputs[random.nextInt(inputs.length)]);
            c.setUserTokens(promptTok);
            c.setAssistantMessage(isFail ? "请求超时，请重试" : outputs[random.nextInt(outputs.length)]);
            c.setAssistantTokens(isFail ? 0 : completionTok);
            c.setTurnIndex(0);
            c.setContextSnapshot("[]");
            c.setContextTokens(0);
            c.setTs(m.getTs());
            convBatch.add(c);
        }

        metricsRepo.saveAll(batch);
        convRepo.saveAll(convBatch);
    }
}

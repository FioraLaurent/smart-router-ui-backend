package com.smartrouter.config;

import com.smartrouter.entity.*;
import com.smartrouter.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

    private final KeywordRuleRepository ruleRepo;
    private final ModelConfigRepository modelRepo;
    private final AlertRuleRepository alertRepo;

    public DataInitializer(KeywordRuleRepository ruleRepo,
                           ModelConfigRepository modelRepo,
                           AlertRuleRepository alertRepo) {
        this.ruleRepo = ruleRepo;
        this.modelRepo = modelRepo;
        this.alertRepo = alertRepo;
    }

    @Override
    public void run(String... args) {
        if (ruleRepo.count() == 0) {
            List<KeywordRule> rules = List.of(
                makeRule("你好|hi|hello|嗨", 1, 5, "问候语"),
                makeRule("今天天气|现在几点|时间", 1, 5, "时间天气查询"),
                makeRule("帮我写.*邮件|写.*通知|生成.*报告", 2, 7, "文档生成"),
                makeRule("分析|优化|重构|架构|设计模式", 3, 9, "复杂分析任务"),
                makeRule("翻译|convert|translate", 1, 4, "翻译任务"),
                makeRule("代码|code|编程|function|class", 2, 6, "代码相关"),
                makeRule("总结|摘要|归纳", 2, 6, "摘要任务"),
                makeRule("解释.*原理|为什么|how.*work", 2, 7, "原理讲解")
            );
            ruleRepo.saveAll(rules);
        }

        if (modelRepo.count() == 0) {
            List<ModelConfig> models = List.of(
                makeModel("gpt-4o-mini", "GPT-4o Mini", 1, "openai", new BigDecimal("0.15"), new BigDecimal("0.60"), 128000, 16384, 75, 99),
                makeModel("gpt-4o", "GPT-4o", 2, "openai", new BigDecimal("2.50"), new BigDecimal("10.00"), 128000, 16384, 92, 98),
                makeModel("gpt-4-turbo", "GPT-4 Turbo", 3, "openai", new BigDecimal("10.00"), new BigDecimal("30.00"), 128000, 4096, 96, 97),
                makeModel("claude-3-haiku", "Claude 3 Haiku", 1, "anthropic", new BigDecimal("0.25"), new BigDecimal("1.25"), 200000, 4096, 78, 99),
                makeModel("claude-3-sonnet", "Claude 3 Sonnet", 2, "anthropic", new BigDecimal("3.00"), new BigDecimal("15.00"), 200000, 8192, 90, 98),
                makeModel("claude-3-opus", "Claude 3 Opus", 3, "anthropic", new BigDecimal("15.00"), new BigDecimal("75.00"), 200000, 4096, 97, 96),
                makeModel("qwen-turbo", "通义千问 Turbo", 1, "aliyun", new BigDecimal("0.20"), new BigDecimal("0.60"), 128000, 8192, 72, 99),
                makeModel("qwen-plus", "通义千问 Plus", 2, "aliyun", new BigDecimal("1.00"), new BigDecimal("4.00"), 128000, 8192, 85, 98)
            );
            modelRepo.saveAll(models);
        }

        if (alertRepo.count() == 0) {
            List<AlertRule> alerts = List.of(
                makeAlert("失败率告警", "fail_rate", new BigDecimal("5.00"), 5, "page", ""),
                makeAlert("P95延迟告警", "latency_p95", new BigDecimal("5000.00"), 5, "page", ""),
                makeAlert("预算超限告警", "budget", new BigDecimal("80.00"), 1, "email", "admin@example.com"),
                makeAlert("熔断告警", "circuit", new BigDecimal("1.00"), 1, "page", "")
            );
            alertRepo.saveAll(alerts);
        }
    }

    private KeywordRule makeRule(String keyword, int tier, int weight, String desc) {
        KeywordRule r = new KeywordRule();
        r.setKeyword(keyword);
        r.setTier(tier);
        r.setWeight(weight);
        r.setEnabled(true);
        r.setDescription(desc);
        r.setGrayRatio(0);
        r.setVersion(1);
        r.setCreatedAt(System.currentTimeMillis());
        r.setUpdatedAt(System.currentTimeMillis());
        return r;
    }

    private ModelConfig makeModel(String modelId, String name, int tier, String provider,
                                  BigDecimal pi, BigDecimal po, int cw, int mo, int qs, int rs) {
        ModelConfig m = new ModelConfig();
        m.setModelId(modelId);
        m.setDisplayName(name);
        m.setTier(tier);
        m.setProvider(provider);
        m.setPriceInput(pi);
        m.setPriceOutput(po);
        m.setContextWindow(cw);
        m.setMaxOutput(mo);
        m.setQualityScore(qs);
        m.setReliabilityScore(rs);
        m.setCircuitBreakerCount(0);
        m.setEnabled(true);
        m.setCreatedAt(System.currentTimeMillis());
        return m;
    }

    private AlertRule makeAlert(String name, String metric, BigDecimal threshold,
                                 int duration, String notifyType, String target) {
        AlertRule a = new AlertRule();
        a.setName(name);
        a.setMetric(metric);
        a.setThreshold(threshold);
        a.setDurationMinutes(duration);
        a.setNotifyType(notifyType);
        a.setNotifyTarget(target);
        a.setEnabled(true);
        return a;
    }
}

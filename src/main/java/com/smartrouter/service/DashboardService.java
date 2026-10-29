package com.smartrouter.service;

import com.smartrouter.dto.*;
import com.smartrouter.entity.*;
import com.smartrouter.repository.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final MetricsDetailRepository metricsRepo;
    private final ModelConfigRepository modelRepo;

    public DashboardService(MetricsDetailRepository metricsRepo, ModelConfigRepository modelRepo) {
        this.metricsRepo = metricsRepo;
        this.modelRepo = modelRepo;
    }

    public DashboardSummary getSummary() {
        long now = System.currentTimeMillis();
        long dayStart = startOfDay(now);
        long weekStart = dayStart - 7L * 24 * 3600 * 1000;
        long monthStart = dayStart - 30L * 24 * 3600 * 1000;

        DashboardSummary summary = new DashboardSummary();

        // Token 统计
        summary.setTotalTokensToday(safeLong(metricsRepo.sumTotalTokens(dayStart, now)));
        summary.setTotalTokensWeek(safeLong(metricsRepo.sumTotalTokens(weekStart, now)));
        summary.setTotalTokensMonth(safeLong(metricsRepo.sumTotalTokens(monthStart, now)));

        // 请求数
        summary.setRequestCountToday(safeLong(metricsRepo.countByTsBetween(dayStart, now)));

        // 延迟
        summary.setAvgLatency(metricsRepo.avgLatency(dayStart, now));
        summary.setFailRate(calcFailRate(dayStart, now));

        // 活跃会话
        summary.setActiveSessions(safeLong(metricsRepo.countActiveSessions(dayStart, now)));

        // Tier 分布
        Map<String, Long> tierDist = new LinkedHashMap<>();
        for (Object[] row : metricsRepo.tierDistribution(dayStart, now)) {
            tierDist.put("Tier-" + row[0], safeLong(row[1]));
        }
        summary.setTierDistribution(tierDist);

        // 模型排行
        List<DashboardSummary.ModelRankItem> ranking = new ArrayList<>();
        for (Object[] row : metricsRepo.topModels(dayStart, now)) {
            String model = (String) row[0];
            Long tokens = safeLong(row[1]);
            ModelConfig config = modelRepo.findByModelId(model).orElse(null);
            DashboardSummary.ModelRankItem item = new DashboardSummary.ModelRankItem();
            item.setModel(model);
            item.setDisplayName(config != null ? config.getDisplayName() : model);
            item.setTotalTokens(tokens);
            item.setCost(calcCost(model, tokens));
            ranking.add(item);
        }
        summary.setModelRanking(ranking);

        // 预算状态（示例值，后续可从配置读取）
        DashboardSummary.BudgetStatus budget = new DashboardSummary.BudgetStatus();
        budget.setDailyLimit(10_000_000L);
        budget.setDailyUsed(summary.getTotalTokensToday());
        budget.setDailyPercent(
            summary.getTotalTokensToday() * 100.0 / budget.getDailyLimit()
        );
        budget.setWeeklyLimit(50_000_000L);
        budget.setWeeklyUsed(summary.getTotalTokensWeek());
        budget.setWeeklyPercent(
            summary.getTotalTokensWeek() * 100.0 / budget.getWeeklyLimit()
        );
        summary.setBudgetStatus(budget);

        return summary;
    }

    public TrendData getTrend(int days) {
        long now = System.currentTimeMillis();
        long from = now - days * 24L * 3600 * 1000;

        List<Object[]> rows = metricsRepo.dailyTrend(from);

        List<String> dates = new ArrayList<>();
        List<Long> tokens = new ArrayList<>();
        List<Long> counts = new ArrayList<>();
        List<Long> t1 = new ArrayList<>();
        List<Long> t2 = new ArrayList<>();
        List<Long> t3 = new ArrayList<>();

        for (Object[] row : rows) {
            dates.add((String) row[0]);
            tokens.add(safeLong(row[1]));
            counts.add(safeLong(row[2]));
        }

        TrendData td = new TrendData();
        td.setDates(dates);
        td.setTokenCounts(tokens);
        td.setRequestCounts(counts);
        td.setTier1Tokens(t1);
        td.setTier2Tokens(t2);
        td.setTier3Tokens(t3);
        td.setLatencies(new ArrayList<>());
        td.setFailRates(new ArrayList<>());

        return td;
    }

    private Double calcFailRate(long from, long to) {
        Long total = metricsRepo.countByTsBetween(from, to);
        Long fails = metricsRepo.countFails(from, to);
        if (total == null || total == 0) return 0.0;
        return fails * 100.0 / total;
    }

    private BigDecimal calcCost(String modelId, Long tokens) {
        return modelRepo.findByModelId(modelId)
            .map(m -> {
                double inCost = tokens * m.getPriceInput().doubleValue() / 1_000_000;
                return BigDecimal.valueOf(inCost).setScale(4, RoundingMode.HALF_UP);
            })
            .orElse(BigDecimal.ZERO);
    }

    private Long safeLong(Object val) {
        if (val == null) return 0L;
        if (val instanceof Long) return (Long) val;
        if (val instanceof Number) return ((Number) val).longValue();
        return 0L;
    }

    private long startOfDay(long ts) {
        return LocalDate.ofInstant(
            Instant.ofEpochMilli(ts), ZoneId.systemDefault()
        ).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}

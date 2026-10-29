package com.smartrouter.dto;

import java.math.BigDecimal;
import java.util.*;

public class DashboardSummary {
    private Long totalTokensToday;
    private Long totalTokensWeek;
    private Long totalTokensMonth;
    private BigDecimal costToday;
    private BigDecimal costWeek;
    private BigDecimal costMonth;
    private Long requestCountToday;
    private Double avgLatencyMs;
    private Double p95LatencyMs;
    private Double failRate;
    private Long activeSessions;
    private Map<String, Long> tierDistribution;
    private List<ModelRankItem> modelRanking;
    private BudgetStatus budgetStatus;

    public DashboardSummary() {}

    public Long getTotalTokensToday() { return totalTokensToday; }
    public void setTotalTokensToday(Long v) { this.totalTokensToday = v; }
    public Long getTotalTokensWeek() { return totalTokensWeek; }
    public void setTotalTokensWeek(Long v) { this.totalTokensWeek = v; }
    public Long getTotalTokensMonth() { return totalTokensMonth; }
    public void setTotalTokensMonth(Long v) { this.totalTokensMonth = v; }
    public BigDecimal getCostToday() { return costToday; }
    public void setCostToday(BigDecimal v) { this.costToday = v; }
    public BigDecimal getCostWeek() { return costWeek; }
    public void setCostWeek(BigDecimal v) { this.costWeek = v; }
    public BigDecimal getCostMonth() { return costMonth; }
    public void setCostMonth(BigDecimal v) { this.costMonth = v; }
    public Long getRequestCountToday() { return requestCountToday; }
    public void setRequestCountToday(Long v) { this.requestCountToday = v; }
    public Double getAvgLatencyMs() { return avgLatencyMs; }
    public void setAvgLatencyMs(Double v) { this.avgLatencyMs = v; }
    public void setAvgLatency(Double v) { this.avgLatencyMs = v; }
    public void setAvgLatencyMsLatency(Double v) { this.avgLatencyMs = v; }
    public Double getP95LatencyMs() { return p95LatencyMs; }
    public void setP95LatencyMs(Double v) { this.p95LatencyMs = v; }
    public Double getFailRate() { return failRate; }
    public void setFailRate(Double v) { this.failRate = v; }
    public Long getActiveSessions() { return activeSessions; }
    public void setActiveSessions(Long v) { this.activeSessions = v; }
    public Map<String, Long> getTierDistribution() { return tierDistribution; }
    public void setTierDistribution(Map<String, Long> v) { this.tierDistribution = v; }
    public List<ModelRankItem> getModelRanking() { return modelRanking; }
    public void setModelRanking(List<ModelRankItem> v) { this.modelRanking = v; }
    public BudgetStatus getBudgetStatus() { return budgetStatus; }
    public void setBudgetStatus(BudgetStatus v) { this.budgetStatus = v; }

    public static class ModelRankItem {
        private String model;
        private String displayName;
        private Long totalTokens;
        private Long requestCount;
        private BigDecimal cost;
        public ModelRankItem() {}
        public String getModel() { return model; }
        public void setModel(String v) { this.model = v; }
        public String getDisplayName() { return displayName; }
        public void setDisplayName(String v) { this.displayName = v; }
        public Long getTotalTokens() { return totalTokens; }
        public void setTotalTokens(Long v) { this.totalTokens = v; }
        public Long getRequestCount() { return requestCount; }
        public void setRequestCount(Long v) { this.requestCount = v; }
        public BigDecimal getCost() { return cost; }
        public void setCost(BigDecimal v) { this.cost = v; }
    }

    public static class BudgetStatus {
        private Long dailyLimit;
        private Long dailyUsed;
        private Double dailyPercent;
        private Long weeklyLimit;
        private Long weeklyUsed;
        private Double weeklyPercent;
        public BudgetStatus() {}
        public Long getDailyLimit() { return dailyLimit; }
        public void setDailyLimit(Long v) { this.dailyLimit = v; }
        public Long getDailyUsed() { return dailyUsed; }
        public void setDailyUsed(Long v) { this.dailyUsed = v; }
        public Double getDailyPercent() { return dailyPercent; }
        public void setDailyPercent(Double v) { this.dailyPercent = v; }
        public Long getWeeklyLimit() { return weeklyLimit; }
        public void setWeeklyLimit(Long v) { this.weeklyLimit = v; }
        public Long getWeeklyUsed() { return weeklyUsed; }
        public void setWeeklyUsed(Long v) { this.weeklyUsed = v; }
        public Double getWeeklyPercent() { return weeklyPercent; }
        public void setWeeklyPercent(Double v) { this.weeklyPercent = v; }
    }
}

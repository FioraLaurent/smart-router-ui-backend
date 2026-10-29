package com.smartrouter.dto;

public class RealtimeMetrics {
    private Long timestamp;
    private Long qps;
    private Double p50Latency;
    private Double p95Latency;
    private Double failRate;
    private Long totalTokensLastMinute;
    private Long activeSessions;

    public RealtimeMetrics() {}

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long v) { this.timestamp = v; }
    public Long getQps() { return qps; }
    public void setQps(Long v) { this.qps = v; }
    public Double getP50Latency() { return p50Latency; }
    public void setP50Latency(Double v) { this.p50Latency = v; }
    public Double getP95Latency() { return p95Latency; }
    public void setP95Latency(Double v) { this.p95Latency = v; }
    public Double getFailRate() { return failRate; }
    public void setFailRate(Double v) { this.failRate = v; }
    public Long getTotalTokensLastMinute() { return totalTokensLastMinute; }
    public void setTotalTokensLastMinute(Long v) { this.totalTokensLastMinute = v; }
    public Long getActiveSessions() { return activeSessions; }
    public void setActiveSessions(Long v) { this.activeSessions = v; }
}

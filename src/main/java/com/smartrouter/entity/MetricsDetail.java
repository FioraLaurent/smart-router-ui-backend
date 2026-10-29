package com.smartrouter.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "metrics_detail")
public class MetricsDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "request_id", nullable = false)
    private String requestId;
    @Column(name = "session_id")
    private String sessionId;
    @Column(name = "agent_id")
    private String agentId;
    @Column(name = "tier", nullable = false)
    private Integer tier;
    @Column(name = "target_model")
    private String targetModel;
    @Column(name = "provider")
    private String provider;
    @Column(name = "prompt_tokens")
    private Integer promptTokens = 0;
    @Column(name = "completion_tokens")
    private Integer completionTokens = 0;
    @Column(name = "total_tokens")
    private Integer totalTokens = 0;
    @Column(name = "cached_tokens")
    private Integer cachedTokens = 0;
    @Column(name = "latency_ms")
    private Integer latencyMs = 0;
    @Column(name = "queue_wait_ms")
    private Integer queueWaitMs = 0;
    @Column(name = "status")
    private String status = "success";
    @Column(name = "fail_reason")
    private String failReason;
    @Column(name = "retry_count")
    private Integer retryCount = 0;
    @Column(name = "switched_from")
    private String switchedFrom;
    @Column(name = "switched_to")
    private String switchedTo;
    @Column(name = "ts", nullable = false)
    private Long ts;

    public MetricsDetail() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }
    public Integer getTier() { return tier; }
    public void setTier(Integer tier) { this.tier = tier; }
    public String getTargetModel() { return targetModel; }
    public void setTargetModel(String targetModel) { this.targetModel = targetModel; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public Integer getPromptTokens() { return promptTokens; }
    public void setPromptTokens(Integer promptTokens) { this.promptTokens = promptTokens; }
    public Integer getCompletionTokens() { return completionTokens; }
    public void setCompletionTokens(Integer completionTokens) { this.completionTokens = completionTokens; }
    public Integer getTotalTokens() { return totalTokens; }
    public void setTotalTokens(Integer totalTokens) { this.totalTokens = totalTokens; }
    public Integer getCachedTokens() { return cachedTokens; }
    public void setCachedTokens(Integer cachedTokens) { this.cachedTokens = cachedTokens; }
    public Integer getLatencyMs() { return latencyMs; }
    public void setLatencyMs(Integer latencyMs) { this.latencyMs = latencyMs; }
    public Integer getQueueWaitMs() { return queueWaitMs; }
    public void setQueueWaitMs(Integer queueWaitMs) { this.queueWaitMs = queueWaitMs; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFailReason() { return failReason; }
    public void setFailReason(String failReason) { this.failReason = failReason; }
    public Integer getRetryCount() { return retryCount; }
    public void setRetryCount(Integer retryCount) { this.retryCount = retryCount; }
    public String getSwitchedFrom() { return switchedFrom; }
    public void setSwitchedFrom(String switchedFrom) { this.switchedFrom = switchedFrom; }
    public String getSwitchedTo() { return switchedTo; }
    public void setSwitchedTo(String switchedTo) { this.switchedTo = switchedTo; }
    public Long getTs() { return ts; }
    public void setTs(Long ts) { this.ts = ts; }
}

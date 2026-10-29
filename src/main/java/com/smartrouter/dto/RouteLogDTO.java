package com.smartrouter.dto;

public class RouteLogDTO {
    private Long id;
    private String requestId;
    private String sessionId;
    private String agentId;
    private Integer tier;
    private String targetModel;
    private String provider;
    private Integer promptTokens;
    private Integer completionTokens;
    private Integer totalTokens;
    private Integer cachedTokens;
    private Integer latencyMs;
    private Integer queueWaitMs;
    private String status;
    private String failReason;
    private Integer retryCount;
    private String switchedFrom;
    private String switchedTo;
    private Long ts;
    private String userMessage;
    private String assistantMessage;
    private Integer userTokens;
    private Integer assistantTokens;
    private Integer turnIndex;

    public RouteLogDTO() {}

    public Long getId() { return id; }
    public void setId(Long v) { this.id = v; }
    public String getRequestId() { return requestId; }
    public void setRequestId(String v) { this.requestId = v; }
    public String getSessionId() { return sessionId; }
    public void setSessionId(String v) { this.sessionId = v; }
    public String getAgentId() { return agentId; }
    public void setAgentId(String v) { this.agentId = v; }
    public Integer getTier() { return tier; }
    public void setTier(Integer v) { this.tier = v; }
    public String getTargetModel() { return targetModel; }
    public void setTargetModel(String v) { this.targetModel = v; }
    public String getProvider() { return provider; }
    public void setProvider(String v) { this.provider = v; }
    public Integer getPromptTokens() { return promptTokens; }
    public void setPromptTokens(Integer v) { this.promptTokens = v; }
    public Integer getCompletionTokens() { return completionTokens; }
    public void setCompletionTokens(Integer v) { this.completionTokens = v; }
    public Integer getTotalTokens() { return totalTokens; }
    public void setTotalTokens(Integer v) { this.totalTokens = v; }
    public Integer getCachedTokens() { return cachedTokens; }
    public void setCachedTokens(Integer v) { this.cachedTokens = v; }
    public Integer getLatencyMs() { return latencyMs; }
    public void setLatencyMs(Integer v) { this.latencyMs = v; }
    public Integer getQueueWaitMs() { return queueWaitMs; }
    public void setQueueWaitMs(Integer v) { this.queueWaitMs = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public String getFailReason() { return failReason; }
    public void setFailReason(String v) { this.failReason = v; }
    public Integer getRetryCount() { return retryCount; }
    public void setRetryCount(Integer v) { this.retryCount = v; }
    public String getSwitchedFrom() { return switchedFrom; }
    public void setSwitchedFrom(String v) { this.switchedFrom = v; }
    public String getSwitchedTo() { return switchedTo; }
    public void setSwitchedTo(String v) { this.switchedTo = v; }
    public Long getTs() { return ts; }
    public void setTs(Long v) { this.ts = v; }
    public String getUserMessage() { return userMessage; }
    public void setUserMessage(String v) { this.userMessage = v; }
    public String getAssistantMessage() { return assistantMessage; }
    public void setAssistantMessage(String v) { this.assistantMessage = v; }
    public Integer getUserTokens() { return userTokens; }
    public void setUserTokens(Integer v) { this.userTokens = v; }
    public Integer getAssistantTokens() { return assistantTokens; }
    public void setAssistantTokens(Integer v) { this.assistantTokens = v; }
    public Integer getTurnIndex() { return turnIndex; }
    public void setTurnIndex(Integer v) { this.turnIndex = v; }
}

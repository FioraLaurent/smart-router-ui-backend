package com.smartrouter.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "conversation_content")
public class ConversationContent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "request_id", nullable = false)
    private String requestId;
    @Column(name = "session_id")
    private String sessionId;
    @Column(name = "agent_id")
    private String agentId;
    @Column(name = "tier")
    private Integer tier;
    @Column(name = "user_message", columnDefinition = "TEXT")
    private String userMessage;
    @Column(name = "user_tokens")
    private Integer userTokens = 0;
    @Column(name = "assistant_message", columnDefinition = "TEXT")
    private String assistantMessage;
    @Column(name = "assistant_tokens")
    private Integer assistantTokens = 0;
    @Column(name = "turn_index")
    private Integer turnIndex = 0;
    @Column(name = "context_snapshot", columnDefinition = "TEXT")
    private String contextSnapshot;
    @Column(name = "context_tokens")
    private Integer contextTokens = 0;
    @Column(name = "ts")
    private Long ts;

    public ConversationContent() {}

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
    public String getUserMessage() { return userMessage; }
    public void setUserMessage(String userMessage) { this.userMessage = userMessage; }
    public Integer getUserTokens() { return userTokens; }
    public void setUserTokens(Integer userTokens) { this.userTokens = userTokens; }
    public String getAssistantMessage() { return assistantMessage; }
    public void setAssistantMessage(String assistantMessage) { this.assistantMessage = assistantMessage; }
    public Integer getAssistantTokens() { return assistantTokens; }
    public void setAssistantTokens(Integer assistantTokens) { this.assistantTokens = assistantTokens; }
    public Integer getTurnIndex() { return turnIndex; }
    public void setTurnIndex(Integer turnIndex) { this.turnIndex = turnIndex; }
    public String getContextSnapshot() { return contextSnapshot; }
    public void setContextSnapshot(String contextSnapshot) { this.contextSnapshot = contextSnapshot; }
    public Integer getContextTokens() { return contextTokens; }
    public void setContextTokens(Integer contextTokens) { this.contextTokens = contextTokens; }
    public Long getTs() { return ts; }
    public void setTs(Long ts) { this.ts = ts; }
}

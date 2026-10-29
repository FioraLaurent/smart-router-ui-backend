package com.smartrouter.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "alert_history")
public class AlertHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rule_id")
    private Long ruleId;
    @Column(name = "triggered_at")
    private Long triggeredAt;
    @Column(name = "resolved_at")
    private Long resolvedAt;
    @Column(name = "metric_value", precision = 10, scale = 2)
    private BigDecimal metricValue;
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    public AlertHistory() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRuleId() { return ruleId; }
    public void setRuleId(Long ruleId) { this.ruleId = ruleId; }
    public Long getTriggeredAt() { return triggeredAt; }
    public void setTriggeredAt(Long triggeredAt) { this.triggeredAt = triggeredAt; }
    public Long getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(Long resolvedAt) { this.resolvedAt = resolvedAt; }
    public BigDecimal getMetricValue() { return metricValue; }
    public void setMetricValue(BigDecimal metricValue) { this.metricValue = metricValue; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

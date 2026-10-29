package com.smartrouter.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rule_change_history")
public class RuleChangeHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rule_id", nullable = false)
    private Long ruleId;
    @Column(name = "action", nullable = false)
    private String action;
    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;
    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;
    @Column(name = "changed_at")
    private Long changedAt;

    public RuleChangeHistory() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRuleId() { return ruleId; }
    public void setRuleId(Long ruleId) { this.ruleId = ruleId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getOldValue() { return oldValue; }
    public void setOldValue(String oldValue) { this.oldValue = oldValue; }
    public String getNewValue() { return newValue; }
    public void setNewValue(String newValue) { this.newValue = newValue; }
    public Long getChangedAt() { return changedAt; }
    public void setChangedAt(Long changedAt) { this.changedAt = changedAt; }
}

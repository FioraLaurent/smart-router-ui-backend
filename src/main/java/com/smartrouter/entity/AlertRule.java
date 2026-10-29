package com.smartrouter.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "alert_rule")
public class AlertRule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "metric", nullable = false)
    private String metric;
    @Column(name = "threshold", precision = 10, scale = 2)
    private BigDecimal threshold;
    @Column(name = "duration_minutes")
    private Integer durationMinutes = 5;
    @Column(name = "notify_type")
    private String notifyType = "page";
    @Column(name = "notify_target")
    private String notifyTarget;
    @Column(name = "enabled")
    private Boolean enabled = true;

    public AlertRule() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }
    public BigDecimal getThreshold() { return threshold; }
    public void setThreshold(BigDecimal threshold) { this.threshold = threshold; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public String getNotifyType() { return notifyType; }
    public void setNotifyType(String notifyType) { this.notifyType = notifyType; }
    public String getNotifyTarget() { return notifyTarget; }
    public void setNotifyTarget(String notifyTarget) { this.notifyTarget = notifyTarget; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
}

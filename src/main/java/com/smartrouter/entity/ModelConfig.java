package com.smartrouter.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "model_config")
public class ModelConfig {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "model_id", nullable = false, unique = true)
    private String modelId;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "tier", nullable = false)
    private Integer tier;
    @Column(name = "provider")
    private String provider;
    @Column(name = "price_input", precision = 10, scale = 4)
    private BigDecimal priceInput = BigDecimal.ZERO;
    @Column(name = "price_output", precision = 10, scale = 4)
    private BigDecimal priceOutput = BigDecimal.ZERO;
    @Column(name = "context_window")
    private Integer contextWindow = 4096;
    @Column(name = "max_output")
    private Integer maxOutput = 2048;
    @Column(name = "quality_score")
    private Integer qualityScore = 70;
    @Column(name = "reliability_score")
    private Integer reliabilityScore = 100;
    @Column(name = "circuit_breaker_count")
    private Integer circuitBreakerCount = 0;
    @Column(name = "enabled")
    private Boolean enabled = true;
    @Column(name = "created_at")
    private Long createdAt;

    public ModelConfig() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getModelId() { return modelId; }
    public void setModelId(String modelId) { this.modelId = modelId; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public Integer getTier() { return tier; }
    public void setTier(Integer tier) { this.tier = tier; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public BigDecimal getPriceInput() { return priceInput; }
    public void setPriceInput(BigDecimal priceInput) { this.priceInput = priceInput; }
    public BigDecimal getPriceOutput() { return priceOutput; }
    public void setPriceOutput(BigDecimal priceOutput) { this.priceOutput = priceOutput; }
    public Integer getContextWindow() { return contextWindow; }
    public void setContextWindow(Integer contextWindow) { this.contextWindow = contextWindow; }
    public Integer getMaxOutput() { return maxOutput; }
    public void setMaxOutput(Integer maxOutput) { this.maxOutput = maxOutput; }
    public Integer getQualityScore() { return qualityScore; }
    public void setQualityScore(Integer qualityScore) { this.qualityScore = qualityScore; }
    public Integer getReliabilityScore() { return reliabilityScore; }
    public void setReliabilityScore(Integer reliabilityScore) { this.reliabilityScore = reliabilityScore; }
    public Integer getCircuitBreakerCount() { return circuitBreakerCount; }
    public void setCircuitBreakerCount(Integer circuitBreakerCount) { this.circuitBreakerCount = circuitBreakerCount; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }
}

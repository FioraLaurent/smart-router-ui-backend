package com.smartrouter.dto;

import jakarta.validation.constraints.*;

public class ModelDTO {
    private Long id;
    @NotBlank(message = "模型ID不能为空")
    private String modelId;
    private String displayName;
    @NotNull(message = "Tier不能为空")
    @Min(1) @Max(3)
    private Integer tier;
    private String provider;
    private java.math.BigDecimal priceInput = java.math.BigDecimal.ZERO;
    private java.math.BigDecimal priceOutput = java.math.BigDecimal.ZERO;
    private Integer contextWindow = 4096;
    private Integer maxOutput = 2048;
    private Integer qualityScore = 70;
    private Integer reliabilityScore = 100;
    private Integer circuitBreakerCount = 0;
    private Boolean enabled = true;
    private Boolean isCircuitOpen = false;
    private Long createdAt;

    public ModelDTO() {}

    public Long getId() { return id; }
    public void setId(Long v) { this.id = v; }
    public String getModelId() { return modelId; }
    public void setModelId(String v) { this.modelId = v; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String v) { this.displayName = v; }
    public Integer getTier() { return tier; }
    public void setTier(Integer v) { this.tier = v; }
    public String getProvider() { return provider; }
    public void setProvider(String v) { this.provider = v; }
    public java.math.BigDecimal getPriceInput() { return priceInput; }
    public void setPriceInput(java.math.BigDecimal v) { this.priceInput = v; }
    public java.math.BigDecimal getPriceOutput() { return priceOutput; }
    public void setPriceOutput(java.math.BigDecimal v) { this.priceOutput = v; }
    public Integer getContextWindow() { return contextWindow; }
    public void setContextWindow(Integer v) { this.contextWindow = v; }
    public Integer getMaxOutput() { return maxOutput; }
    public void setMaxOutput(Integer v) { this.maxOutput = v; }
    public Integer getQualityScore() { return qualityScore; }
    public void setQualityScore(Integer v) { this.qualityScore = v; }
    public Integer getReliabilityScore() { return reliabilityScore; }
    public void setReliabilityScore(Integer v) { this.reliabilityScore = v; }
    public Integer getCircuitBreakerCount() { return circuitBreakerCount; }
    public void setCircuitBreakerCount(Integer v) { this.circuitBreakerCount = v; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean v) { this.enabled = v; }
    public Boolean getIsCircuitOpen() { return isCircuitOpen; }
    public void setIsCircuitOpen(Boolean v) { this.isCircuitOpen = v; }
    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long v) { this.createdAt = v; }
}

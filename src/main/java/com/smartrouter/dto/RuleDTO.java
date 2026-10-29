package com.smartrouter.dto;

import jakarta.validation.constraints.*;

public class RuleDTO {
    private Long id;
    @NotBlank(message = "关键词不能为空")
    private String keyword;
    @NotNull(message = "Tier不能为空")
    @Min(1) @Max(3)
    private Integer tier;
    @Min(1) @Max(10)
    private Integer weight = 5;
    private Boolean enabled = true;
    private String description;
    @Min(0) @Max(100)
    private Integer grayRatio = 0;
    private Integer version;
    private Long createdAt;
    private Long updatedAt;

    public RuleDTO() {}

    public Long getId() { return id; }
    public void setId(Long v) { this.id = v; }
    public String getKeyword() { return keyword; }
    public void setKeyword(String v) { this.keyword = v; }
    public Integer getTier() { return tier; }
    public void setTier(Integer v) { this.tier = v; }
    public Integer getWeight() { return weight; }
    public void setWeight(Integer v) { this.weight = v; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean v) { this.enabled = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
    public Integer getGrayRatio() { return grayRatio; }
    public void setGrayRatio(Integer v) { this.grayRatio = v; }
    public Integer getVersion() { return version; }
    public void setVersion(Integer v) { this.version = v; }
    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long v) { this.createdAt = v; }
    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long v) { this.updatedAt = v; }
}

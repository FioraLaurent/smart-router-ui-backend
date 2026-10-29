package com.smartrouter.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "keyword_rule")
public class KeywordRule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "keyword", columnDefinition = "TEXT", nullable = false)
    private String keyword;
    @Column(name = "tier", nullable = false)
    private Integer tier;
    @Column(name = "weight")
    private Integer weight = 5;
    @Column(name = "enabled")
    private Boolean enabled = true;
    @Column(name = "description")
    private String description;
    @Column(name = "gray_ratio")
    private Integer grayRatio = 0;
    @Column(name = "version")
    private Integer version = 1;
    @Column(name = "created_at")
    private Long createdAt;
    @Column(name = "updated_at")
    private Long updatedAt;

    public KeywordRule() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public Integer getTier() { return tier; }
    public void setTier(Integer tier) { this.tier = tier; }
    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getGrayRatio() { return grayRatio; }
    public void setGrayRatio(Integer grayRatio) { this.grayRatio = grayRatio; }
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }
    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }
}

package com.smartrouter.service;

import com.smartrouter.dto.RuleDTO;
import com.smartrouter.entity.*;
import com.smartrouter.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class RuleService {

    private final KeywordRuleRepository ruleRepo;
    private final RuleChangeHistoryRepository historyRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RuleService(KeywordRuleRepository ruleRepo, RuleChangeHistoryRepository historyRepo) {
        this.ruleRepo = ruleRepo;
        this.historyRepo = historyRepo;
    }

    public List<RuleDTO> getAllRules() {
        return ruleRepo.findByOrderByTierAscWeightDesc().stream()
            .map(this::toDTO)
            .toList();
    }

    public List<RuleDTO> getActiveRules() {
        return ruleRepo.findByEnabledTrueOrderByTierAscWeightDesc().stream()
            .map(this::toDTO)
            .toList();
    }

    @Transactional
    public RuleDTO createRule(RuleDTO dto) {
        KeywordRule rule = toEntity(dto);
        rule.setVersion(1);
        rule.setCreatedAt(System.currentTimeMillis());
        rule.setUpdatedAt(System.currentTimeMillis());
        KeywordRule saved = ruleRepo.save(rule);
        saveHistory(saved.getId(), "CREATE", null, toJSON(saved));
        return toDTO(saved);
    }

    @Transactional
    public RuleDTO updateRule(Long id, RuleDTO dto) {
        KeywordRule existing = ruleRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("规则不存在: " + id));
        String oldJSON = toJSON(existing);

        existing.setKeyword(dto.getKeyword());
        existing.setTier(dto.getTier());
        existing.setWeight(dto.getWeight() != null ? dto.getWeight() : existing.getWeight());
        existing.setEnabled(dto.getEnabled() != null ? dto.getEnabled() : existing.getEnabled());
        existing.setDescription(dto.getDescription());
        existing.setGrayRatio(dto.getGrayRatio() != null ? dto.getGrayRatio() : existing.getGrayRatio());
        existing.setVersion(existing.getVersion() + 1);
        existing.setUpdatedAt(System.currentTimeMillis());

        KeywordRule saved = ruleRepo.save(existing);
        saveHistory(id, "UPDATE", oldJSON, toJSON(saved));
        return toDTO(saved);
    }

    @Transactional
    public void deleteRule(Long id) {
        ruleRepo.findById(id).ifPresent(rule -> {
            saveHistory(id, "DELETE", toJSON(rule), null);
            ruleRepo.delete(rule);
        });
    }

    @Transactional
    public RuleDTO toggleRule(Long id) {
        KeywordRule rule = ruleRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("规则不存在: " + id));
        String oldJSON = toJSON(rule);
        rule.setEnabled(!rule.getEnabled());
        rule.setUpdatedAt(System.currentTimeMillis());
        KeywordRule saved = ruleRepo.save(rule);
        saveHistory(id, "TOGGLE", oldJSON, toJSON(saved));
        return toDTO(saved);
    }

    public List<RuleChangeHistory> getHistory(Long ruleId) {
        return historyRepo.findByRuleIdOrderByChangedAtDesc(ruleId);
    }

    private void saveHistory(Long ruleId, String action, String oldVal, String newVal) {
        RuleChangeHistory h = new RuleChangeHistory();
        h.setRuleId(ruleId);
        h.setAction(action);
        h.setOldValue(oldVal);
        h.setNewValue(newVal);
        h.setChangedAt(System.currentTimeMillis());
        historyRepo.save(h);
    }

    private RuleDTO toDTO(KeywordRule r) {
        RuleDTO dto = new RuleDTO();
        dto.setId(r.getId());
        dto.setKeyword(r.getKeyword());
        dto.setTier(r.getTier());
        dto.setWeight(r.getWeight());
        dto.setEnabled(r.getEnabled());
        dto.setDescription(r.getDescription());
        dto.setGrayRatio(r.getGrayRatio());
        dto.setVersion(r.getVersion());
        dto.setCreatedAt(r.getCreatedAt());
        dto.setUpdatedAt(r.getUpdatedAt());
        return dto;
    }

    private KeywordRule toEntity(RuleDTO dto) {
        KeywordRule r = new KeywordRule();
        r.setKeyword(dto.getKeyword());
        r.setTier(dto.getTier());
        r.setWeight(dto.getWeight() != null ? dto.getWeight() : 5);
        r.setEnabled(dto.getEnabled() != null ? dto.getEnabled() : true);
        r.setDescription(dto.getDescription());
        r.setGrayRatio(dto.getGrayRatio() != null ? dto.getGrayRatio() : 0);
        return r;
    }

    private String toJSON(Object obj) {
        try { return objectMapper.writeValueAsString(obj); }
        catch (Exception e) { return "{}"; }
    }
}

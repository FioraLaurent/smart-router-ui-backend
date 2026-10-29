package com.smartrouter.service;

import com.smartrouter.dto.ModelDTO;
import com.smartrouter.entity.*;
import com.smartrouter.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class ModelConfigService {

    private static final int CIRCUIT_BREAKER_THRESHOLD = 5;

    private final ModelConfigRepository modelRepo;

    public ModelConfigService(ModelConfigRepository modelRepo) {
        this.modelRepo = modelRepo;
    }

    public List<ModelDTO> getAllModels() {
        return modelRepo.findAll().stream()
            .map(this::toDTO)
            .toList();
    }

    public List<ModelDTO> getModelsByTier(int tier) {
        return modelRepo.findByTierOrderByQualityScoreDesc(tier).stream()
            .map(this::toDTO)
            .toList();
    }

    public List<ModelDTO> getEnabledModels() {
        return modelRepo.findByEnabledTrueOrderByTierAsc().stream()
            .map(this::toDTO)
            .toList();
    }

    @Transactional
    public ModelDTO createModel(ModelDTO dto) {
        ModelConfig m = toEntity(dto);
        m.setCreatedAt(System.currentTimeMillis());
        return toDTO(modelRepo.save(m));
    }

    @Transactional
    public ModelDTO updateModel(Long id, ModelDTO dto) {
        ModelConfig m = modelRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("模型不存在: " + id));
        m.setDisplayName(dto.getDisplayName());
        m.setTier(dto.getTier());
        m.setProvider(dto.getProvider());
        m.setPriceInput(dto.getPriceInput() != null ? dto.getPriceInput() : m.getPriceInput());
        m.setPriceOutput(dto.getPriceOutput() != null ? dto.getPriceOutput() : m.getPriceOutput());
        m.setContextWindow(dto.getContextWindow() != null ? dto.getContextWindow() : m.getContextWindow());
        m.setMaxOutput(dto.getMaxOutput() != null ? dto.getMaxOutput() : m.getMaxOutput());
        m.setQualityScore(dto.getQualityScore() != null ? dto.getQualityScore() : m.getQualityScore());
        m.setReliabilityScore(dto.getReliabilityScore() != null ? dto.getReliabilityScore() : m.getReliabilityScore());
        m.setEnabled(dto.getEnabled() != null ? dto.getEnabled() : m.getEnabled());
        return toDTO(modelRepo.save(m));
    }

    @Transactional
    public void deleteModel(Long id) {
        modelRepo.deleteById(id);
    }

    @Transactional
    public void toggleModel(Long id) {
        modelRepo.findById(id).ifPresent(m -> {
            m.setEnabled(!m.getEnabled());
            modelRepo.save(m);
        });
    }

    @Transactional
    public void recordFailure(String modelId) {
        modelRepo.findByModelId(modelId).ifPresent(m -> {
            m.setCircuitBreakerCount(m.getCircuitBreakerCount() + 1);
            modelRepo.save(m);
        });
    }

    @Transactional
    public void resetCircuit(String modelId) {
        modelRepo.findByModelId(modelId).ifPresent(m -> {
            m.setCircuitBreakerCount(0);
            modelRepo.save(m);
        });
    }

    @Transactional
    public void manualCircuitBreak(Long id) {
        modelRepo.findById(id).ifPresent(m -> {
            m.setCircuitBreakerCount(CIRCUIT_BREAKER_THRESHOLD);
            modelRepo.save(m);
        });
    }

    public Map<String, Object> testConnection(Long id) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("success", true);
        result.put("message", "连接测试成功（模拟）");
        result.put("latencyMs", (int) (Math.random() * 200 + 50));
        return result;
    }

    private ModelDTO toDTO(ModelConfig m) {
        ModelDTO dto = new ModelDTO();
        dto.setId(m.getId());
        dto.setModelId(m.getModelId());
        dto.setDisplayName(m.getDisplayName());
        dto.setTier(m.getTier());
        dto.setProvider(m.getProvider());
        dto.setPriceInput(m.getPriceInput());
        dto.setPriceOutput(m.getPriceOutput());
        dto.setContextWindow(m.getContextWindow());
        dto.setMaxOutput(m.getMaxOutput());
        dto.setQualityScore(m.getQualityScore());
        dto.setReliabilityScore(m.getReliabilityScore());
        dto.setCircuitBreakerCount(m.getCircuitBreakerCount());
        dto.setEnabled(m.getEnabled());
        dto.setIsCircuitOpen(m.getCircuitBreakerCount() >= CIRCUIT_BREAKER_THRESHOLD);
        dto.setCreatedAt(m.getCreatedAt());
        return dto;
    }

    private ModelConfig toEntity(ModelDTO dto) {
        ModelConfig m = new ModelConfig();
        m.setModelId(dto.getModelId());
        m.setDisplayName(dto.getDisplayName());
        m.setTier(dto.getTier());
        m.setProvider(dto.getProvider());
        m.setPriceInput(dto.getPriceInput() != null ? dto.getPriceInput() : m.getPriceInput());
        m.setPriceOutput(dto.getPriceOutput() != null ? dto.getPriceOutput() : m.getPriceOutput());
        m.setContextWindow(dto.getContextWindow() != null ? dto.getContextWindow() : m.getContextWindow());
        m.setMaxOutput(dto.getMaxOutput() != null ? dto.getMaxOutput() : m.getMaxOutput());
        m.setQualityScore(dto.getQualityScore() != null ? dto.getQualityScore() : m.getQualityScore());
        m.setReliabilityScore(dto.getReliabilityScore() != null ? dto.getReliabilityScore() : m.getReliabilityScore());
        m.setCircuitBreakerCount(0);
        m.setEnabled(dto.getEnabled() != null ? dto.getEnabled() : true);
        return m;
    }
}

package com.smartrouter.controller;

import com.smartrouter.dto.ModelDTO;
import com.smartrouter.service.ModelConfigService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/models")
public class ModelConfigController {

    private final ModelConfigService modelService;

    public ModelConfigController(ModelConfigService modelService) {
        this.modelService = modelService;
    }

    @GetMapping
    public List<ModelDTO> getAll(@RequestParam(required = false) Integer tier,
                                 @RequestParam(required = false) Boolean enabled) {
        if (enabled != null && enabled) return modelService.getEnabledModels();
        if (tier != null) return modelService.getModelsByTier(tier);
        return modelService.getAllModels();
    }

    @PostMapping
    public ModelDTO create(@Valid @RequestBody ModelDTO dto) {
        return modelService.createModel(dto);
    }

    @PutMapping("/{id}")
    public ModelDTO update(@PathVariable Long id, @Valid @RequestBody ModelDTO dto) {
        return modelService.updateModel(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        modelService.deleteModel(id);
    }

    @PutMapping("/{id}/toggle")
    public void toggle(@PathVariable Long id) {
        modelService.toggleModel(id);
    }

    @PostMapping("/{id}/test")
    public Map<String, Object> test(@PathVariable Long id) {
        return modelService.testConnection(id);
    }

    @PostMapping("/{id}/circuit-break")
    public void circuitBreak(@PathVariable Long id) {
        modelService.manualCircuitBreak(id);
    }
}

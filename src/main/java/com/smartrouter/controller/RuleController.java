package com.smartrouter.controller;

import com.smartrouter.dto.RuleDTO;
import com.smartrouter.entity.RuleChangeHistory;
import com.smartrouter.service.RuleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping
    public List<RuleDTO> getAll(@RequestParam(required = false) Boolean active) {
        return active != null && active ? ruleService.getActiveRules() : ruleService.getAllRules();
    }

    @PostMapping
    public RuleDTO create(@Valid @RequestBody RuleDTO dto) {
        return ruleService.createRule(dto);
    }

    @PutMapping("/{id}")
    public RuleDTO update(@PathVariable Long id, @Valid @RequestBody RuleDTO dto) {
        return ruleService.updateRule(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ruleService.deleteRule(id);
    }

    @PutMapping("/{id}/toggle")
    public RuleDTO toggle(@PathVariable Long id) {
        return ruleService.toggleRule(id);
    }

    @GetMapping("/{id}/history")
    public List<RuleChangeHistory> history(@PathVariable Long id) {
        return ruleService.getHistory(id);
    }
}

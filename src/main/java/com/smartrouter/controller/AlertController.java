package com.smartrouter.controller;

import com.smartrouter.entity.*;
import com.smartrouter.service.AlertService;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping("/rules")
    public List<AlertRule> getRules() {
        return alertService.getAllRules();
    }

    @PostMapping("/rules")
    public AlertRule createRule(@RequestBody AlertRule rule) {
        return alertService.createRule(rule);
    }

    @PutMapping("/rules/{id}")
    public AlertRule updateRule(@PathVariable Long id, @RequestBody AlertRule rule) {
        return alertService.updateRule(id, rule);
    }

    @DeleteMapping("/rules/{id}")
    public void deleteRule(@PathVariable Long id) {
        alertService.deleteRule(id);
    }

    @GetMapping("/history")
    public Page<AlertHistory> history(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return alertService.getHistory(page, size);
    }
}

package com.smartrouter.service;

import com.smartrouter.entity.*;
import com.smartrouter.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
public class AlertService {

    private final AlertRuleRepository ruleRepo;
    private final AlertHistoryRepository historyRepo;

    public AlertService(AlertRuleRepository ruleRepo, AlertHistoryRepository historyRepo) {
        this.ruleRepo = ruleRepo;
        this.historyRepo = historyRepo;
    }

    public List<AlertRule> getAllRules() {
        return ruleRepo.findAll();
    }

    @Transactional
    public AlertRule createRule(AlertRule rule) {
        return ruleRepo.save(rule);
    }

    @Transactional
    public AlertRule updateRule(Long id, AlertRule dto) {
        AlertRule r = ruleRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("告警规则不存在: " + id));
        r.setName(dto.getName());
        r.setMetric(dto.getMetric());
        r.setThreshold(dto.getThreshold());
        r.setDurationMinutes(dto.getDurationMinutes());
        r.setNotifyType(dto.getNotifyType());
        r.setNotifyTarget(dto.getNotifyTarget());
        r.setEnabled(dto.getEnabled());
        return ruleRepo.save(r);
    }

    @Transactional
    public void deleteRule(Long id) {
        ruleRepo.deleteById(id);
    }

    public Page<AlertHistory> getHistory(int page, int size) {
        return historyRepo.findByOrderByTriggeredAtDesc(PageRequest.of(page, size));
    }

    @Transactional
    public void recordAlert(Long ruleId, BigDecimal metricValue, String message) {
        AlertHistory h = new AlertHistory();
        h.setRuleId(ruleId);
        h.setTriggeredAt(System.currentTimeMillis());
        h.setMetricValue(metricValue);
        h.setMessage(message);
        historyRepo.save(h);
    }
}

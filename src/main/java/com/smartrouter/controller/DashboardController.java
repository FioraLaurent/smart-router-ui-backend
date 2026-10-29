package com.smartrouter.controller;

import com.smartrouter.dto.*;
import com.smartrouter.service.DashboardService;
import com.smartrouter.service.MetricsCollector;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final MetricsCollector metricsCollector;

    public DashboardController(DashboardService dashboardService, MetricsCollector metricsCollector) {
        this.dashboardService = dashboardService;
        this.metricsCollector = metricsCollector;
    }

    @GetMapping("/summary")
    public DashboardSummary summary() {
        return dashboardService.getSummary();
    }

    @GetMapping("/trend")
    public TrendData trend(@RequestParam(defaultValue = "7") int days) {
        return dashboardService.getTrend(days);
    }

    @GetMapping("/realtime")
    public void subscribe(@RequestParam("callback") String callback) {
        // SSE 注册，WebSocket 推送通过 MetricsCollector 的 subscribe 完成
    }
}

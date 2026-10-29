package com.smartrouter.controller;

import com.smartrouter.dto.RealtimeMetrics;
import com.smartrouter.service.MetricsCollector;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;

@RestController
public class WebSocketController {

    private final MetricsCollector metricsCollector;
    private final CopyOnWriteArrayList<Object> sessions = new CopyOnWriteArrayList<>();

    public WebSocketController(MetricsCollector metricsCollector) {
        this.metricsCollector = metricsCollector;
    }

    @PostConstruct
    public void init() {
        metricsCollector.subscribe(this::broadcast);
    }

    private void broadcast(RealtimeMetrics metrics) {
        // WebSocket push via STOMP /topic/realtime
        // Logging for observability
    }

    @GetMapping("/api/health")
    public Map<String, Object> health() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("status", "UP");
        m.put("timestamp", System.currentTimeMillis());
        m.put("service", "smart-router");
        return m;
    }
}

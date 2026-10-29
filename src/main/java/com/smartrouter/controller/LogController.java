package com.smartrouter.controller;

import com.smartrouter.dto.RouteLogDTO;
import com.smartrouter.service.RouteLogService;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final RouteLogService logService;

    public LogController(RouteLogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public Page<RouteLogDTO> queryLogs(
            @RequestParam(required = false) Integer tier,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return logService.queryLogs(tier, status, from, to, page, size);
    }

    @GetMapping("/{requestId}")
    public RouteLogDTO getByRequestId(@PathVariable String requestId) {
        return logService.getByRequestId(requestId);
    }

    @GetMapping("/session/{sessionId}")
    public List<RouteLogDTO> getBySession(@PathVariable String sessionId) {
        return logService.getBySessionId(sessionId);
    }
}

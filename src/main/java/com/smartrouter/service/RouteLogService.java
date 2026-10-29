package com.smartrouter.service;

import com.smartrouter.dto.RouteLogDTO;
import com.smartrouter.entity.*;
import com.smartrouter.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RouteLogService {

    private final MetricsDetailRepository metricsRepo;
    private final ConversationContentRepository convRepo;

    public RouteLogService(MetricsDetailRepository metricsRepo,
                          ConversationContentRepository convRepo) {
        this.metricsRepo = metricsRepo;
        this.convRepo = convRepo;
    }

    public Page<RouteLogDTO> queryLogs(Integer tier, String status, Long from, Long to,
                                       int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ts"));
        Page<MetricsDetail> pageData;

        if (tier != null && from != null && to != null) {
            pageData = metricsRepo.findByTierAndTsBetweenOrderByTsDesc(tier, from, to, pageable);
        } else if (status != null && from != null && to != null) {
            pageData = metricsRepo.findByStatusAndTsBetweenOrderByTsDesc(status, from, to, pageable);
        } else if (from != null && to != null) {
            pageData = metricsRepo.findByTsBetweenOrderByTsDesc(from, to, pageable);
        } else {
            pageData = metricsRepo.findAll(pageable);
        }

        return pageData.map(this::toDTO);
    }

    public RouteLogDTO getByRequestId(String requestId) {
        return metricsRepo.findAll().stream()
            .filter(m -> m.getRequestId().equals(requestId))
            .findFirst()
            .map(this::toDTO)
            .orElse(null);
    }

    public List<RouteLogDTO> getBySessionId(String sessionId) {
        return convRepo.findBySessionIdOrderByTsAsc(sessionId).stream()
            .map(this::convToDTO)
            .toList();
    }

    private RouteLogDTO toDTO(MetricsDetail m) {
        RouteLogDTO dto = new RouteLogDTO();
        dto.setId(m.getId());
        dto.setRequestId(m.getRequestId());
        dto.setSessionId(m.getSessionId());
        dto.setAgentId(m.getAgentId());
        dto.setTier(m.getTier());
        dto.setTargetModel(m.getTargetModel());
        dto.setProvider(m.getProvider());
        dto.setPromptTokens(m.getPromptTokens());
        dto.setCompletionTokens(m.getCompletionTokens());
        dto.setTotalTokens(m.getTotalTokens());
        dto.setCachedTokens(m.getCachedTokens());
        dto.setLatencyMs(m.getLatencyMs());
        dto.setQueueWaitMs(m.getQueueWaitMs());
        dto.setStatus(m.getStatus());
        dto.setFailReason(m.getFailReason());
        dto.setRetryCount(m.getRetryCount());
        dto.setSwitchedFrom(m.getSwitchedFrom());
        dto.setSwitchedTo(m.getSwitchedTo());
        dto.setTs(m.getTs());

        // 找对应内容
        convRepo.findByRequestId(m.getRequestId()).ifPresent(c -> {
            dto.setUserMessage(c.getUserMessage());
            dto.setAssistantMessage(c.getAssistantMessage());
            dto.setUserTokens(c.getUserTokens());
            dto.setAssistantTokens(c.getAssistantTokens());
        });

        return dto;
    }

    private RouteLogDTO convToDTO(ConversationContent c) {
        RouteLogDTO dto = new RouteLogDTO();
        dto.setRequestId(c.getRequestId());
        dto.setSessionId(c.getSessionId());
        dto.setAgentId(c.getAgentId());
        dto.setTier(c.getTier());
        dto.setUserMessage(c.getUserMessage());
        dto.setAssistantMessage(c.getAssistantMessage());
        dto.setUserTokens(c.getUserTokens());
        dto.setAssistantTokens(c.getAssistantTokens());
        dto.setTurnIndex(c.getTurnIndex());
        dto.setTs(c.getTs());
        return dto;
    }
}

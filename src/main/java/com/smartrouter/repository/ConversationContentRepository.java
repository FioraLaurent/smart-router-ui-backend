package com.smartrouter.repository;

import com.smartrouter.entity.ConversationContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationContentRepository extends JpaRepository<ConversationContent, Long> {
    Optional<ConversationContent> findByRequestId(String requestId);
    List<ConversationContent> findBySessionIdOrderByTsAsc(String sessionId);
    Page<ConversationContent> findBySessionIdOrderByTsDesc(String sessionId, Pageable pageable);
}

package com.smartrouter.repository;

import com.smartrouter.entity.MetricsDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MetricsDetailRepository extends JpaRepository<MetricsDetail, Long> {

    Page<MetricsDetail> findByTsBetweenOrderByTsDesc(Long from, Long to, Pageable pageable);

    Page<MetricsDetail> findByTierAndTsBetweenOrderByTsDesc(Integer tier, Long from, Long to, Pageable pageable);

    Page<MetricsDetail> findByStatusAndTsBetweenOrderByTsDesc(String status, Long from, Long to, Pageable pageable);

    Page<MetricsDetail> findBySessionIdOrderByTsDesc(String sessionId, Pageable pageable);

    @Query("SELECT SUM(m.totalTokens) FROM MetricsDetail m WHERE m.ts >= :from AND m.ts < :to")
    Long sumTotalTokens(@Param("from") Long from, @Param("to") Long to);

    @Query("SELECT SUM(m.promptTokens) FROM MetricsDetail m WHERE m.ts >= :from AND m.ts < :to")
    Long sumPromptTokens(@Param("from") Long from, @Param("to") Long to);

    @Query("SELECT SUM(m.completionTokens) FROM MetricsDetail m WHERE m.ts >= :from AND m.ts < :to")
    Long sumCompletionTokens(@Param("from") Long from, @Param("to") Long to);

    @Query("SELECT COUNT(m) FROM MetricsDetail m WHERE m.ts >= :from AND m.ts < :to")
    Long countByTsBetween(@Param("from") Long from, @Param("to") Long to);

    @Query("SELECT COUNT(m) FROM MetricsDetail m WHERE m.status = 'fail' AND m.ts >= :from AND m.ts < :to")
    Long countFails(@Param("from") Long from, @Param("to") Long to);

    @Query("SELECT AVG(m.latencyMs) FROM MetricsDetail m WHERE m.ts >= :from AND m.ts < :to AND m.status = 'success'")
    Double avgLatency(@Param("from") Long from, @Param("to") Long to);

    @Query("SELECT m.tier, SUM(m.totalTokens) FROM MetricsDetail m WHERE m.ts >= :from AND m.ts < :to GROUP BY m.tier")
    List<Object[]> tierDistribution(@Param("from") Long from, @Param("to") Long to);

    @Query(value = "SELECT target_model, SUM(total_tokens) as total FROM metrics_detail WHERE ts >= :from AND ts < :to GROUP BY target_model ORDER BY total DESC LIMIT 10", nativeQuery = true)
    List<Object[]> topModels(@Param("from") Long from, @Param("to") Long to);

    @Query("SELECT COUNT(DISTINCT m.sessionId) FROM MetricsDetail m WHERE m.ts >= :from AND m.ts < :to")
    Long countActiveSessions(@Param("from") Long from, @Param("to") Long to);

    @Query(value = "SELECT strftime('%Y-%m-%d', ts/1000, 'unixepoch') as day, SUM(total_tokens) as tokens, COUNT(*) as count FROM metrics_detail WHERE ts >= :from GROUP BY day ORDER BY day", nativeQuery = true)
    List<Object[]> dailyTrend(@Param("from") Long from);

    @Query(value = "SELECT strftime('%Y-%m-%d %H:00', ts/1000, 'unixepoch') as hour, SUM(total_tokens) as tokens, AVG(latency_ms) as latency FROM metrics_detail WHERE ts >= :from AND ts < :to GROUP BY hour ORDER BY hour", nativeQuery = true)
    List<Object[]> hourlyTrend(@Param("from") Long from, @Param("to") Long to);

    List<MetricsDetail> findTop100ByOrderByTsDesc();
}

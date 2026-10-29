package com.smartrouter.repository;

import com.smartrouter.entity.KeywordRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KeywordRuleRepository extends JpaRepository<KeywordRule, Long> {
    List<KeywordRule> findByEnabledTrueOrderByTierAscWeightDesc();
    List<KeywordRule> findByTierOrderByWeightDesc(Integer tier);
    List<KeywordRule> findByOrderByTierAscWeightDesc();
}

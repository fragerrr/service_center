package kz.justdika.service_center.repository;

import kz.justdika.service_center.model.entity.ClaimHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClaimHistoryRepository extends JpaRepository<ClaimHistoryEntity, Long> {
    Optional<ClaimHistoryEntity> findByClaimId(Long claimId);
}

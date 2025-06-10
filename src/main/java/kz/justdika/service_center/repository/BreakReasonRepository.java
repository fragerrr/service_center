package kz.justdika.service_center.repository;

import kz.justdika.service_center.model.entity.BreakReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BreakReasonRepository extends JpaRepository<BreakReason, Long> {
    @Query(value = "SELECT r FROM BreakReason r ORDER BY random() LIMIT 1")
    Optional<BreakReason> findRandom();
}

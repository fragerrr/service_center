package kz.justdika.service_center.repository;

import kz.justdika.service_center.model.entity.ClaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<ClaimEntity, Long> {
}

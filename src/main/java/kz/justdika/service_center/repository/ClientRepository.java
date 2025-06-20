package kz.justdika.service_center.repository;

import kz.justdika.service_center.model.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByPhoneNumber(String phoneNumber);
}

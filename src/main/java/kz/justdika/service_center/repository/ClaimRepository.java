package kz.justdika.service_center.repository;

import kz.justdika.service_center.model.entity.ClaimEntity;
import kz.justdika.service_center.model.enums.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClaimRepository extends JpaRepository<ClaimEntity, Long> {
    @Query(value = """
        SELECT c FROM ClaimEntity c JOIN c.client cl WHERE cl.phoneNumber = :phoneNumber
""")
    List<ClaimEntity> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);


    List<ClaimEntity> findByStatus(ClaimStatus claimStatus);
}

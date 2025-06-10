package kz.justdika.service_center.service;

import kz.justdika.service_center.config.ServiceCenterTestConfiguration;
import kz.justdika.service_center.model.dto.claim.ClaimCreateRequest;
import kz.justdika.service_center.model.enums.ClaimStatus;
import kz.justdika.service_center.model.event.ClaimCreatedEvent;
import kz.justdika.service_center.repository.ClaimHistoryRepository;
import kz.justdika.service_center.repository.ClaimRepository;
import kz.justdika.service_center.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ServiceCenterTestConfiguration.class)
public class ClaimHistoryServiceTest {
    @Autowired
    private ClaimService claimService;

    @Autowired
    private ClaimHistoryService sut;

    @Autowired
    private ClaimHistoryRepository claimHistoryRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @BeforeEach
    void setUp() {
        claimHistoryRepository.deleteAll();
        claimRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void testHistoryIsCreated(){
        var request = new ClaimCreateRequest();
        request.setDescription("TEST");
        request.setClientId(null);
        request.setPhoneNumber("87771234567");

        var result = claimService.create(request);

        sut.claimCreated(new ClaimCreatedEvent(String.valueOf(result.id())));
        var history = claimHistoryRepository.findByClaimId(result.id()).orElse(null);
        assertThat(history).isNotNull();

        assertThat(history.oldStatus()).isEqualTo(ClaimStatus.ACCEPTANCE);
        assertThat(history.newStatus()).isEqualTo(ClaimStatus.PROCESSING);
        assertThat(history.createdAt().truncatedTo(ChronoUnit.MINUTES)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        assertThat(history.claimId()).isEqualTo(result.id());
        assertThat(history.createdBy()).isEqualTo("ClaimHistoryService.claimCreated");
    }
}

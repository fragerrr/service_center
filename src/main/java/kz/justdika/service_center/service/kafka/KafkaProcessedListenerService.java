package kz.justdika.service_center.service.kafka;

import kz.justdika.service_center.model.enums.ClaimStatus;
import kz.justdika.service_center.model.event.ClaimRepairEvent;
import kz.justdika.service_center.repository.ClaimRepository;
import kz.justdika.service_center.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProcessedListenerService {
    private final ClaimRepository claimRepository;
    private final ApplicationEventPublisher publisher;

    @SneakyThrows
    @KafkaListener(topics = Constants.Claim.CLAIM_PROCESSED, groupId = "claim_group")
    public void repair(String claimId) {
        log.info("Claim repair started");
        var claim = claimRepository.findById(Long.valueOf(claimId)).orElseThrow();

        var breakReason = claim.reason();
        claim.status(ClaimStatus.REPAIR);

        log.info("Wait until repair not finished, will wait {} seconds", breakReason.repairTime / 1000);
        Thread.sleep(breakReason.repairTime);

        claimRepository.save(claim);
        log.info("Claim repair finished");
        publisher.publishEvent(new ClaimRepairEvent(claimId));
    }


}

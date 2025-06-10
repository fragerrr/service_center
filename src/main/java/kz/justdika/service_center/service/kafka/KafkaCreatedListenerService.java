package kz.justdika.service_center.service.kafka;

import kz.justdika.service_center.model.enums.ClaimStatus;
import kz.justdika.service_center.model.event.ClaimProcessedEvent;
import kz.justdika.service_center.repository.BreakReasonRepository;
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
public class KafkaCreatedListenerService {
    private final ClaimRepository claimRepository;
    private final BreakReasonRepository breakReasonRepository;
    private final ApplicationEventPublisher publisher;

    /**
     *
     * Processing
     */
    @SneakyThrows
    @KafkaListener(topics = Constants.Claim.CLAIM_CREATED, groupId = "claim_group")
    public void processing(String claimId) {
        log.info("Received claim created with id: {}", claimId);
        log.info("Claim processing started");
        var claim = claimRepository.findById(Long.valueOf(claimId)).orElseThrow();

        var breakReason = breakReasonRepository.findRandom().get();
        claim.status(ClaimStatus.PROCESSING);
        claim.reason(breakReason);

        log.info("Wait until processing not finished, will wait {} seconds", breakReason.processingTime / 1000);
        Thread.sleep(breakReason.processingTime);

        claimRepository.save(claim);
        log.info("Claim processing finished");
        publisher.publishEvent(new ClaimProcessedEvent(claimId));
    }



}
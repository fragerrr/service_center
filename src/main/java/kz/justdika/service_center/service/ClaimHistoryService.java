package kz.justdika.service_center.service;

import kz.justdika.service_center.model.entity.ClaimHistoryEntity;
import kz.justdika.service_center.model.enums.ClaimStatus;
import kz.justdika.service_center.model.event.ClaimCreatedEvent;
import kz.justdika.service_center.model.event.ClaimProcessedEvent;
import kz.justdika.service_center.model.event.ClaimRepairEvent;
import kz.justdika.service_center.repository.ClaimHistoryRepository;
import kz.justdika.service_center.repository.ClaimRepository;
import kz.justdika.service_center.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaimHistoryService {
    private final ClaimHistoryRepository claimHistoryRepository;
    private final ClaimRepository claimRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @TransactionalEventListener
    public void claimCreated(ClaimCreatedEvent claimCreatedEvent) {
        log.info("claimCreatedEvent={}", claimCreatedEvent);
        var claimEntity = claimRepository.findById(Long.valueOf(claimCreatedEvent.claimId())).get();
        var claimHistory = new ClaimHistoryEntity()
                .claim(claimEntity)
                .oldStatus(ClaimStatus.ACCEPTANCE)
                .newStatus(ClaimStatus.PROCESSING)
                .createdAt(LocalDateTime.now())
                .createdBy("ClaimHistoryService.claimCreated");

        claimHistoryRepository.save(claimHistory);
        kafkaTemplate.send(Constants.Claim.CLAIM_CREATED, String.valueOf(claimEntity.id()));
    }

    @EventListener
    public void claimProcessed(ClaimProcessedEvent claimProcessedEvent) {
        log.info("claimProcessedEvent={}", claimProcessedEvent);
        var claimEntity = claimRepository.findById(Long.valueOf(claimProcessedEvent.claimId())).get();
        var claimHistory = new ClaimHistoryEntity()
                .claim(claimEntity)
                .oldStatus(ClaimStatus.PROCESSING)
                .newStatus(ClaimStatus.REPAIR)
                .createdAt(LocalDateTime.now())
                .createdBy("ClaimHistoryService.claimProcessed");

        claimHistoryRepository.save(claimHistory);
        kafkaTemplate.send(Constants.Claim.CLAIM_PROCESSED, String.valueOf(claimEntity.id()));
    }

    @EventListener
    public void claimRepaired(ClaimRepairEvent claimRepairEvent) {
        log.info("claimProcessedEvent={}", claimRepairEvent);
        var claimEntity = claimRepository.findById(Long.valueOf(claimRepairEvent.claimId())).get();
        var claimHistory = new ClaimHistoryEntity()
                .claim(claimEntity)
                .oldStatus(ClaimStatus.REPAIR)
                .newStatus(ClaimStatus.FINISH)
                .createdAt(LocalDateTime.now())
                .createdBy("ClaimHistoryService.claimRepaired");

        claimHistoryRepository.save(claimHistory);
        kafkaTemplate.send(Constants.Claim.CLAIM_REPAIRED, String.valueOf(claimEntity.id()));
    }

}
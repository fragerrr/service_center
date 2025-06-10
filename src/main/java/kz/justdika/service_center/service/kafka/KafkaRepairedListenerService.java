package kz.justdika.service_center.service.kafka;

import kz.justdika.service_center.model.enums.ClaimStatus;
import kz.justdika.service_center.repository.ClaimRepository;
import kz.justdika.service_center.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaRepairedListenerService {
    private final ClaimRepository claimRepository;

    @SneakyThrows
    @KafkaListener(topics = Constants.Claim.CLAIM_REPAIRED, groupId = "claim_group")
    public void repair(Long claimId) {
        var claim = claimRepository.findById(claimId).orElseThrow();

        claim.status(ClaimStatus.FINISH);

        claimRepository.save(claim);
        log.info("Finish claim chain");

        log.info("\n ---------------------------------------------------------------------\n " +
                "Your car was repaired successfully" +
                "\n ---------------------------------------------------------------------");
    }

}

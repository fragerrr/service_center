package kz.justdika.service_center.service;

import kz.justdika.service_center.model.dto.ClaimListResponse;
import lombok.extern.slf4j.Slf4j;
import kz.justdika.service_center.model.dto.claim.ClaimCreateRequest;
import kz.justdika.service_center.model.dto.claim.ClaimCreateResponse;
import kz.justdika.service_center.model.entity.ClaimEntity;
import kz.justdika.service_center.model.enums.ClaimStatus;
import kz.justdika.service_center.model.event.ClaimCreatedEvent;
import kz.justdika.service_center.repository.ClaimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaimService {
    private final ClientService clientService;
    private final ClaimRepository claimRepository;
    private final ApplicationEventPublisher publisher;

    public ClaimCreateResponse create(ClaimCreateRequest request){
        var client = clientService.getByPhoneNumber(request.getPhoneNumber());

        var claim = new ClaimEntity();
        claim.client(client);
        claim.description(request.getDescription());
        claim.status(ClaimStatus.ACCEPTANCE);

        claim = claimRepository.save(claim);

        publisher.publishEvent(new ClaimCreatedEvent(String.valueOf(claim.id())));
        log.info("claim created with id: {}", claim.id());
        return new ClaimCreateResponse(claim.id(), "Claim create successfully");
    }

    public List<ClaimListResponse> findAllByPhoneNumber(String phoneNumber){
        var list = claimRepository.findByPhoneNumber(phoneNumber);

        return list.stream().map(ClaimListResponse::new).toList();
    }


    public List<ClaimListResponse> findByStatus(ClaimStatus claimStatus){
        var list = claimRepository.findByStatus(claimStatus);

        return list.stream().map(ClaimListResponse::new).toList();
    }
}

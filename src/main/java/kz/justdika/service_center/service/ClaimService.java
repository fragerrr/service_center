package kz.justdika.service_center.service;

import kz.justdika.service_center.model.dto.claim.ClaimCreateRequest;
import kz.justdika.service_center.model.dto.claim.ClaimCreateResponse;
import kz.justdika.service_center.model.entity.ClaimEntity;
import kz.justdika.service_center.model.enums.ClaimStatus;
import kz.justdika.service_center.repository.ClaimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClaimService {
    private final ClientService clientService;
    private final ClaimRepository claimRepository;

    public ClaimCreateResponse create(ClaimCreateRequest request){
        var client = clientService.getByPhoneNumber(request.getPhoneNumber());

        var claim = new ClaimEntity();
        claim.client(client);
        claim.description(request.getDescription());
        claim.status(ClaimStatus.ACCEPTANCE);

        claim = claimRepository.save(claim);

        return new ClaimCreateResponse(claim.id(), "Claim create successfully");
    }
}

package kz.justdika.service_center.controller;

import kz.justdika.service_center.model.dto.claim.ClaimCreateRequest;
import kz.justdika.service_center.model.dto.claim.ClaimCreateResponse;
import kz.justdika.service_center.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/claim")
@RequiredArgsConstructor
public class ClaimController {
    private final ClaimService claimService;

    @PostMapping("/new")
    public ResponseEntity<ClaimCreateResponse> create(@RequestBody ClaimCreateRequest request){
        var response = claimService.create(request);

        return ResponseEntity.ok(response);
    }
}


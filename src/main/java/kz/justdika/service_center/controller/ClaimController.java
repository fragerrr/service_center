package kz.justdika.service_center.controller;

import kz.justdika.service_center.model.dto.ClaimListResponse;
import kz.justdika.service_center.model.dto.claim.ClaimCreateRequest;
import kz.justdika.service_center.model.dto.claim.ClaimCreateResponse;
import kz.justdika.service_center.model.enums.ClaimStatus;
import kz.justdika.service_center.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/by/phone-number")
    public ResponseEntity<List<ClaimListResponse>> getList(String phoneNumber){
        return ResponseEntity.ok(claimService.findAllByPhoneNumber(phoneNumber));
    }

    @GetMapping("/by/status")
    public ResponseEntity<List<ClaimListResponse>> getList(ClaimStatus status){
        return ResponseEntity.ok(claimService.findByStatus(status));
    }

}


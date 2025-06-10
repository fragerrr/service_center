package kz.justdika.service_center.model.dto;

import kz.justdika.service_center.model.entity.ClaimEntity;

public class ClaimListResponse {
    public String status;
    public String reason;
    public String description;
    public String phoneNumber;

    public ClaimListResponse(ClaimEntity claimEntity) {
        this.status = claimEntity.status().value();
        this.reason = claimEntity.reason().reason();
        this.description = claimEntity.description();
        this.phoneNumber = claimEntity.client().phoneNumber();
    }
}

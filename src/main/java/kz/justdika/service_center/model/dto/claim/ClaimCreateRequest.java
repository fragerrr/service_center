package kz.justdika.service_center.model.dto.claim;

import lombok.Data;

@Data
public class ClaimCreateRequest {
    public String description;

    public String phoneNumber;

    public Long clientId;
}

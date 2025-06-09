package kz.justdika.service_center.model.entity;

import jakarta.persistence.*;
import kz.justdika.service_center.model.enums.ClaimStatus;

import java.time.LocalDateTime;

/**
 * @author justdika
 */
@Entity
@Table(name = "claim_history")
public class ClaimHistoryEntity {
    @Id
    @SequenceGenerator(name = "seq_claim_id", sequenceName = "seq_claim_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_claim_id", strategy = GenerationType.SEQUENCE)
    public Long id;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "created_by")
    public String createdBy;

    @Column(name = "old_status")
    public ClaimStatus oldStatus;

    @Column(name = "new_status")
    @Enumerated(value = EnumType.STRING)
    public ClaimStatus newStatus;

    @ManyToOne
    @JoinColumn(name = "claim_id", referencedColumnName = "id")
    public ClaimEntity claim;

    @Column(name = "claim_id", insertable = false, updatable = false)
    public Long claimId;
}

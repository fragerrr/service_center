package kz.justdika.service_center.model.entity;

import jakarta.persistence.*;
import kz.justdika.service_center.model.enums.ClaimStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @author justdika
 */
@Accessors(fluent = true)
@Entity
@Table(name = "claim_history")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ClaimHistoryEntity {
    @Id
    @SequenceGenerator(name = "seq_claim_history_id", sequenceName = "seq_claim_history_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_claim_history_id", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "created_by")
    private String createdBy;

    @Setter
    @Column(name = "old_status")
    private ClaimStatus oldStatus;

    @Setter
    @Column(name = "new_status")
    @Enumerated(value = EnumType.STRING)
    private ClaimStatus newStatus;

    @ManyToOne
    @JoinColumn(name = "claim_id", referencedColumnName = "id")
    private ClaimEntity claim;

    @Column(name = "claim_id", insertable = false, updatable = false)
    private Long claimId;

    public ClaimHistoryEntity claim(ClaimEntity claimEntity) {
        this.claim = claimEntity;
        if(claimEntity != null){
            this.claimId = claimEntity.id();
        }
        return this;
    }
}

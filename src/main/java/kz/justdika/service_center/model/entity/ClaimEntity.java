package kz.justdika.service_center.model.entity;

import jakarta.persistence.*;
import kz.justdika.service_center.model.enums.ClaimStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

/**
 * @author justdika
 */
@Accessors(fluent = true)
@Entity
@Table(name = "claim")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ClaimEntity {
    @Id
    @SequenceGenerator(name = "seq_claim_id", sequenceName = "seq_claim_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_claim_id", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Setter
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ClaimStatus status;

    @ManyToOne
    @JoinColumn(name = "reason_id")
    private BreakReason reason;

    @Column(name = "reason_id", insertable = false, updatable = false)
    private Integer reasonId;

    @Setter
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @Column(name = "client_id", updatable = false, insertable = false)
    private Long clientId;

    @OneToMany(mappedBy = "claim")
    private List<ClaimHistoryEntity> claimHistory;

    /**
     * Setters
     */
    public ClaimEntity client(ClientEntity client) {
        this.client = client;
        if (client != null) {
            this.clientId = client.id();
        }
        return this;
    }

    public ClaimEntity reason(BreakReason reason) {
        this.reason = reason;
        if (reason != null) {
            this.reasonId = reason.id();
        }
        return this;
    }
}


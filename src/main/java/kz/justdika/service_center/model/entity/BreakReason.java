package kz.justdika.service_center.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Сущность для хранения возможных причин поломки
 *
 * @author justdika
 */
@Entity
@Table(name = "break_reason")
@Accessors(fluent = true)
@Getter
public class BreakReason {
    @Id
    @SequenceGenerator(name = "seq_claim_history_id", sequenceName = "seq_claim_history_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_claim_history_id", strategy = GenerationType.SEQUENCE)
    public Integer id;

    @Setter
    @Column(name = "reason")
    public String reason;

    @Setter
    @Column(name = "processing_time")
    public Long processingTime;

    @Setter
    @Column(name = "repair_time")
    public Long repairTime;
}


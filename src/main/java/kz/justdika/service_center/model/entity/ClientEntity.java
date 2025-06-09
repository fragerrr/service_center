package kz.justdika.service_center.model.entity;

import jakarta.persistence.*;
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
@Table(name = "client")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ClientEntity {
    @Id
    @SequenceGenerator(name = "seq_client_id", sequenceName = "seq_client_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_client_id", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Setter
    @Column(name = "last_name")
    private String lastname;

    @Setter
    @Column(name = "first_name")
    private String firstname;

    @Setter
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "client")
    private List<ClaimEntity> claims;
}

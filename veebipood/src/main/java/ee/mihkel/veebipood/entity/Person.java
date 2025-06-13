package ee.mihkel.veebipood.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Person { // User on reserveeritud Postgres andmebaasis ehk seda ei saa kasutada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;
    private PersonRole role;

    @CreatedDate
    @JsonBackReference
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonBackReference
    private LocalDateTime updatedAt;

    @ManyToOne
    @CreatedBy
    @JsonBackReference
    private Person createdBy;

    @ManyToOne
    @LastModifiedBy
    @JsonBackReference
    private Person updatedBy;
}

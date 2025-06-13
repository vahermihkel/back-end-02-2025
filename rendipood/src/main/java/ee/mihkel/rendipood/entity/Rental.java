package ee.mihkel.rendipood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double initialFee;
    private double lateFee;
    //@ColumnDefault("0")
    private int bonusDaysUsed; // null ei käi
    @ManyToOne
    private Customer customer;
    @ManyToMany
    private List<Film> films;
}

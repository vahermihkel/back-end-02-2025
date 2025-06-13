package ee.mihkel.veebipood.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String image;
    private boolean active;

    // Property 'Product.category' is not a collection and may not be a '@OneToMany', '@ManyToMany'
    //@ManyToMany --> paremal on Many ehk Category peaks olema sellisel juhul List<Category>
    //@OneToMany  --> paremal on Many ehk Category peaks olema sellisel juhul List<Category>
    //@ManyToOne
    //@OneToOne
    @ManyToOne
    private Category category;
}

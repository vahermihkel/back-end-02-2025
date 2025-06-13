package ee.mihkel.veebipood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@SequenceGenerator(name = "seq", initialValue = 41231200, allocationSize = 1)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;
    private Date created;
    private double totalSum;
    private PaymentStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderRow> orderRows;
    // kui kustutatakse tellimus, siis kõik temaga seotud OrderRow'd kustuvad
    //PRODUCT: {category_id: 99} ---> sai Errori
    //ORDER: {orderRows: {product_id: 1, quantity: 6}}

    @ManyToOne
    private Person person;

    // @OneToMany(cascadeType)
    // private List<OrderRow> orderRows;

    // @OneToOne
    // private Address destination;

}

// public class Address {
//      private String street;
//      private String streetNo;
//      private String city; jnejne postiindeks, riik

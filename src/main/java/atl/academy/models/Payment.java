package atl.academy.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "discount",nullable = false)
    private float discount;

    @Column(name = "amount",nullable = false)
    private float amount;

    @Column(name = "date_pay",nullable = false)
    private LocalDate datePay;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_shopping_cart_FK")
    private ShoppingCart shoppingCart;

}

package atl.academy.models;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
@Table(name = "detail_shop_cart")
public class DetailShopCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "count",nullable = false)
    private int count;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_shopping_cart_FK")
    private ShoppingCart shoppingCart;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_product_FK")
    private Product product;

}

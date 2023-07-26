package atl.academy.models;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
@Table(name = "detail_shop_cart")
public class DetailsShopCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "count",nullable = false)
    private int count;


}

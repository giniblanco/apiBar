package atl.academy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity(name = "detail_shop_carts")
@Data
public class DetailShopCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "count",nullable = false)
    private int count;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_shopping_cart_FK")
    @JsonIgnore
    private ShoppingCartEntity shoppingCartEntity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_product_FK")
    private ProductEntity productEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ShoppingCartEntity getShoppingCartEntity() {
        return shoppingCartEntity;
    }

    public void setShoppingCartEntity(ShoppingCartEntity shoppingCartEntity) {
        this.shoppingCartEntity = shoppingCartEntity;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    public ProductEntity getProductEntity() {
        return productEntity;
    }
}

package atl.academy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "shopping_carts")
@Data
public class ShoppingCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "date_time",nullable = false)
    private Date dateTime;

    @Column(name = "state",nullable = false)
    private boolean state;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_user_FK")
    @JsonIgnore
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bar_FK")
    private BarEntity barEntity;

    @OneToMany(targetEntity = DetailShopCartEntity.class, fetch = FetchType.LAZY, mappedBy = "shoppingCartEntity")
    private List<DetailShopCartEntity> detailsShopCartEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public BarEntity getBarEntity() {
        return barEntity;
    }

    public void setBarEntity(BarEntity barEntity) {
        this.barEntity = barEntity;
    }

    public List<DetailShopCartEntity> getDetailsShopCartEntity() { return this.detailsShopCartEntity;}

    public void setDetailsShopCartEntity(List<DetailShopCartEntity> detailsShopCartEntity) {
        this.detailsShopCartEntity = detailsShopCartEntity;
        for (DetailShopCartEntity detail : detailsShopCartEntity) {
            detail.setShoppingCartEntity(this);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
}

package atl.academy.repositories;
import atl.academy.models.DetailShopCartEntity;
import atl.academy.models.ProductEntity;
import atl.academy.models.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDetailShopCartRepository extends JpaRepository<DetailShopCartEntity, Long> {
    Optional<DetailShopCartEntity> findByShoppingCartAndProduct(ShoppingCartEntity shoppingCartEntity, ProductEntity productEntity);
}
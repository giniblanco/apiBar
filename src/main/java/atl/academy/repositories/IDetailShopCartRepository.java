package atl.academy.repositories;
import atl.academy.models.DetailShopCart;
import atl.academy.models.Product;
import atl.academy.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDetailShopCartRepository extends JpaRepository<DetailShopCart, Long> {
    Optional<DetailShopCart> findByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product);
}
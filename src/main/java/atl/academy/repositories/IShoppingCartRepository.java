package atl.academy.repositories;
import atl.academy.models.ShoppingCartEntity;
import atl.academy.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
    ShoppingCartEntity findByUserEntity(UserEntity userEntity);
    List<ShoppingCartEntity> findAllByUserEntity(UserEntity userEntity);
}

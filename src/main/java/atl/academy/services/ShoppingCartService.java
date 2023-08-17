package atl.academy.services;

import atl.academy.models.ShoppingCartEntity;
import atl.academy.models.DetailShopCartEntity;

import atl.academy.repositories.IDetailShopCartRepository;
import atl.academy.repositories.IShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    @Autowired
    private IShoppingCartRepository shoppingCartRepository;

    @Autowired
    private IDetailShopCartRepository detailShopCartRepository;

    public ShoppingCartEntity saveShoppingCart(ShoppingCartEntity shoppingCartEntity) {
        return shoppingCartRepository.save(shoppingCartEntity);
    }

    public DetailShopCartEntity addDetailToShoppingCart(DetailShopCartEntity detailShopCartEntity) {
        return detailShopCartRepository.save(detailShopCartEntity);
    }
}
package atl.academy.services;

import atl.academy.models.ShoppingCart;
import atl.academy.models.DetailShopCart;

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

    public ShoppingCart saveShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    public DetailShopCart addDetailToShoppingCart(DetailShopCart detailShopCart) {
        return detailShopCartRepository.save(detailShopCart);
    }
}
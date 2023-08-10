package atl.academy.controllers;

import atl.academy.models.DetailShopCart;
import atl.academy.models.ShoppingCart;
import atl.academy.repositories.IDetailShopCartRepository;
import atl.academy.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private IDetailShopCartRepository detailShopCartRepository;

    @PostMapping("/create")
    public ResponseEntity<ShoppingCart> createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart savedCart = shoppingCartService.saveShoppingCart(shoppingCart);
        return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
    }

    @PostMapping("/add-detail")
    public ResponseEntity<?> addDetailToCart(@RequestBody DetailShopCart detailShopCart) {
        boolean productExistsInCart = detailShopCartRepository.findByShoppingCartAndProduct(detailShopCart.getShoppingCart(), detailShopCart.getProduct()).isPresent();

        if (productExistsInCart) {
            return new ResponseEntity<>("El producto ya existe en el carrito de compras.", HttpStatus.CONFLICT);
        }

        DetailShopCart savedDetail = shoppingCartService.addDetailToShoppingCart(detailShopCart);
        return new ResponseEntity<>(savedDetail, HttpStatus.CREATED);
    }
}
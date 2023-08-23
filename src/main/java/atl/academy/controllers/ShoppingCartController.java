package atl.academy.controllers;

import atl.academy.models.DetailShopCartEntity;
import atl.academy.models.ShoppingCartEntity;
import atl.academy.repositories.IDetailShopCartRepository;
import atl.academy.services.ShoppingCartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private IDetailShopCartRepository detailShopCartRepository;

    @PostMapping("/create")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ShoppingCartEntity> createShoppingCart(@RequestBody ShoppingCartEntity shoppingCartEntity) {
        ShoppingCartEntity savedCart = shoppingCartService.saveShoppingCart(shoppingCartEntity);
        return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
    }

    /*
    @PostMapping("/add-detail")
    public ResponseEntity<?> addDetailToCart(@RequestBody DetailShopCartEntity detailShopCartEntity) {
        boolean productExistsInCart = detailShopCartRepository.findByShoppingCartAndProduct(detailShopCartEntity.getShoppingCartEntity(), detailShopCartEntity.getProductEntity()).isPresent();

        if (productExistsInCart) {
            return new ResponseEntity<>("El producto ya existe en el carrito de compras.", HttpStatus.CONFLICT);
        }

        DetailShopCartEntity savedDetail = shoppingCartService.addDetailToShoppingCart(detailShopCartEntity);
        return new ResponseEntity<>(savedDetail, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove-detail")
    public ResponseEntity<?> removeDetailFromCart(@RequestBody DetailShopCartEntity detailShopCartEntity) {
        Optional<DetailShopCartEntity> existingDetail = detailShopCartRepository.findByShoppingCartAndProduct(detailShopCartEntity.getShoppingCartEntity(), detailShopCartEntity.getProductEntity());

        if (!existingDetail.isPresent()) {
            return new ResponseEntity<>("El producto no existe en el carrito de compras.", HttpStatus.NOT_FOUND);
        }
        detailShopCartRepository.delete(existingDetail.get());
        return new ResponseEntity<>("El producto se elimino correctamente.",HttpStatus.OK);
    }*/
}
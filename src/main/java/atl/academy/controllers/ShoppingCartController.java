package atl.academy.controllers;

import atl.academy.models.DetailShopCartEntity;
import atl.academy.models.ProductEntity;
import atl.academy.models.ShoppingCartEntity;
import atl.academy.models.UserEntity;
import atl.academy.repositories.IDetailShopCartRepository;
import atl.academy.services.ProductService;
import atl.academy.services.ShoppingCartService;
import atl.academy.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {
    @Autowired
    private UserService userService;

    @Autowired
    IDetailShopCartRepository detailShopCartRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createShoppingCart(@RequestBody ShoppingCartEntity shoppingCartEntity) {
        ResponseEntity<?> response;
        try {
            String username = getAuthenticatedUsername();

            if (username == null) {
                response = new ResponseEntity<>("No autorizado.", HttpStatus.UNAUTHORIZED);
                return response;
            }

            UserEntity usuario = userService.findByUsername(username).orElseThrow();

            ShoppingCartEntity existingCart = shoppingCartService.findByUser(usuario);
            if (existingCart != null) {
                response = new ResponseEntity<>("El usuario ya tiene un carrito creado.", HttpStatus.BAD_REQUEST);
                return response;
            }

            shoppingCartEntity.setUserEntity(usuario);

            ShoppingCartEntity savedCart = shoppingCartService.saveShoppingCart(shoppingCartEntity);

            List<DetailShopCartEntity> details = shoppingCartEntity.getDetailsShopCartEntity();
            Map<ProductEntity, DetailShopCartEntity> productDetailMap = new HashMap<>();

            for (DetailShopCartEntity detail : details) {
                ProductEntity product = productService.getById(detail.getProductEntity().getId()).orElse(null);

                if (product == null) {
                    response = new ResponseEntity<>("Producto desconocido.", HttpStatus.NOT_FOUND);
                    return response;
                }

                boolean productExistsInCart = detailShopCartRepository.findByShoppingCartEntityAndProductEntity(savedCart, product).isPresent();

                if (productExistsInCart) {
                    return new ResponseEntity<>("El producto ya existe en el carrito de compras.", HttpStatus.CONFLICT);
                }

                detail.setShoppingCartEntity(savedCart);
                DetailShopCartEntity savedDetail = shoppingCartService.addDetailToShoppingCart(detail);
                productDetailMap.put(product, savedDetail);
            }

            savedCart.setDetailsShopCartEntity(new ArrayList<>(productDetailMap.values()));
            savedCart = shoppingCartService.saveShoppingCart(savedCart);

            response = new ResponseEntity<>(savedCart, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>("Hubo un error al procesar la solicitud.", HttpStatus.CONFLICT);
        }
        return response;
    }

    private String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        if (principal instanceof String) {
            return (String) principal;
        }

        return null;
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
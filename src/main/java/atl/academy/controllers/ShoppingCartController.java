package atl.academy.controllers;

import atl.academy.models.DetailShopCartEntity;
import atl.academy.models.ProductEntity;
import atl.academy.models.ShoppingCartEntity;
import atl.academy.models.UserEntity;
import atl.academy.repositories.IDetailShopCartRepository;
import atl.academy.services.ProductService;
import atl.academy.services.ShoppingCartService;
import atl.academy.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Tag(name = "Shopping Cart", description = "Operations related to shopping carts")
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
    @Operation(summary = "Create a shopping cart", description = "Create a shopping cart.")
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "201", description = "Shopping cart created successfully",
            content = @Content(schema = @Schema(implementation = ShoppingCartEntity.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request or user already has a cart")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
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

    @Operation(summary = "List user's shopping carts", description = "List user's shopping carts.")
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "List of user's shopping carts",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingCartEntity.class))))
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<ShoppingCartEntity>> getUserShoppingCarts() {
        ResponseEntity<List<ShoppingCartEntity>> response;
        try {
            String username = getAuthenticatedUsername();

            if (username == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            UserEntity usuario = userService.findByUsername(username).orElse(null);

            if (usuario == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<ShoppingCartEntity> userShoppingCarts = shoppingCartService.findAllByUser(usuario);

            return new ResponseEntity<>(userShoppingCarts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
}
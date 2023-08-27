package atl.academy.controllers;

import atl.academy.models.ProductEntity;
import atl.academy.services.ProductService;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Products", description = "Operations related to products")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get the list of products", description = "Retrieve the list of all products.")
    @ApiResponse(responseCode = "200", description = "List of products",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductEntity.class))))
    @ApiResponse(responseCode = "204", description = "No products available")
    public ResponseEntity<Map<String, Object>> getProducts(){
        Map<String, Object> response = new HashMap<>();
        var allProducts = productService.getAll();
        if(allProducts.isEmpty()){
            response.put("message", "No existen productos actualmente en base de datos.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("product", allProducts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a product", description = "Create a new product in the database.")
    @ApiResponse(responseCode = "200", description = "Product created successfully")
    @ApiResponse(responseCode = "400", description = "Product already exists or invalid request data")
    public ResponseEntity<Map<String,Object>> createProduct(@RequestBody ProductEntity productEntity) {
        Map<String, Object> response = new HashMap<>();

        if(productService.getByUsername(productEntity.getName())){
            response.put("error", "Producto ya existente.");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }else{
            productService.save(productEntity);
            response.put("message", "Producto creado correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    @PutMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a product", description = "Updates the information of a product.")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @ApiResponse(responseCode = "400", description = "Product not found or invalid request data")
    public ResponseEntity<Map<String, String>> updateProduct(@RequestBody ProductEntity productEntity){
        Map<String, String> response = new HashMap<>();

        if(productService.getById(productEntity.getId()).isPresent()){
            productService.save(productEntity);
            response.put("message", "Producto actualizado correctamente");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("error", "No existe el producto que usted esta intentando actualizar.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "Delete a product", description = "Delete a product by its ID.")
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "Product deleted successfully")
    @ApiResponse(responseCode = "400", description = "Product not found", content = @Content(schema = @Schema(implementation = Map.class)))
    public ResponseEntity<Map<String, String>> delete(@PathVariable long id){
        Map<String, String> response = new HashMap<>();
        if(productService.getById(id).isPresent()){
            productService.delete(id);
            response.put("message", "Producto eliminado correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("error", "No existe el producto que usted desea eliminar");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
     @DeleteMapping("/delete")
     @SecurityRequirement(name = "bearerAuth")
     @SecurityRequirement(name = "bearerAuth")
     @Operation(summary = "Delete all products", description = "Deletes all products from the database.")
     @ApiResponse(responseCode = "200", description = "All products deleted successfully")
     public ResponseEntity<Map<String, String>> borrarTodo(){
        Map<String, String> response = new HashMap<String, String>();
        productService.deleteAll();
        response.put("message", "Todos los productos fueron eliminados");

        return new ResponseEntity<>(response , HttpStatus.OK);
     }
}

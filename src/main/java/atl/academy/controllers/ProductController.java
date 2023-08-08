package atl.academy.controllers;

import atl.academy.models.Product;
import atl.academy.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
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
    public ResponseEntity<Map<String,Object>> createProduct(@RequestBody Product product) {
        Map<String, Object> response = new HashMap<>();

        if(productService.getByUsername(product.getName())){
            response.put("error", "Producto ya existente.");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }else{
            productService.save(product);
            response.put("message", "Producto creado correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    @PutMapping
    public ResponseEntity<Map<String, String>> updateProduct(@RequestBody Product product){
        Map<String, String> response = new HashMap<>();

        if(productService.getById(product.getId()).isPresent()){
            productService.save(product);
            response.put("message", "Producto actualizado correctamente");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("error", "No existe el producto que usted esta intentando actualizar.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
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
    public ResponseEntity<Map<String, String>> borrarTodo(){
        Map<String, String> response = new HashMap<String, String>();
        productService.deleteAll();
        response.put("message", "Todos los productos fueron eliminados");

        return new ResponseEntity<>(response , HttpStatus.OK);
    }
}

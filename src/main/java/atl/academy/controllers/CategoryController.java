package atl.academy.controllers;

import atl.academy.models.Category;
import atl.academy.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(){
        Map<String, Object> response = new HashMap<>();
        var allCategories = categoryService.getAll();
        if(allCategories.isEmpty()){
            response.put("message", "No existen categorias.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("category", allCategories);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Category category) {
        try {
            Category savedCategory = categoryService.save(category);
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            String errorMessage = "Existe una categoria con el mismo nombre";
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", errorMessage);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
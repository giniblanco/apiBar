package atl.academy.controllers;

import atl.academy.models.Category;
import atl.academy.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

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
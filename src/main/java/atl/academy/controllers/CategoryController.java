package atl.academy.controllers;

import atl.academy.models.CategoryEntity;
import atl.academy.services.CategoryService;
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

@Tag(name = "Categories", description = "Operations related to categories")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping
    @Operation(summary = "Get the list of categories", description = "Retrieve the list of all categories.")
    @ApiResponse(responseCode = "200", description = "List of categories",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryEntity.class))))
    @ApiResponse(responseCode = "204", description = "No categories available")
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
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> create(@RequestBody CategoryEntity categoryEntity) {
        try {
            CategoryEntity savedCategoryEntity = categoryService.save(categoryEntity);
            return new ResponseEntity<>(savedCategoryEntity, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            String errorMessage = "Existe una categoria con el mismo nombre";
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", errorMessage);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a category", description = "Deletes a category from the database.")
    @ApiResponse(responseCode = "200", description = "Category deleted successfully")
    @ApiResponse(responseCode = "400", description = "Category not found")
    public ResponseEntity<Map<String, String>> delete(@PathVariable long id){
        Map<String, String> response = new HashMap<>();

        if(categoryService.getById(id).isPresent()){
            categoryService.delete(id);
            response.put("message", "Categoria eliminado correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("error", "No existe la categoria que usted desea eliminar");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
   @DeleteMapping("/delete")
   @SecurityRequirement(name = "bearerAuth")
   @Operation(summary = "Delete all categories", description = "Deletes all categories from the database.")
   @ApiResponse(responseCode = "200", description = "All categories deleted successfully")
   public ResponseEntity<Map<String, String>> borrarTodo(){
        Map<String, String> response = new HashMap<String, String>();
        categoryService.deleteAll();
        response.put("message", "Todas las categorias fueron eliminados");

        return new ResponseEntity<>(response , HttpStatus.OK);
   }

    @PutMapping("/update/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a category", description = "Updates the information of a category.")
    @ApiResponse(responseCode = "200", description = "Category updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data or category not found")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable Long id, @RequestBody CategoryEntity categoryEntity){
        if(!id.equals(categoryEntity.getId())){
            return ResponseEntity.badRequest().build();
        }

        CategoryEntity updated = categoryService.updateCategory(id, categoryEntity);

        if(updated != null){
            return ResponseEntity.ok(updated);
        } else{
            return ResponseEntity.notFound().build();
        }
    }


}
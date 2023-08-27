package atl.academy.controllers;

import atl.academy.models.BarEntity;
import atl.academy.services.BarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bars", description = "Operations related to bars")
@RestController
@RequestMapping("/api/bar")
public class BarController {

    @Autowired
    private BarService barService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a bar", description = "Create a new bar in the database.")
    @ApiResponse(responseCode = "201", description = "Bar created successfully")
    @ApiResponse(responseCode = "400", description = "Bar with the same name already exists or invalid request data")
    public ResponseEntity<String> create(@RequestBody BarEntity barEntity){
        try {
            barService.save(barEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Bar created successfully.");
        } catch (IllegalArgumentException e){
            String errorMessage = "Existe un bar con el mismo nombre";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get the list of bars", description = "Retrieve the list of all bars.")
    @ApiResponse(responseCode = "200", description = "List of bars")
    @ApiResponse(responseCode = "404", description = "No bars available")
    public ResponseEntity<List<BarEntity>> getAll(){
        List<BarEntity> barEntityList = barService.getAll();
        if(barEntityList.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(barEntityList);
        }
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a bar", description = "Deletes a bar from the database.")
    @ApiResponse(responseCode = "200", description = "Bar deleted successfully")
    @ApiResponse(responseCode = "404", description = "Bar not found")
    public ResponseEntity<String> delete(@PathVariable Long id){
        if(barService.delete(id)){
            return ResponseEntity.status(HttpStatus.OK).body("bar "+ id +" removed successfully");
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a bar", description = "Updates the information of a bar.")
    @ApiResponse(responseCode = "200", description = "Bar updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data or bar not found")
    public ResponseEntity<BarEntity> update(@PathVariable Long id, @RequestBody BarEntity barEntity){
        if(!id.equals(barEntity.getId())){
            return ResponseEntity.badRequest().build();
        }

        BarEntity updated = barService.update(id, barEntity);

        if(updated != null){
            return ResponseEntity.ok(updated);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

}

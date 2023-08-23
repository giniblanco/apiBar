package atl.academy.controllers;

import atl.academy.models.BarEntity;
import atl.academy.services.BarService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bar")
public class BarController {

    @Autowired
    private BarService barService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
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
    public ResponseEntity<String> delete(@PathVariable Long id){
        if(barService.delete(id)){
            return ResponseEntity.status(HttpStatus.OK).body("bar "+ id +" removed successfully");
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
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

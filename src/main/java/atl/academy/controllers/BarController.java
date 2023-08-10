package atl.academy.controllers;

import atl.academy.models.Bar;
import atl.academy.services.BarService;
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
    public ResponseEntity<String> create(@RequestBody Bar bar){
        try {
            barService.save(bar);
            return ResponseEntity.status(HttpStatus.CREATED).body("Bar created successfully.");
        } catch (IllegalArgumentException e){
            String errorMessage = "Existe un bar con el mismo nombre";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    @GetMapping
    public ResponseEntity<List<Bar>> getAll(){
        List<Bar> barList = barService.getAll();
        if(barList.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(barList);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        if(barService.delete(id)){
            return ResponseEntity.status(HttpStatus.OK).body("bar "+ id +" removed successfully");
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bar> update(@PathVariable Long id, @RequestBody Bar bar){
        if(!id.equals(bar.getId())){
            return ResponseEntity.badRequest().build();
        }

        Bar updated = barService.update(id, bar);

        if(updated != null){
            return ResponseEntity.ok(updated);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

}

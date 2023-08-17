package atl.academy.controllers;

import atl.academy.models.TableEntity;
import atl.academy.services.TableService;
import atl.academy.utils.DefaultMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tables")
public class TableController {
    @Autowired
    TableService tableService;
    @Autowired
    DefaultMessages defaultMessages;

    @GetMapping
    public ResponseEntity<Object> GetTables(){
        Map<String, Object> response = new HashMap<>();
        var tables = tableService.getTables();

        if(tables.isEmpty()){
            response.put("message", defaultMessages.EMPTY);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(tables, HttpStatus.OK);
        }
    }
    @PostMapping
    public ResponseEntity<Object> createTable(@Valid @RequestBody TableEntity tableEntity, BindingResult verifyErrors){
        Map<String, Object> response = new HashMap<>();
        if(tableService.getBy(tableEntity.getNumberTable()).isPresent()){
            response.put("error", defaultMessages.FOUND);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if(verifyErrors.hasErrors()){
            Map<String, Object> errors = new HashMap<>();
            verifyErrors.getFieldErrors().forEach(
                    err -> errors.put(err.getField(), err.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        tableService.saveTable(tableEntity);
        response.put("message", defaultMessages.ADD);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Map<String, Object>> modifyTable(@RequestBody TableEntity tableEntity){
        Map<String, Object> response = new HashMap<>();
        if(tableService.getBy(tableEntity.getId()).isPresent()){
            tableService.saveTable(tableEntity);
            response.put("message", defaultMessages.MODIFIED);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("error", defaultMessages.NOT_FOUND);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteTable(@RequestParam Long id){
        Map<String, Object> response = new HashMap<>();
        if(tableService.getBy(id).isPresent()){
            tableService.deleteTable(id);
            response.put("message", defaultMessages.DELETE);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("error", defaultMessages.NOT_FOUND);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
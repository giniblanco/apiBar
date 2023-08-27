package atl.academy.controllers;

import atl.academy.models.TableEntity;
import atl.academy.services.TableService;
import atl.academy.utils.DefaultMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Tables", description = "Operations related to tables")
@RestController
@RequestMapping("/api/tables")
public class TableController {
    @Autowired
    TableService tableService;
    @Autowired
    DefaultMessages defaultMessages;

    @GetMapping
    @Operation(summary = "Get the list of tables", description = "Retrieve the list of all tables.")
    @ApiResponse(responseCode = "200", description = "List of tables", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TableEntity.class))))
    @ApiResponse(responseCode = "204", description = "No content")
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
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @Operation(summary = "Create a table", description = "Create a new table in the database.")
    @ApiResponse(responseCode = "201", description = "Table created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
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
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a table", description = "Updates the information of a table.")
    @ApiResponse(responseCode = "200", description = "Table updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "404", description = "Table not found")
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
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a table", description = "Deletes a table from the database.")
    @ApiResponse(responseCode = "200", description = "Table deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "404", description = "Table not found")
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
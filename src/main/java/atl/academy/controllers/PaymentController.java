package atl.academy.controllers;

import atl.academy.models.PaymentEntity;
import atl.academy.services.PaymentService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "Payment", description = "Operations related to payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Create a payment", description = "Create a new payment.")
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "201", description = "Payment created successfully",
            content = @Content(schema = @Schema(implementation = PaymentEntity.class)))
    public ResponseEntity<String> create(@RequestBody PaymentEntity paymentEntity){
        paymentService.save(paymentEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Payment created successfully.");
    }

    @Operation(summary = "Update a payment", description = "Update an existing payment by ID.")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "Payment updated successfully",
            content = @Content(schema = @Schema(implementation = PaymentEntity.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request or payment ID mismatch")
    @ApiResponse(responseCode = "404", description = "Payment not found")
    public ResponseEntity<PaymentEntity> update(@PathVariable Long id, @RequestBody PaymentEntity paymentEntity){
        if(!id.equals(paymentEntity.getId())){
            return ResponseEntity.badRequest().build();
        }

        PaymentEntity paymenUpdate = paymentService.update(id, paymentEntity);

        if(paymenUpdate != null){
            return ResponseEntity.ok(paymenUpdate);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all payments", description = "Get a list of all payments.")
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "List of payments",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PaymentEntity.class))))
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "No payments found")
    public ResponseEntity<List<PaymentEntity>> getAll(){
        var paymentList = paymentService.getAll();
        if(paymentList.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(paymentList);
        }
    }

    @Operation(summary = "Get payment by ID", description = "Get a payment by its ID.")
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "Payment found",
            content = @Content(schema = @Schema(implementation = PaymentEntity.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Payment not found")
    public ResponseEntity<PaymentEntity> getForId(@PathVariable Long id){
        var paymentFound = paymentService.getForId(id);
        if(paymentFound != null){
            return ResponseEntity.ok().body(paymentFound);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

package atl.academy.controllers;

import atl.academy.models.PaymentEntity;
import atl.academy.services.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> create(@RequestBody PaymentEntity paymentEntity){
        paymentService.save(paymentEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Payment created successfully.");
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
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

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<PaymentEntity>> getAll(){
        var paymentList = paymentService.getAll();
        if(paymentList.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(paymentList);
        }
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PaymentEntity> getForId(@PathVariable Long id){
        var paymentFound = paymentService.getForId(id);
        if(paymentFound != null){
            return ResponseEntity.ok().body(paymentFound);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

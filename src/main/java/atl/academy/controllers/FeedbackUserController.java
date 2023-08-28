package atl.academy.controllers;

import atl.academy.models.FeedbackUserEntity;
import atl.academy.models.PaymentEntity;
import atl.academy.services.FeedbackUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "/api/feedback")
public class FeedbackUserController {

    @Autowired
    private FeedbackUserService feedbackUserService;

    @Operation(summary = "Create a feedback", description = "Create a new feedback.")
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "201", description = "Feedback created successfully",
            content = @Content(schema = @Schema(implementation = FeedbackUserEntity.class)))
    public ResponseEntity<String> create(@RequestBody FeedbackUserEntity feedbackUserEntity){
        feedbackUserService.save(feedbackUserEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Feedback created successfully.");
    }

    @Operation(summary = "Update a feedback", description = "Update an existing feedback by ID.")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "Feedback updated successfully",
            content = @Content(schema = @Schema(implementation = FeedbackUserEntity.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request or feedback ID mismatch")
    @ApiResponse(responseCode = "404", description = "Feedback not found")
    public ResponseEntity<FeedbackUserEntity> update(@PathVariable Long id, @RequestBody FeedbackUserEntity feedbackUserEntity){
        if(!id.equals(feedbackUserEntity.getId())){
            return ResponseEntity.badRequest().build();
        }

        FeedbackUserEntity updateFeedback = feedbackUserService.update(id, feedbackUserEntity);

        if(updateFeedback != null){
            return ResponseEntity.ok(updateFeedback);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a feedback", description = "Deletes a feedback from the database.")
    @ApiResponse(responseCode = "200", description = "feedback deleted successfully")
    @ApiResponse(responseCode = "404", description = "feedback not found")
    public ResponseEntity<String> delete(@PathVariable Long id){
        if(feedbackUserService.delete(id)){
            return ResponseEntity.status(HttpStatus.OK).body("feedback "+ id +" removed successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

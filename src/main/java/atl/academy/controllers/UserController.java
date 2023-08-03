package atl.academy.controllers;

import atl.academy.models.User;
import atl.academy.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) throws JsonProcessingException {
        Map<String, Object> messageResponse = new HashMap<>();

        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            messageResponse.put("message", "Se encontraron los siguientes errores.");
            bindingResult.getFieldErrors().forEach(
                    f -> errors.put(f.getField(), f.getDefaultMessage())
            );
            messageResponse.put("errors", errors);

            return ResponseEntity.badRequest().body(messageResponse);
        }else{
            userService.registerUser(user);
            messageResponse.put("message", "Usuario registrado con exito");

            return ResponseEntity.ok().body(messageResponse);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }
}

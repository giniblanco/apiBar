package atl.academy.controllers;

import atl.academy.models.User;
import atl.academy.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bar")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody User user){
        userService.registerUser(user);
    }
    @GetMapping("/")
    ResponseEntity<String> hello() {
        return ResponseEntity.badRequest().body("Bad request!");
    }
}

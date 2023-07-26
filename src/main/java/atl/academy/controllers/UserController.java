package atl.academy.controllers;

import atl.academy.models.User;
import atl.academy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/bar")
    public void registerUser(@RequestBody User user){
        userService.registerUser(user);
    }

    @DeleteMapping("/api/bar/{id}")
    public void deleteUser(Long id){
        userService.deleteUser(id);
    }
}

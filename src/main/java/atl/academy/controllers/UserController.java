package atl.academy.controllers;

import atl.academy.models.UserEntity;
import atl.academy.services.UserService;
import atl.academy.utils.DefaultMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    DefaultMessages defaultMessages;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> getUsers(){
        if(userService.getAll().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            var users = userService.getAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody UserEntity userEntity, BindingResult bindingResult){
        Map<String, Object> httpResponse = new LinkedHashMap<>();
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            httpResponse.put("message", "Se encontraron los siguientes errores.");
            bindingResult.getFieldErrors().forEach(
                    f -> errors.put(f.getField(), f.getDefaultMessage())
            );
            httpResponse.put("errors", errors);

            return new ResponseEntity<>(httpResponse, HttpStatus.BAD_REQUEST);
        }else{
            //Encriptamos el password antes de guardar el user.
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userService.save(userEntity);
            httpResponse.put("message", "Usuario registrado con exito");

            return new ResponseEntity<>(httpResponse, HttpStatus.OK);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam Long id){
        Map<String, Object> httpResponse = new LinkedHashMap<>();
        if(userService.getBy(id).isPresent()){
            userService.delete(id);
            httpResponse.put("message", defaultMessages.DELETE);

            return new ResponseEntity<>(httpResponse, HttpStatus.OK);
        }else{
            httpResponse.put("error", defaultMessages.NOT_FOUND);

            return new ResponseEntity<>(httpResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity){
        Map<String, Object> httpResponse = new LinkedHashMap<>();
        if(userService.getBy(userEntity.getId()).isPresent()){
            //Encriptamos el password antes de guardar el user.
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userService.save(userEntity);
            httpResponse.put("message", defaultMessages.MODIFIED);
            return new ResponseEntity<>(httpResponse, HttpStatus.OK);

        }else{
            httpResponse.put("error", defaultMessages.NOT_FOUND);
            return new ResponseEntity<>(httpResponse, HttpStatus.BAD_REQUEST);
        }
    }
}

package atl.academy.controllers;

import atl.academy.models.UserEntity;
import atl.academy.services.UserService;
import atl.academy.utils.DefaultMessages;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/users")
public class UserController {
    UserService userService;
    DefaultMessages defaultMessages;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, DefaultMessages defaultMessages, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.defaultMessages = defaultMessages;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *  Este metodo traera a todos los usuarios que se encuentren
     *  en la base de datos si es que lo encuentra.
     * @return Listado de usuarios en formato JSON.
     */
    @GetMapping
    public ResponseEntity<?> getUsers(){
        if(userService.getAll().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            var users = userService.getAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    /**
     *  Este metodo registrara a un usuario nuevo.
     * @param userEntity el usuario en cuestion
     * @param bindingResult para capturar los errores si es que lo hay a la hora de validar la data.
     * @return un string notificando si se registro o no.
     */
    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserEntity userEntity, BindingResult bindingResult){
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

    /**
     *  Este metodo eliminara un usuario en base de datos.
     * @param id del usuario a eliminar.
     * @return un string, notificando si el usuario realmente fue eliminado de la base de datos.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
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

    /**
     *  Este metodo actualizara la data de un usuario en particular.
     * @param userEntity el usuario en cuestion.
     * @return string, notificando si el usuario fue realmente modificado con exito.
     */
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

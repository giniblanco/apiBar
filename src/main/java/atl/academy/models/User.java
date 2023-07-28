package atl.academy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "No debe ser null.") @NotBlank(message = "Debe contener un valor.")
    @Email(message = "Debe contener la estructura de un email.") @Pattern(regexp = "([a-z]|[0-9])+@[a-z]+\\.[a-z]+", message = "El email debe contener un punto.")
    @Column(name = "email")
    private String email;

    @NotNull(message = "No debe ser null.") @NotBlank(message = "Debe contener un valor.")
    @Size(min = 4, max = 32, message = "Debe contener al menos 4 caracteres y maximo 32.")
    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @NotNull(message = "No debe ser null.") @NotBlank(message = "Debe contener un valor.")
    @Column(name ="role_name")
    private String roleName;
}

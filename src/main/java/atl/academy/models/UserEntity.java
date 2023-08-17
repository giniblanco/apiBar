package atl.academy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import javax.management.relation.Role;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //Implementa el patron de diseño Builder.
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email //Nos aseguramos que siosi deba tener la estructura de un email.
    @Size(max = 80) //Tendra un maximo de 80 caracteres.
    private String email;

    @NotBlank //No se registraran campos username vacios.
    @Size(max = 30) //Tendra un maximo de 30 caracteres.
    private String username;

    @NotBlank  //No se registraran campos password vacios.
    private String password;

    @ManyToOne(targetEntity = RoleEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_fk")
    private RoleEntity role;
}
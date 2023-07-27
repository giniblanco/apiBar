package atl.academy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "No debe ser nulo")
    @Column(name = "email",nullable = false)
    private String email;

    @NotEmpty(message = "No debe ser nulo")
    @Size(min = 4, max = 16)
    @Column(name = "password",nullable = false)
    private String password;

    @NotEmpty(message = "No debe ser nulo")
    @Column(name = "phone")
    private String phone;

    @NotEmpty(message = "No debe ser nulo")
    @Column(name ="role_name",nullable = false)
    private String role_name;
}

package atl.academy.models;

import atl.academy.utils.DefaultMessages;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Entity(name = "tables")
@Getter @Setter
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "No debe ser null")
    @Column(name = "number_table")
    private Integer numberTable;

    @Column(name = "is_active")
    private boolean isActive;
}

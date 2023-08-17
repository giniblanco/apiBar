package atl.academy.models;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name = "tables")
@Getter @Setter
public class TableEntity {
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

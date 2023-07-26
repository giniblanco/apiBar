package atl.academy.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "table")
public class Tables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number_table", nullable = false)
    private int numberTable;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}

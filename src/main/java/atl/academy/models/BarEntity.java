package atl.academy.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "bars")
@Data
public class BarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "description",nullable = false)
    private String description;

    @OneToMany(targetEntity = FeedbackUserEntity.class,
            fetch = FetchType.EAGER,
            mappedBy = "barEntity")
    private List<FeedbackUserEntity> feedbackUsers;
}

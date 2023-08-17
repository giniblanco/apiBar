package atl.academy.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "feedback_users")
@Data
public class FeedbackUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "score", nullable = false)
    private int score;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_user_FK")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_bar_FK")
    private BarEntity barEntity;

}



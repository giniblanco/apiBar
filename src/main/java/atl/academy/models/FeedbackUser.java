package atl.academy.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "feedback_user")
public class FeedbackUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "score", nullable = false)
    private int score;

}



package atl.academy.repositories;

import atl.academy.models.FeedbackUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFeedbackUserRepository extends JpaRepository<FeedbackUserEntity, Long> {
}

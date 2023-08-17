package atl.academy.repositories;

import atl.academy.models.BarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IBarRepository extends JpaRepository<BarEntity, Long> {
    boolean existsByName(String name);
}

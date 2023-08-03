package atl.academy.repositories;

import atl.academy.models.Bar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BarRepository extends JpaRepository<Bar, Long> {
}

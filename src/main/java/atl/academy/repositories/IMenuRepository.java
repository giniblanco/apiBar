package atl.academy.repositories;

import atl.academy.models.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuRepository extends CrudRepository<Menu, Long> {
}
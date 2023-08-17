package atl.academy.repositories;

import atl.academy.models.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITableRepository extends JpaRepository<TableEntity, Long> { }

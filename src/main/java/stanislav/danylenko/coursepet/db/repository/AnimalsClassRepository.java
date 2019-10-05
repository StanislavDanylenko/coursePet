package stanislav.danylenko.coursepet.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.AnimalsClass;

@Repository
public interface AnimalsClassRepository extends JpaRepository<AnimalsClass, Long> {

    AnimalsClass findByName(String name);

}

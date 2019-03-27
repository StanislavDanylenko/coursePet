package stanislav.danylenko.coursepet.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}

package stanislav.danylenko.coursepet.db.repository.associative;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;

import java.util.List;

@Repository
public interface AnimalGraftRepository extends JpaRepository<AnimalGraft, Long> {

    List<AnimalGraft> findByAnimalId(Long animalId);

}

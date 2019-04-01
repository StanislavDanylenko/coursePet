package stanislav.danylenko.coursepet.db.repository.associative;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalGraftPK;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnimalGraftRepository extends JpaRepository<AnimalGraft, AnimalGraftPK> {

    List<AnimalGraft> findByAnimalId(Long animalId);

}

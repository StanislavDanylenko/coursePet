package stanislav.danylenko.coursepet.db.repository.associative;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.entity.Disease;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;

import java.util.List;

@Repository
public interface AnimalDiseaseRepository extends JpaRepository<AnimalDisease, Long> {

    List<AnimalDisease> findByAnimalId(Long animalId);
    List<AnimalDisease> findByDiseaseId(Long diseaseId);
    List<AnimalDisease> findByAnimalAndDisease(Animal animal, Disease disease);

}

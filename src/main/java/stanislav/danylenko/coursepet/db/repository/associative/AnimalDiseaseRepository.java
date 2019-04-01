package stanislav.danylenko.coursepet.db.repository.associative;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalDiseasePK;

import java.util.List;

@Repository
public interface AnimalDiseaseRepository extends JpaRepository<AnimalDisease, AnimalDiseasePK> {

    List<AnimalDisease> findByAnimalId(Long animalId);
    List<AnimalDisease> findByDiseaseId(Long diseaseId);

}

package stanislav.danylenko.coursepet.db.repository.associative;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.entity.Graft;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.web.model.schedule.AnimalGraftStatistics;

import java.util.List;

@Repository
public interface AnimalGraftRepository extends JpaRepository<AnimalGraft, Long> {

    List<AnimalGraft> findByAnimalId(Long animalId);
    List<AnimalGraft> findByAnimalAndGraft(Animal animal, Graft graft);

    @Query("SELECT new stanislav.danylenko.coursepet.web.model.schedule.AnimalGraftStatistics(ag.graft, MAX(ag.date)) FROM AnimalGraft ag WHERE ag.animal.id = ?1 GROUP BY ag.graft")
    List<AnimalGraftStatistics> getListOfLastGrafts(Long id);

}

package stanislav.danylenko.coursepet.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.web.model.epidemic.EpidemicStatistic;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Animal findByName(String name);
    List<Animal> findByUser_CountryId(Long userCountryId);
    List<Animal> findByUserId(Long userId);

    @Query("SELECT MAX(id) FROM Animal")
    Long getLastInsertedId();
}

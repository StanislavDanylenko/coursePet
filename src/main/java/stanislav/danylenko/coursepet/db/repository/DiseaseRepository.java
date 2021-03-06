package stanislav.danylenko.coursepet.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.Disease;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    Disease findByName(String name);

}

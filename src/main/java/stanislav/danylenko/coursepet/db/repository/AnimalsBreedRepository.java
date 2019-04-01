package stanislav.danylenko.coursepet.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.AnimalsBreed;

@Repository
public interface AnimalsBreedRepository extends JpaRepository<AnimalsBreed, Long> {

    AnimalsBreed findByName(String name);

}

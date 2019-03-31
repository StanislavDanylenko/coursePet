package stanislav.danylenko.coursepet.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findByName(String name);

}

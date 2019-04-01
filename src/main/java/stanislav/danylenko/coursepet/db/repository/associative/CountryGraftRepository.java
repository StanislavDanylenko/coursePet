package stanislav.danylenko.coursepet.db.repository.associative;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.Graft;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalDiseasePK;
import stanislav.danylenko.coursepet.db.entity.pk.CountryGraftPK;

import java.util.List;

@Repository
public interface CountryGraftRepository extends JpaRepository<CountryGraft, CountryGraftPK> {
    List<CountryGraft> findByCountryId(Long countryId);
}

package stanislav.danylenko.coursepet.db.repository.associative;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.Country;
import stanislav.danylenko.coursepet.db.entity.Graft;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;

import java.util.List;

@Repository
public interface CountryGraftRepository extends JpaRepository<CountryGraft, Long> {
    List<CountryGraft> findByCountryId(Long countryId);
    CountryGraft findByCountryAndGraft(Country country, Graft graft);
}

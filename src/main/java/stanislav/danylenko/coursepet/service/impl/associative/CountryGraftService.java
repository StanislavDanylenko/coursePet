package stanislav.danylenko.coursepet.service.impl.associative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;
import stanislav.danylenko.coursepet.db.entity.pk.CountryGraftPK;
import stanislav.danylenko.coursepet.db.repository.associative.CountryGraftRepository;
import stanislav.danylenko.coursepet.service.ComplexIdService;

@Service
public class CountryGraftService implements ComplexIdService<CountryGraft, CountryGraftPK> {
    
    @Autowired
    private CountryGraftRepository countryGraftRepository;


    public CountryGraft save(CountryGraft entity) {
        return countryGraftRepository.save(entity);
    }

    public void delete(CountryGraft entity) {
        countryGraftRepository.delete(entity);
    }

    public CountryGraft update(CountryGraft entity) {
        return countryGraftRepository.save(entity);
    }

    public CountryGraft find(CountryGraftPK id) {
        return countryGraftRepository.getOne(id);
    }

    public Iterable<CountryGraft> findAll() {
        return countryGraftRepository.findAll();
    }

    public void delete(CountryGraftPK id) {
       countryGraftRepository.deleteById(id);
    }
}

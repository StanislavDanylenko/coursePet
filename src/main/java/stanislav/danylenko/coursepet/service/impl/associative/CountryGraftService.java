package stanislav.danylenko.coursepet.service.impl.associative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;
import stanislav.danylenko.coursepet.db.entity.pk.CountryGraftPK;
import stanislav.danylenko.coursepet.db.repository.associative.CountryGraftRepository;
import stanislav.danylenko.coursepet.service.ComplexIdService;
import stanislav.danylenko.coursepet.service.impl.CountryService;
import stanislav.danylenko.coursepet.service.impl.GraftService;
import stanislav.danylenko.coursepet.web.model.associative.CountryGraftDto;

@Service
public class CountryGraftService implements ComplexIdService<CountryGraft, CountryGraftPK> {
    
    @Autowired
    private CountryGraftRepository countryGraftRepository;

    @Autowired
    private CountryService countryService;

    @Autowired
    private GraftService graftService;



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

    public CountryGraft prepareForSaving(CountryGraftDto dto) {

        CountryGraft countryGraft = new CountryGraft();

        if(dto.getCountryId() != null) {
            countryGraft.setCountry(countryService.find(dto.getCountryId()));
        }

        if(dto.getGraftId() != null) {
            countryGraft.setGraft(graftService.find(dto.getGraftId()));
        }

        return countryGraft;
    }
}

package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Country;
import stanislav.danylenko.coursepet.db.entity.Graft;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;
import stanislav.danylenko.coursepet.db.repository.CountryRepository;
import stanislav.danylenko.coursepet.db.repository.associative.CountryGraftRepository;
import stanislav.danylenko.coursepet.service.SimpleIdService;
import stanislav.danylenko.coursepet.web.model.CountryWithGraftDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService implements SimpleIdService<Country> {

    public static final String DEFAULT_COUNTRY = "Ukraine";
    
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryGraftRepository countryGraftRepository;
    
    @Override
    public Country save(Country entity) {
        return countryRepository.save(entity);
    }

    @Override
    public void delete(Country entity) {
        countryRepository.delete(entity);
    }

    @Override
    public Country update(Country entity) {
        return countryRepository.save(entity);
    }

    @Override
    public Country find(Long id) {
        return countryRepository.getOne(id);
    }

    public List<Graft> findGraftsByCountryId(Long countryId) {
        List<CountryGraft> countryGrafts = countryGraftRepository.findByCountryId(countryId);
        List<Graft> grafts = new ArrayList<>();
        for(CountryGraft countryGraft : countryGrafts) {
            grafts.add(countryGraft.getGraft());
        }
        return grafts;
    }

    @Override
    public Iterable<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        countryRepository.deleteById(id);
    }

    public Country getDefaultCountry() {
        return countryRepository.findByName(DEFAULT_COUNTRY);
    }

    public CountryWithGraftDto getCountryWithGrafts(Long id){
        Country country = find(id);
        List<Graft> grafts = findGraftsByCountryId(id);
        return new CountryWithGraftDto(country, grafts);
    }
}

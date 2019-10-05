package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Country;
import stanislav.danylenko.coursepet.db.entity.Graft;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;
import stanislav.danylenko.coursepet.db.repository.CountryRepository;
import stanislav.danylenko.coursepet.db.repository.associative.CountryGraftRepository;
import stanislav.danylenko.coursepet.service.GenericService;
import stanislav.danylenko.coursepet.service.impl.associative.CountryGraftService;
import stanislav.danylenko.coursepet.web.model.CountryWithGraftDto;
import stanislav.danylenko.coursepet.web.model.CountryWithGraftUpdateDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService implements GenericService<Country> {

    public static final String DEFAULT_COUNTRY = "Ukraine";

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryGraftService countryGraftService;
    @Autowired
    private GraftService graftService;

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
        for (CountryGraft countryGraft : countryGrafts) {
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

    public void fullUpdate(Country country, CountryWithGraftUpdateDto dto) {

        country.setName(dto.getCountry().getName());
        country.setDescription(dto.getCountry().getDescription());
        save(country);

        List<CountryGraft> countryGrafts =  country.getCountryGrafts();
        for(CountryGraft countryGraft : countryGrafts) {
            countryGraftService.delete(countryGraft.getId());
        }

        for (Long id : dto.getGraftIds()) {
            Graft graft = graftService.find(id);

            CountryGraft countryGraft = new CountryGraft();
            countryGraft.setGraft(graft);
            countryGraft.setCountry(country);

            countryGraftService.save(countryGraft);
        }
    }

    public Country getDefaultCountry() {
        return countryRepository.findByName(DEFAULT_COUNTRY);
    }

    public List<CountryWithGraftDto> getCountriesWithGrafts() {
        Iterable<Country> countries = findAll();
        List<CountryWithGraftDto> fullCountries = new ArrayList<>();
        for (Country country : countries) {
            CountryWithGraftDto fullCountry = new CountryWithGraftDto();
            fullCountry.setCountry(country);
            List<Graft> grafts = findGraftsByCountryId(country.getId());
            fullCountry.setGrafts(grafts);

            fullCountries.add(fullCountry);
        }
        return fullCountries;
    }

    public CountryWithGraftDto getCountryWithGrafts(Long id) {
        Country country = find(id);
        List<Graft> grafts = findGraftsByCountryId(id);
        return new CountryWithGraftDto(country, grafts);
    }
}

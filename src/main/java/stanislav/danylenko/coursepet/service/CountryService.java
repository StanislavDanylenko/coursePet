package stanislav.danylenko.coursepet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Country;
import stanislav.danylenko.coursepet.db.repository.CountryRepository;

@Service
public class CountryService implements GenericService<Country> {
    
    @Autowired
    private CountryRepository countryRepository;
    
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

    @Override
    public Iterable<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        countryRepository.deleteById(id);
    }
}

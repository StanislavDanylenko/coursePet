package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Disease;
import stanislav.danylenko.coursepet.db.repository.DiseaseRepository;
import stanislav.danylenko.coursepet.service.GenericService;

@Service
public class DiseaseService implements GenericService<Disease> {

    public static final String DEFAULT_DISEASE = "DefaultDISEASE";
    
    @Autowired
    private DiseaseRepository diseaseRepository;
    
    @Override
    public Disease save(Disease entity) {
        return diseaseRepository.save(entity);
    }

    @Override
    public void delete(Disease entity) {
        diseaseRepository.delete(entity);
    }

    @Override
    public Disease update(Disease entity) {
        return diseaseRepository.save(entity);
    }

    @Override
    public Disease find(Long id) {
        return diseaseRepository.getOne(id);
    }

    public Disease findByName(String name) {
        return diseaseRepository.findByName(name);
    }

    @Override
    public Iterable<Disease> findAll() {
        return diseaseRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        diseaseRepository.deleteById(id);
    }

    public Disease getDefaultDisease() {
        return findByName(DEFAULT_DISEASE);
    }
}

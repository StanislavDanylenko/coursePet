package stanislav.danylenko.coursepet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Disease;
import stanislav.danylenko.coursepet.db.repository.DiseaseRepository;

@Service
public class DiseaseService implements GenericService<Disease> {
    
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

    @Override
    public Iterable<Disease> findAll() {
        return diseaseRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        diseaseRepository.deleteById(id);
    }
}

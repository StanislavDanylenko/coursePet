package stanislav.danylenko.coursepet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalDiseasePK;
import stanislav.danylenko.coursepet.db.repository.associative.AnimalDiseaseRepository;

@Service
public class AnimalDiseaseService implements GenericService<AnimalDisease> {
    
    @Autowired
    private AnimalDiseaseRepository animalDiseaseRepository;


    @Override
    public AnimalDisease save(AnimalDisease entity) {
        return animalDiseaseRepository.save(entity);
    }

    @Override
    public void delete(AnimalDisease entity) {
        animalDiseaseRepository.delete(entity);
    }

    @Override
    public AnimalDisease update(AnimalDisease entity) {
        return animalDiseaseRepository.save(entity);
    }

    @Override
    public AnimalDisease find(Long id) {
        return null;
    }

    @Override
    public Iterable<AnimalDisease> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {
        System.err.println("Try to use incorrect method");
    }

    public void delete(AnimalDiseasePK id) {
       animalDiseaseRepository.deleteById(id);
    }
}

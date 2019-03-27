package stanislav.danylenko.coursepet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalGraftPK;
import stanislav.danylenko.coursepet.db.repository.associative.AnimalGraftRepository;

@Service
public class AnimalGraftService implements GenericService<AnimalGraft> {
    
    @Autowired
    private AnimalGraftRepository animalDiseaseRepository;


    @Override
    public AnimalGraft save(AnimalGraft entity) {
        return animalDiseaseRepository.save(entity);
    }

    @Override
    public void delete(AnimalGraft entity) {
        animalDiseaseRepository.delete(entity);
    }

    @Override
    public AnimalGraft update(AnimalGraft entity) {
        return animalDiseaseRepository.save(entity);
    }

    @Override
    public AnimalGraft find(Long id) {
        return null;
    }

    @Override
    public Iterable<AnimalGraft> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {
        System.err.println("Try to use incorrect method");
    }

    public void delete(AnimalGraftPK id) {
       animalDiseaseRepository.deleteById(id);
    }
}

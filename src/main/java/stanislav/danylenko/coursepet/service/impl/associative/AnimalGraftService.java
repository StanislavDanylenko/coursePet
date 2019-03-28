package stanislav.danylenko.coursepet.service.impl.associative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalGraftPK;
import stanislav.danylenko.coursepet.db.repository.associative.AnimalGraftRepository;
import stanislav.danylenko.coursepet.service.ComplexIdService;
import stanislav.danylenko.coursepet.service.GenericService;
import stanislav.danylenko.coursepet.service.SimpleIdService;

@Service
public class AnimalGraftService implements ComplexIdService<AnimalGraft, AnimalGraftPK> {
    
    @Autowired
    private AnimalGraftRepository animalDiseaseRepository;


    @Override
    public AnimalGraft save(AnimalGraft entity) {
        return animalDiseaseRepository.save(entity);
    }

    public void delete(AnimalGraft entity) {
        animalDiseaseRepository.delete(entity);
    }

    @Override
    public AnimalGraft update(AnimalGraft entity) {
        return animalDiseaseRepository.save(entity);
    }

    @Override
    public AnimalGraft find(AnimalGraftPK id) {
        return animalDiseaseRepository.getOne(id);
    }

    @Override
    public Iterable<AnimalGraft> findAll() {
        return animalDiseaseRepository.findAll();
    }

    @Override
    public void delete(AnimalGraftPK id) {
       animalDiseaseRepository.deleteById(id);
    }
}

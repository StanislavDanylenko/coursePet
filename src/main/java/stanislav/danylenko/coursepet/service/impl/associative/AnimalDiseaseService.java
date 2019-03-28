package stanislav.danylenko.coursepet.service.impl.associative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalDiseasePK;
import stanislav.danylenko.coursepet.db.repository.associative.AnimalDiseaseRepository;
import stanislav.danylenko.coursepet.service.ComplexIdService;

@Service
public class AnimalDiseaseService implements ComplexIdService<AnimalDisease, AnimalDiseasePK> {
    
    @Autowired
    private AnimalDiseaseRepository animalDiseaseRepository;


    public AnimalDisease save(AnimalDisease entity) {
        return animalDiseaseRepository.save(entity);
    }

    public void delete(AnimalDisease entity) {
        animalDiseaseRepository.delete(entity);
    }

    public AnimalDisease update(AnimalDisease entity) {
        return animalDiseaseRepository.save(entity);
    }

    public AnimalDisease find(AnimalDiseasePK id) {
        return animalDiseaseRepository.getOne(id);
    }

    public Iterable<AnimalDisease> findAll() {
        return animalDiseaseRepository.findAll();
    }

    public void delete(AnimalDiseasePK id) {
       animalDiseaseRepository.deleteById(id);
    }
}

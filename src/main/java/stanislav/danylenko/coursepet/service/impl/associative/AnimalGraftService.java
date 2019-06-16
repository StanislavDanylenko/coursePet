package stanislav.danylenko.coursepet.service.impl.associative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.entity.Graft;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.repository.associative.AnimalGraftRepository;
import stanislav.danylenko.coursepet.service.GenericService;
import stanislav.danylenko.coursepet.service.impl.AnimalService;
import stanislav.danylenko.coursepet.service.impl.GraftService;
import stanislav.danylenko.coursepet.web.model.associative.AnimalGraftDto;

import java.util.List;

@Service
public class AnimalGraftService implements GenericService<AnimalGraft> {
    
    @Autowired
    private AnimalGraftRepository animalDiseaseRepository;

    @Autowired
    private AnimalService animalService;

    @Autowired
    GraftService graftService;

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
    public AnimalGraft find(Long id) {
        return animalDiseaseRepository.getOne(id);
    }

    @Override
    public Iterable<AnimalGraft> findAll() {
        return animalDiseaseRepository.findAll();
    }

    @Override
    public void delete(Long id) {
       animalDiseaseRepository.deleteById(id);
    }

    public List<AnimalGraft> getDefaultAnimalGraft(Animal animal, Graft graft) {
        return animalDiseaseRepository.findByAnimalAndGraft(animal, graft);
    }

    public AnimalGraft prepareForSaving(AnimalGraftDto dto) {

        AnimalGraft animalGraft = new AnimalGraft();

        if(dto.getAnimalId() != null) {
            animalGraft.setAnimal(animalService.find(dto.getAnimalId()));
        }
        if(dto.getGraftId() != null) {
           animalGraft.setGraft(graftService.find(dto.getGraftId()));
        }
        if(dto.getDate() != null) {
            animalGraft.setDate(dto.getDate());
        }
        return animalGraft;
    }
}

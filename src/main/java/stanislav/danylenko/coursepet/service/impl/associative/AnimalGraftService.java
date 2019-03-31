package stanislav.danylenko.coursepet.service.impl.associative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalGraftPK;
import stanislav.danylenko.coursepet.db.repository.associative.AnimalGraftRepository;
import stanislav.danylenko.coursepet.service.ComplexIdService;
import stanislav.danylenko.coursepet.service.GenericService;
import stanislav.danylenko.coursepet.service.SimpleIdService;
import stanislav.danylenko.coursepet.service.impl.AnimalService;
import stanislav.danylenko.coursepet.service.impl.GraftService;
import stanislav.danylenko.coursepet.web.model.associative.AnimalGraftDto;

@Service
public class AnimalGraftService implements ComplexIdService<AnimalGraft, AnimalGraftPK> {
    
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

    public AnimalGraft prepareForSaving(AnimalGraftDto dto) {

        AnimalGraft animalGraft = new AnimalGraft();

        if(dto.getAnimalId() != null) {
            animalGraft.setAnimal(animalService.find(dto.getAnimalId()));
        }
        if(dto.getGraftId() != null) {
           animalGraft.setGraft(graftService.find(dto.getGraftId()));
        }
        if(dto.getLocalDateTime() != null) {
            animalGraft.setDate(dto.getLocalDateTime());
        }
        return animalGraft;
    }
}

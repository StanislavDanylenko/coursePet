package stanislav.danylenko.coursepet.service.impl.associative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalDiseasePK;
import stanislav.danylenko.coursepet.db.repository.associative.AnimalDiseaseRepository;
import stanislav.danylenko.coursepet.service.ComplexIdService;
import stanislav.danylenko.coursepet.service.impl.AnimalService;
import stanislav.danylenko.coursepet.service.impl.DiseaseService;
import stanislav.danylenko.coursepet.web.model.associative.AnimalDiseaseDto;

@Service
public class AnimalDiseaseService implements ComplexIdService<AnimalDisease, AnimalDiseasePK> {
    
    @Autowired
    private AnimalDiseaseRepository animalDiseaseRepository;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private DiseaseService diseaseService;


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

    public AnimalDisease prepareForSaving(AnimalDiseaseDto dto) {

        AnimalDisease animalDisease = new AnimalDisease();

        if(dto.getAnimalId() != null) {
            animalDisease.setAnimal(animalService.find(dto.getAnimalId()));
        }
        if(dto.getDiseaseId() != null) {
            animalDisease.setDisease(diseaseService.find(dto.getDiseaseId()));
        }

        animalDisease = prepareForUpdating(animalDisease, dto);

        return animalDisease;

    }

    public AnimalDisease prepareForUpdating(AnimalDisease animalDisease, AnimalDiseaseDto dto) {

        if(dto.getTreatment() != null) {
            animalDisease.setTreatment(dto.getTreatment());
        }
        if(dto.getStartData() != null) {
            animalDisease.setStartData(dto.getStartData());
        }
        if(dto.getEndDate() != null) {
            animalDisease.setEndDate(dto.getEndDate());
        }

        return animalDisease;

    }
}

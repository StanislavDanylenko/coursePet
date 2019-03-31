package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.AnimalsBreed;
import stanislav.danylenko.coursepet.db.entity.AnimalsClass;
import stanislav.danylenko.coursepet.db.repository.AnimalsBreedRepository;
import stanislav.danylenko.coursepet.service.GenericService;
import stanislav.danylenko.coursepet.service.SimpleIdService;
import stanislav.danylenko.coursepet.web.model.AnimalsBreedDto;

@Service
public class AnimalsBreedService implements SimpleIdService<AnimalsBreed> {
    
    @Autowired
    private AnimalsBreedRepository animalsBreedRepository;

    @Autowired
    private AnimalsClassService animalsClassService;
    
    @Override
    public AnimalsBreed save(AnimalsBreed entity) {
        return animalsBreedRepository.save(entity);
    }

    @Override
    public void delete(AnimalsBreed entity) {
        animalsBreedRepository.delete(entity);
    }

    @Override
    public AnimalsBreed update(AnimalsBreed entity) {
        return animalsBreedRepository.save(entity);
    }

    @Override
    public AnimalsBreed find(Long id) {
        return animalsBreedRepository.getOne(id);
    }

    @Override
    public Iterable<AnimalsBreed> findAll() {
        return animalsBreedRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        animalsBreedRepository.deleteById(id);
    }

    public AnimalsBreed prepareForUpdating(AnimalsBreed animalsBreed, AnimalsBreedDto dto) {
        if (dto.getName() != null) {
            animalsBreed.setName(dto.getName());
        }
        if(dto.getAnimalClassId() != null) {
            AnimalsClass animalsClass = animalsClassService.find(dto.getAnimalClassId());
            animalsBreed.setAnimalsClass(animalsClass);
        }
        return animalsBreed;
    }
}

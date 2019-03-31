package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.repository.AnimalRepository;
import stanislav.danylenko.coursepet.service.SimpleIdService;
import stanislav.danylenko.coursepet.web.model.AnimalCreateDto;
import stanislav.danylenko.coursepet.web.model.AnimalUpdateDto;

import java.time.LocalDateTime;

@Service
public class AnimalService implements SimpleIdService<Animal> {
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @Override
    public Animal save(Animal entity) {
        return animalRepository.save(entity);
    }

    @Override
    public void delete(Animal entity) {
        animalRepository.delete(entity);
    }

    @Override
    public Animal update(Animal entity) {
        return animalRepository.save(entity);
    }

    @Override
    public Animal find(Long id) {
        return animalRepository.getOne(id);
    }

    @Override
    public Iterable<Animal> findAll() {
        return animalRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        animalRepository.deleteById(id);
    }


    public Animal prepareForSaving(AnimalCreateDto dto) {

        Animal animal = new Animal();

        if(dto.getName() != null) {
            animal.setName(dto.getName());
        }

        if(dto.getGender() != null) {
            animal.setGender(dto.getGender());
        }

        if(dto.getBirthDate() != null) {
            animal.setBirthDate(dto.getBirthDate());
        }

        animal = prepareForUpdating(animal, dto);

        return animal;
    }

    public Animal prepareForUpdating(Animal animal, AnimalUpdateDto dto) {

        if(dto.getPhotoURL() != null) {
            animal.setPhotoURL(dto.getPhotoURL());
        }

        if(dto.getHeight() != null) {
            animal.setHeight(dto.getHeight());
        }

        if(dto.getLength() != null) {
            animal.setLength(dto.getLength());
        }

        if(dto.getWeight() != null) {
            animal.setWeight(dto.getWeight());
        }

        return animal;
    }

    private Animal prepareForUpdating(Animal animal, AnimalCreateDto dto) {

        if(dto.getPhotoURL() != null) {
            animal.setPhotoURL(dto.getPhotoURL());
        }

        if(dto.getHeight() != null) {
            animal.setHeight(dto.getHeight());
        }

        if(dto.getLength() != null) {
            animal.setLength(dto.getLength());
        }

        if(dto.getWeight() != null) {
            animal.setWeight(dto.getWeight());
        }

        return animal;
    }

}

package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.repository.AnimalRepository;
import stanislav.danylenko.coursepet.service.SimpleIdService;

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
}

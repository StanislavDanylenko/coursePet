package stanislav.danylenko.coursepet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.AnimalBreed;
import stanislav.danylenko.coursepet.db.repository.AnimalBreedRepository;

@Service
public class AnimalBreedService implements GenericService<AnimalBreed> {
    
    @Autowired
    private AnimalBreedRepository animalBreedRepository;
    
    @Override
    public AnimalBreed save(AnimalBreed entity) {
        return animalBreedRepository.save(entity);
    }

    @Override
    public void delete(AnimalBreed entity) {
        animalBreedRepository.delete(entity);
    }

    @Override
    public AnimalBreed update(AnimalBreed entity) {
        return animalBreedRepository.save(entity);
    }

    @Override
    public AnimalBreed find(Long id) {
        return animalBreedRepository.getOne(id);
    }

    @Override
    public Iterable<AnimalBreed> findAll() {
        return animalBreedRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        animalBreedRepository.deleteById(id);
    }
}

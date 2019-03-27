package stanislav.danylenko.coursepet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.AnimalClass;
import stanislav.danylenko.coursepet.db.repository.AnimalClassRepository;

@Service
public class AnimalClassService implements GenericService<AnimalClass> {

    @Autowired
    private AnimalClassRepository animalClassRepository;

    @Override
    public AnimalClass save(AnimalClass entity) {
        return animalClassRepository.save(entity);
    }

    @Override
    public void delete(AnimalClass entity) {
        animalClassRepository.delete(entity);
    }

    @Override
    public AnimalClass update(AnimalClass entity) {
        return animalClassRepository.save(entity);
    }

    @Override
    public AnimalClass find(Long id) {
        return animalClassRepository.getOne(id);
    }

    @Override
    public Iterable<AnimalClass> findAll() {
        return animalClassRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        animalClassRepository.deleteById(id);
    }
}

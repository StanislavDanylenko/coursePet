package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.AnimalsClass;
import stanislav.danylenko.coursepet.db.repository.AnimalsClassRepository;
import stanislav.danylenko.coursepet.service.SimpleIdService;

@Service
public class AnimalsClassService implements SimpleIdService<AnimalsClass> {

    @Autowired
    private AnimalsClassRepository animalsClassRepository;

    @Override
    public AnimalsClass save(AnimalsClass entity) {
        return animalsClassRepository.save(entity);
    }

    @Override
    public void delete(AnimalsClass entity) {
        animalsClassRepository.delete(entity);
    }

    @Override
    public AnimalsClass update(AnimalsClass entity) {
        return animalsClassRepository.save(entity);
    }

    @Override
    public AnimalsClass find(Long id) {
        return animalsClassRepository.getOne(id);
    }

    @Override
    public Iterable<AnimalsClass> findAll() {
        return animalsClassRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        animalsClassRepository.deleteById(id);
    }
}

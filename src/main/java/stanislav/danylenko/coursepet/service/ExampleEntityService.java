package stanislav.danylenko.coursepet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.ExampleEntity;
import stanislav.danylenko.coursepet.db.repository.ExampleEntityRepository;

@Service
public class ExampleEntityService implements GenericService<ExampleEntity> {
    
    @Autowired
    private ExampleEntityRepository exampleEntityRepository;
    
    @Override
    public ExampleEntity save(ExampleEntity entity) {
        return exampleEntityRepository.save(entity);
    }

    @Override
    public void delete(ExampleEntity entity) {
        exampleEntityRepository.delete(entity);
    }

    @Override
    public ExampleEntity update(ExampleEntity entity) {
        return exampleEntityRepository.save(entity);
    }

    @Override
    public ExampleEntity find(Long id) {
        return exampleEntityRepository.getOne(id);
    }

    @Override
    public Iterable<ExampleEntity> findAll() {
        return exampleEntityRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        exampleEntityRepository.deleteById(id);
    }
}

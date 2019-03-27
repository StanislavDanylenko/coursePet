package stanislav.danylenko.coursepet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Graft;
import stanislav.danylenko.coursepet.db.repository.GraftRepository;

@Service
public class GraftService implements GenericService<Graft> {
    
    @Autowired
    private GraftRepository graftRepository;
    
    @Override
    public Graft save(Graft entity) {
        return graftRepository.save(entity);
    }

    @Override
    public void delete(Graft entity) {
        graftRepository.delete(entity);
    }

    @Override
    public Graft update(Graft entity) {
        return graftRepository.save(entity);
    }

    @Override
    public Graft find(Long id) {
        return graftRepository.getOne(id);
    }

    @Override
    public Iterable<Graft> findAll() {
        return graftRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        graftRepository.deleteById(id);
    }
}

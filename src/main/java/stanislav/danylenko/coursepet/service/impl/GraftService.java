package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Graft;
import stanislav.danylenko.coursepet.db.repository.GraftRepository;
import stanislav.danylenko.coursepet.service.GenericService;

@Service
public class GraftService implements GenericService<Graft> {

    public static String DEFAULT_GRAFT = "DefaultGRAFT";
    
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

    public Graft findByName(String name) {
        return graftRepository.findByName(name);
    }

    @Override
    public Iterable<Graft> findAll() {
        return graftRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        graftRepository.deleteById(id);
    }

    public Graft prepareForUpdating(Graft graft, Graft dto) {
        if(dto.getName() != null) {
            graft.setName(dto.getName());
        }
        if(dto.getFrequency() != null) {
            graft.setFrequency(dto.getFrequency());
        }
        return graft;
    }

    public Graft getDefaultGraft(){
        return findByName(DEFAULT_GRAFT);
    }
}

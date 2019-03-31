package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Record;
import stanislav.danylenko.coursepet.db.repository.RecordRepository;
import stanislav.danylenko.coursepet.service.SimpleIdService;

@Service
public class RecordService implements SimpleIdService<Record> {
    
    @Autowired
    private RecordRepository recordRepository;
    
    @Override
    public Record save(Record entity) {
        return recordRepository.save(entity);
    }

    @Override
    public void delete(Record entity) {
        recordRepository.delete(entity);
    }

    @Override
    public Record update(Record entity) {
        return recordRepository.save(entity);
    }

    @Override
    public Record find(Long id) {
        return recordRepository.getOne(id);
    }

    @Override
    public Iterable<Record> findAll() {
        return recordRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        recordRepository.deleteById(id);
    }
}

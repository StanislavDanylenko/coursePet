package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Record;
import stanislav.danylenko.coursepet.db.entity.SmartDevice;
import stanislav.danylenko.coursepet.db.repository.RecordRepository;
import stanislav.danylenko.coursepet.service.GenericService;
import stanislav.danylenko.coursepet.web.model.RecordDto;

import java.time.LocalDateTime;

@Service
public class RecordService implements GenericService<Record> {
    
    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private SmartDeviceService smartDeviceService;
    
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

    public Record prepareForSaving(RecordDto dto) {

        Record record = new Record();

        if(dto.getSmartDeviceId() != null) {
            SmartDevice smartDevice = smartDeviceService.find(dto.getSmartDeviceId());
            record.setSmartDevice(smartDevice);
        }

        if(dto.getAnimalState() != null) {
            record.setAnimalState(dto.getAnimalState());
        }

        if(dto.getLatitude() != null) {
            record.setLatitude(dto.getLatitude());
        }

        if(dto.getLongitude() != null) {
            record.setLongitude(dto.getLongitude());
        }

        if(dto.getPulse() != null) {
            record.setPulse(dto.getPulse());
        }

        if(dto.getTemperature() != null) {
            record.setTemperature(dto.getTemperature());
        }

        record.setCreationDate(LocalDateTime.now());

        return record;
    }

    public Iterable<Record> getRecordsBySmartDeviceId(Long id) {
        return recordRepository.findBySmartDeviceId(id);
    }

    public Record getLastRecordByDeviceId(Long id) {
        return recordRepository.findFirstBySmartDeviceIdOrderByCreationDateDesc(id);
    }
}

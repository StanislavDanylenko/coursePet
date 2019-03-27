package stanislav.danylenko.coursepet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.SmartDevice;
import stanislav.danylenko.coursepet.db.repository.SmartDeviceRepository;

@Service
public class SmartDeviceService implements GenericService<SmartDevice> {
    
    @Autowired
    private SmartDeviceRepository smartDeviceRepository;
    
    @Override
    public SmartDevice save(SmartDevice entity) {
        return smartDeviceRepository.save(entity);
    }

    @Override
    public void delete(SmartDevice entity) {
        smartDeviceRepository.delete(entity);
    }

    @Override
    public SmartDevice update(SmartDevice entity) {
        return smartDeviceRepository.save(entity);
    }

    @Override
    public SmartDevice find(Long id) {
        return smartDeviceRepository.getOne(id);
    }

    @Override
    public Iterable<SmartDevice> findAll() {
        return smartDeviceRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        smartDeviceRepository.deleteById(id);
    }
}

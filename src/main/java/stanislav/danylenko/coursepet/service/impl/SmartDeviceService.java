package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.entity.SmartDevice;
import stanislav.danylenko.coursepet.db.enumeration.AnimalState;
import stanislav.danylenko.coursepet.db.repository.SmartDeviceRepository;
import stanislav.danylenko.coursepet.service.GenericService;
import stanislav.danylenko.coursepet.service.SimpleIdService;
import stanislav.danylenko.coursepet.web.model.SmartDeviceDto;

@Service
public class SmartDeviceService implements SimpleIdService<SmartDevice> {
    
    @Autowired
    private SmartDeviceRepository smartDeviceRepository;

    @Autowired
    private AnimalService animalService;

    
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


    public Iterable<SmartDevice> getSmartDevicesByAnimalId(Long id) {
        return smartDeviceRepository.findByAnimalId(id);
    }


    public SmartDevice prepareForSaving(SmartDeviceDto dto) {

        SmartDevice smartDevice = new SmartDevice();

        if(dto.getMac() != null) {
            smartDevice.setMac(dto.getMac());
        }

        if(dto.getName() != null) {
            smartDevice.setName(dto.getName());
        }

        smartDevice = prepareForUpdating(smartDevice, dto);

        return smartDevice;
    }

    public SmartDevice prepareForUpdating(SmartDevice smartDevice, SmartDeviceDto dto) {

        if(dto.getAnimalId() != null) {
            Animal animal = animalService.find(dto.getAnimalId());
            smartDevice.setAnimal(animal);
        }

        if(dto.getBatteryLevel() != null) {
            smartDevice.setBatteryLevel(dto.getBatteryLevel());
        }

        return smartDevice;
    }
}

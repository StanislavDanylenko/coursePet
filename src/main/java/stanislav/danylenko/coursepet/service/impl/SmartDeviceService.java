package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.entity.SmartDevice;
import stanislav.danylenko.coursepet.db.entity.User;
import stanislav.danylenko.coursepet.db.repository.SmartDeviceRepository;
import stanislav.danylenko.coursepet.exception.LowBatteryLevelException;
import stanislav.danylenko.coursepet.service.GenericService;
import stanislav.danylenko.coursepet.web.model.SmartDeviceDto;

@Service
public class SmartDeviceService implements GenericService<SmartDevice> {

    public static String DEFAULT_SMART_DEVICE = "DefaultSmartDevice";
    
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

    public SmartDevice findByMac(String mac) {
        return smartDeviceRepository.findByMac(mac);
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
        smartDevice.setIsActive(false);

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

    public SmartDevice getDefaultSmartDevice(){
        return findByMac(DEFAULT_SMART_DEVICE);
    }

    public boolean enableSmartDevice(Long id) throws LowBatteryLevelException {
        SmartDevice device = find(id);
        if (device == null) {
            return false;
        }
        if (device.getBatteryLevel() <= 5.0) {
            throw new LowBatteryLevelException("Low battery level exception");
        }
        Animal animal = device.getAnimal();
        SmartDevice oldActiveDevice = smartDeviceRepository.findByAnimalIdAndIsActiveTrue(animal.getId());
        if (oldActiveDevice != null) {
            oldActiveDevice.setIsActive(false);
            save(oldActiveDevice);
        }
        device.setIsActive(true);
        save(device);
        return true;
    }

    public boolean disableSmartDevice(Long id) {
        SmartDevice device = find(id);
        if (device == null) {
            return false;
        }
        device.setIsActive(false);
        save(device);
        return true;
    }

    public void chargeSmartDevice(Long id) {
        SmartDevice smartDevice = find(id);
        smartDevice.setBatteryLevel(100.0);
        update(smartDevice);
    }

}

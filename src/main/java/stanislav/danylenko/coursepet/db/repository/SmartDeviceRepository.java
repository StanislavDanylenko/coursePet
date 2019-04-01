package stanislav.danylenko.coursepet.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.SmartDevice;

@Repository
public interface SmartDeviceRepository extends JpaRepository<SmartDevice, Long> {

    Iterable<SmartDevice> findByAnimalId(Long animalId);
    SmartDevice findByMac(String mac);

}

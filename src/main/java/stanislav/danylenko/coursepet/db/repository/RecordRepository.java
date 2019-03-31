package stanislav.danylenko.coursepet.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.Record;
import stanislav.danylenko.coursepet.db.enumeration.Localization;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    Iterable<Record> findBySmartDeviceId(Long smartDeviceId);
    Record findBySmartDeviceIdOrderByCreationDateDesc (Long smartDeviceId);

}

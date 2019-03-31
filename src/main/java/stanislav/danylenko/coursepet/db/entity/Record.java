package stanislav.danylenko.coursepet.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.enumeration.AnimalState;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double temperature;
    private Double pulse;
    private Double longitude;
    private Double latitude;

    private AnimalState animalState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smart_device_id")
    private SmartDevice smartDevice;

}

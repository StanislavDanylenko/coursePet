package stanislav.danylenko.coursepet.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.enumeration.AnimalState;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDto implements Serializable {

    private Double temperature;
    private Double pulse;
    private Double longitude;
    private Double latitude;

    private AnimalState animalState;

    private Long smartDeviceId;
}

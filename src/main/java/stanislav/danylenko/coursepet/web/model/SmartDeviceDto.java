package stanislav.danylenko.coursepet.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmartDeviceDto implements Serializable {

    private String mac;
    private String name;
    private Long animalId;
    private Double batteryLevel;

}

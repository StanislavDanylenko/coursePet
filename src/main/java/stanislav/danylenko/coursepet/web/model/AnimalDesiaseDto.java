package stanislav.danylenko.coursepet.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDesiaseDto implements Serializable {

    private Long animalId;
    private Long diseaseId;

    private LocalDateTime startData;
    private LocalDateTime endDate;
    private String treatment;

}

package stanislav.danylenko.coursepet.web.model.associative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDiseaseDto implements Serializable {

    private Long animalId;
    private Long diseaseId;

    private LocalDateTime startData;
    private LocalDateTime endDate;
    private String treatment;

}

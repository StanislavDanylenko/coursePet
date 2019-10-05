package stanislav.danylenko.coursepet.web.model.associative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDiseaseDto implements Serializable {

    private Long id;

    private Long animalId;
    private Long diseaseId;

    private Date startData;
    private Date endDate;
    private String treatment;

}

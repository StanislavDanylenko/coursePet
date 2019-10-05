package stanislav.danylenko.coursepet.web.model.epidemic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.entity.AnimalsBreed;
import stanislav.danylenko.coursepet.db.entity.Country;
import stanislav.danylenko.coursepet.db.entity.Disease;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpidemicStatistic implements Serializable {

    private String country;
    private String breed;
    private String disease;
    private Long count;
    private Double percentage;

}

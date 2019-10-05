package stanislav.danylenko.coursepet.web.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountByBreedInCountryStatisticDto implements Serializable {

    private String breed;
    private Long count;

}

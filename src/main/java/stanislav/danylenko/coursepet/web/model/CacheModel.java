package stanislav.danylenko.coursepet.web.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.entity.*;
import stanislav.danylenko.coursepet.web.model.statistic.CountByBreedInCountryStatisticDto;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheModel implements Serializable {

    private User user;
    private List<Animal> animals;
    private List<CountryWithGraftDto> countries;
    private Iterable<AnimalsBreed> breeds;
    private Iterable<Disease> diseases;
    private Iterable<Graft> grafts;
    private List<CountByBreedInCountryStatisticDto> statistic;

}

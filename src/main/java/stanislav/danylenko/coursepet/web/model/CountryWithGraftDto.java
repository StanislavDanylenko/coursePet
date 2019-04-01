package stanislav.danylenko.coursepet.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.entity.Country;
import stanislav.danylenko.coursepet.db.entity.Graft;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryWithGraftDto implements Serializable {

    private Country country;
    private List<Graft> grafts;

}

package stanislav.danylenko.coursepet.web.model.associative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryGraftDto implements Serializable {

    private Long id;

    private Long countryId;
    private Long graftId;


}

package stanislav.danylenko.coursepet.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryGraftDto implements Serializable {

    private Long countryId;
    private Long graftId;

}

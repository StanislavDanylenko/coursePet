package stanislav.danylenko.coursepet.web.model.associative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryGraftDto implements Serializable {

    private Long countryId;
    private Long graftId;


}

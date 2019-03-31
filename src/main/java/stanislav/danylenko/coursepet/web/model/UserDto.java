package stanislav.danylenko.coursepet.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.enumeration.Localization;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    private Localization localization;
    private Long countryId;

}

package stanislav.danylenko.coursepet.web.model.associative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalGraftDto implements Serializable {

    private Long animalId;
    private Long graftId;
    private LocalDateTime localDateTime;

}

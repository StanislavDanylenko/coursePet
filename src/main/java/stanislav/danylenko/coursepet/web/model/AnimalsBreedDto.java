package stanislav.danylenko.coursepet.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalsBreedDto implements Serializable {

    private String name;
    private Long animalClassId;

}

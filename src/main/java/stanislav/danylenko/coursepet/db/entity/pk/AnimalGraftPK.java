package stanislav.danylenko.coursepet.db.entity.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalGraftPK implements Serializable {

    private Long animal;
    private Long graft;

}

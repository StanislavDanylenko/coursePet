package stanislav.danylenko.coursepet.db.entity.associative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.entity.Graft;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalGraftPK;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@IdClass(AnimalGraftPK.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalGraft implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Id
    @ManyToOne
    @JoinColumn(name = "graft_id")
    private Graft graft;

    private LocalDateTime date;

}

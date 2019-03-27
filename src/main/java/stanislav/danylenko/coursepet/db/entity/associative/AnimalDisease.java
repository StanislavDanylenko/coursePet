package stanislav.danylenko.coursepet.db.entity.associative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.entity.Disease;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalDiseasePK;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@IdClass(AnimalDiseasePK.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDisease implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Id
    @ManyToOne
    @JoinColumn(name = "disease_id")
    private Disease disease;

    private LocalDateTime startData;
    private LocalDateTime endDate;
    private String treatment;

}

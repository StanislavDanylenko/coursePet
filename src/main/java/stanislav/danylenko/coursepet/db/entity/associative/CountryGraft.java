package stanislav.danylenko.coursepet.db.entity.associative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.entity.Country;
import stanislav.danylenko.coursepet.db.entity.Graft;
import stanislav.danylenko.coursepet.db.entity.pk.CountryGraftPK;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(CountryGraftPK.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryGraft implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Country country;

    @Id
    @ManyToOne
    @JoinColumn(name = "graft_id")
    private Graft graft;

}
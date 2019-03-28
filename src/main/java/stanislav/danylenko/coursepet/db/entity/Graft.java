package stanislav.danylenko.coursepet.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graft implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Double frequency;

    @OneToMany(mappedBy = "graft", cascade = CascadeType.ALL)
    private List<AnimalGraft> animalGrafts;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<CountryGraft> countryGrafts;


}

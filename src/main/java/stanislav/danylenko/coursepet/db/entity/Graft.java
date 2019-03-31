package stanislav.danylenko.coursepet.db.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;
import stanislav.danylenko.coursepet.web.JsonRules;

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
    @JsonView(value = {JsonRules.AnimalGraft.class, JsonRules.CountryGraft.class})
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double frequency;

    @OneToMany(mappedBy = "graft", cascade = CascadeType.ALL)
    private List<AnimalGraft> animalGrafts;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<CountryGraft> countryGrafts;


}

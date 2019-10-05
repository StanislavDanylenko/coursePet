package stanislav.danylenko.coursepet.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graft implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = {JsonRules.AnimalGraft.class, JsonRules.CountryGraft.class})
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double frequency;

    @JsonIgnore
    @OneToMany(mappedBy = "graft", cascade = CascadeType.ALL)
    private List<AnimalGraft> animalGrafts;

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private List<CountryGraft> countryGrafts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graft graft = (Graft) o;
        return id.equals(graft.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

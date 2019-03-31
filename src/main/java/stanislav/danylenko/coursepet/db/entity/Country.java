package stanislav.danylenko.coursepet.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<CountryGraft> countryGrafts;
    
}

package stanislav.danylenko.coursepet.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.enumeration.Gender;
import stanislav.danylenko.coursepet.service.GenericService;
import stanislav.danylenko.coursepet.web.JsonRules;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Animal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(value = {JsonRules.AnimalDisease.class, JsonRules.AnimalGraft.class})
    private Long id;

    @Column(nullable = false)
    private String name;
    private String photoURL;

    @Column(nullable = false)
    private Gender gender;
    @Column(nullable = false)
    private LocalDateTime birthDate;

    private Double weight;
    private Double height;
    private Double length;

    @Column(unique = true)
    private String smartCardId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animalBreed_id")
    private AnimalsBreed animalsBreed;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    private List<SmartDevice> smartDevices = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    private List<AnimalGraft> animalGrafts;

    @JsonIgnore
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    private List<AnimalDisease> animalDiseases;
}

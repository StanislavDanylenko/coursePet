package stanislav.danylenko.coursepet.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;

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
    private Long id;

    @Column(nullable = false)
    private String name;
    private String photoURL;

    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private LocalDateTime birthDate;

    private Double weight;
    private Double height;
    private Double length;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animalBreed_id")
    private AnimalsBreed animalsBreed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    private List<SmartDevice> smartDevices = new ArrayList<>();



    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    private List<AnimalGraft> animalGrafts;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    private List<AnimalDisease> animalDiseases;
}

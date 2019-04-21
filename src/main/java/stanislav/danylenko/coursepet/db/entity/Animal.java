package stanislav.danylenko.coursepet.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.enumeration.Gender;
import stanislav.danylenko.coursepet.service.GenericService;
import stanislav.danylenko.coursepet.web.JsonRules;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Animal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = {JsonRules.AnimalDisease.class, JsonRules.AnimalGraft.class})
    private Long id;

    @Column(nullable = false)
    private String name;
    private String photoURL;

    @Column(nullable = false)
    private Gender gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date birthDate;

    private Double weight;
    private Double height;
    private Double length;

    @Column(unique = true)
    private String smartCardId;


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

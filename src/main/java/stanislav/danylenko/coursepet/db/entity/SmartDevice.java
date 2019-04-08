package stanislav.danylenko.coursepet.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmartDevice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String mac;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isActive;

    private Double batteryLevel = 0.0;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @OneToMany(mappedBy = "smartDevice", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

}

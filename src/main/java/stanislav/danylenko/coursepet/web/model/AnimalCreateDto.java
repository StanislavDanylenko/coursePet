package stanislav.danylenko.coursepet.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.enumeration.Gender;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCreateDto implements Serializable {

    private String name;
    private String photoURL;

    private Gender gender;
    private Date birthDate;

    private Double weight;
    private Double height;
    private Double length;

    private Long animalsBreedId;

    private Long userId;

}

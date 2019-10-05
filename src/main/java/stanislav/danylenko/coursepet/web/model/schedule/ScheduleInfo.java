package stanislav.danylenko.coursepet.web.model.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleInfo implements Serializable {

    private String name;
    private Double frequency;
    private String lastDate;
    private String nextDate;
    private Boolean isNew;

}

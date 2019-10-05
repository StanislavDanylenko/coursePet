package stanislav.danylenko.coursepet.web.model.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.enumeration.Gender;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleGraftDto implements Serializable {

    // name / frequency / lastTime / nextTime

    private List<ScheduleInfo> criticalGrafts;
    private List<ScheduleInfo> nextGrafts;
    private List<ScheduleInfo> presentGrafts;

}

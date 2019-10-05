package stanislav.danylenko.coursepet.web.model.schedule;

import stanislav.danylenko.coursepet.db.entity.Graft;

import java.util.Date;

public class AnimalGraftStatistics {

    private Graft Graft;
    private Date maxDate;

    public AnimalGraftStatistics(stanislav.danylenko.coursepet.db.entity.Graft graft, Date maxDate) {
        Graft = graft;
        this.maxDate = maxDate;
    }

    public stanislav.danylenko.coursepet.db.entity.Graft getGraft() {
        return Graft;
    }

    public void setGraft(stanislav.danylenko.coursepet.db.entity.Graft graft) {
        Graft = graft;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }
}

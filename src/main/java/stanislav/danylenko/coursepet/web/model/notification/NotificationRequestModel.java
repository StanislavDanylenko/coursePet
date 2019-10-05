package stanislav.danylenko.coursepet.web.model.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestModel implements Serializable {

    private Notification notification;
    private String to;
    private String priority;

}

package stanislav.danylenko.coursepet.web.model.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationModel implements Serializable {

    private String theme;
    private String message;

}

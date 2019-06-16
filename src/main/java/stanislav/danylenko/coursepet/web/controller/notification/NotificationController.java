package stanislav.danylenko.coursepet.web.controller.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.service.impl.notification.NotificationService;
import stanislav.danylenko.coursepet.web.model.notification.Notification;
import stanislav.danylenko.coursepet.web.model.notification.NotificationModel;
import stanislav.danylenko.coursepet.web.model.notification.NotificationRequestModel;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationController {

    private static final String TOPIC = "JavaSampleApproach";

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public @ResponseBody
    ResponseEntity<String> send(@RequestBody NotificationModel notificationModel) throws JsonProcessingException {

        NotificationRequestModel requestModel = new NotificationRequestModel();

        requestModel.setNotification(new Notification(
                notificationModel.getTheme(),
                notificationModel.getMessage()
        ));
        requestModel.setPriority("high");
        requestModel.setTo("/topics/" + TOPIC);

        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(requestModel);

        HttpEntity<String> request = new HttpEntity<>(message);

        CompletableFuture<String> pushNotification = notificationService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Notification error!", e);
        }
        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }

}

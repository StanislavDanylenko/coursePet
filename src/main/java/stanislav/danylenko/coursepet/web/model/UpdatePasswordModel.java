package stanislav.danylenko.coursepet.web.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordModel {

    private Long id;
    private String oldPassword;
    private String newPassword;

}

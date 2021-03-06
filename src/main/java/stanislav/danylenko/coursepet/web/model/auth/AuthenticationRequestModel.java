package stanislav.danylenko.coursepet.web.model.auth;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestModel implements Serializable {

    @NonNull
    private String username;
    @NonNull
    private String password;

}

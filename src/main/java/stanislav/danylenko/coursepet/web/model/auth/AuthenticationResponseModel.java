package stanislav.danylenko.coursepet.web.model.auth;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseModel implements Serializable {

    @NonNull
    private Long id;
    @NonNull
    private String token;

}

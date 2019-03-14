package stanislav.danylenko.coursepet.web.model;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRersponseModel implements Serializable {

    @NonNull
    private Long id;
    @NonNull
    private String token;

}

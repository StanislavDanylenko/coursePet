package stanislav.danylenko.coursepet.web.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {

    private String username;
    private List<String> roles;

}

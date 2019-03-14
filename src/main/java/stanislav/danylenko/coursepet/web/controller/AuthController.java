package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanislav.danylenko.coursepet.config.security.jwt.JwtTokenProvider;
import stanislav.danylenko.coursepet.db.entity.User;
import stanislav.danylenko.coursepet.db.repository.UserRepository;
import stanislav.danylenko.coursepet.web.model.AuthenticationRequestModel;
import stanislav.danylenko.coursepet.web.model.AuthenticationRersponseModel;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequestModel data) {

        try {
            String username = data.getUsername();
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
            Long id = user.getId();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            AuthenticationRersponseModel rersponseModel = new AuthenticationRersponseModel(id, token);

            return ok(rersponseModel);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}

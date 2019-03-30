package stanislav.danylenko.coursepet.web.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.config.security.jwt.JwtTokenProvider;
import stanislav.danylenko.coursepet.db.entity.User;
import stanislav.danylenko.coursepet.db.repository.UserRepository;
import stanislav.danylenko.coursepet.exception.UserRegistrationException;
import stanislav.danylenko.coursepet.service.impl.UserService;
import stanislav.danylenko.coursepet.web.model.auth.AuthenticationRequestModel;
import stanislav.danylenko.coursepet.web.model.auth.AuthenticationResponseModel;
import stanislav.danylenko.coursepet.web.model.auth.RegistrationRequestModel;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;
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

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequestModel data) {

        try {
            String username = data.getUsername();
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
            Long id = user.getId();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            AuthenticationResponseModel rersponseModel = new AuthenticationResponseModel(id, token);

            return ok(rersponseModel);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegistrationRequestModel data) throws UserRegistrationException {

        UserDetails userDetails = userService.loadUserByUsername(data.getUsername());
        if (userDetails != null) {
            throw new UserRegistrationException("User with this credentials already exists");
        }
        if(!data.getPassword().equals(data.getRepeatPassword())) {
            throw new UserRegistrationException("Passwords bust be the same");
        }

        User user = new User();
        user.setUsername(data.getUsername());
        user.setPassword(data.getPassword());

        userService.save(user);

        return ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
                .stream()
                .map(a -> ((GrantedAuthority) a).getAuthority())
                .collect(toList())
        );
        return ok(model);
    }
}

package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.User;
import stanislav.danylenko.coursepet.db.enumeration.Localization;
import stanislav.danylenko.coursepet.db.enumeration.Role;
import stanislav.danylenko.coursepet.exception.UserRegistrationException;
import stanislav.danylenko.coursepet.service.impl.CountryService;
import stanislav.danylenko.coursepet.service.impl.UserService;
import stanislav.danylenko.coursepet.web.model.UpdatePasswordModel;
import stanislav.danylenko.coursepet.web.model.UserDto;
import stanislav.danylenko.coursepet.web.model.auth.RegistrationRequestModel;
import stanislav.danylenko.coursepet.web.model.auth.UserDetailsDto;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CountryService countryService;

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<User>> getUsers() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addAdmin(@RequestBody RegistrationRequestModel data) throws UserRegistrationException {

        UserDetails userDetails;

        try {
            userDetails = service.loadUserByUsername(data.getUsername());
        } catch (UsernameNotFoundException e) {
            if (!data.getPassword().equals(data.getRepeatPassword())) {
                throw new UserRegistrationException("Passwords bust be the same");
            }

            User user = new User();

            user.setUsername(data.getUsername());
            user.setPassword(passwordEncoder.encode(data.getPassword()));
            user.setCountry(countryService.getDefaultCountry());
            user.setLocalization(Localization.ENGLISH);

            List<Role> roles = new ArrayList<>();
            roles.add(Role.ADMIN);
            user.setRoles(roles);

            service.save(user);
            return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
        }
        throw new UserRegistrationException("User with this credentials already exists");
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<User> updateUser(@RequestBody UserDto newUser, @PathVariable Long id) {
        User user = service.find(id);
        service.prepareUserForUpdate(user, newUser);
        service.update(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }



    ////////////////

    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserDetailsDto dto = new UserDetailsDto();

        dto.setUsername(userDetails.getUsername());
        List<String> roles =  userDetails.getAuthorities()
                                     .stream()
                                     .map(a -> a.getAuthority())
                                     .collect(toList());
        dto.setRoles(roles);
        return ok(dto);
    }

    ///////////

    @PostMapping("/password")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public @ResponseBody
    ResponseEntity<User> updatePassword(@RequestBody UpdatePasswordModel userModel) {
        User user = service.find(userModel.getId());
        boolean isChanged = service.changePassword(userModel);
        if (isChanged) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(user, HttpStatus.CONFLICT);
    }
    
}

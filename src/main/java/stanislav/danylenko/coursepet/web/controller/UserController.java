package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.User;
import stanislav.danylenko.coursepet.service.impl.UserService;
import stanislav.danylenko.coursepet.web.model.UserDto;
import stanislav.danylenko.coursepet.web.model.auth.UserDetailsDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

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
    
}

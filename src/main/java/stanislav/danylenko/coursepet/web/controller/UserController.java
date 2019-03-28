package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.User;
import stanislav.danylenko.coursepet.service.impl.UserService;

import javax.servlet.http.HttpServletResponse;

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

    @PostMapping
    public @ResponseBody
    ResponseEntity<User> createUser(@RequestBody User user) {
        service.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<User> updateUser(@RequestBody User newAnimal, @PathVariable Long id) {
        User user = service.find(id);
        service.update(newAnimal);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

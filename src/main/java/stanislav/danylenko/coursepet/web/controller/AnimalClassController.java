package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.AnimalClass;
import stanislav.danylenko.coursepet.service.AnimalClassService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/animalClass")
public class AnimalClassController {

    @Autowired
    private AnimalClassService service;

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<AnimalClass>> getAnimalClass() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<AnimalClass> getAnimalClass(@PathVariable Long id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<AnimalClass> createAnimalClass(@RequestBody AnimalClass animalClass, @AuthenticationPrincipal UserDetails userDetails) {
        userDetails.getAuthorities();
        service.save(animalClass);
        return new ResponseEntity<>(animalClass, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<AnimalClass> updateAnimalClass(@RequestBody AnimalClass newAnimalClass, @PathVariable Long id) {
        AnimalClass animalClass = service.find(id);
        service.update(animalClass);
        return ResponseEntity.ok(animalClass);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimalClass(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

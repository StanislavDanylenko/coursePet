package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.service.impl.AnimalService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService service;

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<Animal>> getAnimals() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Animal> getAnimal(@PathVariable Long id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<Animal> createAnimal(@RequestBody Animal animal) {
        service.save(animal);
        return new ResponseEntity<>(animal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Animal> updateAnimal(@RequestBody Animal newAnimal, @PathVariable Long id) {
        Animal animal = service.find(id);
        service.update(newAnimal);
        return ResponseEntity.ok(animal);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

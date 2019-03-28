package stanislav.danylenko.coursepet.web.controller.associative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalGraftPK;
import stanislav.danylenko.coursepet.service.impl.associative.AnimalGraftService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/animalGraft")
public class AnimalGraftController {

    @Autowired
    private AnimalGraftService service;

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<AnimalGraft>> getAnimalsGrafts() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<AnimalGraft> getAnimalGraft(@PathVariable AnimalGraftPK id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<AnimalGraft> createAnimalGraft(@RequestBody AnimalGraft animalGraft) {
        service.save(animalGraft);
        return new ResponseEntity<>(animalGraft, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<AnimalGraft> updateAnimalGraft(@RequestBody AnimalGraft newAnimal, @PathVariable AnimalGraftPK id) {
        AnimalGraft animalGraft = service.find(id);
        service.update(newAnimal);
        return ResponseEntity.ok(animalGraft);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimalGraft(@PathVariable AnimalGraftPK id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

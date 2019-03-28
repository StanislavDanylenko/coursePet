package stanislav.danylenko.coursepet.web.controller.associative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalDiseasePK;
import stanislav.danylenko.coursepet.service.impl.associative.AnimalDiseaseService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/animalDisease")
public class AnimalDiseaseController {

    @Autowired
    private AnimalDiseaseService service;

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<AnimalDisease>> getAnimalDiseases() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<AnimalDisease> getAnimalDisease(@PathVariable AnimalDiseasePK id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<AnimalDisease> createAnimalDisease(@RequestBody AnimalDisease animalDisease) {
        service.save(animalDisease);
        return new ResponseEntity<>(animalDisease, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<AnimalDisease> updateAnimalDisease(@RequestBody AnimalDisease newAnimal, @PathVariable AnimalDiseasePK id) {
        AnimalDisease animalDisease = service.find(id);
        service.update(newAnimal);
        return ResponseEntity.ok(animalDisease);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimalDisease(@PathVariable AnimalDiseasePK id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

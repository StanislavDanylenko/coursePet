package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.AnimalsBreed;
import stanislav.danylenko.coursepet.service.impl.AnimalsBreedService;
import stanislav.danylenko.coursepet.web.model.AnimalsBreedDto;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/animalBreed")
public class AnimalsBreedController {

    @Autowired
    private AnimalsBreedService service;

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<AnimalsBreed>> getAnimalBreeds() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<AnimalsBreed> getAnimalBreed(@PathVariable Long id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<AnimalsBreed> createAnimalBreed(@RequestBody AnimalsBreedDto dto) {
        AnimalsBreed animalsBreed = new AnimalsBreed();
        service.prepareForUpdating(animalsBreed, dto);
        service.save(animalsBreed);
        return new ResponseEntity<>(animalsBreed, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<AnimalsBreed> updateAnimalBreed(@RequestBody AnimalsBreedDto dto, @PathVariable Long id) {
        AnimalsBreed animalsBreed = service.find(id);
        service.prepareForUpdating(animalsBreed, dto);
        service.update(animalsBreed);
        return ResponseEntity.ok(animalsBreed);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimalBreed(@PathVariable Long id, HttpServletResponse response) {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}



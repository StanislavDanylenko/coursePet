package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.service.impl.AnimalService;
import stanislav.danylenko.coursepet.web.model.AnimalCreateDto;
import stanislav.danylenko.coursepet.web.model.AnimalFullInfoDto;
import stanislav.danylenko.coursepet.web.model.AnimalUpdateDto;
import stanislav.danylenko.coursepet.web.model.IsAvailableCountry;

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

    @GetMapping("/full/{id}")
    public @ResponseBody
    ResponseEntity<AnimalFullInfoDto> getAnimalFullInfo(@PathVariable Long id) {
        return new ResponseEntity<>(service.getAnimalFullInfo(id), HttpStatus.OK);
    }

    @GetMapping("/country/{countryId}/animal/{animalId}")
    public @ResponseBody
    ResponseEntity<IsAvailableCountry> getAnimalIsAvailableCountryfo(@PathVariable Long countryId, @PathVariable Long animalId) {
        return new ResponseEntity<>(service.checkIsAvailableCountry(animalId, countryId), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<Animal> createAnimal(@RequestBody AnimalCreateDto dto) {
        Animal animal = service.prepareForSaving(dto);
        service.save(animal);
        return new ResponseEntity<>(animal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Animal> updateAnimal(@RequestBody AnimalUpdateDto dto, @PathVariable Long id) {
        Animal animal = service.find(id);
        service.prepareForUpdating(animal, dto);
        service.update(animal);
        return ResponseEntity.ok(animal);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

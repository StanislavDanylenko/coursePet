package stanislav.danylenko.coursepet.web.controller.associative;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.service.impl.associative.AnimalGraftService;
import stanislav.danylenko.coursepet.web.JsonRules;
import stanislav.danylenko.coursepet.web.model.associative.AnimalGraftDto;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/animalGraft")
public class AnimalGraftController {

    @Autowired
    private AnimalGraftService service;

    @JsonView(value = JsonRules.AnimalGraft.class)
    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<AnimalGraft>> getAnimalsGrafts() {
        return ResponseEntity.ok(service.findAll());
    }

    @JsonView(value = JsonRules.AnimalGraft.class)
    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<AnimalGraft> getAnimalGraft(@PathVariable Long id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @JsonView(value = JsonRules.AnimalGraft.class)
    @PostMapping
    public @ResponseBody
    ResponseEntity<AnimalGraft> createAnimalGraft(@RequestBody AnimalGraftDto dto) {
        AnimalGraft animalGraft = service.prepareForSaving(dto);
        service.save(animalGraft);
        return new ResponseEntity<>(animalGraft, HttpStatus.CREATED);
    }

    @JsonView(value = JsonRules.AnimalGraft.class)
    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<AnimalGraft> updateAnimalGraft(@RequestBody AnimalGraftDto dto,
                                                  @PathVariable Long id) {

        AnimalGraft animalGraft = service.find(id);
        if(dto.getDate() != null) {
            animalGraft.setDate(dto.getDate());
        }
        service.update(animalGraft);
        return ResponseEntity.ok(animalGraft);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimalGraft(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

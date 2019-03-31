package stanislav.danylenko.coursepet.web.controller.associative;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalGraftPK;
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
    @GetMapping("/animal/{animalId}/graft/{graftId}")
    public @ResponseBody
    ResponseEntity<AnimalGraft> getAnimalGraft(@PathVariable Long animalId, @PathVariable Long graftId) {
        AnimalGraftPK pk = new AnimalGraftPK(animalId, graftId);
        return new ResponseEntity<>(service.find(pk), HttpStatus.OK);
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
    @PutMapping("/animal/{animalId}/graft/{graftId}")
    public @ResponseBody
    ResponseEntity<AnimalGraft> updateAnimalGraft(@RequestBody AnimalGraftDto dto,
                                                  @PathVariable Long animalId,
                                                  @PathVariable Long graftId) {

        AnimalGraftPK pk = new AnimalGraftPK(animalId, graftId);
        AnimalGraft animalGraft = service.find(pk);
        if(dto.getLocalDateTime() != null) {
            animalGraft.setDate(dto.getLocalDateTime());
        }
        service.update(animalGraft);
        return ResponseEntity.ok(animalGraft);
    }

    @DeleteMapping("/animal/{animalId}/graft/{graftId}")
    public void deleteAnimalGraft(@PathVariable Long animalId, @PathVariable Long graftId, HttpServletResponse response)  {
        AnimalGraftPK pk = new AnimalGraftPK(animalId, graftId);
        service.delete(pk);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

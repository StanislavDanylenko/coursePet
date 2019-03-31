package stanislav.danylenko.coursepet.web.controller.associative;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;
import stanislav.danylenko.coursepet.db.entity.pk.AnimalDiseasePK;
import stanislav.danylenko.coursepet.service.impl.associative.AnimalDiseaseService;
import stanislav.danylenko.coursepet.web.JsonRules;
import stanislav.danylenko.coursepet.web.model.associative.AnimalDiseaseDto;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/animalDisease")
public class AnimalDiseaseController {

    @Autowired
    private AnimalDiseaseService service;

    @JsonView(value = JsonRules.AnimalDisease.class)
    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<AnimalDisease>> getAnimalDiseases() {
        return ResponseEntity.ok(service.findAll());
    }

    @JsonView(value = JsonRules.AnimalDisease.class)
    @GetMapping("/animal/{animalId}/disease/{diseaseId}")
    public @ResponseBody
    ResponseEntity<AnimalDisease> getAnimalDisease(@PathVariable Long animalId, @PathVariable Long diseaseId) {
        AnimalDiseasePK pk = new AnimalDiseasePK(animalId, diseaseId);
        return new ResponseEntity<>(service.find(pk), HttpStatus.OK);
    }

    @JsonView(value = JsonRules.AnimalDisease.class)
    @PostMapping
    public @ResponseBody
    ResponseEntity<AnimalDisease> createAnimalDisease(@RequestBody AnimalDiseaseDto dto) {
        AnimalDisease animalDisease = service.prepareForSaving(dto);
        service.save(animalDisease);
        return new ResponseEntity<>(animalDisease, HttpStatus.CREATED);
    }

    @JsonView(value = JsonRules.AnimalDisease.class)
    @PutMapping("/animal/{animalId}/disease/{diseaseId}")
    public @ResponseBody
    ResponseEntity<AnimalDisease> updateAnimalDisease(@RequestBody AnimalDiseaseDto dto,
                                                      @PathVariable Long animalId,
                                                      @PathVariable Long diseaseId) {

        AnimalDiseasePK pk = new AnimalDiseasePK(animalId, diseaseId);
        AnimalDisease animalDisease = service.find(pk);

        service.prepareForUpdating(animalDisease, dto);
        service.update(animalDisease);

        return ResponseEntity.ok(animalDisease);
    }

    @DeleteMapping("/animal/{animalId}/disease/{diseaseId}")
    public void deleteAnimalDisease(@PathVariable Long animalId,
                                    @PathVariable Long diseaseId, HttpServletResponse response)  {

        AnimalDiseasePK pk = new AnimalDiseasePK(animalId, diseaseId);
        service.delete(pk);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

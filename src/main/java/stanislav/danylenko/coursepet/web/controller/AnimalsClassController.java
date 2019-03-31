package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.AnimalsClass;
import stanislav.danylenko.coursepet.service.impl.AnimalsClassService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/animalClass")
public class AnimalsClassController {

    @Autowired
    private AnimalsClassService service;

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<AnimalsClass>> getAnimalClasses() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<AnimalsClass> getAnimalClass(@PathVariable Long id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<AnimalsClass> createAnimalClass(@RequestBody AnimalsClass animalsClass) {
        service.save(animalsClass);
        return new ResponseEntity<>(animalsClass, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<AnimalsClass> updateAnimalClass(@RequestBody AnimalsClass dto, @PathVariable Long id) {
        AnimalsClass animalsClass = service.find(id);
        animalsClass.setName(dto.getName());
        service.update(animalsClass);
        return ResponseEntity.ok(animalsClass);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimalClass(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

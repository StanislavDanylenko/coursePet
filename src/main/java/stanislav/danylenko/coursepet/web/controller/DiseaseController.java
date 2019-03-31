package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.Disease;
import stanislav.danylenko.coursepet.service.impl.DiseaseService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/disease")
public class DiseaseController {

    @Autowired
    private DiseaseService service;

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<Disease>> getDiseases() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Disease> getDisease(@PathVariable Long id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<Disease> createDisease(@RequestBody Disease disease) {
        service.save(disease);
        return new ResponseEntity<>(disease, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Disease> updateDisease(@RequestBody Disease dto, @PathVariable Long id) {
        Disease disease = service.find(id);
        disease.setName(dto.getName());
        service.update(disease);
        return ResponseEntity.ok(disease);
    }

    @DeleteMapping("/{id}")
    public void deleteDisease(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

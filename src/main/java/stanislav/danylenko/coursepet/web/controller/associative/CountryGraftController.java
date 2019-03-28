package stanislav.danylenko.coursepet.web.controller.associative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;
import stanislav.danylenko.coursepet.db.entity.pk.CountryGraftPK;
import stanislav.danylenko.coursepet.service.impl.associative.CountryGraftService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/countryGraft")
public class CountryGraftController {

    @Autowired
    private CountryGraftService service;

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<CountryGraft>> getCountryGrafts() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<CountryGraft> getCountryGraft(@PathVariable CountryGraftPK id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<CountryGraft> createCountryGraft(@RequestBody CountryGraft countryGraft) {
        service.save(countryGraft);
        return new ResponseEntity<>(countryGraft, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<CountryGraft> updateCountryGraft(@RequestBody CountryGraft newAnimal, @PathVariable CountryGraftPK id) {
        CountryGraft countryGraft = service.find(id);
        service.update(newAnimal);
        return ResponseEntity.ok(countryGraft);
    }

    @DeleteMapping("/{id}")
    public void deleteCountryGraft(@PathVariable CountryGraftPK id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

package stanislav.danylenko.coursepet.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.Country;
import stanislav.danylenko.coursepet.service.impl.CountryService;
import stanislav.danylenko.coursepet.web.JsonRules;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService service;

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<Country>> getCountries() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Country> getCountry(@PathVariable Long id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @JsonView(value = JsonRules.CountryFullInfo.class)
    @GetMapping("/full/{id}")
    public @ResponseBody
    ResponseEntity<Country> getFullInfoCountry(@PathVariable Long id) {
        Country country = new Country();
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<Country> createCountry(@RequestBody Country country) {
        service.save(country);
        return new ResponseEntity<>(country, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Country> updateCountry(@RequestBody Country newCountry, @PathVariable Long id) {
        Country country = service.find(id);
        country.setName(newCountry.getName());
        service.update(country);
        return ResponseEntity.ok(country);
    }

    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

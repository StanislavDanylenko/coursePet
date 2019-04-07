package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.Country;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;
import stanislav.danylenko.coursepet.db.entity.pk.CountryGraftPK;
import stanislav.danylenko.coursepet.service.impl.CountryService;
import stanislav.danylenko.coursepet.service.impl.associative.CountryGraftService;
import stanislav.danylenko.coursepet.web.model.CountryWithGraftDto;
import stanislav.danylenko.coursepet.web.model.CountryWithGraftUpdateDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService service;
    @Autowired
    private CountryGraftService countryGraftService;

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

    @GetMapping("/full")
    public @ResponseBody
    ResponseEntity<List<CountryWithGraftDto>> getFullInfoCountries() {
        return new ResponseEntity<>(service.getCountriesWithGrafts(), HttpStatus.OK);
    }

    @GetMapping("/full/{id}")
    public @ResponseBody
    ResponseEntity<CountryWithGraftDto> getFullInfoCountry(@PathVariable Long id) {
        return new ResponseEntity<>(service.getCountryWithGrafts(id), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<Country> createCountry(@RequestBody Country country) {
        service.save(country);
        return new ResponseEntity<>(country, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    void updateCountry(@RequestBody CountryWithGraftUpdateDto newCountry, @PathVariable Long id,  HttpServletResponse response) {
        Country country = service.find(id);
        try{
            service.fullUpdate(country, newCountry);
            service.update(country);
        } catch (JpaObjectRetrievalFailureException e) {
            // do nothing
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

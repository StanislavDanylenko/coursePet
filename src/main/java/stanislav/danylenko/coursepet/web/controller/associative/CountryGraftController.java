package stanislav.danylenko.coursepet.web.controller.associative;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;
import stanislav.danylenko.coursepet.db.entity.pk.CountryGraftPK;
import stanislav.danylenko.coursepet.service.impl.associative.CountryGraftService;
import stanislav.danylenko.coursepet.web.JsonRules;
import stanislav.danylenko.coursepet.web.model.associative.CountryGraftDto;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/countryGraft")
public class CountryGraftController {

    @Autowired
    private CountryGraftService service;

    @JsonView(value = JsonRules.CountryGraft.class)
    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<CountryGraft>> getCountryGrafts() {
        return ResponseEntity.ok(service.findAll());
    }

    @JsonView(value = JsonRules.CountryGraft.class)
    @GetMapping("/country/{countryId}/graft/{graftId}")
    public @ResponseBody
    ResponseEntity<CountryGraft> getCountryGraft(@PathVariable Long countryId, @PathVariable Long graftId) {
        CountryGraftPK pk = new CountryGraftPK(countryId, graftId);
        return new ResponseEntity<>(service.find(pk), HttpStatus.OK);
    }

    @JsonView(value = JsonRules.CountryGraft.class)
    @PostMapping
    public @ResponseBody
    ResponseEntity<CountryGraft> createCountryGraft(@RequestBody CountryGraftDto dto) {
        CountryGraft countryGraft = service.prepareForSaving(dto);
        service.save(countryGraft);
        return new ResponseEntity<>(countryGraft, HttpStatus.CREATED);
    }


    @DeleteMapping("/country/{countryId}/graft/{graftId}")
    public void deleteCountryGraft(@PathVariable Long countryId, @PathVariable Long graftId, HttpServletResponse response)  {
        CountryGraftPK pk = new CountryGraftPK(countryId, graftId);
        service.delete(pk);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

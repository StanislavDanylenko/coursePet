package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.Graft;
import stanislav.danylenko.coursepet.service.impl.GraftService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/graft")
public class GraftController {

    @Autowired
    private GraftService service;

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<Graft>> getGrafts() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Graft> getGraft(@PathVariable Long id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<Graft> createGraft(@RequestBody Graft graft) {
        service.save(graft);
        return new ResponseEntity<>(graft, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Graft> updateGraft(@RequestBody Graft dto, @PathVariable Long id) {
        Graft graft = service.find(id);
        service.prepareForUpdating(graft, dto);
        service.update(graft);
        return ResponseEntity.ok(graft);
    }

    @DeleteMapping("/{id}")
    public void deleteGraft(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

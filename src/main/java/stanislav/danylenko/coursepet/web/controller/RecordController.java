package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.Record;
import stanislav.danylenko.coursepet.service.impl.RecordService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService service;

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<Record>> getRecords() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Record> getRecord(@PathVariable Long id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<Record> createRecord(@RequestBody Record record) {
        service.save(record);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Record> updateRecord(@RequestBody Record newAnimal, @PathVariable Long id) {
        Record record = service.find(id);
        service.update(newAnimal);
        return ResponseEntity.ok(record);
    }

    @DeleteMapping("/{id}")
    public void deleteRecord(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

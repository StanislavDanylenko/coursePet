package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.Record;
import stanislav.danylenko.coursepet.service.impl.RecordService;
import stanislav.danylenko.coursepet.web.model.RecordDto;

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

    @GetMapping("/device/{id}")
    public @ResponseBody
    ResponseEntity<Iterable<Record>> getRecordsByDeviceId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRecordsBySmartDeviceId(id));
    }

    @GetMapping("/device/last/{id}")
    public @ResponseBody
    ResponseEntity<Record> getLastRecordByDeviceId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getLastRecordByDeviceId(id));
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<Record> createRecord(@RequestBody RecordDto dto) {
        Record record = service.prepareForSaving(dto);
        service.save(record);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteRecord(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.SmartDevice;
import stanislav.danylenko.coursepet.service.impl.SmartDeviceService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/smartDevice")
public class SmartDeviceController {

    @Autowired
    private SmartDeviceService service;

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<SmartDevice>> getSmartDevices() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<SmartDevice> getSmartDevice(@PathVariable Long id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<SmartDevice> createSmartDevice(@RequestBody SmartDevice smartDevice) {
        service.save(smartDevice);
        return new ResponseEntity<>(smartDevice, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<SmartDevice> updateSmartDevice(@RequestBody SmartDevice newAnimal, @PathVariable Long id) {
        SmartDevice smartDevice = service.find(id);
        service.update(newAnimal);
        return ResponseEntity.ok(smartDevice);
    }

    @DeleteMapping("/{id}")
    public void deleteSmartDevice(@PathVariable Long id, HttpServletResponse response)  {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}

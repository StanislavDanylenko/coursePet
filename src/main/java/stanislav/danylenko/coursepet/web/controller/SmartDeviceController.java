package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.db.entity.SmartDevice;
import stanislav.danylenko.coursepet.exception.LowBatteryLevelException;
import stanislav.danylenko.coursepet.service.impl.SmartDeviceService;
import stanislav.danylenko.coursepet.web.model.SmartDeviceDto;

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

    @GetMapping("/animal/{id}")
    public @ResponseBody
    ResponseEntity<Iterable<SmartDevice>> getSmartDevices(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSmartDevicesByAnimalId(id));
    }

    @GetMapping("/enable/{id}")
    public void enableSmartDevice(@PathVariable Long id, HttpServletResponse response) {
        try {
            if (service.enableSmartDevice(id)) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (LowBatteryLevelException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    @GetMapping("/disable/{id}")
    public void disableSmartDevice(@PathVariable Long id, HttpServletResponse response) {
        if (service.disableSmartDevice(id)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<SmartDevice> createSmartDevice(@RequestBody SmartDeviceDto dto) {
        SmartDevice smartDevice = service.prepareForSaving(dto);
        service.save(smartDevice);
        return new ResponseEntity<>(smartDevice, HttpStatus.CREATED);
    }

    @PostMapping("/charge/{id}")
    public  void chargeSmartDevice(@PathVariable("id") Long id, HttpServletResponse response) {
        service.chargeSmartDevice(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<SmartDevice> updateSmartDevice(@RequestBody SmartDeviceDto dto, @PathVariable Long id) {
        SmartDevice smartDevice = service.find(id);
        service.prepareForUpdating(smartDevice, dto);
        service.update(smartDevice);
        return ResponseEntity.ok(smartDevice);
    }

    @DeleteMapping("/{id}")
    public void deleteSmartDevice(@PathVariable Long id, HttpServletResponse response) {
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}

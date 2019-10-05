package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.service.impl.bl.CacheService;
import stanislav.danylenko.coursepet.web.model.CacheModel;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheService service;

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<CacheModel> getCache(@PathVariable Long id) {
        return new ResponseEntity<>(service.getCacheForUserId(id), HttpStatus.OK);
    }

}

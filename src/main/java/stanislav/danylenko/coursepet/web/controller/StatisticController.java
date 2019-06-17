package stanislav.danylenko.coursepet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.danylenko.coursepet.service.impl.AnimalsBreedService;
import stanislav.danylenko.coursepet.service.impl.associative.AnimalDiseaseService;
import stanislav.danylenko.coursepet.service.impl.bl.StatisticService;
import stanislav.danylenko.coursepet.web.model.epidemic.EpidemicStatistic;
import stanislav.danylenko.coursepet.web.model.statistic.CountByBreedInCountryStatisticDto;

import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private AnimalDiseaseService animalDiseaseService;

    @GetMapping("/{countryId}")
    public @ResponseBody
    ResponseEntity<List<CountByBreedInCountryStatisticDto>> getStat(@PathVariable("countryId") Long countryId) {
        return ResponseEntity.ok(statisticService.getCountByBreedInCountryStatistic(countryId));
    }

    @GetMapping("/epidemic")
    public @ResponseBody
    ResponseEntity<List<EpidemicStatistic>> getSEpidemicStat() {
        return ResponseEntity.ok(animalDiseaseService.getEpidemicInfo());
    }

}

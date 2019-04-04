package stanislav.danylenko.coursepet.service.impl.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.service.impl.AnimalService;
import stanislav.danylenko.coursepet.web.model.statistic.CountByBreedInCountryStatisticDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticService {

    @Autowired
    private AnimalService animalService;

    public List<CountByBreedInCountryStatisticDto> getCountByBreedInCountryStatistic(Long countryId){
       List<Animal> animals = animalService.findAnimalByUserCountryId(countryId);
       Map<String, Long> breedCountMap = new HashMap<>();
       for(Animal animal : animals) {
           String breed = animal.getAnimalsBreed().getName();
           if(breedCountMap.containsKey(breed)) {
               Long value = breedCountMap.get(breed);
               breedCountMap.put(breed, ++value);
           } else {
               breedCountMap.put(breed, 1L);
           }
       }
       List<CountByBreedInCountryStatisticDto> result = new ArrayList<>();
       for(Map.Entry<String, Long> entry : breedCountMap.entrySet()) {
            result.add(new CountByBreedInCountryStatisticDto(entry.getKey(), entry.getValue()));
       }
       return result;
    }

}

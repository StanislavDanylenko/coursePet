package stanislav.danylenko.coursepet.service.impl.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.entity.AnimalsBreed;
import stanislav.danylenko.coursepet.db.entity.User;
import stanislav.danylenko.coursepet.service.impl.AnimalService;
import stanislav.danylenko.coursepet.service.impl.AnimalsBreedService;
import stanislav.danylenko.coursepet.service.impl.CountryService;
import stanislav.danylenko.coursepet.service.impl.UserService;
import stanislav.danylenko.coursepet.web.model.CacheModel;
import stanislav.danylenko.coursepet.web.model.CountryWithGraftDto;
import stanislav.danylenko.coursepet.web.model.statistic.CountByBreedInCountryStatisticDto;

import java.util.List;

@Service
public class CacheService {

    @Autowired
    private AnimalService animalService;
    @Autowired
    private UserService userService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private AnimalsBreedService animalsBreedService;
    @Autowired
    private StatisticService statisticService;


    public CacheModel getCacheForUserId(Long userId) {

        CacheModel cacheModel = new CacheModel();

        User user = userService.find(userId);
        List<Animal> animalList = animalService.findAnimalsByUserId(userId);
        List<CountryWithGraftDto> countryList = countryService.getCountriesWithGrafts();
        Iterable<AnimalsBreed> animalsBreeds = animalsBreedService.findAll();
        List<CountByBreedInCountryStatisticDto> statistic = statisticService.getCountByBreedInCountryStatistic(userId);

        cacheModel.setUser(user);
        cacheModel.setAnimals(animalList);
        cacheModel.setCountries(countryList);
        cacheModel.setBreeds(animalsBreeds);
        cacheModel.setStatistic(statistic);

        return cacheModel;

    }

}

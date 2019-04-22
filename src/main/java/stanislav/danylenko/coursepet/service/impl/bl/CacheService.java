package stanislav.danylenko.coursepet.service.impl.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.*;
import stanislav.danylenko.coursepet.service.impl.*;
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
    @Autowired
    private GraftService graftService;
    @Autowired
    private DiseaseService diseaseService;


    public CacheModel getCacheForUserId(Long userId) {

        CacheModel cacheModel = new CacheModel();

        User user = userService.find(userId);
        List<Animal> animalList = animalService.findAnimalsByUserId(userId);
        List<CountryWithGraftDto> countryList = countryService.getCountriesWithGrafts();
        Iterable<AnimalsBreed> animalsBreeds = animalsBreedService.findAll();
        List<CountByBreedInCountryStatisticDto> statistic = statisticService.getCountByBreedInCountryStatistic(userId);
        Iterable<Graft> grafts = graftService.findAll();
        Iterable<Disease> diseases = diseaseService.findAll();

        cacheModel.setUser(user);
        cacheModel.setAnimals(animalList);
        cacheModel.setCountries(countryList);
        cacheModel.setBreeds(animalsBreeds);
        cacheModel.setStatistic(statistic);
        cacheModel.setGrafts(grafts);
        cacheModel.setDiseases(diseases);


        return cacheModel;

    }

}

package stanislav.danylenko.coursepet.service.impl.associative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.entity.Country;
import stanislav.danylenko.coursepet.db.entity.Disease;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;
import stanislav.danylenko.coursepet.db.repository.associative.AnimalDiseaseRepository;
import stanislav.danylenko.coursepet.service.GenericService;
import stanislav.danylenko.coursepet.service.impl.AnimalService;
import stanislav.danylenko.coursepet.service.impl.CountryService;
import stanislav.danylenko.coursepet.service.impl.DiseaseService;
import stanislav.danylenko.coursepet.service.impl.bl.StatisticService;
import stanislav.danylenko.coursepet.web.model.associative.AnimalDiseaseDto;
import stanislav.danylenko.coursepet.web.model.epidemic.EpidemicStatistic;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AnimalDiseaseService implements GenericService<AnimalDisease> {
    // animal group by graft -> count of disease -> having count() > 30% (count)
    
    @Autowired
    private AnimalDiseaseRepository animalDiseaseRepository;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private CountryService countryService;


    public AnimalDisease save(AnimalDisease entity) {
        return animalDiseaseRepository.save(entity);
    }

    public void delete(AnimalDisease entity) {
        animalDiseaseRepository.delete(entity);
    }

    public AnimalDisease update(AnimalDisease entity) {
        return animalDiseaseRepository.save(entity);
    }

    public AnimalDisease find(Long id) {
        return animalDiseaseRepository.getOne(id);
    }

    public Iterable<AnimalDisease> findAll() {
        return animalDiseaseRepository.findAll();
    }

    public void delete(Long id) {
       animalDiseaseRepository.deleteById(id);
    }

    public List<AnimalDisease> getDefaultAnimalDisese(Animal animal, Disease disease) {
        return animalDiseaseRepository.findByAnimalAndDisease(animal, disease);
    }

    public AnimalDisease prepareForSaving(AnimalDiseaseDto dto) {

        AnimalDisease animalDisease = new AnimalDisease();

        if(dto.getAnimalId() != null) {
            animalDisease.setAnimal(animalService.find(dto.getAnimalId()));
        }
        if(dto.getDiseaseId() != null) {
            animalDisease.setDisease(diseaseService.find(dto.getDiseaseId()));
        }

        animalDisease = prepareForUpdating(animalDisease, dto);

        return animalDisease;

    }

    public AnimalDisease prepareForUpdating(AnimalDisease animalDisease, AnimalDiseaseDto dto) {

        if(dto.getTreatment() != null) {
            animalDisease.setTreatment(dto.getTreatment());
        }
        if(dto.getStartData() != null) {
            animalDisease.setStartData(dto.getStartData());
        }
        if(dto.getEndDate() != null) {
            animalDisease.setEndDate(dto.getEndDate());
        }

        return animalDisease;
    }

    public List<EpidemicStatistic> getEpidemicInfo() {

        Iterable<Country> countries = countryService.findAll();
        List<EpidemicStatistic> epidemicStatistics = new ArrayList<>();

        countries.forEach(country -> epidemicStatistics.addAll(getEpidemicInfoByCountry(country)));

        return epidemicStatistics;
    }

    public List<EpidemicStatistic> getEpidemicInfoByCountry(Country country) {
        Long countryId = country.getId();
        List<EpidemicStatistic> epidemicStatistics = new ArrayList<>();
        Map<String, Map<String, Long>> calculatingData = new LinkedHashMap<>(); // 1st - breed name, 2d - disease name, 3d - count with disease

        List<Animal> animalsInCountry = animalService.findAnimalByUserCountryId(countryId);

        animalsInCountry.forEach(animal -> {
            String animalBreed = animal.getAnimalsBreed().getName();
            Map<String, Long> diseaseCountMap = calculatingData.get(animalBreed);

            if (diseaseCountMap == null){
                diseaseCountMap = new LinkedHashMap<>();
            }

            Map<String, Long> finalDiseaseCountMap = diseaseCountMap;
            animal.getAnimalDiseases().forEach(animalDisease -> {
                String diseaseName = animalDisease.getDisease().getName();
                ///
                Date diseaseDate = animalDisease.getEndDate();
                Date currentDate = new Date();
                long diffInMillies = Math.abs(currentDate.getTime() - diseaseDate.getTime());
                long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                /// checking is count of days > 50 for epidemic
                if (days < 50) {
                    Long count = finalDiseaseCountMap.get(diseaseName);
                    if (count == null) {
                        count = 1L;
                    } else {
                        ++count;
                    }
                    finalDiseaseCountMap.put(diseaseName, count);
                }
            });

            calculatingData.put(animalBreed, finalDiseaseCountMap);

        });

        /////
        Map<String, Long> breedCountMap = statisticService.getBreedCountMap(countryId);
        calculatingData.forEach((breedName, diseaseCountMap) -> {

            Long totalBreedCountAnimals = breedCountMap.get(breedName);

            diseaseCountMap.forEach((diseaseName, count) -> {
                double percentage = count / (totalBreedCountAnimals + 0.0);
                if (percentage > 0.15) {
                    EpidemicStatistic epidemicStatistic = new EpidemicStatistic();
                    epidemicStatistic.setBreed(breedName);
                    epidemicStatistic.setDisease(diseaseName);
                    epidemicStatistic.setPercentage(percentage);
                    epidemicStatistic.setCount(count);
                    epidemicStatistic.setCountry(country.getName());
                    epidemicStatistics.add(epidemicStatistic);
                }

            });

        });

        //////////

        return epidemicStatistics;
    }

}

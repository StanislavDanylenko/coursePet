package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.entity.Country;
import stanislav.danylenko.coursepet.db.entity.Disease;
import stanislav.danylenko.coursepet.db.entity.Graft;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;
import stanislav.danylenko.coursepet.db.repository.AnimalRepository;
import stanislav.danylenko.coursepet.db.repository.associative.AnimalDiseaseRepository;
import stanislav.danylenko.coursepet.db.repository.associative.AnimalGraftRepository;
import stanislav.danylenko.coursepet.service.SimpleIdService;
import stanislav.danylenko.coursepet.web.model.AnimalCreateDto;
import stanislav.danylenko.coursepet.web.model.AnimalFullInfoDto;
import stanislav.danylenko.coursepet.web.model.AnimalUpdateDto;
import stanislav.danylenko.coursepet.web.model.IsAvailableCountry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AnimalService implements SimpleIdService<Animal> {

    public static String DEFAULT_ANIMAL = "DefaultAnimal";

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalDiseaseRepository animalDiseaseRepository;

    @Autowired
    private AnimalGraftRepository animalGraftRepository;

    @Autowired
    private CountryService countryService;

    @Override
    public Animal save(Animal entity) {
        return animalRepository.save(entity);
    }

    @Override
    public void delete(Animal entity) {
        animalRepository.delete(entity);
    }

    @Override
    public Animal update(Animal entity) {
        return animalRepository.save(entity);
    }

    @Override
    public Animal find(Long id) {
        return animalRepository.getOne(id);
    }

    public List<AnimalDisease> findAnimalDiseasesByAnimalId(Long id) {
        return animalDiseaseRepository.findByAnimalId(id);
    }

    public List<AnimalGraft> findAnimalGraftsByAnimalId(Long id) {
        return animalGraftRepository.findByAnimalId(id);
    }


    public Animal findByName(String name) {
        return animalRepository.findByName(name);
    }

    @Override
    public Iterable<Animal> findAll() {
        return animalRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        animalRepository.deleteById(id);
    }


    public Animal prepareForSaving(AnimalCreateDto dto) {

        Animal animal = new Animal();

        if (dto.getName() != null) {
            animal.setName(dto.getName());
        }

        if (dto.getGender() != null) {
            animal.setGender(dto.getGender());
        }

        if (dto.getBirthDate() != null) {
            animal.setBirthDate(dto.getBirthDate());
        }

        animal = prepareForUpdating(animal, dto);

        return animal;
    }

    public Animal prepareForUpdating(Animal animal, AnimalUpdateDto dto) {

        if (dto.getPhotoURL() != null) {
            animal.setPhotoURL(dto.getPhotoURL());
        }

        if (dto.getHeight() != null) {
            animal.setHeight(dto.getHeight());
        }

        if (dto.getLength() != null) {
            animal.setLength(dto.getLength());
        }

        if (dto.getWeight() != null) {
            animal.setWeight(dto.getWeight());
        }

        return animal;
    }

    private Animal prepareForUpdating(Animal animal, AnimalCreateDto dto) {

        if (dto.getPhotoURL() != null) {
            animal.setPhotoURL(dto.getPhotoURL());
        }

        if (dto.getHeight() != null) {
            animal.setHeight(dto.getHeight());
        }

        if (dto.getLength() != null) {
            animal.setLength(dto.getLength());
        }

        if (dto.getWeight() != null) {
            animal.setWeight(dto.getWeight());
        }

        return animal;
    }

    public Animal getDefaultAnimal() {
        return animalRepository.findByName(DEFAULT_ANIMAL);
    }

    public AnimalFullInfoDto getAnimalFullInfo(Long id) {
        Animal animal = find(id);
        List<Disease> diseases = new ArrayList<>();
        List<AnimalDisease> animalDiseases = findAnimalDiseasesByAnimalId(id);
        for(AnimalDisease animalDisease : animalDiseases) {
            diseases.add(animalDisease.getDisease());
        }
        List<AnimalGraft> animalGrafts = findAnimalGraftsByAnimalId(id);
        List<Graft> grafts = new ArrayList<>();
        for(AnimalGraft animalGraft : animalGrafts) {
            grafts.add(animalGraft.getGraft());
        }
        AnimalFullInfoDto dto = new AnimalFullInfoDto();

        dto.setAnimal(animal);
        dto.setDiseases(diseases);
        dto.setGrafts(grafts);

        return dto;
    }

    public IsAvailableCountry checkIsAvailableCountry(Long animalId, Long countryId) {

        Country country = countryService.find(countryId);
        Animal animal = find(animalId);

        List<AnimalGraft> animalGrafts = animal.getAnimalGrafts();
        List<CountryGraft> countryGrafts = country.getCountryGrafts();

        Set<Graft> realCountryGrafts = new HashSet<>();
        Set<Graft> realAnimalGrafts = new HashSet<>();

        for(AnimalGraft animalGraft : animalGrafts) {
            realAnimalGrafts.add(animalGraft.getGraft());
        }

        for(CountryGraft countryGraft : countryGrafts) {
            realCountryGrafts.add(countryGraft.getGraft());
        }

        if(realAnimalGrafts.containsAll(realCountryGrafts)) {
            return new IsAvailableCountry(true, null);
        }

        realCountryGrafts.removeAll(realAnimalGrafts);
        return new IsAvailableCountry(false, realCountryGrafts);

    }

}

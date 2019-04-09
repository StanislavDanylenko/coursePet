package stanislav.danylenko.coursepet.config.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import stanislav.danylenko.coursepet.db.entity.*;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalDisease;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.entity.associative.CountryGraft;
import stanislav.danylenko.coursepet.db.enumeration.AnimalState;
import stanislav.danylenko.coursepet.db.enumeration.Gender;
import stanislav.danylenko.coursepet.db.enumeration.Localization;
import stanislav.danylenko.coursepet.db.enumeration.Role;
import stanislav.danylenko.coursepet.service.impl.*;
import stanislav.danylenko.coursepet.service.impl.associative.AnimalDiseaseService;
import stanislav.danylenko.coursepet.service.impl.associative.AnimalGraftService;
import stanislav.danylenko.coursepet.service.impl.associative.CountryGraftService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private AnimalsClassService animalsClassService;

    @Autowired
    private AnimalsBreedService animalsBreedService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private GraftService graftService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private SmartDeviceService smartDeviceService;

    @Autowired
    private AnimalGraftService animalGraftService;

    @Autowired
    private AnimalDiseaseService animalDiseaseService;

    @Autowired
    private CountryGraftService countryGraftService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AtomicLong smartCardIdCreator;

    @Override
    public void run(String... args) throws Exception {
        log.info("initializing data...");

        ////////// country
        Country country = countryService.getDefaultCountry();
        if(country != null) {
            log.info("Test country is already in DB");
        } else {
            country = new Country();
            country.setName(CountryService.DEFAULT_COUNTRY);
            country.setDescription("DescriptionHere");
            countryService.save(country);
            log.info("Created country {Ukraine}");
        }

        ////////////// users
        UserDetails user;
        try {
            user = userService.loadUserByUsername("user");
            log.info("Test user is already in DB");
        } catch (UsernameNotFoundException e) {
            user = userService.save(User.builder()
                    .username("user")
                    .password(this.passwordEncoder.encode("password"))
                    .roles(Arrays.asList(Role.USER))
                    .country(country)
                    .localization(Localization.ENGLISH)
                    .build()
            );
            log.info("Created user {user, password}");
        }

        UserDetails admin;
        try {
            admin = userService.loadUserByUsername("admin");
            log.info("Test admin is already in DB");
        } catch (UsernameNotFoundException e) {
            admin = userService.save(User.builder()
                    .username("admin")
                    .password(this.passwordEncoder.encode("password"))
                    .roles(Arrays.asList(Role.ADMIN))
                    .country(country)
                    .localization(Localization.ENGLISH)
                    .build()
            );
            log.info("Created admin {admin, password}");
        }

        ////////////Animal class
        AnimalsClass animalsClass = animalsClassService.getDefaultAnimalClass();
        if(animalsClass != null) {
            log.info("Test AnimalClass is already in DB");
        } else {
            animalsClass = new AnimalsClass();
            animalsClass.setName(AnimalsClassService.DEFAULT_ANIMAL_CLASS);
            animalsClassService.save(animalsClass);
            log.info("Created AnimalClass {AnimalClass}");
        }

        ////////////Animal breed
        AnimalsBreed animalsBreed = animalsBreedService.getDefaultAnimalBreed();
        if(animalsBreed != null) {
            log.info("Test AnimalBreed is already in DB");
        } else {
            animalsBreed = new AnimalsBreed();
            animalsBreed.setName(AnimalsBreedService.DEFAULT_ANIMAL_BREED);
            animalsBreed.setAnimalsClass(animalsClass);
            animalsBreedService.save(animalsBreed);
            log.info("Created AnimalBreed {AnimalBreed}");
        }

        ////////////Animal
        Animal animal= animalService.getDefaultAnimal();
        if(animal != null) {
            log.info("Test Animal is already in DB");
        } else {
            animal = new Animal();
            animal.setName(AnimalService.DEFAULT_ANIMAL);
            animal.setAnimalsBreed(animalsBreed);
            animal.setBirthDate(Date.from(Instant.now()));
            animal.setWeight(40d);
            animal.setLength(40d);
            animal.setHeight(40d);
            animal.setGender(Gender.MALE);
            animal.setUser((User)user);
            animal.setSmartCardId(smartCardIdCreator.incrementAndGet() + "");
            animalService.save(animal);
            log.info("Created Animal {Animal}");
        }

        ////////////Disease
        Disease disease = diseaseService.getDefaultDisease();
        if(disease != null) {
            log.info("Test Disease is already in DB");
        } else {
            disease = new Disease();
            disease.setName(DiseaseService.DEFAULT_DISEASE);
            diseaseService.save(disease);
            log.info("Created Disease {Disease}");
        }

        ////////////Disease
        Graft graft = graftService.getDefaultGraft();
        if(graft != null) {
            log.info("Test Graft is already in DB");
        } else {
            graft = new Graft();
            graft.setName(GraftService.DEFAULT_GRAFT);
            graft.setFrequency(20d);
            graftService.save(graft);
            log.info("Created Graft {Graft}");
        }

        ////////////Animal Graft
        try{
            AnimalGraft animalGraft = new AnimalGraft();
            animalGraft.setAnimal(animal);
            animalGraft.setGraft(graft);
            animalGraft.setDate(LocalDateTime.now());
            animalGraftService.save(animalGraft);
            log.info("Created AnimalGraft {AnimalGraft}");
        } catch (Exception ex) {
            log.info("Test AnimalGraft is already in DB");
        }


        ////////////Animal Disease
        try{
            AnimalDisease animalDisease = new AnimalDisease();
            animalDisease.setAnimal(animal);
            animalDisease.setDisease(disease);
            animalDisease.setTreatment("Treathment default");
            animalDisease.setStartData(LocalDateTime.now());
            animalDisease.setEndDate(LocalDateTime.now());
            animalDiseaseService.save(animalDisease);
            log.info("Created AnimalDisease {AnimalDisease}");
        } catch (Exception ex) {
            log.info("Test AnimalDisease is already in DB");
        }

        ////////////Country Graft
        try{
            CountryGraft countryGraft = new CountryGraft();
            countryGraft.setCountry(country);
            countryGraft.setGraft(graft);
            countryGraftService.save(countryGraft);
            log.info("Created CountryGraft {CountryGraft}");
        } catch (Exception ex) {
            log.info("Test CountryGraft is already in DB");
        }

        ////////////Smart Device
        SmartDevice smartDevice = smartDeviceService.getDefaultSmartDevice();
        if(smartDevice != null) {
            log.info("Test SmartDevice is already in DB");
        } else {
            smartDevice = new SmartDevice();
            smartDevice.setAnimal(animal);
            smartDevice.setName(SmartDeviceService.DEFAULT_SMART_DEVICE);
            smartDevice.setBatteryLevel(100d);
            smartDevice.setMac(SmartDeviceService.DEFAULT_SMART_DEVICE);
            smartDevice.setIsActive(false);
            smartDeviceService.save(smartDevice);
            log.info("Created SmartDevice {SmartDevice}");


            Record record1 = new Record();
            record1.setCreationDate(LocalDateTime.now());
            record1.setTemperature(40d);
            record1.setPulse(40d);
            record1.setLongitude(40d);
            record1.setLatitude(40d);
            record1.setAnimalState(AnimalState.ACTIVE);
            record1.setSmartDevice(smartDevice);

            Record record2 = new Record();
            record2.setCreationDate(LocalDateTime.now());
            record2.setTemperature(20d);
            record2.setPulse(20d);
            record2.setLongitude(30d);
            record2.setLatitude(40d);
            record2.setAnimalState(AnimalState.SLEEP);
            record2.setSmartDevice(smartDevice);

            recordService.save(record1);
            recordService.save(record2);

        }

    }
}

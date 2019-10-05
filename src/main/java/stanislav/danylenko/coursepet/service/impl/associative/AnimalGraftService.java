package stanislav.danylenko.coursepet.service.impl.associative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Animal;
import stanislav.danylenko.coursepet.db.entity.Graft;
import stanislav.danylenko.coursepet.db.entity.associative.AnimalGraft;
import stanislav.danylenko.coursepet.db.enumeration.Localization;
import stanislav.danylenko.coursepet.db.repository.associative.AnimalGraftRepository;
import stanislav.danylenko.coursepet.service.GenericService;
import stanislav.danylenko.coursepet.service.impl.AnimalService;
import stanislav.danylenko.coursepet.service.impl.CountryService;
import stanislav.danylenko.coursepet.service.impl.GraftService;
import stanislav.danylenko.coursepet.web.model.schedule.AnimalGraftStatistics;
import stanislav.danylenko.coursepet.web.model.schedule.ScheduleGraftDto;
import stanislav.danylenko.coursepet.web.model.associative.AnimalGraftDto;
import stanislav.danylenko.coursepet.web.model.schedule.ScheduleInfo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnimalGraftService implements GenericService<AnimalGraft> {

    Map<String, String> englishDictionary;
    Map<String, String> ukrainianDictionary;

    {
        englishDictionary = new LinkedHashMap<>();
        englishDictionary.put("After", "After ");
        englishDictionary.put(",near", " months , near ");
        englishDictionary.put("thisMonth", "In this month. Near ");
        englishDictionary.put("now", "Just now. Had to do near  ");
        englishDictionary.put("never", "Never");
        englishDictionary.put("justNow", "Just now!");


        ukrainianDictionary = new LinkedHashMap<>();
        ukrainianDictionary.put("After", "Через ");
        ukrainianDictionary.put(",near", " місяців, приблизно ");
        ukrainianDictionary.put("thisMonth", "У цьому місяці. Приблизно ");
        ukrainianDictionary.put("now", "Негайно. Неохідно буо зробити ");
        ukrainianDictionary.put("never", "Ніколи");
        ukrainianDictionary.put("justNow", "Негайно!");

    }
    
    @Autowired
    private AnimalGraftRepository animalDiseaseRepository;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private GraftService graftService;
    
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public AnimalGraft save(AnimalGraft entity) {
        return animalDiseaseRepository.save(entity);
    }

    public void delete(AnimalGraft entity) {
        animalDiseaseRepository.delete(entity);
    }

    @Override
    public AnimalGraft update(AnimalGraft entity) {
        return animalDiseaseRepository.save(entity);
    }

    @Override
    public AnimalGraft find(Long id) {
        return animalDiseaseRepository.getOne(id);
    }

    @Override
    public Iterable<AnimalGraft> findAll() {
        return animalDiseaseRepository.findAll();
    }

    @Override
    public void delete(Long id) {
       animalDiseaseRepository.deleteById(id);
    }

    public List<AnimalGraft> getDefaultAnimalGraft(Animal animal, Graft graft) {
        return animalDiseaseRepository.findByAnimalAndGraft(animal, graft);
    }

    public ScheduleGraftDto getListOfLastGrafts(Long id, Localization localization) {

        Map<String, String> langMap = englishDictionary;
        if (localization == Localization.UKRAINIAN) {
            langMap = ukrainianDictionary;
        }

        ScheduleGraftDto scheduleGraftDto = new ScheduleGraftDto();

        List<ScheduleInfo> criticalGrafts = new ArrayList<>();
        List<ScheduleInfo> presentGrafts = new ArrayList<>();
        List<ScheduleInfo> nextGrafts = new ArrayList<>();

        ///////

        Animal animal = animalService.find(id);
        List<AnimalGraftStatistics> listOfLastGrafts = animalDiseaseRepository.getListOfLastGrafts(id);

        //////

        Set<Graft> animalGrafts = listOfLastGrafts.stream().map(AnimalGraftStatistics::getGraft).filter(Objects::nonNull).collect(Collectors.toSet());

        Set<Graft> countryGrafts = new HashSet<>(countryService.findGraftsByCountryId(animal.getUser().getCountry().getId()));
        Set<Graft> countryGraftsCopy = new HashSet<>(countryGrafts);

        countryGrafts.retainAll(animalGrafts);
        countryGraftsCopy.removeAll(animalGrafts);

        final Date currentDate = new Date();

        //////////
        Map<String, String> finalLangMap = langMap;
        listOfLastGrafts.forEach(animalGraftFromDB -> {
            Graft graft = animalGraftFromDB.getGraft();
            if (countryGrafts.contains(graft)) {

                ScheduleInfo scheduleInfo = new ScheduleInfo();
                scheduleInfo.setName(graft.getName());
                scheduleInfo.setFrequency(graft.getFrequency());

                Date lastDate = animalGraftFromDB.getMaxDate();
                String strDateString = new SimpleDateFormat(DATE_FORMAT).format(lastDate);
                String currentDateString = new SimpleDateFormat(DATE_FORMAT).format(currentDate);

                Period diff = Period.between(
                        LocalDate.parse(strDateString).withDayOfMonth(1),
                        LocalDate.parse(currentDateString).withDayOfMonth(1));

                int monthsCount = diff.getYears() * 12 + diff.getMonths();
                int frequency = (int)(graft.getFrequency() + 0.5);

                String nextDate = "";
                int difference = frequency - monthsCount;

                Calendar cal = Calendar.getInstance();
                cal.setTime(lastDate);

                List<ScheduleInfo> list = null;

                if (difference > 0) {
                    cal.add(Calendar.MONTH, difference + 1);
                    nextDate = finalLangMap.get("After") + (frequency - monthsCount + 1) + finalLangMap.get(",near") + new SimpleDateFormat(DATE_FORMAT).format(cal.getTime());
                    list = nextGrafts;
                } else if(difference == 0){
                    cal.add(Calendar.MONTH, frequency);
                    nextDate = finalLangMap.get("thisMonth") + new SimpleDateFormat(DATE_FORMAT).format(cal.getTime());
                    list = presentGrafts;
                } else {
                    cal.add(Calendar.MONTH, frequency);
                    nextDate = finalLangMap.get("now") + new SimpleDateFormat(DATE_FORMAT).format(cal.getTime());
                    list = criticalGrafts;
                    scheduleInfo.setIsNew(false);
                }

                scheduleInfo.setLastDate(strDateString);
                scheduleInfo.setNextDate(nextDate);
                list.add(scheduleInfo);

            }
        });

        countryGraftsCopy.forEach(graft -> {
            ScheduleInfo scheduleInfo = new ScheduleInfo();
            scheduleInfo.setName(graft.getName());
            scheduleInfo.setFrequency(graft.getFrequency());
            scheduleInfo.setLastDate(finalLangMap.get("never"));
            scheduleInfo.setNextDate(finalLangMap.get("Just now!"));
            scheduleInfo.setIsNew(true);

            List<ScheduleInfo> list = criticalGrafts;
            list.add(scheduleInfo);
        });

        scheduleGraftDto.setCriticalGrafts(criticalGrafts);
        scheduleGraftDto.setNextGrafts(nextGrafts);
        scheduleGraftDto.setPresentGrafts(presentGrafts);

        return scheduleGraftDto;
    }

    public AnimalGraft prepareForSaving(AnimalGraftDto dto) {

        AnimalGraft animalGraft = new AnimalGraft();

        if(dto.getAnimalId() != null) {
            animalGraft.setAnimal(animalService.find(dto.getAnimalId()));
        }
        if(dto.getGraftId() != null) {
           animalGraft.setGraft(graftService.find(dto.getGraftId()));
        }
        if(dto.getDate() != null) {
            animalGraft.setDate(dto.getDate());
        }
        return animalGraft;
    }
}

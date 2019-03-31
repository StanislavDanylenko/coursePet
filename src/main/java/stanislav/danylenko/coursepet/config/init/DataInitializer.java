package stanislav.danylenko.coursepet.config.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import stanislav.danylenko.coursepet.db.entity.Country;
import stanislav.danylenko.coursepet.db.entity.User;
import stanislav.danylenko.coursepet.db.enumeration.Role;
import stanislav.danylenko.coursepet.service.impl.CountryService;
import stanislav.danylenko.coursepet.service.impl.UserService;

import java.util.Arrays;
import java.util.HashSet;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("initializing data...");


        ////////////// users

        try {
            UserDetails user = userService.loadUserByUsername("user");
            log.info("Test user is already in DB");
        } catch (UsernameNotFoundException e) {
            userService.save(User.builder()
                    .username("user")
                    .password(this.passwordEncoder.encode("password"))
                    .roles(new HashSet<>(Arrays.asList(Role.USER)))
                    .build()
            );
            log.info("Created user {user, password}");
        }


        try {
            UserDetails admin = userService.loadUserByUsername("admin");
            log.info("Test admin is already in DB");
        } catch (UsernameNotFoundException e) {
            userService.save(User.builder()
                    .username("admin")
                    .password(this.passwordEncoder.encode("password"))
                    .roles(new HashSet<>(Arrays.asList(Role.USER, Role.ADMIN)))
                    .build()
            );
            log.info("Created admin {admin, password}");
        }

        ////////// country


        Country country = countryService.getDefaultCountry();
        if(country != null) {
            log.info("Test country is already in DB");
        } else {
            country = new Country();
            country.setName(CountryService.DEFAULT_COUNTRY);
            countryService.save(country);
            log.info("Created country {Ukraine}");
        }

    }
}

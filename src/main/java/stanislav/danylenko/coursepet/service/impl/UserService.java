package stanislav.danylenko.coursepet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import stanislav.danylenko.coursepet.db.entity.Country;
import stanislav.danylenko.coursepet.db.entity.User;
import stanislav.danylenko.coursepet.db.enumeration.Localization;
import stanislav.danylenko.coursepet.db.repository.UserRepository;
import stanislav.danylenko.coursepet.service.GenericService;
import stanislav.danylenko.coursepet.service.SimpleIdService;
import stanislav.danylenko.coursepet.web.model.UpdatePasswordModel;
import stanislav.danylenko.coursepet.web.model.UserDto;

import java.util.List;

@Service
@Primary
public class UserService implements SimpleIdService<User>, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryService countryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + name + " not found"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User find(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User prepareUserForUpdate(User user, UserDto newUser) {
        Localization localization = newUser.getLocalization();
        Long countryId = newUser.getCountryId();

        if(localization != null) {
            user.setLocalization(localization);
        }
        if(countryId != null) {
            Country country = countryService.find(countryId);
            user.setCountry(country);
        }
        return user;
    }

    public boolean changePassword(UpdatePasswordModel model) {
        User user = userRepository.findById(model.getId()).orElse(null);
        if (user != null) {
            if (passwordEncoder.matches(model.getOldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(model.getNewPassword()));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}

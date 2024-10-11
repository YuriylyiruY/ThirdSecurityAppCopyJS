package SecurityApp.services;

import SecurityApp.models.User;
import SecurityApp.models.Auth;
import SecurityApp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
public class RegistrationServiceImp implements RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonDetailsService personDetailsService;
    Auth auth1;

    @Autowired
    public RegistrationServiceImp(
            PeopleRepository peopleRepository,
            PasswordEncoder passwordEncoder,
            PersonDetailsService personDetailsService,
            UserServiceImp peopleService) {

        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
        this.personDetailsService = personDetailsService;

    }


    public void makeEncode(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        peopleRepository.save(user);
    }


    @Transactional
    public void registerSuperAdmin(User user) {
        System.out.println("hhhhhhhhhhhhhh");
        Auth auth3 = personDetailsService.findRole("ROLE_USER");
        Auth auth4 = personDetailsService.findRole("ROLE_ADMIN");
        auth3.setPeople(user);
        auth4.setPeople(user);

    }

    @Transactional
    public void registerAdmin(User user, Auth auth4) {

        if (Objects.equals(auth4.getRole(), "ADMIN")) {
            auth4 = personDetailsService.findRole("ROLE_ADMIN");
            user.setAuths(auth4);
        } else {
            auth4 = personDetailsService.findRole("ROLE_USER");
            user.setAuths(auth4);
        }


    }


}

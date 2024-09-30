package SecurityApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import SecurityApp.models.Auth;
import SecurityApp.models.User;
import SecurityApp.repositories.AuthRepository;
import SecurityApp.repositories.PeopleRepository;

import java.util.Optional;


@Service
public class RegistrationService {
    private final AuthRepository authRepository;
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonDetailsService personDetailsService;
    Auth auth1;

    @Autowired
    public RegistrationService(AuthRepository authRepository,
                               PeopleRepository peopleRepository,
                               PasswordEncoder passwordEncoder,
                               PersonDetailsService personDetailsService,
                               PeopleService peopleService) {
        this.authRepository = authRepository;
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
        this.personDetailsService = personDetailsService;

    }

    @Transactional
    public boolean findByRole(String s) throws UsernameNotFoundException {
        Optional<Auth> auth = authRepository.findByRole(s);

        return auth.isEmpty();

    }

    @Transactional
    public void registerAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        peopleRepository.save(user);

        Auth auth = new Auth("ROLE_USER");
        Auth auth2 = new Auth("ROLE_ADMIN");

        if (findByRole("ROLE_USER")) {


            authRepository.save(auth);
            authRepository.save(auth2);
            // auth.setPeople(user);
            // auth2.setPeople(user);
            user.setAuths(auth);
            user.setAuths(auth2);


        }
        Auth auth3 = personDetailsService.findRole("ROLE_USER");
        Auth auth4 = personDetailsService.findRole("ROLE_ADMIN");
        System.out.println("hhhhhhhhhhhhhh");
        // auth3.setPeople(user);
        // auth4.setPeople(user);
        user.setAuths(auth3);
        user.setAuths(auth4);
    }

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        peopleRepository.save(user);

        Auth auth = new Auth("ROLE_USER");
        Auth auth2 = new Auth("ROLE_ADMIN");


        if (findByRole("ROLE_USER")) {


            this.auth1 = authRepository.save(auth);
            authRepository.save(auth2);
            //   auth1.setPeople(user);
            user.setAuths(auth1);


        }
        Auth auth3 = personDetailsService.findRole("ROLE_USER");
        System.out.println("hhhhhhhhhhhhhh");
        //  auth3.setPeople(user);
        user.setAuths(auth3);
    }

}

package SecurityApp.services;

import SecurityApp.models.User;
import SecurityApp.models.Role;
import SecurityApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
public class RegistrationServiceImp implements RegistrationService {

    private final UserRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService personDetailsService;
    private final RoleService roleService;
    Role auth1;

    @Autowired
    public RegistrationServiceImp(
            UserRepository peopleRepository,
            PasswordEncoder passwordEncoder,
            UserDetailsService personDetailsService,
            UserServiceImp peopleService,
            RoleService roleService) {

        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
        this.personDetailsService = personDetailsService;
        this.roleService = roleService;
    }


    public void makeEncode(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        peopleRepository.save(user);
    }


    @Transactional
    public void registerSuperAdmin(User user) {
        System.out.println("hhhhhhhhhhhhhh");
        Role auth3 = roleService.findRole("ROLE_USER");
        Role auth4 = roleService.findRole("ROLE_ADMIN");
        auth3.setPeople(user);
        auth4.setPeople(user);

    }

//    @Transactional
//    public void registerAdmin(User user, Auth auth4) {
//
//        if (Objects.equals(auth4.getRole(), "ADMIN")) {
//            auth4 = roleService.findRole("ROLE_ADMIN");
//            user.setAuths(auth4);
//        } else {
//            auth4 = roleService.findRole("ROLE_USER");
//            user.setAuths(auth4);
//        }
//
//
//    }

    @Transactional
    public void registerAAdmin(User user, Role auth4, String isAdmin, String isUser) {
        if (Objects.equals(user.getAdmin(), "") && Objects.equals(user.getUser(), "")) {
            auth4 = roleService.findRole("ROLE_USER");
            user.setAuths(auth4);
        }
        if (user.getUser().equals("on")&& user.getAdmin().equals("on")) {
            auth4 = roleService.findRole("ROLE_ADMIN");
            user.setAuths(auth4);
            auth4 = roleService.findRole("ROLE_USER");
            user.setAuths(auth4);
        }
        if (user.getAdmin().equals("on")) {
            auth4 = roleService.findRole("ROLE_ADMIN");
            user.setAuths(auth4);
        }
        if (user.getUser().equals("on")) {
            auth4 = roleService.findRole("ROLE_USER");
            user.setAuths(auth4);
        }


    }


}

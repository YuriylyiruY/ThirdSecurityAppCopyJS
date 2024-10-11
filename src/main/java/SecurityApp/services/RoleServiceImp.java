package SecurityApp.services;

import SecurityApp.models.User;
import SecurityApp.models.Auth;
import SecurityApp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class RoleServiceImp implements RoleService {
    @Autowired
    private final RoleRepository authRepository;

    public RoleServiceImp(RoleRepository authRepository) {
        this.authRepository = authRepository;
    }


    public void addRolesToTable(User user) {
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
    }

    public boolean findByRole(String s) throws UsernameNotFoundException {
        Optional<Auth> auth = authRepository.findByRole(s);

        return auth.isEmpty();

    }
}

package SecurityApp.services;


import SecurityApp.models.Auth;
import SecurityApp.models.User;
import SecurityApp.repositories.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import SecurityApp.repositories.PeopleRepository;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final AuthRepository authRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, AuthRepository authRepository) {
        this.peopleRepository = peopleRepository;
        this.authRepository = authRepository;
    }

    public List<User> findAll() {
        return peopleRepository.findAll();
    }

    public User findOne(int id) {
        Optional<User> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(User user) {
        peopleRepository.save(user);
    }

    @Transactional
    public void update(int id, User updatedPerson) {
        updatedPerson.setId(id);

        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }


}

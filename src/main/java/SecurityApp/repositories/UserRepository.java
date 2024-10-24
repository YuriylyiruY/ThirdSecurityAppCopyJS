package SecurityApp.repositories;

import SecurityApp.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String username);
    @EntityGraph(attributePaths = "auths")
    Optional<User>findByEmail(String email);


}

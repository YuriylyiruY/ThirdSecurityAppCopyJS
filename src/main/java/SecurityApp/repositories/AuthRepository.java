package SecurityApp.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import SecurityApp.models.Auth;

import java.util.Optional;


@Repository
public interface AuthRepository extends JpaRepository<Auth, Integer> {


    Optional<Auth> findByRole(String role);

}


package SecurityApp.repositories;


import SecurityApp.models.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Auth, Integer> {


    Optional<Auth> findByRole(String role);

}


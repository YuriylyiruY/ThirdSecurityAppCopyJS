package SecurityApp.services;

import SecurityApp.models.User;

public interface RoleService {
     boolean findByRole(String s);
     void addRolesToTable(User user);
}

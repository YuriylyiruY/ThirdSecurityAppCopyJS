package SecurityApp.services;

import SecurityApp.models.User;
import SecurityApp.models.Auth;

public interface RegistrationService {
     void makeEncode(User user);
     void registerSuperAdmin(User user);
     void registerAdmin(User user, Auth auth4);

}

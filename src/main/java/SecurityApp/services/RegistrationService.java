package SecurityApp.services;

import SecurityApp.models.User;
import SecurityApp.models.Role;

public interface RegistrationService {
     void makeEncode(User user);
     void registerSuperAdmin(User user);
   //  void registerAdmin(User user, Auth auth4);
     void registerAAdmin(User user, Role auth4, Boolean isAdmin, Boolean isUser);
}

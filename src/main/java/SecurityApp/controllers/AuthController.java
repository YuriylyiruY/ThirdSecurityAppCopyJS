package SecurityApp.controllers;

import SecurityApp.models.Auth;
import SecurityApp.models.User;
import SecurityApp.services.RegistrationService;
import SecurityApp.services.RoleService;
import SecurityApp.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final RoleService roleService;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator, RoleService roleService) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.roleService = roleService;
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") User user, @ModelAttribute("ttt") Auth role, Model model) {
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);
        return "registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid User user,
                                      BindingResult bindingResult, Auth role) {
        personValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "registration";
        registrationService.makeEncode(user);
        roleService.addRolesToTable(user);


        //user.setAuths(auth);
        registrationService.registerAdmin(user, role);
        return "redirect:/auth/login";
    }
}

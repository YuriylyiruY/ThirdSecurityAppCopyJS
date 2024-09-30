package SecurityApp.controllers;

import SecurityApp.models.User;
import SecurityApp.security.PersonDetails;
import SecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import SecurityApp.services.PeopleService;
import SecurityApp.services.RegistrationService;
import SecurityApp.util.PersonValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;



@Controller
@RequestMapping("/")
public class HelloController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public HelloController(PeopleService peopleService, PersonValidator personValidator, RegistrationService registrationService, PersonDetailsService personDetailsService) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.personDetailsService = personDetailsService;
    }


    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());

        return "hello";
    }


    @GetMapping("/adminPage")
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "adminPage";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        return "show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") User user) {
        return "new";
    }

    @GetMapping("/newAdmin")
    public String newAdmin(@ModelAttribute("persona") User user) {
        return "admin";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid User user,
                         BindingResult bindingResult) {

        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "new";

        registrationService.register(user);

        peopleService.save(user);
        return "redirect:/adminPage";
    }

    @PostMapping("/a")
    public String createAdmin(@ModelAttribute("persona") @Valid User user,
                              BindingResult bindingResult) {

        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "admin";

        registrationService.registerAdmin(user);

        peopleService.save(user);
        return "redirect:/hello";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid User user, BindingResult bindingResult,
                         HttpServletRequest request) {
        if (bindingResult.hasErrors())
            return "edit";
        int id = Integer.parseInt(request.getParameter("id"));
        peopleService.update(id, user);
        return "redirect:/adminPage";
    }

    @PostMapping("/del/{id}")
    public String delete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        peopleService.delete(id);
        return "redirect:/adminPage";
    }

    @GetMapping("/userPage")
    public String userPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        model.addAttribute("person", personDetails.getPerson());
        return "userPage";
    }
}






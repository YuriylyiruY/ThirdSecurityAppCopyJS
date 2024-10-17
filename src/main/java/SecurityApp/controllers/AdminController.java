package SecurityApp.controllers;

import SecurityApp.models.Auth;
import SecurityApp.models.User;
import SecurityApp.security.PersonDetails;
import SecurityApp.services.UserService;
import SecurityApp.services.PersonDetailsService;
import SecurityApp.services.RegistrationService;
import SecurityApp.services.RoleService;
import SecurityApp.util.PersonValidator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api")
public class AdminController {

    private final UserService peopleService;
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService peopleService, PersonValidator personValidator, RegistrationService registrationService, PersonDetailsService personDetailsService, RoleService roleService) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.roleService = roleService;

    }

    @GetMapping("/users")
    public ModelAndView index(Model model, @ModelAttribute("ttt") Auth auth, @ModelAttribute("userS") User user) {
       // model.addAttribute("people", peopleService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();


        model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);

        ModelAndView modelAndView = new ModelAndView("adminPage");
        modelAndView.addObject("people",peopleService.findAll() );


        return  modelAndView;
    }
////////////////////////////////////////////////////////////////////////////example
    @GetMapping("/example")
    public ModelAndView indexS(Model model, @ModelAttribute("ttt") Auth auth, @ModelAttribute("userS") User user) {
        // model.addAttribute("people", peopleService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();




        ModelAndView modelAndView = new ModelAndView("example");
        modelAndView.addObject("people",peopleService.findAll() );


        return  modelAndView;
    }

    @PutMapping("/example")
    public ModelAndView updateS(@ModelAttribute("person") @Valid User user, BindingResult bindingResult,
                               @ModelAttribute("ttt") Auth auth,Model model,
                               HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("example");
            modelAndView.addObject("people",peopleService.findAll() );


            return  modelAndView;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        registrationService.registerAdmin(user, auth);
        peopleService.update(id, user);
        ModelAndView modelAndView = new ModelAndView("example");
        modelAndView.addObject("people",peopleService.findAll() );


        return  modelAndView;
    }
    @PostMapping("/exampleDel/")
    public ModelAndView deleteS(HttpServletRequest request,Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);
        peopleService.delete(id);



        ModelAndView modelAndView = new ModelAndView("adminPage");
        modelAndView.addObject("people",peopleService.findAll() );


        return  modelAndView;
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/users")
    public ModelAndView create(@ModelAttribute("person") @Valid User user,
                         @ModelAttribute("ttt") Auth auth,Model model,
                         BindingResult bindingResult) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);
        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("adminPage");
            modelAndView.addObject("people",peopleService.findAll() );


            return  modelAndView;

        }

        registrationService.makeEncode(user);
        roleService.addRolesToTable(user);
        System.out.println(auth.getRole());
        System.out.println(auth.getIdForAuth());
        //user.setAuths(auth);
        registrationService.registerAdmin(user, auth);

        peopleService.save(user);
        ModelAndView modelAndView = new ModelAndView("adminPage");
        modelAndView.addObject("people",peopleService.findAll() );


        return  modelAndView;
    }





    @PutMapping("/users")
    public ModelAndView update(@ModelAttribute("person") @Valid User user, BindingResult bindingResult,
                         @ModelAttribute("ttt") Auth auth,Model model,
                         HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("adminPage");
            modelAndView.addObject("people",peopleService.findAll() );


            return  modelAndView;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        registrationService.registerAdmin(user, auth);
        peopleService.update(id, user);
        ModelAndView modelAndView = new ModelAndView("adminPage");
        modelAndView.addObject("people",peopleService.findAll() );


        return  modelAndView;
    }

    @DeleteMapping("/users")
    public ModelAndView delete(HttpServletRequest request,Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);

           peopleService.delete(id);




        ModelAndView modelAndView = new ModelAndView("adminPage");

        modelAndView.addObject("people",peopleService.findAll() );



        return  modelAndView;
    }

    @GetMapping("/adminUserPage")
    public ModelAndView userAdminPage( Model model) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getPerson());
        model.addAttribute("person", personDetails.getPerson());

        ModelAndView modelAndView = new ModelAndView("adminUserPage");
        modelAndView.addObject("person",personDetails.getPerson() );


        return  modelAndView;
    }
    @GetMapping("/adminPage")
    public ModelAndView adminPage( Model model) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);


        ModelAndView modelAndView = new ModelAndView("adminPage");
        modelAndView.addObject("person",peopleService.findAll() );


        return  modelAndView;
    }
    @GetMapping("/adminPageMod")
    public ModelAndView adminUserPage( Model model) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
      // model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);


        ModelAndView modelAndView = new ModelAndView("adminPageMod");
        modelAndView.addObject("user",personDetails.getPerson() );


        return  modelAndView;
    }



    //////////////////////////////////////////////////////////
    @GetMapping("/deleteMod")
    public ModelAndView indexMod(Model model, @ModelAttribute("ttt") Auth auth, @ModelAttribute("userS") User user) {
        // model.addAttribute("people", peopleService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);




        ModelAndView modelAndView = new ModelAndView("deleteMod");
        modelAndView.addObject("people",peopleService.findAll() );


        return  modelAndView;
    }

/////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/createMod")
    public ModelAndView indexModCreate(Model model, @ModelAttribute("ttt") Auth auth, @ModelAttribute("userS") User user) {
        // model.addAttribute("people", peopleService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);




        ModelAndView modelAndView = new ModelAndView("createMod");
        modelAndView.addObject("people",peopleService.findAll() );


        return  modelAndView;
    }





}







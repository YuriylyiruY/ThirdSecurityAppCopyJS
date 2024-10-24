package SecurityApp.controllers;

import SecurityApp.exeptionHandling.NoSuchUser;
import SecurityApp.exeptionHandling.UserIncorData;
import SecurityApp.models.Role;
import SecurityApp.models.User;
import SecurityApp.security.UserDetails;
import SecurityApp.services.UserService;
import SecurityApp.services.UserDetailsService;
import SecurityApp.services.RegistrationService;
import SecurityApp.services.RoleService;
import SecurityApp.util.PersonValidator;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public AdminController(UserService peopleService, PersonValidator personValidator, RegistrationService registrationService, UserDetailsService personDetailsService, RoleService roleService) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.roleService = roleService;


    }

    @GetMapping("/users")
    public List<User> index() {
        List<User> users = peopleService.findAll();
        System.out.println(users);
        String[] str = users.toString().split(" ,");
        List<User> users1 = new ArrayList<>();
        for (User user : users) {
            User usa = new User();
            usa.setName(user.getName());
            usa.setLastName(user.getLastName());
            usa.setEmail(user.getEmail());
            usa.setAge(user.getAge());
            usa.setId(user.getId());
            users1.add(usa);
        }

        if (users1.isEmpty()) {
            throw new NoSuchUser("Incorrect data, Emty");
        }


        return users1;
    }

    @GetMapping("/usersS")
    public ModelAndView indexXS(Model model, @ModelAttribute("ttt") Role auth, @ModelAttribute("userS") User user) {
        model.addAttribute("people", peopleService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();


        model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);

        ModelAndView modelAndView = new ModelAndView("adminPage");
        modelAndView.addObject("people", peopleService.findAll());
        // List<User>  list1 =peopleService.findAll();

        return modelAndView;
    }

    ////////////////////////////////////////////////////////////////////////////example
    @GetMapping("/example")
    public ModelAndView indexS(Model model, @ModelAttribute("ttt") Role auth, @ModelAttribute("userS") User user) {
        // model.addAttribute("people", peopleService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();


        ModelAndView modelAndView = new ModelAndView("example");
        modelAndView.addObject("people", peopleService.findAll());


        return modelAndView;
    }

    @PutMapping("/example")
    public ModelAndView updateS(@ModelAttribute("person") @Valid User user, BindingResult bindingResult,
                                @ModelAttribute("ttt") Role auth, Model model,
                                HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("example");
            modelAndView.addObject("people", peopleService.findAll());


            return modelAndView;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        registrationService.registerAAdmin(user, auth, user.getAdmin(), user.getUser());
        peopleService.update(id, user);
        ModelAndView modelAndView = new ModelAndView("example");
        modelAndView.addObject("people", peopleService.findAll());


        return modelAndView;
    }


    @PostMapping("/exampleDel/")
    public ModelAndView deleteS(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);
        peopleService.delete(id);


        ModelAndView modelAndView = new ModelAndView("adminPage");
        modelAndView.addObject("people", peopleService.findAll());


        return modelAndView;
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/users")
    public String create(@RequestBody @Valid User userK,
                         // @ModelAttribute("person")  User user,
                         // @ModelAttribute("ttt")
                         Role auth,
                         Model model,
                         BindingResult bindingResult) {

        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH" + userK);
        //   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        // model.addAttribute("user", personDetails.getPerson());
        //    List<String> list = Arrays.asList("ADMIN", "USER");
//
//        model.addAttribute("list", list);
        personValidator.validate(userK, bindingResult);
        if (bindingResult.hasErrors()) {
            //     ModelAndView modelAndView = new ModelAndView("adminPage");
            //   modelAndView.addObject("people",peopleService.findAll() );

            throw new NoSuchUser("Incorrect data, try again");


        }

        registrationService.makeEncode(userK);
        roleService.addRolesToTable(userK);

        //  System.out.println(auth.getRole());
        //  System.out.println(auth.getIdForAuth());
        //user.setAuths(auth);
        System.out.println("admincontroller   " + userK.getAdmin());
        System.out.println(userK);
        registrationService.registerAAdmin(userK, auth, userK.getAdmin(), userK.getUser());


        peopleService.save(userK);
        //model.addAttribute("user",user );
        //  ModelAndView modelAndView = new ModelAndView("adminPage");
        //  modelAndView.addObject("people",peopleService.findAll() );


        return "send";
    }


    @PostMapping("/usersS")
    public ModelAndView createXS(@RequestBody @Valid User user,
                                 // @ModelAttribute("person")  User user,
                                 // @ModelAttribute("ttt")
                                 Role auth,
                                 Model model,
                                 BindingResult bindingResult) {

        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH" + user);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        // model.addAttribute("user", personDetails.getPerson());
        //    List<String> list = Arrays.asList("ADMIN", "USER");
//
//        model.addAttribute("list", list);
        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new NoSuchUser("Incorrect data, try again");

        }

        registrationService.makeEncode(user);
        roleService.addRolesToTable(user);

        //  System.out.println(auth.getRole());
        //  System.out.println(auth.getIdForAuth());
        //user.setAuths(auth);
        System.out.println("admincontroller   " + user.getAdmin());
        System.out.println(user);
        registrationService.registerAAdmin(user, auth, user.getAdmin(), user.getUser());


        peopleService.save(user);
        model.addAttribute("user", user);
        ModelAndView modelAndView = new ModelAndView("adminPage");
        modelAndView.addObject("people", peopleService.findAll());


        return modelAndView;
    }


    @PutMapping("/users")
    public String update(@RequestBody @Valid User user,
                          Role auth
    ) {



        int id = user.getId();

        registrationService.makeEncode(user);
        registrationService.registerAAdmin(user, auth, user.getAdmin(), user.getUser());
        peopleService.update(id, user);

        User user1 = peopleService.findOne(id);
        if (user1==null) {
            throw new NoSuchUser("Incorrect data, Emty");
        }
        return user1.toString();
    }

    @PutMapping("/usersS")
    public ModelAndView updateXS(@RequestBody @Valid User user, BindingResult bindingResult,
                                 @ModelAttribute("ttt") Role auth, Model model
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("adminPage");
            modelAndView.addObject("people", peopleService.findAll());


            return modelAndView;
        }

        int id = user.getId();
        registrationService.makeEncode(user);
        registrationService.registerAAdmin(user, auth, user.getAdmin(), user.getUser());
        peopleService.update(id, user);
        ModelAndView modelAndView = new ModelAndView("adminPage");
        modelAndView.addObject("people", peopleService.findAll());


        return modelAndView;
    }

    @DeleteMapping("/users")
    public String delete(@RequestBody User user, Model model) {
        int id = user.getId();
        System.out.println(user);


        peopleService.delete(id);


        return "Delete";
    }

    @DeleteMapping("/usersS")
    public ModelAndView deleteXS(@RequestBody User user, Model model) {
        int id = user.getId();
        System.out.println(user);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", user);
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);

        peopleService.delete(id);


        ModelAndView modelAndView = new ModelAndView("adminPage");

        modelAndView.addObject("people", peopleService.findAll());


        return modelAndView;
    }

    @GetMapping("/adminUserPage")
    public ModelAndView userAdminPage(Model model) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getPerson());
        model.addAttribute("person", personDetails.getPerson());

        ModelAndView modelAndView = new ModelAndView("adminUserPage");
        modelAndView.addObject("person", personDetails.getPerson());


        return modelAndView;
    }

    @GetMapping("/adminPage")
    public ModelAndView adminPage(Model model) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);


        ModelAndView modelAndView = new ModelAndView("adminPage");
        modelAndView.addObject("person", peopleService.findAll());


        return modelAndView;
    }

    @GetMapping("/adminPageMod")
    public ModelAndView adminUserPage(Model model) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        // model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);


        ModelAndView modelAndView = new ModelAndView("adminPageMod");
        modelAndView.addObject("user", personDetails.getPerson());


        return modelAndView;
    }


    //////////////////////////////////////////////////////////
    @GetMapping("/deleteMod")
    public ModelAndView indexMod(Model model, @ModelAttribute("ttt") Role auth, @ModelAttribute("userS") User user) {
        // model.addAttribute("people", peopleService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);


        ModelAndView modelAndView = new ModelAndView("deleteMod");
        modelAndView.addObject("people", peopleService.findAll());


        return modelAndView;
    }

/////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/createMod")
    public ModelAndView indexModCreate(Model model, @ModelAttribute("ttt") Role auth, @ModelAttribute("userS") User user) {
        // model.addAttribute("people", peopleService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);


        ModelAndView modelAndView = new ModelAndView("createMod");
        modelAndView.addObject("people", peopleService.findAll());


        return modelAndView;
    }




}







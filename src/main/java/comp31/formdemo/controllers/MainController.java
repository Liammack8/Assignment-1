package comp31.formdemo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import comp31.formdemo.model.Employee;
import comp31.formdemo.services.LoginService;

@Controller
public class MainController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    LoginService loginService;

    public MainController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/")
    String getRoot(Model model) {
        logger.info("---- At root.");
        Employee employee = new Employee(); // Create backing object and
        model.addAttribute("employee", employee); // send it to login form
        return "login-form";
    }

    @PostMapping("/login")
    public String getForm(Employee employee, Model model) {
        logger.info("---- At /login.");
        logger.info("---- " + employee.toString());

        return loginService.login(employee, model);
    }

    @GetMapping({"/admin" ,"add-employee"})
    public String getEmployee(Model model) { 
        model.addAttribute("employee", new Employee());
        return "departments/admin";
    }

    @PostMapping("/add-employee")
    public String postEmployee(Model model, Employee employee) {
        loginService.addEmployee(employee);
        model.addAttribute("employee", new Employee());
        return "redirect:/add-employee"; 
    }

}

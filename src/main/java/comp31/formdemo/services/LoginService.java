package comp31.formdemo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import comp31.formdemo.model.Employee;
import comp31.formdemo.repositories.Accounts;

@Service
public class LoginService {

    Logger logger = LoggerFactory.getLogger(LoginService.class);

    Accounts accounts;

    public LoginService(Accounts accounts) {
        this.accounts = accounts;

        String[] userIds = {"sam", "sally", "ollie", "olivia", "rachel", "ralph", "abbie", "arthur"};
        String[] firstName = {"sam", "sally", "ollie", "olivia", "rachel", "ralph", "abbie", "arthur"};
        String[] lastName = {"smith", "mae", "clarke", "wilde", "johnson", "hugh", "song", "mccloud"};
        String[] password = {"sam", "sally", "ollie", "olivia", "rachel", "ralph", "abbie", "arthur"};
        String[] department = {"admin", "sales", "orders", "repairs", "sales", "admin", "orders", "repairs"};
        

    for (int i = 0; i < userIds.length; i++) {
            addEmployee(
                userIds[i], 
                firstName[i], 
                lastName[i], 
                password[i], 
                department[i]
            );
        }
    }

    public void addEmployee(String userId, String firstName, 
                    String lastName, String password, String department) {

        logger.info("Adding user: " + userId);
        Employee employee = new Employee();
        employee.setUserId(userId);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setPassword(password);
        employee.setDepartment(department);

        accounts.add(employee);
    }

    public void addEmployee(Employee employee) {
        accounts.add(employee);
    }

    public Employee findByUserId(String userId) {
        return accounts.findByUserId(userId);
    }

    public String login(Employee employee, Model model) {
        Employee currentUser = findByUserId(employee.getUserId());
        String returnPage = "login-form";
        if (currentUser == null) {
            model.addAttribute("employee", employee);
            returnPage = "login-form";
        } else {
            if(currentUser.getUserId().equals(employee.getUserId()) && 
                currentUser.getPassword().equals(employee.getPassword())) {
                
                if(currentUser.getDepartment().equals("admin")) {
                    model.addAttribute("employee", employee);
                    returnPage = "departments/" +  currentUser.getDepartment();
                }
                returnPage = "departments/" + currentUser.getDepartment();     
            } else
                returnPage = "login-form";    
        }
        return returnPage;
    }

}

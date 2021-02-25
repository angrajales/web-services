package co.com.realistic.app.employee.employeems.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("co.com.realistic.app.employee.employeems")
public class EmployeeMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeMsApplication.class, args);
    }
}

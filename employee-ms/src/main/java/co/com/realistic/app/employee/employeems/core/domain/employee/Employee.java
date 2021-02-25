package co.com.realistic.app.employee.employeems.core.domain.employee;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Data
public class Employee {
    private String name;
    private String lastname;
    private String documentType;
    private String documentNumber;
    private LocalDate birthDate;
    private LocalDate entailmentDate;
    private String role;
    private double salary;
}
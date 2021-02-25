package co.com.realistic.app.employee.employeems.core.domain.employee.repository;

import co.com.realistic.app.employee.employeems.core.domain.employee.Employee;
import co.com.realistic.app.employee.employeems.core.domain.employee.EmployeeInterestingAttributes;
import reactor.core.publisher.Mono;

public interface EmployeeRepository {
    Mono<EmployeeInterestingAttributes> addEmployee(Employee employee);
}
package co.com.realistic.app.employee.employeems.core.usecases.employee;

import co.com.realistic.app.employee.employeems.core.domain.employee.Employee;
import co.com.realistic.app.employee.employeems.core.domain.employee.EmployeeInterestingAttributes;
import co.com.realistic.app.employee.employeems.core.domain.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class EmployeeUseCase {
    private final EmployeeRepository repository;

    public Mono<EmployeeInterestingAttributes> addEmployee(Employee employee) {
        return repository
                .addEmployee(employee)
                .doOnSuccess(e -> log.info("The employee identified by " + employee.getDocumentNumber() + " has been created."))
                .doOnError(e -> log.error("The employee identified by " + employee.getDocumentNumber() + " couldn't be added because " + e.getMessage()));

    }
}

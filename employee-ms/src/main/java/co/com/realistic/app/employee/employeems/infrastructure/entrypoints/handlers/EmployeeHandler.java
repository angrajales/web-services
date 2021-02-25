package co.com.realistic.app.employee.employeems.infrastructure.entrypoints.handlers;

import co.com.realistic.app.employee.employeems.core.domain.employee.Employee;
import co.com.realistic.app.employee.employeems.core.domain.employee.repository.EmployeeRepository;
import co.com.realistic.app.employee.employeems.infrastructure.entrypoints.models.ErrorModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class EmployeeHandler {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final EmployeeRepository repository;

    public Mono<ServerResponse> addEmployee(ServerRequest request) {
        String name = request.queryParam("name").orElse(null);
        String lastname = request.queryParam("lastname").orElse(null);
        String documentType = request.queryParam("document_type").orElse(null);
        String documentNumber = request.queryParam("document_number").orElse(null);
        String birthDate = request.queryParam("birth_date").orElse(null);
        String entailmentDate = request.queryParam("entailment_date").orElse(null);
        String role = request.queryParam("role").orElse(null);
        int salary = Integer.parseInt(request.queryParam("salary").orElse("0"));
        return Mono.just(
                Employee
                        .builder()
                        .name(name)
                        .lastname(lastname)
                        .documentType(documentType)
                        .documentNumber(documentNumber)
                        .birthDate(LocalDate.parse(birthDate, FORMATTER))
                        .entailmentDate(LocalDate.parse(entailmentDate, FORMATTER))
                        .role(role)
                        .salary(salary)
                        .build()
        )
                .log()
                .flatMap(repository::addEmployee)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(e))
                .switchIfEmpty(
                        ServerResponse
                                .status(HttpStatus.NOT_MODIFIED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(buildGenericErrorResponse()))
                .onErrorResume(e -> ServerResponse
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buildGenericErrorResponse()));
    }

    private ErrorModel buildGenericErrorResponse() {
        return ErrorModel
                .builder()
                .code("CC001")
                .reason("Error creating an employee")
                .message("The employee couldn't be created")
                .status("Employee not created")
                .build();
    }
}

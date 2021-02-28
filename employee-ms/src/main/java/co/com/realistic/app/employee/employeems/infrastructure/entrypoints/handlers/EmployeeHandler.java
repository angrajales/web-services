package co.com.realistic.app.employee.employeems.infrastructure.entrypoints.handlers;

import co.com.realistic.app.employee.employeems.core.domain.employee.Employee;
import co.com.realistic.app.employee.employeems.core.domain.employee.repository.EmployeeRepository;
import co.com.realistic.app.employee.employeems.infrastructure.entrypoints.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
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
        log.info(name);
        log.info(lastname);
        log.info(documentNumber);
        log.info(documentType);
        log.info(birthDate);
        log.info(entailmentDate);
        log.info(role);
        log.info(salary+"");
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
                        .bodyValue(this.buildCorrectResponse("/create", e)))
                .switchIfEmpty(
                        ServerResponse
                                .status(HttpStatus.NOT_MODIFIED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(buildGenericErrorResponse("/create")))
                .onErrorResume(e -> ServerResponse
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buildGenericErrorResponse("/creat")));
    }

    public Mono<ServerResponse> getEmployee(ServerRequest request) {
        String documentType = request.queryParam("document_type").orElse(null);
        String documentNumber = request.queryParam("document_number").orElse(null);
        log.info(documentNumber);
        log.info(documentType);
        Map<String, String> data = new HashMap<>();
        data.put("document_type", documentType);
        data.put("document_number", documentNumber);
        return Mono.just(
                data
        )
                .log()
                .flatMap(repository::getEmployee)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(this.buildCorrectResponse("/get", e)))
                .switchIfEmpty(
                        ServerResponse
                                .status(HttpStatus.NOT_MODIFIED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(buildGenericErrorResponse("/get")))
                .onErrorResume(e -> ServerResponse
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buildGenericErrorResponse("/get")));
    }

    private <T> Data buildCorrectResponse(String service, T employee) {
        return Data
                .builder()
                .data(Collections.singletonList(
                        DataElement
                                .builder()
                                .metadata(metadata(service))
                                .response(Response
                                        .<T>builder()
                                        .employee(employee)
                                        .build())
                                .build()
                ))
                .build();
    }

    private Errors buildGenericErrorResponse(String service) {
        return Errors
                .builder()
                .errors(Collections.singletonList(
                        ErrorsElement
                                .builder()
                                .metadata(metadata(service))
                                .error(ErrorModel
                                        .builder()
                                        .code("CC001")
                                        .reason("Error trying to execute the request")
                                        .message("The operation could not be executed")
                                        .status("Operation not executed")
                                        .build())
                                .build()
                ))
                .build();
    }

    private Metadata metadata(String service) {
        String id = UUID.randomUUID().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        return Metadata
                .builder()
                .id(id)
                .source(service)
                .date(formatter.format(LocalDateTime.now()))
                .build();
    }
}

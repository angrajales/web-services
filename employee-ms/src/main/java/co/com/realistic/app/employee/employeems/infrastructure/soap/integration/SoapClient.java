package co.com.realistic.app.employee.employeems.infrastructure.soap.integration;

import co.com.realistic.app.employee.employeems.core.domain.employee.Employee;
import co.com.realistic.app.employee.employeems.core.domain.employee.EmployeeInterestingAttributes;
import co.com.realistic.app.employee.employeems.core.domain.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import soap.SoapAddEmployeeRequest;
import soap.SoapAddEmployeeResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Slf4j
public class SoapClient implements EmployeeRepository {
    private final WebClient webClient;
    private final String soapServiceUrl;
    private static final String ERROR_MESSAGE = "Error creating the given employee";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final LocalDate NOW = LocalDate.now();

    public Mono<EmployeeInterestingAttributes> addEmployee(Employee employee) {
        Mono<SoapAddEmployeeRequest> soapAddEmployeeRequest = Mono.just(SoapAddEmployeeRequest
                .builder()
                .name(employee.getName())
                .lastname(employee.getLastname())
                .documentType(employee.getDocumentType())
                .documentNumber(employee.getDocumentNumber())
                .birthDate(dateToString(employee.getBirthDate()))
                .entailmentDate(dateToString(employee.getEntailmentDate()))
                .role(employee.getRole())
                .salary(employee.getSalary())
                .build());
        return webClient
                .post()
                .uri(soapServiceUrl)
                .header("SOAP")
                .body(soapAddEmployeeRequest, SoapAddEmployeeRequest.class)
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        clientResponse ->
                                clientResponse.bodyToMono(String.class)
                                        .flatMap(errorResponseBody ->
                                                Mono.error(new ResponseStatusException(
                                                        clientResponse.statusCode(),
                                                        errorResponseBody
                                                )))
                ).bodyToMono(SoapAddEmployeeResponse.class)
                .log()
                .doOnSuccess((SoapAddEmployeeResponse response) -> {
                    log.info("Success " + response.isSuccess());
                    log.info("EntailmentDate " + response.getEntailmentDate());
                    log.info("BirthDate " + response.getBirthDate());
                    log.info("Message " + response.getMessage());
                })
                .log()
                .flatMap(this::convertToInterestingAttributesResponse);
    }


    private Mono<EmployeeInterestingAttributes> convertToInterestingAttributesResponse(
            SoapAddEmployeeResponse response) {
        if (!response.isSuccess()) {
            throw new RuntimeException(ERROR_MESSAGE);
        }
        LocalDate birthDate = stringToDate(response.getBirthDate());
        LocalDate entailmentDate = stringToDate(response.getEntailmentDate());
        return
                Mono.just(
                        EmployeeInterestingAttributes
                                .builder()
                                .birthDateYear(getYear(birthDate))
                                .birthDateMonth(getMonth(birthDate))
                                .birthDateDay(getDay(birthDate))
                                .entailmentDateDay(getDay(entailmentDate))
                                .entailmentDateMonth(getMonth(entailmentDate))
                                .entailmentDateYear(getYear(entailmentDate))
                                .build()
                );
    }

    private int getYear(LocalDate date) {
        return NOW.getYear() - date.getYear();
    }

    private int getMonth(LocalDate date) {
        return NOW.getMonthValue() - date.getMonthValue();
    }

    private int getDay(LocalDate date) {
        return NOW.getDayOfMonth() - date.getDayOfMonth();
    }

    private String dateToString(LocalDate localDate) {
        return FORMATTER.format(localDate);
    }

    private LocalDate stringToDate(String date) {
        return LocalDate.parse(date, FORMATTER);
    }
}

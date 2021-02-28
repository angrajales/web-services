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
import soap.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class SoapClient implements EmployeeRepository {
    private final WebClient webClient;
    private final String soapServiceUrl;
    private static final String ERROR_MESSAGE = "Error doing the soap request";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final LocalDate NOW = LocalDate.now();

    public Mono<EmployeeInterestingAttributes> addEmployee(Employee employee) {
        Mono<SoapAddEmployeeRequest> soapAddEmployeeRequest = Mono.just(getAddEmployeeRequest(employee));
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
                    log.info("Success " + response.getResponseStatus().isStatus());
                    log.info("Message " + response.getResponseStatus().getMessage());
                })
                .log()
                .flatMap(this::convertToInterestingAttributesResponse);
    }

    public Mono<Employee> getEmployee(Map<String, String> attributes) {
        Mono<SoapGetEmployeeRequest> soapAddEmployeeRequest = Mono.just(getEmployeeRequest(attributes));
        return webClient
                .post()
                .uri(soapServiceUrl)
                .header("SOAP")
                .body(soapAddEmployeeRequest, SoapGetEmployeeRequest.class)
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
                ).bodyToMono(SoapGetEmployeeResponse.class)
                .log()
                .doOnSuccess((SoapGetEmployeeResponse response) -> {
                    log.info("Success " + response.getResponseStatus().isStatus());
                    log.info("Message " + response.getResponseStatus().getMessage());
                })
                .log()
                .flatMap(this::convertToEmployee);
    }

    private SoapGetEmployeeRequest getEmployeeRequest(Map<String, String> attributes) {
        return SoapGetEmployeeRequest
                .builder()
                .identification(SoapEmployeeIdentification
                        .builder()
                        .documentType(attributes.get("document_type"))
                        .documentNumber(attributes.get("document_number"))
                        .build())
                .build();

    }

    private SoapAddEmployeeRequest getAddEmployeeRequest(Employee employee) {
        return SoapAddEmployeeRequest
                .builder()
                .employee(SoapEmployee
                        .builder()
                        .personalInformation(SoapPersonalInformation
                                .builder()
                                .name(employee.getName())
                                .lastname(employee.getLastname())
                                .birthDate(dateToString(employee.getBirthDate()))
                                .build())
                        .additionalInformation(SoapAdditionalInformation
                                .builder()
                                .role(employee.getRole())
                                .salary(employee.getSalary())
                                .entailmentDate(dateToString(employee.getEntailmentDate()))
                                .build())
                        .identification(SoapEmployeeIdentification
                                .builder()
                                .documentNumber(employee.getDocumentNumber())
                                .documentType(employee.getDocumentType())
                                .build())
                        .build())
                .build();
    }


    private Mono<Employee> convertToEmployee(SoapGetEmployeeResponse response) {
        if (!response.getResponseStatus().isStatus()) {
            throw new RuntimeException(ERROR_MESSAGE);
        }
        SoapPersonalInformation personalInfo = response.getEmployee().getPersonalInformation();
        SoapAdditionalInformation addInfo = response.getEmployee().getAdditionalInformation();
        SoapEmployeeIdentification identification = response.getEmployee().getIdentification();
        return Mono.just(Employee
                .builder()
                .birthDate(stringToDate(personalInfo.getBirthDate()))
                .name(personalInfo.getName())
                .lastname(personalInfo.getLastname())
                .documentNumber(identification.getDocumentNumber())
                .documentType(identification.getDocumentType())
                .role(addInfo.getRole())
                .salary(addInfo.getSalary())
                .entailmentDate(stringToDate(addInfo.getEntailmentDate()))
                .build());
    }

    private Mono<EmployeeInterestingAttributes> convertToInterestingAttributesResponse(
            SoapAddEmployeeResponse response) {
        if (!response.getResponseStatus().isStatus()) {
            throw new RuntimeException(ERROR_MESSAGE);
        }
        SoapCommonInterestingInformation birthDate = response
                .getInterestingEmployeeInformation()
                .getBirthDateInformation()
                .getBirthDate();
        SoapCommonInterestingInformation entailmentDate = response
                .getInterestingEmployeeInformation()
                .getEntailmentInformation()
                .getEntailmentDate();
        return
                Mono.just(
                        EmployeeInterestingAttributes
                                .builder()
                                .birthDateYear(birthDate.getYear())
                                .birthDateMonth(birthDate.getMonth())
                                .birthDateDay(birthDate.getDay())
                                .entailmentDateDay(entailmentDate.getDay())
                                .entailmentDateMonth(entailmentDate.getMonth())
                                .entailmentDateYear(entailmentDate.getYear())
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

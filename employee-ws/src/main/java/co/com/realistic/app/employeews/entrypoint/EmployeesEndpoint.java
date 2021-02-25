package co.com.realistic.app.employeews.entrypoint;

import co.com.realistic.app.employeews.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.SoapAddEmployeeRequest;
import soap.SoapAddEmployeeResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Endpoint
@Slf4j
@RequiredArgsConstructor
public class EmployeesEndpoint {
    public static final String NAMESPACE_URI = "http://www.realistic-example.org/employee-ws";
    private final EmployeeService service;
    @Value("${business.data.min-required-age}")
    private int minRequiredAge;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addEmployeeRequest")
    @ResponsePayload
    public SoapAddEmployeeResponse addEmployee(@RequestPayload SoapAddEmployeeRequest request) {
        log.info(request.getName());
        log.info(request.getLastname());
        log.info(request.getDocumentType());
        log.info(request.getDocumentNumber());
        log.info(request.getBirthDate());
        log.info(request.getEntailmentDate());
        log.info(request.getRole());
        log.info(request.getSalary() + "");
        LocalDate birthDate = validateDateFormat(request.getBirthDate());
        LocalDate entailmentDate = validateDateFormat(request.getEntailmentDate());
        int age = getCurrentEmployeeAge(birthDate);
        boolean preValidationsOK = birthDate != null && entailmentDate != null && age >= minRequiredAge;
        Pair<String, Boolean> result = Pair.of("Invalid attributes", false);
        if (preValidationsOK) {
            result = service.addEmployeeToDatabase(
                    request.getName(),
                    request.getLastname(),
                    request.getDocumentType(),
                    request.getDocumentNumber(),
                    birthDate,
                    entailmentDate,
                    request.getRole(),
                    request.getSalary());
        }
        return SoapAddEmployeeResponse
                .builder()
                .success(preValidationsOK && result.getSecond())
                .message(result.getFirst())
                .entailmentDate(request.getEntailmentDate())
                .birthDate(request.getBirthDate())
                .build();
    }

    private LocalDate validateDateFormat(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(date, formatter);
        } catch (Exception ex) {
            log.error("Error trying to convert date " + ex.getMessage());
            return null;
        }
    }

    private int getCurrentEmployeeAge(LocalDate birthDate) {
        return LocalDate.now()
                .getYear() - birthDate.getYear();
    }
}

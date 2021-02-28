package co.com.realistic.app.employeews.entrypoint;

import co.com.realistic.app.employeews.entities.Employee;
import co.com.realistic.app.employeews.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Endpoint
@Slf4j
@RequiredArgsConstructor
public class EmployeesEndpoint {
    public static final String NAMESPACE_URI = "http://www.realistic-example.org/employee-ws";
    private static final LocalDate NOW = LocalDate.now();
    private final EmployeeService service;
    @Value("${business.data.min-required-age}")
    private int minRequiredAge;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addEmployeeRequest")
    @ResponsePayload
    public SoapAddEmployeeResponse addEmployee(@RequestPayload SoapAddEmployeeRequest request) {
        SoapEmployee employee = request.getEmployee();
        SoapAdditionalInformation addInfo = employee.getAdditionalInformation();
        SoapPersonalInformation personalInfo = employee.getPersonalInformation();
        SoapEmployeeIdentification identification = employee.getIdentification();
        LocalDate birthDate = validateDateFormat(personalInfo.getBirthDate());
        LocalDate entailmentDate = validateDateFormat(addInfo.getEntailmentDate());
        int age = getCurrentEmployeeAge(birthDate);
        boolean preValidationsOK = birthDate != null && entailmentDate != null && age >= minRequiredAge;
        Pair<String, Boolean> result = Pair.of("Invalid attributes", false);
        if (preValidationsOK) {
            result = service.addEmployeeToDatabase(
                    personalInfo.getName(),
                    personalInfo.getLastname(),
                    identification.getDocumentType(),
                    identification.getDocumentNumber(),
                    birthDate,
                    entailmentDate,
                    addInfo.getRole(),
                    addInfo.getSalary());
        }
        return SoapAddEmployeeResponse
                .builder()
                .responseStatus(SoapResponseStatus
                        .builder()
                        .status(preValidationsOK && result.getSecond())
                        .message(result.getFirst())
                        .build())
                .interestingEmployeeInformation(soapInterestingEmployeeInformation(birthDate, entailmentDate))
                .build();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmployeeRequest")
    @ResponsePayload
    public SoapGetEmployeeResponse getEmployee(@RequestPayload SoapGetEmployeeRequest request) {
        log.info(request.getIdentification().getDocumentNumber());
        log.info(request.getIdentification().getDocumentType());
        SoapEmployeeIdentification identification = request.getIdentification();
        EmployeeService.EmployeePair result = service.findByDocumentNumber(
                identification.getDocumentNumber(),
                identification.getDocumentType()
        );
        if (result.first == null) {
            return SoapGetEmployeeResponse
                    .builder()
                    .responseStatus(
                            SoapResponseStatus
                                    .builder()
                                    .status(false)
                                    .message(result.second)
                                    .build()
                    )
                    .build();
        }
        return soapGetEmployeeResponse(result.first, result.second);
    }

    private SoapGetEmployeeResponse soapGetEmployeeResponse(Employee employee, String message) {
        return SoapGetEmployeeResponse
                .builder()
                .responseStatus(SoapResponseStatus
                        .builder()
                        .message(message)
                        .status(true)
                        .build())
                .employee(SoapEmployee
                        .builder()
                        .identification(SoapEmployeeIdentification
                                .builder()
                                .documentNumber(employee.getDocumentNumber())
                                .documentType(employee.getDocumentType())
                                .build())
                        .personalInformation(SoapPersonalInformation
                                .builder()
                                .birthDate(dateToString(employee.getBirthDate()))
                                .lastname(employee.getLastname())
                                .name(employee.getName())
                                .build())
                        .additionalInformation(SoapAdditionalInformation
                                .builder()
                                .role(employee.getRole())
                                .salary(employee.getSalary())
                                .entailmentDate(dateToString(employee.getEntailmentDate()))
                                .build())
                        .build())
                .build();
    }

    private SoapInterestingEmployeeInformation soapInterestingEmployeeInformation(LocalDate birthDate,
                                                                                  LocalDate entailmentDate) {
        return SoapInterestingEmployeeInformation
                .builder()
                .birthDateInformation(SoapBirthDateInterestingInformation
                        .builder()
                        .birthDate(soapCommonInterestingInformation(birthDate))
                        .build())
                .entailmentInformation(SoapEntailmentInterestingInformation
                        .builder()
                        .entailmentDate(soapCommonInterestingInformation(entailmentDate))
                        .build())
                .build();
    }

    private SoapCommonInterestingInformation soapCommonInterestingInformation(LocalDate localDate) {
        return SoapCommonInterestingInformation
                .builder()
                .day(NOW.getDayOfMonth() - localDate.getDayOfMonth())
                .month(NOW.getMonthValue() - localDate.getMonthValue())
                .year(NOW.getYear() - localDate.getYear())
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

    private String dateToString(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(localDate);
    }
}

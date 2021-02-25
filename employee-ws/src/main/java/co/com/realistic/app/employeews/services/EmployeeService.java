package co.com.realistic.app.employeews.services;

import co.com.realistic.app.employeews.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.script.ScriptException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository repository;

    public Pair<String, Boolean> addEmployeeToDatabase(String name, String lastname, String documentType, String documentNumber, LocalDate birthDate, LocalDate entailmentDate, String role, double salary) {
        String message = null;
        boolean success = false;
        try {
            repository.addEmployee(name,
                    lastname,
                    documentType,
                    documentNumber,
                    birthDate,
                    entailmentDate,
                    role,
                    salary);
            message = "Employee added successfully";
            success = true;
        } catch (ScriptException ex) {
            log.error("Error in the SQL statement: " + ex.getMessage());
            message = ex.getMessage();
        } catch (RecoverableDataAccessException ex) {
            log.error("Error retrieving data: " + ex.getMessage());
            message = ex.getMessage();
        } catch (NonTransientDataAccessException ex) {
            log.error("No transient data error " + ex.getMessage());
            message = ex.getMessage();
        } catch (Exception ex) {
            log.error("Uncaught exception during the request to SQL");
            message = "The client could not be added to the database";
        }
        return Pair.of(message, success);
    }
}

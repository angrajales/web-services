package co.com.realistic.app.employeews.repositories;

import co.com.realistic.app.employeews.entities.Employee;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.script.ScriptException;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tb_employees " +
            "(name, " +
            "lastname, " +
            "document_type, " +
            "document_number, " +
            "birth_date, " +
            "entailment_date, " +
            "role, " +
            "salary) VALUES " +
            "(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)", nativeQuery = true )
    void addEmployee(String name,
                     String lastname,
                     String documentType,
                     String documentNumber,
                     LocalDate birthDate,
                     LocalDate entailmentDate,
                     String role,
                     double salary)
            throws TransientDataAccessException,
            ScriptException,
            RecoverableDataAccessException,
            NonTransientDataAccessException;
    @Query(value = "SELECT * FROM tb_employees WHERE document_number=$1", nativeQuery = true)
    Employee findByDocumentNumber(String documentNumber);
}

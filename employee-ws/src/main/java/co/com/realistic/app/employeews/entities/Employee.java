package co.com.realistic.app.employeews.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "tb_employee")
@AllArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @Column(name = "document_number")
    private String documentNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "document_type")
    private String documentType;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "entailment_date")
    private LocalDate entailmentDate;
    @Column(name = "role")
    private String role;
    @Column(name = "salary")
    private double salary;
}
package soap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "name",
        "lastname",
        "documentType",
        "documentNumber",
        "birthDate",
        "entailmentDate",
        "role",
        "salary"
})
@XmlRootElement(name = "addEmployeeRequest")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SoapAddEmployeeRequest {
    private String name;
    private String lastname;
    private String documentType;
    private String documentNumber;
    private String birthDate;
    private String entailmentDate;
    private String role;
    private double salary;
}

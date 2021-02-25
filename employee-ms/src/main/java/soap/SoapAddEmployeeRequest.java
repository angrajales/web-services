package soap;

import lombok.*;

import javax.xml.bind.annotation.*;

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
@Builder(toBuilder = true)
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

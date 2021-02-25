package soap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "success",
        "message",
        "entailmentDate",
        "birthDate"
})
@XmlRootElement(name = "addEmployeeResponse")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class SoapAddEmployeeResponse {
    private boolean success;
    private String message;
    private String entailmentDate;
    private String birthDate;
}

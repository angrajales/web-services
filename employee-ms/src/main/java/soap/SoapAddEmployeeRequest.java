package soap;

import lombok.*;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "employee"
})
@XmlRootElement(name = "addEmployeeRequest")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class SoapAddEmployeeRequest {
    @XmlElement(required = true)
    private SoapEmployee employee;
}
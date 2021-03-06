package soap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class SoapAddEmployeeRequest {
    @XmlElement(required = true)
    private SoapEmployee employee;
}
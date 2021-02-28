package soap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "employee",
        "responseStatus"
})
@XmlRootElement(name = "employeeInformation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class SoapGetEmployeeResponse {
    private SoapEmployee employee;
    @XmlElement(required = true)
    private SoapResponseStatus responseStatus;
}

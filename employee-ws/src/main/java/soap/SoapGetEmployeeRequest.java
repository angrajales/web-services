package soap;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "identification"
})
@XmlRootElement(name = "getEmployeeRequest")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SoapGetEmployeeRequest {
    @XmlElement(required = true)
    private SoapEmployeeIdentification identification;
}

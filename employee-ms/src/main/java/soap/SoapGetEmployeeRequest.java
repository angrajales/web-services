package soap;


import lombok.*;

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
@Builder(toBuilder = true)
public class SoapGetEmployeeRequest {
    @XmlElement(required = true)
    private SoapEmployeeIdentification identification;
}

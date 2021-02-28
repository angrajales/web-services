package soap;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "identification", propOrder = {
        "documentType",
        "documentNumber",
})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class SoapEmployeeIdentification {
    @XmlElement(required = true)
    private String documentType;
    @XmlElement(required = true)
    private String documentNumber;
}

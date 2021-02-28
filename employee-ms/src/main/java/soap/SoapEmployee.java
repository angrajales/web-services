package soap;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "employee", propOrder = {
        "identification",
        "personalInformation",
        "additionalInformation"
})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class SoapEmployee {
    @XmlElement(required = true)
    private SoapEmployeeIdentification identification;
    @XmlElement(required = true)
    private SoapPersonalInformation personalInformation;
    @XmlElement(required = true)
    private SoapAdditionalInformation additionalInformation;
}
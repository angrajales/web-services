package soap;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "additionalInformation", propOrder = {
        "role",
        "salary",
        "entailmentDate"
})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class SoapAdditionalInformation {
    @XmlElement(required = true)
    private String role;
    @XmlElement(required = true)
    private double salary;
    @XmlElement(required = true)
    private String entailmentDate;
}

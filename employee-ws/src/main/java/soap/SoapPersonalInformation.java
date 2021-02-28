package soap;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personalInformation", propOrder = {
        "name",
        "lastname",
        "birthDate"
})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class SoapPersonalInformation {
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String lastname;
    @XmlElement(required = true)
    private String birthDate;
}

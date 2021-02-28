package soap;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "interestingEmployeeInformation", propOrder = {
        "entailmentInformation",
        "birthDateInformation"
})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class SoapInterestingEmployeeInformation {
    @XmlElement(required = true)
    private SoapEntailmentInterestingInformation entailmentInformation;
    @XmlElement(required = true)
    private SoapBirthDateInterestingInformation birthDateInformation;
}

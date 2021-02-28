package soap;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entailmentInterestingInformation", propOrder = {
        "entailmentDate",
})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class SoapEntailmentInterestingInformation {
    @XmlElement(required = true)
    private SoapCommonInterestingInformation entailmentDate;
}

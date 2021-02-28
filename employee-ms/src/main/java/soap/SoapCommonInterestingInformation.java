package soap;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "commonInterestingInformation", propOrder = {
        "day",
        "month",
        "year"
})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class SoapCommonInterestingInformation {
    @XmlElement(required = true)
    private int day;
    @XmlElement(required = true)
    private int month;
    @XmlElement(required = true)
    private int year;
}

package soap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "interestingEmployeeInformation",
        "responseStatus"
})
@XmlRootElement(name = "addEmployeeResponse")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class SoapAddEmployeeResponse {
    private SoapInterestingEmployeeInformation interestingEmployeeInformation;
    @XmlElement(required = true)
    private SoapResponseStatus responseStatus;

}

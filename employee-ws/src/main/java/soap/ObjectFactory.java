package soap;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
@NoArgsConstructor
public class ObjectFactory {
    public SoapAddEmployeeRequest createSoapAddEmployeeRequest() {
        return new SoapAddEmployeeRequest();
    }

    public SoapAddEmployeeResponse createSoapAddEmployeeResponse() {
        return new SoapAddEmployeeResponse();
    }

    public SoapCommonInterestingInformation createSoapCommonInterestingInformation() {
        return new SoapCommonInterestingInformation();
    }

    public SoapAdditionalInformation createSoapAdditionalInformation() {
        return new SoapAdditionalInformation();
    }

    public SoapBirthDateInterestingInformation createSoapBirthDateInterestingInformation() {
        return new SoapBirthDateInterestingInformation();
    }

    public SoapEmployee createSoapEmployee() {
        return new SoapEmployee();
    }

    public SoapEmployeeIdentification createSoapEmployeeIdentification() {
        return new SoapEmployeeIdentification();
    }

    public SoapEntailmentInterestingInformation createSoapEntailmentInterestingInformation() {
        return new SoapEntailmentInterestingInformation();
    }

    public SoapGetEmployeeRequest createSoapGetEmployeeRequest() {
        return new SoapGetEmployeeRequest();
    }

    public SoapGetEmployeeResponse createSoapGetEmployeeResponse() {
        return new SoapGetEmployeeResponse();
    }

    public SoapInterestingEmployeeInformation createSoapInterestingEmployeeInformation() {
        return new SoapInterestingEmployeeInformation();
    }

    public SoapPersonalInformation createSoapPersonalInformation() {
        return new SoapPersonalInformation();
    }

    public SoapResponseStatus createSoapResponseStatus() {
        return new SoapResponseStatus();
    }

}

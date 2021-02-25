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
}

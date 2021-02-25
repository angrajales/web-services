@XmlSchema(namespace = "http://www.realistic-example.org/employee-ws",
        elementFormDefault = XmlNsForm.QUALIFIED,
        xmlns = {
                @XmlNs(prefix = "emp", namespaceURI = "http://www.realistic-example.org/employee-ws")
        })
package soap;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;
# web-services

This is an example on how to build a modern reactive system which comunicates with a "legacy" system (SOAP Protocol). In order to learn new things about SOAP Protocol in Spring and Reactive WebFlux I created a simple application which adds an employee to a MySQL database (WS SOAP). This system is called from a Reactive System (Special thanks to [gungor](https://github.com/gungor/) who implemented a soap reactive client).
<br/>
<br/>
The example microservice uses an approach similar to clean architecture with [Reactive WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html), so it's nice to see how it works!

## Run Soap Service

```
cd ~
git clone https://github.com/angrajales/web-services.git
cd web-services/employee-ws/
./gradlew bootRun
```
Note: Please make sure you have [MySQL](https://dev.mysql.com/downloads/) installed. Then edit the file ```employee-ws/src/main/resources/application.yaml``` with your own credentials.

## Run Rest Service
```
cd ~
git clone https://github.com/angrajales/web-services.git
cd web-services/employee-ms/
./gradlew bootRun
```

## Test the application (REST)

```
curl --request GET \
  --url 'http://localhost:8081/employees/create?name=Juan&lastname=Opel&document_type=CC&document_number=177777777&entailment_date=2015-01-20&birth_date=1988-01-01&role=Director&salary=7777777'
```
```
curl --location --request GET 'http://localhost:8081/employees/get?document_number=123461617&document_type=CC'
```
## Test the application (SOAP)

```
curl --request POST \
  --url http://localhost:8080/ws \
  --header 'Content-Type: text/xml' \
  --data '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:emp="http://www.realistic-example.org/employee-ws">
   <soapenv:Header/>
   <soapenv:Body>
      <emp:addEmployeeRequest>
         <emp:employee>
            <emp:identification>
               <emp:documentType>CC</emp:documentType>
               <emp:documentNumber>123461617</emp:documentNumber>
            </emp:identification>
            <emp:personalInformation>
               <emp:name>Anderson</emp:name>
               <emp:lastname>Grajales</emp:lastname>
               <emp:birthDate>1992-01-02</emp:birthDate>
            </emp:personalInformation>
            <emp:additionalInformation>
               <emp:role>Director</emp:role>
               <emp:salary>7000000</emp:salary>
               <emp:entailmentDate>2012-01-01</emp:entailmentDate>
            </emp:additionalInformation>
         </emp:employee>
      </emp:addEmployeeRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```
```
curl --request POST \
  --url http://localhost:8080/ws \
  --header 'Content-Type: text/xml' \
  --data '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:emp="http://www.realistic-example.org/employee-ws">
   <soapenv:Header/>
   <soapenv:Body>
      <emp:getEmployeeRequest>
         <emp:identification>
            <emp:documentType>CC</emp:documentType>
            <emp:documentNumber>123461616</emp:documentNumber>
         </emp:identification>
      </emp:getEmployeeRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### Sample Response (SOAP)
```
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
      <ns2:addEmployeeResponse xmlns:ns2="http://www.realistic-example.org/employee-ws">
         <ns2:interestingEmployeeInformation>
            <ns2:entailmentInformation>
               <ns2:entailmentDate>
                  <ns2:day>27</ns2:day>
                  <ns2:month>1</ns2:month>
                  <ns2:year>9</ns2:year>
               </ns2:entailmentDate>
            </ns2:entailmentInformation>
            <ns2:birthDateInformation>
               <ns2:birthDate>
                  <ns2:day>26</ns2:day>
                  <ns2:month>1</ns2:month>
                  <ns2:year>29</ns2:year>
               </ns2:birthDate>
            </ns2:birthDateInformation>
         </ns2:interestingEmployeeInformation>
         <ns2:responseStatus>
            <ns2:status>true</ns2:status>
            <ns2:message>Employee added successfully</ns2:message>
         </ns2:responseStatus>
      </ns2:addEmployeeResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```
```
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
      <ns2:employeeInformation xmlns:ns2="http://www.realistic-example.org/employee-ws">
         <ns2:employee>
            <ns2:identification>
               <ns2:documentType>CC</ns2:documentType>
               <ns2:documentNumber>123461616</ns2:documentNumber>
            </ns2:identification>
            <ns2:personalInformation>
               <ns2:name>Anderson</ns2:name>
               <ns2:lastname>Grajales</ns2:lastname>
               <ns2:birthDate>1992-01-02</ns2:birthDate>
            </ns2:personalInformation>
            <ns2:additionalInformation>
               <ns2:role>Director</ns2:role>
               <ns2:salary>7000000.0</ns2:salary>
               <ns2:entailmentDate>2012-01-01</ns2:entailmentDate>
            </ns2:additionalInformation>
         </ns2:employee>
         <ns2:responseStatus>
            <ns2:status>true</ns2:status>
            <ns2:message>Employee found successfully</ns2:message>
         </ns2:responseStatus>
      </ns2:employeeInformation>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```
### Sample Error (SOAP)
```
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
      <ns2:addEmployeeResponse xmlns:ns2="http://www.realistic-example.org/employee-ws">
         <ns2:interestingEmployeeInformation>
            <ns2:entailmentInformation>
               <ns2:entailmentDate>
                  <ns2:day>27</ns2:day>
                  <ns2:month>1</ns2:month>
                  <ns2:year>9</ns2:year>
               </ns2:entailmentDate>
            </ns2:entailmentInformation>
            <ns2:birthDateInformation>
               <ns2:birthDate>
                  <ns2:day>26</ns2:day>
                  <ns2:month>1</ns2:month>
                  <ns2:year>29</ns2:year>
               </ns2:birthDate>
            </ns2:birthDateInformation>
         </ns2:interestingEmployeeInformation>
         <ns2:responseStatus>
            <ns2:status>false</ns2:status>
            <ns2:message>could not execute statement</ns2:message>
         </ns2:responseStatus>
      </ns2:addEmployeeResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```
```
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
      <ns2:employeeInformation xmlns:ns2="http://www.realistic-example.org/employee-ws">
         <ns2:responseStatus>
            <ns2:status>false</ns2:status>
            <ns2:message>The employee does not exists</ns2:message>
         </ns2:responseStatus>
      </ns2:employeeInformation>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

### Sample Response (REST)
```
{
    "data": [
        {
            "metadata": {
                "id": "3c1c9351-eeb9-40d0-b963-b1d26be3ecba",
                "source": "/create",
                "date": "2021-02-28 02:07:11"
            },
            "response": {
                "employee": {
                    "birthDateDay": 27,
                    "birthDateMonth": 1,
                    "birthDateYear": 29,
                    "entailmentDateDay": 27,
                    "entailmentDateMonth": 1,
                    "entailmentDateYear": 9
                }
            }
        }
    ]
}
```
```
{
    "data": [
        {
            "metadata": {
                "id": "08fdc6b5-98ad-459d-b974-ab95016df466",
                "source": "/get",
                "date": "2021-02-28 02:29:36"
            },
            "response": {
                "employee": {
                    "name": "Anderson",
                    "lastname": "Grajales",
                    "documentType": "CC",
                    "documentNumber": "123461617",
                    "birthDate": [
                        1992,
                        1,
                        2
                    ],
                    "entailmentDate": [
                        2012,
                        1,
                        1
                    ],
                    "role": "Director",
                    "salary": 7000000.0
                }
            }
        }
    ]
}
```
### Sample Error (REST)
```
{
  "status": "Employee not created",
  "message": "The employee couldn't be created",
  "reason": "Error creating an employee",
  "code": "CC001"
}
```
<br/>
<br/>

## Some useful references to build this project

[spring-webclient-soap](https://github.com/gungor/spring-webclient-soap)
<br/> <br/>
[How to build a SOAP Web Service with Spring](https://www.javaspringclub.com/publish-and-consume-soap-web-services-using-spring-boot-part-1/)

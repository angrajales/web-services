# web-services

This is an example on how to build a modern reactive system which comunicates with a "legacy" system (SOAP Protocol). In order to learn new things about SOAP Protocol in Spring and Reactive WebFlux I created a simple application which adds and employee to a MySQL database (WS SOAP). This system is called from a Reactive System (Special thanks to [gungor](https://github.com/gungor/) who implemented a soap reactive client).
<br/>
<br/>
The microservice example uses an approach similar to clean architecture with [Reactive WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html), so it's nice to see how it works!

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

## Test the application (SOAP)

```
curl --request POST \
  --url http://localhost:8080/ws \
  --header 'Content-Type: text/xml' \
  --data '<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:emp="http://www.realistic-example.org/employee-ws">
   <SOAP-ENV:Header />
   <SOAP-ENV:Body>
      <emp:addEmployeeRequest>
         <emp:name>Juan</emp:name>
         <emp:lastname>Opel</emp:lastname>
         <emp:documentType>CC</emp:documentType>
         <emp:documentNumber>1234526777</emp:documentNumber>
         <emp:birthDate>1988-01-01</emp:birthDate>
         <emp:entailmentDate>2015-01-20</emp:entailmentDate>
         <emp:role>Director</emp:role>
         <emp:salary>1.23456789E8</emp:salary>
      </emp:addEmployeeRequest>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>'
```

### Sample Response (SOAP)
```
<SOAP-ENV:Envelope
  xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Header/>
  <SOAP-ENV:Body>
    <ns2:addEmployeeResponse
      xmlns:ns2="http://www.realistic-example.org/employee-ws">
      <ns2:success>true</ns2:success>
      <ns2:message>Employee added successfully</ns2:message>
      <ns2:entailmentDate>2015-01-20</ns2:entailmentDate>
      <ns2:birthDate>1988-01-01</ns2:birthDate>
    </ns2:addEmployeeResponse>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```
### Sample Error (SOAP)
```
<SOAP-ENV:Envelope
  xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Header/>
  <SOAP-ENV:Body>
    <ns2:addEmployeeResponse
      xmlns:ns2="http://www.realistic-example.org/employee-ws">
      <ns2:success>false</ns2:success>
      <ns2:message>Employee cannot be added</ns2:message>
      <ns2:entailmentDate>2015-01-20</ns2:entailmentDate>
      <ns2:birthDate>1988-01-01</ns2:birthDate>
    </ns2:addEmployeeResponse>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

### Sample Response (REST)
```
{
  "birthDateDay": 1,
  "birthDateMonth": 1,
  "birthDateYear": 1988,
  "entailmentDateDay": 20,
  "entailmentDateMonth": 1,
  "entailmentDateYear": 2015
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

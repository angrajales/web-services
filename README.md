# web-services

This is an example on how to build a modern reactive system which comunicates with a "legacy" system (SOAP Protocol). In order to learn new things about SOAP Protocol in Spring and Reactive WebFlux I created a simple application which adds and employee to a MySQL database (WS SOAP). This system is called from a Reactive System (Special thanks to [gungor](https://github.com/gungor/) who implemented a soap reactive client)

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

## Test the application

```
curl --request GET \
  --url 'http://localhost:8081/employees/create?name=Juan&lastname=Opel&document_type=CC&document_number=177777777&entailment_date=2015-01-20&birth_date=1988-01-01&role=Director&salary=7777777'
```

### Sample Response
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
### Sample Error
```
{
  "status": "Employee not created",
  "message": "The employee couldn't be created",
  "reason": "Error creating an employee",
  "code": "CC001"
}
```

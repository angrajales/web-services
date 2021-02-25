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

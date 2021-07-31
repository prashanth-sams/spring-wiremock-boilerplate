# SpringBoot WireMock Boilerplate

> ### Application
> **Input:** Chapter ID
> 
> **Output:** Chapter Details
>
> Port: 8081

### Application Runner
```shell
mvn clean spring-boot:run
```

### Test Runner
```shell
mvn clean test -Dcom.wmock.info.api
```


|        | URL             |
| -------------- | ---------          |
| API endpoint        | http://localhost:8081/api/chapter/1 |
| Swagger UI        | http://localhost:8081/swagger |


#### Tools
- Version: Java16
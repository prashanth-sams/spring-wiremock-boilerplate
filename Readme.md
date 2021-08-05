# SpringBoot WireMock Boilerplate

| Exercise       | Status             | Exercise       | Status             | Exercise       | Status             |
| -------------- | ---------          | -------------- | ---------          | -------------- | ---------          |
| Global Tags Configuration        | :white_check_mark: | Singleton MockServer Initializer         | :white_check_mark: | Stub Initializer        | :white_check_mark: |
| Stub Mapping      | :white_check_mark: | URL Mapping Rules      | :white_check_mark: | Stub QueryParam      | :white_check_mark: |
| Reusable Stub Response      | :white_check_mark: | REST Assured Validations      | :white_check_mark: | Spring ActiveProfiles      | :white_check_mark: |
| Dynamic Port Handler      | :white_check_mark: | Custom Port Handler      | :white_check_mark: | Stub withRequestBody | :white_check_mark: |
| Loggers      | :white_check_mark: |

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
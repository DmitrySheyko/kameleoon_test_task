# Kameleoon_test_task

### Description:
- java version: 18 (Amazon Corretto 18.0.2);
- Spring Boot version: 3.0.2;
- database: h2;
- database version control: liquibase;
- springdoc-openapi v2.0.2

### Functions:
 - creation of user accounts;
 - creation of quotes;
 - vote (up/down) for quotes;
 - deletion of quotes;
 - getting quotes by id;
 - getting top 10 quotes;
 - getting flop 10 quotes;
 - getting evolution graph for quote.

### Launching:
From project files: 
1) download image by link from [GitHub](https://github.com/DmitrySheyko/kameleoon_test_task.git)
2) go in console to the project folder;
3) use command _docker-compose up_.

From IDEA: 
1) launch class _src/main/java/com/dmitrySheyko/kameleoon_test_task/KameleoonTestTaskApplication.java_.

From docker image: 
1) download image by link from [DockerHub](https://hub.docker.com/repository/docker/dmitrysheyko/kameleoon_test_task/general);
2) use command _docker container run -p 8080:8080 dmitrysheyko/kameleoon_test_task:1.0.0_.

### Tests:
By [link](https://github.com/DmitrySheyko/kameleoon_test_task/blob/main/postman-tests/kameleoon_test_task.json) presented set of Postman tests.

### Docs:
Swagger UI:
1) Launch application;
2) Use link: http://localhost:8080/swagger-ui/index.html

OpenAPI:
1) Launch application;
2) Use link : http://localhost:8080/v3/api-docs
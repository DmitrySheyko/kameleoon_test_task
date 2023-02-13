# Kameleoon_test_task

### Description:
- java version: 18 (Amazon Corretto 18.0.2);
- Spring Boot version: 3.0.2;
- database: h2;
- database version control: liquibase.

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





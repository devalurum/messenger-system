# Messenger RESTful API

Серверная часть дипломной работы по теме 
"Разработка справочно-коммуникационной системы для пациентов стационара."

### Стэк технологий
- Java 11
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- Spring Web
- Swagger (OpenAPI 3.0)
- Postgresql (+PostGis)
- Docker
- MapStruct
- Lombok
- Gradle

<!--
## Сборка приложения
```shell script
# Склонировать проект к себе
git clone https://github.com/devalurum/messenger-system.git

# поднять контейнер c Postgresql(+Postgis) и PgAdmin 
docker-compose up -d

# загружает gradle wrapper
gradlew wrapper

# сборка проекта
gradlew clean build 

# запуск Spring сервиса
java -jar build/libs/messenger-system.jar 
```
-->
## Deployment
Приложение задеплоено на heroku по адресу:

https://messenger-restful.herokuapp.com/messenger-system/swagger-ui/index.html
## Сборка приложения
```shell script
# Склонировать проект, либо просто скачать файл "docker-compose.yaml"
git clone https://github.com/devalurum/messenger-system.git

# поднять контейнеры c PostgreSQL(+Postgis) и Spring Boot 
docker-compose up -d
```
Открыть в браузере Swagger: http://localhost:8080/messanger-system/swagger-ui/index.html

## OpenAPI описание
1. Откройте адрес в браузере http://localhost:8080/messanger-system/swagger-ui/index.html
2. Выполните регистрацию пользователя http://localhost:8080/messenger-system/swagger-ui/index.html#/Authentication/register
3. Скопируйте токен и авторизируетесь в Swagger UI. После чего **token** будет вставлен автоматически в запросы.
4. pgAdmin (GUI для postgresql) http://localhost:5050
   1. login: admin@admin.com
   2. password: admin
      1. host: host.docker.internal (регистрация базы данных)
* Поля time, id, sender при post/patch запросах к сущностям игнорируются, т.к генерируются на стороне сервера.
* Формат времени в OpenAPI генерируются неправильно (пока не понятно как поменять паттерн), поэтому поля time лучше удалять при отправке запросов.
### Todo:
- Разобраться, почему не работает аннотация RolesAllowed в контроллерах.
- Написать тесты. Попробовать Testcontainers либо H2Gis для тестирования репозиториев.
- Разделить DTO на Requests и Responses (Возможно), для удаления невалидных полей при запросах.
- Разобраться подробнее с маппингом DTO через MapStruct+Lombok.
- Рефакторинг.

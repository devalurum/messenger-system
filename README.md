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
- Postgresql
- Docker
- MapStruct
- Lombok
- Gradle

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
## OpenAPI описание

1. Откройте адрес в браузере http://localhost:8080/messanger-system/swagger-ui/index.html
2. Выполните регистрацию пользователя http://localhost/messenger-system/swagger-ui/index.html#/Authentication/register
3. Скопируйте токен и авторизируетесь в Swagger UI. После чего **token** будет вставлен автоматически в запросы.
4. pgAdmin (GUI для postgresql) http://localhost:5050
   1. login: admin@admin.com
   2. password: admin
      1. host: host.docker.internal (регистрация базы данных)
   
### Todo:
- Разобраться, почему не работает RolesAllowed/Secured/PreAuthorize в контроллерах
- Написать тесты.
- Разобраться подробнее с маппингом DTO через MapStruct+Lombok.
- Рефакторинг.

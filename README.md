# Messenger RESTful API

Серверная часть дипломной работы по теме 
"Разработка справочно-коммуникационной системы для пациентов стационара."

### Стэк технологий
- Java 11
- Spring Boot
- Spring Data JPA
- Spring Web
- Swagger2
- Postgresql
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
## OpenApi описание

1. Откройте адрес в браузере http://localhost:8080/messanger-system/swagger-ui/index.html
2. pgAdmin (GUI для postgresql) http://localhost:5050
   1. login: admin@admin.com
   2. password: admin
      1. host: host.docker.internal (регистрация базы данных)
   
### Todo:
- Реализовать аутентификацию и авторизацию через Spring Security.
- Распределение ролей и доступа к методам API.
- Написать тесты.
- Разобраться подробнее с маппингом DTO через MapStruct+Lombok.
- Рефакторинг.

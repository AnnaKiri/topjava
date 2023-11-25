[![Codacy Badge](https://app.codacy.com/project/badge/Grade/9edec21c95a346dba3feac37d67734b5)](https://app.codacy.com/gh/AnnaKiri/topjava/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)

[Java Enterprise Online Project](https://javaops.ru/view/topjava)
===============================
Разработка полнофункционального Spring/JPA Enterprise приложения c авторизацией и правами доступа на основе ролей с
использованием наиболее популярных инструментов и технологий Java: Maven, Spring MVC, Security, JPA(Hibernate), REST(
Jackson), Bootstrap (css,js), DataTables, jQuery + plugins, Java 8 Stream and Time API и хранением в базах данных
Postgresql и HSQLDB.

## Topjava REST API - Managing Meals

## Get a Meal by ID

```bash
curl -X GET http://localhost:8080/topjava/rest/meals/100003 \
  -H 'Accept: application/json'
```

## Update a Meal

```bash
  curl -X PUT http://localhost:8080/topjava/rest/meals/100003 \
  -H 'Content-Type: application/json' \
  -d '{
        "dateTime": "2020-01-30T10:00:00",
        "description": "Breakfast",
        "calories": 500
      }'
```

## Delete a Meal

```bash
curl -X DELETE http://localhost:8080/topjava/rest/meals/100003
```

## Get All Meals

```bash
curl -X GET http://localhost:8080/topjava/rest/meals \
  -H 'Accept: application/json'
```

## Create a New Meal

```bash
curl -X POST http://localhost:8080/topjava/rest/meals \
  -H 'Content-Type: application/json' \
  -d '{
        "dateTime": "2023-01-30T13:00:00",
        "description": "Lunch",
        "calories": 1000
      }'
```

## Filter Meals by Date and Time

```bash
curl -X GET "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-30&startTime=10:00&endTime=20:00" \
  -H 'Accept: application/json'
```
# Backend

Проект разработан в рамках хакатона HITs Hackathon. Это backend-приложение на Java Spring Boot 21, которое использует LLM для создания умных заметок с уведомлениями по времени или местоположению пользователя.

## Технологии:
- Java Spring Boot 21
- PostgreSQL
- Firebase
- Spring Security
- WeatherAPI
- n8n
- GitHub Actions - CI/CD

## Функционал
Приложение позволяет:
- Создавать умные заметки с помощью LLM
- Настраивать уведомления:
    - По времени
    - По текущему местоположению пользователя
    - Получать персонализированные напоминания

## API Endpoints

### Авторизация

`POST /auth/registration`

Регистрация нового пользователя

`POST /auth/login`

Вход в систему

`POST /auth/logout`

Выход из системы

### Уведомления

`POST /notification/token/save`

Сохранение токена для уведомлений

`POST /notification/test`

Тест отправки уведомления

`POST /notification/test/hints`

Тест отправки подсказок

### Заметки

`PUT /note/{noteId}/update`

Обновление заметки и отправка на обработку

`POST /note/process-text`

Отправка текста заметки на обработку

`POST /note/handle`

Обработка заметки

`GET /note/my`

Получение всех заметок пользователя

### Локации

`GET /location`

Получение всех сохраненных локаций

`POST /location`

Добавление новой локации

`POST /location/coords`

Получение локации по координатам

`GET /location/for/model`

Получение локаций для модели

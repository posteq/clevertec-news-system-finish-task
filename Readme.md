#   Финальное задание Clevertec.by
#   Система управления новостями

### Основные сущности:
- news (новость) содержит поля: id, time, title, text .
- comment содержит поля: id, time, text, username и news_id.

### [Описание api]( index.html)

Приложение использует стартер для логирования 
- @LogIt аннотация для логирования 
- aop.logging.enabled 

Обработки ошибок:
- aop.exception.handler.enabled

Имеется поддержка кэша : LFU,LRU
- cache.algorithm
- cache.capacity
## Инструкция по запуску 

Перейдите в каталог exception-handler-starter

`cd exception-handler-starter`

Опубликуйте в локальный репозиторий:
    `./gradlew publishToMavenLocal`

Перейдите в каталог logger-starter

`cd logger-starter`

Опубликуйте в локальный репозиторий:
`./gradlew publishToMavenLocal`

Создайте сеть my_network

'docker create network my_network'

Перейдите в каталог news-api

`cd NewsService-clevertecFinal/news-api`

Для запуска контейнера выполните

`docker-compose up -d`

Перейдите в каталог comment-api

`cd CommentService-clevertecFinal/comment-api`

Для запуска контейнера выполните

`docker-compose up -d`

### Подключение к БД
Comment

`jdbc:postgresql://localhost:5555/clevertec`

News

`jdbc:postgresql://localhost:5556/clevertec`

### Подключение к приложению News

http://localhost:8082/clevertec/news

### Подключение к приложению Comment

http://localhost:8085/clevertec/comment
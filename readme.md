# Explore with me - Документация

## Архитектура системы

Система построена по **микросервисной архитектуре**, каждый сервис выполняет свою область ответственности.

### Основные сервисы

#### Event Service

* **Назначение:** управление событиями (создание, обновление, поиск), хранение информации о категории, инициаторе, участниках.
* **Данные:** события, категории, инициаторы, подтверждённые заявки.
* **Конфигурация:** `application.yml` – настройки базы данных, порт.

#### Category Service

* **Назначение:** управление категориями для событий.
* **Данные:** id и название категорий.
* **Конфигурация:** `application.yml`.

#### User Service

* **Назначение:** управление пользователями системы (инициатор события, участники).
* **Данные:** id пользователя, имя, контактные данные.
* **Конфигурация:** `application.yml`.

#### Compilation Service

* **Назначение:** объединение событий в подборки для фронтенда.
* **Конфигурация:** `application.yml`, Feign-клиенты для Event Service.

#### Requests Service

* **Назначение:** управление заявками на участие в событиях.
* **Данные:** id заявки, статус (CONFIRMED, REJECTED, PENDING), привязка к пользователю и событию.
* **Конфигурация:** `application.yml`, Feign-клиенты для Event Service.

---

## Взаимодействие между сервисами

Сервисы общаются друг с другом через **Feign-клиенты** и **REST API**.

| Отправитель         | Получатель          | Метод                      | Назначение                                           |
| ------------------- | ------------------- | -------------------------- | ---------------------------------------------------- |
| Compilation Service | Event Service       | GET `/events/findAllById`  | Получение списка событий по id для создания подборки |
| Event Service       | Category Service    | POST `/categories/getIds`  | Получение списка категорий по id для событий         |
| Event Service       | User Service        | GET `/users`               | Получение данных пользователей (инициаторы)          |
| Event Service       | Requests Service    | GET/POST `/updateStatuses` | Подтверждение или отклонение заявок на участие       |
| Frontend            | Compilation Service | POST `/admin/compilations` | Создание новой подборки событий                      |
| Frontend            | Event Service       | GET `/events/{id}`         | Получение полной информации о событии                |

---

## Внутренний API (для взаимодействия сервисов)

### Event Service

```java
@GetMapping("/events/findAllById")
List<EventShortDto> findAllById(@RequestParam Set<Long> eventId);

@PatchMapping("/users/{userId}/events/{eventId}/requests")
UpdRequestsStatusResult updateRequests(@PathVariable Long userId,
                                       @PathVariable Long eventId,
                                       @RequestBody EventRequestStatusUpdateRequest updDto);
```

### Category Service

```java
@PostMapping("/categories/getIds")
List<CategoryDto> getCategoriesByIds(@RequestBody List<Long> categoryIds);
```

### User Service

```java
@GetMapping("/users")
List<UserShortDto> getUsersByIds(@RequestParam Set<Long> userIds);
```

> Для всех вызовов между сервисами используется Feign.

---

## Настройка конфигураций

| Компонент      | Где настраивается                                              |
| -------------- | -------------------------------------------------------------- |
| Порты сервисов | `application.yml` в Config-Server                              |
| Базы данных    | `application.yml` в Config-Server (url, username, password)    |
| Feign-клиенты  | Модуль `common`                                                |
| Логирование    | `application.yml` в Config-Server для каждого сервиса отдельно |

---

## Внешний API

### Public: Подборки событий

| Метод | URL                      | Описание                   |
| ----- | ------------------------ | -------------------------- |
| GET   | `/compilations`          | Получение подборок событий |
| GET   | `/compilations/{compId}` | Получение подборки по id   |

### Public: Категории

| Метод | URL                   | Описание                               |
| ----- | --------------------- | -------------------------------------- |
| GET   | `/categories`         | Получение всех категорий               |
| GET   | `/categories/{catId}` | Получение информации о категории по id |

### Public: События

| Метод | URL            | Описание                                       |
| ----- | -------------- | ---------------------------------------------- |
| GET   | `/events`      | Получение событий с фильтрацией                |
| GET   | `/events/{id}` | Получение подробной информации о событии по id |

### Private: События (для пользователя)

| Метод | URL                                         | Описание                                       |
| ----- | ------------------------------------------- | ---------------------------------------------- |
| GET   | `/users/{userId}/events`                    | Получение событий текущего пользователя        |
| POST  | `/users/{userId}/events`                    | Добавление нового события                      |
| GET   | `/users/{userId}/events/{eventId}`          | Получение полного события пользователя         |
| PATCH | `/users/{userId}/events/{eventId}`          | Изменение события пользователя                 |
| GET   | `/users/{userId}/events/{eventId}/requests` | Получение информации о заявках на событие      |
| PATCH | `/users/{userId}/events/{eventId}/requests` | Изменение статуса заявок (CONFIRMED, REJECTED) |

### Private: Запросы на участие

| Метод | URL                                           | Описание                                                  |
| ----- | --------------------------------------------- | --------------------------------------------------------- |
| GET   | `/users/{userId}/requests`                    | Получение заявок пользователя на участие в чужих событиях |
| POST  | `/users/{userId}/requests`                    | Создание новой заявки                                     |
| PATCH | `/users/{userId}/requests/{requestId}/cancel` | Отмена своей заявки                                       |

### Admin: Категории

| Метод  | URL                         | Описание             |
| ------ | --------------------------- | -------------------- |
| POST   | `/admin/categories`         | Добавление категории |
| PATCH  | `/admin/categories/{catId}` | Изменение категории  |
| DELETE | `/admin/categories/{catId}` | Удаление категории   |

### Admin: События

| Метод | URL                       | Описание                                     |
| ----- | ------------------------- | -------------------------------------------- |
| GET   | `/admin/events`           | Поиск событий                                |
| PATCH | `/admin/events/{eventId}` | Редактирование данных события, смена статуса |

### Admin: Пользователи

| Метод  | URL                     | Описание                             |
| ------ | ----------------------- | ------------------------------------ |
| GET    | `/admin/users`          | Получение информации о пользователях |
| POST   | `/admin/users`          | Добавление нового пользователя       |
| DELETE | `/admin/users/{userId}` | Удаление пользователя                |

### Admin: Подборки событий

| Метод  | URL                            | Описание                         |
| ------ | ------------------------------ | -------------------------------- |
| POST   | `/admin/compilations`          | Добавление новой подборки        |
| PATCH  | `/admin/compilations/{compId}` | Обновление информации о подборке |
| DELETE | `/admin/compilations/{compId}` | Удаление подборки                |

---

> Спецификация внешнего API хранится в папке `api-specifications` в формате `json`.
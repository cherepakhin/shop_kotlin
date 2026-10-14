# API Documentation — Shop Kotlin

REST-контроллеры приложения **shop-kotlin** (Spring Boot + Kotlin).

---

## Оглавление

1. [EchoCtrl](#echctrl)
2. [LogCtrl](#logctrl)
3. [GroupProductRest](#groupproductrest)
4. [ProductRest](#productrest)

---

(в примерах ниже используется [httpie](https://httpie.io/))

## EchoCtrl <a name="echctrl"></a>

**Base path:** `/shop_kotlin/api/echo`

Тестовый контроллер для echo-запросов с подсчётом количества вызовов.

| Поле                | Тип      | Описание                              |
|---------------------|----------|---------------------------------------|
| `counter`           | `Long`   | Счётчик запросов                      |
| `MaxValueCounter`   | `Long`   | Максимальное значение (100 000)       |

### Методы

#### `GET /shop_kotlin/api/echo/{mes}` — echo message

Возвращает полученное сообщение обратно.

| Параметр | Тип    | Обяз. | Описание            |
|----------|--------|-------|---------------------|
| `mes`    | String | Да    | Любая строка для эха|

**Пример:**
````shell
http :8980/shop_kotlin/api/echo/hello
````

**Ответ (200):**
```
hello
```

---

## LogCtrl <a name="logctrl"></a>

**Base path:** `/shop_kotlin/api/log`

Контроллер для чтения файла логов приложения.

### Методы

#### `GET /shop_kotlin/api/log/` — Getting log

Возвращает содержимое файла `log/spring.log`.

**Пример:**
````shell
http :8980/shop_kotlin/api/log/
````

**Ответ (200):** Текст файла лога.

---

## GroupProductRest <a name="groupproductrest"></a>

**Base path:** `/shop_kotlin/api/group_product`

REST-контроллер для управления группами товаров.

> Определяет ТОЛЬКО интерфейсы доступа к сервису. Маппинг в DTO делается в сервисе.

### Методы

#### `GET /shop_kotlin/api/group_product/echo/{mes}` — Simple echo test

Тестовый echo-метод.

| Параметр | Тип    | Обяз. | Описание                  |
|----------|--------|-------|---------------------------|
| `mes`    | String | Да    | Любая строка              |

**Пример:**
````shell
http :8980/shop_kotlin/api/group_product/echo/test
````

**Ответ (200):**
```
test
```

---

#### `POST /shop_kotlin/api/group_product` — Create GroupProduct

Создаёт новую группу товаров из DTO. Валидирует входные данные. Очищает кэш (`group_products`, `allGroupProductDTO`).

| Параметр | Тип              | Обяз. | Описание             |
|----------|------------------|-------|----------------------|
| body     | GroupProductDTO  | Да    | DTO группы товаров   |

**GroupProductDTO:**

| Поле           | Тип    | Описание              |
|----------------|--------|-----------------------|
| `n`            | Long   | ID группы             |
| `name`         | String | Название группы       |
| `parentN`      | Long   | ID родительской группы|
| `haveSubGroups`| Boolean| Наличие подгрупп      |

**Пример запроса:**
````shell
http POST :8980/shop_kotlin/api/group_product << EOF
{
  "n": 1,
  "name": "Электроника",
  "parentN": null,
  "haveSubGroups": true
}
EOF
````

**Ответ (200):** GroupProductDTO

**Ошибки:**
- `400` — Ошибки валидации DTO
- `500` — Ошибка создания

---

#### `GET /shop_kotlin/api/group_product/{n}` — Get GroupProduct by ID

Возвращает группу товаров по ID. Кэшируется в `group_products`.

| Параметр | Тип  | Обяз. | Описание         |
|----------|------|-------|------------------|
| `n`      | Long | Да    | ID группы        |

**Пример:**
````shell
http :8980/shop_kotlin/api/group_product/1
````

**Ответ (200):** GroupProductDTO

**Ошибки:**
- `400` — ID должен быть положительным
- `404` — Группа не найдена

---

#### `GET /shop_kotlin/api/group_product/` — Get all groups

Возвращает список всех групп товаров, отсортиров��нный по ID. Кэшируется в `allGroupProductDTO`.

**Пример:**
```
GET /shop_kotlin/api/group_product/
```

**Ответ (200):** `List<GroupProductDTO>`

**Ошибки:**
- `404` — Группы не найдены

---

#### `GET /shop_kotlin/api/group_product/clear_cache` — Clear cache

Очищает все кэши (`group_products`, `allGroupProductDTO`).

**Пример:**
```
GET /shop_kotlin/api/group_product/clear_cache
```

**Ответ (200):**
```
cleared
```

---

#### `GET /shop_kotlin/api/group_product/find` — Find groups by name

Ищет группы по частичному совпадению названия.

| Параметр | Тип    | Обяз. | Описание             |
|----------|--------|-------|----------------------|
| `name`   | String | Да    | Название (частичное) |

**Пример:**
```
GET /shop_kotlin/api/group_product/find?name=эл
```

**Ответ (200):** `List<GroupProductDTO>`

**Ошибки:**
- `404` — Группы с таким названием не найдены

---

#### `GET /shop_kotlin/api/group_product/{n}/subgroups` — Get subgroups

Возвращает подгруппы указанной группы. Кэшируется в `allGroupProductDTO`.

| Параметр | Тип  | Обяз. | Описание         |
|----------|------|-------|------------------|
| `n`      | Long | Да    | ID родительской группы |

**Пример:**
```
GET /shop_kotlin/api/group_product/1/subgroups
```

**Ответ (200):** `List<GroupProductDTO>`

**Ошибки:**
- `404` — Группа не найдена

---

#### `DELETE /shop_kotlin/api/group_product/{n}` — Delete GroupProduct

Удаляет группу товаров по ID. Проверяет отсутствие подгрупп и товаров. Очищает кэш.


| Параметр | Тип  | Обяз. | Описание         |
|----------|------|-------|------------------|
| `n`      | Long | Да    | ID группы        |

**Пример:**
```
DELETE /shop_kotlin/api/group_product/1
```

**Ошибки:**
- `404` — Группа не найдена
- `409` — Группа содержит подгруппы
- `409` — Группа содержит товары

---

## ProductRest <a name="productrest"></a>

**Base path:** `/shop_kotlin/api/product`

REST-контроллер для управления товарами.

### Методы

#### `GET /shop_kotlin/api/product/count_names` — Get number of product NAMES

Возвращает количество уникальных названий товаров.

**Пример:**
```
GET /shop_kotlin/api/product/count_names
```

**Ответ (200):** `Long` — количество уникальных названий.

---

#### `GET /shop_kotlin/api/product/echo/{mes}` — Simple echo test

Тестовый echo-метод. Кэшируется в `product_echo`.

| Параметр | Тип    | Обяз. | Описание                  |
|----------|--------|-------|---------------------------|
| `mes`    | String | Да    | Любая строка              |

**Пример:**
```
GET /shop_kotlin/api/product/echo/test
```

**Ответ (200):**
```
test
```

---

#### `POST /shop_kotlin/api/product` — Create Product

Создаёт новый товар из DTO. Валидирует входные данные.

| Параметр | Тип         | Обяз. | Описание         |
|----------|-------------|-------|------------------|
| body     | ProductDTO  | Да    | DTO товара       |


**ProductDTO:**

| Поле           | Тип    | Описание              |
|----------------|--------|-----------------------|
| `n`            | Long   | ID товара             |
| `name`         | String | Название товара       |
| `price`        | Double | Цена                  |
| `groupProductN`| Long   | ID группы товаров     |

**Пример запроса:**
```json
POST /shop_kotlin/api/product
Content-Type: application/json

{
  "n": 1,
  "name": "Смартфон",
  "price": 29990.0,
  "groupProductN": 1
}
```

**Ответ (200):** ProductDTO

**Ошибки:**
- `400` — Ошибки валидации DTO

---

#### `GET /shop_kotlin/api/product/{n}` — Get Product by N

Возвращает товар по ID. Кэшируется в `products`.

| Параметр | Тип  | Обяз. | Описание        |
|----------|------|-------|-----------------|
| `n`      | Long | Да    | ID товара       |

**Пример:**
```
GET /shop_kotlin/api/product/1
```

**Ответ (200):** ProductDTO

---

#### `GET /shop_kotlin/api/product/` — Get all products

Возвращает список всех товаров. Кэшируется в `products`.

**Пример:**
```
GET /shop_kotlin/api/product/
```

**Ответ (200):** `List<ProductDTO>`

---

#### `POST /shop_kotlin/api/product/{n}` — Update Product

Обновляет товар. Очищает кэши (`products`, `allGroupProductDTO`).

| Параметр | Тип         | Обяз. | Описание         |
|----------|-------------|-------|------------------|
| `n`      | Long        | Да    | ID товара        |
| body     | ProductDTO  | Да    | DTO товара       |

**Пример запроса:**
```json
POST /shop_kotlin/api/product/1
Content-Type: application/json

{
  "n": 1,
  "name": "Смартфон Pro",
  "price": 34990.0,
  "groupProductN": 1
}
```

**Ответ (200):** ProductDTO

**Ошибки:**
- `400` — Ошибки валидации DTO

---

#### `DELETE /shop_kotlin/api/product/{n}` — Delete Product

Удаляет товар по ID. Очищает кэш `products`.

| Параметр | Тип  | Обяз. | Описание        |
|----------|------|-------|-----------------|
| `n`      | Long | Да    | ID товара       |

**Пример:**
```
DELETE /shop_kotlin/api/product/1
```

**Ответ (204):** No Content

---

## Кэширование

| Кэш                  | Контроллер         | Методы                              |
|----------------------|--------------------|-------------------------------------|
| `group_products`     | GroupProductRest   | `GET /shop_kotlin/api/group_product/{n}`, `GET /shop_kotlin/api/group_product/find`, `GET /shop_kotlin/api/group_product/{n}/subgroups` |
| `allGroupProductDTO` | GroupProductRest   | `GET /shop_kotlin/api/group_product/`, `GET /shop_kotlin/api/group_product/{n}/subgroups`       |
| `product_echo`       | ProductRest        | `GET /shop_kotlin/api/product/echo/{mes}`                   |
| `products`           | ProductRest        | `GET /shop_kotlin/api/product/{n}`, `GET /shop_kotlin/api/product/`                 |

### Очистка кэша

- `GET /shop_kotlin/api/group_product/clear_cache` — очистка кэшей групп товаров
- `@CacheEvict` — автоматическая очистка при `POST`/`DELETE` операциях

---

## Обработка ошибок

Используются кастомные исключения с глобальной обработкой (`GlobalExceptionHandler`):

| Исключение                   | HTTP Status | Описание                     |
|------------------------------|-------------|------------------------------|
| `ResourceNotFoundException`  | 404         | Ресурс не найден             |
| `ResourceConflictException`  | 409         | Конфликт (есть подгруппы/товары) |
| `ValidationException`        | 400         | Ошибка валидации DTO         |
| `Exception`                  | 500         | Внутренняя ошибка сервера    |

Пример ответа об ошибке:
```json
{
    "status": 404,
    "error": "Not Found",
    "message": "Group not found with id: 11111",
    "timestamp": "2026-10-14T11:49:11.836+00:00"
}
```

---

## Ошибки

| Код  | Описание                        |
|------|---------------------------------|
| 400  | Ошибки валидации / некорректный ID |
| 404  | Ресурс не найден                |
| 409  | Конфликт (есть подгруппы/товары) |
| 500  | Внутренняя ошибка сервера       |

Возвращает товар по ID. Кэшируется в `products`.

| Параметр | Тип  | Обяз. | Описание        |
|----------|------|-------|-----------------|
| `n`      | Long | Да    | ID товара       |

**Пример:**
```
GET /shop_kotlin/api/product/1
```

**Ответ (200):** ProductDTO

---

#### `GET /shop_kotlin/api/product/` — Get all products

Возвращает список всех товаров. Кэшируется в `products`.

**Пример:**
```
GET /shop_kotlin/api/product/
```

**Ответ (200):** `List<ProductDTO>`

---

#### `POST /shop_kotlin/api/product/{n}` — Update Product

Обновляет товар. Очищает кэши (`products`, `allGroupProductDTO`).

| Параметр | Тип         | Обяз. | Описание         |
|----------|-------------|-------|------------------|
| `n`      | Long        | Да    | ID товара        |
| body     | ProductDTO  | Да    | DTO товара       |

**Пример запроса:**
```json
POST /shop_kotlin/api/product/1
Content-Type: application/json

{
  "n": 1,
  "name": "Смартфон Pro",
  "price": 34990.0,
  "groupProductN": 1
}
```

**Ответ (200):** ProductDTO

**Ошибки:**
- `400` — Ошибки валидации DTO

---

#### `DELETE /shop_kotlin/api/product/{n}` — Delete Product

Удаляет товар по ID. Очищает кэш `products`.

| Параметр | Тип  | Обяз. | Описание        |
|----------|------|-------|-----------------|
| `n`      | Long | Да    | ID товара       |

**Пример:**
```
DELETE /shop_kotlin/api/product/1
```

**Ответ (204):** No Content

---

## Кэширование

| Кэш                  | Контроллер         | Методы                              |
|----------------------|--------------------|-------------------------------------|
| `group_products`     | GroupProductRest   | `GET /shop_kotlin/api/group_product/{n}`, `GET /shop_kotlin/api/group_product/find`, `GET /shop_kotlin/api/group_product/{n}/subgroups` |
| `allGroupProductDTO` | GroupProductRest   | `GET /shop_kotlin/api/group_product/`, `GET /shop_kotlin/api/group_product/{n}/subgroups`       |
| `product_echo`       | ProductRest        | `GET /shop_kotlin/api/product/echo/{mes}`                   |
| `products`           | ProductRest        | `GET /shop_kotlin/api/product/{n}`, `GET /shop_kotlin/api/product/`                 |

### Очистка кэша

- `GET /shop_kotlin/api/group_product/clear_cache` — очистка кэшей групп товаров
- `@CacheEvict` — автоматическая очистка при `POST`/`DELETE` операциях

---

## Ошибки

| Код  | Описание                        |
|------|---------------------------------|
| 400  | Ошибки валидации / некорректный ID |
| 404  | Ресурс не найден                |
| 409  | Конфликт (есть подгруппы/товары) |
| 500  | Внутренняя ошибка сервера       |

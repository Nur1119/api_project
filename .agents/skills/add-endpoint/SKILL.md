---
name: add-endpoint
description: Рецепт добавления нового эндпоинта/домена Nessie по слоям проекта. Применяй, когда нужно покрыть новый ресурс API (customers, transfers, deposits и т.д.) или новый метод существующего домена. Триггеры — "добавь эндпоинт", "покрой домен X", "нужен клиент для /customers", "add endpoint", "new api client". Держит разделение слоёв: Routes → Api-клиент → тест. Не пишет ассерты в клиент, не пишет HTTP в тест.
---

# Add endpoint — рецепт по слоям

Добавляет покрытие нового ресурса Nessie, не ломая архитектуру. Контекст слоёв — `../../rules/architecture.md`, конвенции — `../../rules/conventions.md`, зоны/апрув — `../../rules/source-code-safety.md`.

## Когда использовать / когда нет

Использовать: новый домен (`customers`, `transfers`…) или новый метод существующего клиента.
Не использовать: правку самих слоёв-инфраструктуры (`Specs`, `BaseApi`, `Config`) — это отдельное архитектурное изменение через апрув.

## Hard rules (перечитать перед началом)

- **Апрув до кода.** Сначала показать план (какие файлы/методы), получить «да», потом писать (`source-code-safety.md`).
- **Ассертов в клиенте НЕТ.** `api/`-класс только выполняет запрос и отдаёт `Response`.
- **HTTP/URL/ключа в тесте НЕТ.** Тест ходит только через клиент + `Specs`.
- **Пути — только в `Routes`.** Никаких строковых литералов пути в клиенте/тесте.
- **Сообщения/логи — English.** Inline-комментов нет.
- **Контекст над сигнатурами обязателен** — структурированный Javadoc (`@layer`/`@domain`/`@context` над классом; `@endpoint`/`@behavior`/`@returns` над методом клиента; `@scenario` над тестом). Канон: `../../rules/conventions.md`.
- **Точный путь и тело — из документации Nessie**, не угадывать. После проверки внести строку в `../../rules/api-map.md`.

## Шаги

### 1. Роут → `routes/Routes.java`
Добавить константу пути с контекст-тегом:
```java
/** @endpoint base path домена customers */
public static final String CUSTOMERS = "/customers";
```

### 2. Клиент → `api/<Domain>Api.java`
Наследник `BaseApi`, метод(ы) отдают `Response`:
```java
/**
 * Клиент домена customers.
 *
 * @layer api
 * @domain customers
 * @context Инкапсулирует HTTP к /customers. Отдаёт сырой Response, без ассертов.
 * @see .agents/rules/architecture.md
 */
public class CustomersApi extends BaseApi {

    /**
     * @endpoint GET /customers
     * @behavior Возвращает всех клиентов ключа (JSON-массив).
     * @returns raw Response — проверки в тесте
     */
    public Response getAll() {
        return get(Routes.CUSTOMERS);
    }
}
```
Если нужного HTTP-метода нет в `BaseApi` (например `post`) — это правка инфраструктуры: остановись и согласуй отдельно.

### 3. Тест → `tests/<Domain>...Test.java`
Наследник `BaseTest`, через клиент + `Specs`:
```java
/**
 * Smoke домена customers.
 *
 * @layer tests
 * @domain customers
 * @context Проверяет, что /customers живо и ключ валиден. Через клиент + Specs, без HTTP в тесте.
 * @see .agents/rules/architecture.md
 */
class CustomersSmokeTest extends BaseTest {

    private final CustomersApi customersApi = new CustomersApi();

    /** @scenario GET /customers с валидным ключом → 200 + JSON. */
    @Test
    @DisplayName("GET /customers с валидным ключом возвращает 200")
    void getCustomers_returns200() {
        customersApi.getAll()
                .then()
                .spec(Specs.okJson());
    }
}
```

### 4. Модель (только если проверяем тело)
С Фазы 3: `models/<Entity>.java` как `record`, десериализация `.as(Entity.class)`. Для проверки только статуса — не заводить (YAGNI).

### 5. Обновить карту API
Внести проверенный эндпоинт в `../../rules/api-map.md` (метод, путь, статус, клиент).

## Антипаттерны (❌)

```java
// ❌ ассерт в клиенте
public Response getAll() {
    Response r = get(Routes.CUSTOMERS);
    r.then().statusCode(200);   // проверка — забота теста, не клиента
    return r;
}

// ❌ HTTP/путь в тесте
given().baseUri("http://api.nessieisreal.com").get("/customers?key=..."); // всё мимо слоёв
```

## Проверка (definition of done)

- Путь в `Routes`, клиент в `api/`, тест в `tests/`, все extends верных базовых классов.
- В клиенте нет ассертов, в тесте нет HTTP/URL/ключа.
- Тест зелёный в IntelliJ на Java 25.
- `api-map.md` дополнен.

# Карта проекта (Project Map)

**Single source of truth по путям.** Что где лежит и зачем. Другие файлы ссылаются сюда, не дублируют.

## Корень

| Путь | Описание |
|---|---|
| `pom.xml` | Maven: Java 25 (`compiler.release`), JUnit 5.14.4, REST Assured 6.0.0, плагины compiler 3.15.0 + surefire 3.5.6. Версии — в `<properties>`. |
| `.env` | Секрет: `NESSIE_API_KEY=...`. **В git не коммитится** (`.gitignore`). Единственное место хранения ключа. |
| `.gitignore` | Игнор: `.idea/`, `target/`, `*.env`, `secrets.properties`. |
| `mvnw`, `mvnw.cmd`, `.mvn/` | Maven wrapper (Maven 3.9.2), фиксирует версию сборщика. |
| `CLAUDE.md` | Loader контекста: правила работы + `@`-импорт `.agents/README.md`. |
| `PROGRESS.md` | Живой роадмап, фазы, журнал. Источник правды по статусу. |
| `.agents/` | Контекст-слой для ИИ (этот каталог). Вход — `.agents/README.md`. |

## Код тестов — `src/test/java/com/nur/nessie/`

Всё в `src/test/java` намеренно: проект не производит продакшн-артефакт, значит весь код — test-scope. Обоснование — `architecture.md`.

| Пакет | Ответственность |
|---|---|
| `config/` | `Config` — доступ к конфигу: `baseUrl()` из `config.properties`, `apiKey()` из env → `.env`. |
| `routes/` | `Routes` — константы путей эндпоинтов (`ACCOUNTS = "/accounts"`). |
| `specs/` | `Specs` — переиспользуемые `RequestSpecification` / `ResponseSpecification` (REST Assured builders). |
| `api/` | Service objects: `BaseApi` (выполняет HTTP на общей спеке) + доменные клиенты (`AccountsApi`). Без ассертов. |
| `base/` | `BaseTest` — глобальная инициализация REST Assured (`@BeforeAll`). |
| `tests/` | Тест-классы (`AccountsSmokeTest`). Только сценарии и проверки. |

## Ресурсы — `src/test/resources/`

| Путь | Описание |
|---|---|
| `config.properties` | Несекретный конфиг (`base.url`). Коммитится. Секреты сюда НЕ кладём (они в `.env`). |

# PROGRESS.md — Автотесты REST API (Capital One Nessie)

> **Для любого ИИ, который продолжает работу:** прочитай этот файл целиком.
> Ориентируйся на раздел **«Текущий статус»**. **Не перескакивай через незавершённые шаги.**
> После каждого выполненного шага: обнови чекбоксы, раздел «Текущий статус» и «Журнал», затем закоммить.
> Это единый источник правды по проекту.

## О проекте
Учебно-практический проект по автоматизированному тестированию REST API банковского приложения. Тестируемое API — Capital One Nessie (sandbox). Формат гибридный: рефреш стека + реальная практика прямо в репозитории.

## Зачем / чему учит
- **Java 25** (core, коллекции, стримы) на практике
- **Maven** — сборка, зависимости, жизненный цикл
- **REST Assured** — API-тесты, сериализация, проверки
- **WireMock** — стабы, контрактное тестирование, изоляция от нестабильного API
- **Docker** — контейнеризация тест-окружения
- Best practices архитектуры автотестов + подготовка к собесам в финтехе

## Стек
Java 25 · Maven · JUnit 5 · REST Assured · Jackson · (Lombok) · WireMock · Docker · GitHub Actions (CI)

## Тестируемое API
- Базовый URL: `http://api.nessieisreal.com`
- Ключ: GitHub-аккаунт → профиль на сайте Nessie → API key. Передаётся в каждом запросе как `?key=...`.
- **Ключ хранится ВНЕ репозитория** (env-переменная), в git не коммитится.
- Домены: customers, accounts, transfers, deposits, withdrawals, bills, purchases, merchants.

## Фазы
- [ ] **Фаза 1 — Окружение и каркас проекта**
  - [x] JDK 25 установлен (`java -version` показывает 25)
  - [x] IntelliJ IDEA установлена
  - [x] Git + GitHub-репозиторий, локальный клон
  - [x] Ключ Nessie получен и положен в env (не в репо)
  - [x] Каркас Maven-проекта: `pom.xml`, структура, `.gitignore`, Maven wrapper
  - [ ] Первый smoke-тест зелёный (`GET /accounts?key=...` → `200`)
- [ ] **Фаза 2 — REST Assured: базовые тесты Nessie** (GET-эндпоинты, статусы, тела, JSON path)
- [ ] **Фаза 3 — Модели домена + CRUD** (POJO/record, Jackson, create/read/update/delete)
- [ ] **Фаза 4 — Архитектура фреймворка** (request/response specs, конфиг, тест-данные, переиспользование)
- [ ] **Фаза 5 — WireMock** (стабы, матчинг запросов, контрактные тесты, изоляция от флакого Nessie)
- [ ] **Фаза 6 — Docker** (контейнеризация, WireMock в Docker, `docker-compose` тест-окружение)
- [ ] **Фаза 7 — CI и полировка** (GitHub Actions, уборка, подготовка к собесу)

## Текущий статус
**Фаза 1 — почти закрыта.**
- Сделано: проект инициализирован, трекер заведён, **JDK 25 (Temurin 25.0.3)** работает, IntelliJ есть, **Git + GitHub синхронизированы** (`origin` = Nur1119/api_project), **ключ Nessie в `.env`** (gitignored, проверен `GET /accounts` → `200`). **Каркас Maven готов:** `pom.xml` (Java 25, JUnit 5.14.4, REST Assured 6.0.0, плагины compiler 3.15.0 + surefire 3.5.6), структура `src/test/java/com/nur/nessie` + `src/test/resources`, **Maven wrapper** (Maven 3.9.2) — `BUILD SUCCESS`, зависимости резолвятся в IntelliJ.
- **Сейчас:** шаг 6 — первый smoke-тест на REST Assured: `GET /accounts?key=...` → `200`.
- Дальше: закрыть Фазу 1, перейти к Фазе 2 (базовые GET-тесты).

## Журнал
- 2026-06-29 — Проект инициализирован. Заведён роадмап и трекер прогресса.
- 2026-06-29 — Стек переведён с Java 21 на **Java 25** (LTS). Установлен Temurin JDK 25.0.3 (per-user, без админа: `~\Java\temurin-25`), `JAVA_HOME`/`PATH` настроены, `java -version` = 25.0.3. Чекбоксы JDK + IntelliJ закрыты.
- 2026-06-29 — Первый коммит, добавлен `.gitignore` (`.idea/`, `target/`, `.env`/секреты). Запушено на GitHub (`origin/main`). Шаг 3 (Git + GitHub) закрыт.
- 2026-06-30 — Ключ Nessie положен в `.env` (gitignored, в репо не попадает). Проверено: `git check-ignore` ловит `*.env`, запрос `GET /accounts?key=...` → `200 []`. Шаг 4 закрыт.
- 2026-06-30 — Каркас Maven: `pom.xml` (Java 25 via `compiler.release`, JUnit 5.14.4, REST Assured 6.0.0, плагины compiler 3.15.0 + surefire 3.5.6), структура `src/test/java/com/nur/nessie` + `src/test/resources`, Maven wrapper (Maven 3.9.2, `only-script`). `wrapper:wrapper` → `BUILD SUCCESS`, зависимости подтянулись в IntelliJ. Шаг 5 закрыт.

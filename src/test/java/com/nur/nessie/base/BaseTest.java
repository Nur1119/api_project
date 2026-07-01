package com.nur.nessie.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

/**
 * Базовый класс тестов.
 *
 * @layer base
 * @context Глобальная инициализация REST Assured для всех тестов.
 * @see .agents/rules/architecture.md
 */
public abstract class BaseTest {

    /** @behavior Логирует запрос/ответ только при падении проверки. */
    @BeforeAll
    static void globalSetup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}

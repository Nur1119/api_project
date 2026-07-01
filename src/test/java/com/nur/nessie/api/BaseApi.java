package com.nur.nessie.api;

import com.nur.nessie.specs.Specs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Базовый API-клиент.
 *
 * @layer api
 * @context Выполняет HTTP на общей RequestSpecification. Наследники описывают домены. Без ассертов.
 * @see .agents/rules/architecture.md
 */
public abstract class BaseApi {

    /**
     * @behavior Выполняет GET на общей спецификации.
     * @returns raw Response — проверки в тесте
     */
    protected Response get(String path) {
        return given()
                .spec(Specs.request())
                .when()
                .get(path);
    }
}

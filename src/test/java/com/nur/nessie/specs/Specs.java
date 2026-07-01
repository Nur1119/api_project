package com.nur.nessie.specs;

import com.nur.nessie.config.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * Переиспользуемые спецификации запроса/ответа REST Assured.
 *
 * @layer specs
 * @context Общая часть запроса/ответа в одном месте: baseUri, ключ, JSON, ожидаемый статус.
 * @see .agents/rules/architecture.md
 */
public final class Specs {

    private Specs() {}

    /** @behavior Базовый запрос: baseUri + ключ (query) + JSON. */
    public static RequestSpecification request() {
        return new RequestSpecBuilder()
                .setBaseUri(Config.baseUrl())
                .addQueryParam("key", Config.apiKey())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    /** @behavior Ожидание успешного JSON-ответа: 200 + application/json. */
    public static ResponseSpecification okJson() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
}

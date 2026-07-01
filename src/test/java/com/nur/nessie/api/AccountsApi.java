package com.nur.nessie.api;

import com.nur.nessie.routes.Routes;
import io.restassured.response.Response;

/**
 * Клиент домена accounts.
 *
 * @layer api
 * @domain accounts
 * @context Инкапсулирует HTTP к /accounts. Отдаёт сырой Response, без ассертов.
 * @see .agents/rules/architecture.md
 */
public class AccountsApi extends BaseApi {

    /**
     * @endpoint GET /accounts
     * @behavior Возвращает все аккаунты текущего ключа (JSON-массив).
     * @returns raw Response — проверки в тесте
     */
    public Response getAll() {
        return get(Routes.ACCOUNTS);
    }
}

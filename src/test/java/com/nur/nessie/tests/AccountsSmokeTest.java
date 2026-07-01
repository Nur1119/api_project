package com.nur.nessie.tests;

import com.nur.nessie.api.AccountsApi;
import com.nur.nessie.base.BaseTest;
import com.nur.nessie.specs.Specs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Smoke домена accounts.
 *
 * @layer tests
 * @domain accounts
 * @context Проверяет, что API живо и ключ валиден. Через клиент + Specs, без HTTP в тесте.
 * @see .agents/rules/architecture.md
 */
class AccountsSmokeTest extends BaseTest {

    private final AccountsApi accountsApi = new AccountsApi();

    /** @scenario GET /accounts с валидным ключом → 200 + JSON. */
    @Test
    @DisplayName("GET /accounts с валидным ключом возвращает 200")
    void getAccounts_returns200() {
        accountsApi.getAll()
                .then()
                .spec(Specs.okJson());
    }
}

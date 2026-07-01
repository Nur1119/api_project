package com.nur.nessie.routes;

/**
 * Централизованные пути эндпоинтов Nessie.
 *
 * @layer routes
 * @context Все пути в одном месте — защита от опечаток и дублей.
 * @see .agents/rules/architecture.md
 */
public final class Routes {

    private Routes() {}

    /** @endpoint base path домена accounts */
    public static final String ACCOUNTS = "/accounts";
}

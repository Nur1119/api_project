package com.nur.nessie.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;

/**
 * Доступ к конфигурации тестов.
 *
 * @layer config
 * @context Единственный источник baseURL и секрета. baseURL из config.properties, ключ из env → .env.
 * @see .agents/rules/architecture.md
 */
public final class Config {

    private static final String API_KEY = "NESSIE_API_KEY";
    private static final Properties PROPS = loadProperties();

    private Config() {}

    /** @behavior Базовый URL тестируемого API (из config.properties). */
    public static String baseUrl() {
        return require("base.url", PROPS.getProperty("base.url"));
    }

    /** @behavior Ключ Nessie: env-переменная → .env. Fail-fast, если не найден. */
    public static String apiKey() {
        return apiKeyFromEnv()
                .or(Config::apiKeyFromDotEnv)
                .orElseThrow(() -> new IllegalStateException(
                        "API key " + API_KEY + " not found. Set the " + API_KEY
                                + " environment variable or add " + API_KEY + "=... to .env"));
    }

    /** @behavior Грузит config.properties из classpath один раз. */
    private static Properties loadProperties() {
        Properties p = new Properties();
        try (InputStream in = Config.class.getResourceAsStream("/config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on the classpath");
            }
            p.load(in);
            return p;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read config.properties", e);
        }
    }

    /** @behavior Ключ из переменной окружения, если задана и не пуста. */
    private static Optional<String> apiKeyFromEnv() {
        String value = System.getenv(API_KEY);
        return (value != null && !value.isBlank()) ? Optional.of(value.trim()) : Optional.empty();
    }

    /** @behavior Ключ из файла .env в корне проекта. */
    private static Optional<String> apiKeyFromDotEnv() {
        Path dotEnv = Path.of(".env");
        if (!Files.exists(dotEnv)) {
            return Optional.empty();
        }
        try {
            String prefix = API_KEY + "=";
            return Files.readAllLines(dotEnv).stream()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                    .filter(line -> line.startsWith(prefix))
                    .map(line -> line.substring(prefix.length()).trim())
                    .findFirst();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read .env", e);
        }
    }

    /** @behavior Возвращает значение или бросает, если оно пусто. */
    private static String require(String name, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required property: " + name);
        }
        return value.trim();
    }
}

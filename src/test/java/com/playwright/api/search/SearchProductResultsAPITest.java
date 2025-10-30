package com.playwright.api.search;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.fixtures.HeadlessChromeOptions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.HashMap;
import java.util.stream.Stream;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright(HeadlessChromeOptions.class)
public class SearchProductResultsAPITest {

    private static APIRequestContext requestContext;

    record Product(String name, Double price) {
    }

    @BeforeAll
    public static void setupRequestContext(Playwright playwright) {

        requestContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL("https://api.practicesoftwaretesting.com")
                        .setExtraHTTPHeaders(new HashMap<>() {{
                            put("Accept", "application/json");
                        }})
        );
    }

    static Stream<Product> products() {
        APIResponse response = requestContext.get("/products?page=2");
        Assertions.assertThat(response.status()).isEqualTo(200);

        JsonObject jsonObject = new Gson().fromJson(response.text(), JsonObject.class);
        JsonArray data = jsonObject.getAsJsonArray("data");

        return data.asList().stream()
                .map(jsonElement -> {
                    JsonObject productJson = jsonElement.getAsJsonObject();
                    return new Product(
                            productJson.get("name").getAsString(),
                            productJson.get("price").getAsDouble()
                    );
                });
    }

    @DisplayName("Check presence of known products")
    @ParameterizedTest(name = "Checking product {0}")
    @MethodSource("products")
    void checkKnownProduct(Product product, Page page) {
        page.navigate("https://practicesoftwaretesting.com");
        page.getByPlaceholder("Search").waitFor();

        page.fill("[placeholder='Search']", product.name);
        page.click("button:has-text('Search')");

        // Check that the product appears with the correct name and price

        Locator productCard = page.locator(".card")
                .filter(
                        new Locator.FilterOptions()
                                .setHasText(product.name)
                                .setHasText(Double.toString(product.price))
                );
        assertThat(productCard).isVisible();
    }
}

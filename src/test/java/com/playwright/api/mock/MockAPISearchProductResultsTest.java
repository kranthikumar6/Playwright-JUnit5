package com.playwright.api.mock;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Route;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.api.mock.response.MockSearchResponses;
import com.playwright.fixtures.HeadlessChromeOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@DisplayName("Playwright allows us to mock out API responses")
@UsePlaywright(HeadlessChromeOptions.class)
public class MockAPISearchProductResultsTest {

    @BeforeEach
    public void openUrl(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
        page.getByPlaceholder("Search").waitFor();
    }

    @Test
    @DisplayName("When a search returns a single product")
    void whenASingleItemIsFound(Page page) {

        page.route("**/products/search?q=pliers",
                route -> route.fulfill(new Route.FulfillOptions()
                        .setBody(MockSearchResponses.RESPONSE_WITH_A_SINGLE_ENTRY)
                        .setStatus(200))
        );

        Locator searchBox = page.getByPlaceholder("Search");
        searchBox.fill("pliers");
        searchBox.press("Enter");

        page.waitForCondition(() ->
                page.getByTestId("product-name").count() == 1
        );

        assertThat(page.getByTestId("product-name")).hasCount(1);
        assertThat(page.getByTestId("product-name")
                .filter(new Locator.FilterOptions().setHasText("Super Pliers")))
                .isVisible();
    }

    @Test
    @DisplayName("When a search returns no products")
    void whenNoItemsAreFound(Page page) {

        page.route("**/products/search?q=pliers",
                route -> route.fulfill(new Route.FulfillOptions()
                        .setBody(MockSearchResponses.RESPONSE_WITH_NO_ENTRIES)
                        .setStatus(200))
        );
        Locator searchBox = page.getByPlaceholder("Search");
        searchBox.fill("pliers");
        searchBox.press("Enter");

        page.waitForCondition(() ->
                page.getByTestId("search_completed").isVisible()
        );

        assertThat(page.getByTestId("product-name")).isHidden();
        assertThat(page.getByTestId("search_completed")).hasText("There are no products found.");
    }
}

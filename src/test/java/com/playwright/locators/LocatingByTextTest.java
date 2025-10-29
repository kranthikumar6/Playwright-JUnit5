package com.playwright.locators;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.LoadState;
import com.playwright.fixtures.PlaywrightTestRunner;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("Locating elements by text")
public class LocatingByTextTest extends PlaywrightTestRunner {

    @BeforeEach
    void openTheCatalogPage() {
        page.navigate("https://practicesoftwaretesting.com");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector("xpath=(//img[@class='card-img-top'])[1]");
    }

    @DisplayName("Locating an element by text contents")
    @Test
    void byText() {
        page.getByText("Bolt Cutters").click();
        PlaywrightAssertions.assertThat(page.getByText("MightyCraft Hardware")).isVisible();
    }

    @DisplayName("Using alt text")
    @Test
    void byAltText() {
        page.getByAltText("Combination Pliers").click();
        page.waitForResponse("**/related", () ->
                PlaywrightAssertions.assertThat(page.getByText("ForgeFlex Tools")).isVisible()
        );
    }

    @DisplayName("Using title")
    @Test
    void byTitle() {
        page.getByAltText("Combination Pliers").click();
        page.getByTitle("Practice Software Testing - Toolshop").click();
    }
}

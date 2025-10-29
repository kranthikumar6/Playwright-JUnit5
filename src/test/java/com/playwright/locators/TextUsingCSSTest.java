package com.playwright.locators;

import com.playwright.fixtures.PlaywrightTestRunner;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Feature("Locating elements by text using CSS")
public class TextUsingCSSTest extends PlaywrightTestRunner {

    @BeforeEach
    void openContactPage() {
        page.navigate("https://practicesoftwaretesting.com/contact");
    }

    // :has-text matches any element containing specified text somewhere inside.
    @DisplayName("Using :has-text")
    @Test
    void locateTheSendButtonByText() {
        page.locator("#first_name").fill("Sarah-Jane");
        page.locator("#last_name").fill("Smith");
        page.locator("input:has-text('Send')").click();
    }

    // :text matches the smallest element containing specified text.
    @DisplayName("Using :text")
    @Test
    void locateAProductItemByText() {
        page.locator(".nav-link:text('Home')").click();
        page.locator(".card-title:text('Bolt')").click();
        assertThat(page.locator("[data-test=product-name]")).hasText("Bolt Cutters");
    }

    // Exact matches
    @DisplayName("Using :text-is")
    @Test
    void locateAProductItemByTextIs() {
        page.locator(".nav-link:text('Home')").click();
        page.locator(".card-title:text-is('Bolt Cutters')").click();
        assertThat(page.locator("[data-test=product-name]")).hasText("Bolt Cutters");
    }

    // matching with regular expressions
    @DisplayName("Using :text-matches")
    @Test
    void locateAProductItemByTextMatches() {
        page.locator(".nav-link:text('Home')").click();
        page.locator(".card-title:text-matches('Bolt \\\\w+')").click();
        assertThat(page.locator("[data-test=product-name]")).hasText("Bolt Cutters");
    }
}
package com.playwright.locators;

import com.microsoft.playwright.options.LoadState;
import com.playwright.fixtures.PlaywrightTestRunner;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("Locating visible elements")
public class VisibleElementsTest extends PlaywrightTestRunner {

    @BeforeEach
    void openContactPage() {
        page.navigate("https://practicesoftwaretesting.com");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector("xpath=(//img[@class='card-img-top'])[1]");
    }

    @DisplayName("Finding visible and invisible elements")
    @Test
    void locateVisibleAndInvisibleItems() {
        int dropdownItems = page.locator(".dropdown-item").count();
        Assertions.assertTrue(dropdownItems > 0);
    }

    @DisplayName("Finding only visible elements")
    @Test
    void locateVisibleItems() {
        int dropdownItems = page.locator(".dropdown-item:visible").count();
        Assertions.assertEquals(0, dropdownItems);
    }
}

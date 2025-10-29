package com.playwright.locators;

import com.microsoft.playwright.options.LoadState;
import com.playwright.fixtures.PlaywrightTestRunner;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("Locating elements by test Id")
public class LocatingByTestIDTest extends PlaywrightTestRunner {

    @BeforeEach
    public void openPage() {
        page.navigate("https://practicesoftwaretesting.com");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector("xpath=(//img[@class='card-img-top'])[1]");
    }

    @DisplayName("Using a custom data-test field")
    @Test
    void byTestId() {
        page.getByTestId("search-query").fill("Pliers");
        page.getByTestId("search-submit").click();
    }
}

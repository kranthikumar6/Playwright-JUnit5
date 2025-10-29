package com.playwright.products;

import com.microsoft.playwright.options.LoadState;
import com.playwright.fixtures.PlaywrightTestRunner;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

@Feature("Page Dom Content Load")
public class WaitingForProductTest extends PlaywrightTestRunner {

    @BeforeEach
    void openHomePage() {
        page.navigate("https://practicesoftwaretesting.com");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector(".card-img-top");
    }

    @Test
    void shouldShowAllProductNames() {
        List<String> productNames = page.getByTestId("product-name").allInnerTexts();
        Assertions.assertThat(productNames).contains("Pliers", "Bolt Cutters", "Hammer");
    }

    @Test
    void shouldShowAllProductImages() {
        List<String> productImageTitles = page.locator(".card-img-top").all()
                .stream()
                .map(img -> img.getAttribute("alt"))
                .toList();

        Assertions.assertThat(productImageTitles).contains("Pliers", "Bolt Cutters", "Hammer");
    }
}

package com.playwright.products;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.playwright.fixtures.PlaywrightTestRunner;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Feature("Page Default Wait")
public class ProductCategoryWaitTest extends PlaywrightTestRunner {

    @BeforeEach
    void openHomePage() {
        page.navigate("https://practicesoftwaretesting.com");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector(".card-img-top");
    }

    // Automatic wait
    @Test
    @DisplayName("Should wait for the filter checkbox options to appear before clicking")
    void shouldWaitForTheFilterCheckboxes() {
        Locator screwdriverFilter = page.getByLabel("Screwdriver");
        screwdriverFilter.click();
        assertThat(screwdriverFilter).isChecked();
    }

    @Test
    @DisplayName("Should filter products by category")
    void shouldFilterProductsByCategory() {
        page.getByRole(AriaRole.MENUBAR).getByText("Categories").click();
        page.getByRole(AriaRole.MENUBAR).getByText("Power Tools").click();

        page.waitForSelector(".card",
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000)
        );

        List<String> filteredProducts = page.getByTestId("product-name").allInnerTexts();

        Assertions.assertThat(filteredProducts).contains("Sheet Sander", "Belt Sander", "Random Orbit Sander");
    }
}

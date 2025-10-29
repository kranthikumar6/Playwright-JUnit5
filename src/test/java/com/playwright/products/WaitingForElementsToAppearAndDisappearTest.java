package com.playwright.products;

import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.playwright.fixtures.PlaywrightTestRunner;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Feature("Element Appear and Disappear Test")
public class WaitingForElementsToAppearAndDisappearTest extends PlaywrightTestRunner {

    @BeforeEach
    void openHomePage() {
        page.navigate("https://practicesoftwaretesting.com");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector("xpath=(//img[@class='card-img-top'])[1]");
    }

    @Test
    @DisplayName("It should display a toaster message when an item is added to the cart")
    void shouldDisplayToasterMessage() {
        page.getByText("Bolt Cutters").click();
        page.getByText("Add to cart").click();

        // Wait for the toaster message to appear
        assertThat(page.getByRole(AriaRole.ALERT)).isVisible();
        assertThat(page.getByRole(AriaRole.ALERT)).hasText("Product added to shopping cart.");

        page.waitForCondition(() -> page.getByRole(AriaRole.ALERT).isHidden());
    }

    @Test
    @DisplayName("Should update the cart item count")
    void shouldUpdateCartItemCount() {
        page.getByText("Bolt Cutters").click();
        page.getByText("Add to cart").click();

        page.waitForCondition(() -> page.getByTestId("cart-quantity").textContent().equals("1"));
        // page.waitForSelector("[data-test=cart-quantity]:has-text('1')");
    }

    // Wait for an element to have a particular state
    @Test
    @DisplayName("It should display a toaster message when an item is added to the cart")
    void shouldDisplayTheCartItemCount() {
        page.getByText("Bolt Cutters").click();
        page.getByText("Add to cart").click();

        // Wait for the cart quantity to update
        page.waitForCondition(() -> page.getByTestId("cart-quantity").textContent().equals("1"));
        // Or
        page.waitForSelector("[data-test='cart-quantity']:has-text('1')");
    }
}

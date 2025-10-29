package com.playwright.locators;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.playwright.fixtures.PlaywrightTestRunner;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("Nested locators")
public class NestedLocatorsTest extends PlaywrightTestRunner {

    @BeforeEach
    public void openUrl() {
        page.navigate("https://practicesoftwaretesting.com");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector("xpath=(//img[@class='card-img-top'])[1]");
    }

    @DisplayName("Using roles")
    @Test
    void locatingAMenuItemUsingRoles() {
        page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main Menu"))
                .getByRole(AriaRole.MENUITEM, new Locator.GetByRoleOptions().setName("Home"))
                .click();
    }

    @DisplayName("Using roles with other strategies")
    @Test
    void locatingAMenuItemUsingRolesAndOtherStrategies() {
        page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main Menu"))
                .getByText("Home")
                .click();
    }

    @DisplayName("filtering locators by text")
    @Test
    void filteringMenuItems() {
        page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main Menu"))
                .getByText("Categories")
                .click();

        page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main Menu"))
                .getByText("Power Tools")
                .click();

        page.waitForCondition(() -> page.getByTestId("product-name").count() > 0);

        List<String> allProducts = page.getByTestId("product-name")
                .filter(new Locator.FilterOptions().setHasText("Sander"))
                .allTextContents();

        assertThat(allProducts).allMatch(name -> name.contains("Sander"));
    }

    @DisplayName("filtering locators by locator")
    @Test
    void filteringMenuItemsByLocator() {
        List<String> allProducts = page.locator(".card")
                .filter(new Locator.FilterOptions().setHas(page.getByText("Out of stock")))
                .getByTestId("product-name")
                .allTextContents();

        assertThat(allProducts).hasSize(1)
                .allMatch(name -> name.contains("Long Nose Pliers"));
    }
}

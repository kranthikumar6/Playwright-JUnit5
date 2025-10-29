package com.playwright.locators;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import com.playwright.fixtures.PlaywrightTestRunner;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("Locating elements by placeholders and labels")
public class PlaceholdersAndLabelsTest extends PlaywrightTestRunner {

    @BeforeEach
    public void openContactUrl() {
        page.navigate("https://practicesoftwaretesting.com/contact");
    }

    @DisplayName("Using a label text")
    @Test
    public void byLabel() {
        page.getByLabel("First name").fill("Obi-Wan");
        page.getByLabel("Last name").fill("Kenobi");
        page.getByLabel("Email address").fill("obi-wan@kenobi.com");
        page.getByLabel("Subject").selectOption(new SelectOption().setLabel("Customer service"));
        page.getByLabel("Message *").fill("Hello there");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send"));
    }

    @DisplayName("Using a placeholder text")
    @Test
    public void byPlaceholder() {
        page.getByPlaceholder("Your first name").fill("Obi-Wan");
        page.getByPlaceholder("Your last name").fill("Kenobi");
        page.getByPlaceholder("Your email").fill("obi-wan@kenobi.com");
        page.getByLabel("Subject").selectOption(new SelectOption().setLabel("Customer service"));
        page.getByLabel("Message *").fill("Hello there");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send"));
    }
}

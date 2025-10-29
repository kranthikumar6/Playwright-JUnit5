package com.playwright.locators;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.playwright.fixtures.PlaywrightTestRunner;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

@Feature("Locating elements using CSS")
public class LocatingByCSSTest extends PlaywrightTestRunner {

    @BeforeEach
    void openContactPage() {
        page.navigate("https://practicesoftwaretesting.com/contact");
    }

    @DisplayName("By id")
    @Test
    void locateTheFirstNameFieldByID() {
        page.locator("#first_name").fill("Sarah-Jane");
        PlaywrightAssertions.assertThat(page.locator("#first_name")).hasValue("Sarah-Jane");
    }

    @DisplayName("By CSS class")
    @Test
    void locateTheSendButtonByCssClass() {
        page.locator("#first_name").fill("Sarah-Jane");
        page.locator(".btnSubmit").click();
        List<String> alertMessages = page.locator(".alert").allTextContents();
        Assertions.assertFalse(alertMessages.isEmpty());
    }

    @DisplayName("By attribute")
    @Test
    void locateTheSendButtonByAttribute() {
        page.locator("input[placeholder='Your last name *']").fill("Smith");
        PlaywrightAssertions.assertThat(page.locator("#last_name")).hasValue("Smith");
    }
}

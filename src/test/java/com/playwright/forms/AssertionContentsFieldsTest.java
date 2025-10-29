package com.playwright.forms;

import com.microsoft.playwright.Locator;
import com.playwright.fixtures.PlaywrightTestRunner;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Feature("Making assertions about the contents of a field")
public class AssertionContentsFieldsTest extends PlaywrightTestRunner {

    @BeforeEach
    void openContactPage() {
        page.navigate("https://practicesoftwaretesting.com/contact");
    }

    @DisplayName("Checking the value of a field")
    @Test
    void fieldValues() {
        Locator firstNameField = page.getByLabel("First name");
        firstNameField.fill("Sarah-Jane");
        assertThat(firstNameField).hasValue("Sarah-Jane");
        assertThat(firstNameField).not().isDisabled();
        assertThat(firstNameField).isVisible();
        assertThat(firstNameField).isEditable();
    }

    @DisplayName("Checking the value of a text field")
    @Test
    void textFieldValues() {
        Locator messageField = page.getByLabel("Message");
        messageField.fill("This is my message");
        assertThat(messageField).hasValue("This is my message");
    }

    @DisplayName("Checking the value of a dropdown field")
    @Test
    void dropdownFieldValues() {
        Locator subjectField = page.getByLabel("Subject");
        subjectField.selectOption("Warranty");
        assertThat(subjectField).hasValue("warranty");
    }
}

package com.playwright.forms;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.playwright.fixtures.HeadlessChromeOptions;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("Interacting with text fields")
@UsePlaywright(HeadlessChromeOptions.class)
public class PlaywrightFormsTest {

    @BeforeEach
    void openContactPage(Page page) {
        page.navigate("https://practicesoftwaretesting.com/contact");
    }

    @DisplayName("Complete the form")
    @Test
    void completeForm(Page page) throws URISyntaxException {
        Locator firstNameField = page.getByLabel("First name");
        Locator lastNameField = page.getByLabel("Last name");
        Locator emailNameField = page.getByLabel("Email");
        Locator messageField = page.getByLabel("Message");
        Locator subjectField = page.getByLabel("Subject");
        Locator uploadField = page.getByLabel("Attachment");

        firstNameField.fill("Sarah-Jane");
        lastNameField.fill("Smith");
        emailNameField.fill("sarah-jane@example.com");
        messageField.fill("Hello, world!");
        subjectField.selectOption("Warranty");

        Path fileToUpload = Paths.get(ClassLoader.getSystemResource("data/sample-data.txt").toURI());

        page.setInputFiles("#attachment", fileToUpload);

        PlaywrightAssertions.assertThat(firstNameField).hasValue("Sarah-Jane");
        PlaywrightAssertions.assertThat(lastNameField).hasValue("Smith");
        PlaywrightAssertions.assertThat(emailNameField).hasValue("sarah-jane@example.com");
        PlaywrightAssertions.assertThat(messageField).hasValue("Hello, world!");
        PlaywrightAssertions.assertThat(subjectField).hasValue("warranty");

        String uploadedFile = uploadField.inputValue();
        assertThat(uploadedFile).endsWith("sample-data.txt");
    }

    @DisplayName("Mandatory fields")
    @ParameterizedTest
    @ValueSource(strings = {"First name", "Last name", "Email", "Message"})
    void mandatoryFields(String fieldName, Page page) {
        Locator firstNameField = page.getByLabel("First name");
        Locator lastNameField = page.getByLabel("Last name");
        Locator emailNameField = page.getByLabel("Email");
        Locator messageField = page.getByLabel("Message");
        Locator subjectField = page.getByLabel("Subject");
        Locator sendButton = page.getByText("Send");

        // Fill in the field values
        firstNameField.fill("Sarah-Jane");
        lastNameField.fill("Smith");
        emailNameField.fill("sarah-jane@example.com");
        messageField.fill("Hello, world!");
        subjectField.selectOption("Warranty");

        // Clear one of the fields
        page.getByLabel(fieldName).clear();

        sendButton.click();

        // Check the error message for that field
        Locator errorMessage = page.getByRole(AriaRole.ALERT).getByText(fieldName + " is required");
        PlaywrightAssertions.assertThat(errorMessage).isVisible();
    }

    @DisplayName("Text fields")
    @Test
    void textFieldValues(Page page) {
        Locator messageField = page.getByLabel("Message");
        messageField.fill("This is my message");
        PlaywrightAssertions.assertThat(messageField).hasValue("This is my message");
    }

    @DisplayName("Dropdown lists")
    @Test
    void dropdownFieldValues(Page page) {
        Locator subjectField = page.getByLabel("Subject");
        subjectField.selectOption("Warranty");
        PlaywrightAssertions.assertThat(subjectField).hasValue("warranty");
    }

    @DisplayName("File uploads")
    @Test
    void fileUploads(Page page) throws URISyntaxException {
        Locator attachmentField = page.getByLabel("Attachment");
        Path attachment = Paths.get(ClassLoader.getSystemResource("data/sample-data.txt").toURI());
        page.setInputFiles("#attachment", attachment);
        String uploadedFile = attachmentField.inputValue();
        assertThat(uploadedFile).endsWith("sample-data.txt");
    }

    @DisplayName("By CSS class")
    @Test
    void locateTheSendButtonByCssClass(Page page) {
        page.locator("#first_name").fill("Sarah-Jane");
        page.locator(".btnSubmit").click();
        List<String> alertMessages = page.locator(".alert").allTextContents();
        Assertions.assertFalse(alertMessages.isEmpty());
    }

    @DisplayName("By attribute")
    @Test
    void locateTheSendButtonByAttribute(Page page) {
        page.locator("input[placeholder='Your last name *']").fill("Smith");
        PlaywrightAssertions.assertThat(page.locator("#last_name")).hasValue("Smith");
    }
}
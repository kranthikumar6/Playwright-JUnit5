package com.playwright.contact;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.nio.file.Path;

public class ContactForm {
    private final Page page;
    private final Locator firstNameField;
    private final Locator lastNameField;
    private final Locator emailNameField;
    private final Locator messageField;
    private final Locator subjectField;
    private final Locator sendButton;

    public ContactForm(Page page) {
        this.page = page;
        this.firstNameField = page.getByLabel("First name");
        this.lastNameField = page.getByLabel("Last name");
        this.emailNameField = page.getByLabel("Email");
        this.messageField = page.getByLabel("Message");
        this.subjectField = page.getByLabel("Subject");
        this.sendButton = page.getByText("Send");
    }

    public void setFirstName(String firstName) {
        firstNameField.fill(firstName);
    }

    public void setLastName(String lastName) {
        lastNameField.fill(lastName);
    }

    public void setEmail(String email) {
        emailNameField.fill(email);
    }

    public void setMessage(String message) {
        messageField.fill(message);
    }

    public void selectSubject(String subject) {
        subjectField.selectOption(subject);
    }

    public void setAttachment(Path fileToUpload) {
        page.setInputFiles("#attachment", fileToUpload);
    }

    public void submitForm() {
        sendButton.click();
    }

    public String getAlertMessage() {
        return page.getByRole(AriaRole.ALERT).textContent();
    }

    public void clearField(String fieldName) {
        page.getByLabel(fieldName).clear();
    }
}

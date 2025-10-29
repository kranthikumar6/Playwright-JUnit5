package com.playwright.locators;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.playwright.fixtures.PlaywrightTestRunner;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.regex.Pattern;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("Locating elements by role")
public class LocatingByRoleTest extends PlaywrightTestRunner {

    @BeforeEach
    public void openPage() {
        page.navigate("https://practicesoftwaretesting.com");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector("xpath=(//img[@class='card-img-top'])[1]");
    }

    @DisplayName("Using the BUTTON role")
    @Test
    void byButton() {
        page.getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName("Contact")).click();
        page.getByRole(AriaRole.BUTTON,
                        new Page.GetByRoleOptions().setName("Send"))
                .click();
        List<String> errorMessages = page.getByRole(AriaRole.ALERT).allTextContents();
        Assertions.assertFalse(errorMessages.isEmpty());
    }

    @DisplayName("Using the HEADING role")
    @Test
    void byHeaderRole() {
        page.locator("#search-query").fill("Pliers");
        page.getByRole(AriaRole.BUTTON,
                        new Page.GetByRoleOptions().setName("Search"))
                .click();
        Locator searchHeading = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName(Pattern.compile("Searched for:.*")));

        PlaywrightAssertions.assertThat(searchHeading).isVisible();
        PlaywrightAssertions.assertThat(searchHeading).hasText("Searched for: Pliers");
    }

    @DisplayName("Using the HEADING role and level")
    @Test
    void byHeaderRoleLevel() {
        List<String> level4Headings
                = page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions()
                                .setName("Pliers")
                                .setLevel(5))
                .allTextContents();

        assertThat(level4Headings).isNotEmpty();
    }

    @DisplayName("Identifying checkboxes")
    @Test
    void byCheckboxes() {
        page.getByLabel("Hammer").click();
        page.getByLabel("Chisels").click();
        page.getByLabel("Wrench").click();

        int selectedCount =
                page.getByTestId("filters").
                        getByRole(AriaRole.CHECKBOX,
                                new Locator.GetByRoleOptions().setChecked(true))
                        .count();

        assertThat(selectedCount).isEqualTo(3);

        List<String> selectedOptions =
                page.getByTestId("filters").
                        getByRole(AriaRole.CHECKBOX,
                                new Locator.GetByRoleOptions().setChecked(true))
                        .all()
                        .stream()
                        .map(Locator::inputValue)
                        .toList();

        assertThat(selectedOptions).hasSize(3);
    }
}

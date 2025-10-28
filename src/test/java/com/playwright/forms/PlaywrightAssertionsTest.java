package com.playwright.forms;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.playwright.fixtures.PlaywrightTestRunner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import java.util.Comparator;
import java.util.List;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PlaywrightAssertionsTest extends PlaywrightTestRunner {

    @DisplayName("Making assertions about the contents of a field")
    @Nested
    class LocatingElementsUsingCSS {

        @BeforeEach
        void openContactPage() {
            page.navigate("https://practicesoftwaretesting.com/contact");
        }

        @DisplayName("Checking the value of a field")
        @Test
        void fieldValues() {
            var firstNameField = page.getByLabel("First name");

            firstNameField.fill("Sarah-Jane");

            assertThat(firstNameField).hasValue("Sarah-Jane");

            assertThat(firstNameField).not().isDisabled();
            assertThat(firstNameField).isVisible();
            assertThat(firstNameField).isEditable();
        }

        @DisplayName("Checking the value of a text field")
        @Test
        void textFieldValues() {
            var messageField = page.getByLabel("Message");

            messageField.fill("This is my message");

            assertThat(messageField).hasValue("This is my message");
        }

        @DisplayName("Checking the value of a dropdown field")
        @Test
        void dropdownFieldValues() {
            var subjectField = page.getByLabel("Subject");

            subjectField.selectOption("Warranty");

            assertThat(subjectField).hasValue("warranty");
        }
    }

    @DisplayName("Making assertions about data values")
    @Nested
    class MakingAssertionsAboutDataValues {

        @BeforeEach
        void openHomePage() {
            openPage();
            page.waitForCondition(() -> page.getByTestId("product-name").count() > 0);
        }

        @Test
        void allProductPricesShouldBeCorrectValues() {
            List<Double> prices = page.getByTestId("product-price")
                    .allInnerTexts()
                    .stream()
                    .map(price -> Double.parseDouble(price.replace("$", "")))
                    .toList();

            Assertions.assertThat(prices)
                    .isNotEmpty()
                    .allMatch(price -> price > 0)
                    .doesNotContain(0.0)
                    .allMatch(price -> price < 1000)
                    .allSatisfy(price ->
                            Assertions.assertThat(price)
                                    .isGreaterThan(0.0)
                                    .isLessThan(1000.0));
        }


        @Test
        void shouldSortInAlphabeticalOrder() {

            page.waitForResponse("**/products*asc*", ()-> page.getByLabel("Sort").selectOption("Name (A - Z)"));

            List<String> productNames = page.getByTestId("product-name").allTextContents();

            Assertions.assertThat(productNames).isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);
        }

        @Test
        void shouldSortInReverseAlphabeticalOrder() {
            page.waitForResponse("**/products*desc*", () -> page.getByLabel("Sort").selectOption("Name (Z - A)"));

            List<String> productNames = page.getByTestId("product-name").allTextContents();

            Assertions.assertThat(productNames).isSortedAccordingTo(Comparator.reverseOrder());
        }
    }

    private void openPage() {
        page.navigate("https://practicesoftwaretesting.com");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector(".card-img-top");
    }
}
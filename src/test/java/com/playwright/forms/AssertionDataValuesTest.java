package com.playwright.forms;

import com.microsoft.playwright.options.LoadState;
import com.playwright.fixtures.PlaywrightTestRunner;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import java.util.List;

@Feature("Making assertions about data values")
public class AssertionDataValuesTest extends PlaywrightTestRunner {

    @BeforeEach
    void openHomePage() {
        page.navigate("https://practicesoftwaretesting.com");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector("xpath=(//img[@class='card-img-top'])[1]");
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
        page.waitForResponse("**/products*asc*", () -> page.getByLabel("Sort").selectOption("Name (A - Z)"));
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

package com.playwright.api.filter;

import com.microsoft.playwright.options.LoadState;
import com.playwright.fixtures.PlaywrightTestRunner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import java.util.Comparator;
import java.util.List;

public class WaitingForAPICallsTest extends PlaywrightTestRunner {

    @BeforeEach
    public void openPage() {
        page.navigate("https://practicesoftwaretesting.com");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector("xpath=(//img[@class='card-img-top'])[1]");
    }

    @Test
    void sortByDescendingPrice() {

        // Sort by descending price
        page.waitForResponse("**/products*desc*",
                () -> page.getByTestId("sort").selectOption("Price (High - Low)"));

        // Find all the prices on the page
        List<Double> productPrices = page.getByTestId("product-price")
                .allInnerTexts()
                .stream()
                .map(WaitingForAPICallsTest::extractPrice)
                .toList();

        // Are the prices in the correct order
        Assertions.assertThat(productPrices)
                .isNotEmpty()
                .isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    void sortByAscendingPrice() {

        // Sort by ascending price
        page.waitForResponse("**/products*asc*",
                () -> page.getByTestId("sort").selectOption("Price (Low - High)"));

        // Find all the prices on the page
        List<Double> productPrices = page.getByTestId("product-price")
                .allInnerTexts()
                .stream()
                .map(WaitingForAPICallsTest::extractPrice)
                .toList();

        // Are the prices in the correct order
        Assertions.assertThat(productPrices)
                .isNotEmpty()
                .isSortedAccordingTo(Comparator.naturalOrder());
    }

    private static double extractPrice(String price) {
        return Double.parseDouble(price.replace("$", ""));
    }
}

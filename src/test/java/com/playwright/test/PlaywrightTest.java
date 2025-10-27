package com.playwright.test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

public class PlaywrightTest {

    Playwright playwright;
    Browser browser;
    Page page;

    String url = "https://practicesoftwaretesting.com/";

    @BeforeEach
    public void setUp() {
        playwright = Playwright.create();
        //browser = playwright.chromium().launch();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true)
                .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions")));
        page = browser.newPage();
    }

    @AfterEach
    public void tearDown() {
        browser.close();
        playwright.close();
    }

    @Test
    public void shouldShowPageTitle() {
        page.navigate(url);
        String title = "Practice Software Testing - Toolshop - v5.0";

        Assertions.assertEquals(title, page.title());
    }

    @Test
    public void shouldSearchByKeyword() {
        page.navigate(url);
        page.locator("[placeholder=Search]").fill("Pliers");
        page.locator("button:has-text('Search')").click();

        int searchResults = page.locator(".card").count();

        assertThat(searchResults).isGreaterThan(0);
    }
}

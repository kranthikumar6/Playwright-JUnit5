package com.playwright.test;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

public class PlaywrightTest4 {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;

    Page page;

    String url = "https://practicesoftwaretesting.com/";

    @BeforeAll
    public static void setUpBrowser() {
        playwright = Playwright.create();
        //browser = playwright.chromium().launch();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true)
                .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions")));
        browserContext = browser.newContext();
    }

    @BeforeEach
    public void setUp() {
        page = browserContext.newPage();
    }

    @AfterAll
    public static void tearDown() {
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

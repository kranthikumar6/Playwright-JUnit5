package com.playwright.test;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@UsePlaywright(AnAnnotatedPlaywrightTest.MyOptions.class)
public class AnAnnotatedPlaywrightTest {

    public static class MyOptions implements OptionsFactory {

        @Override
        public Options getOptions() {
            return new Options().setHeadless(false)
                    .setLaunchOptions(new BrowserType.LaunchOptions().setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu")));
        }
    }

    @Test
    public void shouldShowPageTitle(Page page) {
        page.navigate("https://practicesoftwaretesting.com/");
        assertEquals("Practice Software Testing - Toolshop - v5.0", page.title());
    }

    @Test
    public void shouldSearchByKeyword(Page page) {
        page.navigate("https://practicesoftwaretesting.com/");
        page.locator("[placeholder=Search]").fill("Pliers");
        page.locator("button:has-text('Search')").click();

        int matchingSearchResults = page.locator(".container > .card").count();

        assertThat(matchingSearchResults).isGreaterThan(0);
    }
}

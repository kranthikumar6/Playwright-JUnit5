package com.playwright.test;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

@UsePlaywright(PlaywrightTest3.MyOptions.class)
public class PlaywrightTest3 {


    String url = "https://practicesoftwaretesting.com/";

    public static class MyOptions implements OptionsFactory {

        @Override
        public Options getOptions() {
            return new Options()
                    .setHeadless(true)
                    .setLaunchOptions(new BrowserType.LaunchOptions()
                            .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions")));
        }
    }

    @Test
    public void shouldShowPageTitle(Page page) {
        page.navigate(url);
        String title = "Practice Software Testing - Toolshop - v5.0";

        Assertions.assertEquals(title, page.title());
    }

    @Test
    public void shouldSearchByKeyword(Page page) {
        page.navigate(url);
        page.locator("[placeholder=Search]").fill("Pliers");
        page.locator("button:has-text('Search')").click();

        int searchResults = page.locator(".card").count();

        assertThat(searchResults).isGreaterThan(0);
    }
}

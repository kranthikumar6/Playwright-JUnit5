package com.playwright.fixtures;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.nio.file.Paths;
import java.util.Arrays;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.util.Locale;

public abstract class PlaywrightTestRunner {

    protected BrowserContext browserContext;
    protected Page page;

    // Thread-safe Playwright instance
    private static final ThreadLocal<Playwright> playwright = ThreadLocal.withInitial(() -> {
        Playwright pw = Playwright.create();
        pw.selectors().setTestIdAttribute("data-test");
        return pw;
    });

    // Thread-safe Browser instance, parameterized by browser type
    private static final ThreadLocal<Browser> browser = ThreadLocal.withInitial(() -> {
        String browserName = System.getProperty("browser",
                        System.getenv().getOrDefault("BROWSER", "chromium"))
                .toLowerCase(Locale.ROOT);

        BrowserType browserType;
        Playwright pw = playwright.get();

        switch (browserName) {
            case "firefox":
                browserType = pw.firefox();
                break;
            case "webkit":
                browserType = pw.webkit();
                break;
            case "chromium":
            default:
                browserType = pw.chromium();
        }

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(System.getProperty("headless", "true")))
                .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu"));

        return browserType.launch(options);
    });

    @BeforeEach
    void setUpBrowserContext() {
        browserContext = browser.get().newContext();

        browserContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true)
        );

        page = browserContext.newPage();
    }

    @AfterEach
    void closeContext(TestInfo testInfo) {
        String traceName = String.format(
                "target/traces/%s.zip",
                testInfo.getDisplayName().replaceAll("[^a-zA-Z0-9-]", "")
        );

        browserContext.tracing().stop(
                new Tracing.StopOptions().setPath(Paths.get(traceName))
        );
        browserContext.close();
    }

    @AfterAll
    static void tearDown() {
        Browser b = browser.get();
        if (b != null) {
            b.close();
            browser.remove();
        }

        Playwright pw = playwright.get();
        if (pw != null) {
            pw.close();
            playwright.remove();
        }
    }
}
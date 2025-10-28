package com.playwright.catalog.pageobjects;

import com.microsoft.playwright.Page;
import com.playwright.fixtures.ScreenshotManager;
import io.qameta.allure.Step;

public class NavBar {
    private final Page page;

    public NavBar(Page page) {
        this.page = page;
    }

    @Step("Open the shopping cart")
    public void openCart() {
        page.getByTestId("nav-cart").click();
        ScreenshotManager.takeScreenshot(page, "Shopping cart");
    }

    @Step("Open home page")
    public void openHomePage() {
        page.navigate("https://practicesoftwaretesting.com");
        ScreenshotManager.takeScreenshot(page, "Home page");
    }

    @Step("Open contact page")
    public void toTheContactPage() {
        page.navigate("https://practicesoftwaretesting.com/contact");
        ScreenshotManager.takeScreenshot(page, "Contact page");
    }
}
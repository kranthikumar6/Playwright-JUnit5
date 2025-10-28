package com.playwright.api.login;


import com.playwright.fixtures.PlaywrightTestRunner;
import com.playwright.api.register.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginWithRegisteredUserTest extends PlaywrightTestRunner {

    @Test
    @DisplayName("Should be able to login with a registered user")
    void should_login_with_registered_user() {
        // Register a user via the API
        User user = User.randomUser();
        UserAPIClient userAPIClient = new UserAPIClient(page);
        userAPIClient.registerUser(user);

        // Login via the login page
        LoginPage loginPage = new LoginPage(page);
        loginPage.open();
        loginPage.loginAs(user);

        // Check that we are on the right account page
        assertThat(loginPage.title()).isEqualTo("My account");
    }

    @Test
    @DisplayName("Should reject a user if they provide a wrong password")
    void should_reject_user_with_invalid_password() {
        User user = User.randomUser();
        UserAPIClient userAPIClient = new UserAPIClient(page);
        userAPIClient.registerUser(user);

        LoginPage loginPage = new LoginPage(page);
        loginPage.open();
        loginPage.loginAs(user.withPassword("wrong-password"));

        assertThat(loginPage.loginErrorMessage()).isEqualTo("Invalid email or password");
    }
}

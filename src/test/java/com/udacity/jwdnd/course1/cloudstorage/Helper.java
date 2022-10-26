package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    protected HomePage signUpAndLogin(WebDriver driver, int port) {
        driver.get("http://localhost:" + port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.setFirstName("baris");
        signupPage.setLastName("eroglu");
        signupPage.setUserName("beroglu");
        signupPage.setPassword("12345");
        signupPage.signUp();
        driver.get("http://localhost:" + port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUserName("beroglu");
        loginPage.setPassword("12345");
        loginPage.login();

        return new HomePage(driver);
    }
}

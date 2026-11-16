package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC07_InvalidLogin extends BaseClass {

    @Test(
        groups = { "regression", "negative" },
        retryAnalyzer = RetryAnalyzer.class
    )
    public void testInvalidLogin() {
        log.info("===== TC07_InvalidLogin STARTED =====");

        try {
            HomePage hp = new HomePage(getDriver());
            hp.clickMyAccount();
            hp.goToLogin();

            LoginPage lp = new LoginPage(getDriver());
            lp.login("invaliduser@cloudberry.services", "WrongPassword123!");

            Assert.assertTrue(
                lp.isLoginButtonDisplayed(),
                "Login button should remain visible after invalid credentials"
            );

            boolean stillOnLoginPage = getDriver().getCurrentUrl().contains("login");
            Assert.assertTrue(stillOnLoginPage, "User should remain on the login page after invalid credentials");

            log.info("Invalid login scenario passed. Login page remained visible.");

        } catch (AssertionError ae) {
            log.error("Assertion failed in TC07_InvalidLogin", ae);
            captureScreen("TC07_InvalidLogin");
            throw ae;
        } catch (Exception e) {
            log.error("Unexpected error in TC07_InvalidLogin", e);
            captureScreen("TC07_InvalidLogin_EXCEPTION");
            throw e;
        } finally {
            log.info("===== TC07_InvalidLogin FINISHED =====");
        }
    }
}

package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AffiliatePage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC09_AffiliateValidation extends BaseClass {

    @Test(
        groups = { "regression", "boundary" },
        retryAnalyzer = RetryAnalyzer.class
    )
    public void testAffiliateValidation() throws Exception {

        log.info("===== TC09_AffiliateValidation STARTED =====");

        try {
            HomePage hp = new HomePage(getDriver());
            hp.clickMyAccount();
            hp.goToLogin();

            LoginPage lp = new LoginPage(getDriver());
            lp.login("sid@cloudberry.services", "Test123");

            AffiliatePage ap = new AffiliatePage(getDriver());
            ap.navigateToAffiliateForm();

            ap.fillAffiliateDetails("", "invalid", "", "");

            Assert.assertFalse(
                ap.isAffiliateAdded(),
                "Affiliate form should reject empty or invalid values"
            );

            log.info("Affiliate validation scenario passed.");

        } catch (AssertionError ae) {
            log.error("Assertion failed in TC09_AffiliateValidation", ae);
            captureScreen("TC09_AffiliateValidation");
            throw ae;
        } catch (Exception e) {
            log.error("Unexpected error in TC09_AffiliateValidation", e);
            captureScreen("TC09_AffiliateValidation_EXCEPTION");
            throw e;
        } finally {
            log.info("===== TC09_AffiliateValidation FINISHED =====");
        }
    }
}
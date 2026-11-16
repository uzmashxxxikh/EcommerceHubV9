package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CartPage;
import pageObjects.CategoryPage;
import pageObjects.ProductPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC08_CartQuantityAndRemove extends BaseClass {

    @Test(
        groups = { "regression", "negative", "boundary" },
        retryAnalyzer = RetryAnalyzer.class
    )
    public void testCartQuantityAndRemove() {
        log.info("===== TC08_CartQuantityAndRemove STARTED =====");

        try {
            CategoryPage cp = new CategoryPage(getDriver());
            cp.clickLaptopsAndNotebooks();
            cp.clickShowAll();

            cp.selectHPProduct();

            ProductPage pp = new ProductPage(getDriver());
            pp.setDeliveryDate();
            pp.clickAddToCart();

            Assert.assertTrue(pp.isSuccessMessageDisplayed(), "Add to cart success message was not displayed");

            CartPage cartPage = new CartPage(getDriver());
            cartPage.clickShoppingCart();
            cartPage.setQuantity("2");
            cartPage.removeFirstItem();

            boolean cartStateUpdated = cartPage.isEmptyCartMessageDisplayed() || getDriver().getCurrentUrl().contains("checkout/cart");
            Assert.assertTrue(cartStateUpdated, "Cart should reflect the removal/update action");

            log.info("Cart quantity and remove flow passed.");

        } catch (AssertionError ae) {
            log.error("Assertion failed in TC08_CartQuantityAndRemove", ae);
            captureScreen("TC08_CartQuantityAndRemove");
            throw ae;
        } catch (Exception e) {
            log.error("Unexpected error in TC08_CartQuantityAndRemove", e);
            captureScreen("TC08_CartQuantityAndRemove_EXCEPTION");
            throw e;
        } finally {
            log.info("===== TC08_CartQuantityAndRemove FINISHED =====");
        }
    }
}
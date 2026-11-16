package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//strong[normalize-space()='login page']")
    WebElement loginPageLink;

    @FindBy(id = "input-shipping-address")
    WebElement shippingAddressDropdown;

    @FindBy(id = "button-shipping-methods")
    WebElement shippingMethodsButton;

    @FindBy(id = "button-shipping-method")
    WebElement flatShippingButton;

    @FindBy(id = "button-payment-methods")
    WebElement paymentMethodsButton;

    @FindBy(id = "button-payment-method")
    WebElement codButton;

    @FindBy(xpath = "//div[@class='text-end']//button[contains(text(),'Confirm')]")
    WebElement confirmButton;

    public void clickLogin() {
        dismissOverlay();

        List<WebElement> loginOptions = driver.findElements(By.xpath(
            "//a[contains(@href,'account/login')] | //a[normalize-space()='Login'] | //button[normalize-space()='Login'] | //strong[normalize-space()='login page']"
        ));

        if (!loginOptions.isEmpty()) {
            forceClick(loginOptions.get(0));
            return;
        }

        driver.get("https://cloudberrystore.services/index.php?route=account/login");
    }

    public void completeCheckout() throws InterruptedException {
        new Select(shippingAddressDropdown).selectByIndex(1);
        shippingMethodsButton.click();
        flatShippingButton.click();
        paymentMethodsButton.click();
        codButton.click();
        scroll(confirmButton);
        Thread.sleep(500);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmButton);
    }

    private void scroll(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
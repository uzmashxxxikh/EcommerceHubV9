package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//i[@class='fa-solid fa-user']")
    WebElement link_MyAccount;

    @FindBy(xpath = "//a[contains(.,'Login')] | //a[contains(@href,'account/login')] | //a[@class='dropdown-item'][normalize-space()='Login']")
    WebElement link_Login;

    public void clickMyAccount() {
        dismissOverlay();
        try {
            waitForClickable(link_MyAccount);
            forceClick(link_MyAccount);
        } catch (Exception e) {
            driver.get("https://cloudberrystore.services/index.php?route=account/login");
        }
    }

    public void goToLogin() {
        dismissOverlay();
        try {
            waitForClickable(link_Login);
            forceClick(link_Login);
        } catch (Exception e) {
            driver.get("https://cloudberrystore.services/index.php?route=account/login");
        }
    }
}
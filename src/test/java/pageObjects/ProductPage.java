package pageObjects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-option-225']")
    WebElement input_DeliveryDate;

    @FindBy(xpath = "//button[@id='button-cart']")
    WebElement btn_AddToCart;

    @FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
    WebElement alert_Success;
    
    @FindBy(xpath = "//a[@title='Checkout']//i[@class='fa-solid fa-share']")
    WebElement link_Checkout;
    
    
    
    @FindBy(xpath = "//div//button//i[@class='fa-solid fa-heart']")
    WebElement wishlistIcon;

    @FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
    WebElement successMessage;

    public void addToWishlist() {
        dismissOverlay();
        waitForClickable(wishlistIcon);
        forceClick(wishlistIcon);
    }

    public void setDeliveryDate() {
        waitForVisible(input_DeliveryDate);
        LocalDate date = LocalDate.now().plusDays(5);
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        scrollToView(input_DeliveryDate);
        input_DeliveryDate.clear();
        input_DeliveryDate.sendKeys(formattedDate);
    }
    
    

    public void clickAddToCart() {
        dismissOverlay();
        waitForClickable(btn_AddToCart);
        forceClick(btn_AddToCart);
    }

    public boolean isSuccessMessageDisplayed() {
        waitForVisible(alert_Success);
        return alert_Success.getText().contains("Success");
    }
    
    public void clickCheckout() {
        dismissOverlay();
        waitForClickable(link_Checkout);
        forceClick(link_Checkout);
    }
}
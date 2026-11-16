package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void clickShoppingCart() {
        dismissOverlay();
        try {
            driver.get("https://cloudberrystore.services/index.php?route=checkout/cart");
        } catch (Exception e) {
            driver.get("https://cloudberrystore.services/index.php?route=checkout/cart");
        }
    }

    public void setQuantity(String qty) {
        List<WebElement> quantityFields = driver.findElements(By.cssSelector("input[name='quantity'], input[type='number']"));
        if (quantityFields.isEmpty()) {
            return;
        }

        WebElement quantityField = quantityFields.get(0);
        waitForVisible(quantityField);
        quantityField.clear();
        quantityField.sendKeys(qty);
    }

    public void removeFirstItem() {
        List<WebElement> removeButtons = driver.findElements(By.cssSelector("button.btn-danger, button.remove, .btn-danger, a[href*='remove']"));
        if (removeButtons.isEmpty()) {
            return;
        }

        WebElement removeButton = removeButtons.get(0);
        waitForClickable(removeButton);
        forceClick(removeButton);
    }

    public boolean isEmptyCartMessageDisplayed() {
        String pageText = driver.getPageSource();
        return pageText.contains("Your shopping cart is empty!") || pageText.contains("Your cart is empty");
    }
}
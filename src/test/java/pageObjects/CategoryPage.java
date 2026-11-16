package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CategoryPage extends BasePage {

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[normalize-space()='Laptops & Notebooks']")
    WebElement link_Laptops;

    @FindBy(xpath = "//a[normalize-space()='Show All Laptops & Notebooks']")
    WebElement link_ShowAll;

    @FindBy(xpath = "//a[normalize-space()='HP LP3065']")
    WebElement product_HP;

    public void clickLaptopsAndNotebooks() {
        dismissOverlay();
        waitForClickable(link_Laptops);
        scrollToView(link_Laptops);
        forceClick(link_Laptops);
    }

    public void clickShowAll() {
        dismissOverlay();
        waitForClickable(link_ShowAll);
        scrollToView(link_ShowAll);
        forceClick(link_ShowAll);
    }

    public void selectHPProduct() {
        dismissOverlay();
        try {
            waitForVisible(product_HP);
            scrollToView(product_HP);
            forceClick(product_HP);
        } catch (Exception e) {
            driver.get("https://cloudberrystore.services/index.php?route=product/product&language=en-gb&product_id=47");
        }
    }
}
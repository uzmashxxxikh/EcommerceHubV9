package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage 
{
	protected WebDriver driver;
	protected WebDriverWait wait;
	
	public BasePage(WebDriver driver)
	// This is the constructor for this class.
		// It takes a WebDriver object as an argument, which is used to interact with
		// the browser.
	{
		this.driver = driver; //The passed driver is assigned to the instance variable driver. This allows
		// the class and its subclasses to use it for browser interactions.
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// The above line initializes the web elements defined in the class using Selenium's
				// PageFactory.
				// PageFactory.initElements() tells Selenium to scan the current class (this)
				// for any @FindBy annotations
				// and connect them to actual elements on the page using the provided driver.
		PageFactory.initElements(driver, this);
	}
	
//Locators
	
//Methods
	protected void waitForVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void scrollToView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    protected void safeClick(WebElement element) {
        waitForClickable(element);
        element.click();
    }

    protected void dismissOverlay() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                "var modals = document.querySelectorAll('.modal.show, #modal-shipping, #modal-login'); for (var i = 0; i < modals.length; i++) { modals[i].classList.remove('show'); modals[i].style.display = 'none'; } var backdrops = document.querySelectorAll('.modal-backdrop'); for (var i = 0; i < backdrops.length; i++) { backdrops[i].remove(); }"
            );
        } catch (Exception e) {
            // ignore if no overlay is present
        }
    }

    protected void forceClick(WebElement element) {
        try {
            dismissOverlay();
            scrollToView(element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            dismissOverlay();
            element.click();
        }
    }

    protected void closeShippingModalIfPresent() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                "var modal = document.querySelector('#modal-shipping'); if (modal) { modal.classList.remove('show'); modal.style.display='none'; } var backdrop = document.querySelector('.modal-backdrop'); if (backdrop) { backdrop.remove(); }"
            );
        } catch (Exception e) {
            // Ignore if modal is not present or already closed
        }
    }
}
package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "email")
    WebElement txt_Email;

    @FindBy(name = "password")
    WebElement txt_Password;

    @FindBy(xpath = "//button[@type='submit' and normalize-space()='Login']")
    WebElement btn_Login;

    @FindBy(xpath = "//div[contains(@class,'alert-danger')] | //div[contains(@class,'alert alert-danger')] | //div[contains(@class,'alert-dismissible')]")
    WebElement loginError;

    public void setEmail(String email) {
        waitForVisible(txt_Email);
        txt_Email.clear();
        txt_Email.sendKeys(email);
    }

    public void setPwd(String pwd) {
        waitForVisible(txt_Password);
        txt_Password.clear();
        txt_Password.sendKeys(pwd);
    }

    public void clickLogin() {
        waitForClickable(btn_Login);
        forceClick(btn_Login);
    }

    public void login(String email, String password) {
        setEmail(email);
        setPwd(password);
        clickLogin();
    }

    public boolean isLoginButtonDisplayed() {
        return btn_Login.isDisplayed();
    }

    public boolean isErrorMessageDisplayed() {
        return loginError.isDisplayed();
    }

    public String getErrorMessageText() {
        waitForVisible(loginError);
        return loginError.getText();
    }
}
package pages;

import utils.ConfigReader;
import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    private WebDriverWait wait;
    private By emailInputLocator = By.xpath("//input[@data-test='email']");
    private By passwordInputLocator = By.xpath("//input[@data-test='password']");
    private By loginButtonLocator = By.xpath("//input[@data-test='login-submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Integer.parseInt(ConfigReader.getProperty("default.timeout", "20")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputLocator));
    }

    public void enterUsername(String username) {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputLocator));
        emailInput.clear();
        emailInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
        loginButton.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isAtLoginPage() {
    try {
        return wait.until(ExpectedConditions.urlContains("/auth/login")) &&
               driver.findElement(emailInputLocator).isDisplayed();
    } catch (Exception e) {
        return false;
    }
}

}

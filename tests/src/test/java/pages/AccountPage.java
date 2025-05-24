package pages;

import utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountPage extends BasePage {

    private WebDriverWait wait;  

    private By pageTitleLocator = By.xpath("//h1[@data-test='page-title' and text()='My account']");
    private By userMenu = By.xpath("//a[@data-test='nav-menu']");
    private By signOutLink = By.xpath("//a[@data-test='nav-sign-out']");

    public AccountPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Integer.parseInt(ConfigReader.getProperty("default.timeout", "20")));
    }

    public boolean isAt() {
        try {
            boolean urlOk = wait.until(ExpectedConditions.urlToBe("https://practicesoftwaretesting.com/account"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitleLocator));
            return urlOk;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserMenuVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(userMenu));
            return driver.findElement(userMenu).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserNameCorrect(String expectedName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(userMenu));
            String actualName = driver.findElement(userMenu).getText().trim();
            System.out.println(actualName);
            return actualName.equals(expectedName);
        } catch (Exception e) {
            return false;
        }
    }

    public void clickUserMenu() {
        click(userMenu);
    }

    public void clickSignOut() {
        click(signOutLink);
    }

public boolean isOnLoginPage() {
    try {
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after logout: " + currentUrl);
        return wait.until(ExpectedConditions.urlContains("/auth/login"));
    } catch (Exception e) {
        return false;
    }
}


}

package pages;

import utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class RegistrationPage extends BasePage {

    private WebDriverWait wait;

    public RegistrationPage(WebDriver driver) {
        super(driver); 
        wait = new WebDriverWait(driver, Integer.parseInt(ConfigReader.getProperty("default.timeout", "20")));

    }

    // Locators (using XPath)
    private By firstName = By.xpath("//input[@data-test='first-name']");
    private By lastName = By.xpath("//input[@data-test='last-name']");
    private By dob = By.xpath("//input[@data-test='dob']");
    private By street = By.xpath("//input[@data-test='street']");
    private By postalCode = By.xpath("//input[@data-test='postal_code']");
    private By city = By.xpath("//input[@data-test='city']");
    private By state = By.xpath("//input[@data-test='state']");
    private By country = By.xpath("//select[@data-test='country']");
    private By phone = By.xpath("//input[@data-test='phone']");
    private By email = By.xpath("//input[@data-test='email']");
    private By password = By.xpath("//input[@data-test='password']");
    private By registerBtnLocator = By.xpath("//button[@data-test='register-submit']");

    public void fillRegistrationForm(String fName, String lName, String birthDate, String str,
                                     String zip, String town, String region, String nation,
                                     String phoneNum, String emailAddr, String pwd) {

        driver.findElement(firstName).sendKeys(fName);
        driver.findElement(lastName).sendKeys(lName);
        driver.findElement(dob).sendKeys(birthDate);
        driver.findElement(street).sendKeys(str);
        driver.findElement(postalCode).sendKeys(zip);
        driver.findElement(city).sendKeys(town);
        driver.findElement(state).sendKeys(region);
        new Select(driver.findElement(country)).selectByValue(nation); // e.g., "GB"
        driver.findElement(phone).sendKeys(phoneNum);
        driver.findElement(email).sendKeys(emailAddr);
        driver.findElement(password).sendKeys(pwd);
    }

    

public void submitRegistration() {
    WebElement registerBtn = wait.until(ExpectedConditions.elementToBeClickable(registerBtnLocator));

    // Scroll into view before clicking
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerBtn);

    // Optional pause for animations
    try { Thread.sleep(500); } catch (InterruptedException ignored) {}

    registerBtn.click();
}

}

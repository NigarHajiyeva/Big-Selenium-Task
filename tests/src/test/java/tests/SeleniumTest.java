package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.JavascriptExecutor;
import pages.AccountPage;
import pages.RegistrationPage;
import pages.LoginPage;
import pages.HomePage;
import utils.ConfigReader;
import utils.DriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.TimeoutException;



public class SeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private AccountPage accountPage;
    private DriverManager driverManager;
    private HomePage homePage;
    private RegistrationPage registrationPage;

   
    private final String testEmail = "testuser" + System.currentTimeMillis() + "@mail.com";
    private final String testPassword = ConfigReader.getProperty("test.password");


    public void takeScreenshot(String filename) {
    File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    try {
        FileUtils.copyFile(srcFile, new File("screenshots/" + filename));
        System.out.println("Screenshot saved: screenshots/" + filename);
    } catch (IOException e) {
        System.err.println("Failed to save screenshot: " + e.getMessage());
    }
}

@BeforeClass
public void setup() throws Exception {
    driverManager = new DriverManager();
    driver = driverManager.setup();
    wait = new WebDriverWait(driver, Integer.parseInt(ConfigReader.getProperty("default.timeout", "20")));

    driver.get(ConfigReader.getProperty("url") + "auth/register");
        
    registrationPage = new RegistrationPage(driver);
    loginPage = new LoginPage(driver);
    accountPage = new AccountPage(driver);
    homePage = new HomePage(driver);
}


@Test(alwaysRun = true)
public void testHomePageTitle() {
  
    // Get and print the title
    String title = homePage.getPageTitle();
    System.out.println("Home Page Title: " + title);

    Assert.assertFalse(title.isEmpty(), "Title should not be empty");
    Assert.assertTrue(title.toLowerCase().contains("practice"), "Title should contain 'practice'");
}


@Test(priority = 1)
public void testRegistration() {
    driver.get(ConfigReader.getProperty("url") + "auth/register");

registrationPage.fillRegistrationForm(
        ConfigReader.getProperty("test.firstName"),
        ConfigReader.getProperty("test.lastName"),
        ConfigReader.getProperty("test.dob"),
        ConfigReader.getProperty("test.street"),
        ConfigReader.getProperty("test.postalCode"),
        ConfigReader.getProperty("test.city"),
        ConfigReader.getProperty("test.state"),
        ConfigReader.getProperty("test.country"),
        ConfigReader.getProperty("test.phone"),
        testEmail,
        testPassword
    );


    registrationPage.submitRegistration();
    // takeScreenshot("after-register-click.png");

  

  try {
    boolean redirected = wait.until(ExpectedConditions.urlContains("/auth/login"));
    Assert.assertTrue(redirected, "User was not redirected to login page after registration.");
} catch (TimeoutException e) {
    // takeScreenshot("registration-redirect-timeout2.png");
    throw e;
}

}


    @Test(priority = 2, dependsOnMethods = "testRegistration")
    public void testLogin() {
        driver.get(ConfigReader.getProperty("url") + "auth/login");
        loginPage.login(testEmail, testPassword);

        Assert.assertTrue(accountPage.isAt(), "Login failed or not redirected to account page.");
        Assert.assertTrue(accountPage.isUserMenuVisible(), "User menu not visible after login.");
    }


    @Test(priority = 3, dependsOnMethods="testLogin")
    public void testLogout() {
        accountPage.clickUserMenu();
        accountPage.clickSignOut();
        Assert.assertTrue(accountPage.isOnLoginPage(), "Logout failed or not redirected to login page.");
    }


    @Test(priority = 4, alwaysRun = true)
    public void testBrowserBackNavigation() {
        // Navigate to Home
        driver.get(ConfigReader.getProperty("url"));
        System.out.println("Navigated to Home");
        WebElement homeLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-test='nav-home']")));
        Assert.assertTrue(homeLink.isDisplayed(), "Home link not visible");

        // Navigate to Hand Tools
        driver.get(ConfigReader.getProperty("url") + "category/hand-tools");
        System.out.println("Navigated to Hand Tools");
        WebElement handTools = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@data-test='page-title' and contains(text(), 'Hand Tools')]")));
        Assert.assertTrue(handTools.isDisplayed(), "Hand Tools page not loaded");

        // Navigate to Power Tools
        driver.get(ConfigReader.getProperty("url") + "category/power-tools");
        System.out.println("Navigated to Power Tools");
        WebElement powerTools = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@data-test='page-title' and contains(text(),'Power Tools')]")));
        Assert.assertTrue(powerTools.isDisplayed(), "Power Tools page not loaded");

        // Navigate to Other
        driver.get(ConfigReader.getProperty("url") + "category/other");
        System.out.println("Navigated to Other");
        WebElement otherLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@data-test='page-title' and contains(text(), 'Other')]")));
        Assert.assertTrue(otherLink.isDisplayed(), "Other page not loaded");

        // Navigate to Special Tools
        driver.get(ConfigReader.getProperty("url") + "category/special-tools");
        System.out.println("Navigated to Special Tools");
        WebElement specialTools = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@data-test='page-title' and contains(text(),'Special Tools')]")));
        Assert.assertTrue(specialTools.isDisplayed(), "Special Tools page not loaded");

        // Go back to Other
        driver.navigate().back();
        System.out.println("Navigated back to Other");
        WebElement backToOther = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(),'Other')]")));
        Assert.assertTrue(backToOther.isDisplayed(), "Failed to navigate back to Other");

        // Back to Power Tools
        driver.navigate().back();
        System.out.println("Navigated back to Power Tools");
        WebElement backToPower = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(),'Power Tools')]")));
        Assert.assertTrue(backToPower.isDisplayed(), "Failed to navigate back to Power Tools");

        // Back to Hand Tools
        driver.navigate().back();
        System.out.println("Navigated back to Hand Tools");
        WebElement backToHand = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(),'Hand Tools')]")));
        Assert.assertTrue(backToHand.isDisplayed(), "Failed to navigate back to Hand Tools");

        // Back to Home
        driver.navigate().back();
        System.out.println("Navigated back to Home");
        WebElement backToHome = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@data-test='nav-home']")));
        Assert.assertTrue(backToHome.isDisplayed(), "Failed to navigate back to Home");
    }


    @AfterClass
    public void tearDown() {
        driverManager.quitDriver();
    }
}
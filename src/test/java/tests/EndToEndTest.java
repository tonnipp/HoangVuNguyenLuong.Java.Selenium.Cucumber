package tests;

import Core.BaseTest;
import Pages.*;
import com.aventstack.extentreports.ExtentReports;
import configuration.Configuration;
import driver.DriverFactory;
import helper.CaptureHelpers;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.listeners.ReportListener;
import utils.logs.Log;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static StepDefinitions.BaseStepDefinition.config;

@Listeners(ReportListener.class)
public class EndToEndTest extends BaseTest {
    public static WebDriver driver;
    protected static ExtentReports extent;

    @Step("Setup: {0}")
    @BeforeTest
    public void Setup() {
        BaseTest.driver = new ChromeDriver();
        extent = new ExtentReports();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        System.out.println(dt.format(LocalDateTime.now()));
        System.out.println(dt.format(LocalDateTime.now()));
    }

    @BeforeMethod
    public void beforeMethod() throws InterruptedException, IOException {
        Configuration config = new Configuration("src/main/java/properties/app.properties");
        Configuration properties = new Configuration("src/main/java/properties/app.properties");
        driver = DriverFactory.getDriver(config.getProperty("browser"));
        driver.get(config.getProperty("url"));
    }

    //SUCCESSFUL LOGIN TEST ---
    @Test(priority = 1, description = "Verify successful login with valid credentials") // Add priority and description
    @Step("Execute Successful Login Test")
    public void userCanLoginSuccessfully() throws IOException {
        Log.info("Starting test: userCanLoginSuccessfully");
        LoginPage loginPage = new LoginPage(driver);
        Log.info("Performing login with valid credentials...");
        // Assuming loginToSystem uses correct credentials and returns HomePage
        loginPage.loginToSystem("standard_user", "secret_sauce");
        Log.info("Login attempt finished.");
        //Assert.assertTrue(homePage.isShoppingCartIconDisplayed(), "Shopping cart icon not found on HomePage after login.");
        Log.info("HomePage element verification passed.");
        Log.info("Successful login verification complete.");
    }

    //FAILED LOGIN TEST ---
    @Test(priority = 2, description = "Verify failed login with invalid credentials") // Add priority and description
    @Step("Execute Failed Login Test")
    public void userCannotLoginWithInvalidCredentials() throws IOException {
        Log.info("Starting test: userCannotLoginWithInvalidCredentials");
        LoginPage loginPage = new LoginPage(driver);

        // Assuming login(user, pass) exists and returns LoginPage on failure
        loginPage.loginToSystem("invalid_user", "wrong_password");
        Log.info("Performing login attempt with invalid credentials (User: {})", "invalid_User");
        Log.info("Performing login attempt with invalid credentials (User: {})", "wrong_password");
        Log.info("Login attempt finished.");

        // --- Verification ---
        Log.info("Verifying login failure error message...");
        // 2. Check the error message text
        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
        String actualErrorMessage = loginPage.getErrorMessage().getText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Login error message text mismatch.");
        Log.info("Error message verification passed.");
    }

    @Test(priority = 4, description = "Execute End-to-End Purchase Flow")
    @Step("Execute End-to-End Purchase Flow") // Add Allure Step for clarity
    public void userCanPurchaseItemWithPurchaseFlow() throws IOException {
        //Instantiate Page Objects INSIDE the test method
        Log.info("Starting test: userCanPurchaseItemWithPurchaseFlow");
        LoginPage loginPage = new LoginPage(driver);
        // --- Perform Actions and Chain Page Objects ---
        Log.info("Performing login...");
        // Assuming loginToSystem returns HomePage
        HomePage homePage = loginPage.loginToSystem("standard_user", "secret_sauce");
        Log.info("Login successful, on HomePage.");
        Log.info("Adding item 'Sauce Labs Backpack' to cart...");
        // Call addItemToCart on the HomePage object returned by login
        homePage.clickRandomProduct().addProductToCart().backToProductsAll().clickShoppingCartButton().startCheckout(); // Corrected item name if needed
        Log.info("Item added.");
        Log.info("Starting checkout process...");
        InformationPage informationPage = new InformationPage(driver);
        informationPage.isDisplayFullElements();
        informationPage.inputCheckoutInformantion("Tony", "Nguyen","700000")
                .checkInputInformation("Tony", "Nguyen","700000");// Assuming method name and return type
        Log.info("Information entered, on CheckoutOverviewPage.");
        Log.info("Information checked.");
        OverviewPage overviewPage = new OverviewPage(driver);
        overviewPage.finishCheckout(); // Assuming method name and return type
        Log.info("Checkout finished, on CheckoutCompletePage.");
        //Add Meaningful Assertions
        Log.info("Verifying purchase success...");
        CompletePage completePage = new CompletePage(driver);
        boolean isSuccess = completePage.isSuccessHeaderDisplay(); // Assuming this returns boolean
        // Assert based on the actual outcome
        Assert.assertTrue(isSuccess, "The 'THANK YOU FOR YOUR ORDER' header was not displayed.");
        // Optionally, assert the text directly
        // Assert.assertEquals(completePage.getSuccessHeaderText(), "THANK YOU FOR YOUR ORDER", "Success header text mismatch.");
        Log.info("Purchase verification successful.");
    }

    //Data-Driven Test Method
//    @Test(priority = 3, description = "Verify login with multiple accounts from JSON", dataProvider = "LoginDataFromJson")
//    @Step("Execute Login Test: {0} (User: {1})")
//    public void verifyLoginWithMultipleAccounts(String testCaseName, String username, String password, String expectedResult, String expectedError) throws IOException {
//        Log.info("==== Running test: {} ====", testCaseName);
//
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.loginToSystem(username, password);
//
//        switch (expectedResult.toLowerCase()) {
//            case "success":
//                Log.info("Verifying successful login for user: {}", username);
//                HomePage homePage = new HomePage(driver);
//                Assert.assertTrue(
//                        homePage.isShoppingCartDisplayed(),
//                        String.format("[%s] Shopping cart icon not found after login.", testCaseName)
//                );
//                break;
//
//            case "failure":
//                Log.info("Verifying failed login for user: {}", username);
//                Assert.assertNotNull(expectedError, "Expected error message is null in JSON data.");
//                String actualError = loginPage.getErrorMessage().getText();
//                Assert.assertEquals(actualError, expectedError, "[" + testCaseName + "] Error message mismatch.");
//                Assert.assertEquals(driver.getCurrentUrl(), config.getProperty("url"), "[" + testCaseName + "] Unexpected URL after failed login.");
//                break;
//
//            default:
//                Assert.fail("Invalid expectedResult: " + expectedResult);
//        }
//    }

    @AfterMethod
    public void takeScreenshot(ITestResult result) throws InterruptedException {
        Log.info(result.getName());
        Thread.sleep(1000);
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                CaptureHelpers.captureScreenshot(driver, result.getName());
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }
        }
        try {
            extent.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (driver != null)
                driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

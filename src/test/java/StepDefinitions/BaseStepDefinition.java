package StepDefinitions;

import Pages.LoginPage;
import configuration.Configuration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import utils.logs.Log;

import java.io.IOException;
import java.time.Duration;

public class BaseStepDefinition {
    private WebDriverWait wait;
    public static Configuration properties;
    public static Configuration config; // Keep both if they read different files, otherwise consolidate

    static {
        try {
            properties = new Configuration("src/main/java/properties/app.properties");
            config = new Configuration("src/main/java/properties/app.properties"); // Or different file
            Log.info("Configuration properties loaded.");
        } catch (Exception e) {
            // Use a logger available during static initialization or print error
            System.err.println("FATAL: Could not load configuration properties in static block.");
            // Optionally rethrow or handle more gracefully
            throw new RuntimeException("Failed to load configuration properties", e);
        }
    }
    @Before
    public void setup(Scenario scenario) { // Add Scenario parameter for logging
        System.out.println("!!! @BEFORE HOOK STARTING for Scenario: " + scenario.getName() + " !!!"); // OBVIOUS PRINT
        Log.info("----- Starting Scenario: {} -----", scenario.getName());
        if (driver == null) {
            System.out.println("!!! DRIVER IS NULL - Initializing... !!!"); // OBVIOUS PRINT
            Log.info("WebDriver is null. Setting up new WebDriver instance...");
            try {
                Log.info("Setting up ChromeDriver using WebDriverManager...");
                WebDriverManager.chromedriver().setup();
                Log.info("WebDriverManager setup complete.");

                Log.info("Instantiating ChromeDriver...");
                driver = new ChromeDriver(); // *** Breakpoint Here ***
                System.out.println("!!! ChromeDriver Instantiated: " + driver + " !!!"); // OBVIOUS PRINT + Check if driver is null here
                if (driver == null) {
                    throw new RuntimeException("ChromeDriver instantiation resulted in a null driver!");
                }

                driver.manage().window().maximize();
                Log.info("Browser window maximized.");

                // --- INSERT URL NAVIGATION HERE ---
                String url = properties.getProperty("url"); // Get URL from properties
                if (url == null || url.trim().isEmpty()) {
                    throw new RuntimeException("Application URL not found or empty in properties file (key='url').");
                }
                Log.info("Navigating to URL: {}", url);
                driver.get(url); // Navigate to the URL
                Log.info("Navigation to URL complete.");
                // --- END URL NAVIGATION ---

                wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                System.out.println("!!! WebDriverWait Initialized: " + wait + " !!!"); // OBVIOUS PRINT + Check if wait is null
                if (wait == null) {
                    throw new RuntimeException("WebDriverWait initialization resulted in null!");
                }
                Log.info("WebDriverWait initialized with 10 second timeout.");

                Log.info("WebDriver setup complete for Scenario: {}", scenario.getName());

            } catch (IOException ioEx) { // Catch specific IOException from properties
                System.err.println("!!! @BEFORE HOOK - FAILED to read properties file !!!");
                logger.error("!!! FAILED to read properties file during WebDriver setup for Scenario: {} !!!", scenario.getName(), ioEx);
                Assert.fail("Configuration Error: Failed to read properties file: " + ioEx.getMessage());
            } catch (Exception e) {
                System.err.println("!!! @BEFORE HOOK - WebDriver setup FAILED !!!"); // OBVIOUS PRINT (Error Stream)
                logger.error("!!! WebDriver setup FAILED for Scenario: {} !!!", scenario.getName(), e);
                // Ensure this Assert.fail is NOT caught elsewhere
                // Clean up partially created driver if setup fails mid-way
                if (driver != null) {
                    try {
                        driver.quit();
                    } catch (Exception quitEx) { /* ignore */ }
                    driver = null; // Ensure driver is null if setup failed
                }
                Assert.fail("WebDriver setup failed: " + e.getMessage()); // This SHOULD stop the test
            }
        } else {
            System.out.println("!!! @BEFORE HOOK - DRIVER ALREADY EXISTS: " + driver + " !!!"); // OBVIOUS PRINT
            Log.warn("WebDriver instance already exists. Reusing it for Scenario: {}", scenario.getName());
            // Optional: Navigate to base URL even if reusing driver
            // try {
            //     String url = properties.getProperty("url");
            //     Log.info("Reusing driver. Ensuring navigation to base URL: {}", url);
            //     driver.get(url);
            // } catch (Exception e) {
            //     logger.error("Failed to navigate to base URL when reusing driver", e);
            //     Assert.fail("Failed to navigate to base URL when reusing driver: " + e.getMessage());
            // }
        }
        System.out.println("!!! @BEFORE HOOK FINISHED !!!"); // OBVIOUS PRINT
    }



    // Logger (Consider using the Log util class consistently if preferred)
    private static final Logger logger = LoggerFactory.getLogger(HomePageStepDefinition.class);
    public WebDriver driver;

    /// This Gherkin will log user to system
    @Given("User login to system")
    public void userLoginToSystem() throws InterruptedException {
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.loginToSystem("standard_user", "secret_sauce");
        } catch (Exception ex) {
            LoggerFactory.getLogger(HomePageStepDefinition.class)
                    .error("Failed to Login to system", ex.getMessage());
        }
    }

    /// This Gherkin will verify the navigation by current URL
    @Then("User navigate to {string} by URL")
    public void userNavigateToByURL(String pageName) {
        String expectedURL = "";
        try {
            //Handle expected value of URL
            if (pageName.equalsIgnoreCase("home page")) {
                expectedURL = properties.getProperty("saucedemo_homepage_url");
            } else if (pageName.equalsIgnoreCase("shopping cart page")){
            expectedURL = properties.getProperty("saucedemo_shoppingcart_url");
        }else {
            throw new Exception("Please help to check the expected Page Name from input again.");
        }

            //Navigate to URL
            driver.navigate().to(expectedURL);

        } catch (Exception ex) {
            LoggerFactory.getLogger(HomePageStepDefinition.class)
                    .error("Failed to Navigate to '{pageName}' by URL", ex.getMessage());
        }
    }

    /// This Gherkin will verify the navigation by current URL
    @Given("User navigate successfully to {string}")
    public void userSuccessfullyNavigateToHomePage(String pageName) throws InterruptedException {
        String currentURL = "";
        String expectedURL = "";
        try {
            // ProductModel.totalNumberOfProductInShoppingCart.toString();

            //Get current URL displayed on screen
            currentURL = driver.getCurrentUrl();
            //Handle expected value of URL
            if (pageName.equalsIgnoreCase("home page")) {
                expectedURL = properties.getProperty("saucedemo_homepage_url");
            } else if(pageName.equalsIgnoreCase("shopping cart page")) {
                expectedURL = properties.getProperty("saucedemo_shoppingcart_url");
            }else {
                throw new Exception("Please help to check the expected Page Name from input again.");
            }
            //Assertion current URL should match expected URL
            Log.assertString("Assert current URL of home page: ", currentURL, expectedURL);
            Assert.assertEquals(currentURL, expectedURL);

        } catch (Exception ex) {
            LoggerFactory.getLogger(HomePageStepDefinition.class)
                    .error("Failed to navigate to home page", ex.getMessage());
        }
    }

     @After
     public void teardown(Scenario scenario) {
         Log.info("----- Finishing Scenario: {} -----", scenario.getName());
         if (driver != null) {
             Log.info("Quitting WebDriver...");
             try {
                 driver.quit();
             } catch (Exception e) {
                 logger.error("Error quitting WebDriver: {}", e.getMessage(), e);
             } finally {
                 driver = null; // Ensure driver is null after quitting
                 wait = null;
                 Log.info("WebDriver instance set to null.");
             }
         } else {
             Log.warn("WebDriver was already null before @After hook.");
         }
     }


}

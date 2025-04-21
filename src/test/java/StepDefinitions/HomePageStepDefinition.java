package StepDefinitions;

import Pages.HomePage;
import configuration.Configuration;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import utils.logs.Log;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomePageStepDefinition {
    public WebDriver driver;

    // Initialize properties once for the class instance
    public Configuration properties = new Configuration("src/main/java/properties/app.properties");

    // Logger (Consider using the Log util class consistently if preferred)
    private static final Logger logger = LoggerFactory.getLogger(HomePageStepDefinition.class);
    /// This Gherkin will navigate back to home page by URL
    @When("User navigate back to {string}")
    public void userNavigateToByURL(String pageName) {
        String expectedURL = "";
        try {
            //Handle expected value of URL
            if (pageName.equalsIgnoreCase("home page")) {
                expectedURL = properties.getProperty("saucedemo_homepage_url");
            } else if (pageName.equalsIgnoreCase("shopping cart page")) {
                expectedURL = properties.getProperty("saucedemo_shoppingcart_url");
            } else {
                throw new Exception("Please help to check the expected Page Name from input again.");
            }

            //Navigate to URL
            driver.navigate().to(expectedURL);
            Duration.ofSeconds(100); // This line doesn't do anything. You probably meant to use it in a wait.

        } catch (Exception ex) {
            LoggerFactory.getLogger(HomePageStepDefinition.class)
                    .error("Failed to Navigate to '{pageName}' by URL", ex.getMessage());
        }
    }

    /// This Gherkin will add list of product
    @When("User add the list of \"<product>\"")
    public void userAddTheListOfProduct(DataTable dataTable) {
        // *** Instantiate HomePage INSIDE the method using inherited driver ***
        HomePage homePage = null; // Initialize for try-catch scope
        try {
            Log.info("Instantiating HomePage...");
            homePage = new HomePage(driver); // Use inherited driver
            Log.info("HomePage instantiated successfully.");
        } catch (Exception e) {
            logger.error("!!! Failed to instantiate HomePage !!!", e);
            Assert.fail("Failed to create HomePage object: " + e.getMessage());
            return; // Stop if page object creation fails
        }

        // --- Read data specifically from the "product" column ---
        List<String> productNames = new ArrayList<>();
        try {
            // asMaps() treats the first row as header, subsequent rows as data maps
            List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
            String columnName = "product"; // The header name of the column you want

            for (Map<String, String> row : rows) {
                if (row.containsKey(columnName)) {
                    String productName = row.get(columnName);
                    if (productName != null && !productName.trim().isEmpty()) {
                        productNames.add(productName.trim());
                    } else {
                        logger.warn("Found empty or null value in '{}' column for row: {}", columnName, row);
                    }
                } else {
                    // This shouldn't happen if the table structure is consistent, but good to check
                    logger.warn("Column '{}' not found in row: {}", columnName, row);
                }
            }
            // Alternative using Streams:
             productNames = dataTable.asMaps().stream()
                     .map(row -> row.get(columnName)) // Get value for the "product" key
                     .filter(name -> name != null && !name.trim().isEmpty()) // Filter out null/empty
                     .map(String::trim) // Trim whitespace
                     .collect(Collectors.toList());

            logger.info("Attempting to add the following products (from '{}' column): {}", columnName, productNames);

        } catch (Exception e) {
            logger.error("Error reading DataTable using asMaps(): {}", e.getMessage(), e);
            Assert.fail("Failed to process product list from DataTable: " + e.getMessage());
            return;
        }
        // --- End reading data ---


        // --- Loop through extracted list and add items ---
        if (productNames.isEmpty()) {
            logger.warn("No valid product names extracted from the DataTable's '{}' column.", "product");
            // Decide if this should be a failure or just a warning
            Assert.fail("No valid product names found in the DataTable.");
            return;
        }

        try {
            for (String productName : productNames) {
                // The list now only contains cleaned names from the specific column
                logger.info("Adding product: '{}'", productName);
                // Call method on the instantiated Page Object
                homePage.addItemToCart(productName); // Assuming waits are handled inside this method
            }
            logger.info("Finished adding products from list.");

        } catch (Exception ex) {
            logger.error("Failed while adding one or more products from the list.", ex);
            // Ensure test fails
            Assert.fail("Exception occurred while adding products: " + ex.getMessage());
        }
    }

//    @When("User added product to shopping cart")
//    public void userAddedProductToShoppingCart() {
//        // Define the default product to add
//        String defaultProductName = "Sauce Labs Backpack"; // Or read from config if needed
//
//        // *** Instantiate HomePage INSIDE the method ***
//        HomePage homePage = null; // Initialize for try-catch scope
//        try {
//            Log.info("Instantiating HomePage...");
//            homePage = new HomePage(driver); // Pass the non-null inherited driver
//            Log.info("HomePage instantiated successfully.");
//        } catch (Exception e) {
//            logger.error("!!! Failed to instantiate HomePage !!!", e);
//            Assert.fail("Failed to create HomePage object: " + e.getMessage());
//            return; // Stop if page object creation fails
//        }
//
//        Log.info("Attempting to add default product: '{}'", defaultProductName);
//        try {
//            // Call the action method on the Page Object
//            homePage.addItemToCart(defaultProductName); // Assuming waits are handled inside this method
//            Log.info("Successfully added default product '{}'", defaultProductName);
//        } catch (Exception ex) {
//            // Log the error and fail the test
//            logger.error("Failed to add default product '{}'", defaultProductName, ex);
//            Assert.fail("Exception occurred while adding default product '" + defaultProductName + "': " + ex.getMessage());
//        }
//    }



}

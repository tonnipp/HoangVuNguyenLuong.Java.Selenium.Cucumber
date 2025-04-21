package StepDefinitions;

import Pages.CartPage;
import configuration.Configuration;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.ProductModel;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import utils.logs.Log;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class ShoppingCartStepDefinition {
    // Initialize properties once for the class instance
    public Configuration properties = new Configuration("src/main/java/properties/app.properties");

    // Logger (Consider using the Log util class consistently if preferred)
    private static final Logger logger = LoggerFactory.getLogger(HomePageStepDefinition.class);
    public WebDriver driver;

    //Initialize Pages
    CartPage cartPage = new CartPage(driver);


    /// This Gherkin will verify number of product in shopping cart page
    @Then("User verify there is {int} product in shopping cart page")
    public void userVerifyThereIsProductInShoppingCartPage(Integer expectedNumberOfProduct) {
        int actualNumberOfProduct = 0;

        try {
            //Handle actual number of product
            //Return value always in integer format
            actualNumberOfProduct = cartPage.getItemsCount();
            //Save actual value to Product model
            ProductModel.totalNumberOfProductInShoppingCart = actualNumberOfProduct;
            Log.assertQuantity("Assert number of product in shopping cart page: ", actualNumberOfProduct, expectedNumberOfProduct);
            //Assertion actual number of product should match expected number of product
            Log.assertQuantity("Assert actual number of product displayed in shopping cart: ", actualNumberOfProduct, expectedNumberOfProduct);
            Assert.assertEquals(actualNumberOfProduct, expectedNumberOfProduct);
        } catch (Exception ex) {
            LoggerFactory.getLogger(HomePageStepDefinition.class)
                    .error("Failed to Verify there is {numberOfProduct} product in shopping cart page ", ex.getMessage());
        }
    }



    @When("User navigate succesfully to {string}")
    public void userNavigateSuccesfullyToShoppingCartPage(String pageName) {
        String expectedURL = "";
        try {
            //Handle expected value of URL
            if (pageName.equalsIgnoreCase("shopping cart page")) {
                expectedURL = properties.getProperty("saucedemo_shoppingcart_url");
            } else if (pageName.equalsIgnoreCase("home page")) {
                expectedURL = properties.getProperty("saucedemo_homepage_url");
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

    @Then("User verify there is {int} product in shopping cart")
    public void userVerifyThereIsProductInShoppingCart(Integer expectedNumberOfProduct) {
        int actualNumberOfProduct = 0;

        try {
            //Handle actual number of product
            //Return value always in integer format
            actualNumberOfProduct = cartPage.getItemsCount();
            //Save actual value to Product model
            ProductModel.totalNumberOfProductInShoppingCart = actualNumberOfProduct;
            Log.assertQuantity("Assert number of product in shopping cart page: ", actualNumberOfProduct, expectedNumberOfProduct);
            //Assertion actual number of product should match expected number of product
            Log.assertQuantity("Assert actual number of product displayed in shopping cart: ", actualNumberOfProduct, expectedNumberOfProduct);
            Assert.assertEquals(actualNumberOfProduct, expectedNumberOfProduct);
        } catch (Exception ex) {
            LoggerFactory.getLogger(HomePageStepDefinition.class)
                    .error("Failed to Verify there is {numberOfProduct} product in shopping cart page ", ex.getMessage());
        }

    }
    /// This Gherkin will add list of product
    @Then("Verify that the list of {string} is displayed")
    public void verifyThatTheListOfProductIsDisplayed(DataTable dataTable) {
        // *** Instantiate CartPage here using the inherited driver ***
        CartPage cartPage = null; // Initialize for try-catch scope
        try {
            Log.info("Instantiating CartPage...");
            cartPage = new CartPage(driver); // Pass the non-null inherited driver
            Log.info("CartPage instantiated successfully.");
        } catch (Exception e) {
            logger.error("!!! Failed to instantiate CartPage !!!", e);
            Assert.fail("Failed to create CartPage object: " + e.getMessage());
            return; // Stop if page object creation fails
        }

        // 1. Get Expected List from DataTable and Clean It
        List<String> expectedProductNamesRaw = dataTable.asList(String.class);
        List<String> expectedProductNames = expectedProductNamesRaw.stream()
                .map(String::trim) // Remove leading/trailing whitespace
                .filter(name -> !name.isEmpty() && // Remove empty rows
                        !name.equalsIgnoreCase("product") && // Remove header "product"
                        !name.equalsIgnoreCase("<product>")) // Remove header "<product>"
                .collect(Collectors.toList());
        Log.info("Expected products from DataTable (cleaned): {}", expectedProductNames.toString());

        try {
            // 2. Get Actual List from Cart Page
            // This method MUST use waits internally to be reliable
            List<String> actualProductNames = cartPage.getDisplayedItemNames();
            Log.info("Actual products displayed in cart: {}", actualProductNames.toString());

            // 3. Compare Lists (Order Independent)
            // Sort both lists before comparing
            Collections.sort(expectedProductNames);
            Collections.sort(actualProductNames);
            Log.info("Sorted Expected Products: {}", expectedProductNames.toString());
            Log.info("Sorted Actual Products: {}", actualProductNames.toString());

            // 4. Assert Equality
            Assert.assertEquals(actualProductNames, expectedProductNames,
                    "Verification Failed: The list of products displayed in the cart does not match the expected list.");

            Log.info("Verification Passed: Displayed product list matches the expected list.");

        } catch (Exception ex) {
            // Use the correct logger instance
            logger.error("Failed during verification of product list in shopping cart.", ex);
            // *** Fail the test explicitly using TestNG Assert ***
            Assert.fail("Exception occurred while verifying product list: " + ex.getMessage());
        }
    }
}

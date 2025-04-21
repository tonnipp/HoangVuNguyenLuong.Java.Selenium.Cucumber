package Pages;

import org.openqa.selenium.*;
import utils.logs.Log;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends SauceDemoBasePage{
    public CartPage(WebDriver driver){super(driver);}

    //Locators
    private static final String btnCheckout = "//*[@id = 'checkout']";
    private static final String cartList = "//*[@class = 'cart_list']";
    private static final String cartItem = "//*[@class = 'cart_item']";
    private static final String btnContinue = "//*[@id = 'continue-shopping']";
    private static final String txtNameOfProduct = "//div[@data-test='inventory-item-name' and contains(text(),'Sauce Labs')]";


    //Elements
    public WebElement getCartList(){return driver.findElement(By.xpath(cartList));}
    public List<WebElement> itemList(){return getCartList().findElements(By.xpath(cartItem));}
    public WebElement getBtnCheckout(){return driver.findElement(By.xpath(btnCheckout));}
    public WebElement getBtnContinue(){return driver.findElement(By.xpath(btnContinue));}
    public List<WebElement> getProductName(){return driver.findElements(By.xpath(txtNameOfProduct));}

    //Actions
    public InformationPage startCheckout(){
        getBtnCheckout().click();
        return new InformationPage(driver);
    }

    public HomePage continueShopping(){
        getBtnContinue().click();
        return new HomePage(driver);
    }

    public int getItemsCount(){return itemList().size();}

    public List<String> getDisplayedItemNames() {
        Log.info("Attempting to retrieve displayed item names from the cart.");
        List<String> itemNames = new ArrayList<>();
        try {
            // 1. Get the list of cart item WebElements (waits internally)
            List<WebElement> cartItems = itemList();
            Log.info("Found {} potential item elements in the cart.", String.valueOf(cartItems.size()));

            // 2. Iterate through each item WebElement
            for (WebElement item : cartItems) {
                try {
                    // 3. Find the name element *within* the current item element
                    // *** Use the correct relative locator ***
                    WebElement nameElement = getProductName().get(0);
                    // 4. Get the text and add it to the list
                    String nameText = nameElement.getText();
                    itemNames.add(nameText);
                    // *** Fix Log.debug syntax ***
                    Log.debug("Found item name: {}");
                } catch (NoSuchElementException e) {
                    // Log if a name element isn't found within an item element, but continue
                    // *** Log the locator string, not the removed method ***
                    Log.warn("Could not find product name element ({}) within a cart item element. Skipping.");
                }
            }
        } catch (TimeoutException e) { // Catch specific TimeoutException if needed
            Log.warn("Timed out waiting for cart items when getting names. Returning potentially incomplete list.");
            // Depending on requirements, you might want to throw an exception here or return the partial list
        } catch (Exception e) {
            // *** Use the consistent Log utility (assuming SLF4J) and fix syntax ***
            Log.error("Error retrieving item names from cart: {}");
            // Depending on requirements, you might want to throw an exception here
        }
        Log.info("Retrieved displayed item names: {}", itemNames.toString());
        return itemNames;
    }

}

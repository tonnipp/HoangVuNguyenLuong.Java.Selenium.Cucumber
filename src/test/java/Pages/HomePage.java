package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.LoggerFactory;
import utils.logs.Log;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class HomePage extends SauceDemoBasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    private Select select;
    private Wait<WebDriver> wait;

    //Loactors
    private static final String lstInventory = "//*[@data-test = 'inventory-list']";
    private static final String lstItem = ".//div[@data-test = 'inventory-item']";
    private static final String lblInventoryName = "//*[@data-test = 'inventory-item-name']";
    private static final String bthShoppingCart = "//*[@data-test = 'shopping-cart-link']";
    private static final String btnMenu = "//*[@id = 'react-burger-menu-btn']";
    private static final String btnCloseMenu = "//*[@id = 'react-burger-cross-btn']";
    private static final String btnLogout = "//*[@id = 'logout_sidebar_link']";
    private static final String lnkTwitter = "//*[@class = 'social_twitter']";
    private static final String lnkFacebook = "//*[@class = 'social_facebook']";
    private static final String lnkLinkedin = "//*[@class = 'social_linkedin']";
    private static final String btnProductSortContainer = "//*[@data-test = 'product-sort-container']";
    private static final String btnAddItemToCart = "//div[text()='\" + productName + \"']/ancestor::div[@class='inventory_item']//button";

    //Filter options locators by name
    private final static String A_TO_Z = "Name (A to Z)";
    private final static String Z_TO_A = "Name (Z to A)";
    private final static String LOW_TO_HIGH = "Price (low to high)";
    private final static String HIGH_TO_LOW = "Price (high to low)";


    //Elements
    public WebElement getListInventory() {
        return driver.findElement(By.xpath(lstInventory));
    }

    public List<WebElement> productList() {
        return driver.findElements(By.xpath(lstItem));
    }

    public WebElement getBtnShoppingCart() {
        return driver.findElement(By.xpath(bthShoppingCart));
    }

    public WebElement getBtnMenu() {
        return driver.findElement(By.xpath(btnMenu));
    }

    public WebElement getBtnCloseMenu() {
        return driver.findElement(By.xpath(btnCloseMenu));
    }

    public WebElement getBtnLogout() {
        return driver.findElement(By.xpath(btnLogout));
    }

    public WebElement getLnkTwitter() {
        return driver.findElement(By.xpath(lnkTwitter));
    }

    public WebElement getLnkFacebook() {
        return driver.findElement(By.xpath(lnkFacebook));
    }

    public WebElement getLnkLinkedin() {
        return driver.findElement(By.xpath(lnkLinkedin));
    }

    public WebElement getBtnProductSortContainer() {
        return driver.findElement(By.xpath(btnProductSortContainer));
    }

    public WebElement getBtnAddItemToCart(String productName) {
        String finalXPath = String.format(btnAddItemToCart, productName);
        return driver.findElement(By.xpath(finalXPath));
    }


    //Function for filter
    public void clickFilter(String filterText) {
        select = new Select(getBtnProductSortContainer());
        select.selectByVisibleText(filterText);
    }

    public ProductDetailPage clickRandomProduct() {
        List<WebElement> products = productList(); // All product containers

        if (products.isEmpty()) {
            throw new IllegalStateException("No products found on the page!");
        }
        int randomIndex = new Random().nextInt(products.size()); // random 0 to (size - 1)

        WebElement randomProduct = products.get(randomIndex)
                .findElement(By.xpath(lblInventoryName)); // clickable name inside product

        String productName = randomProduct.getText();
        System.out.println("🔍 Clicking on random product: " + productName);

        keywords.click(randomProduct); // your custom click method
        return new ProductDetailPage(driver);
    }

    //Actions
    public HomePage clickMenu() {
        getBtnMenu().click();
        return new HomePage(driver);
    }

    public HomePage clickCloseMenu() {
        getBtnCloseMenu().click();
        return new HomePage(driver);
    }

    public WebElement clickLogout() {
        getBtnLogout().click();
        return wait.until(ExpectedConditions.elementToBeClickable(getBtnLogout()));
    }

    public HomePage clickTwitterIcon() {
        getLnkTwitter().click();
        return new HomePage(driver);
    }

    public HomePage clickFacebookIcon() {
        getLnkFacebook().click();
        return new HomePage(driver);
    }

    public HomePage clickLinkedinIcon() {
        getLnkLinkedin().click();
        return new HomePage(driver);
    }

    public CartPage clickShoppingCartButton() {
        getBtnShoppingCart().click();
        return new CartPage(driver);
    }

    public int getItemsCount() {
        return productList().size();
    }

    public ProductDetailPage clickFirstProduct() {
        keywords.click(productList().get(0).findElement(By.xpath(lblInventoryName)));
        return new ProductDetailPage(driver);
    }

    public ProductDetailPage clickSecondProduct() {
        keywords.click(productList().get(1).findElement(By.xpath(lblInventoryName)));
        return new ProductDetailPage(driver);
    }


    public void filterAToZ() {
        clickFilter(A_TO_Z);
    }

    public void filterZToA() {
        clickFilter(Z_TO_A);
    }

    public void filterLowToHigh() {
        clickFilter(LOW_TO_HIGH);
    }

    public void filterHighToLow() {
        clickFilter(HIGH_TO_LOW);
    }

    public boolean isShoppingCartDisplayed(){
        boolean displayed = getBtnShoppingCart().isDisplayed();
        return displayed;
    }

    public String getUrlFromNewTabNavigate() {
        Set<String> allWindows = driver.getWindowHandles();
        List<String> allWindowsList = allWindows.stream().toList();
        driver.switchTo().window(allWindowsList.get(1));
        String pageUrl = driver.getCurrentUrl();
        driver.close();
        driver.switchTo().window(allWindowsList.get(0));
        return pageUrl;
    }

    public void addItemToCart(String productName) {
        try {
            Log.info("Attempting to add product: '{}'", productName);
            // Get the button WebElement using the method that should include waits
            WebElement addButton = getBtnAddItemToCart(productName);
            Log.info("Found button for '{}', attempting click.", productName);
            addButton.click();
            Log.info("Clicked 'Add to cart' (or 'Remove') for product: '{}'", productName);
            // Optional: Add a wait here to ensure the button text changes to "Remove"
            // e.g., wait.until(ExpectedConditions.textToBePresentInElement(addButton, "Remove"));

        } catch (Exception ex) { // Catch the general Exception
            // Log the error with product name and exception details
            LoggerFactory.getLogger(HomePage.class).error("Error occurred while trying to add product '{}' to cart: {}", productName, ex.getMessage(), ex);
            // Re-throw the exception to ensure the test step fails
            throw ex;
        }
    }
}

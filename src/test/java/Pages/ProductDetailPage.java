package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductDetailPage extends SauceDemoBasePage{
    public ProductDetailPage(WebDriver driver){super(driver);}

    //Locators
    private static final String btnAddToCart = "//*[contains(@class,'btn_inventory')]";
    private static final String lblBackToProducts = "//*[@id = 'back-to-products']";
    private static final String lblItemName = "//*[@data-test = 'inventory-item-name']";
    private static final String lblItemPrice = "//*[@data-test = 'inventory-item-price']";
    private static final String lblDetailItem = "//*[@data-test = 'inventory-item-desc']";

    //Elements
    public WebElement getBtnAddToCart(){return driver.findElement(By.xpath(btnAddToCart));}
    public WebElement getLblBackToProducts(){return driver.findElement(By.xpath(lblBackToProducts));}
    public WebElement getLblItemName(){return driver.findElement(By.xpath(lblItemName));}
    public WebElement getLblItemPrice(){return driver.findElement(By.xpath(lblItemPrice));}
    public WebElement getLblDetailItem(){return driver.findElement(By.xpath(lblDetailItem));}


    //Action
    public ProductDetailPage addProductToCart(){
        getBtnAddToCart().click();
        return new ProductDetailPage(driver);
    }

    public HomePage backToProductsAll(){
        getLblBackToProducts().click();
        return new HomePage(driver);
    }

    public String getItemName(){
        return getLblItemName().getText();
    }
    public String getItemPrice(){
        return getLblItemPrice().getText();
    }
    public String getItemDetail(){
        return getLblDetailItem().getText();
    }

}

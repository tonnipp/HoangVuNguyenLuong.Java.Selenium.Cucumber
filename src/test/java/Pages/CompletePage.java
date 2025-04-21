package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CompletePage extends SauceDemoBasePage{
    public CompletePage(WebDriver driver){super(driver);}
    //Locators
    private static final String lblSuccesHeader = "complete-header";


    //Elements
    public WebElement getLblSuccesHeader(){return driver.findElement(By.className(lblSuccesHeader));}

    //Actions
    public boolean isSuccessHeaderDisplay(){return getLblSuccesHeader().isDisplayed();}
}

package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OverviewPage extends SauceDemoBasePage{
    public OverviewPage(WebDriver driver){super(driver);}

    //Locators
    private static final String btnFinish = "//*[@id = 'finish']";

    //Elements
    public WebElement getBtnFinish(){return driver.findElement(By.xpath(btnFinish));}

    //Actions
    public CompletePage finishCheckout(){
        getBtnFinish().click();
        return new CompletePage(driver);
    }
}

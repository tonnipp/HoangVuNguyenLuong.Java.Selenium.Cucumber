package keywords;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ActionKeywords {
    public WebDriver driver;
    public WebDriverWait wait;

    public ActionKeywords(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void openUrl(String url) throws Exception {
        if(!(url.startsWith("http://") || url.startsWith("https://"))){
            throw new Exception("Invalid URL format");
        }
        this.driver.get(url);
    }

    public WebElement findElement(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Scroll to the element before execute clicking.
     * @param elem
     */
    public void click(WebElement elem){
        Actions actions = new Actions(this.driver);
        actions.moveToElement(elem).build().perform();
        elem.click();
    }

    /**
     * Clear text before setting text
     * @param elem element to set text
     * @param text value to set to element
     */
    public void setText(WebElement elem, String text){
        try{
            elem.clear();
            elem.sendKeys(text);
        }
        catch (WebDriverException e){
            throw new WebDriverException("Element is not enable to set text");
        }
    }

    public void select(WebElement element, SelectType type, String options) throws Exception {
        Select select = new Select(element);
        switch (type)
        {
            case SELECT_BY_INDEX:
                try
                {
                    select.selectByIndex(Integer.parseInt(options));
                }
                catch (Exception e)
                {
                    throw new Exception("Please input numberic on selectOption for SelectType.SelectByIndex");
                }
                break;
            case SELECT_BY_TEXT:
                select.selectByVisibleText(options);
                break;
            case SELECT_BY_VALUE:
                select.selectByValue(options);
                break;
            default:
                throw new Exception("Get error in using Selected");
        }
    }

    public enum SelectType {
        SELECT_BY_INDEX,
        SELECT_BY_TEXT,
        SELECT_BY_VALUE,
    }
}

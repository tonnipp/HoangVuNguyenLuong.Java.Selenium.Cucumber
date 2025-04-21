package Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class LoginPage extends SauceDemoBasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }


    //Locators
    private static final String txtUserName = "//*[@id = 'user-name']";
    private static final String txtPassword = "//*[@id = 'password']";
    private static final String btnLogin = "//*[@id = 'login-button']";
    private static final String errorMessageText = "//*[@data-test = 'error']";

    //Elements
    public WebElement getTxtUserName(){return driver.findElement(By.xpath(txtUserName));}
    public WebElement getTxtPassword(){return driver.findElement(By.xpath(txtPassword));}
    public WebElement getBtnLogin(){return driver.findElement(By.xpath(btnLogin));}
    public WebElement getErrorMessage(){return driver.findElement(By.xpath(errorMessageText));}

    //Actions
    public HomePage loginToSystem(String username, String password) {
        getTxtUserName().sendKeys(username);
        getTxtPassword().sendKeys(password);
        keywords.click(getBtnLogin());
        return new HomePage(driver);
    }

    public LoginPage loginPage(){
        getTxtUserName().sendKeys();
        getTxtPassword().sendKeys();
        keywords.click(getBtnLogin());
        return new LoginPage(driver);
    }

    public LoginPage checkErrorMessage(){
        getErrorMessage().getText();
        return new LoginPage(driver);
    }

    public boolean isErrorMessageDisplay(){return getErrorMessage().isDisplayed();}
    public boolean isLoginButtonDisplay(){return getBtnLogin().isDisplayed();}
}


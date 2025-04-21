package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InformationPage extends SauceDemoBasePage{
    public InformationPage(WebDriver driver){super(driver);}

    //Locators
    private static final String txtFirstName = "//*[@id = 'first-name']";
    private static final String txtLastName = "//*[@id = 'last-name']";
    private static final String txtPostCode = "//*[@id = 'postal-code']";
    private static final String btnContinue = "//*[@id = 'continue']";
    private static final String btnCancel = "//*[@id = 'cancel']";
    private static final String lblErrorMessage = "//*[@data-test = 'error']";


    //Elements
    public WebElement getTxtFirstName(){return driver.findElement(By.xpath(txtFirstName));}
    public WebElement getTxtLastName(){return driver.findElement(By.xpath(txtLastName));}
    public WebElement getTxtPostCode(){return driver.findElement(By.xpath(txtPostCode));}
    public WebElement getBtnContinue(){return driver.findElement(By.xpath(btnContinue));}
    public WebElement getBtnCancel(){return driver.findElement(By.xpath(btnCancel));}
    public WebElement getLblErrorMessage(){return driver.findElement(By.xpath(lblErrorMessage));}

    //Actions
    public InformationPage inputCheckoutInformantion(String firstName, String lastName, String postCode){
        keywords.setText(getTxtFirstName(),"First Name");
        keywords.setText(getTxtLastName(), "Last Name");
        keywords.setText(getTxtPostCode(), "700000");
        return new InformationPage(driver);
    }

    public InformationPage continueOrder(){
        getBtnContinue().click();
        return new InformationPage(driver);
    }
    public OverviewPage checkInputInformation(String firstName, String lastName, String postCode){
        inputCheckoutInformantion(firstName, lastName, postCode);
        continueOrder();
        return new OverviewPage(driver);
    }
    public HomePage cancelInputInformation(){
        getBtnCancel().click();
        return new HomePage(driver);
    }

    public boolean isDisplayFullElements(){
        if (getTxtFirstName().isDisplayed() && getTxtLastName().isDisplayed() && getTxtPostCode().isDisplayed() && getBtnContinue().isDisplayed()){
            return true;
        }
        else return false;
    }

    public InformationPage getErrorMessage(){
        getLblErrorMessage().getText();
        return new InformationPage(driver);
    }

    public boolean isErrorMessageDisplay(){return getLblErrorMessage().isDisplayed();}

}

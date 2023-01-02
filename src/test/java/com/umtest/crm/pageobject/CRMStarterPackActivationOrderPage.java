package com.umtest.crm.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class CRMStarterPackActivationOrderPage extends MainUtil {
    private RemoteWebDriver driver;

    public CRMStarterPackActivationOrderPage(RemoteWebDriver driver) {
        this.driver = driver;
    }

    @FindBy(xpath = "//button[contains(text(),'Next')]")
    private WebElement nextButton;

    @FindBy(xpath = "//button[@class='btn btn-primary js-ok']")
    private WebElement orderValidationResultsOkBtn;

    @FindBy(xpath = "//span[contains(text(),'Has confirmed the order with customer?')]/preceding-sibling::div/input")
    private WebElement confirmCheckBox;

    @FindBy(xpath = "//button[@class='btn btn-primary js-btn-next']")
    private WebElement confirmNextBtn;

    @FindBy(xpath = "//input[@class='js-check-bypass']")
    private WebElement bypassAcknowledgeCheckBox;

    @FindBy(xpath = "//button[@class='btn btn-primary js-btn-pay']")
    private WebElement payButton;

    @FindBy(xpath = "//span[contains(text(),'Starter Pack Activation')]/parent::div/span[@class='da-val']")
    private WebElement starterPackMSISDN;

    @FindBy(xpath = "//div[contains(@class,'oe-print-td')]/span[contains(text(),'Unlimited Funz')]")
    private WebElement planName;

    @FindBy(xpath = "//h4[contains(text(),'Submit Successfully！')]")
    private WebElement successMessage;

    @FindBy(xpath = "//button[@id='back-oe-intro']")
    private WebElement confirmButton;

    public WebElement getNextButton() {
        return AppWait.waitForElementForVisibility(driver, nextButton);
    }

    public WebElement getOrderValidationResultsOkBtn() {
        return AppWait.waitForElementForVisibility(driver, orderValidationResultsOkBtn);
    }

    public WebElement getConfirmCheckBox() {
        return AppWait.waitForElementForVisibility(driver, confirmCheckBox);
    }

    public WebElement getConfirmNextBtn() {
        return AppWait.waitForElementForVisibility(driver, confirmNextBtn);
    }

    public WebElement getBypassAcknowledgeCheckBox() {
        return AppWait.waitForElementForVisibility(driver, bypassAcknowledgeCheckBox);
    }

    public WebElement getPayButton() {
        return AppWait.waitForElementForVisibility(driver, payButton);
    }

    public WebElement getStarterPackMSISDN() {
        return AppWait.waitForElementForVisibility(driver, starterPackMSISDN);
    }

    public WebElement getPlanName() {
        return AppWait.waitForElementForVisibility(driver, planName);
    }

    public WebElement getSuccessMessage() {
        return AppWait.waitForElementForVisibility(driver, successMessage);
    }

    public WebElement getConfirmButton() {
        return AppWait.waitForElementForVisibility(driver, confirmButton);
    }

}

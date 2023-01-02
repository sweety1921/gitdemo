package com.umtest.umb.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;

public class UMBDialSimulatorPage extends MainUtil {

	private RemoteWebDriver driver;

	public UMBDialSimulatorPage(RemoteWebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//a[@href='#' and text()='SIMULATION AND TRACING']")
	private WebElement linkSimulationAndTracingMenu;

	@FindBy(xpath = "//a[contains(text(),'Simulation')]")
	private WebElement linkSimulation;
	
	@FindBy(xpath = "//a[contains(text(),'Phonesim')]")
	private WebElement linkPhonesim;
	
	@FindBy(xpath = "//td[contains(text(),'MSISDN')]//parent::td/following-sibling::td/input[@maxlength='21']")
	private WebElement textboxMSISDN;
	
	@FindBy(xpath = "//input[@id='userdata']")
	private WebElement textboxUSSDCode;
	
	@FindBy(xpath = "//button[contains(text(),'Send')]")
	private WebElement buttonSend;

	@FindBy(xpath = "//div[@id='content_container']//div//table//tbody//tr//td//div")
	private WebElement textareaUSSDResponse;
	
	@FindBy(xpath = "//button[contains(text(),'Reset USSD')]")
	private WebElement buttonResetUSSD;
	
	@FindBy(xpath = "//button[contains(text(),'Clear Display')]")
	private WebElement buttonClear;



	public WebElement getlinkSimulationAndTracingMenu() {
		return AppWait.waitForElementToBeClickable(driver, linkSimulationAndTracingMenu);
	}

	public WebElement getlinkSimulation() {
		return AppWait.waitForElementToBeClickable(driver, linkSimulation);
	}

	public WebElement getLinkPhonesim() {
		return AppWait.waitForElementToBeClickable(driver, linkPhonesim);
		//return AppWait.waitForElementToBeClickable(driver, "//a[contains(text(),'Phonesim')]");
	}
	
	public WebElement gettextboxMSISDN() {
		return AppWait.waitForElementToBeClickable(driver, textboxMSISDN);
		//return AppWait.waitForElementToBeClickable(driver, "//tr[7]//td[4]//input[1]");
	}
	
	public WebElement getTextboxUSSDCode() {
		return AppWait.waitForElementToBeClickable(driver, textboxUSSDCode);
		//return AppWait.waitForElementToBeClickable(driver, "//input[@id='userdata']");
	}
	
	public WebElement getbuttonSend() {
		return AppWait.waitForElementToBeClickable(driver, buttonSend);
		//return AppWait.waitForElementToBeClickable(driver, "//button[contains(text(),'Send')]");
	}
	
	public WebElement gettextareaUSSDResponse() {
		return AppWait.waitForElementToBeClickable(driver, textareaUSSDResponse);
		//return AppWait.waitForElementToBeClickable(driver, "//div[@id='content_container']//div//table//tbody//tr//td//div");
	}
	
	public WebElement getbuttonResetUSSD() {
		return AppWait.waitForElementToBeClickable(driver, buttonResetUSSD);
		//return AppWait.waitForElementToBeClickable(driver, "//button[contains(text(),'Reset USSD')]");
	}
	
	public WebElement getbuttonClear() {
		return AppWait.waitForElementToBeClickable(driver, buttonClear);
		//return AppWait.waitForElementToBeClickable(driver, "//button[contains(text(),'Clear Display')]");
	}
	
	
	
	
}
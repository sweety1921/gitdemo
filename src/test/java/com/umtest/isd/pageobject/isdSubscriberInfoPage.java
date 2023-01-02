package com.umtest.isd.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;

public class isdSubscriberInfoPage extends MainUtil {

	private RemoteWebDriver driver;
	public isdSubscriberInfoPage(RemoteWebDriver driver) {
		this.driver = driver;
		
	}
	@FindBy(xpath="//body/div[@id='wrapper']/nav[@class='navbar-default navbar-static-side sidebar']/div[@class='sidebar-collapse']/ul[@id='side-menu']/li[7]/a[1]")
	//@FindBy(xpath="ul[@id='side-menu']/li[7]/a")
	private WebElement menuUMBandBLC;
	
	@FindBy(xpath="//a[contains(text(),'BLC SM Tool')]")
	private WebElement menuBLCSMTool;
	
	@FindBy(xpath="//select[@id='arapi']")
	private WebElement dropdownOption;
	
	@FindBy(xpath="//th[contains(text(),'Operation')]")
	private WebElement tableHeader;
	
	@FindBy(xpath="//option[contains(text(),'exists')]")
	private WebElement selectExist;
	
	@FindBy(xpath="//input[@id='MSISDN']")
	private WebElement textMSISDN;
	
	@FindBy(xpath="//button[@id='submission']")
	private WebElement buttonSubmit;
	
	@FindBy(xpath="//table[@class='table table-bordered table-hover']")
	private WebElement table;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[1]")
	private WebElement tableData1;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[2]")
	private WebElement tableData2;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[3]")
	private WebElement tableData3;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[4]")
	private WebElement tableData4;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[5]")
	private WebElement tableData5;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[6]")
	private WebElement tableData6;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[7]")
	private WebElement tableData7;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[8]")
	private WebElement tableData8;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[9]")
	private WebElement tableData9;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[10]")
	private WebElement tableData10;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[11]")
	private WebElement tableData11;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[12]")
	private WebElement tableData12;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[13]")
	private WebElement tableData13;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[14]")
	private WebElement tableData14;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[15]")
	private WebElement tableData15;
	
	@FindBy(xpath="//div[@class='table-responsive']/table/tbody/tr/td[16]")
	private WebElement tableData16;
	
	
	
	
	public WebElement getMenuUMBandBLC() {
		return AppWait.waitForElementForVisibility(driver, menuUMBandBLC);
	}
	public WebElement getMenuBLCSMTool() {
		return AppWait.waitForElementForVisibility(driver, menuBLCSMTool);
	}
	public WebElement getSelectExist() {
		return AppWait.waitForElementForVisibility(driver, selectExist);
	}
	public WebElement getTextMSISDN() {
		return AppWait.waitForElementForVisibility(driver, textMSISDN);
	}
	public WebElement getButtonSubmit() {
		return AppWait.waitForElementForVisibility(driver, buttonSubmit);
	}
	public WebElement getdropdownOption() {
		return AppWait.waitForElementForVisibility(driver, dropdownOption);
	}
	
	public WebElement getTable() {
		return AppWait.waitForElementForVisibility(driver, table);
	}
	
	public WebElement getTableHeader() {
		return AppWait.waitForElementForVisibility(driver, tableHeader);
	}
	
	public WebElement gettableData1() {
		return AppWait.waitForElementForVisibility(driver, tableData1);
	}
	
	public WebElement gettableData2() {
		return AppWait.waitForElementForVisibility(driver, tableData2);
	}
	
	public WebElement gettableData3() {
		return AppWait.waitForElementForVisibility(driver, tableData3);
	}
	
	public WebElement gettableData4() {
		return AppWait.waitForElementForVisibility(driver, tableData4);
	}
	
	public WebElement gettableData5() {
		return AppWait.waitForElementForVisibility(driver, tableData5);
	}
	
	public WebElement gettableData6() {
		return AppWait.waitForElementForVisibility(driver, tableData6);
	}
	
	public WebElement gettableData7() {
		return AppWait.waitForElementForVisibility(driver, tableData7);
	}
	
	public WebElement gettableData8() {
		return AppWait.waitForElementForVisibility(driver, tableData8);
	}
	
	public WebElement gettableData9() {
		return AppWait.waitForElementForVisibility(driver, tableData9);
	}
	
	public WebElement gettableData10() {
		return AppWait.waitForElementForVisibility(driver, tableData10);
	}
	
	public WebElement gettableData11() {
		return AppWait.waitForElementForVisibility(driver, tableData11);
	}
	
	public WebElement gettableData12() {
		return AppWait.waitForElementForVisibility(driver, tableData12);
	}
	
	public WebElement gettableData13() {
		return AppWait.waitForElementForVisibility(driver, tableData13);
	}
	
	public WebElement gettableData14() {
		return AppWait.waitForElementForVisibility(driver, tableData14);
	}
	
	public WebElement gettableData15() {
		return AppWait.waitForElementForVisibility(driver, tableData15);
	}
	
	public WebElement gettableData16() {
		return AppWait.waitForElementForVisibility(driver, tableData16);
	}
	
}

package com.umtest.umrexportal.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;

import java.util.List;

public class UMREXPortalPage extends MainUtil {

	private RemoteWebDriver driver;

	public UMREXPortalPage(RemoteWebDriver driver) {
		this.driver = driver;
	}


	@FindBy(id = "txtUsername")
	private WebElement textboxUsername;
	
	@FindBy(id = "txtPassword")
	private WebElement textboxPassword;
	
	@FindBy(id = "btnLogin")
	private WebElement buttonLogin;
	
	@FindBy(xpath = "//span[@class='menu--label'][text()='Report']")
	private WebElement buttonReports;
	
	@FindBy(xpath = "//li[@class='sub_menu--item mySubMenu RegistrationReport']//a[@class='sub_menu--link'][contains(text(),'Registration Report')]")
	private WebElement buttonRegistrationReport;

	@FindBy(xpath = "//li[@class='sub_menu--item mySubMenu PurchaseReport']//a[@class='sub_menu--link'][contains(text(),'Purchase Report')]")
	private WebElement buttonPurchaseReport;
	
	@FindBy(id = "btn-search")
	private WebElement buttonSearch;
	
	@FindBy(xpath = "//input[@type='search']")
	private WebElement textboxSearch;
	
	@FindBy(xpath = "(//td[@class=' td-GroupCodeLimit'])[5]")
	private WebElement labelRegistrationStatus;

	@FindBy(xpath = "//div[@class='dataTables_scrollHeadInner']/child::table[@class='table table-striped table-bordered display nowrap dataTable no-footer']/descendant::tr[1]/child::th")
	private List<WebElement> listRegistrationReportTableHeader;

	@FindBy(xpath = "//tbody[@class='table-body']/child::tr")
	private List<WebElement> listRegistrationReportTableResultRows;

	@FindBy(xpath = "//a[@class='menu--link logout-link' and @title='Logout']")
	private WebElement buttonLogout;

	@FindBy(xpath = "//td[@class=' dt-body-center'][12]")
	private WebElement umrexLabelRegistrationStatus;

	public WebElement getTextboxUsername() {
		return AppWait.waitForElementToBeClickable(driver, textboxUsername);
	}
	
	public WebElement getTextboxPassword() {
		return AppWait.waitForElementToBeClickable(driver, textboxPassword);
	}
	
	public WebElement getButtonLogin() {
		return AppWait.waitForElementToBeClickable(driver, buttonLogin);
	}

	public WebElement getButtonLogout() {
		return buttonLogout;
	}
	
	public WebElement getButtonReports() {
		return AppWait.waitForElementToBeClickable(driver, buttonReports);
	}
	
	public WebElement getButtonRegistrationReport() {
		return AppWait.waitForElementToBeClickable(driver, buttonRegistrationReport);
	}

	public WebElement getButtonPurchaseReport() {
		return AppWait.waitForElementToBeClickable(driver, buttonRegistrationReport);
	}
	
	public WebElement getButtonSearch() {
		return AppWait.waitForElementToBeClickable(driver, buttonSearch);
	}
	
	public WebElement getTextboxSearch() {
		return AppWait.waitForElementToBeClickable(driver, textboxSearch);
	}
	
	public WebElement getLabelRegistrationStatus() {
//		return AppWait.waitForElementToBeClickable(driver, labelRegistrationStatus);
		return AppWait.waitForElementToBeClickable(driver, umrexLabelRegistrationStatus);
	}

	/*############################################################################################################################*/
	public Integer getHeaderColumnNumberRegistrationReportTable(String sColName) {

		int iSize=listRegistrationReportTableHeader.size();

		for(int iVar1=0;iVar1<iSize;iVar1++){

			String sColNameAct=listRegistrationReportTableHeader.get(iVar1).getText().trim();

			if(sColName.equalsIgnoreCase(sColNameAct)){
				return iVar1;
			}
		}
		return -1;
	}

	public String getRegistrationStatusRegistrationReportTable(String sMSISDN) {

		int iColNumberMSISDN=getHeaderColumnNumberRegistrationReportTable("MSISDN");

		if(!(iColNumberMSISDN==-1)){
//			int iRowsSize=listRegistrationReportTableResultRows.size();

			for(WebElement ele: listRegistrationReportTableResultRows){
				List<WebElement> sColElements = ele.findElements(By.xpath("//td[contains(@class,'dt-body-center')]"));

				String sActMSISDN=sColElements.get(iColNumberMSISDN).getText().trim();

				System.out.println("abc: "+sActMSISDN);


			}



/*
			for(int iVar1=0;iVar1<iRowsSize;iVar1++){

				List<WebElement> sColElements=listRegistrationReportTableResultRows.get(iVar1).findElements(By.xpath("//td[contains(@class,'dt-body-center')]"));

				String sActMSISDN=sColElements.get(iColNumberMSISDN).getText().trim();

				if(sActMSISDN.equalsIgnoreCase(sMSISDN)){
					int iColNumRegistrationStatus=getHeaderColumnNumberRegistrationReportTable("Registration Status");
					return sColElements.get(iColNumRegistrationStatus).getText().trim();
				}
			}*/
		}

		return "null";
	}



}

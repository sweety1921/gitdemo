package com.umtest.umrexportal.pageobject;

import com.umtest.erechargeportal.pageobject.ERECHARGEPORTALTransactionsPage;
import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.umrexportal.pagefunction.UMREXPortalFuncs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static com.umtest.testframe.listener.ExtentTestNGITestListener.getTest;

public class UMREXPortalTopUpReportsPage extends MainUtil {

	private RemoteWebDriver driver;
	private UMREXPortalFuncs umrexPortal;

	private static Logger logger = LogManager.getLogger(UMREXPortalTopUpReportsPage.class);

	public UMREXPortalTopUpReportsPage(RemoteWebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	private String sXpathTopUpReportsTableCellValue="(//tbody/child::tr[iVarRowNo])[1]/child::td[iVarColNo]";

	@FindBy(xpath = "//li[@class='sub_menu--item mySubMenu TopUpReport']/child::a[contains(text(),'Top Up Report')]")
	private WebElement sideSubMenuTopUpReports;

	@FindBy(id = "btn-search")
	private WebElement buttonSearch;
	
	@FindBy(xpath = "//label[text()='Search:']/child::input[@type='search']")
	private WebElement textboxSearch;

	@FindBy(xpath = "//thead/child::tr[1]/child::th[@aria-controls='result-table' and @tabindex='0']")
	private List<WebElement> tableHeaderList;

	public WebElement getSideSubMenuTopUpReports(){
		return sideSubMenuTopUpReports;
	}
	public WebElement getSearchButton() {
		return buttonSearch;
	}

	public WebElement getSearchTextbox() {
		return textboxSearch;
	}

	public int getColNumByName(String sColName){

		try{
			for(WebElement el:tableHeaderList){
				if(el.getText().replaceAll("\\s+"," ").equalsIgnoreCase(sColName)){
					System.out.println(sColName);
					System.out.println("Index - "+tableHeaderList.indexOf(el));
					return tableHeaderList.indexOf(el);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting column number for a given column name - "+sColName+" :" + e);
		}
		return -1;
	}

	public Object getResultTableCellValue(String sRow, String sCol){
		String sXpathTopUpReportsTableCellValue1=sXpathTopUpReportsTableCellValue.replace("iVarRowNo",sRow);
		String sXpathTopUpReportsTableCellValue2=sXpathTopUpReportsTableCellValue1.replace("iVarColNo",sCol);

		return getTextUsingXpath(sXpathTopUpReportsTableCellValue2,"Top Up Reports table Cell Value ("+sRow+","+sCol+")", driver);
	}

	public void getTopUpReportTopResult(){

		try{
			String sTransID =getResultTableCellValue("1",Integer.toString(getColNumByName("uPay Transaction ID")+1)).toString();
			String sDateTime =getResultTableCellValue("1",Integer.toString(getColNumByName("Top Up Date Time")+1)).toString();
			String sMSISDN =getResultTableCellValue("1",Integer.toString(getColNumByName("MSISDN")+1)).toString();
			String sAmount =getResultTableCellValue("1",Integer.toString(getColNumByName("Amount (RM)")+1)).toString();
			String sStatus =getResultTableCellValue("1",Integer.toString(getColNumByName("Status")+1)).toString();

			MainUtil.dictionary.put("UMREX_PORTAL_TRANSACTION_ID", sTransID);
			MainUtil.dictionary.put("UMREX_PORTAL_ACT_DATE_TIME", sDateTime);
			MainUtil.dictionary.put("UMREX_PORTAL_ACT_AMOUNT", sAmount);
			MainUtil.dictionary.put("UMREX_PORTAL_ACT_MSISDN", sMSISDN);
			MainUtil.dictionary.put("UMREX_PORTAL_ACT_STATUS", sStatus);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting Transaction Details in eRecharge Portal  :" + e);
		}
	}

	public void verifyTopUpReport(String sExpMSISDN, String sExpTopUpAmount,String sExpTransactionID, String sExpDateTime, String sStatus){
		try {
			umrexPortal=new UMREXPortalFuncs(driver);
			umrexPortal.loginUMREXPortal();

			clickElement(umrexPortal.getButtonReports(), "Reports side menu option", driver);
			clickElement(getSideSubMenuTopUpReports(), "Top Up Report sub menu option", driver);
			clickElement(getSearchButton(), "Search Button", driver);
			sendKeys(getSearchTextbox(), sExpTransactionID, "Transaction ID", "", driver);

			getTopUpReportTopResult();

			sExpDateTime=changeTopUpDateTimeFormat(sExpDateTime,"dd/MM/yyyy HH:mm:ss");

			sExpDateTime=sExpDateTime.substring(0,16);
			String sActDateTime=dictionary.get("UMREX_PORTAL_ACT_DATE_TIME");
			sActDateTime=sActDateTime.substring(0,16);

			compareWholeString(sActDateTime,sExpDateTime,"Top Up Date Time");
			compareWholeString(dictionary.get("UMREX_PORTAL_ACT_MSISDN").replace("-",""),sExpMSISDN,"MSISDN");
			compareWholeString(dictionary.get("UMREX_PORTAL_ACT_AMOUNT"),sExpTopUpAmount,"Amount (RM)");
			compareWholeString(dictionary.get("UMREX_PORTAL_TRANSACTION_ID"),sExpTransactionID,"uPay Transaction ID");
			compareWholeString(dictionary.get("UMREX_PORTAL_ACT_STATUS"),sStatus,"Status");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while verifying prepaid topup in UMREX Portal :" + e);
		}
	}



	public static String changeTopUpDateTimeFormat(String dateValue,String newFormat) {
		String formattedDate=null;

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat formatter2 = new SimpleDateFormat(newFormat);
			Date date = formatter.parse(dateValue);
			formattedDate= formatter2.format(date);
			Log.info(formattedDate);

		} catch (Exception e) {
			logger.error("Exception occurred", e);
		}
		return formattedDate;
	}
}

package com.umtest.erechargeportal.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.reporters.jq.Main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ERECHARGEPORTALTransactionsPage extends MainUtil {

	private RemoteWebDriver driver;
	private ERECHARGEPORTALLoginLogoutPage erechargeportalLogin;
	ERECHARGEPORTALTopUpPage erechargeportalTopUpPage;
	UMBVerificationFuncs umbVerificationFuncs;
	private static Logger logger = LogManager.getLogger(ERECHARGEPORTALTransactionsPage.class);

	public ERECHARGEPORTALTransactionsPage(RemoteWebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	private String sXpathTransTableCellValue="(//tbody/child::tr[iVarRowNo])[1]/child::td[iVarColNo]";
	private String sXpathViewTransDetails="(//tbody/child::tr[iVarRowNo])[1]/child::td[iVarColNo]/descendant::i[@title='View transaction details.']";

	@FindBy(xpath = "//thead/tr[@__gwt_header_row='0']/th")
	private List<WebElement> tableHeaderList;

	@FindBy(xpath = "(//button[@title='Download Transaction Report']/following::input[@class='gwt-TextBox'])[1]")
	private WebElement textboxSearch;

	@FindBy(xpath = "(//button[@title='Search'])[1]")
	private WebElement buttonSearchIcon;

	@FindBy(xpath = "(//div[contains(text(),'No result')])[1]")
	private WebElement textNoResultMessage;

	@FindBy(xpath = "(//th[text()='Opening'])[1]/following::td[1]")
	private WebElement textOpeningBalanceTransDetailsPopUp;

	@FindBy(xpath = "(//th[text()='Closing'])[1]/following::td[1]")
	private WebElement textClosingBalanceTransDetailsPopUp;

	public WebElement getSearchTextbox(){
		return AppWait.waitForElementToBeClickable(driver,textboxSearch);
	}

	public String getOpeningBalance(){
		return getText(textOpeningBalanceTransDetailsPopUp);
	}

	public String getClosingBalance(){
		return getText(textClosingBalanceTransDetailsPopUp);
	}

	public void clickViewTransactionDetails(){
		String sXpathViewTransDetails1=sXpathViewTransDetails.replace("iVarRowNo","1");
		String sXpathViewTransDetails2=sXpathViewTransDetails1.replace("iVarColNo",Integer.toString(getColNumByNameTransactionTable("STATUS")+2));

		clickElementUsingXpathString(sXpathViewTransDetails2,"View Transaction",driver);
	}

	public int getColNumByNameTransactionTable(String sColName){

		try{
			for(WebElement el:tableHeaderList){
				if(el.getText().equalsIgnoreCase(sColName)){
					return tableHeaderList.indexOf(el);
				}
				}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting column number for a given column name - "+sColName+" :" + e);
		}
		return -1;
	}

	public Object getTransactionTableCellValue(String sRow, String sCol){
		String sXpathTransTableCellValue1=sXpathTransTableCellValue.replace("iVarRowNo",sRow);
		String sXpathTransTableCellValue2=sXpathTransTableCellValue1.replace("iVarColNo",sCol);

		return getTextUsingXpath(sXpathTransTableCellValue2,"Transaction table cell value ("+sRow+","+sCol+")", driver);
	}

	public void getTransactionDetails(String sTransactionID){

		try{
			clickElement(getSearchTextbox(),"Search",driver);
			sendKeys(getSearchTextbox(), sTransactionID, "Search Transaction ID", "", driver);
			clickElement(buttonSearchIcon,"Search icon", driver);

			String sTransID =getTransactionTableCellValue("1",Integer.toString(getColNumByNameTransactionTable("TRANSACTION ID")+1)).toString();
			String sDateTime =getTransactionTableCellValue("1",Integer.toString(getColNumByNameTransactionTable("DATE & TIME")+1)).toString();
			String sMSISDN =getTransactionTableCellValue("1",Integer.toString(getColNumByNameTransactionTable("TO MSISDN")+1)).toString();
			String sAmount =getTransactionTableCellValue("1",Integer.toString(getColNumByNameTransactionTable("AMOUNT")+1)).toString();
			String sStatus =getTransactionTableCellValue("1",Integer.toString(getColNumByNameTransactionTable("STATUS")+1)).toString();

			MainUtil.dictionary.put("TRANSACTION_ID", sTransID);
			MainUtil.dictionary.put("ACT_DATE_TIME", sDateTime);
			MainUtil.dictionary.put("ACT_AMOUNT", "RM "+sAmount);
			MainUtil.dictionary.put("ACT_MSISDN", sMSISDN);
			MainUtil.dictionary.put("ACT_STATUS", sStatus);

			clickViewTransactionDetails();

			MainUtil.dictionary.put("ACT_OPENING_BALANCE",getOpeningBalance());
			MainUtil.dictionary.put("ACT_CLOSING_BALANCE",getClosingBalance());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting Transaction Details in eRecharge Portal  :" + e);
		}

	}

   public void verifyTopUp(String sExpMSISDN, String sExpTopUpAmount,String sExpTransactionID, String sExpDateTime,  String sExpClosingBalance){

	    String sActualDateTime="";
	    String sExpOpeningBalance="";
	    double iExpOpeningBalance=0;

	    erechargeportalLogin=new ERECHARGEPORTALLoginLogoutPage(driver);

		try{
		   erechargeportalLogin.login();
		   getTransactionDetails(sExpTransactionID);
		   erechargeportalLogin.logout();

		   System.out.println("ACT_MSISDN - "+dictionary.get("ACT_MSISDN"));
		   System.out.println("ACT_AMOUNT - "+dictionary.get("ACT_AMOUNT"));
		   System.out.println("ACT_STATUS - "+dictionary.get("ACT_STATUS"));
		   System.out.println("ACT_OPENING_BALANCE - "+dictionary.get("ACT_OPENING_BALANCE"));
		   System.out.println("ACT_CLOSING_BALANCE - "+dictionary.get("ACT_CLOSING_BALANCE"));
		   System.out.println("ACT_DATE_TIME - "+dictionary.get("ACT_DATE_TIME"));

		   sActualDateTime=changeTransDateTimeFormat(dictionary.get("ACT_DATE_TIME"),"yyyy-MM-dd HH:mm:ss");

		   iExpOpeningBalance=convertTransAmountStringToDouble(sExpTopUpAmount)+convertTransAmountStringToDouble(sExpClosingBalance);
		   sExpOpeningBalance="RM "+formatDoubleWithDecimalPrecision(iExpOpeningBalance);

		   compareWholeString(dictionary.get("TRANSACTION_ID"), sExpTransactionID,"Transaction ID");
		   compareWholeString(dictionary.get("ACT_MSISDN"), sExpMSISDN,"MSISDN");
		   compareWholeString(sActualDateTime, sExpDateTime,"Date Time");
		   compareWholeString(dictionary.get("ACT_AMOUNT"), sExpTopUpAmount,"Top Up Amount");
		   compareWholeString(dictionary.get("ACT_STATUS"), "OK","Transaction Status");
		   compareWholeString(dictionary.get("ACT_OPENING_BALANCE"), sExpOpeningBalance,"Opening Balance");
		   compareWholeString(dictionary.get("ACT_CLOSING_BALANCE"), sExpClosingBalance,"Closing Balance");

	   } catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while verifying Transaction Details in eRecharge Portal  :" + e);
		}
   }

	public static String changeTransDateTimeFormat(String dateValue,String newFormat) {
		String formattedDate=null;

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			SimpleDateFormat formatter2 = new SimpleDateFormat(newFormat);
			Date date = formatter.parse(dateValue);
			formattedDate= formatter2.format(date);
			Log.info(formattedDate);

		} catch (Exception e) {
			logger.error("Exception occurred", e);
		}
		return formattedDate;
	}


	public double convertTransAmountStringToDouble(String sAmount) throws ParseException {

		String[] aAmount=sAmount.split(" ");
		String aAmount1=aAmount[1];

		double iAmount=NumberFormat.getNumberInstance(Locale.ENGLISH).parse(aAmount1).doubleValue();
		return iAmount;
	}

	public String formatDoubleWithDecimalPrecision(double dValue) throws ParseException {
		DecimalFormat df = new DecimalFormat("###,###,###.00");

		return df.format(dValue);
	}

	/*public void maintainMSISDNBalance(String MSISDN, String sAmount, String sMinAmountCheck, RemoteWebDriver driver) throws Exception {

		erechargeportalTopUpPage = new ERECHARGEPORTALTopUpPage(driver);
		erechargeportalLogin = new ERECHARGEPORTALLoginLogoutPage(driver);
		umbVerificationFuncs =new UMBVerificationFuncs(driver);

		String sBalanceAndExpiryDate=umbVerificationFuncs.getPrepaidBalanceExpiryDate(MSISDN);
		String finalBalance;
		String[] accBalance = sBalanceAndExpiryDate.split("\\r?\\n");
		finalBalance = accBalance[2];
		finalBalance = finalBalance.substring(2);

	}*/
}




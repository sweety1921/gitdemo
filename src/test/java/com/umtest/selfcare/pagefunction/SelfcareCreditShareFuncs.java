package com.umtest.selfcare.pagefunction;

import com.umtest.selfcare.pageobject.SelfcareCreditSharePage;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class SelfcareCreditShareFuncs extends SelfcareCreditSharePage {
	private static Logger logger = LogManager.getLogger(SelfcareCreditShareFuncs.class);
	private RemoteWebDriver driver;
		
	public SelfcareCreditShareFuncs(RemoteWebDriver driver) {
		super(driver);
		this.driver =  driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
	}
	
	public boolean performCreditTransfer(String sReceiverMSISDN) {

		boolean methodReturn = false;

		try {
			clickCreditTransferMenu();
			clickCreditTransfer();
			selectTransferAmount(MainUtil.dictionary.get("TRANSFER_AMOUNT"));
			enterMobileNumber(sReceiverMSISDN);

			clickTransferCreditButton();
			
			methodReturn = MainUtil.compareInString(getsucessmsg().getText(),"Your request is currently being processed", "Verify the response message", driver);
			Thread.sleep(1000);
			captureAppScreenshot("Screen Capture: Message",driver);
//			clickCloseButton();
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ESCAPE);
			Thread.sleep(10000);
			
			if(methodReturn) {
				
				String	senderChargers = MainUtil.calculateBalance(MainUtil.dictionary.get("TRANSFER_AMOUNT"),"-" + ApplicationUtil.getSenderReceiverFee("sender_sfee",MainUtil.dictionary.get("TRANSFER_AMOUNT")));
				String	receivingAmount = MainUtil.calculateBalance(MainUtil.dictionary.get("TRANSFER_AMOUNT"), ApplicationUtil.getSenderReceiverFee("receiver_sfee",MainUtil.dictionary.get("TRANSFER_AMOUNT")));

				ApplicationUtil.updateAccInfoAfterCreditTransfer(MainUtil.dictionary.get("MSISDN"),"main_balance",MainUtil.calculateBalance(MainUtil.dictionary.get("CURRENT_BALANCE"),senderChargers));
				ApplicationUtil.updateAccInfoAfterCreditTransfer(sReceiverMSISDN,"credit_amount",MainUtil.calculateBalance(MainUtil.dictionary.get("CREDIT_AMOUNT_RECEIVER"),"-" + receivingAmount));
				
				methodReturn = true;
			}
				
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Change Pin :" + e);
		}
		return methodReturn;
		}

	public boolean performCreditTransfer_bkp(String sReceiverMSISDN) {

		boolean methodReturn = false;

		try {
			clickElement(getbuttonCredittransfer(), "Credit sharelink", driver);
			clickElement(getTransferCredit(), "Transfer Credit", driver);

			if (MainUtil.dictionary.get("TRANSFER_AMOUNT").equals("1")){
				clickElement(getbuttontrsfamount_1(), "Select Amount 1", driver);
			} else if (MainUtil.dictionary.get("TRANSFER_AMOUNT").equals("3")){
				clickElement(getbuttontrsfamount_3(), "Select Amount 3", driver);
			} else if (MainUtil.dictionary.get("TRANSFER_AMOUNT").equals("5")){
				clickElement(getbuttontrsfamount_5(), "Select Amount 5", driver);
			} else {
				clickElement(getbuttontrsfamount_10(), "Select Amount 5", driver);
			}

			sendKeys(getprephonenumber(), sReceiverMSISDN.substring(1, 4), "Pre Mobile Number", "", driver);

			sendKeys(getPhoneNo(), sReceiverMSISDN.substring(4, sReceiverMSISDN.length()), "Mobile Number", "", driver);

			clickElement(getbuttontransfercredit(), "Button Transfer Credit", driver);

			methodReturn = MainUtil.compareInString(getsucessmsg().getText(),"Your request is currently being processed", "Verify the response message", driver);

			clickElement(getClosebutton(), "Button Close", driver);
			Thread.sleep(10000);

			if(methodReturn) {

				String	senderChargers = MainUtil.calculateBalance(MainUtil.dictionary.get("TRANSFER_AMOUNT"),"-" + ApplicationUtil.getSenderReceiverFee("sender_sfee",MainUtil.dictionary.get("TRANSFER_AMOUNT")));
				String	receivingAmount = MainUtil.calculateBalance(MainUtil.dictionary.get("TRANSFER_AMOUNT"), ApplicationUtil.getSenderReceiverFee("receiver_sfee",MainUtil.dictionary.get("TRANSFER_AMOUNT")));

				ApplicationUtil.updateAccInfoAfterCreditTransfer(MainUtil.dictionary.get("MSISDN"),"main_balance",MainUtil.calculateBalance(MainUtil.dictionary.get("CURRENT_BALANCE"),senderChargers));
				ApplicationUtil.updateAccInfoAfterCreditTransfer(sReceiverMSISDN,"credit_amount",MainUtil.calculateBalance(MainUtil.dictionary.get("CREDIT_AMOUNT_RECEIVER"),"-" + receivingAmount));

				methodReturn = true;
			}


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Change Pin :" + e);
		}
		return methodReturn;
	}
}

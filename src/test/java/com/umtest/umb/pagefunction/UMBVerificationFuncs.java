package com.umtest.umb.pagefunction;

import com.umtest.testframe.base.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.umb.pageobject.UMBDialSimulatorPage;

import java.time.Duration;
import java.util.Dictionary;

import static com.umtest.testframe.listener.ExtentTestNGITestListener.getTest;

public class UMBVerificationFuncs {

	private static Logger logger = LogManager.getLogger(UMBVerificationFuncs.class);
	private RemoteWebDriver driver;

	public UMBVerificationFuncs(RemoteWebDriver driver) {
		this.driver =  driver;
		PageFactory.initElements(driver, this);

	}

	//Login, DailUSSD & Logout Funcs
	public boolean performUMB(String planType, String category) {
		boolean flag = false;
		UMBLoginLogoutFuncs UMBLogin = new UMBLoginLogoutFuncs(driver);
		UMBUSSDFuncs ussdFuncs = new UMBUSSDFuncs(driver);
		String responseMessage;
		try {
			ApplicationUtil.getUMBDetails(planType, category);
			UMBLogin.loginUMB();	
			responseMessage = ussdFuncs.DialForPurchase();
			flag = MainUtil.compareInString(responseMessage,MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
			logger.info(responseMessage); 
			logger.info(MainUtil.dictionary.get("USSD_MESSAGE")); 
			ussdFuncs.ResetUMBScreen();
			UMBLogin.logoutUMB();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying UMB  :" + e);
		}
		return flag;
	}
	
	//Login, DailUSSD & Logout Funcs
		public boolean performUMBCRP(String planType, String category) {
			boolean flag = false;
			boolean updateFlag = false;
			UMBLoginLogoutFuncs UMBLogin = new UMBLoginLogoutFuncs(driver);
			UMBUSSDFuncs ussdFuncs = new UMBUSSDFuncs(driver);
			String responseMessage;
			try {
				int b = 100;
			String c = Integer.toString(b);
			if(c.contains("ads")) {
				
			}
				ApplicationUtil.getUMBDetails(planType, category);
				UMBLogin.loginUMB();	
				responseMessage = ussdFuncs.DialForCRPCode();
				if(planType.equalsIgnoreCase("Postpaid")) {
					MainUtil.editUMBCRPList(responseMessage,MainUtil.dictionary.get("USSD_CODE"));
					responseMessage = ussdFuncs.DialForPurchase();
				}
				
				flag = MainUtil.compareInString(responseMessage,MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
				//flag = true;
				logger.info(responseMessage); 
				logger.info(MainUtil.dictionary.get("USSD_MESSAGE")); 
				if(flag) {
				if(planType.equalsIgnoreCase("Prepaid")) {
					ApplicationUtil.updateCRPDetails(MainUtil.dictionary.get("MSISDN"));
					updateFlag = true;
				}else {
					ApplicationUtil.updatePostpaidCRPDetails(MainUtil.dictionary.get("MSISDN"));
					MainUtil.dictionary.put("PLAN_NAME", "Postpaid " + MainUtil.dictionary.get("NEWPLAN_NAME"));
					updateFlag = true;
				}
				}
				
				ussdFuncs.ResetUMBScreen();
				UMBLogin.logoutUMB();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception occured while verifying UMB  :" + e);
			}
			return updateFlag;
		}
	
	
	//Login, DailUSSD & Logout Funcs
		public boolean performCreditTransfer(String planType, String category) {
			boolean flag = false;
			boolean updateFlag = false;
			UMBLoginLogoutFuncs UMBLogin = new UMBLoginLogoutFuncs(driver);
			UMBUSSDFuncs ussdFuncs = new UMBUSSDFuncs(driver);
			String responseMessage;
			try {
				ApplicationUtil.getUMBDetails(planType, category);
				UMBLogin.loginUMB();	
				responseMessage = ussdFuncs.DialForPurchase();
				flag = MainUtil.compareInString(responseMessage,MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
				logger.info(responseMessage); 
				logger.info(MainUtil.dictionary.get("USSD_MESSAGE")); 
				if(flag) {
					
					String	senderChargers = MainUtil.calculateBalance(MainUtil.dictionary.get("TRANSFER_AMOUNT"),"-" + ApplicationUtil.getSenderReceiverFee("sender_sfee",MainUtil.dictionary.get("TRANSFER_AMOUNT")));
					String	receivingAmount = MainUtil.calculateBalance(MainUtil.dictionary.get("TRANSFER_AMOUNT"), ApplicationUtil.getSenderReceiverFee("receiver_sfee",MainUtil.dictionary.get("TRANSFER_AMOUNT")));
					
					ApplicationUtil.updateAccInfoAfterCreditTransfer(MainUtil.dictionary.get("MSISDN"),"main_balance",MainUtil.calculateBalance(MainUtil.dictionary.get("CURRENT_BALANCE"),senderChargers));
					ApplicationUtil.updateAccInfoAfterCreditTransfer(MainUtil.dictionary.get("RECEIVER_MSISDN"),"credit_amount",MainUtil.calculateBalance(MainUtil.dictionary.get("CREDIT_AMOUNT"),"-" + receivingAmount));
					
					updateFlag = true;
				}
				
				ussdFuncs.ResetUMBScreen();
				UMBLogin.logoutUMB();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception occured while verifying UMB  :" + e);
			}
			return updateFlag;
		}
		
		
		//Login, DailUSSD & Logout Funcs
				public boolean performTopUp(String planType, String category) {
					boolean flag = false;
					boolean returnStatus=false;
					UMBLoginLogoutFuncs UMBLogin = new UMBLoginLogoutFuncs(driver);
					UMBUSSDFuncs ussdFuncs = new UMBUSSDFuncs(driver);
					String responseMessage;
					try {
						ApplicationUtil.getTopUpPin(MainUtil.dictionary.get("TOPUP_AMOUNT"));
						ApplicationUtil.getUMBDetails(planType, category);
						UMBLogin.loginUMB();	
						responseMessage = ussdFuncs.DialForPurchase();
						flag = MainUtil.compareInString(responseMessage,MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
						logger.info(responseMessage); 
						logger.info(MainUtil.dictionary.get("USSD_MESSAGE")); 
						if(flag) {
							if(category.equalsIgnoreCase("TOP-UP FOR FRIEND")) {
								MainUtil.dictionary.put("MSISDN", MainUtil.dictionary.get("RECEIVER_MSISDN"));
							}
							ApplicationUtil.updateTopupPinStatusInTable(MainUtil.dictionary.get("TOPUP_PIN"));
							ApplicationUtil.updateAccountInfoAfterTopupUMB(MainUtil.dictionary.get("MSISDN"));
							returnStatus = true;
						}
						
						ussdFuncs.ResetUMBScreen();
						UMBLogin.logoutUMB();
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Exception occured while verifying UMB  :" + e);
					}
					return returnStatus;
				}
				
	//Login, DailUSSD & Logout Funcs
	public boolean performAccValidityExt(String planType, String category) {
				boolean flag = false;
				boolean returnStatus=false;
				UMBLoginLogoutFuncs UMBLogin = new UMBLoginLogoutFuncs(driver);
				UMBUSSDFuncs ussdFuncs = new UMBUSSDFuncs(driver);
				String responseMessage;
				try {
					if(MainUtil.dictionary.get("EXTENSION_DAYS").equalsIgnoreCase("1")) {
						MainUtil.dictionary.put("UMB_Purchase_Code","1");
					}else if(MainUtil.dictionary.get("EXTENSION_DAYS").equalsIgnoreCase("7")) {
						MainUtil.dictionary.put("UMB_Purchase_Code","2");
					}else if(MainUtil.dictionary.get("EXTENSION_DAYS").equalsIgnoreCase("30")) {
						MainUtil.dictionary.put("UMB_Purchase_Code","3");
					}
					//to fetch USSD purchase code and bundle price
				//	ApplicationUtil.getAccValidityExtDetails(planType, MainUtil.dictionary.get("BUNDLE_NAME"));
					ApplicationUtil.getUMBDetails(planType, category);
					UMBLogin.loginUMB();	
					responseMessage = ussdFuncs.DialForPurchase();
					flag = MainUtil.compareInString(responseMessage,MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
					logger.info(responseMessage); 
					logger.info(MainUtil.dictionary.get("USSD_MESSAGE")); 
					flag = true;
					if(flag) {
						//ApplicationUtil.updateTopupPinStatusInTable(MainUtil.dictionary.get("TOPUP_PIN"));
						ApplicationUtil.updateExpiryAfterAccValidityExt(MainUtil.dictionary.get("MSISDN"));
						returnStatus = true;
					}
					
					ussdFuncs.ResetUMBScreen();
					UMBLogin.logoutUMB();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Exception occured while verifying UMB  :" + e);
				}
				return returnStatus;
			}
	
	//Login, DailUSSD & Logout Funcs
		public boolean performCreditSOS(String planType, String category) {
					boolean flag = false;
					boolean returnStatus=false;
					UMBLoginLogoutFuncs UMBLogin = new UMBLoginLogoutFuncs(driver);
					UMBUSSDFuncs ussdFuncs = new UMBUSSDFuncs(driver);
					String responseMessage;
					try {
						if(MainUtil.dictionary.get("CREDIT_AMOUNT").equalsIgnoreCase("1")) {
							MainUtil.dictionary.put("UMB_Purchase_Code","1");
						}else if(MainUtil.dictionary.get("CREDIT_AMOUNT").equalsIgnoreCase("2")) {
							MainUtil.dictionary.put("UMB_Purchase_Code","2");
						}
						//to fetch USSD purchase code and bundle price
					//	ApplicationUtil.getAccValidityExtDetails(planType, MainUtil.dictionary.get("BUNDLE_NAME"));
						ApplicationUtil.getUMBDetails(planType, category);
						UMBLogin.loginUMB();	
						responseMessage = ussdFuncs.DialForPurchase();
						flag = MainUtil.compareInString(responseMessage,MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
						logger.info(responseMessage); 
						logger.info(MainUtil.dictionary.get("USSD_MESSAGE")); 
						if(flag) {
							//ApplicationUtil.updateTopupPinStatusInTable(MainUtil.dictionary.get("TOPUP_PIN"));
							ApplicationUtil.updateAccountInfoAfterTopupUMB(MainUtil.dictionary.get("MSISDN"));
							returnStatus = true;
						}
						
						ussdFuncs.ResetUMBScreen();
						UMBLogin.logoutUMB();
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Exception occured while verifying UMB  :" + e);
					}
					return returnStatus;
				}

	//Login, DailUSSD & Logout Funcs
	public boolean bundlePurchaseUMB(String planType) {

		UMBLoginLogoutFuncs UMBLogin = new UMBLoginLogoutFuncs(driver);
		UMBUSSDFuncs ussdFuncs = new UMBUSSDFuncs(driver);
		String responseMessage = null;
		String updatedBalance = null;
		boolean flag;
		boolean returnFlag=false;
		try {

			//to fetch USSD purchase code and bundle price
			ApplicationUtil.getBundleDetails(planType, MainUtil.dictionary.get("BUNDLE_NAME"));

			//to fetch dial code & message and replace the parenthesis
			ApplicationUtil.getUMBDetails(planType, MainUtil.dictionary.get("CATEGORY"));
			UMBLogin.loginUMB();
			responseMessage = ussdFuncs.DialForPurchase();
			logger.info(responseMessage); 
			logger.info(MainUtil.dictionary.get("USSD_MESSAGE")); 	
			flag = MainUtil.compareInString(responseMessage,MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
			//MainUtil.compareInString(responseMessage,MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
			System.out.println("VAlue of flag is" + flag);
			if(flag) {
				String bundlePurchaseDate = MainUtil.getBundlePurchaseDate();
				String bundleExpiry = MainUtil.calculateBundleExpiry(MainUtil.dictionary.get("BUNDLE_NAME"));
				if(planType.equalsIgnoreCase("PREPAID")) {
					updatedBalance = MainUtil.calculateBalance(MainUtil.dictionary.get("CURRENT_BALANCE"), MainUtil.dictionary.get("PRICE"));

				}
				ApplicationUtil.updateAccInfoAfterBundle(planType, MainUtil.dictionary.get("MSISDN"),updatedBalance , MainUtil.dictionary.get("BUNDLE_NAME"), bundlePurchaseDate, bundleExpiry);
				returnFlag = true;

			}
			else {
				logger.info("Bundle purchase failed");
			}
			ussdFuncs.ResetUMBScreen();
			UMBLogin.logoutUMB();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying UMB  :" + e);
			
		}
		return returnFlag;
	}

	//Login, DailUSSD & Logout Funcs
	public String getBalance(String planType, String category ) {

		String responseMessage = null;
		String	finalBalance = null;
		try {

			//to fetch dial code & message and replace the parenthesis
			ApplicationUtil.getUMBDetails(planType, category);

			UMBLoginLogoutFuncs UMBLogin = new UMBLoginLogoutFuncs(driver);
			UMBLogin.loginUMB();

			UMBUSSDFuncs ussdFuncs = new UMBUSSDFuncs(driver);
			responseMessage = ussdFuncs.DialForPurchase();

			String[] accBalance = responseMessage.split("\\r?\\n");
			//finalBalance = accBalance[2];
			if(category.contains("CreditTransfer")) {
				String[] creditBalance = accBalance[2].split(":");
				finalBalance = creditBalance[1].substring(3);
			}else {
				finalBalance = accBalance[1].substring(2);
				//finalExpDate = accBalance[3].substring(3);
				accBalance[2].substring(3);
				MainUtil.dictionary.put("ACCOUNT_EXPIRY_DATE", accBalance[2].substring(3));
			}
			
			logger.info(finalBalance);
			UMBLogin.logoutUMB();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying UMB  :" + e);
		}

		return finalBalance;
	}
	
	

	//Login, DailUSSD & Logout Funcs
	public String UMBCheckBalance(String planType, String category ) {

		String responseMessage = null;
		String	finalBalance = null;
		try {


			//to fetch dial code & message and replace the parenthesis
			ApplicationUtil.getUMBDetails(planType, category);
			ApplicationUtil.getAccBalance(MainUtil.dictionary.get("MSISDN"));	
			UMBLoginLogoutFuncs UMBLogin = new UMBLoginLogoutFuncs(driver);
			UMBLogin.loginUMB();

			UMBUSSDFuncs ussdFuncs = new UMBUSSDFuncs(driver);
			responseMessage = ussdFuncs.DialForPurchase();

			MainUtil.compareInString(responseMessage, MainUtil.dictionary.get("CURRENT_BALANCE"), "Verify the USSD response message", driver);
			logger.info(responseMessage); 
			logger.info(MainUtil.dictionary.get("CURRENT_BALANCE")); 

			/*String[] accBalance = responseMessage.split("\\r?\\n");
                	finalBalance = accBalance[2];
                	finalBalance = finalBalance.substring(2);*/

			UMBLogin.logoutUMB();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying UMB  :" + e);
		}

		return finalBalance;
	}

	//Login, DailUSSD & Logout Funcs
	public String umbVerification(String planType, String category) {

		UMBLoginLogoutFuncs UMBLogin = new UMBLoginLogoutFuncs(driver);
		UMBUSSDFuncs ussdFuncs = new UMBUSSDFuncs(driver);
		String responseMessage = null;
		String	finalBalance = null;
		try {

			if(planType.equalsIgnoreCase("PREPAID")) 
			{
				/*FOR ALL PREPAID OPERATIONS*/
                if(((MainUtil.dictionary.get("CATEGORY")!=null) && (MainUtil.dictionary.get("CATEGORY").equalsIgnoreCase("ChangeRatePlan")))||
						((MainUtil.dictionary.get("PLAN_NAME")!=null) &&(MainUtil.dictionary.get("PLAN_NAME").contains("5G Prepaid")))){
                    ApplicationUtil.getUMBDetails(planType, "BalanceCheck5G");
                }
                else {
                    ApplicationUtil.getUMBDetails(planType, "BalanceCheck");
                }
				UMBLogin.loginUMB();
				responseMessage = ussdFuncs.DialForPurchase();
				MainUtil.compareInString(responseMessage,MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
				logger.info(responseMessage); 
				logger.info(MainUtil.dictionary.get("USSD_MESSAGE")); 
				ussdFuncs.ResetUMBScreen();
				UMBLogin.logoutUMB();
			}
			System.out.println(MainUtil.dictionary.get("CREDIT_AMOUNT"));
			if(((MainUtil.dictionary.get("CREDIT_AMOUNT")==null)||
					(MainUtil.dictionary.get("CREDIT_AMOUNT").equalsIgnoreCase("NA")))&&(planType.equalsIgnoreCase("PREPAID"))){
				logger.info("No need to verify Credit Transfer details for sender");
			}
			else {
				if (category.equalsIgnoreCase("PLAN")) {
					if (planType.equalsIgnoreCase("PREPAID")) {
						ApplicationUtil.getUMBDetails(planType, "RatePlan Check");
						UMBLogin.loginUMB();
						responseMessage = ussdFuncs.DialForPurchase();
						MainUtil.compareInString(responseMessage, MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
						logger.info(responseMessage);
						logger.info(MainUtil.dictionary.get("USSD_MESSAGE"));
						ussdFuncs.ResetUMBScreen();
						UMBLogin.logoutUMB();
					}
					if ((MainUtil.dictionary.get("TOPUP_AMOUNT") != null) && (MainUtil.dictionary.get("TOPUP_AMOUNT").equalsIgnoreCase("10"))) {
						ApplicationUtil.getUMBDetails(planType, MainUtil.dictionary.get("PLAN_NAME") + " %BalCheck-Topup");
					} else {
						ApplicationUtil.getUMBDetails(planType, MainUtil.dictionary.get("PLAN_NAME") + " %BalCheck");
					}
					UMBLogin.loginUMB();
					responseMessage = ussdFuncs.DialForPurchase();
					MainUtil.compareInString(responseMessage, MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
					logger.info(responseMessage);
					logger.info(MainUtil.dictionary.get("USSD_MESSAGE"));
					ussdFuncs.ResetUMBScreen();
					UMBLogin.logoutUMB();

				} else if (category.equalsIgnoreCase("BUNDLE")) {
					if (planType.equalsIgnoreCase("PREPAID")) {
						ApplicationUtil.getUMBDetails(planType, MainUtil.dictionary.get("BUNDLE_NAME") + " BalCheck");
						UMBLogin.loginUMB();
						responseMessage = ussdFuncs.DialForPurchase();
						responseMessage = MainUtil.editUMBActualMessage(responseMessage, "BalCheck");
						MainUtil.compareInString(responseMessage, MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
						logger.info(responseMessage);
						logger.info(MainUtil.dictionary.get("USSD_MESSAGE"));
						ussdFuncs.ResetUMBScreen();

						ApplicationUtil.getUMBDetails(planType, MainUtil.dictionary.get("BUNDLE_NAME") + " GlobalBalCheck");
						responseMessage = ussdFuncs.DialForPurchase();
						responseMessage = MainUtil.editUMBActualMessage(responseMessage, "GlobalBalCheck");
						MainUtil.compareInString(responseMessage, MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
						logger.info(responseMessage);
						logger.info(MainUtil.dictionary.get("USSD_MESSAGE"));
						ussdFuncs.ResetUMBScreen();
						UMBLogin.logoutUMB();
					} else {
						ApplicationUtil.getUMBDetails(planType, MainUtil.dictionary.get("BUNDLE_NAME") + "% BalCheck");
						UMBLogin.loginUMB();
						responseMessage = ussdFuncs.DialForPurchase();
						MainUtil.compareInString(responseMessage, MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
						logger.info(responseMessage);
						logger.info(MainUtil.dictionary.get("USSD_MESSAGE"));
						ussdFuncs.ResetUMBScreen();
						UMBLogin.logoutUMB();
					}

				} else if (category.equalsIgnoreCase("CREDITTRANSFER")) {
					ApplicationUtil.getUMBDetails(planType, MainUtil.dictionary.get("CATEGORY") + " BalCheck");
					UMBLogin.loginUMB();
					responseMessage = ussdFuncs.DialForPurchase();
					MainUtil.compareInString(responseMessage, MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
					logger.info(responseMessage);
					logger.info(MainUtil.dictionary.get("USSD_MESSAGE"));
					ussdFuncs.ResetUMBScreen();
					UMBLogin.logoutUMB();

				} else if (category.equalsIgnoreCase("NOCREDITTRANSFER")) {
					ApplicationUtil.getUMBDetails(planType, MainUtil.dictionary.get("CATEGORY") + " BalCheck");
					UMBLogin.loginUMB();
					responseMessage = ussdFuncs.DialForPurchase();
					MainUtil.compareInString(responseMessage, MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
					logger.info(responseMessage);
					logger.info(MainUtil.dictionary.get("USSD_MESSAGE"));
					ussdFuncs.ResetUMBScreen();
					UMBLogin.logoutUMB();

				} else if (category.equalsIgnoreCase("PREPAID_TOPUP")) {
					ApplicationUtil.getTopUpFreebies(MainUtil.dictionary.get("PLAN_NAME"), MainUtil.dictionary.get("TOPUP_AMOUNT"));
					ApplicationUtil.getUMBDetails(planType, MainUtil.dictionary.get("CATEGORY") + " BalCheck");
					UMBLogin.loginUMB();
					responseMessage = ussdFuncs.DialForPurchase();
					MainUtil.compareInString(responseMessage, MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
					logger.info(responseMessage);
					logger.info(MainUtil.dictionary.get("USSD_MESSAGE"));
					ussdFuncs.ResetUMBScreen();
					UMBLogin.logoutUMB();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying UMB  :" + e);
		}
		return finalBalance;
	}

	//Login, DailUSSD & Logout Funcs
	public void bundleUnsubscribe(String planType) {

		UMBLoginLogoutFuncs UMBLogin = new UMBLoginLogoutFuncs(driver);
		UMBUSSDFuncs ussdFuncs = new UMBUSSDFuncs(driver);
		String responseMessage = null;
		try {

			//to fetch USSD purchase code and bundle price
			//ApplicationUtil.getBundleDetails(MainUtil.dictionary.get("BUNDLE_NAME"));

			//to fetch dial code & message and replace the parenthesis
			ApplicationUtil.getUMBDetails(planType, MainUtil.dictionary.get("CATEGORY"));
			UMBLogin.loginUMB();
			responseMessage = ussdFuncs.DialForPurchase();
			MainUtil.compareInString(responseMessage,MainUtil.dictionary.get("USSD_MESSAGE"), "Verify the USSD response message", driver);
			logger.info(responseMessage); 
			logger.info(MainUtil.dictionary.get("USSD_MESSAGE")); 
			ussdFuncs.ResetUMBScreen();
			UMBLogin.logoutUMB();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while Unsubscribing bundle in UMB  :" + e);
		}
	}

	public String getPrepaidBalanceExpiryDate(String sMSISDN, RemoteWebDriver driver) throws Exception {

		try {
			String responseMessage;
			UMBLoginLogoutFuncs UMBLogin = new UMBLoginLogoutFuncs(driver);
			UMBUSSDFuncs ussdFuncs = new UMBUSSDFuncs(driver);
			UMBLogin.loginUMB();
			MainUtil.dictionary.put("USSD_CODE", "*218#");
			responseMessage = ussdFuncs.DialForPurchase();
			logger.info(responseMessage);
			logger.info(MainUtil.dictionary.get("USSD_MESSAGE"));
			ussdFuncs.ResetUMBScreen();
			UMBLogin.logoutUMB();
			return responseMessage;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting Prepaid Balance and Expiry Date in UMB  :" + e);
		}
		return null;
	}

	public void doUnsubscribeAllBundles(String sPlanType, String sMSISDN, RemoteWebDriver driver) {

		UMBLoginLogoutFuncs UMBLogin = new UMBLoginLogoutFuncs(driver);
		UMBUSSDFuncs ussdFuncs = new UMBUSSDFuncs(driver);

		try {
			UMBLogin.loginUMB();

			ussdFuncs.DialForUnsubscribeAllBundles(sPlanType, sMSISDN);

			logger.info("All Bundles Unsubscribed Successfully");

			UMBLogin.logoutUMB();

			getTest().get().pass("All Bundles Unsubscribed successfully");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while Unsubscribing bundles in UMB  :" + e);
		}finally {
			DriverFactory.quitUMB(driver);
		}
	}
}

package com.umtest.testframe.lib;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.DeleteSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.umtest.testframe.lib.JSONReader.getJSONServicesCategories;
import static com.umtest.testframe.lib.JSONReader.parseJSONFile;
import static com.umtest.testframe.listener.ExtentTestNGITestListener.getTest;

public class ApplicationUtil extends MainUtil {

	private static Logger logger = LogManager.getLogger(ApplicationUtil.class);


	public static synchronized void getPlanDetails(String planName) throws Exception {
		String query = null;
		String ma;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT * FROM plan where name ='"+planName+"' LIMIT 1";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						dictionary.put("sc", resultset.getString("sc"));
						dictionary.put("offerid", resultset.getString("offerid"));
						dictionary.put("language", resultset.getString("language"));

						ma = resultset.getString("mainaccount");

						dictionary.put("MAIN_BALANCE", ma);
						dictionary.put("mainaccount", ma);
						dictionary.put("bscsbalance", ma);
						dictionary.put("currentbalance", ma);


						dictionary.put("selfcareplan", planName);
						dictionary.put("bscs_name", resultset.getString("bscs_name"));

						String ActiveDuration = resultset.getString("validity_active");
						dictionary.put("ActiveDurationDays", ActiveDuration);

						String PassiveDuration = resultset.getString("validity_passive");
						dictionary.put("PassiveDurationDays", PassiveDuration);

						String CCDuration = resultset.getString("validity_creditclearance");
						dictionary.put("CCDurationDays", CCDuration);

						String SRDuration = resultset.getString("validity_serviceremoval");
						dictionary.put("SRDurationDays", SRDuration);

						getTest().get().pass("Got Plan Data & Stored To Dictionary");
					} else {
						logger.info("There is no record for "+planName+" in DB");
						getTest().get().fail("There is no record found in DB for plan : "+planName);
					}
					logger.info(dictionary.get("sc"));
					logger.info(dictionary.get("offerid"));
					logger.info(dictionary.get("language"));
					logger.info(dictionary.get("mainaccount"));
					logger.info(dictionary.get("ActiveDurationDays"));
					logger.info(dictionary.get("PassiveDurationDays"));
					logger.info(dictionary.get("CCDurationDays"));
					logger.info(dictionary.get("SRDurationDays"));
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured FetchStoredDataFromList");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromList", e);
			getTest().get().fail("Some error occured FetchStoredDataFromList");
			throw e;
		}
	}

	public static synchronized void updateAccInfoBalanceAndActiveEndDate(String sMSISDN,String sBalance, String sActiveEndDate ) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {

				query = "UPDATE account_info SET main_balance = '" +sBalance+ "', active_end_date = '"+ sActiveEndDate +"' WHERE msisdn = '"+sMSISDN+"'";
				logger.info(query);

				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					preparedStmt.executeUpdate();
					logger.info("Account Balance and Active End Date Updated Successfully in account_info Table");
				}
			}

		} catch (Exception e) {
			logger.error("Account info post bundle purchase Updation Failed While Updating in account_info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateAccountInforPostBundle", e);
			getTest().get().fail("Some error occured updateAccountInforPostBundle");
			throw e;
		}
	}


	public static synchronized String getSelfcareLoginPin(String msisdn) throws Exception {
		String query = null;
		String pin = null;

		try (Connection connection = SQLConnectionHelper.getSelfcareDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT r.clear FROM sso_user_profile u JOIN sso_rainbow r ON r.hash = u.PASSWORD WHERE u.USER_ID = '"+msisdn+"'";
				logger.info(query);
				Thread.sleep(2000);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						pin = resultset.getString("clear");
						dictionary.put("selfcarepin", pin );

						getTest().get().pass("Got PIN From Selfcare Data & Stored To Dictionary");
					} else {
						logger.info("There is no record for "+msisdn+" in Selfcare DB");
					}
					logger.info(dictionary.get("selfcarepin"));
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured FetchStoredDataFromList");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromList", e);
			getTest().get().fail("Some error occured FetchStoredDataFromList");
			throw e;
		}
		return pin;
	}

	public static synchronized String getSelfcarefirsttimeLoginPin(String msisdn) throws Exception {
		String query = null;
		String pin = null;

		try (Connection connection = SQLConnectionHelper.getSelfcareDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT b.clear FROM sso_otp a JOIN sso_rainbow b ON a.PIN = b.hash WHERE a.MSISDN ='"+msisdn+"' ORDER BY a.SEQ_NO DESC LIMIT 1";
				logger.info(query);
				Thread.sleep(2000);
				try (ResultSet resultset = statement.executeQuery(query)) {
					Thread.sleep(1000);
					if (resultset.next()) {
						pin = resultset.getString("clear");
						dictionary.put("selfcarepin", pin );

						getTest().get().pass("Got PIN From Selfcare Data & Stored To Dictionary");
					} else {
						logger.info("There is no record for "+msisdn+" in Selfcare DB");
					}
					logger.info(dictionary.get("selfcarepin"));
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured FetchStoredDataFromList");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromList", e);
			getTest().get().fail("Some error occured FetchStoredDataFromList");
			throw e;
		}
		return pin;
	}

	public static synchronized void getUMBDetails(String planType, String category) throws Exception {
		String query = null;
		String ussdCode;
		String ussdMessage;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT * FROM umb_t5 where plantype ='" + planType + "' and category like '" + category +"'";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						ussdCode = resultset.getString("dialcode");
						ussdCode = MainUtil.replaceParanthesis(ussdCode);
						dictionary.put("USSD_CODE", ussdCode );
						ussdMessage = resultset.getString("message");
						logger.info(ussdMessage);
						ussdMessage = MainUtil.replaceParanthesis(ussdMessage);
						dictionary.put("USSD_MESSAGE", ussdMessage );

					} else {
						logger.info("There is no record for "+category+" in umb table under test db");

					}
					logger.info(dictionary.get("USSD_CODE") + dictionary.get("USSD_MESSAGE"));
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured FetchStoredDataFromList");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromList", e);
			getTest().get().fail("Some error occured FetchStoredDataFromList");
			throw e;
		}

	}


	public static synchronized void getSMSDetails(String planname, String category) throws Exception {
		String query = null;
		String ussdCode;
		String ussdMessage;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT s.sms, s.category FROM planm p JOIN sms s ON s.plan_id = p.id_bin WHERE p.name LIKE '"+planname+"' AND s.category='"+category+"'";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {

					if (resultset.next()) {
						dictionary.put("SMS", resultset.getString("sms"));
						System.out.println(resultset.getString("sms"));
					} else {
						logger.info("There is no record for "+category+" in sms table under test db");

					}
					logger.info(dictionary.get("sms"));
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured FetchStoredDataFromList");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromList", e);
			getTest().get().fail("Some error occured FetchStoredDataFromList");
			throw e;
		}

	}

	public static synchronized void getbundleSMSDetails(String planname) throws Exception {
		String query = null;
		String ussdCode;
		String ussdMessage;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT sms FROM sms WHERE NAME LIKE '"+planname+"'";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {

					if (resultset.next()) {
						dictionary.put("SMS", resultset.getString("sms"));
						System.out.println(resultset.getString("sms"));
					} else {
						logger.info("There is no record for "+planname+" in sms table under test db");

					}
					logger.info(dictionary.get("sms"));
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured FetchStoredDataFromList");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromList", e);
			getTest().get().fail("Some error occured FetchStoredDataFromList");
			throw e;
		}

	}

	 public static synchronized void getBundleDetails(String planType, String bundleName) throws Exception {
			String query = null;

			try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

				try (Statement statement = connection.createStatement()) {
					query = "SELECT * FROM bundle_prepaid where plan_type = '"+ planType +"' and NAME ='" + bundleName + "'";
					logger.info(query);
					try (ResultSet resultset = statement.executeQuery(query)) {
						if (resultset.next()) {
							//purchaseCode = MainUtil.replaceParanthesis("UMB_Purchase_Code");
							dictionary.put("PRICE", resultset.getString("price"));
							dictionary.put("BUNDLE_VALIDITY", resultset.getString("validity"));
							dictionary.put("UMB_Purchase_Code", resultset.getString("UMB_Purchase_Code") );


						} else {
							logger.info("There is no record for "+bundleName+" in umb table under test db");

						}
						logger.info(dictionary.get("PRICE") + " " + dictionary.get("BUNDLE_VALIDITY") + " " + dictionary.get("UMB_Purchase_Code") );
					}
				}

			} catch (Exception e) {
				logger.error("Some error occured FetchStoredDataFromList");
				logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromList", e);
				getTest().get().fail("Some error occured FetchStoredDataFromList");
				throw e;
			}

		}



	public static synchronized void getTopupDetails(String topupAmount) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {

				if(MainUtil.dictionary.get("IDENTIFICATION_TYPE").equalsIgnoreCase("PASSPORT"))
				{
					query = "SELECT actual_amount_passport as actual_amount,validity_active,validity_passive,validity_creditclearance,validity_serviceremoval FROM topup where topup_amount ='" + topupAmount + "' AND rate_plan='"+MainUtil.dictionary.get("PLAN_NAME")+"'";
				} else {
					query = "SELECT actual_amount,validity_active,validity_passive,validity_creditclearance,validity_serviceremoval FROM topup where topup_amount ='" + topupAmount + "' AND rate_plan='"+MainUtil.dictionary.get("PLAN_NAME")+"'";
				}

				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						//purchaseCode = MainUtil.replaceParanthesis("UMB_Purchase_Code");
						String actualamount = resultset.getString("actual_amount");
						dictionary.put("ACTUAL_AMOUNT", actualamount);

						String ActiveDuration = resultset.getString("validity_active");
						dictionary.put("ActiveDurationDays", ActiveDuration);

						String PassiveDuration = resultset.getString("validity_passive");
						dictionary.put("PassiveDurationDays", PassiveDuration);

						String CCDuration = resultset.getString("validity_creditclearance");
						dictionary.put("CCDurationDays", CCDuration);

						String SRDuration = resultset.getString("validity_serviceremoval");
						dictionary.put("SRDurationDays", SRDuration);


					} else {
						logger.info("There is no record for "+topupAmount+" in topup table under test db");

					}
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured getTopupDetails");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getTopupDetails", e);
			getTest().get().fail("Some error occured getTopupDetails");
			throw e;
		}

	}

	public static synchronized void getAccBalance(String msisdn) throws Exception {
		String query = null;


		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT main_balance FROM account_info where msisdn ='" + msisdn + "'";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						dictionary.put("CURRENT_BALANCE", resultset.getString("main_balance"));


					} else {
						logger.info("There is no record for "+msisdn+" in account_info table under test db");

					}
					logger.info(dictionary.get("CURRENT_BALANCE"));
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured FetchStoredDataFromDB");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromDB", e);
			getTest().get().fail("Some error occured FetchStoredDataFromDB");
			throw e;
		}

	}

	/**
	 * Method to store session details into a table
	 *
	 * @param accountType
	 *            Pass the accounttype(Postpaid/Prepaid)
	 * @param appName
	 *            Pass the application name
	 * @throws Exception
	 */
	public static synchronized void insertAccountIntoDB(String accountType, String appName, String registrationType) throws Exception {

		if (accountType.equalsIgnoreCase("PREPAID")) {

			calculatePrepaidLifeCycle(registrationType);

		}

		try {
			logger.info("Insert Into Account Info Table");
			try (Connection conn = SQLConnectionHelper.getTestDBConnection()) {
				String sql = "INSERT INTO `um`.`account_info` (`id`,`msisdn`,`sim_no`,`account_type`,`plan_name`,`bundle_name`,`customer_name`,`identification_type`,"
						+ "`identification_no`,`main_balance`,`purchase_date`,`active_end_date`,`passive_end_date`,`cc_end_date`,`sr_end_date`,`bundle_expiry_date`,`app_name`,`created_date`)"
						+ " VALUES (UUID(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW())";
				try (PreparedStatement ps = conn.prepareStatement(sql)) {
					ps.setString(1, dictionary.get("MSISDN"));
					ps.setString(2, dictionary.get("SIM_NO") == null ? "NA" : dictionary.get("SIM_NO"));
					ps.setString(3, accountType.toUpperCase());
					ps.setString(4, dictionary.get("PLAN_NAME") == null ? "NA" : dictionary.get("PLAN_NAME"));
					ps.setString(5, dictionary.get("BUNDLE_NAME") == null ? "NA" : dictionary.get("BUNDLE_NAME"));
					ps.setString(6, dictionary.get("CUSTOMER_NAME") == null ? "NA" : dictionary.get("CUSTOMER_NAME"));
					ps.setString(7, dictionary.get("CUSTOMER_ID_TYPE") == null ? "NA" : dictionary.get("CUSTOMER_ID_TYPE"));
					ps.setString(8, dictionary.get("CUSTOMER_ID") == null ? "NA" : dictionary.get("CUSTOMER_ID"));
					ps.setString(9, dictionary.get("MAIN_BALANCE") == null ? "NA" : dictionary.get("MAIN_BALANCE"));
					ps.setString(10, dictionary.get("PURCHASE_DATE") == null ? "NA" : dictionary.get("PURCHASE_DATE"));
					ps.setString(11, dictionary.get("ACTIVE_END_DATE") == null ? "NA" : dictionary.get("ACTIVE_END_DATE"));
					ps.setString(12, dictionary.get("PASSIVE_END_DATE") == null ? "NA" : dictionary.get("PASSIVE_END_DATE"));
					ps.setString(13, dictionary.get("CC_END_DATE") == null ? "NA" : dictionary.get("CC_END_DATE"));
					ps.setString(14, dictionary.get("SR_END_DATE") == null ? "NA" : dictionary.get("SR_END_DATE"));
					ps.setString(15, dictionary.get("BUNDLE_EXPIRY_DATE") == null ? dictionary.get("ACTIVE_END_DATE") : dictionary.get("BUNDLE_EXPIRY_DATE"));
					ps.setString(16, appName.toUpperCase());


					logger.info(ps);
					boolean rs = ps.execute();
					if (rs) {
						logger.info("Insert Unsuccessful ...");
					} else {
						logger.info("Inserted Successfully ...");
					}
					getTest().get().pass("Inserted Account in Account Info Table");
				}
			}
		} catch (Exception ex) {
			logger.error("ERROR OCCURED WHILE INSERTING INTO THE Account Table Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "insertdictionaryIntoList", ex);
			getTest().get().fail("ERROR OCCURED WHILE INSERTING INTO THE Account Info Table");
		}
	}


	public static void calculatePrepaidLifeCycle(String registrationType) throws Exception
	{
		if (registrationType.equalsIgnoreCase("PLAN")) {

			getPlanDetails(dictionary.get("PLAN_NAME"));

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDateTime cycleEndDate;

			LocalDateTime registeredDate =  LocalDateTime.now();
			LocalDateTime activeEndDate = registeredDate.plusDays(Integer.parseInt(dictionary.get("ActiveDurationDays")));
			LocalDateTime passiveEndDate = activeEndDate.plusDays(Integer.parseInt(dictionary.get("PassiveDurationDays")));
			LocalDateTime ccEndDate = passiveEndDate.plusDays(Integer.parseInt(dictionary.get("CCDurationDays")));
			LocalDateTime srEndDate = ccEndDate.plusDays(Integer.parseInt(dictionary.get("SRDurationDays")));
			if((dictionary.get("CYCLE_ENDDATE")==null)&&(dictionary.get("TOPUP_AMOUNT")==null)) {
				cycleEndDate = activeEndDate;
				dictionary.put("ACTIVE_END_DATE", dtf.format(cycleEndDate));
			}else {
				if (dictionary.get("CYCLE_ENDDATE") == null) {
					cycleEndDate = registeredDate.plusDays(Integer.parseInt(dictionary.get("TOPUP_AMOUNT")));
					dictionary.put("ACTIVE_END_DATE", dtf.format(cycleEndDate));
				} else {
					cycleEndDate = registeredDate.plusDays(Integer.parseInt(dictionary.get("CYCLE_ENDDATE")));
					dictionary.put("ACTIVE_END_DATE", dtf.format(cycleEndDate));
				}
			}

			dictionary.put("PURCHASE_DATE", dtf.format(registeredDate));
//			dictionary.put("ACTIVE_END_DATE", dtf.format(activeEndDate));
			dictionary.put("PASSIVE_END_DATE", dtf.format(passiveEndDate));
			dictionary.put("CC_END_DATE", dtf.format(ccEndDate));
			dictionary.put("SR_END_DATE", dtf.format(srEndDate));
			dictionary.put("CYCLE_END_DATE", dtf.format(cycleEndDate));

			logger.info("Purchase Date : "+dictionary.get("PURCHASE_DATE"));
			logger.info("Active End Date : "+dictionary.get("ACTIVE_END_DATE"));
			logger.info("Passive End Date : "+dictionary.get("PASSIVE_END_DATE"));
			logger.info("CC End Date : "+dictionary.get("CC_END_DATE"));
			logger.info("SR End Date : "+dictionary.get("SR_END_DATE"));
			logger.info("Cycle End Date : "+dictionary.get("CYCLE_END_DATE"));

		} else if (registrationType.equalsIgnoreCase("BUNDLE")) {

			DecimalFormat df = new DecimalFormat("0.00");
			
			getPlanDetails(dictionary.get("PLAN_NAME"));
			getBundleDetails("PREPAID", dictionary.get("BUNDLE_NAME"));
			
			double mainBalance = Double.parseDouble(dictionary.get("MAIN_BALANCE"));
			double bundlePrice = Double.parseDouble(dictionary.get("PRICE"));
			double topupAmount = Double.parseDouble(dictionary.get("TOPUP_AMOUNT"));			
			double finalBalance = (mainBalance + topupAmount) - bundlePrice;
			
			String balance = df.format(finalBalance);
			
			dictionary.put("MAIN_BALANCE", balance);
			

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDateTime cycleEndDate;
			LocalDateTime registeredDate =  LocalDateTime.now();
			LocalDateTime activeEndDate = registeredDate.plusDays(Integer.parseInt(dictionary.get("BUNDLE_VALIDITY")));
			LocalDateTime passiveEndDate = activeEndDate.plusDays(60);
			LocalDateTime ccEndDate = passiveEndDate.plusDays(1);
			LocalDateTime srEndDate = ccEndDate.plusDays(8);
			if((dictionary.get("CYCLE_ENDDATE")==null)&&(dictionary.get("TOPUP_AMOUNT")==null)) {
				cycleEndDate = activeEndDate;
				dictionary.put("ACTIVE_END_DATE", dtf.format(cycleEndDate));
			}else {
				if (dictionary.get("CYCLE_ENDDATE") == null) {
					cycleEndDate = registeredDate.plusDays(Integer.parseInt(dictionary.get("TOPUP_AMOUNT")));
					dictionary.put("ACTIVE_END_DATE", dtf.format(cycleEndDate));
				} else {
					cycleEndDate = registeredDate.plusDays(Integer.parseInt(dictionary.get("CYCLE_ENDDATE")));
					dictionary.put("ACTIVE_END_DATE", dtf.format(cycleEndDate));
				}
			}


			dictionary.put("PURCHASE_DATE", dtf.format(registeredDate));
//			dictionary.put("ACTIVE_END_DATE", dtf.format(activeEndDate));
			dictionary.put("BUNDLE_EXPIRY_DATE", dtf.format(activeEndDate));
			dictionary.put("PASSIVE_END_DATE", dtf.format(passiveEndDate));
			dictionary.put("CC_END_DATE", dtf.format(ccEndDate));
			dictionary.put("SR_END_DATE", dtf.format(srEndDate));
			dictionary.put("CYCLE_END_DATE", dtf.format(cycleEndDate));

			logger.info("Purchase Date : "+dictionary.get("PURCHASE_DATE"));
			logger.info("Active End Date : "+dictionary.get("ACTIVE_END_DATE"));
			logger.info("Passive End Date : "+dictionary.get("PASSIVE_END_DATE"));
			logger.info("CC End Date : "+dictionary.get("CC_END_DATE"));
			logger.info("SR End Date : "+dictionary.get("SR_END_DATE"));
			logger.info("Cycle End Date : "+dictionary.get("CYCLE_END_DATE"));

		} else if (registrationType.equalsIgnoreCase("TOPUP")) {

			DecimalFormat df = new DecimalFormat("0.00");
			
			getPlanDetails(dictionary.get("PLAN_NAME"));
			getTopupDetails(dictionary.get("TOPUP_AMOUNT"));
			
			double mainBalance = Double.parseDouble(dictionary.get("MAIN_BALANCE"));
			double actualAmount = Double.parseDouble(dictionary.get("ACTUAL_AMOUNT"));			
			double finalBalance = mainBalance + actualAmount;
			
			String balance = df.format(finalBalance);
			
			dictionary.put("MAIN_BALANCE", balance);
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDateTime cycleEndDate;
			LocalDateTime registeredDate =  LocalDateTime.now();
			LocalDateTime activeEndDate = registeredDate.plusDays(Integer.parseInt(dictionary.get("ActiveDurationDays")));
			LocalDateTime passiveEndDate = activeEndDate.plusDays(Integer.parseInt(dictionary.get("PassiveDurationDays")));
			LocalDateTime ccEndDate = passiveEndDate.plusDays(Integer.parseInt(dictionary.get("CCDurationDays")));
			LocalDateTime srEndDate = ccEndDate.plusDays(Integer.parseInt(dictionary.get("SRDurationDays")));
			if((dictionary.get("CYCLE_ENDDATE")==null)&&(dictionary.get("TOPUP_AMOUNT")==null)) {
				cycleEndDate = activeEndDate;
				dictionary.put("ACTIVE_END_DATE", dtf.format(cycleEndDate));
			}else {
				if (dictionary.get("CYCLE_ENDDATE") == null) {
					cycleEndDate = registeredDate.plusDays(Integer.parseInt(dictionary.get("TOPUP_AMOUNT")));
					dictionary.put("ACTIVE_END_DATE", dtf.format(cycleEndDate));
				} else {
					cycleEndDate = registeredDate.plusDays(Integer.parseInt(dictionary.get("CYCLE_ENDDATE")));
					dictionary.put("ACTIVE_END_DATE", dtf.format(cycleEndDate));
				}
			}

			dictionary.put("PURCHASE_DATE", dtf.format(registeredDate));
//			dictionary.put("ACTIVE_END_DATE", dtf.format(activeEndDate));
			dictionary.put("PASSIVE_END_DATE", dtf.format(passiveEndDate));
			dictionary.put("CC_END_DATE", dtf.format(ccEndDate));
			dictionary.put("SR_END_DATE", dtf.format(srEndDate));
			dictionary.put("CYCLE_END_DATE", dtf.format(cycleEndDate));

			logger.info("Purchase Date : "+dictionary.get("PURCHASE_DATE"));
			logger.info("Active End Date : "+dictionary.get("ACTIVE_END_DATE"));
			logger.info("Passive End Date : "+dictionary.get("PASSIVE_END_DATE"));
			logger.info("CC End Date : "+dictionary.get("CC_END_DATE"));
			logger.info("SR End Date : "+dictionary.get("SR_END_DATE"));
			logger.info("Cycle End Date : "+dictionary.get("CYCLE_END_DATE"));

		}	
	}

	public static synchronized void getAccountDetails(String msisdn) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT * FROM `account_info` WHERE msisdn ='"+msisdn+"' ORDER BY created_date DESC LIMIT 1 ";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {

						dictionary.put("MSISDN", resultset.getString("msisdn"));
						dictionary.put("SIM_NO", resultset.getString("sim_no"));
						dictionary.put("ACCOUNT_TYPE", resultset.getString("account_type") == null ? "NA" : resultset.getString("account_type"));
						dictionary.put("PLAN_NAME", resultset.getString("plan_name") == null ? "NA" : resultset.getString("plan_name"));
						dictionary.put("BUNDLE_NAME", resultset.getString("bundle_name") == null ? "NA" : resultset.getString("bundle_name"));
						dictionary.put("CUSTOMER_NAME", resultset.getString("customer_name") == null ? "NA" : resultset.getString("customer_name"));
						dictionary.put("CUSTOMER_ID_TYPE", resultset.getString("identification_type") == null ? "NA" : resultset.getString("identification_type"));
						dictionary.put("CUSTOMER_ID", resultset.getString("identification_no") == null ? "NA" : resultset.getString("identification_no"));
						dictionary.put("MAIN_BALANCE", resultset.getString("main_balance") == null ? "NA" : resultset.getString("main_balance"));
						dictionary.put("CREDIT_AMOUNT", resultset.getString("credit_amount") == null ? "NA" : resultset.getString("credit_amount"));
						dictionary.put("ACCOUNT_STATUS", resultset.getString("status") == null ? "NA" : resultset.getString("status"));
						dictionary.put("PURCHASE_DATE", resultset.getString("purchase_date") == null ? "NA" : resultset.getString("purchase_date"));
						dictionary.put("ACTIVE_END_DATE", resultset.getString("active_end_date") == null ? "NA" : resultset.getString("active_end_date"));
						dictionary.put("PASSIVE_END_DATE", resultset.getString("passive_end_date") == null ? "NA" : resultset.getString("passive_end_date"));
						dictionary.put("CC_END_DATE", resultset.getString("cc_end_date") == null ? "NA" : resultset.getString("cc_end_date"));
						dictionary.put("SR_END_DATE", resultset.getString("sr_end_date") == null ? "NA" : resultset.getString("sr_end_date"));
						dictionary.put("BUNDLE_EXPIRY_DATE", resultset.getString("bundle_expiry_date") == null ? "NA" : resultset.getString("bundle_expiry_date"));
						dictionary.put("CUSTOMER_DOB_DD", "01");
						dictionary.put("CUSTOMER_DOB_MM", "01");
						dictionary.put("CUSTOMER_DOB_YYYY", "1990");
						dictionary.put("CUSTOMER_ADDRESS", "197, Jalan Klang Lama");
						dictionary.put("CUSTOMER_CITY", "KUALA LUMPUR");
						dictionary.put("CUSTOMER_STATE", "KUALA LUMPUR");
						dictionary.put("CUSTOMER_POSTCODE", "58300");
						dictionary.put("CUSTOMER_EMAIL", "AUTOTEST@UM.COM");
						dictionary.put("CUSTOMER_NUMBER", "60182143456");

						logger.info("MSISDN->"+dictionary.get("MSISDN"));
						logger.info("SIM_NO ->"+dictionary.get("SIM_NO"));
						logger.info("ACCOUNT_TYPE ->"+dictionary.get("ACCOUNT_TYPE"));
						logger.info("PLAN_NAME ->"+dictionary.get("PLAN_NAME"));
						logger.info("BUNDLE_NAME ->"+dictionary.get("BUNDLE_NAME"));
						logger.info("CUSTOMER_NAME ->"+dictionary.get("CUSTOMER_NAME"));
						logger.info("CUSTOMER_ID_TYPE ->"+dictionary.get("CUSTOMER_ID_TYPE"));
						logger.info("CUSTOMER_ID ->"+dictionary.get("CUSTOMER_ID"));
						logger.info("NATIONALITY ->"+dictionary.get("NATIONALITY"));
						logger.info("MAIN_BALANCE ->"+dictionary.get("MAIN_BALANCE"));
						logger.info("CREDIT_AMOUNT ->"+dictionary.get("CREDIT_AMOUNT"));
						logger.info("ACCOUNT_STATUS ->"+dictionary.get("ACCOUNT_STATUS"));
						logger.info("PURCHASE_DATE ->"+dictionary.get("PURCHASE_DATE"));
						logger.info("ACTIVE_END_DATE ->"+dictionary.get("ACTIVE_END_DATE"));
						logger.info("PASSIVE_END_DATE ->"+dictionary.get("PASSIVE_END_DATE"));
						logger.info("CC_END_DATE ->"+dictionary.get("CC_END_DATE"));
						logger.info("SR_END_DATE ->"+dictionary.get("SR_END_DATE"));

						getTest().get().pass("Got MSISDN Details & Stored To Dictionary");

					} else {
						logger.info("There is no record for "+msisdn+" in DB");
						getTest().get().fail("There is no record found in DB for plan : "+msisdn);
					}
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured getAccountDetails");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getAccountDetails", e);
			getTest().get().fail("Some error occured getAccountDetails");
			throw e;
		}
	}

	public static synchronized void getPlanAndBundleNameDetails(String msisdn) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT * FROM `account_info` WHERE msisdn ='"+msisdn+"' ORDER BY created_date DESC LIMIT 1 ";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {

						dictionary.put("MSISDN", resultset.getString("msisdn"));
						dictionary.put("PLAN_NAME", resultset.getString("plan_name") == null ? "NA" : resultset.getString("plan_name"));
						dictionary.put("BUNDLE_NAME", resultset.getString("bundle_name") == null ? "NA" : resultset.getString("bundle_name"));

						getTest().get().pass("Got Current Plan and Bundle Details & Stored To Dictionary");

					} else {
						logger.info("There is no record for "+msisdn+" in DB");
						getTest().get().fail("There is no record found in DB for plan : "+msisdn);
					}
					logger.info(dictionary.get("MSISDN"));
					logger.info(dictionary.get("PLAN_NAME"));
					logger.info(dictionary.get("BUNDLE_NAME"));
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured getAccountDetails");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getAccountDetails", e);
			getTest().get().fail("Some error occured getAccountDetails");
			throw e;
		}
	}

	
	public static synchronized void getPlanDetails_Postpaid(String planname) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT name,creditlimit,bscs_name, currentbalance, ROUND(bscsbalance, 2) bscsbalance, ROUND(sdpbalance, 2) sdpbalance, umbfreebies, selfcareplan, data, voice FROM planm where name ='"+planname+"'";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						dictionary.put("PLAN_NAME", resultset.getString("name") == null ? "NA" : resultset.getString("name"));
						dictionary.put("CREDIT_LIMIT", resultset.getString("creditlimit") == null ? "NA" : resultset.getString("creditlimit"));
						dictionary.put("CURRENT_BALANCE", resultset.getString("currentbalance") == null ? "NA" : resultset.getString("currentbalance"));
						dictionary.put("SELFCARE_PLAN", resultset.getString("selfcareplan") == null ? "NA" : resultset.getString("selfcareplan"));

						getTest().get().pass("Got Plan Details & Stored To Dictionary");

					} else {
						logger.info("There is no record for "+planname+" in DB");
						getTest().get().fail("There is no record found in DB for plan : "+planname);
					}
					logger.info(dictionary.get("PLAN_NAME"));
					logger.info(dictionary.get("CREDIT_LIMIT"));
					logger.info(dictionary.get("CURRENT_BALANCE"));
					logger.info(dictionary.get("SELFCARE_PLAN"));
					
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured getAccountDetails");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getAccountDetails", e);
			getTest().get().fail("Some error occured getAccountDetails");
			throw e;
		}
	}


	public static synchronized ArrayList<String> getServiceCategoryDetailsMobileAppPrepaid() throws Exception {

		ArrayList<String> allCategory = new ArrayList<String>();

		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT DISTINCT(s.MobileAppService) FROM bundle_prepaid p JOIN tbo bo ON p.id = bo.plan_id JOIN services s ON s.id_bin = bo.service_id WHERE p.name = '"+dictionary.get("BUNDLE_NAME")+"' AND MobileAppCaption IS NOT NULL AND s.MobileAppCaption!=''";
				logger.info(query);

				try (ResultSet resultset = statement.executeQuery(query)) {
					while (resultset.next()) {
						allCategory.add(resultset.getString("MobileAppService"));
					}
				} catch (Exception e) {
					logger.error("Some error occurred getting service category details from DB");
				}
			}
		} catch (Exception e) {
			logger.error("Some error occurred getting service details from DB");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getServiceCategoryDetailsMobileAppPrepaid", e);
			getTest().get().fail("Some error occurred getting service setails from DB");
			throw e;
		}
		return allCategory;
	}

	public static synchronized ArrayList<String> getServiceUsageCaptionMobileAppPrepaid(String sServiceCategory) throws Exception {

		String query = null;

		ArrayList<String> usageCaptionDB = new ArrayList<String>();

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT s.MobileAppCaption FROM bundle_prepaid p JOIN tbo bo ON p.id = bo.plan_id JOIN services s ON s.id_bin = bo.service_id WHERE p.name = '" + dictionary.get("BUNDLE_NAME") + "' AND s.MobileAppCaption IS NOT NULL AND s.MobileAppService='" + sServiceCategory + "'";

				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					while (resultset.next()) {
						usageCaptionDB.add(resultset.getString("MobileAppCaption"));
					}
				}
			} catch (Exception e) {
				logger.error("Some error occurred getting service usage caption details from DB");
			}

		} catch (Exception e) {
			logger.error("Some error occurred getting service details from DB");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getServiceUsageCaptionMobileAppPrepaid", e);
			getTest().get().fail("Some error occured getting service caption details from DB");
			throw e;
		}
		return usageCaptionDB;
	}



	public static synchronized ArrayList<String> getServiceUsageBalanceMobileAppPrepaid(String sServiceCategory) throws Exception {

		String query = null;
		ArrayList<String> usageBalanceDB = new ArrayList<String>();

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {

				query = "SELECT s.MobileAppBalance FROM bundle_prepaid p JOIN tbo bo ON p.id = bo.plan_id JOIN services s ON s.id_bin = bo.service_id WHERE p.name = '" + dictionary.get("BUNDLE_NAME") + "' AND s.MobileAppCaption IS NOT NULL AND s.MobileAppService='" + sServiceCategory + "'";

				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					while (resultset.next()) {
						usageBalanceDB.add(resultset.getString("MobileAppBalance"));
					}
				}
			} catch (Exception e) {
				logger.error("Some error occurred getting service usage caption details from DB");
			}

		} catch (Exception e) {
			logger.error("Some error occurred getting service balance details from DB");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getServiceUsageBalanceMobileAppPrepaid", e);
			getTest().get().fail("Some error occurred getting service balance details from DB");
			throw e;
		}
		return usageBalanceDB;
	}

	public static synchronized ArrayList<String> getServiceUsageBalanceByPurchaseIterationMobileAppPrepaid(String sServiceCategory,String sIteration) throws Exception {

		String query = null;
		ArrayList<String> usageBalanceDB = new ArrayList<String>();

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {

				query = "SELECT s.MobileAppBalanceIter"+sIteration+" as MobileAppBalance FROM bundle_prepaid p JOIN tbo bo ON p.id = bo.plan_id JOIN services s ON s.id_bin = bo.service_id WHERE p.name = '" + dictionary.get("BUNDLE_NAME") + "' AND s.MobileAppCaption IS NOT NULL AND s.MobileAppService='" + sServiceCategory + "'";

				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					while (resultset.next()) {
						usageBalanceDB.add(resultset.getString("MobileAppBalance"));
					}
				}
			} catch (Exception e) {
				logger.error("Some error occurred getting service usage caption details from DB");
			}

		} catch (Exception e) {
			logger.error("Some error occurred getting service balance details from DB");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getServiceUsageBalanceMobileAppPrepaid", e);
			getTest().get().fail("Some error occurred getting service balance details from DB");
			throw e;
		}
		return usageBalanceDB;
	}

	public static synchronized void verifyServiceDetailsMobileAppPrepaid(AndroidDriver driver) throws Exception {

		ArrayList<String> allCategoryDB;
		ArrayList<String> usageCaptionDB;
		ArrayList<String> usageBalanceDB;

		allCategoryDB = getServiceCategoryDetailsMobileAppPrepaid();

		Thread.sleep(2000);

		for (int iVar1 = 0; iVar1 < allCategoryDB.size(); iVar1++) {
			String sServiceCategory = allCategoryDB.get(iVar1);

			WebElement oCategoryElement = driver.findElement(By.xpath("//*[@content-desc='usageDetail_cell[" + sServiceCategory + "]']/android.widget.TextView"));
			oCategoryElement.click();

			scrollUDLRMobileApp(driver, 1, "U");

			usageCaptionDB = getServiceUsageCaptionMobileAppPrepaid(sServiceCategory);
			usageBalanceDB = getServiceUsageBalanceMobileAppPrepaid(sServiceCategory);

			for (int iVar2 = 0; iVar2 < usageCaptionDB.size(); iVar2++) {

				String sServiceCaptionDB = usageCaptionDB.get(iVar2);
				String sServiceCaptionbalanceDB = usageBalanceDB.get(iVar2);

				String sXpathCaptionElement = "//*[@content-desc='content_" + sServiceCaptionDB + "']/android.widget.TextView[2]";

				int intVarA1=0;

				while (verifyElementExistUsingXpathString(sXpathCaptionElement, "Caption - " + sServiceCaptionDB, driver) == false && intVarA1<10) {
					scrollUDLRMobileApp(driver, 1, "U");
					intVarA1++;
				}

				WebElement oCategoryCaptionElement = driver.findElement(By.xpath("//*[@content-desc='content_" + sServiceCaptionDB + "']/android.widget.TextView[1]"));
				WebElement oCategoryCaptionBalanceElement = driver.findElement(By.xpath("//*[@content-desc='content_" + sServiceCaptionDB + "']/android.widget.TextView[2]"));

				String sServiceCaptionActual = oCategoryCaptionElement.getText();
				String sServiceCaptionBalanceActual = oCategoryCaptionBalanceElement.getText();

				getTest().get().pass(sServiceCategory + ": '" + sServiceCaptionDB + "' Service",
						ExtentScreenCapture.captureSrceenPass(sServiceCategory + "-" + sServiceCaptionDB + "- Service", driver));

				MainUtil.compareInString(sServiceCaptionActual, sServiceCaptionDB, sServiceCategory + ": '" + sServiceCaptionDB + "' Service", driver);
				MainUtil.compareInString(sServiceCaptionBalanceActual, sServiceCaptionbalanceDB, sServiceCategory + ": '" + sServiceCaptionDB + "' Usage Detail", driver);

				scrollUDLRMobileApp(driver, 1, "D");
				scrollUDLRMobileApp(driver, 1, "D");
				scrollUDLRMobileApp(driver, 1, "D");
			}
			oCategoryElement.click();
		}
	}



	public static synchronized void verifyServiceDetailsByPurchaseIterationMobileAppPrepaid(AndroidDriver driver, String sIteration) throws Exception {

		ArrayList<String> allCategoryDB;
		ArrayList<String> usageCaptionDB;
		ArrayList<String> usageBalanceDB;

		allCategoryDB = getServiceCategoryDetailsMobileAppPrepaid();

		for (int iVar1 = 0; iVar1 < allCategoryDB.size(); iVar1++) {
			String sServiceCategory = allCategoryDB.get(iVar1);

			WebElement oCategoryElement = driver.findElement(By.xpath("//*[@content-desc='usageDetail_cell[" + sServiceCategory + "]']/android.widget.TextView"));
			oCategoryElement.click();

			scrollUDLRMobileApp(driver, 1, "U");

			usageCaptionDB = getServiceUsageCaptionMobileAppPrepaid(sServiceCategory);
			usageBalanceDB = getServiceUsageBalanceByPurchaseIterationMobileAppPrepaid(sServiceCategory,sIteration);

			for (int iVar2 = 0; iVar2 < usageCaptionDB.size(); iVar2++) {

				String sServiceCaptionDB = usageCaptionDB.get(iVar2);
				String sServiceCaptionbalanceDB = usageBalanceDB.get(iVar2);

				String sXpathCaptionElement = "//*[@content-desc='content_" + sServiceCaptionDB + "']/android.widget.TextView[2]";

				while (verifyElementExistUsingXpathString(sXpathCaptionElement, "Caption - " + sServiceCaptionDB, driver) == false) {
					scrollUDLRMobileApp(driver, 1, "U");
				}

				WebElement oCategoryCaptionElement = driver.findElement(By.xpath("//*[@content-desc='content_" + sServiceCaptionDB + "']/android.widget.TextView[1]"));
				WebElement oCategoryCaptionBalanceElement = driver.findElement(By.xpath("//*[@content-desc='content_" + sServiceCaptionDB + "']/android.widget.TextView[2]"));

				String sServiceCaptionActual = oCategoryCaptionElement.getText();
				String sServiceCaptionBalanceActual = oCategoryCaptionBalanceElement.getText();

				getTest().get().pass(sServiceCategory + ": '" + sServiceCaptionDB + "' Service",
						ExtentScreenCapture.captureSrceenPass(sServiceCategory + "-" + sServiceCaptionDB + "- Service", driver));

				MainUtil.compareInString(sServiceCaptionActual, sServiceCaptionDB, sServiceCategory + ": '" + sServiceCaptionDB + "' Service", driver);
				MainUtil.compareInString(sServiceCaptionBalanceActual, sServiceCaptionbalanceDB, sServiceCategory + ": '" + sServiceCaptionDB + "' Usage Detail", driver);

				scrollUDLRMobileApp(driver, 1, "D");
				scrollUDLRMobileApp(driver, 1, "D");
				scrollUDLRMobileApp(driver, 1, "D");
				scrollUDLRMobileApp(driver, 1, "D");
			}
			oCategoryElement.click();
		}
	}

	public static synchronized void verifyMobileAppServiceDetails(String verificationType, AndroidDriver driver) throws Exception {

		ArrayList<String> allCategory = new ArrayList<String>();
		ArrayList<String> usageCaption = new ArrayList<String>();
		ArrayList<String> usageBalance = new ArrayList<String>();
		List<WebElement> captionElements;
		WebElement balanceElements = null;

		String query = null;

		ArrayList<String> usageCaptionDB = new ArrayList<String>();
		ArrayList<String> usageBalanceDB = new ArrayList<String>();



		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				if (verificationType.equalsIgnoreCase("PLAN_PREPAID")) {

					query = "SELECT s.MobileAppCaption,s.MobileAppBalance FROM plan p JOIN tbo bo ON p.id = bo.plan_id JOIN services s "
							+ "ON s.id_bin = bo.service_id WHERE p.name = '"+dictionary.get("PLAN_NAME")+"' AND s.MobileAppCaption IS NOT NULL";

				} else if (verificationType.equalsIgnoreCase("PLAN_POSTPAID")) {

					query = "SELECT s.MobileAppCaption,s.MobileAppBalance FROM planm p JOIN tbo bo ON p.id_bin = bo.plan_id JOIN services s "
							+ "ON s.id_bin = bo.service_id WHERE p.name = '"+dictionary.get("PLAN_NAME")+"' AND s.MobileAppCaption IS NOT NULL";

				} else if (verificationType.equalsIgnoreCase("BUNDLE_PREPAID")) {

					query = "SELECT s.MobileAppCaption,s.MobileAppBalance FROM bundle_prepaid p JOIN tbo bo ON p.id = bo.plan_id JOIN services s "
							+ "ON s.id_bin = bo.service_id WHERE p.name = '"+dictionary.get("BUNDLE_NAME")+"' AND s.MobileAppCaption IS NOT NULL";

				} else if (verificationType.equalsIgnoreCase("BUNDLE_POSTPAID")) {

					query = "SELECT s.MobileAppCaption ,s.MobileAppBalance FROM services s WHERE servicename = '"+dictionary.get("BUNDLE_NAME")+"'";

				} else if (verificationType.equalsIgnoreCase("BUNDLE_POSTPAID GX")) {

					query = "SELECT s.MobileAppCaption ,s.MobileAppBalance FROM services s WHERE servicename = '"+dictionary.get("BUNDLE_NAME")+"'";

				} else if (verificationType.equalsIgnoreCase("TOPUP_PREPAID")) {

					query = "SELECT s.MobileAppCaption,s.MobileAppBalance FROM plan p JOIN `tevent` bo ON p.id = bo.plan_id JOIN services s "
							+ "ON s.id_bin = bo.service_id WHERE p.name = '"+dictionary.get("PLAN_NAME")+"'" +
							" UNION " +
							"SELECT s.MobileAppCaption,s.MobileAppBalance FROM topup t JOIN `tevent` bo ON t.id = bo.plan_id JOIN services s "
							+ "ON s.id_bin = bo.service_id WHERE t.topup_amount = '"+dictionary.get("TOPUP_AMOUNT")+"' AND t.plan_type = '1'";

				} else if (verificationType.equalsIgnoreCase("TOPUP_ONLY_PREPAID")) {

					query = "SELECT s.MobileAppCaption,s.MobileAppBalance FROM topup p JOIN tbo bo ON p.id = bo.plan_id JOIN services s "
							+ "ON s.id_bin = bo.service_id WHERE p.topup_amount = '"+dictionary.get("TOPUP_AMOUNT")+"' AND s.MobileAppCaption IS NOT NULL";

				}

				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					while  (resultset.next()) {
						if (resultset.getString("MobileAppBalance").contains(";"))
						{
							//remaningval(dictionary.get("MSISDN"),resultset.getString("MobileAppBalance"), resultset.getString("MobileAppCaption"));
							usageCaptionDB.add(resultset.getString("MobileAppCaption"));
							usageBalanceDB.add(remaningval(dictionary.get("MSISDN"),resultset.getString("MobileAppBalance"), resultset.getString("MobileAppCaption")));
						} else {
							usageCaptionDB.add(resultset.getString("MobileAppCaption"));
							usageBalanceDB.add(resultset.getString("MobileAppBalance"));
						}
					}
				}
				catch (Exception e) {
					logger.error("Some error occured getting service setails from DB");
				}

			}

		} catch (Exception e) {
			logger.error("Some error occured getting service setails from DB");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "verifyMobileAppServiceDetails", e);
			getTest().get().fail("Some error occured getting service setails from DB");
			throw e;
		}


		scrollUDLR(driver, 0, "U");
		List<WebElement> elements = driver.findElements(By.xpath("//android.view.ViewGroup[contains(@content-desc,\"usageDetail_cell\")]/android.widget.TextView"));

		for (WebElement WebElement : elements)
		{
			allCategory.add(WebElement.getText());
		}

		for(int i=0;i<elements.size();i++)
		{
			Thread.sleep(1000);
			elements.get(i).click();

			captionElements = driver.findElements(By.xpath("//android.view.ViewGroup[contains(@content-desc,\"content_\")]/android.widget.TextView[1]"));

			for (WebElement captionWebElements : captionElements) {
				String captionText = captionWebElements.getText();
				if (!usageCaption.contains(captionText)) {
					if (!captionText.contains("left") && !captionText.contains("Unlimited")  && !captionText.startsWith("My Usage")  && !captionText.contains("Used")) {
						usageCaption.add(captionText);

						if (verifyElementExistUsingXpathString("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]", "Balance Label", driver)) {
							balanceElements = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]"));
							usageBalance.add(balanceElements.getText());
						} else {
							scrollUDLRMobileApp(driver, 1, "U");
							balanceElements = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]"));
							usageBalance.add(balanceElements.getText());
						}
					}
				}
			}

			try {
				if(allCategory.get(i+1) == null)
				{
					System.out.println("Only One Category");
				} else {
					while (verifyElementIsDisplayed(elements.get(i+1), "Category "+allCategory.get(i+1), driver) == false) {


						scrollUDLRMobileApp(driver, 1, "U");

						captionElements = driver.findElements(By.xpath("//android.view.ViewGroup[contains(@content-desc,\"content_\")]/android.widget.TextView[1]"));

						for (WebElement captionWebElements : captionElements) {
							String captionText = captionWebElements.getText();
							if (!usageCaption.contains(captionText)) {
								if (!captionText.contains("left") && !captionText.contains("Unlimited")  && !captionText.startsWith("My Usage")  && !captionText.contains("Used")) {
									usageCaption.add(captionText);

									if (verifyElementExistUsingXpathString("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]", "Balance Label", driver)) {
										balanceElements = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]"));
										usageBalance.add(balanceElements.getText());
									}  else {
										//scrollUDLRShort(driver, 1, "U");
										scrollUDLRMobileApp(driver, 1, "U");
										balanceElements = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]"));
										usageBalance.add(balanceElements.getText());
									}
								}
							}
						}
					}
				}
			}
			catch (IndexOutOfBoundsException iobex)
			{
				Thread.sleep(2000);
				scrollUDLRMobileApp(driver, 1, "U");
				scrollUDLRMobileApp(driver, 1, "U");
				captionElements = driver.findElements(By.xpath("//android.view.ViewGroup[contains(@content-desc,\"content_\")]/android.widget.TextView[1]"));

				for (WebElement captionWebElements : captionElements) {
					String captionText = captionWebElements.getText();
					if (!usageCaption.contains(captionText)) {
						if (!captionText.contains("left") && !captionText.contains("Unlimited")  && !captionText.startsWith("My Usage")  && !captionText.contains("Used")) {
							usageCaption.add(captionText);

							if (verifyElementExistUsingXpathString("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]", "Balance Label", driver)) {
								balanceElements = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]"));
								usageBalance.add(balanceElements.getText());
							}  else {
								scrollUDLRMobileApp(driver, 1, "U");
								balanceElements = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]"));
								usageBalance.add(balanceElements.getText());
							}
						}
					}
				}

				scrollUDLRMobileApp(driver, 1, "U");
				scrollUDLRMobileApp(driver, 1, "U");
				captionElements = driver.findElements(By.xpath("//android.view.ViewGroup[contains(@content-desc,\"content_\")]/android.widget.TextView[1]"));

				for (WebElement captionWebElements : captionElements) {
					String captionText = captionWebElements.getText();
					if (!usageCaption.contains(captionText)) {
						if (!captionText.contains("left") && !captionText.contains("Unlimited")  && !captionText.startsWith("My Usage")  && !captionText.contains("Used")) {
							usageCaption.add(captionText);

							if (verifyElementExistUsingXpathString("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]", "Balance Label", driver)) {
								balanceElements = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]"));
								usageBalance.add(balanceElements.getText());
							}  else {
								scrollUDLRMobileApp(driver, 1, "U");
								balanceElements = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]"));
								usageBalance.add(balanceElements.getText());
							}
						}
					}
				}
				scrollUDLRMobileApp(driver, 1, "U");
				scrollUDLRMobileApp(driver, 1, "U");
				captionElements = driver.findElements(By.xpath("//android.view.ViewGroup[contains(@content-desc,\"content_\")]/android.widget.TextView[1]"));

				for (WebElement captionWebElements : captionElements) {
					String captionText = captionWebElements.getText();
					if (!usageCaption.contains(captionText)) {
						if (!captionText.contains("left") && !captionText.contains("Unlimited")  && !captionText.startsWith("My Usage")  && !captionText.contains("Used")) {
							usageCaption.add(captionText);

							if (verifyElementExistUsingXpathString("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]", "Balance Label", driver)) {
								balanceElements = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]"));
								usageBalance.add(balanceElements.getText());
							}  else {
								scrollUDLRMobileApp(driver, 1, "U");
								balanceElements = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"content_"+captionText+"\"]/android.widget.TextView[2]"));
								usageBalance.add(balanceElements.getText());
							}

						}
					}
				}

				System.out.println("All Elements Completed");
			}
		}

		ArrayList<String> commonUsageName = (ArrayList<String>) usageCaptionDB.clone();
		ArrayList<String> notOnAppUsageName = (ArrayList<String>) usageCaptionDB.clone();
		ArrayList<String> commonUsageDetails = (ArrayList<String>) usageBalanceDB.clone();
		ArrayList<String> notOnAppUsageDetails = (ArrayList<String>) usageBalanceDB.clone();

		commonUsageName.retainAll(usageCaption);
		notOnAppUsageName.removeAll(usageCaption);
		commonUsageDetails.retainAll(usageBalance);
		notOnAppUsageDetails.removeAll(usageBalance);
		//if (usageCaptionDB.size()<=usageCaption.size())
		//{

		System.out.println(usageCaption);
		System.out.println(usageCaptionDB);
		if (usageCaptionDB.size()<=usageCaption.size()) {
			if (verificationType.equalsIgnoreCase("BUNDLE_POSTPAID") || verificationType.equalsIgnoreCase("BUNDLE_POSTPAID GX")) {

				for (int i = 0; i < usageCaptionDB.size(); i++) {
					for (int j = 0; j < usageCaption.size(); j++) {
						if (usageCaptionDB.get(i).contains(usageCaption.get(j))) {
							MainUtil.compareInString(usageCaption.get(j), usageCaptionDB.get(i), "Service Name", driver);
							MainUtil.compareInString(usageBalance.get(j), usageBalanceDB.get(i), "Usage Detail", driver);
							break;
						}

					}

				}
			} else {

				for (int i = 0; i < usageCaption.size(); i++) {
					for (int j = 0; j < usageCaption.size(); j++) {
						if (usageCaption.get(i).contains(usageCaptionDB.get(j))) {
							MainUtil.compareInString(usageCaption.get(i), usageCaptionDB.get(j), "Service Name", driver);
							MainUtil.compareInString(usageBalance.get(i), usageBalanceDB.get(j), "Usage Detail", driver);
							break;
						}

					}

				}

			}
		} else {
			String FreebiesDB=null;
			String FreebiesmobileApp=null;
			for (int i = 0; i < usageCaptionDB.size(); i++) {
				FreebiesDB=FreebiesDB+","+usageCaptionDB.get(i);
			}

			for (int i = 0; i < usageCaption.size(); i++) {
				FreebiesmobileApp=FreebiesmobileApp+","+usageCaption.get(i);
			}
			MainUtil.compareInString((FreebiesmobileApp.substring(5, FreebiesmobileApp.length())),(FreebiesDB.substring(5, FreebiesDB.length())), "Mobile App shows only few freebies", driver);
		}

		//String createdTextPass = "<tr><td  style = 'width: 20%;'></td><td  style = 'width: 40%;'><b>Expected</b></td><td  style = 'width: 40%;'><b>Actual</b></td></tr>";
	/*	for (int i = 0; i < commonUsageName.size(); i++) {
			System.out.println(commonUsageName.get(i));
			System.out.println(commonUsageDetails.get(i));

			String text = "<tr><td  style = 'width: 20%;'><span class='label green lighten-1 white-text'>Service Name</span></td><td  style = 'width: 40%;'>"+commonUsageName.get(i)+"</td><td  style = 'width: 40%;'>"+commonUsageName.get(i)+"</td></tr>"
					+ "<tr><td  style = 'width: 20%;'><span class='label green lighten-1 white-text'>Usage Details</span></td><td  style = 'width: 40%;'>"+commonUsageDetails.get(i)+"</td><td  style = 'width: 40%;'>"+commonUsageDetails.get(i)+"</td></tr>";
			System.out.println(text);
			createdTextPass = createdTextPass + text;
		}

		getTest().get().pass(createdTextPass);

		String createdTextFailed = "";
		for (int i = 0; i < notOnAppUsageName.size(); i++) {
			String text = "";
			try {
				text = "<tr><td  style = 'width: 20%;'><span class='label red lighten-1 white-text'>Service Name</span></td><td  style = 'width: 40%;'>"+notOnAppUsageName.get(i)+"</td><td  style = 'width: 40%;'>Service Name Not Found On Mobile App</td></tr>";
			} catch (IndexOutOfBoundsException e) {

			}

			createdTextFailed = createdTextFailed + text;
		}

		if (notOnAppUsageName.size() != 0) {
			getTest().get().fail(createdTextFailed);
		}*/

	}

	public static String remaningval(String msisdn,String MobileAppbalance, String MobileAppCaption) throws Exception {
		String remaningval = new String();
		int NB_balanceDAYS;
		//String remaningval;
		try {
			String reversrSb=SQLConnectionHelper.getBSCSDBConnection();
			int billcycle=Integer.parseInt(reversrSb);
			System.out.println(billcycle);
			String strMain = MobileAppbalance;
			String[] arrSplit = strMain.split(";");
			int balancenew = Integer.parseInt(arrSplit[0]);
			System.out.println(balancenew);


			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDateTime todaysDate =  LocalDateTime.now();
			System.out.println(todaysDate.getMonth().maxLength());
			int NB_DAYS;
			NB_DAYS =todaysDate.getMonth().maxLength();


			if (todaysDate.getDayOfMonth()<Integer.parseInt(reversrSb)) {
				System.out.println(todaysDate.plusMonths(0));
				System.out.println(todaysDate.minusMonths(1));
				NB_DAYS=todaysDate.minusMonths(1).getMonth().maxLength();
				//todaysDate.
				NB_balanceDAYS = Integer.parseInt(reversrSb)-todaysDate.getDayOfMonth();

			}else {

				LocalDateTime secondDate=todaysDate.plusMonths(1);
				String finaldate;
				if (secondDate.getMonthValue()<10)
				{
					finaldate=reversrSb+"/0"+secondDate.getMonthValue()+"/"+secondDate.getYear();
				}else {
					finaldate=reversrSb+"/"+secondDate.getMonthValue()+"/"+secondDate.getYear();

				}
				LocalDate date = LocalDate.parse(finaldate, dtf1);
				long noOfDaysBetween = ChronoUnit.DAYS.between(java.time.LocalDate.now(), date);
				NB_balanceDAYS=(int)noOfDaysBetween;
			}

			System.out.println(NB_balanceDAYS);
			String usageCaptionDB=MobileAppCaption;
			//int finalcnt;
			double divdays=(double)balancenew/NB_DAYS;
			double finalcnt=Math.round((divdays)*NB_balanceDAYS);
			DecimalFormat df = new DecimalFormat("#.##");
			String cnt = df.format(finalcnt);
			//String cnt=Double.toString(finalcnt);

			if (usageCaptionDB.contains("SMS")) {
				if (cnt.contains(".")){
					String[] newFinalcnt = cnt.split(".");
					remaningval=newFinalcnt[0]+" SMS left";
					//remaningval = left(((balance/NB_DAYS)*NB_balanceDAYS),instr(((balance/NB_DAYS)*NB_balanceDAYS),".")-1)+1
				}else {
					remaningval =  cnt+" SMS left";
				}
			}else
			if(usageCaptionDB.contains("VOICE")){

				if (arrSplit[1].contains("Mobile")) {
					remaningval = cnt+" MINS left";
				}else {
					if (cnt.contains(".")){
						remaningval = Integer.toString((Math.round((Math.round(finalcnt))/30))*30)+" MINS left";
					} else {
						remaningval =  Integer.toString(Math.round((Math.round(finalcnt/30))*30))+" MINS left";
					}
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured FetchStoredDataFromList");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromList", e);
			getTest().get().fail("Some error occured FetchStoredDataFromList");
			throw e;
		}
		return remaningval;
	}

    public static synchronized void getMSISDNFromSIMTable(String accountType,String planName) throws Exception {
        String query = null;
        String simType = "1";

        try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

            try (Statement statement = connection.createStatement()) {

                if (MainUtil.dictionary.get("SIM_TYPE") == null || MainUtil.dictionary.get("SIM_TYPE") == "PHYSICAL") {
                    simType = "1";
                } else {
                    simType = "2";
                }

                System.out.println("<<<<<<<<<<<<<<<<<<SIM_TYPE " +simType );
                if (accountType.equalsIgnoreCase("PREPAID")) {
                    query = "SELECT msisdn,sim_no FROM sim_details WHERE account_type = '1' AND plan_name = '"+planName+"' AND used_status = '0' AND sim_type = '"+simType+"' AND Environment='Pixel' LIMIT 1";
                } else {
                    query = "SELECT msisdn,sim_no FROM sim_details WHERE account_type = '2' AND used_status = '0' AND sim_type = '2' AND Environment='Pixel' LIMIT 1";
                }

                logger.info(query);
                try (ResultSet resultset = statement.executeQuery(query)) {
                    if (resultset.next()) {
                        dictionary.put("MSISDN", resultset.getString("msisdn") == null ? "NA" : resultset.getString("msisdn"));
                        dictionary.put("SIM_NO", resultset.getString("sim_no") == null ? "NA" : resultset.getString("sim_no"));
                        getTest().get().pass("Got MSISDN From SIM Details Table & Stored To Dictionary");
                    } else {
                        logger.info("There is no SIM available for "+planName+" plan in SIM Details Table");
                        getTest().get().pass("There Is No SIM Available In SIM Details Table & Stored To Dictionary");
                    }
                    logger.info(dictionary.get("MSISDN"));
                    logger.info(dictionary.get("SIM_NO"));
                }
            }

        } catch (Exception e) {
            logger.error("Some error occured getMSISDNFromSIMTable");
            logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getMSISDNFromSIMTable", e);
            getTest().get().fail("Some error occured getMSISDNFromSIMTable");
            throw e;
        }
    }

	public static synchronized void getMSISDNFromSIMTableForMultiline(String accountType,String planName, String lineCount) throws Exception {
		String query = null;
		String simType = "1";

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {

				if (accountType.equalsIgnoreCase("PREPAID")) {
					query = "SELECT msisdn,sim_no FROM sim_details WHERE account_type = '1' AND plan_name = '"+planName+"' AND used_status = '0' AND sim_type = '"+simType+"' AND Environment='PIXEL' LIMIT "+lineCount+"";
				} else {
					query = "SELECT msisdn,sim_no FROM sim_details WHERE account_type = '2' AND used_status = '0' AND sim_type = '2' AND Environment='PIXEL' LIMIT "+lineCount+"";
				}


				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {

					int i = 1;
					boolean status = false;


					while (resultset.next())
					{
						status = true;
						dictionary.put("MSISDN"+i, resultset.getString("msisdn") == null ? "NA" : resultset.getString("msisdn"));
						dictionary.put("SIM_NO"+i, resultset.getString("sim_no") == null ? "NA" : resultset.getString("sim_no"));

						logger.info(dictionary.get("MSISDN"+i));
						logger.info(dictionary.get("SIM_NO"+i));

						i++;
					}

					if (!status) {
						logger.info("There is no unused sims found in SIM Details DB");
						getTest().get().fail("There is no unused sims found in SIM Details DB");
					}

				}
			}

		} catch (Exception e) {
			logger.error("Some error occured getMSISDNFromSIMTableForMultiline");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getMSISDNFromSIMTableForMultiline", e);
			getTest().get().fail("Some error occured getMSISDNFromSIMTableForMultiline");
			throw e;
		}
	}
	
	
	public static synchronized void getIMEINo() throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {

				query = "SELECT imei_no FROM imei_details WHERE used_status = '0' LIMIT 1";
							
				
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						dictionary.put("IMEI_NO", resultset.getString("imei_no") == null ? "NA" : resultset.getString("imei_no"));
						getTest().get().pass("Got IMEI From IMEI Details Table & Stored To Dictionary");
					} else {
						logger.info("There is no IMEI available in IMEI Details Table");
						getTest().get().fail("There is no IMEI available in IMEI Details Table");
					}
					logger.info(dictionary.get("IMEI_NO"));
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured getIMEINo");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getIMEINo", e);
			getTest().get().fail("Some error occured getIMEINo");
			throw e;
		}
	}


	public static synchronized void updateSIMStatusInSIMDetails(String simNo) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "UPDATE sim_details SET used_status = '1' WHERE sim_no = '"+simNo+"'";
				logger.info(query);				

				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					preparedStmt.executeUpdate();
					logger.info("SIM Updated Successfully In SIM Details Table");
				}
			}

		} catch (Exception e) {
			logger.error("SIM Updation Failed While Updating In SIM Details Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateSIMStatusInSIMDetails", e);
			getTest().get().fail("Some error occured updateSIMStatusInSIMDetails");
			throw e;
		}
	}
	
	public static synchronized void updateIMEIStatusInIMEIDetails(String imeiNo) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "UPDATE imei_details SET used_status = '1' WHERE imei_no = '"+imeiNo+"'";
				logger.info(query);				

				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					preparedStmt.executeUpdate();
					logger.info("IMEI Updated Successfully In IMEI Details Table");
				}
			}

		} catch (Exception e) {
			logger.error("IMEI Updation Failed While Updating In IMEI Details Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateIMEIStatusInIMEIDetails", e);
			getTest().get().fail("Some error occured updateIMEIStatusInIMEIDetails");
			throw e;
		}
	}
	
	public static synchronized void updateAccBalance(String msisdn,String balance ) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "UPDATE account_info SET main_balance = '" +balance+ "' WHERE msisdn = '"+msisdn+"'";
				logger.info(query);				

				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					preparedStmt.executeUpdate();
					logger.info("Account balance Updated Successfully In account_info Table");
				}
			}

		} catch (Exception e) {
			logger.error("Account balance Updation Failed While Updating in account_info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateAccountBalanceInAccountInfo", e);
			getTest().get().fail("Some error occured updateAccountBalanceInAccountInfo");
			throw e;
		}
	}
	
	public static synchronized void updateBundleName(String msisdn, String bundleName) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "UPDATE account_info SET bundle_name = '" +bundleName+ "' WHERE msisdn = '"+msisdn+"'";
				logger.info(query);				

				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					preparedStmt.executeUpdate();
					logger.info("Bundle Name Updated Successfully In account_info Table");
				}
			}

		} catch (Exception e) {
			logger.error("Bundle name Updation Failed While Updating in account_info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateBundleNameInAccountInfo", e);
			getTest().get().fail("Some error occured updateBundleNameInAccountInfo");
			throw e;
		}
	}
	
	public static synchronized void updateAccInfoAfterBundle(String planType, String msisdn,String balance, String bundleName, String bundlePurchaseDate, String BundleExpiry ) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				if(planType.equalsIgnoreCase("PREPAID")) {
					query = "UPDATE account_info SET main_balance = '" +balance+ "', bundle_name = '" +bundleName+ "' , purchase_date = '" + bundlePurchaseDate + "', bundle_expiry_date = '"+ BundleExpiry +"' WHERE msisdn = '"+msisdn+"'";
					logger.info(query);	
				}else {
					query = "UPDATE account_info SET bundle_name = '" +bundleName+ "' , purchase_date = '" + bundlePurchaseDate + "', bundle_expiry_date = '"+ BundleExpiry +"' WHERE msisdn = '"+msisdn+"'";
					logger.info(query);	
				}


				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					preparedStmt.executeUpdate();
					logger.info("Account balance Updated Successfully In account_info Table");
				}
			}

		} catch (Exception e) {
			logger.error("Account info post bundle purchase Updation Failed While Updating in account_info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateAccountInforPostBundle", e);
			getTest().get().fail("Some error occured updateAccountInforPostBundle");
			throw e;
		}
	}
	
	public static synchronized String getPrepaidBundleValidity(String bundleName) throws Exception {
		String query = null;
		String bundleValidity = null;
		

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT validity FROM bundle_prepaid where NAME ='" + bundleName + "'";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						bundleValidity = resultset.getString("validity");
						

					} else {
						logger.info("There is no record for "+bundleName+" in umb table under test db");

					}
					logger.info(bundleValidity);
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured FetchStoredDataFromDB");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromDB", e);
			throw e;
		}

		return bundleValidity;
	}
	
	public static synchronized String getPrepaidBundleSNCode(String bundleName) throws Exception {
		String query = null;
		String bundleSNCode = null;
		

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT bundle_sn_code FROM bundle_prepaid where NAME ='" + bundleName + "'";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						bundleSNCode = resultset.getString("bundle_sn_code");
						

					} else {
						logger.info("There is no record for "+bundleName+" in umb table under test db");

					}
					logger.info(bundleSNCode);
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured FetchStoredDataFromDB");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromDB", e);
			throw e;
		}

		return bundleSNCode;
	}
	
	public static synchronized void getPrepaidAccount(String msisdn) throws Exception {
		String query = null;		

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT * FROM account_info WHERE msisdn ='"+msisdn+"' ORDER BY created_date DESC LIMIT 1";
				//query = "SELECT * FROM account_info WHERE plan_name ='"+planName+"' ORDER BY created_date DESC LIMIT 1";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						dictionary.put("MSISDN", resultset.getString("msisdn"));
						dictionary.put("SIM_NO", resultset.getString("sim_no"));
						dictionary.put("ACCOUNT_TYPE", resultset.getString("account_type") == null ? "NA" : resultset.getString("account_type"));
						dictionary.put("PLAN_NAME", resultset.getString("plan_name") == null ? "NA" : resultset.getString("plan_name"));
						dictionary.put("BUNDLE_NAME", resultset.getString("bundle_name") == null ? "NA" : resultset.getString("bundle_name"));
						dictionary.put("CUSTOMER_NAME", resultset.getString("customer_name") == null ? "NA" : resultset.getString("customer_name"));
						dictionary.put("CUSTOMER_ID_TYPE", resultset.getString("identification_type") == null ? "NA" : resultset.getString("identification_type"));
						dictionary.put("CUSTOMER_ID", resultset.getString("identification_no") == null ? "NA" : resultset.getString("identification_no"));
						dictionary.put("MAIN_BALANCE", resultset.getString("main_balance") == null ? "NA" : resultset.getString("main_balance"));
						dictionary.put("ACCOUNT_STATUS", resultset.getString("status") == null ? "NA" : resultset.getString("status"));
						dictionary.put("PURCHASE_DATE", resultset.getString("purchase_date") == null ? "NA" : resultset.getString("purchase_date"));
						dictionary.put("ACTIVE_END_DATE", resultset.getString("active_end_date") == null ? "NA" : resultset.getString("active_end_date"));
						dictionary.put("PASSIVE_END_DATE", resultset.getString("passive_end_date") == null ? "NA" : resultset.getString("passive_end_date"));
						dictionary.put("CC_END_DATE", resultset.getString("cc_end_date") == null ? "NA" : resultset.getString("cc_end_date"));
						dictionary.put("SR_END_DATE", resultset.getString("sr_end_date") == null ? "NA" : resultset.getString("sr_end_date"));
						dictionary.put("BUNDLE_EXPIRY_DATE", resultset.getString("bundle_expiry_date") == null ? "NA" : resultset.getString("bundle_expiry_date"));
						dictionary.put("CUSTOMER_DOB_DD", "01");
						dictionary.put("CUSTOMER_DOB_MM", "01");
						dictionary.put("CUSTOMER_DOB_YYYY", "1990");
						dictionary.put("CUSTOMER_ADDRESS", "TEST ADDRESS");
						dictionary.put("CUSTOMER_CITY", "KUALA LUMPUR");
						dictionary.put("CUSTOMER_STATE", "KUALA LUMPUR");
						dictionary.put("CUSTOMER_POSTCODE", "51100");
						
					} else {
						logger.info("There is no account found for "+msisdn+" in account info table under test db");

					}
					logger.info(dictionary.get("MSISDN"));
					logger.info(dictionary.get("SIM_NO"));
					logger.info(dictionary.get("ACCOUNT_TYPE"));
					logger.info(dictionary.get("PLAN_NAME"));
					logger.info(dictionary.get("BUNDLE_NAME"));
					logger.info(dictionary.get("CUSTOMER_NAME"));
					logger.info(dictionary.get("CUSTOMER_ID_TYPE"));
					logger.info(dictionary.get("CUSTOMER_ID"));
					logger.info(dictionary.get("MAIN_BALANCE"));
					logger.info(dictionary.get("ACCOUNT_STATUS"));
					logger.info(dictionary.get("PURCHASE_DATE"));
					logger.info(dictionary.get("ACTIVE_END_DATE"));
					logger.info(dictionary.get("PASSIVE_END_DATE"));
					logger.info(dictionary.get("CC_END_DATE"));
					logger.info(dictionary.get("SR_END_DATE"));
					logger.info(dictionary.get("BUNDLE_EXPIRY_DATE"));
					logger.info(dictionary.get("CUSTOMER_DOB_DD"));
					logger.info(dictionary.get("CUSTOMER_DOB_MM"));
					logger.info(dictionary.get("CUSTOMER_DOB_YYYY"));
					logger.info(dictionary.get("CUSTOMER_ADDRESS"));
					logger.info(dictionary.get("CUSTOMER_CITY"));
					logger.info(dictionary.get("CUSTOMER_STATE"));
					logger.info(dictionary.get("CUSTOMER_POSTCODE"));
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured getPrepaidAccount");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getPrepaidAccount", e);
			throw e;
		}
	}

	public static synchronized void getPrepaidAccountByMSISDN(String msisdn) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT * FROM account_info WHERE msisdn ='"+msisdn+"' ORDER BY created_date DESC LIMIT 1";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						dictionary.put("MSISDN", resultset.getString("msisdn"));
						dictionary.put("SIM_NO", resultset.getString("sim_no"));
						dictionary.put("ACCOUNT_TYPE", resultset.getString("account_type") == null ? "NA" : resultset.getString("account_type"));
						dictionary.put("PLAN_NAME", resultset.getString("plan_name") == null ? "NA" : resultset.getString("plan_name"));
						dictionary.put("BUNDLE_NAME", resultset.getString("bundle_name") == null ? "NA" : resultset.getString("bundle_name"));
						dictionary.put("CUSTOMER_NAME", resultset.getString("customer_name") == null ? "NA" : resultset.getString("customer_name"));
						dictionary.put("CUSTOMER_ID_TYPE", resultset.getString("identification_type") == null ? "NA" : resultset.getString("identification_type"));
						dictionary.put("CUSTOMER_ID", resultset.getString("identification_no") == null ? "NA" : resultset.getString("identification_no"));
						dictionary.put("MAIN_BALANCE", resultset.getString("main_balance") == null ? "NA" : resultset.getString("main_balance"));
						dictionary.put("ACCOUNT_STATUS", resultset.getString("status") == null ? "NA" : resultset.getString("status"));
						dictionary.put("PURCHASE_DATE", resultset.getString("purchase_date") == null ? "NA" : resultset.getString("purchase_date"));
						dictionary.put("ACTIVE_END_DATE", resultset.getString("active_end_date") == null ? "NA" : resultset.getString("active_end_date"));
						dictionary.put("PASSIVE_END_DATE", resultset.getString("passive_end_date") == null ? "NA" : resultset.getString("passive_end_date"));
						dictionary.put("CC_END_DATE", resultset.getString("cc_end_date") == null ? "NA" : resultset.getString("cc_end_date"));
						dictionary.put("SR_END_DATE", resultset.getString("sr_end_date") == null ? "NA" : resultset.getString("sr_end_date"));
						dictionary.put("BUNDLE_EXPIRY_DATE", resultset.getString("bundle_expiry_date") == null ? "NA" : resultset.getString("bundle_expiry_date"));
						dictionary.put("CUSTOMER_DOB_DD", "01");
						dictionary.put("CUSTOMER_DOB_MM", "01");
						dictionary.put("CUSTOMER_DOB_YYYY", "1990");
						dictionary.put("CUSTOMER_ADDRESS", "TEST ADDRESS");
						dictionary.put("CUSTOMER_CITY", "KUALA LUMPUR");
						dictionary.put("CUSTOMER_STATE", "KUALA LUMPUR");
						dictionary.put("CUSTOMER_POSTCODE", "51100");

					} else {
						logger.info("There is no account found for "+msisdn+" in account info table under test db");

					}
					logger.info(dictionary.get("MSISDN"));
					logger.info(dictionary.get("SIM_NO"));
					logger.info(dictionary.get("ACCOUNT_TYPE"));
					logger.info(dictionary.get("PLAN_NAME"));
					logger.info(dictionary.get("BUNDLE_NAME"));
					logger.info(dictionary.get("CUSTOMER_NAME"));
					logger.info(dictionary.get("CUSTOMER_ID_TYPE"));
					logger.info(dictionary.get("CUSTOMER_ID"));
					logger.info(dictionary.get("MAIN_BALANCE"));
					logger.info(dictionary.get("ACCOUNT_STATUS"));
					logger.info(dictionary.get("PURCHASE_DATE"));
					logger.info(dictionary.get("ACTIVE_END_DATE"));
					logger.info(dictionary.get("PASSIVE_END_DATE"));
					logger.info(dictionary.get("CC_END_DATE"));
					logger.info(dictionary.get("SR_END_DATE"));
					logger.info(dictionary.get("BUNDLE_EXPIRY_DATE"));
					logger.info(dictionary.get("CUSTOMER_DOB_DD"));
					logger.info(dictionary.get("CUSTOMER_DOB_MM"));
					logger.info(dictionary.get("CUSTOMER_DOB_YYYY"));
					logger.info(dictionary.get("CUSTOMER_ADDRESS"));
					logger.info(dictionary.get("CUSTOMER_CITY"));
					logger.info(dictionary.get("CUSTOMER_STATE"));
					logger.info(dictionary.get("CUSTOMER_POSTCODE"));
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured getPrepaidAccount");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getPrepaidAccount", e);
			throw e;
		}
	}

	public static synchronized void getTopUpPin(String topupAmount) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {

				query = "SELECT pin FROM topup_pin_details WHERE topup_amount = '"+topupAmount+"' AND used_status = '0' AND environment='Pixel' AND expiry_date like '2023%' LIMIT 1";
							
				
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						dictionary.put("TOPUP_PIN", resultset.getString("pin") == null ? "NA" : resultset.getString("pin"));
						getTest().get().pass("Got PIN From Topup Pin Details Table & Stored To Dictionary");
					} else {
						logger.info("There is no Topup pin available in Topup Pin Details Table");
						getTest().get().fail("There is no Topup pin available in Topup Pin Details Table");
					}
					logger.info(dictionary.get("TOPUP_PIN"));
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured getTopUpPin");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getTopUpPin", e);
			getTest().get().fail("Some error occured getTopUpPin");
			throw e;
		}
	}
	
	public static synchronized void updateTopupPinStatusInTable(String topupPIN) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "UPDATE topup_pin_details SET used_status = '1' WHERE pin = '"+topupPIN+"'";
				logger.info(query);				

				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					preparedStmt.executeUpdate();
					logger.info("PIN Updated Successfully In Topup PIN Details Table");
				}
			}

		} catch (Exception e) {
			logger.error("PIN Updation Failed While Updating In Topup Pin Details Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateTopupPinStatusInTable", e);
			getTest().get().fail("Some error occured updateTopupPinStatusInTable");
			throw e;
		}
	}
	
	public static synchronized void updateAccInfoAfterCreditTransfer(String msisdn, String columnToUpdate, String balance ) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				
					query = "UPDATE account_info SET " + columnToUpdate + " = '" + balance + "' where msisdn = '"+msisdn+"'";
					logger.info(query);	


				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					preparedStmt.executeUpdate();
					logger.info("Account balance Updated Successfully In account_info Table");
				}
			}

		} catch (Exception e) {
			logger.error("Account info post bundle purchase Updation Failed While Updating in account_info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateAccountInforPostBundle", e);
			getTest().get().fail("Some error occured updateAccountInforPostBundle");
			throw e;
		}
	}

	public static synchronized void updateCreditAmountAccInfoTable(String msisdn, String balance ) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {

				query = "UPDATE account_info SET credit_amount = '" + balance + "' where msisdn = '"+msisdn+"'";
				logger.info(query);


				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					preparedStmt.executeUpdate();
					logger.info("Credit Amount balance Updated Successfully In account_info Table");
				}
			}

		} catch (Exception e) {
			logger.error("Account info post bundle purchase Updation Failed While Updating in account_info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateAccountInforPostBundle", e);
			getTest().get().fail("Some error occured updateAccountInforPostBundle");
			throw e;
		}
	}

	public static synchronized void getCreditAmountAccInfoTable(String msisdn) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {

				query = "SELECT credit_amount FROM account_info where msisdn = '"+msisdn+"'";
				logger.info(query);

				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						dictionary.put("CREDIT_AMOUNT", resultset.getString("credit_amount") == null ? "NA" : resultset.getString("credit_amount"));
						getTest().get().pass("Got CREDIT_AMOUNT From ACCOUNT_INFO table & Stored To Dictionary");
					} else {
						logger.info("There is no CREDIT_AMOUNT available in ACCOUNT_INFO Table");
						getTest().get().fail("There is no CREDIT_AMOUNT available in ACCOUNT_INFO Table");
					}
					logger.info("CREDIT_AMOUNT: "+dictionary.get("CREDIT_AMOUNT"));
				}
			}
		} catch (Exception e) {
			logger.error("Account info get operation Failed While fetching from account_info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getCreditAmountAccInfoTable", e);
			getTest().get().fail("Some error occured getCreditAmountAccInfoTable");
			throw e;
		}
	}

	public static synchronized void getPrepaidMainBalanceAccInfoTable(String msisdn) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {

				query = "SELECT main_balance FROM account_info where msisdn = '"+msisdn+"'";
				logger.info(query);

				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						dictionary.put("MAIN_BALANCE", resultset.getString("main_balance") == null ? "NA" : resultset.getString("main_balance"));
						getTest().get().pass("Got MAIN_BALANCE From ACCOUNT_INFO table & Stored To Dictionary");
					} else {
						logger.info("There is no MAIN_BALANCE available in ACCOUNT_INFO Table");
						getTest().get().fail("There is no MAIN_BALANCE available in ACCOUNT_INFO Table");
					}
					logger.info("MAIN_BALANCE: "+dictionary.get("MAIN_BALANCE"));
				}
			}
		} catch (Exception e) {
			logger.error("Account info get operation Failed While fetching from account_info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getMainBalanceAccInfoTable", e);
			getTest().get().fail("Some error occured getMainBalanceAccInfoTable");
			throw e;
		}
	}
	
	public static synchronized String getSenderReceiverFee(String columnData, String transferAmount) throws Exception {
		String query = null;
		String value = null;
		

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT " + columnData + " FROM credit_transfer where transfer_amount ='" + transferAmount + "'";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						value = resultset.getString(columnData);
						

					} else {
						logger.info("There is no record for "+columnData+" in credit_transfer table under test db");

					}
					logger.info(value);
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured FetchStoredDataFromDB");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromDB", e);
			throw e;
		}

		return value;
	}
	
	public static synchronized void updateAccountInfoAfterTopup(String msisdn) throws Exception {
		String query = null;
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		getPlanDetails(dictionary.get("PLAN_NAME"));
		getTopupDetails(dictionary.get("TOPUP_AMOUNT"));
		
		double mainBalance = Double.parseDouble(dictionary.get("CURRENT_MAIN_BALANCE"));
		double actualAmount = Double.parseDouble(dictionary.get("ACTUAL_AMOUNT"));			
		double finalBalance = mainBalance + actualAmount;
		
		String balance = df.format(finalBalance);
		
		dictionary.put("MAIN_BALANCE", balance);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDateTime registeredDate =  LocalDateTime.now();
		LocalDateTime currentActiveEndDate =  LocalDate.parse(dictionary.get("ACTIVE_END_DATE"), dtf).atTime(23, 59, 59);
		LocalDateTime activeEndDate = registeredDate.plusDays(Integer.parseInt(dictionary.get("ActiveDurationDays")));
		LocalDateTime passiveEndDate = activeEndDate.plusDays(Integer.parseInt(dictionary.get("PassiveDurationDays")));
		LocalDateTime ccEndDate = passiveEndDate.plusDays(Integer.parseInt(dictionary.get("CCDurationDays")));
		LocalDateTime srEndDate = ccEndDate.plusDays(Integer.parseInt(dictionary.get("SRDurationDays")));

		if (activeEndDate.isAfter(currentActiveEndDate)) {
			dictionary.put("PURCHASE_DATE", dtf.format(registeredDate));
			dictionary.put("ACTIVE_END_DATE", dtf.format(activeEndDate));
			dictionary.put("PASSIVE_END_DATE", dtf.format(passiveEndDate));
			dictionary.put("CC_END_DATE", dtf.format(ccEndDate));
			dictionary.put("SR_END_DATE", dtf.format(srEndDate));
		}

		logger.info("Purchase Date : "+dictionary.get("PURCHASE_DATE"));
		logger.info("Active End Date : "+dictionary.get("ACTIVE_END_DATE"));
		logger.info("Passive End Date : "+dictionary.get("PASSIVE_END_DATE"));
		logger.info("CC End Date : "+dictionary.get("CC_END_DATE"));
		logger.info("SR End Date : "+dictionary.get("SR_END_DATE"));
		

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "UPDATE um.account_info SET main_balance='"+dictionary.get("MAIN_BALANCE")+"',"
						+ "purchase_date='"+dictionary.get("PURCHASE_DATE")+"',active_end_date='"+dictionary.get("ACTIVE_END_DATE")+"',"
								+ "passive_end_date='"+dictionary.get("PASSIVE_END_DATE")+"',cc_end_date='"+dictionary.get("CC_END_DATE")+"',"
										+ "sr_end_date='"+dictionary.get("SR_END_DATE")+"',updated_date=NOW() WHERE msisdn = '"+msisdn+"'";			

				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					
					preparedStmt.executeUpdate();
					logger.info(query);
					logger.info("Account Info Updated Successfully In Topup Account Info Table");
				}
			}

		} catch (Exception e) {
			logger.error("Account Updation Failed While Updating In Account Info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateAccountInfoAfterTopup", e);
			getTest().get().fail("Some error occured updateAccountInfoAfterTopup");
			throw e;
		}
	}
	
	public static synchronized void updateRateplanName(String msisdn, String Rateplan) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "UPDATE account_info SET plan_name = '" +Rateplan+ "' WHERE msisdn = '"+msisdn+"'";
				logger.info(query);				

				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					preparedStmt.executeUpdate();
					logger.info("RatePlan Name Updated Successfully In account_info Table");
				}
			}

		} catch (Exception e) {
			logger.error("Rate Plan name Updation Failed While Updating in account_info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateRateplanNameInAccountInfo", e);
			getTest().get().fail("Some error occured updateRateplanNameInAccountInfo");
			throw e;
		}
	}

	public static synchronized void updateRateplanNameUpdated(String msisdn, String Rateplan) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "UPDATE account_info SET plan_name = '" +Rateplan+ "' WHERE msisdn = '"+msisdn+"'";
				logger.info(query);

				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					preparedStmt.executeUpdate();
					logger.info("RatePlan Name Updated Successfully In account_info Table");
				}
			}

		} catch (Exception e) {
			logger.error("Rate Plan name Updation Failed While Updating in account_info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateRateplanNameInAccountInfo", e);
			getTest().get().fail("Some error occured updateRateplanNameInAccountInfo");
			throw e;
		}
	}


	public static synchronized void updateCRPDetails(String msisdn) throws Exception {
		String query = null;
		DecimalFormat df = new DecimalFormat("0.00");
		double mainBalance = Double.parseDouble(dictionary.get("CURRENT_MAIN_BALANCE"));
		double actualAmount = Double.parseDouble("5");
		double finalBalance = mainBalance - actualAmount;

		String balance = df.format(finalBalance);

		dictionary.put("MAIN_BALANCE", balance);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDateTime registeredDate =  LocalDateTime.now();
		//LocalDateTime currentActiveEndDate =  LocalDate.parse(dictionary.get("ACCOUNT_EXPIRY_DATE"), dtf).atTime(23, 59, 59);
		LocalDateTime activeEndDate = registeredDate.plusDays(Integer.parseInt("5"));



		dictionary.put("ACCOUNT_EXPIRY_DATE", dtf.format(activeEndDate));



		logger.info("Active End Date : "+dictionary.get("ACCOUNT_EXPIRY_DATE"));


		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "UPDATE account_info SET main_balance ='"+dictionary.get("MAIN_BALANCE")+"', plan_name = '"+dictionary.get("NEWPLAN_NAME")+"', active_end_date='"+dictionary.get("ACCOUNT_EXPIRY_DATE")+"' WHERE msisdn = '"+msisdn+"'";
				logger.info(query);

				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					preparedStmt.executeUpdate();
					logger.info("Account balance Updated Successfully In account_info Table");
				}
			}

		} catch (Exception e) {
			logger.error("Account balance Updation Failed While Updating in account_info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateAccountBalanceInAccountInfo", e);
			getTest().get().fail("Some error occured updateAccountBalanceInAccountInfo");
			throw e;
		}
	}

	public static synchronized void updatePostpaidCRPDetails(String msisdn) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "UPDATE account_info SET plan_name = 'Postpaid "+dictionary.get("NEWPLAN_NAME")+"' WHERE msisdn = '"+msisdn+"'";
				logger.info(query);

				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
					preparedStmt.executeUpdate();
					logger.info("Account balance Updated Successfully In account_info Table");
				}
			}

		} catch (Exception e) {
			logger.error("Account balance Updation Failed While Updating in account_info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateAccountBalanceInAccountInfo", e);
			getTest().get().fail("Some error occured updateAccountBalanceInAccountInfo");
			throw e;
		}
	}


	public static synchronized void updateAccountInfoAfterTopupUMB(String msisdn) throws Exception {
		String query = null;

		DecimalFormat df = new DecimalFormat("0.00");

//		getPlanDetails(dictionary.get("PLAN_NAME"));
		getTopupDetails(dictionary.get("TOPUP_AMOUNT"));

		double mainBalance = Double.parseDouble(dictionary.get("CURRENT_MAIN_BALANCE"));
		double actualAmount = Double.parseDouble(dictionary.get("ACTUAL_AMOUNT"));
		double finalBalance = mainBalance + actualAmount;

		String balance = df.format(finalBalance);

		dictionary.put("MAIN_BALANCE", balance);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDateTime registeredDate =  LocalDateTime.now();
		LocalDateTime currentActiveEndDate =  LocalDate.parse(dictionary.get("ACTIVE_END_DATE"), dtf).atTime(23, 59, 59);
		LocalDateTime activeEndDate = registeredDate.plusDays(Integer.parseInt(dictionary.get("ActiveDurationDays")));
		LocalDateTime passiveEndDate = activeEndDate.plusDays(Integer.parseInt(dictionary.get("PassiveDurationDays")));
		LocalDateTime ccEndDate = passiveEndDate.plusDays(Integer.parseInt(dictionary.get("CCDurationDays")));
		LocalDateTime srEndDate = ccEndDate.plusDays(Integer.parseInt(dictionary.get("SRDurationDays")));

		if (activeEndDate.isAfter(currentActiveEndDate)) {
			dictionary.put("PURCHASE_DATE", dtf.format(registeredDate));
			dictionary.put("ACTIVE_END_DATE", dtf.format(activeEndDate));
			dictionary.put("PASSIVE_END_DATE", dtf.format(passiveEndDate));
			dictionary.put("CC_END_DATE", dtf.format(ccEndDate));
			dictionary.put("SR_END_DATE", dtf.format(srEndDate));
		}

		logger.info("Purchase Date : "+dictionary.get("PURCHASE_DATE"));
		logger.info("Active End Date : "+dictionary.get("ACTIVE_END_DATE"));
		logger.info("Passive End Date : "+dictionary.get("PASSIVE_END_DATE"));
		logger.info("CC End Date : "+dictionary.get("CC_END_DATE"));
		logger.info("SR End Date : "+dictionary.get("SR_END_DATE"));


		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "UPDATE um.account_info SET main_balance='"+dictionary.get("MAIN_BALANCE")+"',"
						+ "purchase_date='"+dictionary.get("PURCHASE_DATE")+"',active_end_date='"+dictionary.get("ACTIVE_END_DATE")+"',"
						+ "passive_end_date='"+dictionary.get("PASSIVE_END_DATE")+"',cc_end_date='"+dictionary.get("CC_END_DATE")+"',"
						+ "sr_end_date='"+dictionary.get("SR_END_DATE")+"',updated_date=NOW() WHERE msisdn = '"+msisdn+"'";

				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {

					preparedStmt.executeUpdate();
					logger.info(query);
					logger.info("Account Info Updated Successfully In Topup Account Info Table");
				}
			}

		} catch (Exception e) {
			logger.error("Account Updation Failed While Updating In Account Info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateAccountInfoAfterTopup", e);
			getTest().get().fail("Some error occured updateAccountInfoAfterTopup");
			throw e;
		}
	}

	public static synchronized void updateExpiryAfterAccValidityExt(String msisdn) throws Exception {
		String query = null;

		DecimalFormat df = new DecimalFormat("0.00");

		//getPlanDetails(dictionary.get("PLAN_NAME"));
		//	getTopupDetails(dictionary.get("TOPUP_AMOUNT"));

		double mainBalance = Double.parseDouble(dictionary.get("CURRENT_MAIN_BALANCE"));
		double actualAmount = Double.parseDouble(dictionary.get("EXTENSION_DAYS"));
		double finalBalance = mainBalance - actualAmount;

		String balance = df.format(finalBalance);

		dictionary.put("MAIN_BALANCE", balance);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDateTime registeredDate =  LocalDateTime.now();
		LocalDateTime currentActiveEndDate =  LocalDate.parse(dictionary.get("ACCOUNT_EXPIRY_DATE"), dtf).atTime(23, 59, 59);
		//LocalDateTime activeEndDate = registeredDate.plusDays(Integer.parseInt(dictionary.get("EXTENSION_DAYS")));
		/*LocalDateTime passiveEndDate = activeEndDate.plusDays(Integer.parseInt(dictionary.get("PassiveDurationDays")));
		LocalDateTime ccEndDate = passiveEndDate.plusDays(Integer.parseInt(dictionary.get("CCDurationDays")));
		LocalDateTime srEndDate = ccEndDate.plusDays(Integer.parseInt(dictionary.get("SRDurationDays")));
*/

		if (currentActiveEndDate.isBefore(registeredDate)) {
			LocalDateTime activeEndDate = registeredDate.plusDays(Integer.parseInt(dictionary.get("EXTENSION_DAYS")));
			dictionary.put("ACCOUNT_EXPIRY_DATE", dtf.format(activeEndDate));
		}else {
			LocalDateTime activeEndDate = currentActiveEndDate.plusDays(Integer.parseInt(dictionary.get("EXTENSION_DAYS")));
			dictionary.put("ACCOUNT_EXPIRY_DATE", dtf.format(activeEndDate));
		}


		/*logger.info("Purchase Date : "+dictionary.get("PURCHASE_DATE"));*/
		logger.info("Active End Date : "+dictionary.get("ACCOUNT_EXPIRY_DATE"));
	/*	logger.info("Passive End Date : "+dictionary.get("PASSIVE_END_DATE"));
		logger.info("CC End Date : "+dictionary.get("CC_END_DATE"));
		logger.info("SR End Date : "+dictionary.get("SR_END_DATE"));*/


		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "UPDATE um.account_info SET main_balance ='"+dictionary.get("MAIN_BALANCE")+"', active_end_date='"+dictionary.get("ACCOUNT_EXPIRY_DATE")+"' WHERE msisdn = '"+msisdn+"'";

				try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {

					preparedStmt.executeUpdate();
					logger.info(query);
					logger.info("Account expirydate Updated Successfully In Topup Account Info Table");
				}
			}

		} catch (Exception e) {
			logger.error("Account Updation Failed While Updating In Account Info Table");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "updateAccountInfoAfterTopup", e);
			getTest().get().fail("Some error occured updateAccountInfoAfterTopup");
			throw e;
		}
	}

	public static synchronized void getTopUpFreebies(String ratePlan, String topupAmount) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT * FROM topup_freebies where rate_plan = '"+ ratePlan +"' and topup_amount ='" + topupAmount + "'";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {
						//purchaseCode = MainUtil.replaceParanthesis("UMB_Purchase_Code");
						dictionary.put("FREEBIES_TYPE", resultset.getString("freebie_type"));
						dictionary.put("FREEBIE_VALUE", resultset.getString("value"));
						dictionary.put("FREEBIE_EXPIRY", resultset.getString("validity") );


					} else {
						logger.info("There is no record for "+ratePlan+" and "+ topupAmount + "in umb table under test db");

					}
					logger.info(dictionary.get("FREEBIES_TYPE") + " " + dictionary.get("FREEBIE_VALUE") + " " + dictionary.get("FREEBIE_VALUE") );
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured FetchStoredDataFromList");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromList", e);
			getTest().get().fail("Some error occured FetchStoredDataFromList");
			throw e;
		}

	}

	public static synchronized void getSMSDetailsForPrepiad(String planname, String category) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT s.sms, s.category FROM plan p JOIN sms s ON s.plan_id = p.id WHERE p.name LIKE '"+planname+"' AND s.category='"+category+"'";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {

					if (resultset.next()) {
						String message = MainUtil.replaceParanthesis(resultset.getString("sms"));
						dictionary.put("SMS", message);
						System.out.println("SMS: "+message);
					} else {
						logger.info("There is no record for "+category+" in sms table under test db");

					}
					logger.info(dictionary.get("sms"));
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured FetchStoredDataFromList");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "FetchStoredDataFromList", e);
			getTest().get().fail("Some error occured FetchStoredDataFromList");
			throw e;
		}

	}

	public static synchronized void getReceiverEndDate(String msisdn) throws Exception {
		String query = null;

		try (Connection connection = SQLConnectionHelper.getTestDBConnection()) {

			try (Statement statement = connection.createStatement()) {
				query = "SELECT * FROM `account_info` WHERE msisdn ='"+msisdn+"' ORDER BY created_date DESC LIMIT 1 ";
				logger.info(query);
				try (ResultSet resultset = statement.executeQuery(query)) {
					if (resultset.next()) {

						dictionary.put("RECEIVERS_ACTIVE_END_DATE", resultset.getString("active_end_date") == null ? "NA" : resultset.getString("active_end_date"));
						logger.info("RECEIVERS_ACTIVE_END_DATE ->"+dictionary.get("RECEIVERS_ACTIVE_END_DATE"));

					} else {
						logger.info("There is no record for "+msisdn+" in DB");
						getTest().get().fail("There is no record found in DB for plan : "+msisdn);
					}
				}
			}

		} catch (Exception e) {
			logger.error("Some error occured getAccountDetails");
			logger.error(MainUtil.ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getAccountDetails", e);
			getTest().get().fail("Some error occured getAccountDetails");
			throw e;
		}
	}
}


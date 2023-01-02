package com.umtest.testframe.lib;

import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.parsing.Parser;
/*import io.restassured.path.json.JsonPath;*/
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

import static io.restassured.RestAssured.given;

public class APIRequestLibrary {

	private static Logger logger = LogManager.getLogger(APIRequestLibrary.class);


	public void postUnsubscribeGolife(String sMSISDN) throws Exception {

    	RestAssured.defaultParser = Parser.JSON;
		RestAssured.baseURI="http://10.70.40.203:80/api/rest";

		double dMsgID=Math.random()*(1-1000000000+1)+1;

		String sRequestBodyPayload="{\n"+
				" \"access_token\": \"NTJkNzYwMzIyZGFkMWY1NzE5ZWU5ZjljZjc3MjU3NWM=\",\n"+
				" \"method\": \"unsubscribeGoLife\",\n"+
				" \"version\": \"1.0\", \n"+
				" \"content\":{ \n"+
				" \"msgId\": \""+dMsgID+"\", \n"+
				" \"seqNo\": \"1\", \n"+
				" \"channel\": \"WEB_SELFCARE\", \n"+
				" \"msisdn\": \""+sMSISDN+"\"\n}}";

		String sResponseActual = given()
				           .header("Content-type", "application/json")
						   .and()
						   .body(sRequestBodyPayload)
				           .when()
				           .post("")
				.getBody().asString();

		String sResponseMessageSplitText="\"resultMsg\":";

		String[] sResponseActual1=sResponseActual.split(sResponseMessageSplitText);

		String[] sResponseMessage=sResponseActual1[1].split("}");

		System.out.println("[API] Unsubscribe GoLife : "+sResponseMessage[0]);

	}

	public void syncSubscriberBalanceAndExpiryDate(String sMSISDN) throws Exception {

		RestAssured.defaultParser = Parser.JSON;
//		RestAssured.baseURI="http://10.70.40.203:80/api/rest?msisdn="+sMSISDN;
//		RestAssured.baseURI="http://10.97.166.203/api/rest?msisdn="+sMSISDN;
		RestAssured.baseURI="http://10.97.217.121/api/rest?msisdn="+sMSISDN;

		double dMsgID=Math.random()*(1-1000000000+1)+1;

		/*" \"access_token\": \"MGM3MTc4MWVkYjRjZmQxOWM2N2JlNzg4YWVkMWVjNmY=\",\n"+*/


		String sRequestBodyPayload="{\n"+
				" \"access_token\": \"NTMyMWNkYWRkNTU1MjgyZmI1YjA0YjQxMTY5OTQ5YmI=\",\n"+
				" \"method\": \"querySubscriberBalance\",\n"+
				" \"version\": \"1.0\", \n"+
				" \"content\":{ \n"+
				" \"msgId\": \""+dMsgID+"\", \n"+
				" \"channel\": \"WEB_SELFCARE\", \n"+
				" \"msisdn\": \""+sMSISDN+"\"\n}}";
//		String sRequestBodyPayload="{\n"+
//				" \"access_token\": \"N2FmMWZhZTE4OTdjOTkxZjhiY2UzZTAyZTllYWY3NmU=\",\n"+
//				" \"method\": \"querySubscriberBalance\",\n"+
//				" \"version\": \"1.0\", \n"+
//				" \"content\":{ \n"+
//				" \"msgId\": \""+dMsgID+"\", \n"+
//				" \"channel\": \"WEB_SELFCARE\", \n"+
//				" \"msisdn\": \""+sMSISDN+"\"\n}}";

		String sResponseActual = given()
				.header("Content-type", "application/json")
				.and()
				.body(sRequestBodyPayload)
				.when()
				.post("")
				.getBody()
				.asString();

		JSONParser parser = new JSONParser();
		JSONObject jsonResponse = (JSONObject) parser.parse(sResponseActual);

		String joResultMessage= jsonResponse.get("res_message").toString();

		if(joResultMessage.equalsIgnoreCase("Operation success")) {

			JSONObject joResult = (JSONObject) jsonResponse.get("result");
			JSONArray jaAccountBalance = (JSONArray) joResult.get("balDtoList");
			Object oBalanceType = ((JSONObject) jaAccountBalance.get(0)).get("acctResName");

			if (oBalanceType.toString().equalsIgnoreCase("Prepaid basic balance")) {
				String sExpiryDate = ((JSONObject) jaAccountBalance.get(0)).get("expDate").toString();
				String sBalance = ((JSONObject) jaAccountBalance.get(0)).get("balance").toString();
				String sFormattedExpiryDate;

				String[] aResponseACCT_EXPIRY_DATE=sExpiryDate.split(" ");

				String[] aResponseACCT_EXPIRY_DATE1=aResponseACCT_EXPIRY_DATE[0].split("-");

				String sYear=aResponseACCT_EXPIRY_DATE1[0];
				String sMonth=aResponseACCT_EXPIRY_DATE1[1];
				String sDay=aResponseACCT_EXPIRY_DATE1[2];

				sFormattedExpiryDate=sDay+"/"+sMonth+"/"+sYear;

				String last2Digits = sBalance.substring(sBalance.length() - 2, sBalance.length());
				String firstDigits = sBalance.substring(0, sBalance.length() - 2);
				sBalance = firstDigits + "." + last2Digits;

				/*System.out.println("Expiry Date: "+sFormattedExpiryDate);
				System.out.println("Balance: "+sBalance);*/

				MainUtil.dictionary.put("API_MSISDN",sMSISDN);
				MainUtil.dictionary.put("API_BALANCE",sBalance);
				MainUtil.dictionary.put("API_ACCT_EXPIRY_DATE",sFormattedExpiryDate);

				ApplicationUtil.getAccountDetails(sMSISDN);

				String dbMainBalance=MainUtil.dictionary.get("MAIN_BALANCE");
				String selfcareAPIMainBalance=MainUtil.dictionary.get("API_BALANCE");

				String dbExpiryDate=MainUtil.dictionary.get("ACTIVE_END_DATE");
				String selfcareAPIExpiryDate=MainUtil.dictionary.get("API_ACCT_EXPIRY_DATE");

				if (!dbMainBalance.equalsIgnoreCase(selfcareAPIMainBalance) || !dbExpiryDate.equalsIgnoreCase(selfcareAPIExpiryDate)) {
					ApplicationUtil.updateAccInfoBalanceAndActiveEndDate(sMSISDN,MainUtil.dictionary.get("API_BALANCE"),MainUtil.dictionary.get("API_ACCT_EXPIRY_DATE"));
					System.out.println("[TEST DATA SYNC] MSISDN '"+sMSISDN+"' balance/expiry date synced with zSmart and Automation DB ");
				}else
				{
					System.out.println("[TEST DATA SYNC] MSISDN '"+sMSISDN+"' balance/expiry date already in sync with zSmart and Automation DB ");
				}
			}
		}
	}

	public void getSubscriberBalanceAndExpiryDate(String sMSISDN) throws Exception {

		RestAssured.defaultParser = Parser.JSON;
//		RestAssured.baseURI="http://10.70.40.203:80/api/rest?msisdn="+sMSISDN;
//		RestAssured.baseURI="http://10.97.166.203/api/rest?msisdn="+sMSISDN;
		RestAssured.baseURI="http://10.97.217.121/api/rest?msisdn="+sMSISDN;

		double dMsgID=Math.random()*(1-1000000000+1)+1;

		MainUtil.dictionary.put("API_BALANCE","null");
		MainUtil.dictionary.put("API_ACCT_EXPIRY_DATE","null");

		//				" \"access_token\": \"MDRhMzY3OWFmZDFjZDQ1NGJmODI0MDJmMzA5N2I2NmQ=\",\n"+

		String sRequestBodyPayload="{\n"+
				" \"access_token\": \"NTMyMWNkYWRkNTU1MjgyZmI1YjA0YjQxMTY5OTQ5YmI=\",\n"+
				" \"method\": \"querySubscriberBalance\",\n"+
				" \"version\": \"1.0\", \n"+
				" \"content\":{ \n"+
				" \"msgId\": \""+dMsgID+"\", \n"+
				" \"channel\": \"WEB_SELFCARE\", \n"+
				" \"msisdn\": \""+sMSISDN+"\"\n}}";
//		String sRequestBodyPayload="{\n"+
//				" \"access_token\": \"N2FmMWZhZTE4OTdjOTkxZjhiY2UzZTAyZTllYWY3NmU=\",\n"+
//				" \"method\": \"querySubscriberBalance\",\n"+
//				" \"version\": \"1.0\", \n"+
//				" \"content\":{ \n"+
//				" \"msgId\": \""+dMsgID+"\", \n"+
//				" \"channel\": \"WEB_SELFCARE\", \n"+
//				" \"msisdn\": \""+sMSISDN+"\"\n}}";

		String sResponseActual = given()
				.header("Content-type", "application/json")
				.and()
				.body(sRequestBodyPayload)
				.when()
				.post("")
				.getBody()
				.asString();

		JSONParser parser = new JSONParser();
		JSONObject jsonResponse = (JSONObject) parser.parse(sResponseActual);

		String joResultMessage= jsonResponse.get("res_message").toString();

		if(joResultMessage.equalsIgnoreCase("Operation success")) {

			JSONObject joResult = (JSONObject) jsonResponse.get("result");
			JSONArray jaAccountBalance = (JSONArray) joResult.get("balDtoList");
			Object oBalanceType = ((JSONObject) jaAccountBalance.get(0)).get("acctResName");

			if (oBalanceType.toString().equalsIgnoreCase("Prepaid basic balance")) {
				String sExpiryDate = ((JSONObject) jaAccountBalance.get(0)).get("expDate").toString();
				String sBalance = ((JSONObject) jaAccountBalance.get(0)).get("balance").toString();
				String sFormattedExpiryDate;

				String[] aResponseACCT_EXPIRY_DATE=sExpiryDate.split(" ");

				String[] aResponseACCT_EXPIRY_DATE1=aResponseACCT_EXPIRY_DATE[0].split("-");

				String sYear=aResponseACCT_EXPIRY_DATE1[0];
				String sMonth=aResponseACCT_EXPIRY_DATE1[1];
				String sDay=aResponseACCT_EXPIRY_DATE1[2];

				sFormattedExpiryDate=sDay+"/"+sMonth+"/"+sYear;

				String last2Digits = sBalance.substring(sBalance.length() - 2, sBalance.length());
				String firstDigits = sBalance.substring(0, sBalance.length() - 2);
				sBalance = firstDigits + "." + last2Digits;

				MainUtil.dictionary.put("API_MSISDN",sMSISDN);
				MainUtil.dictionary.put("API_BALANCE",sBalance);
				MainUtil.dictionary.put("API_ACCT_EXPIRY_DATE",sFormattedExpiryDate);

				System.out.println("MSISDN:"+sMSISDN);
				System.out.println("Balance:"+sBalance);
				System.out.println("Active End Date:"+sFormattedExpiryDate);
			}
		}
	}

	public void syncSubscriberCreditShare(String sMSISDN) throws Exception {

		Boolean sCreditShareBalanceFound=false;

		RestAssured.defaultParser = Parser.JSON;
		RestAssured.baseURI="http://10.70.40.203:80/api/rest?msisdn="+sMSISDN;

		double dMsgID=Math.random()*(1-1000000000+1)+1;

		MainUtil.dictionary.put("API_CREDIT_SHARE_BALANCE","0.00");

		String sRequestBodyPayload="{\n"+
				" \"access_token\": \"MDRhMzY3OWFmZDFjZDQ1NGJmODI0MDJmMzA5N2I2NmQ=\",\n"+
				" \"method\": \"querySubscriberBalance\",\n"+
				" \"version\": \"1.0\", \n"+
				" \"content\":{ \n"+
				" \"msgId\": \""+dMsgID+"\", \n"+
				" \"channel\": \"WEB_SELFCARE\", \n"+
				" \"msisdn\": \""+sMSISDN+"\"\n}}";

		String sResponseActual = given()
				.header("Content-type", "application/json")
				.and()
				.body(sRequestBodyPayload)
				.when()
				.post("")
				.getBody()
				.asString();

		JSONParser parser = new JSONParser();
		JSONObject jsonResponse = (JSONObject) parser.parse(sResponseActual);

		String joResultMessage= jsonResponse.get("res_message").toString();

		ApplicationUtil.getCreditAmountAccInfoTable(sMSISDN);
		String dbCreditAmount=MainUtil.dictionary.get("CREDIT_AMOUNT");

		if(joResultMessage.equalsIgnoreCase("Operation success")) {

			JSONObject joResult = (JSONObject) jsonResponse.get("result");
			JSONArray jaAccountBalance = (JSONArray) joResult.get("balDtoList");

			for(int intVar1=0;intVar1<jaAccountBalance.size();intVar1++){

				Object oBalanceType = ((JSONObject) jaAccountBalance.get(intVar1)).get("acctResName");

				if(oBalanceType.toString().equalsIgnoreCase("CreditShare Balance")) {
					sCreditShareBalanceFound=true;
					String sCreditShareBalanceAPI = ((JSONObject) jaAccountBalance.get(0)).get("balance").toString();

					String sCreditShareBalance = ((JSONObject) jaAccountBalance.get(intVar1)).get("balance").toString();

					if(!sCreditShareBalance.equalsIgnoreCase("0")){
						String last2Digits = sCreditShareBalance.substring(sCreditShareBalance.length() - 2, sCreditShareBalance.length());
						String firstDigits = sCreditShareBalance.substring(0, sCreditShareBalance.length() - 2);
						sCreditShareBalanceAPI = firstDigits + "." + last2Digits;
					}else
					{
						sCreditShareBalanceAPI="0.00";
					}

					MainUtil.dictionary.put("API_MSISDN", sMSISDN);
					MainUtil.dictionary.put("API_CREDIT_SHARE_BALANCE", sCreditShareBalanceAPI);

					System.out.println("MSISDN:" + sMSISDN);
					System.out.println("Credit Share Balance:" + sCreditShareBalanceAPI);

					if (!dbCreditAmount.equalsIgnoreCase(sCreditShareBalanceAPI)) {
						ApplicationUtil.updateCreditAmountAccInfoTable(sMSISDN,MainUtil.dictionary.get("API_CREDIT_SHARE_BALANCE"));
						System.out.println("[TEST DATA SYNC] MSISDN '"+sMSISDN+"' Credit Share balance synced with zSmart and Automation DB ");
					}else
					{
						System.out.println("[TEST DATA SYNC] MSISDN '"+sMSISDN+"' Credit Share balance already synced with zSmart and Automation DB ");
					}
					break;
				}
			}

			if(!sCreditShareBalanceFound && !dbCreditAmount.equalsIgnoreCase("0.00")){
				ApplicationUtil.updateCreditAmountAccInfoTable(sMSISDN,MainUtil.dictionary.get("API_CREDIT_SHARE_BALANCE"));
				System.out.println("Credit Share Balance: 0.00");
				System.out.println("[TEST DATA SYNC] MSISDN '"+sMSISDN+"' Credit Share balance synced with zSmart and Automation DB ");
			}
		}
	}


	public void unsubscribePrepaidOffer(String sMSISDN) throws Exception {

		String[] aExpectedOfferCodes= new String[] {"100115","100118","100120","100119","100111","100122","100126","100114","100127","100217","100205","100204","100203","100209","100210","100211","100212","100213","100206"};
		List<String> lExpectedOfferCodesList=new ArrayList<>(Arrays.asList(aExpectedOfferCodes));

		RestAssured.defaultParser = Parser.JSON;
		RestAssured.baseURI="http://10.70.40.203:80/api/rest?msisdn="+sMSISDN;

		double dMsgID=Math.random()*(1-1000000000+1)+1;

		String dMsgID1="034202110311605053363";

		MainUtil.dictionary.put("API_BALANCE","null");
		MainUtil.dictionary.put("API_ACCT_EXPIRY_DATE","null");

		String sRequestBodyPayload="{\n"+
				" \"access_token\": \"NTMyMWNkYWRkNTU1MjgyZmI1YjA0YjQxMTY5OTQ5YmI=\",\n"+
				" \"method\": \"getSubscriberBaseInfo\",\n"+
				" \"version\": \"1.3\", \n"+
				" \"content\":{ \n"+
				" \"msgId\": \""+dMsgID1+"\", \n"+
				" \"channel\": \"MYUM_APP\", \n"+
				" \"msisdn\": \""+sMSISDN+"\"\n}}";

		String sResponseActual = given()
				.header("Content-type", "application/json")
				.and()
				.body(sRequestBodyPayload)
				.when()
				.post("")
				.getBody()
				.asString();

		JSONParser parser = new JSONParser();
		JSONObject jsonResponse = (JSONObject) parser.parse(sResponseActual);

		String joResultMessage= jsonResponse.get("res_message").toString();

		if(joResultMessage.equalsIgnoreCase("Operation success")) {

			JSONObject joResult = (JSONObject) jsonResponse.get("result");
			JSONArray jaOffersList = (JSONArray) joResult.get("offers");

			for(int iVar1=0;iVar1<jaOffersList.size();iVar1++){

				String sOfferCode = ((JSONObject) jaOffersList.get(iVar1)).get("offerCode").toString();

				if(lExpectedOfferCodesList.contains(sOfferCode)){

					String sRequestBodyPayloadUnsubscribeOffer="{\n"+
							" \"access_token\": \"NTMyMWNkYWRkNTU1MjgyZmI1YjA0YjQxMTY5OTQ5YmI=\",\n"+
							" \"method\": \"setVass\",\n"+
							" \"version\": \"1.0\", \n"+
							" \"content\":{ \n"+
							" \"msgId\": \""+dMsgID+"\", \n"+
							" \"channel\": \"UMB\", \n"+
							" \"msisdn\": \""+sMSISDN+"\", \n" +
							" \"vass\":[{ \n" +
							" \"offerCode\": \""+sOfferCode+"\",\n"+
							" \"operationType\": \"D\", \n" +
							" \"effType\": \"\", \n" +
							" \"effDate\": \"\", \n" +
							" \"expDate\": \"\" \n}]}}";

					String sResponseUnsubscription = given()
							.header("Content-type", "application/json")
							.and()
							.body(sRequestBodyPayloadUnsubscribeOffer)
							.when()
							.post("")
							.getBody()
							.asString();

					System.out.println("[API Response] Unsubscribe Bundle:\n"+sResponseUnsubscription);

				}

			}

		}
	}

	public void unsubscribePrepaidOffer_bkp(String sMSISDN) throws Exception {

		String[] aExpectedOfferCodes= new String[] {"100115","100118","100120","100119","100111","100122","100126","100114","100127","100217","100205","100204","100203","100209","100210","100211","100212","100213","100206"};
		List<String> lExpectedOfferCodesList=new ArrayList<>(Arrays.asList(aExpectedOfferCodes));

		RestAssured.defaultParser = Parser.JSON;
		RestAssured.baseURI="http://10.70.40.203:80/api/rest?msisdn="+sMSISDN;

		double dMsgID=Math.random()*(1-1000000000+1)+1;

		String dMsgID1="034202110311605053363";

		MainUtil.dictionary.put("API_BALANCE","null");
		MainUtil.dictionary.put("API_ACCT_EXPIRY_DATE","null");

		String sRequestBodyPayload="{\n"+
				" \"access_token\": \"ZjQ5YzBhMzE5NWRmODlkZjdkZWI1YTFkYWY0MjdlMjA=\",\n"+
				" \"method\": \"getSubscriberBaseInfo\",\n"+
				" \"version\": \"1.3\", \n"+
				" \"content\":{ \n"+
				" \"msgId\": \""+dMsgID1+"\", \n"+
				" \"channel\": \"MYUM_APP\", \n"+
				" \"msisdn\": \""+sMSISDN+"\"\n}}";

		String sResponseActual = given()
				.header("Content-type", "application/json")
				.and()
				.body(sRequestBodyPayload)
				.when()
				.post("")
				.getBody()
				.asString();

		JSONParser parser = new JSONParser();
		JSONObject jsonResponse = (JSONObject) parser.parse(sResponseActual);

		String joResultMessage= jsonResponse.get("res_message").toString();

		if(joResultMessage.equalsIgnoreCase("Operation success")) {

			JSONObject joResult = (JSONObject) jsonResponse.get("result");
			JSONArray jaOffersList = (JSONArray) joResult.get("offers");

			for(int iVar1=0;iVar1<jaOffersList.size();iVar1++){

				String sOfferCode = ((JSONObject) jaOffersList.get(iVar1)).get("offerCode").toString();

				if(lExpectedOfferCodesList.contains(sOfferCode)){

					String sRequestBodyPayloadUnsubscribeOffer="{\n"+
							" \"access_token\": \"YzE4MDZlMDBmMTliYjIxODc3MmE4NzZmZWJjNTM1YTM=\",\n"+
							" \"method\": \"setVass\",\n"+
							" \"version\": \"1.0\", \n"+
							" \"content\":{ \n"+
							" \"msgId\": \""+dMsgID+"\", \n"+
							" \"channel\": \"UMB\", \n"+
							" \"msisdn\": \""+sMSISDN+"\", \n" +
							" \"vass\":[{ \n" +
							" \"offerCode\": \""+sOfferCode+"\",\n"+
							" \"operationType\": \"D\", \n" +
							" \"effType\": \"\", \n" +
							" \"effDate\": \"\", \n" +
							" \"expDate\": \"\" \n}]}}";

					String sResponseUnsubscription = given()
							.header("Content-type", "application/json")
							.and()
							.body(sRequestBodyPayloadUnsubscribeOffer)
							.when()
							.post("")
							.getBody()
							.asString();

					System.out.println("[API Response] Unsubscribe Bundle:\n"+sResponseUnsubscription);

				}

			}

		}
	}

	public static  String removeDoubleQuotes( String request) {
		return request.replace("\"", "");
	}

	public String getAPIResponseXMLParamValue(String sFullString,String sParam){

		String[] aSubString1=sFullString.split(sParam);
		String[] aSubString2=aSubString1[1].split("value=");
		String[] aSubString3;

		if(sParam.equalsIgnoreCase("BALANCE") || sParam.equalsIgnoreCase("ACCT_EXPIRY_DATE")){
			aSubString3=aSubString2[1].split("fid=");
		}else
		{
			aSubString3=aSubString2[1].split("/><para name=");
		}

		String aSubString4=removeDoubleQuotes(aSubString3[0].trim());

		return aSubString4;

	}

	public void syncTestDataInfoWithCRM(String sMSISDN) throws Exception {

		System.out.println("Sync Test Data with CRM (MSISDN - "+sMSISDN+")");
		String sResponseRATEPLANNAME="";
		String sResponseBALANCE="";
		String sResponseACCT_EXPIRY_DATE="";
		String sFormattedExpiryDate="";

		Thread.sleep(1000);

		RestAssured.defaultParser = Parser.XML;

		String sResponse = given().when().get("http://10.67.32.158:9000/ussd/root/get_subr_base_info?MSGID=1&SEQNO=1&MSISDN="+sMSISDN+"&CHANNEL=ussd").getBody().asString();

		String sResponseLIFECYCLESTATUS=getAPIResponseXMLParamValue(sResponse,"LIFE_CYCLE_STATUS");

		if(sResponseLIFECYCLESTATUS.equalsIgnoreCase("DEACTIVATED")){
			sResponseRATEPLANNAME="";
			sResponseBALANCE="";
			sResponseACCT_EXPIRY_DATE="";
		}else {
			sResponseRATEPLANNAME = getAPIResponseXMLParamValue(sResponse, "RATEPLAN_NAME");
			sResponseBALANCE = getAPIResponseXMLParamValue(sResponse, "BALANCE");
			sResponseACCT_EXPIRY_DATE = getAPIResponseXMLParamValue(sResponse, "ACCT_EXPIRY_DATE");

			String[] aResponseACCT_EXPIRY_DATE=sResponseACCT_EXPIRY_DATE.split("T");

			String sYear=aResponseACCT_EXPIRY_DATE[0].substring(0,4);
			String sMonth=aResponseACCT_EXPIRY_DATE[0].substring(4,6);
			String sDay=aResponseACCT_EXPIRY_DATE[0].substring(6,8);

			sFormattedExpiryDate=sDay+"/"+sMonth+"/"+sYear;

			if(sResponseBALANCE.equalsIgnoreCase("0")){
				sResponseBALANCE="0.00";
			}else {
				String last2Digits = sResponseBALANCE.substring(sResponseBALANCE.length() - 2, sResponseBALANCE.length());
				String firstDigits = sResponseBALANCE.substring(0, sResponseBALANCE.length() - 2);
				sResponseBALANCE = firstDigits + "." + last2Digits;
			}
		}

		ApplicationUtil.getAccountDetails(sMSISDN);

		String dbMainBalance=MainUtil.dictionary.get("MAIN_BALANCE");
		String blcAPIMainBalance=MainUtil.dictionary.get("API_BALANCE");

		String dbExpiryDate=MainUtil.dictionary.get("ACTIVE_END_DATE");
		String blcAPIExpiryDate=MainUtil.dictionary.get("API_ACCT_EXPIRY_DATE");

		System.out.println("*******************************************************");

		System.out.println("[CRM] RATE_PLAN_NAME:: "+sResponseRATEPLANNAME);
		System.out.println("[CRM] LIFE_CYCLE_STATUS :: "+sResponseLIFECYCLESTATUS);
		System.out.println("[CRM] BALANCE :: "+sResponseBALANCE);
		System.out.println("[AUTOMATION DB] BALANCE :: "+dbMainBalance);

		System.out.println("[CRM] ACCT_EXPIRY_DATE :: "+sFormattedExpiryDate);
		System.out.println("[AUTOMATION DB] ACCT_EXPIRY_DATE :: "+dbExpiryDate);

		System.out.println("*******************************************************");

		MainUtil.dictionary.put("API_MSISDN",sMSISDN);
		MainUtil.dictionary.put("API_RATE_PLAN_NAME",sResponseRATEPLANNAME);
		MainUtil.dictionary.put("API_LIFE_CYCLE_STATUS",sResponseLIFECYCLESTATUS);
		MainUtil.dictionary.put("API_BALANCE",sResponseBALANCE);
		MainUtil.dictionary.put("API_ACCT_EXPIRY_DATE",sFormattedExpiryDate);

		if(sResponseLIFECYCLESTATUS.equalsIgnoreCase("ACTIVE")) {

			/*	String dbMainBalance=MainUtil.dictionary.get("MAIN_BALANCE");
				String blcAPIMainBalance=MainUtil.dictionary.get("API_BALANCE");

				String dbExpiryDate=MainUtil.dictionary.get("ACTIVE_END_DATE");
				String blcAPIExpiryDate=MainUtil.dictionary.get("API_ACCT_EXPIRY_DATE");*/

			if (!dbMainBalance.equalsIgnoreCase(blcAPIMainBalance) || !dbExpiryDate.equalsIgnoreCase(blcAPIExpiryDate)) {
				ApplicationUtil.updateAccInfoBalanceAndActiveEndDate(sMSISDN,MainUtil.dictionary.get("API_BALANCE"),MainUtil.dictionary.get("API_ACCT_EXPIRY_DATE"));

				System.out.println("MSISDN '"+sMSISDN+"' details synced with CRM ");
				System.out.println("*******************************************************");
			}
			else
			{
				System.out.println("MSISDN '"+sMSISDN+"' details already synced with CRM");
				System.out.println("*******************************************************");
			}
		}
	}


}

package com.umtest.testframe.lib;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zaxxer.hikari.HikariDataSource;

public class SQLConnectionHelper {
	private static Logger logger = LogManager.getLogger(SQLConnectionHelper.class);
	private static String testDBIP;
	private static String testDBPort;
	private static String testDBSchema;
	private static String testDBUsername;
	private static String testDBPassword;
	
	private static String selfcareDBIP;
	private static String selfcareDBPort;
	private static String selfcareDBSchema;
	private static String selfcareDBUsername;
	private static String selfcareDBPassword;
	
	private static String crmDBIP;
	private static String crmDBPort;
	private static String crmDBSchema;
	private static String crmDBUsername;
	private static String crmDBPassword;

	private static HikariDataSource testDBSource;
	private static HikariDataSource crmDBSource;
	private static HikariDataSource selfcareDBSource;

	private static String BSCSDBUsername;
	private static String BSCSDBPassword;
	private static String BSCSDBSource;


	private SQLConnectionHelper() {
	}

	public static Connection getTestDBConnection_onprem() throws Exception
	{
		testDBIP = PropertyHelper.getProperties("TEST_DB_IP");
		testDBPort = PropertyHelper.getProperties("TEST_DB_PORT");
		testDBSchema = PropertyHelper.getProperties("TEST_DB_UM_SCHEMA");
		testDBUsername = PropertyHelper.getProperties("TEST_DB_USERNAME");
		testDBPassword = PropertyHelper.getProperties("TEST_DB_PASSWORD");

		String DB_URL = "jdbc:mysql://"+testDBIP+":"+testDBPort+"/"+testDBSchema+"?useSSL=false&allowPublicKeyRetrieval=true&useTimezone=true&serverTimezone=UTC";
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

		Connection con = DriverManager.getConnection(DB_URL, testDBUsername, testDBPassword);
		return con;
	}



	public static Connection getTestDBConnection() throws Exception
	{
		return getTestDBConnection_onprem();
	}

	public static Connection getSelfcareDBConnection() throws Exception
	{
		selfcareDBIP = resolveSelfcareDBIP();
		//	selfcareDBIP = PropertyHelper.getENVProperties("SELFCARE_DB_IP");
		selfcareDBPort = PropertyHelper.getENVProperties("SELFCARE_DB_PORT");
		selfcareDBSchema = PropertyHelper.getENVProperties("SELFCARE_DB_UM_SCHEMA");
		selfcareDBUsername = PropertyHelper.getENVProperties("SELFCARE_DB_USERNAME");
		selfcareDBPassword = PropertyHelper.getENVProperties("SELFCARE_DB_PASSWORD");

		String DB_URL = "jdbc:mysql://"+selfcareDBIP+":"+selfcareDBPort+"/"+selfcareDBSchema+"?useTimezone=true&serverTimezone=UTC";
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

		Connection con = DriverManager.getConnection(DB_URL, selfcareDBUsername, selfcareDBPassword);
		return con;

	}
	
	public static Connection getCRMConnection() throws Exception
	{
		crmDBIP = PropertyHelper.getENVProperties("CRM_DB_IP");
		crmDBPort = PropertyHelper.getENVProperties("CRM_DB_PORT");
		crmDBSchema = PropertyHelper.getENVProperties("CRM_DB_UM_SCHEMA");
		crmDBUsername = PropertyHelper.getENVProperties("CRM_DB_USERNAME");
		crmDBPassword = PropertyHelper.getENVProperties("CRM_DB_PASSWORD");

		String DB_URL = "jdbc:mysql://"+crmDBIP+":"+crmDBPort+"/"+crmDBSchema+"?useTimezone=true&serverTimezone=UTC";
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

		Connection con = DriverManager.getConnection(DB_URL, crmDBUsername, crmDBPassword);
		return con;

	}

	private void closeTestDBConnection(Connection con) throws Exception
	{
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public static Connection getDBConnection(String dbname) {
		try {
			switch (dbname) {
			case "CRM":
				return crmDBSource.getConnection();
			case "SELFCARE":
				return selfcareDBSource.getConnection();
			default:
				throw new IllegalStateException("Unexpected value: " + dbname);
			}
		} catch (SQLException e) {
			logger.error("Error while accessing the connection\n", e);
		}
		return null;
	}

	public static String getBSCSDBConnection() throws Exception
	{
		String rev = new String("");
		BSCSDBUsername = PropertyHelper.getProperties("BSCS_DB_USERNAME");
		BSCSDBPassword = PropertyHelper.getProperties("BSCS_DB_PASSWORD");
		BSCSDBSource = PropertyHelper.getProperties("BSCS_DB_DATASOURCE");

		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.61.1.119:1521/BSCST1",BSCSDBUsername,BSCSDBPassword);
		Statement stmt = con.createStatement();
		String Query = "Select Billcycle from billcycle_assignment_history where customer_id = (select customer_id from contract_all where co_id = (select CO_ID from Contr_services_cap where dn_id =(Select DN_ID from DIRECTORY_NUMBER where DN_NUM ='"+MainUtil.dictionary.get("MSISDN")+"') and CS_ACTIV_DATE = (Select max(CS_ACTIV_DATE) from Contr_services_cap where dn_id =(Select DN_ID from DIRECTORY_NUMBER where DN_NUM ='"+MainUtil.dictionary.get("MSISDN")+"'))))";
		ResultSet rs=stmt.executeQuery(Query);
		while(rs.next())
		{
			System.out.println(rs.getString("BILLCYCLE"));
			rev= rs.getString("BILLCYCLE");

		}
		return rev;
	}

	public static void closeDBConnPool() {
		{
			logger.info("Closing the DB Connection Pool");
			logger.info("Closing Test DB Source");
			//testDBSource.close();
		}
	}

	public static String resolveSelfcareDBIP() throws IOException {
		if(sendPingRequest("10.67.36.90")){
			//System.out.println("***Note: Selfcare IP - 10.67.36.90");
			return "10.67.36.90";
		}else {
		//	System.out.println("***Note: Selfcare IP - 10.70.26.101");
			return "10.70.26.101";
		}
	}

	// Sends ping request to a provided IP address
	public static boolean sendPingRequest(String ipAddress)
			throws IOException, IOException, UnknownHostException {
		InetAddress ipObject = InetAddress.getByName(ipAddress);
		if (ipObject.isReachable(5000)) {
			return true;
		} else {
			return false;
		}
	}




}
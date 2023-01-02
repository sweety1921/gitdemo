package com.umtest.testframe.lib;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PropertyHelper {
	private static Properties configprop = null;
	private static Properties envprop = null;
	private static Logger logger = LogManager.getLogger(PropertyHelper.class);


	public static String getProperties(String propertyName) {
		logger.info("Get the properties from properties files");
		if (configprop == null) {
			try {
				InputStream in = PropertyHelper.class.getClassLoader().getResourceAsStream("config.properties");
				configprop = new Properties();
				configprop.load(in);
				configprop.getProperty(propertyName);
				logger.info("propertyName: {}", propertyName);
				logger.info("configprop.getProperty(propertyName): {}", configprop.getProperty(propertyName));
			} catch (IOException io) {
				logger.info("Error while creating the config file", io);
			}
		}
		return configprop.getProperty(propertyName);
	}


	public static String getENVProperties(String propertyName) {
		if (envprop == null) {
			try {
				envprop = new Properties();
				InputStream in = PropertyHelper.class.getClassLoader()
						.getResourceAsStream(System.getProperty("env").toLowerCase() + ".properties");
				envprop.load(in);
				envprop.getProperty(propertyName);
			} catch (IOException io) {
				logger.info("Error while creating the env file", io);
			}
		}
		return envprop.getProperty(propertyName);
	}


	/**
	 * This method is used to get the plan details from the DB
	 *
	 * @param parameterName
	 *            Pass the plan parameter name
	 * @return a string
	 * @throws Exception
	 *             Will throw exception
	 */
	public static String getPlanDetails(String parameterName) throws Exception {
		String dataValue = null;
		try (Connection con = SQLConnectionHelper.getTestDBConnection()) {
			logger.info("Get Plan Details from DB");
			String query;
			query = "select parameter_value from auto_plan_details where parameter_name=? and auto_plan_name_id=(select id from auto_plan_names where plan_name=?)";

			try (PreparedStatement stmt1 = con.prepareStatement(query)) {
				stmt1.setString(1, parameterName);

				if (MainUtil.APPLICATION_NAME.equalsIgnoreCase("YOS")) {
					stmt1.setString(2, MainUtil.dictionary.get("YOS_PRODUCT_NAME"));
				} else {
					stmt1.setString(2, MainUtil.dictionary.get("PLAN_NAME"));
				}
				logger.info(query);
				try (ResultSet rs1 = stmt1.executeQuery()) {
					while (rs1.next()) {
						dataValue = rs1.getString(1);
					}
				}
			}
		} catch (SQLException e) {
			logger.error("SQL Exception: ", e);
		}

		logger.info("data value {} {}", MainUtil.ProjectConst.VALUE.getMsg(), dataValue);
		return dataValue;
	}

	/**
	 * Method to get value from specific db on specific condition
	 *
	 * @param dbName
	 * @param tableName
	 * @param columnName
	 * @param whereClause
	 * @return
	 * @throws Exception
	 */
	public static String getDataFromDB(String dbName, String tableName, String columnName, String whereClause)
			throws Exception {
		String dataValue = null;
//		SQLConnectionHelper.closeConnection();
		try (Connection con = SQLConnectionHelper.getDBConnection(dbName)) {
			logger.info("Get Plan Details from DB");
			try (Statement stmt1 = con.createStatement()) {
				String query;
				query = "select " + columnName + " from " + tableName + " where " + whereClause + " ";
				logger.info(query);
				try (ResultSet rs1 = stmt1.executeQuery(query)) {
					while (rs1.next()) {
						dataValue = rs1.getString(1);
					}
				}
			}
		} catch (SQLException e) {
			logger.error("SQL Exception: ", e);
		}
		logger.info("data value {} {}", MainUtil.ProjectConst.VALUE.getMsg(), dataValue);
		return dataValue;
	}
}

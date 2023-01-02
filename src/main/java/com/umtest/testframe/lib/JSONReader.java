package com.umtest.testframe.lib;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public class JSONReader {

	private static Logger logger = LogManager.getLogger(JSONReader.class);

	public static JSONObject parseJSONFile(String sFileNameWithPath) throws IOException, ParseException {
		Object objJSON = null;
		JSONObject jsonobj=null;

		JSONParser jsonParser = new JSONParser();

		try (FileReader jsonFileReader = new FileReader(sFileNameWithPath)) {
			objJSON = jsonParser.parse(jsonFileReader);
			jsonobj=(JSONObject) objJSON;
			return jsonobj;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonobj;
	}

	public static void main(String[] args) throws IOException, ParseException {



		JSONObject fileGX38=parseJSONFile("src\\test\\resources\\testData\\bundlePurchase\\unlimitedFunz\\GX38.json");

		getJSONServicesCategories(fileGX38);


	}


	public static void getJSONServicesCategories(JSONObject jsonObj){

		JSONArray jaServiceCategories= (JSONArray) jsonObj.get("serviceCategory");

		for(int intVar1=0; intVar1<jaServiceCategories.size();intVar1++){

			JSONObject joServiceCategory=(JSONObject)jaServiceCategories.get(intVar1);

			String sServiceCategoryType=joServiceCategory.get("type").toString();

			JSONArray jaServices= (JSONArray) joServiceCategory.get("services");

				JSONObject joServiceItems;
				String sServiceName;
				String sServiceQuota;
				String sServiceUsage;
				String sServiceValidity;

				for(int intVar2=0; intVar2<jaServices.size();intVar2++){

					 joServiceItems=(JSONObject)jaServices.get(intVar2);

					 sServiceName=joServiceItems.get("serviceName").toString();
					 sServiceQuota=joServiceItems.get("quota").toString();
					 sServiceUsage=joServiceItems.get("usage").toString();
					 sServiceValidity=joServiceItems.get("validity").toString();

					System.out.println("Service CategoryType :: "+sServiceCategoryType+" --> "+sServiceName);
					System.out.println("Service CategoryType :: "+sServiceCategoryType+" --> "+sServiceQuota);
					System.out.println("Service CategoryType :: "+sServiceCategoryType+" --> "+sServiceUsage);
					System.out.println("Service CategoryType :: "+sServiceCategoryType+" --> "+sServiceValidity);

					System.out.println("################################################################################");
				}
		}

	}


	public static JSONObject loadJSONTestDataFile(String sJSONTestDataFileNameWithPath) throws IOException, ParseException {
		return parseJSONFile(sJSONTestDataFileNameWithPath);
	}
}
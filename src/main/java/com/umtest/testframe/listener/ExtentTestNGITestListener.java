package com.umtest.testframe.listener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.testframe.reports.ExtentManager;

public class ExtentTestNGITestListener extends TestListenerAdapter {
	private static String dateName = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
	public static String USER_DIR = System.getProperty("user.dir");
	public static String table_id = System.getProperty("table_id");
	private static String reportDirectoryName = "reports/extentreport_" + dateName;
	private static ExtentReports extent;
	private static Logger logger = LogManager.getLogger(ExtentTestNGITestListener.class);
	public static String htmlpassLocation;
	public static String htmlfailLocation;
	public static String htmlreportname;

	static String reportL;

	public static void getExtent() {
		
		if (extent != null) {
			extent.flush();
		}
		
		//extent.flush();
		reportL = "R";

		logger.info("USER DIRECTORY ISS----> {}", USER_DIR);
		htmlpassLocation = "./images/Pass";
		htmlfailLocation = "./images/Fail";
		htmlreportname = reportDirectoryName + "/" + PropertyHelper.getProperties("REPORTNAME") + "_" + dateName + ".html";
		logger.info("{}/{}", USER_DIR, htmlreportname);
		extent = ExtentManager.createInstance(USER_DIR + "/" + htmlreportname);
		extent.flush();
}

	
private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<>();
private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

public static File passLocation = new File("./" + reportDirectoryName + "/images/Pass");
public static File failLocation = new File("./" + reportDirectoryName + "/images/Fail");

public ExtentTestNGITestListener() {
	if (!failLocation.exists()) {
		failLocation.mkdirs();
	}
	if (!passLocation.exists()) {
		passLocation.mkdirs();
	}
}

@Override
public synchronized void onStart(ITestContext context) {
    ExtentTest parent = extent.createTest(context.getName());
    parentTest.set(parent);

}

@Override
public synchronized void onFinish(ITestContext context) {
	extent.flush();
}

@Override
public synchronized void onTestStart(ITestResult result) {

	/*ExtentTest child = parentTest.get().createNode(result.getMethod().getMethodName() + "-" + result.getMethod().getDescription());
	MainUtil.dictionary.put("EXTENT_CHILD", child);*/

}

// Pass type: applcation|description|jiraid
public static synchronized void createNode(String type) {
	
	ExtentTest child = parentTest.get().createNode(type);
	test.set(child);
}


@Override
public synchronized void onTestFailure(ITestResult result) {
	getTest().get().fail("" + result.getName());

}

@Override
public synchronized void onTestSkipped(ITestResult result) {
	System.out.println("The name of the testcase Skipped is :" + result.getName());
	getTest().get().skip("" + result.getName());
}


public static ThreadLocal<ExtentTest> getParentTest() {
	return parentTest;
}

public static ThreadLocal<ExtentTest> getTest() {
	extent.flush();
	return test;
}


public static void putTestPassMessage(String sMessage){
	getTest().get().pass("<b>"+sMessage);

}

}

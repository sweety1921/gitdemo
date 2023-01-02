package com.umtest.testframe.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.umtest.testframe.lib.PropertyHelper;

public class ExtentManager {
    private static ExtentReports extent;
    /**
     * Returns instance of extent reports
     *
     * @param fileName Enter the Report or file name
     * @return Returns the ExtentReports object
     */
    public static ExtentReports createInstance(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);

        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setReportName("Test Automation Report");
        htmlReporter.config().setDocumentTitle(PropertyHelper.getProperties("REPORTNAME"));
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setProtocol(Protocol.HTTPS);
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setTimeStampFormat("MM/dd/yyyy hh:mm:ss a");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        return extent;
    }
}

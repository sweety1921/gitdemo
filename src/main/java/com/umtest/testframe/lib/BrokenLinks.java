package com.umtest.testframe.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.umtest.testframe.listener.ExtentTestNGITestListener.getTest;

import java.net.*;
import java.util.List;

public class BrokenLinks {

    private static HttpURLConnection httpURLConnect;
    private static Logger logger = LogManager.getLogger(BrokenLinks.class);

    public static List<WebElement> verifyLinks(String url, RemoteWebDriver driver) {
        driver.get(url);
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (WebElement webElement: links)
        {
            String urlForVerification = webElement.getAttribute("href");
            verifyLinkActive(urlForVerification);
        }
		return links;
    }

    private static void verifyLinkActive(String linkUrl) {
        try {
            System.setProperty("jsse.enableSNIExtension", "false");
            URL url = new URL(linkUrl);
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            httpURLConnect = (HttpURLConnection) url.openConnection();
            httpURLConnect.setConnectTimeout(10000);
            httpURLConnect.connect();
            if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_OK) {
                logger.info("URL: {} Status code: {}", linkUrl, httpURLConnect.getResponseCode());
                getTest().get().pass(linkUrl + " loaded successfully");
            }
        } catch (Exception e) {
            getTest().get().fail(linkUrl + " load unsuccessful");
            logger.error("URL: " + linkUrl);
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}



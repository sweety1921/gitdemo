package com.umtest.testframe.base;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServer {

	private AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;
	private DesiredCapabilities cap;

	public void startServer() {
		//Set Capabilities
		/*cap = new DesiredCapabilities();
		cap.setCapability("noReset", "false");

		//builder.withIPAddress("127.0.0.1");
		builder.usingPort(4651);
		builder.withCapabilities(cap);
		builder.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"));
		builder.withAppiumJS(new File(""));
		builder.withArgument(GeneralServerFlag.RELAXED_SECURITY);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
		service = AppiumDriverLocalService.buildService(builder);*/
		service = AppiumDriverLocalService.buildDefaultService();
		service.start();
		MainUtil.dictionary.put("APPIUM_URL", service.getUrl().toString());
		System.out.println("Appium URL : "+MainUtil.dictionary.get("APPIUM_URL"));
	}

	public void stopServer() {
		service.stop();
	}

	public boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			//If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}	

	public static void main(String[] args) {
		AppiumServer appiumServer = new AppiumServer();

		int port = 4723;
		if(!appiumServer.checkIfServerIsRunnning(port)) {
			appiumServer.startServer();
			appiumServer.stopServer();
		} else {
			System.out.println("Appium Server already running on Port - " + port);
		}
	}
}
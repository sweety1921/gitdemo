package com.umtest.testframe.lib;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class PropReader {

	private static Logger logger = LogManager.getLogger(PropReader.class);
	private static Properties getApplicationProp;

	/**
	 * Method to read property file
	 * 
	 * @param path Pass the path of the property file
	 * @return properties
	 */
	public static synchronized Properties readPropertyFile(Path path) {
		File file = new File(path.toString());
		if (FilenameUtils.getExtension(file.getName()).equals("properties")) {
			getApplicationProp = new Properties();
			try (FileInputStream fis = new FileInputStream(file)) {
				getApplicationProp.load(fis);
			} catch (IOException e) {
				logger.info(e);
			}
		}
		return getApplicationProp;
	}
}

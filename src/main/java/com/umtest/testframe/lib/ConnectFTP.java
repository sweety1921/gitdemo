package com.umtest.testframe.lib;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Paths;

public class ConnectFTP {

    private static Logger logger = LogManager.getLogger(ConnectFTP.class);

    public static void downloadFileFromFTP(String userName, String password, String fileName, String host, int port) {
        FTPClient client = new FTPClient();
        try {
            logger.info("Connecting to FTP Server");
            client.connect(host, port);
            logger.info("Logging to FTP Server");
            boolean login = client.login(userName, password);
            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);
            if (login) {
                logger.info("Connection established...");
                File apk = new File(Paths.get(System.getProperty("user.dir"), "src", "main", "resources", fileName).toString());
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(apk));
                boolean success = client.retrieveFile(fileName, outputStream);
                outputStream.close();
                if (success) {
                    logger.info("APK has been downloaded successfully.");
                }
                boolean logout = client.logout();
                if (logout) {
                    logger.info("Connection close...");
                }
            } else {
                logger.info("Connection fail...");
            }
        } catch (IOException e) {
            logger.error(e);

        } finally {
            try {
                client.disconnect();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }
}

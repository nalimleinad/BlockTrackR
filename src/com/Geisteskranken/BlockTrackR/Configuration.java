package com.Geisteskranken.BlockTrackR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;

public class Configuration {

    static Properties prop = new Properties();
    static OutputStream output = null;
    static File Dir = new File("plugins\\BlockTrackR");
    static String ConfDir = "plugins\\BlockTrackR\\BlockTrackR.conf";

    public static boolean readConfig() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            try {
            	if(!Dir.exists()){
            		Dir.mkdir();
            	}
                input = new FileInputStream(ConfDir);
            } catch (FileNotFoundException e) {
                createConfig();
                return false;
            }

            prop.load(input);

            BlockTrackR.host = prop.getProperty("host");
            BlockTrackR.database = prop.getProperty("database");
            BlockTrackR.dbuser = prop.getProperty("dbuser");
            BlockTrackR.dbpass = prop.getProperty("dbpass");
        } catch (IOException ex) {
        	BlockTrackR.logger.log(Level.WARNING, "Disabled! Configuration error.", ex);
        }
        try {
            input.close();
        } catch (IOException e) {
            BlockTrackR.logger.log(Level.WARNING, "Disabled! Configuration error.", e);
            return false;
        }
        BlockTrackR.logger.info("Config: OK");
        return true;
    }

    public static void createConfig() {
        try {

            output = new FileOutputStream(ConfDir);

            prop.setProperty("host", "localhost");
            prop.setProperty("database", "minecraft");
            prop.setProperty("dbuser", "db_username");
            prop.setProperty("dbpass", "db_pasword");

            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                    BlockTrackR.logger.log(Level.WARNING, "Configuration file created. Please edit and restart server");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}

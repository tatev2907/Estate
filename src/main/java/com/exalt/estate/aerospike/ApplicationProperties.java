package com.exalt.estate.aerospike;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * The ApplicationProperties is a generic class that enables getting properties from config.properties  file
 */
public class ApplicationProperties {

    private final Properties properties;

    ApplicationProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

        } catch (IOException ioex) {
            Logger.getLogger(getClass().getName()).log(Level.ALL, "IOException Occured while loading properties file::::" +ioex.getMessage());
        }
    }

    public String readProperty(String keyName) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Reading Property " + keyName);
        return properties.getProperty(keyName, "There is no key in the properties file");
    }
}

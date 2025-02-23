package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesManager {

    protected final static String projectPath = System.getProperty("user.dir");
    protected final static String propertiesPath = projectPath + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "test.properties";
    protected final static Logger logger = LogManager.getLogger(PropertiesManager.class);
    protected static Properties properties= new Properties();

    public PropertiesManager init() {
        logger.info("Reading properties from file: " + propertiesPath);
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(propertiesPath))) {
            properties.load(reader);
            logger.info("Properties file is successfully read.");
        } catch (Exception e) {
            logger.warn(e.getMessage());
            logger.warn("Properties file not found!");
        }
        return this;
    }

    public String getProperty(String parmName) {
        return properties.getProperty(parmName);
    }

    public void setProperty(String parmName, String parmValue) {
        properties.setProperty(parmName, parmValue);
    }
}

package configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private Properties prop;
    private InputStream input;
    private String filePath;
    private String runningURL;

    public Configuration(String filePath){
        this.filePath = filePath;
    }

    public String getProperty(String propertyName) throws IOException {
        String result = "";
        try {
            prop = new Properties();
            input = new FileInputStream(this.filePath);

            if (input != null) {
                prop.load(input);
            } else {
                throw new FileNotFoundException("property file '" + filePath + "' not found in the classpath");
            }
            result = prop.getProperty(propertyName);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            input.close();
        }

        return result;
    }
    public String getUrl() throws IOException {
        switch(this.getProperty("country")){
            case "singapore":
                this.runningURL = String.format("%s/en-SG", getProperty("url"));
                return this.runningURL;
            case "vietnam":
                this.runningURL = String.format("%s/vi-VN", getProperty("url"));;
                return this.runningURL;
            default:
                return "";
        }
    }
}

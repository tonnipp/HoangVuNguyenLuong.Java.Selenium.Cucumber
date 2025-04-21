package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;

public class DriverFactory {
    public static WebDriver getDriver(String browser) throws IOException {
        switch (browser) {
            case "edge":
                // Driver MS Edge for Windows only
//                System.setProperty("webdriver.msedge.driver", "src/driver/msedgedriver.exe");

                // Driver MS Edge for MacOS only
                System.setProperty("webdriver.msedge.driver", "src/driver/msedgedriver");
                EdgeOptions edgeOptions = new EdgeOptions();
                return new EdgeDriver(edgeOptions);
            case "firefox":
                // Driver FireFox for Windows only
//                System.setProperty("webdriver.gecko.driver", "src/driver/geckodriver.exe");

                // Driver FireFox for MacOS only
                System.setProperty("webdriver.gecko.driver", "src/driver/geckodriver");
//                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                // maximize window
                firefoxOptions.addArguments("--start-maximize");
                // private mode
                firefoxOptions.addArguments("-private");

                return new FirefoxDriver(firefoxOptions);
            default:
                // Driver Chrome for Windows only
                // System.setProperty("webdriver.chrome.driver", "src/driver/chromedriver.exe");

                // Driver Chrome for MacOS only
                System.setProperty("webdriver.chrome.driver", "src/driver/chromedriver");
                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();
                // maximize window
                chromeOptions.addArguments("start-maximized");
                // private mode
                chromeOptions.addArguments("--incognito");
                // run browser without open browser
//                chromeOptions.addArguments("--headless");
                return new ChromeDriver(chromeOptions);
        }
    }
}

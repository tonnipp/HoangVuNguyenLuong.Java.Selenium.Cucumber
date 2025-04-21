package utils.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import Core.BaseSetup;
import utils.logs.Log;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    static ExtentReports extent = ExtentManager.createExtentReports();
    private static Status status;

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public static synchronized ExtentTest saveToReport(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }
  public static WebDriver driver;

    public static void addScreenShot(String message) {
        // Get the current test object for this thread
        ExtentTest currentTest = getTest();
        String base64Image = "";
        try {
            // --- Capture Screenshot ---
            Log.debug("Capturing screenshot for Extent Report...");
            base64Image = "data:image/png;base64,"
                    + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            Log.debug("Screenshot captured successfully.");

            // --- Log to Report ---
            currentTest.log(status, message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());

        } catch (Exception e) {
            // Log the message and error without a screenshot
            currentTest.log(status, message + " (Screenshot capture failed: " + e.getMessage() + ")");
        }
    }

    public static void addScreenShot(Status status, String message) {

        String base64Image = "data:image/png;base64,"
                + ((TakesScreenshot) BaseSetup.getDriver()).getScreenshotAs(OutputType.BASE64);
        getTest().log(status, message,               MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
    }

    public static void logMessage(String message) {
        getTest().log(Status.INFO, message);
    }

    public static void logMessage(Status status, String message) {
        getTest().log(status, message);
    }
}

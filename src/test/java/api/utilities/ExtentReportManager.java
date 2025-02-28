package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {
    String reportName;
    public ExtentSparkReporter extentReporter;
    public ExtentTest test;
    public ExtentReports reports;

    public void onStart(ITestContext context) {
       String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HHmmss").format(new Date());
       reportName="API-Test-Report-"+timeStamp+".html";
        extentReporter = new ExtentSparkReporter("./reports/"+reportName);
        extentReporter.config().setDocumentTitle("API-Rest-Assured-Test");
        extentReporter.config().setReportName("API-Rest-Assured-Test");
        extentReporter.config().setTheme(Theme.DARK);
        reports = new ExtentReports();
        reports.attachReporter(extentReporter);
        reports.setSystemInfo("Application", "Pet Store API");
        reports.setSystemInfo("Operating System", System.getProperty("os.name"));
        reports.setSystemInfo("System Version", System.getProperty("os.version"));
        reports.setSystemInfo("User Name", System.getProperty("user.name"));
        reports.setSystemInfo("User ", "Bhargava");
        reports.setSystemInfo("Environment", "Staging");
    }

    public void onTestSuccess(ITestResult result) {
        test = reports.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.PASS,"Test passed");
    }

    public void onTestFailure(ITestResult result) {
        test = reports.createTest(result.getName());
        test.createNode(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL,"Test failed");
        test.log(Status.FAIL,result.getThrowable().getMessage());
    }
    public void onTestSkipped(ITestResult result) {
        test = reports.createTest(result.getName());
        test.createNode(result.getName());
        test.log(Status.SKIP,"Test Skipped");
        test.log(Status.SKIP,result.getThrowable().getMessage());
    }
    public void onFinish(ITestContext context) {
        reports.flush();
    }
}

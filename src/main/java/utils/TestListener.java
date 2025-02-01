package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TestListener implements ITestListener {
    private PrintWriter writer;

    @Override
    public void onStart(ITestContext context) {
        File outputDir = new File("test-output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        try {
            File file = new File("test-output/test-results.log");
            writer = new PrintWriter(new FileWriter(file, true), true);
        } catch (IOException e) {
            Logger.info("Can't access test-results.log file: [%s]", e.getMessage());
        }
        Logger.info("Test suite started");
    }
    @Override
    public void onTestStart(ITestResult result) {
        Logger.info("Test started: [%s]", result.getName());
        writer.println(result.getName() + " - STARTED");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Logger.info("Test passed: [%s]", result.getName());
        writer.println(result.getName() + " - PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Logger.info("Test failed: [%s]", result.getName());
        writer.println(result.getName() + " - FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Logger.info("Test skipped: [%s]", result.getName());
        writer.println(result.getName() + " - SKIPPED");
    }

    @Override
    public void onFinish(ITestContext context) {
        Logger.info("Test suite finished");
        writer.close();
    }
}

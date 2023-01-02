package com.umtest.testframe.listener;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class SuiteFailingInvokedMethodListener implements IInvokedMethodListener {

    private static volatile boolean failing;

    public SuiteFailingInvokedMethodListener() {
        failing = false;
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (failing) {
            throw new RuntimeException("Test skipped due to test failures");
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (! testResult.isSuccess()) {
            failing = true;
        }

        if ((failing) && (testResult.getThrowable().getMessage().contains("Test skipped"))) {
            testResult.setStatus(ITestResult.SKIP);
        }
    }
}

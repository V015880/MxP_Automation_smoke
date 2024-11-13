package com.united;

import java.util.ArrayList;

public class FailureAnalysys 
{	
	ArrayList<String> sqlException=new ArrayList<String>();
	ArrayList<String> scriptException=new ArrayList<String>();
	ArrayList<String> applicationException=new ArrayList<String>();
	ArrayList<String> loggingReportingException=new ArrayList<String>();
	ArrayList<String> apiException=new ArrayList<String>();
	ArrayList<String> testDataException=new ArrayList<String>();
	ArrayList<String> networkException=new ArrayList<String>();
	public ArrayList<String> sqlData() {
		sqlException.add("java.sql.SQLException");
		sqlException.add("java.sql.SQLNonTransientException");
		sqlException.add("java.sql.SQLTransientException");
		sqlException.add("java.sql.SQLRecoverableException");		
		return sqlException;
	}
	
	public ArrayList<String> scriptData() {
		scriptException.add("org.openqa.selenium.WebDriverException");
		scriptException.add("java.lang.NullPointerException");
		scriptException.add("java.lang.IllegalStateException");
		scriptException.add("org.openqa.selenium.NoSuchElementException");
		scriptException.add("org.openqa.selenium.NoSuchWindowException");
		scriptException.add("org.openqa.selenium.NoSuchFrameException");
		scriptException.add("org.openqa.selenium.NoAlertPresentException");
		scriptException.add("org.openqa.selenium.InvalidSelectorException");
		scriptException.add("org.openqa.selenium.ElementNotVisibleException");
		scriptException.add("org.openqa.selenium.TimeoutException");
		scriptException.add("org.openqa.selenium.NoSuchSessionException");
		scriptException.add("org.openqa.selenium.StaleElementReferenceException");
		scriptException.add("io.cucumber.core.exception.CucumberException");
		scriptException.add("java.lang.IllegalStateException");
		scriptException.add("org.openqa.selenium.ElementNotInteractableException");
		return scriptException;
	}
	public ArrayList<String> apiData() {
		apiException.add("com.google.api.server.spi.response.BadRequestException");
		apiException.add("com.google.api.server.spi.response.UnauthorizedException");
		apiException.add("com.google.api.server.spi.response.ForbiddenException");
		apiException.add("org.assertj.core.api.AssertionError");
		apiException.add("com.ual.tms.TMSLogin$APIException");
		return apiException;
	}
	public ArrayList<String> testData() {
		testDataException.add("com.ual.tms.TMSLogin$TestDataException");
		return testDataException;
	}
	public ArrayList<String> applicationData() {
		applicationException.add("java.lang.AssertionError");
		applicationException.add("org.openqa.selenium.UnhandledAlertException");
		applicationException.add("java.lang.ClassCastException");
		applicationException.add("java.lang.NoSuchMethodError");
		applicationException.add("org.junit.ComparisonFailure");
		applicationException.add("org.testng.asserts.AssertionError");
		applicationException.add("org.opentest4j.AssertionFailedError");
		applicationException.add("org.junit.ComparisonFailure");
		return applicationException;
	}
	public ArrayList<String> loggingReportingData() {
		loggingReportingException.add("org.apache.log4j.LoggerNotFoundException");
		loggingReportingException.add("org.apache.log4j.LogRuntimeException");
		loggingReportingException.add("org.apache.log4j.LogException");
		loggingReportingException.add("org.apache.log4j.spi.LoggingEventNullException");
		loggingReportingException.add("org.apache.log4j.PatternLayoutFormatException");
		loggingReportingException.add("org.apache.log4j.RollingFileAppender$RollingFileAppenderIOException");
		loggingReportingException.add("org.apache.log4j.AppenderAttachException");
		loggingReportingException.add("org.apache.log4j.config.PropertySetterException");
		loggingReportingException.add("org.apache.log4j.config.PropertySetter");
		loggingReportingException.add("org.apache.log4j.config.PropertySetter$PropertyGetter");
		loggingReportingException.add("com.ual.tms.TMSLogin$ExtentReportException");
		loggingReportingException.add("com.aventstack.extentreports.reporter.ExtentReporterConverterException");
		loggingReportingException.add("com.aventstack.extentreports.viewdefs.XMLParseException");
		loggingReportingException.add("com.aventstack.extentreports.utils.FileUtilException");
		loggingReportingException.add("com.aventstack.extentreports.utils.StringUtilException");
		loggingReportingException.add("com.aventstack.extentreports.exceptions.ExtentHtmlReporterException");
		loggingReportingException.add("com.aventstack.extentreports.exceptions.UnsupportedEntityException");
		loggingReportingException.add("com.aventstack.extentreports.exceptions.ConfigurationException");
		loggingReportingException.add("com.aventstack.extentreports.exceptions.GherkinKeywordException");	
		return loggingReportingException;
	}
	public ArrayList<String> networkData() {
		networkException.add("org.openqa.selenium.SessionNotCreatedException");
		return networkException;
	}
}

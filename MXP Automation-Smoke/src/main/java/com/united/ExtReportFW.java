package com.united;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.KeyStore;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.http.entity.ByteArrayEntity;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.experitest.client.Client;
import com.experitest.client.GridClient;

public class ExtReportFW {
	ExtentReports er = null;
	ExtentReports er2 = null;
	ExtentTest ET = null;
	ExtentTest ET2 = null;
	Client client = null;
	boolean gridexe = false;
	WebDriver driver = null;
	public WebDriver stSSdriver = null;
	String capturescreenpath = "";
	int Imgcount = 0;
	LocalDateTime ExecnStarttime = null;
	static int timeout = 60;
	String testsuitename = "";
	String ReportFolder = "";
	String finalexecomments = "";
	String Extenthtml = "";
	String curtcidNname = "";
	String configsheetname = "";
	String browser = "";
	String Dockerhuburl = "";
	String webresolution = "";
	String environment = "";
	String Reporttime = "";
	String failurereason = "";
	String TCID;
	public String selected_Value = "";
	WebServiceCalls wsCalls = new WebServiceCalls();

	com.aventstack.extentreports.ExtentReports NewExtRep;
	com.aventstack.extentreports.ExtentTest Aventtest;
	ExtentSparkReporter ExtReporter;
	Map<String, String> wdor;

	public ExtReportFW(String Testsuitename, String Configsheetname, String dockhuburl, String Browser,
			String WebResolution, String Environment, String reportFolder,
			com.aventstack.extentreports.ExtentReports newExtRep, Map<String, String> wdor) throws IOException {
		this.testsuitename = Testsuitename;
		this.configsheetname = Configsheetname;
		this.Dockerhuburl = dockhuburl;
		this.browser = Browser;
		this.webresolution = WebResolution;
		this.environment = Environment;
		this.ReportFolder = reportFolder;
		this.NewExtRep = newExtRep;
		this.wdor = wdor;
	}

	public void wdClick(String xpath) {

		WebDriverWait wdwt = new WebDriverWait(driver, timeout);
		wdwt.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		driver.findElement(By.xpath(xpath)).click();
		String variableName = getVariableNameByXPath(xpath);
		this.addLogofWebDriver(LogStatus.PASS, "WebDriver Click", "Clicked on \"" + variableName + "\" (" + xpath + ")",
				true);
	}

	private String getVariableNameByXPath(String xpath) {
		for (Map.Entry<String, String> entry : wdor.entrySet()) {
			if (entry.getValue().equals(xpath)) {
				return entry.getKey();
			}
		}
		return "unknown variable";
	}

	public void logs(Object str) {
		System.setOut(RunClass.console);
		System.out.println(str);
		System.setOut(RunClass.stream);
		System.out.println(str);
		System.setOut(RunClass.console);
	}

	public void InitExtReport(String ReportFolderwithtime, ExtentSparkReporter extReporter) throws IOException {

		this.ExtReporter = extReporter;
		NewExtRep.attachReporter(ExtReporter);
		NewExtRep.setAnalysisStrategy(AnalysisStrategy.TEST);
		Extenthtml = ReportFolderwithtime;
		er = new ExtentReports(ReportFolderwithtime, false);
		String ExecnType = System.getenv("EXECTYPE");
		if (ExecnType == null)
			ExecnType = "";
		else
			ExecnType = " - " + ExecnType;
		String buildnum = System.getenv("BUILD_NUMBER");
		if (buildnum == null)
			buildnum = "";
		else {
			buildnum = " Build Number:" + buildnum;
			com.google.common.io.Files.write("".getBytes(),
					new File(ReportFolder + System.getenv("EXECTYPE") + "_" + buildnum.replace(":", "_") + ".DAT"));
		}
	RunClass.console = System.out;
	}

	public void startTest(String TCN, String DES, String Module) throws Exception {

		curtcidNname = TCN;
		this.TCID = TCN.split("__")[0];
		System.out.println("Connecting to MySQL database...");
		Files.createDirectories(Paths.get(ReportFolder + "\\Images" + "\\" + TCID));
		ET = er.startTest(TCN, DES);
		this.logs(Reporttime = ReportFolder + ("" + new Date()).replaceAll(" ", "").replaceAll(":", "") + "_"
				+ TCN.split("_")[0] + ".html");
		Aventtest = NewExtRep.createTest(TCN, DES);
		Aventtest.log(Status.PASS, "Test Started");
		er2 = new ExtentReports(Reporttime, true);
		ET2 = er2.startTest(TCN, DES);
	}

	public void InsertDataintoBaseTD(String strQuery) throws FilloException {
		Fillo BaseTD = new Fillo();
		com.codoid.products.fillo.Connection NREconnection = BaseTD
				.getConnection(System.getProperty("user.dir") + "\\TestInputs\\Base_TD.xls");
		NREconnection.executeUpdate(strQuery);
		NREconnection.close();
	}

	public void InsertDataintoVisitSchedulingData(String strQuery) throws FilloException {
		Fillo BaseTD = new Fillo();
		com.codoid.products.fillo.Connection NREconnection = BaseTD
				.getConnection(System.getProperty("user.dir") + "\\TestInputs\\VisitSchedulingData.xlsx");
		NREconnection.executeUpdate(strQuery);
		NREconnection.close();
	}

	public String getScreenshotofWebElement(String element) throws Exception {
		double tempht = 0, tempwdh = 0;
		WebElement ele = wdGetWebElement(element);
		movetoElementforActions(element);
		java.io.File tempscreen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(tempscreen);
		// Get the location of element on the page
		org.openqa.selenium.Point point = ele.getLocation();
		// Get width and height of the element
		int eleWidth = ele.getSize().getWidth();
		int eleHeight = ele.getSize().getHeight();
		tempht = fullImg.getRaster().getBounds().getHeight();
		tempwdh = fullImg.getRaster().getBounds().getWidth();
		if (point.getX() + eleWidth > tempwdh) {
			int diff = (int) (tempwdh - (point.getX() + eleWidth));
			eleWidth = eleWidth + diff;
		}
		if (point.getY() + eleHeight > tempht) {
			int diff = (int) (tempht - (point.getY() + eleHeight));
			eleHeight = eleHeight + diff;
		}

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, "png", tempscreen);
		// Copy the element screenshot to disk
		this.Imgcount++;
		capturescreenpath = ReportFolder + "Images\\" + TCID + "\\" + Imgcount + ".jpg";
		try {
			FileUtils.copyFile(tempscreen, new java.io.File(capturescreenpath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return capturescreenpath;
	}

	public void movetoElementforActions(WebElement element) {
		int i = 40;
		while (!element.isDisplayed() && i > 0) {
			try {
				Thread.sleep(500);
				i--;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(300);
		} catch (Exception e) {
		}
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	public String getScreenshotofWebElement(WebElement ele) throws Exception {
		double tempht = 0, tempwdh = 0;
		this.movetoElementforActions(ele);
		java.io.File tempscreen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(tempscreen);
		// Get the location of element on the page
		org.openqa.selenium.Point point = ele.getLocation();
		// Get width and height of the element
		int eleWidth = ele.getSize().getWidth();
		int eleHeight = ele.getSize().getHeight();
		tempht = fullImg.getRaster().getBounds().getHeight();
		tempwdh = fullImg.getRaster().getBounds().getWidth();
		if (point.getX() + eleWidth > tempwdh) {
			int diff = (int) (tempwdh - (point.getX() + eleWidth));
			eleWidth = eleWidth + diff;
		}
		if (point.getY() + eleHeight > tempht) {
			int diff = (int) (tempht - (point.getY() + eleHeight));
			eleHeight = eleHeight + diff;
		}
		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, "png", tempscreen);
		// Copy the element screenshot to disk
		Imgcount++;
		capturescreenpath = ReportFolder + "Images\\" + TCID + "\\" + Imgcount + ".jpg";
		try {
			FileUtils.copyFile(tempscreen, new java.io.File(capturescreenpath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return capturescreenpath;
	}

	public void addLogofSeeTest(LogStatus status, String stepName, String details, boolean ScreenCapture) {
		String tempscreen = "";
		if (ScreenCapture) {
			if (this.gridexe) {
				try {
					Thread.sleep(500);
				} catch (Exception e) {

				}
				java.io.File tempscreenfile = ((TakesScreenshot) this.stSSdriver).getScreenshotAs(OutputType.FILE);
				Imgcount++;
				capturescreenpath = ReportFolder + "Images\\" + TCID + "\\" + Imgcount + ".jpg";
				try {
					FileUtils.copyFile(tempscreenfile, new java.io.File(capturescreenpath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				tempscreen = client.capture("Capturing Screen after action");
				Imgcount++;
				capturescreenpath = ReportFolder + "Images\\" + TCID + "\\" + Imgcount + ".jpg";
				try {
					Files.copy(Paths.get(tempscreen), Paths.get(capturescreenpath), StandardCopyOption.COPY_ATTRIBUTES);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		this.logs(status + "-" + stepName + "-" + details);
		ET.log(status, stepName, details);
		ET2.log(status, stepName, details);
		if (ScreenCapture) {
			ET.log(LogStatus.INFO, "Screen Captured", ET.addScreenCapture(capturescreenpath.replace("\\", "/")));
			ET2.log(LogStatus.INFO, "Screen Captured", ET.addScreenCapture(capturescreenpath.replace("\\", "/")));
			Aventtest.log(Status.valueOf(status.name()), stepName + "--" + details,
					MediaEntityBuilder.createScreenCaptureFromPath(capturescreenpath.replace("\\", "/")).build());
		} else
			Aventtest.log(Status.valueOf(status.name()), stepName + "--" + details);
		if (status.equals(LogStatus.FAIL)) {
			this.failurereason = stepName + "--" + details;
		}
	}

	public void addLogofWebDriver(LogStatus status, String stepName, String details, boolean ScreenCapture) {
		java.io.File tempscreen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Imgcount++;
		capturescreenpath = ReportFolder + "Images\\" + TCID + "\\" + Imgcount + ".jpg";
		try {
			FileUtils.copyFile(tempscreen, new java.io.File(capturescreenpath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.logs(status + "-" + stepName + "-" + details);
		ET.log(status, stepName, details);
		ET2.log(status, stepName, details);
		if (ScreenCapture) {
			ET.log(LogStatus.INFO, "Screen Captured", ET.addScreenCapture(capturescreenpath.replace("\\", "/")));
			ET2.log(LogStatus.INFO, "Screen Captured", ET.addScreenCapture(capturescreenpath.replace("\\", "/")));
			Aventtest.log(Status.valueOf(status.name()), stepName + "--" + details,
					MediaEntityBuilder.createScreenCaptureFromPath(capturescreenpath.replace("\\", "/")).build());
		} else
			Aventtest.log(Status.valueOf(status.name()), stepName + "--" + details);
		if (status.equals(LogStatus.FAIL)) {
			this.failurereason = stepName + "--" + details;
		}
	}

	public void addLog(LogStatus logStatus, String stepName, String details, boolean ScreenCapture) {
		this.logs(logStatus + "-" + stepName + "-" + details);
		ET.log(logStatus, stepName, details);
		ET2.log(logStatus, stepName, details);
		Aventtest.log(Status.valueOf(logStatus.name()), stepName + "--" + details);
		if (ScreenCapture) {
			ET.addScreenCapture(capturescreenpath);
			ET2.addScreenCapture(capturescreenpath);
		}
		if (logStatus.equals(LogStatus.FAIL)) {
			this.failurereason = stepName + "--" + details;
		}
	}

	public void endTest() throws Exception {
		String htmlcontentstr = "";
		String status = "";
		er.endTest(ET);
		this.logs(status = "" + ET2.getTest().getStatus());
		String exetime[] = ET.getTest().getRunDuration().split(" ");
		String url = "jdbc:mysql://" + RunClass.execnresultsDB + "/projectsdetails?useSSL=false";
		String username = "nikhil";
		String password = "nikhil";
		System.out.println("Connecting to MySQL database...");
		int passpercent = 0;
		int totalcurtcpass = 0;
		int totalcurtcfail = 0;
		int totalcurtcruns = 1;
		String passhistory = "";
		RunClass.curexecntime = RunClass.curexecntime + RunClass.TCAvgExecutiontimings.get(TCID);

		int hrs = Integer.parseInt(exetime[0].replaceAll("h", "").replaceAll("H", ""));
		int mins = Integer.parseInt(exetime[1].replaceAll("m", "").replaceAll("M", ""));
		int secs = Integer.parseInt(exetime[2].split("s")[0]);
		int exeseconds = (hrs * 60 * 60) + (mins * 60) + secs;
		RunClass.actualcurexecntime = RunClass.actualcurexecntime + exeseconds;
//		RunClass.curexecntime = RunClass.curexecntime+exeseconds;
		try {
			if (RunClass.tcstatusconnection == null || RunClass.tcstatusconnection.isClosed()) {
				try {
					RunClass.tcstatusconnection = DriverManager.getConnection(url, username, password);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Properties prop = new Properties();
			prop.load(new FileInputStream(new File(
					System.getProperty("user.dir") + "\\TestInputs\\" + environment + "_Credentials.properties")));
			String repstatsdays = prop.getProperty("REPORTSTATS_DAYS");
			ResultSet rs = RunClass.tcstatusconnection
					.prepareStatement("SELECT * FROM projectsdetails.tcexecution_status where projid='"
							+ RunClass.projID + "' and tcnum='" + curtcidNname.split("__")[0]
							+ "' and entrytime>=NOW() - INTERVAL " + repstatsdays + " DAY order by entrytime asc;")
					.executeQuery();
			while (rs.next()) {
				totalcurtcruns++;
				String tempstatus = rs.getString("status");
				passhistory = passhistory + tempstatus;
				if (tempstatus.equalsIgnoreCase("Pass")) {
					totalcurtcpass++;
				} else if (tempstatus.equalsIgnoreCase("Fail")) {
					totalcurtcfail++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		passhistory = passhistory + status;
		passhistory = passhistory.replaceAll("PASS", "P").replaceAll("Pass", "P").replaceAll("pass", "P");
		passhistory = passhistory.replaceAll("FAIL", "F").replaceAll("Fail", "F").replaceAll("fail", "F");
		passhistory = passhistory.replaceAll("warning", "W").replaceAll("skip", "W");

		if (status.equalsIgnoreCase("PASS")) {
			totalcurtcpass++;
			RunClass.PassTCCount = totalcurtcpass;
			htmlcontentstr = htmlcontentstr + "<tr><td align=\"center\">" + curtcidNname.split("__")[0]
					+ "</td><td align=\"center\">" + curtcidNname.split("__")[1]
					+ "</td><td align=\"center\" bgcolor=\"#00ff23\">PASS</td>";
		} else if (status.equalsIgnoreCase("FAIL")) {
			totalcurtcfail++;
			RunClass.FailTCCount = totalcurtcfail;
			htmlcontentstr = htmlcontentstr + "<tr><td align=\"center\">" + curtcidNname.split("__")[0]
					+ "</td><td align=\"center\">" + curtcidNname.split("__")[1]
					+ "</td><td align=\"center\" bgcolor=\"#ff1900\">FAIL</td>";
		} else if (status.equalsIgnoreCase("WARNING")) {
			totalcurtcfail++;
			htmlcontentstr = htmlcontentstr + "<tr><td align=\"center\">" + curtcidNname.split("__")[0]
					+ "</td><td align=\"center\">" + curtcidNname.split("__")[1]
					+ "</td><td align=\"center\" bgcolor=\"#FEB06A\">WARN</td>";
		}

		passpercent = (100 * totalcurtcpass) / (totalcurtcfail + totalcurtcpass);
		String passpercentcolor = "";
		if (passpercent >= 80) {
			passpercentcolor = "#00ff23";
		} else if (passpercent >= 50 && passpercent < 80) {
			passpercentcolor = "#FEB06A";
		} else if (passpercent < 50) {
			passpercentcolor = "#ff1900";
		}

		double currentexecutionmins = ((float) exeseconds) / 60;
		currentexecutionmins = Math.round(currentexecutionmins * 100.0) / 100.0;
		double avgexemins = ((float) RunClass.TCAvgExecutiontimings.get(TCID)) / 60;
		avgexemins = Math.round(avgexemins * 100.0) / 100.0;
		htmlcontentstr = htmlcontentstr + "<td align=\"center\">" + this.failurereason + "</td>";
		htmlcontentstr = htmlcontentstr + "<td align=\"center\">" + currentexecutionmins + "</td><td align=\"center\" >"
				+ avgexemins + "</td>";
		htmlcontentstr = htmlcontentstr + "<td align=\"center\" bgcolor=\"" + passpercentcolor + "\">" + passpercent
				+ "%</td><td align=\"center\">" + passhistory + "</td>";
		htmlcontentstr = htmlcontentstr + "</tr>";

		int percentofExe = (int) (((float) RunClass.curexecntime / RunClass.totalexecntime) * 100);
		int tempActualCurExecn = 0;
		if (RunClass.datathreadcount > 1) {
			if (RunClass.TCAvgExecutiontimings.keySet().size() > RunClass.datathreadcount) {
				tempActualCurExecn = (int) (RunClass.actualcurexecntime / RunClass.datathreadcount);
			} else {
				tempActualCurExecn = (int) (RunClass.actualcurexecntime
						/ RunClass.TCAvgExecutiontimings.keySet().size());
			}

		} else
			tempActualCurExecn = RunClass.actualcurexecntime;
		String expectingminNsec = "";
		if (tempActualCurExecn <= RunClass.totalexecntime) {
			if (percentofExe < 100) {
				int temp = RunClass.totalexecntime - RunClass.curexecntime;
				if ((temp / 60) > 0) {
					expectingminNsec = "" + (temp / 60) + " minutes ";
				}
				expectingminNsec = expectingminNsec + (temp % 60) + " seconds";
				htmlcontentstr = htmlcontentstr + "<script>	\r\n" + "		if (" + percentofExe + " > -1) {\r\n"
						+ "  document.getElementById(\"progesstext\").innerHTML = \"Suite Execution Progress: "
						+ percentofExe + "% Completed.. more " + expectingminNsec + " remaining\";\r\n" + "}\r\n"
						+ "document.getElementById(\"progressbar\").setAttribute(\"value\", \"" + percentofExe + "\");"
						+ "</script>";
			} else if (percentofExe == 100) {
				int diff = RunClass.totalexecntime - tempActualCurExecn;
				String lessormore = "more";
				if (diff > 0) {
					lessormore = "less";
				}
				int temp = RunClass.totalexecntime - RunClass.curexecntime;
				if (!((diff / 60) == 0)) {
					expectingminNsec = "" + (diff / 60) + " minutes ";
				}
				expectingminNsec = expectingminNsec + (diff % 60) + " seconds ";
				htmlcontentstr = htmlcontentstr + "<script>	\r\n" + "		if (" + percentofExe + " > -1) {\r\n"
						+ "  document.getElementById(\"progesstext\").innerHTML = \"Suite Execution Progress: "
						+ percentofExe + "% Completed.. Execution Time took " + expectingminNsec + lessormore
						+ " than expected\";\r\n" + "}\r\n"
						+ "document.getElementById(\"progressbar\").setAttribute(\"value\", \"" + percentofExe + "\");"
						+ "</script>";
				this.finalexecomments = "Execution Time took " + expectingminNsec + " " + lessormore + " than expected";
			}
		} else {
			int temp = RunClass.totalexecntime - RunClass.curexecntime;
			if ((temp / 60) > 0) {
				expectingminNsec = "" + (temp / 60) + " minutes ";
			}
			expectingminNsec = expectingminNsec + (temp % 60) + " seconds";

			temp = RunClass.actualcurexecntime - RunClass.totalexecntime;
			String moretimeinminNsec = "";
			if ((temp / 60) > 0) {
				moretimeinminNsec = "" + (temp / 60) + " minutes ";
			}
			moretimeinminNsec = moretimeinminNsec + (temp % 60) + " seconds";

			htmlcontentstr = htmlcontentstr + "<script>	\r\n"
					+ "  document.getElementById(\"progesstext\").innerHTML = \"Suite Execution Progress: Taking more time than expected to Complete: +"
					+ moretimeinminNsec + "... Expecting more " + expectingminNsec + " to complete\";\r\n"
					+ "document.getElementById(\"progressbar\").setAttribute(\"value\", \"100\");" + "</script>";
			this.finalexecomments = "Execution Time took " + moretimeinminNsec + " more than expected";
		}

		Files.write(Paths.get(ReportFolder + "email.html"), htmlcontentstr.getBytes(), StandardOpenOption.APPEND);
		er2.endTest(ET2);
		er2.flush();
		Aventtest.log(Status.INFO, "<span>IF YOU ARE ON EXECUTED MACHINE, CLICK <a href=\"" + Reporttime
				+ "\">HERE</a> FOR INDIVIDUAL TC REPORT</span>");
	}

	public void flushExtRep() throws Exception {
		er.flush();
		if (this.client != null) {
			this.client.generateReport();
			this.client.releaseClient();
		}
		if (this.driver != null) {
			try {
				this.driver.quit();
			} catch (Exception e) {
			}
		}
		if (this.stSSdriver != null) {
			try {
				this.stSSdriver.quit();
			} catch (Exception e) {
			}
		}
	}

	public void stInitSeeTestClient(String DeviceName) throws FileNotFoundException, IOException {
		String httptext = "http";
		boolean stcsecure = false;
		if (this.client == null) {
			GridClient grid = null;
			if (this.GetValueFromCredentialProps("stcsecured").equalsIgnoreCase("Yes")) {
				httptext = "https";
				stcsecure = true;
			}
			if (DeviceName.contains("emulator") || DeviceName.contains("simulator")) {
				gridexe = true;
				String[] Device = DeviceName.split(":");

				grid = new GridClient(this.GetValueFromCredentialProps("stcuserkey"),
						this.GetValueFromCredentialProps("stcserver"),
						Integer.parseInt(this.GetValueFromCredentialProps("stcport")), stcsecure);

				if (DeviceName.contains("emulator")) {
					client = grid.lockDeviceForExecution(curtcidNname,
							"@os='android' and @emulator='true' and @category='" + Device[2].toUpperCase() + "'", 1440,
							50000);
				} else {
//                    client = grid.lockDeviceForExecution(curtcidNname, "@os='ios' and @emulator='true' and contains(@name, 'iPhone')", 1440, 50000);
					client = grid.lockDeviceForExecution(curtcidNname,
							"@os='ios' and @emulator='true' and @category='" + Device[2].toUpperCase() + "'", 1440,
							50000);
				}
				client.startLoggingDevice(ReportFolder + "\\logs");
				client.setDefaultTimeout(timeout * 1000);
				client.setProperty("screen.quality", "20");
				client.setSpeed("Fast");
				client.setProperty("ios.native.nonInstrumented", "false");
			} else if (!DeviceName.contains("_app") && !DeviceName.contains("adb")) {
				gridexe = true;
				grid = new GridClient(this.GetValueFromCredentialProps("stcuserkey"),
						this.GetValueFromCredentialProps("stcserver"),
						Integer.parseInt(this.GetValueFromCredentialProps("stcport")), stcsecure);

				client = grid.lockDeviceForExecution(curtcidNname, "@name='" + DeviceName + "'", 1440, 100000);
				client.startLoggingDevice(ReportFolder + "\\logs");
				client.setDefaultTimeout(timeout * 1000);
				client.setProperty("screen.quality", "20");
				client.setSpeed("Fast");
				client.setProperty("ios.native.nonInstrumented", "false");
			} else {
				client = new Client();
				client.setDevice(DeviceName);
				client.startLoggingDevice(ReportFolder + "\\logs");
				client.setDefaultTimeout(timeout * 1000);
				client.setProperty("screen.quality", "20");
				client.setSpeed("Fast");
				client.setProperty("ios.native.nonInstrumented", "false");
			}

			if (gridexe) {
				if (this.GetValueFromCredentialProps("screenreflection").equalsIgnoreCase("Yes")) {
					stSSdriver = this.wdInitChromeWebDriver();
				} else {
					stSSdriver = this.wdInitHeadlessChromeWebDriver();
				}
				stSSdriver.get(httptext + "://" + this.GetValueFromCredentialProps("stcserver") + "/#/execution/grid");
				stSSdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				logs("Opening Screen reflection...");
				stSSdriver.findElement(By.name("username")).sendKeys(this.GetValueFromCredentialProps("stcuser"));
				stSSdriver.findElement(By.name("password")).sendKeys(this.GetValueFromCredentialProps("stcpwd"));
				stSSdriver.findElement(By.xpath("//div[@data-auto=\"action login\"]//button")).click();
				stSSdriver.findElement(By.xpath("//div[@title='" + curtcidNname + "']")).click();
				logs("Opening Screen reflection...");
				String addr = stSSdriver.getWindowHandle();
				try {
					Thread.sleep(2000);
				} catch (Exception e) {

				}
				JavascriptExecutor executor = (JavascriptExecutor) this.stSSdriver;
				executor.executeScript("arguments[0].click();",
						this.stSSdriver.findElement(By.xpath("//button[contains(text(),'View')]")));
				executor = null;
				Set<String> WindowAddrs = this.stSSdriver.getWindowHandles();
				Iterator WinAddrsIter = WindowAddrs.iterator();
				while (WinAddrsIter.hasNext()) {
					String temp = WinAddrsIter.next().toString();
					if (!addr.equalsIgnoreCase(temp)) {
						addr = temp;
						break;
					}
				}
				this.stSSdriver.switchTo().window(addr);
				logs("Opening Screen reflection...");
				WebDriverWait wdwt = new WebDriverWait(this.stSSdriver, timeout);
				wdwt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"device-interactions\"]")));
				int i = 50;
				while (!this.stSSdriver.findElement(By.xpath("//*[@id=\"device-interactions\"]")).isDisplayed()
						&& i > 0) {
					try {
						Thread.sleep(500);
						this.logs("Waiting for element to display...");
						i--;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			this.addLogofSeeTest(LogStatus.PASS, "Initiation of Device",
					DeviceName + " Device Initialised Successfully", true);
			client.setReporter("xml", ReportFolder + "/SeeTestReport", curtcidNname);
		}

	}

	public String readDataInExcel(Sheet sheet, int row, int col) {
		Row currentRow = sheet.getRow(row);
		Cell CurrentCell = currentRow.getCell(col);
		return CurrentCell.toString();
	}

	public void SwitchToWindowWithAddress(String Addr) {
		this.driver.switchTo().window(Addr);
	}

	public void SwitchToWindowOtherThantheAddress(String Addr) {
		Set<String> WindowAddrs = this.driver.getWindowHandles();
		Iterator WinAddrsIter = WindowAddrs.iterator();
		while (WinAddrsIter.hasNext()) {
			String temp = WinAddrsIter.next().toString();
			if (!Addr.equalsIgnoreCase(temp)) {
				Addr = temp;
				break;
			}
		}
		this.driver.switchTo().window(Addr);
	}

	public WebDriver wdInitChromeWebDriver() throws FileNotFoundException, IOException {
		String downloadFilepath = System.getProperty("user.home") + System.getProperty("file.separator") + "Downloads";

		// Set Chrome options
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", downloadFilepath); // Set the default directory for downloads
        prefs.put("download.prompt_for_download", false); // Disable download prompt
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true); // Disable safe browsing warnings
		ChromeOptions chromeOptions = new ChromeOptions();
		if (!webresolution.equalsIgnoreCase("NA"))
			chromeOptions.addArguments("--window-size=" + webresolution);
		chromeOptions.addArguments("--force-device-scale-factor=0.67");
        chromeOptions.setExperimentalOption("prefs", prefs);
		WebDriver Driver = new ChromeDriver(chromeOptions);
		addLog(LogStatus.INFO, "Driver", "GC", false);
		return Driver;
	}

	public WebDriver wdInitHeadlessChromeWebDriver() throws FileNotFoundException, IOException {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("--disable-gpu");
		chromeOptions.addArguments("--window-size=" + webresolution);
		WebDriver Driver = new ChromeDriver(chromeOptions);
		addLog(LogStatus.INFO, "Driver", "GCH", false);
		return Driver;
	}

	public void wdInitConfiguredWebDriver() throws FileNotFoundException, IOException {
		if (this.driver == null) {

			if (this.browser.equalsIgnoreCase("gc")) {
				this.driver = this.wdInitChromeWebDriver();
				this.driver.manage().window().maximize();
			} else if (this.browser.equalsIgnoreCase("gch")) {
				this.driver = this.wdInitHeadlessChromeWebDriver();
				this.driver.manage().window().maximize();
			} else if (this.browser.equalsIgnoreCase("edge")) {
				this.driver = this.wdInitEdgeDriver();
				this.driver.manage().window().maximize();
			} else if (this.browser.equalsIgnoreCase("ie")) {
				this.driver = this.wdInitIEDriver();
			} else if (this.browser.equalsIgnoreCase("ff")) {
				this.driver = this.wdInitFirefoxDriver();
			} else if (this.browser.equalsIgnoreCase("dgc")) {
				this.driver = this.wdDockerInitChromeDriver();
				driver.manage().window().setSize(new Dimension(Integer.parseInt(webresolution.split(",")[0]),
						Integer.parseInt(webresolution.split(",")[1])));
			} else if (this.browser.equalsIgnoreCase("dff")) {
				this.driver = this.wdDockerInitfirefoxDriver();
				driver.manage().window().setSize(new Dimension(Integer.parseInt(webresolution.split(",")[0]),
						Integer.parseInt(webresolution.split(",")[1])));
			}

//			driver.manage().window().maximize() ;
//			driver.manage().timeouts().pageLoadTimeout(arg0, arg1)(timeout, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
			driver.manage().deleteAllCookies();
		}
	}

	private WebDriver wdDockerInitChromeDriver() throws MalformedURLException {
		// TODO Auto-generated method stub
		ChromeOptions chromeOptions = new ChromeOptions();
//		chromeOptions.addArguments("--headless");	   
//		chromeOptions.addArguments("--disable-gpu");
//		chromeOptions.addArguments("--no-sandbox");
//		chromeOptions.addArguments("--window-size="+webresolution);		
		DesiredCapabilities dc = new DesiredCapabilities();
//		dc.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		dc.setCapability(CapabilityType.PLATFORM, Platform.WINDOWS);
		dc.setCapability(CapabilityType.BROWSER_NAME, "chrome");
		return new RemoteWebDriver(new URL(Dockerhuburl), dc);
	}

	private WebDriver wdDockerInitfirefoxDriver() throws MalformedURLException {
		// TODO Auto-generated method stub
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.PLATFORM, Platform.LINUX);
		dc.setCapability(CapabilityType.BROWSER_NAME, "firefox");
		return new RemoteWebDriver(new URL(Dockerhuburl), dc);
	}

	private WebDriver wdInitFirefoxDriver() {
		// TODO Auto-generated method stub
		WebDriver Driver = new FirefoxDriver();
		return Driver;
	}

	private WebDriver wdInitEdgeDriver() {
		System.setProperty("webdriver.edge.driver",
				"C:\\Users\\v015880\\eclipse-United\\MXP Automation-Smoke\\msedgedriver.exe");
		WebDriver Driver = new EdgeDriver();
		return Driver;
	}

	private WebDriver wdInitIEDriver() {
		// TODO Auto-generated method stub
		WebDriver Driver = new InternetExplorerDriver();
		return Driver;
	}

	public void stClick(String zone, String element, int index, int clickCount) {
		client.click(zone, element, index, clickCount);
		this.addLogofSeeTest(LogStatus.PASS, "SeeTest Click", "After Clicking:" + element, true);
	}
//	public void wdClick(String variableName) {
//        String xpath = wdor.get(variableName);
//        WebDriverWait wdwt = new WebDriverWait(driver, timeout);
//        wdwt.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
//        driver.findElement(By.xpath(xpath)).click();
//        this.addLogofWebDriver(LogStatus.PASS, "WebDriver Click", "Clicked on " + variableName + " (" + xpath + ")", true);
//    }
//	public void wdClick(String element) {
//		movetoElementforActions(element);
//		WebDriverWait wdwt = new WebDriverWait(driver, timeout);
//		wdwt.until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
//		driver.findElement(By.xpath(element)).click();
//		;
//		this.addLogofWebDriver(LogStatus.PASS, "WebDriver Click", "After Clicking:" + element, true);
//	}

	public void wdClickbyJSexecutor(String xpath) {
	    JavascriptExecutor executor = (JavascriptExecutor) driver;
	    WebElement element = wdGetWebElement(xpath);
	    executor.executeScript("arguments[0].click();", element);
	    String variableName = getVariableNameByXPath(xpath);
	    this.addLogofWebDriver(LogStatus.PASS, "WebDriver Click JSE", "After Clicking: \"" + variableName + "\" (" + xpath + ")", true);
	}
	public void wdSelectionList_SelectbyIndex(String xpath, int index) {
	    movetoElementforActions(xpath);
	    Select sel = new Select(driver.findElement(By.xpath(xpath)));
	    sel.selectByIndex(index);
	    String variableName = getVariableNameByXPath(xpath);
	    addLogofWebDriver(LogStatus.INFO, "Selection", "Index " + index + " value selected by index for Element: \"" + variableName + "\" (" + xpath + ")", true);
	}
	public void wdSelectionList_SelectbyValue(String xpath, String value) {
	    Select sel = new Select(driver.findElement(By.xpath(xpath)));
	    sel.selectByValue(value);
	    String variableName = getVariableNameByXPath(xpath);
	    addLogofWebDriver(LogStatus.INFO, "Selection", "Value \"" + value + "\" selected for Element: \"" + variableName + "\" (" + xpath + ")", true);
	}
	public void wdSelectionList_SelectbyVisibleText(String xpath, String visibleText) {
	    Select sel = new Select(driver.findElement(By.xpath(xpath)));
	    sel.selectByVisibleText(visibleText);
	    String variableName = getVariableNameByXPath(xpath);
	    addLogofWebDriver(LogStatus.INFO, "Selection", "Visible text \"" + visibleText + "\" selected for Element: \"" + variableName + "\" (" + xpath + ")", true);
	}
	public void movetoElementforActions(String xpath) {
	    //waitforBAMWebToLoad();
	    WebDriverWait wdwt = new WebDriverWait(driver, timeout);
	    wdwt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
	    int i = 40;
	    while (!wdGetWebElement(xpath).isDisplayed() && i > 0) {
	        try {
	            Thread.sleep(300);
	            i--;
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    WebElement element = driver.findElement(By.xpath(xpath));
	    js.executeScript("arguments[0].scrollIntoView();", element);
	    String variableName = getVariableNameByXPath(xpath);
	    this.addLogofWebDriver(LogStatus.INFO, "Scroll", "Scrolled to Element: \"" + variableName + "\" (" + xpath + ")", true);
	}
	public boolean wdIsElementFound(String xpath) {
	    boolean found = false;
	    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	    if (driver.findElements(By.xpath(xpath)).size() > 0) {
	        found = true;
	    }
	    driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	    String variableName = getVariableNameByXPath(xpath);
	    if (found) {
	        this.addLogofWebDriver(LogStatus.INFO, "Element found", "Element found: \"" + variableName + "\" (" + xpath + ")", false);
	    } else {
	        this.addLogofWebDriver(LogStatus.INFO, "Element not found", "Element not found: \"" + variableName + "\" (" + xpath + ")", false);
	    }
	    return found;
	}
	public void wdWaitforElement(String xpath) {
	    int i = 40;
	    WebDriverWait wdwt = new WebDriverWait(driver, timeout);
	    wdwt.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	    while (!wdGetWebElement(xpath).isDisplayed() && i > 0) {
	        try {
	            Thread.sleep(500);
	            this.logs("Waiting for element to display...");
	            i--;
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	    String variableName = getVariableNameByXPath(xpath);
	    this.addLogofWebDriver(LogStatus.INFO, "Wait", "Waited for Element: \"" + variableName + "\" (" + xpath + ")", true);
	}


	public void waitforBAMWebToLoad() {
		int i = timeout;
		boolean flg = false;
		while (wdIsElementFound("//div[@class=\"App\"]//div[@class=\"spinner-container\"]") && i > 0) {
			try {
				this.logs("Loading...");
				Thread.sleep(500);
				i--;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flg = true;
		}
		if (flg) {
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void wdWaitforElementToBeVisible(String xpath) {
	    WebDriverWait wdwt = new WebDriverWait(driver, timeout);
	    wdwt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	    String variableName = getVariableNameByXPath(xpath);
	    this.addLogofWebDriver(LogStatus.INFO, "Wait", "Element \"" + variableName + "\" (" + xpath + ") is now visible.", true);
	}
	public void wdWaitforElementCountZero(String xpath) throws InterruptedException {
	    int count = 60;
	    while (count > 0) {
	        if (wdGetElementCount(xpath) == 0) {
	            break;
	        } else {
	            Thread.sleep(900);
	        }
	        this.logs("Waiting for element count to be zero for Element: \"" + getVariableNameByXPath(xpath) + "\" (" + xpath + ")");
	        count--;
	    }
	    String variableName = getVariableNameByXPath(xpath);
	    this.addLogofWebDriver(LogStatus.INFO, "Wait", "Element count for \"" + variableName + "\" (" + xpath + ") is zero.", true);
	}
	public void wdWaitforElementNotVisible(String xpath) {
	    WebDriverWait wdwt = new WebDriverWait(driver, timeout);
	    wdwt.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
	    String variableName = getVariableNameByXPath(xpath);
	    this.addLogofWebDriver(LogStatus.INFO, "Wait", "Element \"" + variableName + "\" (" + xpath + ") is no longer visible.", true);
	}
	public void wdWaitforElementtoVanish(String xpath) throws InterruptedException {
	    WebDriverWait wdwt = new WebDriverWait(driver, timeout);
	    wdwt.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
	    while (wdGetWebElement(xpath).isDisplayed()) {
	        Thread.sleep(100);
	    }
	    String variableName = getVariableNameByXPath(xpath);
	    this.addLogofWebDriver(LogStatus.INFO, "Wait", "Element \"" + variableName + "\" (" + xpath + ") has vanished from view.", true);
	}


	public void stElementSendText(String zone, String element, int index, String text) {
		client.elementSendText(zone, element, index, text);
		if (element.toLowerCase().contains("password") || element.toLowerCase().contains("pwd"))
			this.addLogofSeeTest(LogStatus.PASS, "SeeTest SendKeys",
					"After entering Text:******** (Masked as the element is expected to be a Password) to element:"
							+ element,
					true);
		else
			this.addLogofSeeTest(LogStatus.PASS, "SeeTest SendKeys",
					"After entering:" + text + " to element:" + element, true);
	}

	public void wdElementSendText(String xpath, String text) {
	    wdClick(xpath);
	    WebElement element = driver.findElement(By.xpath(xpath));
	    element.clear();
	    element.sendKeys(text);
	    String variableName = getVariableNameByXPath(xpath);
	    
	    if (xpath.toLowerCase().contains("password") || xpath.toLowerCase().contains("pwd")) {
	        this.addLogofWebDriver(LogStatus.PASS, "WebDriver SendKeys",
	                "After entering Text: ******** (Masked as the element is expected to be a Password) to element: \"" + variableName + "\" (" + xpath + ")", true);
	    } else {
	        this.addLogofWebDriver(LogStatus.PASS, "WebDriver SendKeys",
	                "After entering: \"" + text + "\" to element: \"" + variableName + "\" (" + xpath + ")", true);
	    }
	}
	public String wdElementSendKeys(String xpath, String text) {
	    this.movetoElementforActions(xpath);
	    WebElement element = driver.findElement(By.xpath(xpath));
	    element.sendKeys(text);
	    String variableName = getVariableNameByXPath(xpath);
	    this.addLogofWebDriver(LogStatus.PASS, "WebDriver SendKeys",
	            "After entering: \"" + text + "\" to element: \"" + variableName + "\" (" + xpath + ")", true);
	    return text;
	}
	public int wdGetElementCount(String xpath) {
	    int count = 0;
	    this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	    count = this.driver.findElements(By.xpath(xpath)).size();
	    this.driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	    String variableName = getVariableNameByXPath(xpath);
	    this.logs("Element: \"" + variableName + "\" (" + xpath + ") - Count: " + count);
	    return count;
	}


	public void stVerifyElementFound(String element, int index) {
		client.verifyElementFound("NATIVE", element, index);
		this.addLogofSeeTest(LogStatus.PASS, "SeeTest Verify Element found", "Verify existence of element:" + element,
				true);
	}

	public void stVerifyElementNotFound(String element, int index) {
		client.verifyElementNotFound("NATIVE", element, index);
		this.addLogofSeeTest(LogStatus.PASS, "SeeTest Verify Element Not found",
				"Verify non existence of element:" + element, true);
	}

	public void wdVerifyElementNotFound(String xpath) {
	    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	    if (driver.findElements(By.xpath(xpath)).size() == 0) {
	        String variableName = getVariableNameByXPath(xpath);
	        this.addLogofWebDriver(LogStatus.PASS, "WebDriver Verify Element Not Found",
	                "Verified non-existence of element: \"" + variableName + "\" (" + xpath + ")", true);
	    } else {
	        String variableName = getVariableNameByXPath(xpath);
	        this.addLogofWebDriver(LogStatus.FAIL, "WebDriver Verify Element Not Found",
	                "Failed - Element should not be found: \"" + variableName + "\" (" + xpath + ")", false);
	        throw new WebDriverException("Element should not be found: \"" + variableName + "\" (" + xpath + ")");
	    }
	    driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}
	public void wdVerifyElementFound(String xpath) {
	    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	    if (driver.findElements(By.xpath(xpath)).size() > 0) {
	        this.movetoElementforActions(xpath);
	        String variableName = getVariableNameByXPath(xpath);
	        this.addLogofWebDriver(LogStatus.PASS, "WebDriver Verify Element Found",
	                "Verified existence of element: \"" + variableName + "\" (" + xpath + ")", true);
	    } else {
	        String variableName = getVariableNameByXPath(xpath);
	        this.addLogofWebDriver(LogStatus.FAIL, "WebDriver Verify Element Found",
	                "Failed - Element should be found: \"" + variableName + "\" (" + xpath + ")", false);
	        throw new WebDriverException("Element should be found: \"" + variableName + "\" (" + xpath + ")");
	    }
	    driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}


	public String GetValueFromCredentialProps(String Key) throws FileNotFoundException, IOException {
		String value = "";
		Properties prop = new Properties();
		prop.load(new FileInputStream(new java.io.File(
				System.getProperty("user.dir") + "/TestInputs/" + environment + "_Credentials.properties")));
		value = "" + prop.get(Key);
		String orgstr = value;
		String str = "";
		if (orgstr.contains("ENCRYPT:[")) {
			str = orgstr.substring(orgstr.indexOf("ENCRYPT:["));
			str = str.substring(0, str.indexOf("]") + 1);
			value = value.replace(str, PasswordEncryptandDecrypt.PasswordParse.DecryptPassCode(str));
		}

		return value;

	}

	public boolean isInt(String str) {
		boolean flg = false;
		try {
			Integer.parseInt(str);
			flg = true;
		} catch (Exception e) {

		}
		return flg;
	}

	public boolean isFloat(String str) {
		boolean flg = false;
		try {
			Float.parseFloat(str);
			flg = true;
		} catch (Exception e) {

		}
		return flg;
	}

	public void setiOSdeviceTimeandReturntoApplication(int Hrin24hrformat) {
		String settingsapp = "com.apple.Preferences";
		String CurrApp = this.client.getCurrentApplicationName();
		this.client.launch("", false, true);
		this.client.click("NATIVE", "xpath=(//*[@value='Date & Time'])[1]", 0, 1);
		String curdevicetime = this.client.getDeviceProperty("device.time");
		int curhr = Integer.parseInt(((curdevicetime.split(" ")[1]).split(":"))[0]);
		this.client.click("NATIVE", "xpath=//*[@text='"
				+ (curdevicetime.split(" ")[1].split(":")[0] + ":" + curdevicetime.split(" ")[1].split(":")[1]) + "']",
				0, 1);
		int diff = Hrin24hrformat - curhr;
		if (diff < 0) {
			this.client.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 1, "down:" + (diff * -1));

		} else if (diff > 0) {
			this.client.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 1, "up:" + diff);
		}
		this.client.applicationClose(settingsapp);
		if (!CurrApp.equalsIgnoreCase(settingsapp))
			this.client.launch(CurrApp, true, false);
	}

	public void stGenericElementSwipeWhileNotFoundforIpad(String componentElement, String direction,
			String elementtofind, boolean click) {
		int index = 0, clickCount = 0;
		client.elementSwipeWhileNotFound("NATIVE", componentElement, direction, 600, 2800, "NATIVE", elementtofind,
				index, 1250, 10, click);
	}

	public void stGenericElementSwipeWhileNotFoundforIpad(String componentElement, String direction,
			String elementtofind, int rounds, boolean click) {
		int index = 0, clickCount = 0;
		client.elementSwipeWhileNotFound("NATIVE", componentElement, direction, 600, 2800, "NATIVE", elementtofind,
				index, 1250, rounds, click);
	}

	public void clickKeyBoardSearchbtn() {
//		this.client.setProperty("ios.native.nonInstrumented", "true");
//		stClick("NATIVE", "xpath=//*[(@id='Search' or @id='search') and @class='UIAButton' and @visible='true']", 0, 1);
//		this.client.setProperty("ios.native.nonInstrumented", "false");
		stClick("NATIVE", "nixpath=//*[(@id='Search' or @id='search') and @class='UIAButton' and @visible='true']", 0,
				1);
	}

	public String DecryptText(String encryptedText) {
		return PasswordEncryptandDecrypt.PasswordParse.DecryptPassCode(encryptedText);
	}

	public String DateGen() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMM_HHmmss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public String DateGen(String format) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public WebElement wdGetWebElement(String element) {
		WebElement ele = this.driver.findElement(By.xpath(element));
		return ele;
	}

	public Recordset QueryAsheetfromConfigFile(String SQLQuery) throws FilloException {
		Fillo TCSheet = new Fillo();
		Connection TCSheetConnection = TCSheet
				.getConnection(System.getProperty("user.dir") + "\\TestInputs\\" + configsheetname);
		Recordset rs = TCSheetConnection.executeQuery(SQLQuery);
		TCSheetConnection.close();
		TCSheet = null;
		return rs;
	}

	public Recordset QueryAsheetfromGivenDataFileinTestInputs(String SQLQuery, String SheetFileName)
			throws FilloException {
		Recordset rs = null;
		try {
			Fillo TCSheet = new Fillo();
			Connection TCSheetConnection = TCSheet
					.getConnection(System.getProperty("user.dir") + "\\TestInputs\\" + SheetFileName);
			rs = TCSheetConnection.executeQuery(SQLQuery);
			TCSheetConnection.close();
			TCSheet = null;
		} catch (Exception e) {
		}
		return rs;
	}

	public WebElement wdGetWebElementwithIndex(String element, int index) {
		WebElement ele = this.driver.findElements(By.xpath(element)).get(index);
		return ele;
	}

	public boolean wdIsElementClickable(String element) {
		boolean flg = false;
		WebDriverWait wdwt = new WebDriverWait(driver, 1);
		try {
			wdwt.until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
			flg = true;
		} catch (Exception e) {
		}
		return flg;
	}

	public void dragNdropUsingJSExecutor(String cssSource, String cssTarget) {
		try {
			String filePath = "./dnd.js-master//dnd.js";
			String source = "div[id='draggable']";
			String target = "div[id='droppable']";
			StringBuffer buffer = new StringBuffer();
			String line;
//		      BufferedReader br = new BufferedReader(new FileReader(filePath));
//		      while((line = br.readLine())!=null)
//		          buffer.append(line);
//
//		      Pattern pattern = Pattern.compile("'(.*?)'");
//		      Matcher matcherSource = pattern.matcher(source);
//		      Matcher matcherTarget = pattern.matcher(target);

			String javaScript = buffer.toString();

			javaScript = javaScript + "$('" + cssSource + "').simulateDragDrop({ dropTarget: '" + cssTarget + "'});";
			((JavascriptExecutor) this.driver).executeScript(javaScript);

		} catch (Exception ex) {
			this.logs(ex.getMessage());
		}
	}

	public void SendExecutionDetailstoDashBoard(LocalDateTime ExecnStarttime) {
		try {
			this.logs("in to automation dashboards");
			String url = "http://10.164.67.113:8700/AutomationExecutionData/automationExecutions.asmx?wsdl";

			String startDateTime = ("" + ExecnStarttime).split("\\.")[0];
			LocalDateTime endtime = LocalDateTime.now();
			String endDateTime = ("" + endtime).split("\\.")[0];
			long totalTimeforexecution = ChronoUnit.MINUTES.between(ExecnStarttime, endtime);
			if (totalTimeforexecution == 0) {
				totalTimeforexecution = 1;
			}

			int totlaCases = RunClassNonParallel.PassTCCount + RunClassNonParallel.FailTCCount;
			int totalPass = RunClassNonParallel.PassTCCount;
			int totalFail = RunClassNonParallel.FailTCCount;
			String projectID = GetValueFromCredentialProps("ProjectID");
			String releaseName = GetValueFromCredentialProps("ReleaseName");
			String environment = GetValueFromCredentialProps("Environment");
			String VMused = InetAddress.getLocalHost().getHostName();
			String testingType = GetValueFromCredentialProps("TestingType");
			String purpose = GetValueFromCredentialProps("Purpose");
			String reportedBy = GetValueFromCredentialProps("ReportedBy");
			if (totlaCases > 20) {
				purpose = "REGRESSION TESTING";
			} else {
				purpose = "SMOKE TESTING";
			}
			testingType = "REGRESSION";
			String xml = "" + // "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n " +
					"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"> \r\n"
					+ "  <soap:Body>\r\n " + "    <SaveAutomationRuns xmlns=\"http://tempuri.org/\">\r\n "
					+ "      <ProjectID>" + projectID + "</ProjectID>\r\n " + "      <ExecutionStartDateTime>"
					+ startDateTime + "</ExecutionStartDateTime>\r\n " + "      <ExecutionEndDateTime>" + endDateTime
					+ "</ExecutionEndDateTime>\r\n " + "      <ExecutionTime>" + totalTimeforexecution
					+ "</ExecutionTime> \r\n" + "      <TotalCasesExecuted>" + totlaCases + "</TotalCasesExecuted>\r\n "
					+ "      <TotalCasesPassed>" + totalPass + "</TotalCasesPassed>\r\n " + "      <TotalCasesFailed>"
					+ totalFail + "</TotalCasesFailed> \r\n" + "      <ReleaseName>" + releaseName
					+ "</ReleaseName>\r\n " + "      <Environment>" + environment + "</Environment>\r\n "
					+ "      <VMUsed>" + VMused + "</VMUsed>\r\n " + "      <TestingType>" + testingType
					+ "</TestingType>\r\n " + "      <TestingPurpose>" + purpose + "</TestingPurpose>\r\n "
					+ "      <ReportedBy>" + reportedBy + "</ReportedBy>\r\n " + "    </SaveAutomationRuns>\r\n "
					+ "  </soap:Body>\r\n " + "</soap:Envelope>\r\n ";

			this.logs(xml);
			this.logs("totlaCases     is   " + totlaCases);
			this.logs("totalPass     is   " + totalPass);
			this.logs("totalFail     is   " + totalFail);
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(xml);
			wr.flush();
			wr.close();
			this.logs(con.getResponseMessage());
			this.logs(con.getResponseCode());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void overallExecutiondetailsinEMAILnp(int passcount, int failcount) throws IOException {
		InetAddress id = InetAddress.getLocalHost();
		String hostname = id.getHostName();
		int totalTCcount = passcount + failcount;
		int passpercent = (int) (((float) passcount * 100) / totalTCcount);
		int failpercent = 100 - passpercent;

		String proghtmlcontent = "\r\n" + "	</table>	\r\n" + "			<br>\r\n"
				+ "			<h>OverAll Status of Tests:</h>"
				+ "	<table id=\"customprogress\" style=\"width:100%\" width=\"50\" rules=\"none\"  bgcolor=\"#F0F0F0\">\r\n"
				+ "		<tr>\r\n";

		for (int i = 0; i < passpercent; i++) {
			proghtmlcontent = proghtmlcontent + "<td bgcolor=\"#00ff23\" ></td>";
			i++;
		}
		for (int i = 0; i < failpercent; i++) {
			proghtmlcontent = proghtmlcontent + "<td bgcolor=\"#ff1900\" ></td>";
			i++;
		}
		int temp = RunClass.actualcurexecntime;
		String totalactualexetimeMinNsec = "";
		if (!((temp / 60) == 0)) {
			totalactualexetimeMinNsec = "" + (temp / 60) + " minutes ";
		}
		totalactualexetimeMinNsec = totalactualexetimeMinNsec + (temp % 60) + " seconds ";

		proghtmlcontent = proghtmlcontent + "</tr>			\r\n" + "	</table>\r\n" + "	<p> </p>\r\n"
				+ "	<p> </p>\r\n" + "	\r\n" + "	<table id=\"info\"  bgcolor=\"#F0F0F0\">		\r\n"
				+ "		<tr bgcolor=\"#000000\">\r\n"
				+ "			<th align=\"center\" style=\"color:white; font-size:12pt;\"></th>\r\n"
				+ "			<th align=\"center\" style=\"color:white; font-size:12pt;\">Additional Information</th>\r\n"
				+ "		</tr>\r\n" + "		<tr>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">PASS</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">" + passpercent + "%</td>\r\n" + "		</tr>\r\n"
				+ "		<tr>\r\n" + "			<td bgcolor=\"#ff1900\" align=\"center\">FAIL</td>\r\n"
				+ "			<td bgcolor=\"#ff1900\" align=\"center\">" + failpercent + "%</td>\r\n"
				+ "		</tr>		\r\n" + "		<tr>\r\n"
				+ "			<td align=\"center\" bgcolor=\"#4275f5\" >Host</td>\r\n"
				+ "			<td align=\"center\" bgcolor=\"#4275f5\" >" + hostname + "</td>\r\n" + "		</tr>\r\n"
				+ "		<tr>\r\n" + "			<td align=\"center\" bgcolor=\"#deded9\" >Execution Comments</td>\r\n"
				+ "			<td align=\"center\" bgcolor=\"#deded9\" >" + this.finalexecomments + "</td>\r\n"
				+ "		</tr>\r\n" + "		<tr>\r\n"
				+ "			<td align=\"center\" bgcolor=\"#deded9\" >Total Execution Time</td>\r\n"
				+ "			<td align=\"center\" bgcolor=\"#deded9\" >" + totalactualexetimeMinNsec + "</td>\r\n"
				+ "		</tr>\r\n" + "		\r\n" + "	</table>";

		Files.write(Paths.get(ReportFolder + "email.html"), proghtmlcontent.getBytes(), StandardOpenOption.APPEND);
		overallFailurAnalysisinEMAILnp();
	}

	public void overallExecutiondetailsinEMAIL(int passcount, int failcount) throws IOException {
		InetAddress id = InetAddress.getLocalHost();
		String hostname = id.getHostName();
		int totalTCcount = passcount + failcount;
		int passpercent = (int) (((float) passcount * 100) / totalTCcount);
		int failpercent = 100 - passpercent;

		String proghtmlcontent = "\r\n" + "	</table>	\r\n" + "			<br>\r\n"
				+ "			<h>OverAll Status of Tests:</h>"
				+ "	<table id=\"customprogress\" style=\"width:100%\" width=\"50\" rules=\"none\"  bgcolor=\"#F0F0F0\">\r\n"
				+ "		<tr>\r\n";

		for (int i = 0; i < passpercent; i++) {
			proghtmlcontent = proghtmlcontent + "<td bgcolor=\"#00ff23\" ></td>";
			i++;
		}
		for (int i = 0; i < failpercent; i++) {
			proghtmlcontent = proghtmlcontent + "<td bgcolor=\"#ff1900\" ></td>";
			i++;
		}
		int temp = RunClass.actualcurexecntime;
		String totalactualexetimeMinNsec = "";
		if (!((temp / 60) == 0)) {
			totalactualexetimeMinNsec = "" + (temp / 60) + " minutes ";
		}
		totalactualexetimeMinNsec = totalactualexetimeMinNsec + (temp % 60) + " seconds ";

		proghtmlcontent = proghtmlcontent + "</tr>			\r\n" + "	</table>\r\n" + "	<p> </p>\r\n"
				+ "	<p> </p>\r\n" + "	\r\n" + "	<table id=\"info\"  bgcolor=\"#F0F0F0\">		\r\n"
				+ "		<tr bgcolor=\"#000000\">\r\n"
				+ "			<th align=\"center\" style=\"color:white; font-size:12pt;\"></th>\r\n"
				+ "			<th align=\"center\" style=\"color:white; font-size:12pt;\">Additional Information</th>\r\n"
				+ "		</tr>\r\n" + "		<tr>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">PASS</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">" + passpercent + "%</td>\r\n" + "		</tr>\r\n"
				+ "		<tr>\r\n" + "			<td bgcolor=\"#ff1900\" align=\"center\">FAIL</td>\r\n"
				+ "			<td bgcolor=\"#ff1900\" align=\"center\">" + failpercent + "%</td>\r\n"
				+ "		</tr>		\r\n" + "		<tr>\r\n"
				+ "			<td align=\"center\" bgcolor=\"#4275f5\" >Host</td>\r\n"
				+ "			<td align=\"center\" bgcolor=\"#4275f5\" >" + hostname + "</td>\r\n" + "		</tr>\r\n"
				+ "		<tr>\r\n" + "			<td align=\"center\" bgcolor=\"#deded9\" >Execution Comments</td>\r\n"
				+ "			<td align=\"center\" bgcolor=\"#deded9\" >" + this.finalexecomments + "</td>\r\n"
				+ "		</tr>\r\n" + "		<tr>\r\n"
				+ "			<td align=\"center\" bgcolor=\"#deded9\" >Total Execution Time</td>\r\n"
				+ "			<td align=\"center\" bgcolor=\"#deded9\" >" + totalactualexetimeMinNsec + "</td>\r\n"
				+ "		</tr>\r\n" + "		\r\n" + "	</table>";

		Files.write(Paths.get(ReportFolder + "email.html"), proghtmlcontent.getBytes(), StandardOpenOption.APPEND);
		overallFailurAnalysisinEMAIL();
	}

	public void overallFailurAnalysisinEMAILnp() throws IOException {
		String typeOfFailuresCount = "\r\n" + "	</table>	\r\n" + "			<br>\r\n"
				+ "			<h>Failure Analysis:</h>"
				+ "	<table id=\"customprogress\" style=\"width:100%\" width=\"50\" rules=\"none\"  bgcolor=\"#F0F0F0\">\r\n"
				+ "		<tr>\r\n";

		typeOfFailuresCount = typeOfFailuresCount + "</tr>			\r\n" + "	</table>\r\n" + "	<p> </p>\r\n"
				+ "	<p> </p>\r\n" + "	\r\n" + "	<table id=\"info\"  bgcolor=\"#F0F0F0\">		\r\n"
				+ "		<tr bgcolor=\"#000000\">\r\n"
				+ "			<th align=\"center\" style=\"color:white; font-size:12pt;\">Type Of Failure</th>\r\n"
				+ "			<th align=\"center\" style=\"color:white; font-size:12pt;\">Total Scripts Failed</th>\r\n"
				+ "		</tr>\r\n" + "		<tr>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">Script Related Failures</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">" + RunClassNonParallel.scriptrelatedfailurescount
				+ "</td>\r\n" + "		</tr>\r\n" + "		<tr>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">Database Related Failures</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">"
				+ RunClassNonParallel.databaserelatedfailurescount + "</td>\r\n" + "		</tr>\r\n"
				+ "		<tr>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">Application Related Failures</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">"
				+ RunClassNonParallel.applicationrelatedfailurescount + "</td>\r\n" + "		</tr>\r\n"
				+ "		<tr>\r\n" + "			<td bgcolor=\"#00ff23\" align=\"center\">Api Related Failures</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">" + RunClassNonParallel.apirelatedfailurescount
				+ "</td>\r\n" + "		</tr>\r\n" + "		<tr>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">Network Related Failures</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">" + RunClassNonParallel.networkrelatedfailurescount
				+ "</td>\r\n" + "		</tr>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">Other Failures</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">" + RunClassNonParallel.otherfailurescount
				+ "</td>\r\n" + "		</tr>\r\n";

		Files.write(Paths.get(ReportFolder + "email.html"), typeOfFailuresCount.getBytes(), StandardOpenOption.APPEND);
	}

	public void overallFailurAnalysisinEMAIL() throws IOException {
		String typeOfFailuresCount = "\r\n" + "	</table>	\r\n" + "			<br>\r\n"
				+ "			<h>Failure Analysis:</h>"
				+ "	<table id=\"customprogress\" style=\"width:100%\" width=\"50\" rules=\"none\"  bgcolor=\"#F0F0F0\">\r\n"
				+ "		<tr>\r\n";

		typeOfFailuresCount = typeOfFailuresCount + "</tr>			\r\n" + "	</table>\r\n" + "	<p> </p>\r\n"
				+ "	<p> </p>\r\n" + "	\r\n" + "	<table id=\"info\"  bgcolor=\"#F0F0F0\">		\r\n"
				+ "		<tr bgcolor=\"#000000\">\r\n"
				+ "			<th align=\"center\" style=\"color:white; font-size:12pt;\">Type Of Failure</th>\r\n"
				+ "			<th align=\"center\" style=\"color:white; font-size:12pt;\">Total Scripts Failed</th>\r\n"
				+ "		</tr>\r\n" + "		<tr>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">Script Related Failures</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">" + RunClass.scriptrelatedfailurescount
				+ "</td>\r\n" + "		</tr>\r\n" + "		<tr>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">Database Related Failures</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">" + RunClass.databaserelatedfailurescount
				+ "</td>\r\n" + "		</tr>\r\n" + "		<tr>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">Application Related Failures</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">" + RunClass.applicationrelatedfailurescount
				+ "</td>\r\n" + "		</tr>\r\n" + "		<tr>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">Api Related Failures</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">" + RunClass.apirelatedfailurescount + "</td>\r\n"
				+ "		</tr>\r\n" + "		<tr>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">Network Related Failures</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">" + RunClass.networkrelatedfailurescount
				+ "</td>\r\n" + "		</tr>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">Other Failures</td>\r\n"
				+ "			<td bgcolor=\"#00ff23\" align=\"center\">" + RunClass.otherfailurescount + "</td>\r\n"
				+ "		</tr>\r\n";

		Files.write(Paths.get(ReportFolder + "email.html"), typeOfFailuresCount.getBytes(), StandardOpenOption.APPEND);
	}

	public static void sendEmailReport(String environment, String ReportFolder, String ConsolidateReport)
			throws Exception {

		try {
			String tempstr = "";
			byte[] htmlstrings = Files.readAllBytes(Paths.get(ReportFolder + "/ConsolidatedReport/Index.html"));// StandardCharsets.ISO_8859_1
			tempstr = new String(htmlstrings);
			tempstr = tempstr.replaceAll(("file:///" + ReportFolder).replace("\\", "/"), "");
			String toReplacewithLogos = "<ul class=\"nav-left\">\r\n" + "<div>\r\n"
					+ "<img src=\"https://www.united.com/8ea0b8c6d5c3fab39bb81ad99f8f0fc8.svg\" style=\"float: left; margin-left: 15px;\" width=\"150\" height=\"25\">\r\n"
					+ "</img>\r\n"
					+ "<img src=\"https://www.valuelabs.com/wp-content/themes/vl/images/valuelabs-logo.svg\" width=\"90\" height=\"15\" style=\"float: right; margin-right: 15px;\">\r\n"
					+ "</img>\r\n" + "</div>\r\n" + "</ul>	\r\n" + "\r\n" + "<ul class=\"nav-right\">";
			tempstr = tempstr.replace("<ul class=\"nav-right\">", toReplacewithLogos);

			Files.deleteIfExists(Paths.get(ReportFolder + "/ConsolidatedReport/ConsolidatedReport.html"));
			Thread.sleep(3000);
			Files.write(Paths.get(ReportFolder + "/ConsolidatedReport/ConsolidatedReport.html"), tempstr.getBytes(),
					StandardOpenOption.CREATE_NEW);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			String ExecnType = System.getenv("EXECTYPE");
			if (ExecnType == null)
				ExecnType = "";
			else
				ExecnType = " - " + ExecnType;
			String buildnum = System.getenv("BUILD_NUMBER");
			if (buildnum == null)
				buildnum = "";
			else {
				buildnum = " Build Number:" + buildnum;
				// com.google.common.io.Files.write("".getBytes(), new
				// File(ReportFolder+System.getenv("EXECTYPE")+"_"+buildnum.replace(":",
				// "_")+".DAT" ));
			}
			Properties prop = new Properties();
			InputStream input = new FileInputStream(
					System.getProperty("user.dir") + "\\TestInputs\\" + environment + "_Credentials.properties");
			prop.load(input);
			String recipients = "";
			recipients = System.getenv("MAILRECIPIENTS");
			if (recipients == null)
				recipients = prop.getProperty("recipients");

			if (prop.getProperty("SENDMAIL").equalsIgnoreCase("YES")) {
				String to[] = recipients.split(",");
				String user = prop.getProperty("email_UserName");
				String password = prop.getProperty("email_Password");
				Properties properties = System.getProperties();
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.starttls.enable", "false");
				properties.setProperty("mail.smtp.host", prop.getProperty("SMTP_HOST_NAME"));
				properties.put("mail.smtp.port", prop.getProperty("SMTP_PORT"));

				Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
				});

				// 2) compose message

				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(user));

				// TO List
				InternetAddress[] mailAddress_TO = new InternetAddress[to.length];
				for (int i = 0; i < to.length; i++) {
					mailAddress_TO[i] = new InternetAddress(to[i]);
				}
				message.addRecipients(Message.RecipientType.TO, mailAddress_TO);
				message.setSubject(prop.getProperty("subject") + ExecnType + buildnum);
				Date date = new Date();
				String DATE_FORMAT = "MM/dd/yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

				// 3) create MimeBodyPart object and set your message text
				MimeBodyPart messageBodyPart1 = new MimeBodyPart();

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				List<String> strLIST;
				strLIST = Files.readAllLines(Paths.get(ReportFolder + "email.html"), Charset.defaultCharset());
				System.out.println("Email body: " + strLIST);
				String strcont = "";
				for (int i = 0; i < strLIST.size(); i++) {
					strcont = strcont + strLIST.get(i);
				}
				messageBodyPart1.setContent(strcont, "text/html; charset=utf-8");
				// message.setContent(strcont, "text/html");
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart = new MimeBodyPart();
				// String filename = Extenthtml.replaceAll(".html", "-shareableReport.html");

				Thread.sleep(10000);
				DataSource source = new FileDataSource(ConsolidateReport);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName("ConsolidatedTestReport.html");
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);
				Transport.send(message);
				input.close();
				System.out.println("Email sent status: Mail Sent");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Map<String, String> GetMapfromTechOpsDataHandlerStringResponse(String str) {
		Map<String, String> OpMap = new HashMap<String, String>();
		String[] keyvals = str.split(",");
		for (int i = 0; i < keyvals.length; i++) {
			OpMap.put(keyvals[i].split("=")[0], keyvals[i].split("=")[1]);
		}
		return OpMap;
	}

//	public String wsRESTGetAction(String URI, String querystring)
//	{		  
//		// Specify the base URL to the RESTful web service
//		RestAssured.baseURI = URI;
//
//		// Get the RequestSpecification of the request that you want to sent
//		// to the server. The server is specified by the BaseURI that we have
//		// specified in the above step.
//		
////		http.protocol.content-charset=UTF-8, http.protocol.version=HTTP/1.1
//		RequestSpecification httpRequest = RestAssured.given();
//		
//		// Make a request to the server by specifying the method Type and the method URL.
//		// This will return the Response from the server. Store the response in a variable.
//		Response response = httpRequest.request(Method.GET, querystring);
//
//		// Now let us print the body of the message to see what response
//		// we have recieved from the server
//		String responseBody = response.getBody().asString();
//		return responseBody;
//	}

	public String wsGetNodeValueFromJSON(String JSONstr, String JSONpath) throws Exception {
		String Value = "" + com.jayway.jsonpath.JsonPath.read(JSONstr, JSONpath);
		logs(Value);
		return Value;
	}

	public int wsGetArrayNodeLengtheFromJSON(String JSONstr, String JSONpath) throws Exception {
		int Value = (int) com.jayway.jsonpath.JsonPath.read(JSONstr, JSONpath);
		logs(Value);
		return Value;
	}

	public HttpResponse wsHTTPclientGetAction(String URL, HashMap<String, String> headers, String querystring)
			throws Exception {
		HttpClient httpclient = getNewHttpClient();
		String url = URL + querystring;
		HttpGet hp = new HttpGet(url);
		Set<String> keys = headers.keySet();
		for (String key : keys) {
			hp.addHeader(key, headers.get(key));
		}
		HttpResponse response = httpclient.execute(hp);
		addLog(LogStatus.INFO, "WS Get Action",
				"URL:" + URL + " Query:" + querystring + " Status Code:" + response.getStatusLine().getStatusCode(),
				false);
		return response;
	}

	public HttpResponse wsHTTPclientPostAction(String URL, HashMap<String, String> headers, String JSONReqstr)
			throws Exception {
		HttpClient httpclient = getNewHttpClient();
		String url = URL;
		HttpPost hp = new HttpPost(url);
		Set<String> keys = headers.keySet();
		for (String key : keys) {
			hp.addHeader(key, headers.get(key));
		}
		hp.setEntity(new ByteArrayEntity(JSONReqstr.getBytes("UTF-8")));
		HttpResponse response = httpclient.execute(hp);
		addLog(LogStatus.INFO, "WS Post Action",
				"URL:" + URL + " Status Code:" + response.getStatusLine().getStatusCode(), false);
		return response;
	}

	public String wsGetJSONstringfromHttpResponse(HttpResponse response) throws Exception {
		String str = EntityUtils.toString(response.getEntity());
		logs(str);
		return str;
	}

	public HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
			HttpClient httpc = new DefaultHttpClient(ccm, params);
			System.out.println(httpc.getParams());
			return httpc;
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	public void writeDataIntoExcelUsingTreeMap(String sheetName, TreeMap<String, Object[]> excelTableData,
			String Opfilepath) throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet(sheetName);
		XSSFRow row;
		Set<String> keyid = excelTableData.keySet();
		int rowid = 0;
		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = excelTableData.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		FileOutputStream out = new FileOutputStream(new File(Opfilepath));
		workbook.write(out);
		out.close();
		logs(Opfilepath + " written successfully");
	}

	public void writeDataIntoExcelUsingArrayData(String sheetName, String[] arr, String Opfilepath) throws Exception {
		// each row should be of form Ex: "id;;empnum;;hyderabad"
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet(sheetName);
		XSSFRow row;
		int rowid = 0;
		for (String str : arr) {
			row = spreadsheet.createRow(rowid++);
			String[] cellvals = str.split(";;");
			int cellid = 0;
			for (String eachcellval : cellvals) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue(eachcellval);
			}
		}
		FileOutputStream out = new FileOutputStream(new File(Opfilepath));
		workbook.write(out);
		out.close();
		logs(Opfilepath + " written successfully");
	}

	public void wdWaitforElementToBeClickable(String element) {
		WebDriverWait wdwt = new WebDriverWait(driver, timeout);
		wdwt.until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
	}

	public boolean wdWaitAndVerifyElementFound(String element) {
		int i = 40;
		// waitforBAMWebToLoad();
		WebDriverWait wdwt = new WebDriverWait(driver, timeout);
		wdwt.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element)));
		while (!wdGetWebElement(element).isDisplayed() && i > 0) {
			try {
				Thread.sleep(500);
				System.out.println("Waiting for element to display...");
				i--;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.addLogofWebDriver(LogStatus.PASS, "WebDriver Wait and Verify Element found",
				"Verify existence of element:" + element, true);
		return true;
	}

	public String wdConvertDate(String dateSource, String currenPattern, String expectedPattern) throws ParseException {

		String convertedDate = new SimpleDateFormat(expectedPattern)
				.format(new SimpleDateFormat(currenPattern).parse(dateSource));

		return convertedDate;

	}

	public void acceptAlert() {
		// TODO Auto-generated method stub
		this.driver.switchTo().alert().accept();
	}

	public Response wsRestPut(String baseUrl, HashMap<String, String> headers, String RequestBody) {
		RestAssured.baseURI = baseUrl;
		RequestSpecification httpRequest = RestAssured.given();
		Set<String> keys = headers.keySet();
		for (String key : keys) {
			httpRequest.header(key, headers.get(key));
		}
		Response res = httpRequest.and().body(RequestBody).when().put().then().extract().response();
		return res;
	}

	public Response wsRestPost(String baseUrl, HashMap<String, String> headers, String RequestBody)
			throws URISyntaxException {
		RestAssured.baseURI = baseUrl;
		RequestSpecification httpRequest = RestAssured.given();
		Set<String> keys = headers.keySet();
		for (String key : keys) {
			httpRequest.header(key, headers.get(key));
		}
		Response res = httpRequest.and().body(RequestBody).when().post().then().extract().response();
		return res;
	}

	public Response wsRestPost(String baseUrl, HashMap<String, String> headers) throws URISyntaxException {
		RestAssured.baseURI = baseUrl;
		RequestSpecification httpRequest = RestAssured.given();
		Set<String> keys = headers.keySet();
		for (String key : keys) {
			httpRequest.header(key, headers.get(key));
		}
		Response res = httpRequest.when().get().then().extract().response();
		return res;
	}
	// =========================================================================================================//

	// TODO Pandian Custom methods
	public int wdRandomNumber(int min, int max) {
		int randomNum = (int) (Math.random() * (max - min + 1) + min);
		return randomNum;

	}

	// enter alpabets
	public String wdRandomAlphaString(int stringLength) {
		String randomAlphaString = "";
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; stringLength > i; i++) {
			Random r = new Random();
			char randomChar = alphabet.charAt(r.nextInt(alphabet.length()));
			randomAlphaString = randomAlphaString + randomChar;
		}
		return randomAlphaString;

	}

	// enter capital letters and numbers
	public String wdRandomAlphaNumericString(int stringLength) {
		String randomAlphaNumericString = "";
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321";
		for (int i = 0; stringLength > i; i++) {
			Random r = new Random();
			char randomChar = alphabet.charAt(r.nextInt(alphabet.length()));
			randomAlphaNumericString = randomAlphaNumericString + randomChar;
		}
		return randomAlphaNumericString;

	}

	// enter capital letters
	public String wdRandomCapitalAlphaString(int stringLength) {
		String randomAlphaString = "";
		String alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; stringLength > i; i++) {
			Random r = new Random();
			char randomChar = alphabet.charAt(r.nextInt(alphabet.length()));
			randomAlphaString = randomAlphaString + randomChar;
		}
		return randomAlphaString;

	}

	// mouse hover on webelement without scrolling
	public void wdMouseHoverElement(String xpath) {
	    WebElement element = driver.findElement(By.xpath(xpath));
	    Actions actions = new Actions(driver);
	    actions.moveToElement(element).perform();
	    String variableName = getVariableNameByXPath(xpath);
	    this.addLogofWebDriver(LogStatus.PASS, "Mouse Hover",
	            "Mouse hovered over element: \"" + variableName + "\" (" + xpath + ")", true);
	}

	// click on webelement without scrolling the page
	public void wdStaticClick(String xpath) {
	    WebElement element = driver.findElement(By.xpath(xpath));
	    Actions actions = new Actions(driver);
	    actions.moveToElement(element).perform();
	    element.click();
	    String variableName = getVariableNameByXPath(xpath);
	    this.addLogofWebDriver(LogStatus.PASS, "Static Click",
	            "Clicked on element: \"" + variableName + "\" (" + xpath + ")", true);
	}

	// scroll down to load infinite load record
	public void wdInfiniteScroll(String elementString, int positionToMove) throws InterruptedException {
		for (int i = 1; i <= positionToMove; i++) {
			String newElementString = elementString + "[" + i + "]";
			movetoElementforActions(newElementString);
		}

	}

	// get text from value attribute
	public String wdGetValue(String element) {
		wdWaitforElementToBeVisible(element);
		return driver.findElement(By.xpath(element)).getAttribute("value");
	}

	// to fetch text from web element
	public String wdFetchText(String element) {
		wdWaitforElementToBeVisible(element);
		return driver.findElement(By.xpath(element)).getText();
	}

	// to select random value form the drop down
	

	public String SelectRandomDropDownValue(String xpath) {
	    int elementCount = wdGetElementCount(xpath);
	    if (elementCount == 0) {
	        throw new NoSuchElementException("No elements found for XPath: " + xpath);
	    }
	    System.out.println("Number of values present in the dropdown: " + elementCount);
	    // Determine random index based on whether 'Any' or 'Select' is present
	    int randomIndex;
	    if (wdFetchText(xpath + "[1]").contains("Any") || wdFetchText(xpath + "[1]").contains("Select")) {
	        randomIndex = wdRandomNumber(2, elementCount); // Exclude first item if it’s a placeholder
	    } else {
	        randomIndex = wdRandomNumber(1, elementCount); // Include all items
	    }

	    // Construct XPath for the element to be selected
	    String elementToBeSelectedXPath = xpath + "[" + randomIndex + "]";
	    String selectedValue = wdFetchText(elementToBeSelectedXPath);
	    
	    // Print and log selected value
	    System.out.println("Selected value: " + selectedValue);
	    wdClick(elementToBeSelectedXPath);
	    
	    this.addLogofWebDriver(LogStatus.PASS, "DropDown Selection",
	            "Selected value: \"" + selectedValue + "\" from dropdown: " + xpath, true);
	    
	    return selectedValue;
	}

	public boolean wdverifyElementWithSpecifiedText(String text) {
		String element = "//*[contains(text(),'" + text + "')]";
		wdVerifyElementFound(element);
		this.addLogofWebDriver(LogStatus.PASS, "Element found", " with the TEXT " + text, true);
		return true;
	}

	public void wdverifyElementWithSpecifiedValue(String value) {
		String element = "//*[contains(@value,'" + value + "')]";
		wdVerifyElementFound(element);
		this.addLogofWebDriver(LogStatus.PASS, "Element found", " with the value " + value, true);
	}

	public void wdSwitchToNewOpenedWindow() {
		String mainwindow = driver.getWindowHandle();
		System.out.println(mainwindow);
		Set<String> windowHandles = driver.getWindowHandles();
		System.out.println(windowHandles);
		for (String window : windowHandles) {
			if (window != mainwindow) {
				driver.switchTo().window(mainwindow);
				System.out.println(driver.getTitle());
			}
		}
	}

	public void applyFluentWait(String element) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement element1 = driver.findElement(By.xpath(element));
		FluentWait wait2 = new FluentWait(driver);
		wait2.withTimeout(10, TimeUnit.SECONDS);
		wait2.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait2.ignoring(NoSuchElementException.class);
		wait2.until(ExpectedConditions.elementToBeClickable(element1));
	}

	public String GetXpathFromProps(String key) throws FileNotFoundException, IOException {
		String value = "";
		Properties prop = new Properties();
		prop.load(new FileInputStream(
				new java.io.File(System.getProperty("user.dir") + "/TestInputs/" + "xpath.properties")));
		value = "" + prop.get(key);
		return value;
	}

	public String GetTextFromProps(String flag) throws FileNotFoundException, IOException {
		String value = "";
		Properties prop = new Properties();
		prop.load(new FileInputStream(
				new java.io.File(System.getProperty("user.dir") + "/TestInputs/" + "note and warning.properties")));
		value = "" + prop.get(flag);
		return value;
	}

	public void zoomOut() throws AWTException {
		Robot robot = new Robot();
		System.out.println("About to zoom out");
		for (int i = 0; i < 4; i++) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
	}
	// =========================================================================================================//

	// TODO Rajesh Custom methods
	public void wdBrowserBack() throws FileNotFoundException, IOException {
		driver.navigate().back();
	}


}


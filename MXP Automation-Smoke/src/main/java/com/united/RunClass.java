package com.united;

import org.testng.annotations.Test;
import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.TestNG;
import org.testng.TestNGUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.internal.TestNGProperty;
import org.testng.internal.thread.TestNGThread;
//import org.testng.internal.thread.TestNGThreadFactory;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public class RunClass {
	public static boolean testRunFlag = true;
	static String configsheetname = "";
	String Appname = "";
	static java.sql.Connection tcstatusconnection = null;
	String Devicename = "";
	static String projID = "";
	static String projName = "";
	String Dockerhuburl = "";
	String browser = "";
	String webresolution = "";
	String object_Repository="";
	String subsuitgeroup = "";
	String environment = "";
	String runsubsuitegroup = "";
	String testsuitename = "";
	LocalDateTime ExecnStarttime = null;
	String Reportfolder = "";
	String ReportFolderwithtime = "";
	int reporterflag = 0;
	ExtReportFW fw2;
	WebServiceCalls wsCalls = new WebServiceCalls();
	static int datathreadcount = 0;
	static int TotalTCToBeExecuted = 0;
	static int PassTCCount = 0;
	static int FailTCCount = 0;
	static HashMap<String, Integer> TCAvgExecutiontimings = new HashMap<String, Integer>();
	static int totalexecntime = 0;
	static int curexecntime = 0;
	static int actualcurexecntime = 0;
	static int scriptrelatedfailurescount = 0;
	static int databaserelatedfailurescount = 0;
	static int apirelatedfailurescount = 0;
	static int applicationrelatedfailurescount = 0;
	static int networkrelatedfailurescount = 0;
	static int loggingreportingrelatedfailurescount = 0;
	static int testdatarelatedfailurescount = 0;
	static int otherfailurescount = 0;
	boolean typeofFailureCount = false;
	static String simpleName = "";
	static String typeofFailure = "";
	static File logfile = null;
	static PrintStream stream = null;
	static PrintStream console = null;
	static String execnresultsDB = "";
	com.aventstack.extentreports.ExtentReports ExtRep;
	static Map<String, String> TCSpecificData = new HashMap<String, String>();
	FailureAnalysys data = new FailureAnalysys();

	@BeforeSuite()
	@Parameters({ "projid", "projname" })
	public void setSuiteParams(String projid, String Projname) throws Exception {
		this.projID = System.getenv("projid");
		if (this.projID == null)
			this.projID = projid;
		this.projName = Projname;
		System.out.println("projID: " + this.projID);
		System.out.println("projName: " + this.projName);
		this.ExecnStarttime = LocalDateTime.now();
	}

	@BeforeTest
	@Parameters({ "TestCaseSheet", "AppName", "DeviceName", "dockerhubURL", "browser", "webresolution", "environment",
			"runsubsuitegroup", "reportfolderpath", "execnresultsDB" ,"object_Repository"})
	public void setTestParams(String TestCaseSheet, String AppName, String DeviceName, String dockhuburl,
			String Browser, String Webresolution, String Environment, String Runsubsuitegroup, String reportfolderpath,
			String locexecnresultsDB,String Object_Repository, final ITestContext testContext) throws IOException {
		configsheetname = System.getenv("CONFIGFILE");
		if (configsheetname == null)
			configsheetname = TestCaseSheet;
		System.out.println("Test Inputs_TC File Name: " + configsheetname);
		Appname = AppName;
		Devicename = DeviceName;
		Dockerhuburl = dockhuburl;
		browser = Browser;
		webresolution = Webresolution;
		environment = Environment;
		runsubsuitegroup = Runsubsuitegroup.toLowerCase();
		testsuitename = testContext.getName();
		datathreadcount = testContext.getSuite().getXmlSuite().getDataProviderThreadCount();
		ExtRep = new com.aventstack.extentreports.ExtentReports();
		Reportfolder = reportfolderpath + "\\TestReports\\" + testsuitename
				+ ("__" + new Date()).replaceAll(" ", "").replaceAll(":", "") + "\\";
		ReportFolderwithtime = Reportfolder + ("" + new Date()).replaceAll(" ", "").replaceAll(":", "")
				+ "_Consolidated.html";
		execnresultsDB = locexecnresultsDB;
		object_Repository= Object_Repository;
	}

	@DataProvider(name = "dp", parallel = true)
	public Object[] dp1() throws Exception {
		Files.createDirectories(Paths.get(Reportfolder + "\\Images"));
		Files.write(Paths.get(Reportfolder + "logs.txt"), "".getBytes(), StandardOpenOption.CREATE);
		logfile = new File(Reportfolder + "logs.txt");
		stream = new PrintStream(RunClass.logfile);
		Properties prop = new Properties();
		prop.load(new FileInputStream(
				new File(System.getProperty("user.dir") + "\\TestInputs\\" + environment + "_Credentials.properties")));
		String repstatsdays = prop.getProperty("REPORTSTATS_DAYS");
		Fillo TCSheet = new Fillo();
		Connection TCSheetConnection = TCSheet
				.getConnection(System.getProperty("user.dir") + "\\TestInputs\\" + configsheetname);
		Recordset rsScenarios = TCSheetConnection.executeQuery("Select * from Scenarios where Execution_Flag='Y'");
		List<String> TCConfigs = new ArrayList<String>();
		String url = "jdbc:mysql://" + execnresultsDB + "/projectsdetails?useSSL=false";
		String username = "nikhil";
		String password = "nikhil";
		System.out.println("Connecting to MySQL database...");
		if (tcstatusconnection == null || tcstatusconnection.isClosed()) {
			try {
				tcstatusconnection = DriverManager.getConnection(url, username, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//**

				Map<String, Integer> avgExecutionTimes = new HashMap<>();
				Set<String> includedTCIDs = new HashSet<>();
				Set<String> excludedTCIDs = new HashSet<>();
				List<String> expandedSubsuiteGroups = new ArrayList<>();
				String[] parts = runsubsuitegroup.split(",\\(");
				String includedPart = parts[0].trim();
				String excludedPart = parts.length > 1 ? parts[1].replace(")", "").trim() : "";
				if (!includedPart.equalsIgnoreCase("NA")) {
					String[] subsuitegrps = includedPart.toLowerCase().split(",");
					for (String subsuitegrp : subsuitegrps) {
						subsuitegrp = subsuitegrp.trim(); // Clean leading/trailing spaces
						if (subsuitegrp.contains("~")) {
							String[] rangeParts = subsuitegrp.split("~");
							if (rangeParts.length == 2) {
								String start = rangeParts[0].trim();
								String end = rangeParts[1].trim();
								int startNum = Integer.parseInt(start.replaceAll("[^0-9]", ""));
								int endNum = Integer.parseInt(end.replaceAll("[^0-9]", ""));
								String prefix = start.replaceAll("[0-9]", ""); // Extract non-numeric prefix
								for (int i = startNum; i <= endNum; i++) {
									includedTCIDs.add(prefix + i);
								}
							}
						} else {
							includedTCIDs.add(subsuitegrp);
						}
					}
				}
				if (!excludedPart.equalsIgnoreCase("NA")) {
					String[] excludedGrps = excludedPart.toLowerCase().split(",");
					for (String excludedGrp : excludedGrps) {
						excludedGrp = excludedGrp.trim();
						if (excludedGrp.contains("~")) {
							String[] rangeParts = excludedGrp.split("~");
							if (rangeParts.length == 2) {
								String start = rangeParts[0].trim();
								String end = rangeParts[1].trim();
								int startNum = Integer.parseInt(start.replaceAll("[^0-9]", ""));
								int endNum = Integer.parseInt(end.replaceAll("[^0-9]", ""));
								String prefix = start.replaceAll("[0-9]", ""); // Extract non-numeric prefix
								for (int i = startNum; i <= endNum; i++) {
									excludedTCIDs.add(prefix + i);
								}
							}
						} else {
							excludedTCIDs.add(excludedGrp);
						}
					}
				}
				includedTCIDs.removeAll(excludedTCIDs);
				expandedSubsuiteGroups = new ArrayList<>(includedTCIDs);
				List<String> tcIdsToQuery = new ArrayList<>();
				while (rsScenarios.hasNext()) {
					rsScenarios.next();
					boolean runthisTC = true;
					String TCID = rsScenarios.getField("TCID");
					if (!expandedSubsuiteGroups.isEmpty()) {
						runthisTC = false;
						if (expandedSubsuiteGroups.contains(TCID.toLowerCase())) {
							runthisTC = true;
						}
					} else if (runsubsuitegroup.equalsIgnoreCase("NA")) {
						runthisTC = true;
					} else if (includedTCIDs.isEmpty() && !excludedTCIDs.isEmpty()) {
						if (includedPart.equalsIgnoreCase("NA") && excludedTCIDs.contains(TCID.toLowerCase())) {
							runthisTC = false;
						}
					}
					if (runthisTC) {
						tcIdsToQuery.add(TCID);
						String ADO_TCID = rsScenarios.getField("ADO_TCID");
						String TCNAME = rsScenarios.getField("TCNAME");
						String Module = rsScenarios.getField("MODULE");
						String Keywordmethod = rsScenarios.getField("KEYWORD");
						String TCDES = rsScenarios.getField("DESCRIPTION") + " ";
						TCConfigs.add(TCID + ";;;" + ADO_TCID + ";;;" + TCNAME + ";;;" + Module + ";;;" + Keywordmethod + ";;;"
								+ TCDES);
					}
				}
				if (!tcIdsToQuery.isEmpty()) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < tcIdsToQuery.size(); i++) {
						if (i > 0)
							sb.append(", ");
						sb.append("'").append(tcIdsToQuery.get(i)).append("'");
					}
					String avgQuery = "SELECT tcnum, AVG(executiontime) as avgexe FROM projectsdetails.tcexecution_status "
							+ "WHERE projid='" + projID + "' AND tcnum IN (" + sb.toString() + ") " + "GROUP BY tcnum";
					ResultSet rsAvgTimes = RunClass.tcstatusconnection.prepareStatement(avgQuery).executeQuery();
					while (rsAvgTimes.next()) {
						String tcNum = rsAvgTimes.getString("tcnum");
						int avgExeTime = rsAvgTimes.getInt("avgexe");
						avgExecutionTimes.put(tcNum, avgExeTime);
					}
				}
				for (String TCID : tcIdsToQuery) {
					int avgexetime = avgExecutionTimes.getOrDefault(TCID, 0);
					RunClass.TCAvgExecutiontimings.put(TCID, avgexetime);
					RunClass.totalexecntime += avgexetime;
				}

		//**

		int temp = RunClass.totalexecntime;
		String expectingminNsec = "";
		if ((temp / 60) > 0) {
			expectingminNsec = "" + (temp / 60) + " minutes ";
		}
		expectingminNsec = expectingminNsec + (temp % 60) + " seconds";
		System.out.println("Expected time to complete: " + expectingminNsec);
		String htmlcontentstr = "<html><header><div @id='blackbg' style=\"background-color:black;\"><img src=\"https://www.united.com/8ea0b8c6d5c3fab39bb81ad99f8f0fc8.svg\" style=\"background-color:black;float: left; margin-left: 15px;\" width=\"145\" height=\"28\"></img><img align=\"right\"  src=\"https://www.valuelabs.com/wp-content/themes/vl/images/valuelabs-logo.svg\" width=\"145\" height=\"28\" style=\"background-color:black;float: right; margin-right: 15px;\"></img><h1 align=\"center\"  style=\"color:white;\">"
				+ RunClass.projName
				+ " Automation Test Report</h1></div></header><head><meta http-equiv=\"refresh\" content=\"5\" ></head><body>"
				+ "<table id=\"progress\" style=\"width:100%\" rules=none width=\"50\" bgcolor=\"#F0F0F0\">\r\n"
				+ "		<h id=\"progesstext\">Expected time to complete: " + expectingminNsec + "<h>\r\n"
				+ "	</table>" + "<progress id=\"progressbar\" style=\"width:100%\" value=\"0\" max=\"100\"> </progress>"
				+ "<table style=\"width:100%\" border=\"1\" width=\"50\" bgcolor=\"#F0F0F0\"><tr bgcolor=\"#000000\"><th align=\"center\" width=\"5%\" style=\"color:white; font-size:12pt;\">TCNo.</th><th align=\"center\" width=\"70%\" style=\"color:white; font-size:12pt;\">TC Name</th><th align=\"center\" width=\"10%\" style=\"color:white; font-size:12pt;\">TC Result</th><th align=\"center\" width=\"10%\" style=\"color:white; font-size:12pt;\">Reason for Failure</th>\r\n"
				+ "<th align=\"center\" width=\"10%\" style=\"color:white; font-size:12pt;\">Execution Time in Mins</th>\r\n"
				+ "<th align=\"center\" width=\"10%\" style=\"color:white; font-size:12pt;\">Avg Execution Time in Mins</th>\r\n"
				+ "<th align=\"center\" width=\"10%\" style=\"color:white; font-size:12pt;\">Last " + repstatsdays
				+ " Day Pass %</th>\r\n"
				+ "<th align=\"center\" width=\"10%\" style=\"color:white; font-size:12pt;\">Last " + repstatsdays
				+ " Day Run Results</th></tr>";
		if (!new File(Reportfolder + "email.html").exists())
			Files.write(Paths.get(Reportfolder + "email.html"), htmlcontentstr.getBytes(),
					StandardOpenOption.CREATE_NEW);
		System.out.println("TCConfigs: " + TCConfigs);
		Object[][] configs = new Object[TCConfigs.size()][6];
		for (int i = 0; i < TCConfigs.size(); i++) {
			configs[i][0] = TCConfigs.get(i).split(";;;")[0];
			// System.out.println("configs["+i+"][1]: TCID: "+configs[i][0]);
			configs[i][1] = TCConfigs.get(i).split(";;;")[1];
			// System.out.println("configs["+i+"][1]: ADO_TCID: "+configs[i][1]);
			configs[i][2] = TCConfigs.get(i).split(";;;")[2];
			// System.out.println("configs["+i+"][2]: TCNAME: "+configs[i][2]);
			configs[i][3] = TCConfigs.get(i).split(";;;")[3];
			// System.out.println("configs["+i+"][3]: Module: "+configs[i][3]);
			configs[i][4] = TCConfigs.get(i).split(";;;")[4];
			// System.out.println("configs["+i+"][4]: Keyword: "+configs[i][4]);
			configs[i][5] = TCConfigs.get(i).split(";;;")[5];
			// System.out.println("configs["+i+"][5]: DESCRIPTION: "+configs[i][5]);
		}
		TotalTCToBeExecuted = TCConfigs.size();
		System.out.println("Test Cases to be executed: " + Integer.toString(TotalTCToBeExecuted));
		return configs;
	};

//	@Parameters({"TestCaseSheet","AppName","DeviceName","browser","webresolution", "environment", "runsubsuitegroup"})
	// public void MainTest(String TestCaseSheet,String AppName,String DeviceName,
	// String Browser,String Webresolution, String Environment, String
	// Runsubsuitegroup, final ITestContext testContext ) throws IOException,
	// FilloException, SQLException
	@Test(dataProvider = "dp")
	public void testInitiation(String TCID, String ADO_TCID, String TCNAME, String Module, String Keywordmethod,
			String TCDES) {
		String exceptioncaught = "";
		String exceptionType = "";
		boolean execptcought = false;
		try {

			Fillo ORsheet = new Fillo();

			Connection ORsheetConnection = ORsheet
					.getConnection(System.getProperty("user.dir")+"\\Object Repository\\"+object_Repository);
			Recordset rs_stor = ORsheetConnection.executeQuery("Select * from ST_repo");
			Recordset rs_wdor = ORsheetConnection.executeQuery("Select * from WD_repo");
			Map<String, String> wdor = ORreturn(rs_wdor, "Xpath");
			Map<String, String> stiosor = ORreturn(rs_stor, "ios");
			rs_stor = ORsheetConnection.executeQuery("Select * from ST_repo");
			Map<String, String> standroidor = ORreturn(rs_stor, "android");
			ExtReportFW fw = new ExtReportFW(testsuitename, configsheetname, Dockerhuburl, browser, webresolution,
					environment, Reportfolder, ExtRep, wdor);
//    	fw.ReportTest(TCDES);
			ExtentSparkReporter ExtReporters;
			if (reporterflag == 0) {
				ExtReporters = new ExtentSparkReporter(new File(Reportfolder + "ConsolidatedReport"));
				reporterflag++;
			} else {
				ExtReporters = new ExtentSparkReporter(new File(""));
			}
			fw.InitExtReport(ReportFolderwithtime, ExtReporters);
//        ExtentReports er = new ExtentReports("", true);

			KeyWords kw = new KeyWords(fw, wsCalls, wdor, stiosor, standroidor, Devicename, Appname);
			if (wsCalls.ADO_integration && wsCalls.ADO_createNewTestRun && testRunFlag) {
				wsCalls.createTestRun_ADO();
				testRunFlag = false;
			}
			Fillo TCSheet = new Fillo();
			Connection TCSheetConnection = TCSheet
					.getConnection(System.getProperty("user.dir") + "\\TestInputs\\" + configsheetname);
			Recordset rsScenarioParams = TCSheetConnection
					.executeQuery("Select * from TCPARAMETERS where PARAMS_NEEDED='Y'");
			HashMap<String, String> Params = new HashMap<String, String>();
			while (rsScenarioParams.hasNext()) {
				rsScenarioParams.next();
				Params.put(rsScenarioParams.getField("TCID"), rsScenarioParams.getField("PARAMETERS"));
			}
			String url = "jdbc:mysql://" + execnresultsDB + "/projectsdetails?useSSL=false";
			String username = "nikhil";
			String password = "nikhil";
			System.out.println("Connecting to MySQL database...");
			if (tcstatusconnection == null || tcstatusconnection.isClosed()) {
				try {
					tcstatusconnection = DriverManager.getConnection(url, username, password);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			System.out.println("TCID: " + TCID + "   " + "ADO_TCID: " + ADO_TCID + "   " + "TCName: " + TCNAME);
			try {
				HashMap<String, String> TCParams = new HashMap<String, String>();
				fw.startTest(TCID + "__" + TCNAME, TCDES, Module);
				try {
					if (!Params.getOrDefault(TCID, "none").equalsIgnoreCase("none")) {
						String[] tempparamms = Params.getOrDefault(TCID, "none").split(";");
						for (int i = 0; i < tempparamms.length; i++) {
							TCParams.put(tempparamms[i].split("--")[0].toLowerCase(), tempparamms[i].split("--")[1]);
						}
					}
				} catch (Exception e) {
					throw new Exception("TC Parameters wrongly configured for:" + TCID);
				}
				int KWruntimes = Integer.parseInt(TCParams.getOrDefault("run", "1"));
				while (KWruntimes > 0) {
					exceptioncaught = "";
					execptcought = false;
					kw.ExecuteKeyWordMethod(Keywordmethod, TCParams);
					KWruntimes--;
				}
				fw.endTest();
			} catch (Exception e) {
				e.printStackTrace();
				simpleName = e.getClass().getName();
				String typeofFailureName = typeofFailure();
				typeofFailureCount = typeofFailureCount(typeofFailureName);
				StackTraceElement[] exceptionstack = e.getStackTrace();
				exceptioncaught = e.getMessage().split("\\n")[0];
				exceptionType = e.getMessage().split(":")[0];
				String tempexception = "";
				for (int i = 0; i < exceptionstack.length; i++) {
					tempexception = tempexception + "class:" + exceptionstack[i].getClassName() + " Line:"
							+ exceptionstack[i].getLineNumber() + "\n";
					if ((("" + exceptionstack[i].getClassName() + " Line:" + exceptionstack[i].getLineNumber())
							.contains(".WebDriverMethods")
							|| ("" + exceptionstack[i].getClassName() + " Method:" + exceptionstack[i].getMethodName()
									+ " Line:" + exceptionstack[i].getLineNumber()).contains(".SeeTestMethods")
							|| ("" + exceptionstack[i].getClassName() + " Method:" + exceptionstack[i].getMethodName()
									+ " Line:" + exceptionstack[i].getLineNumber()).contains(".KeyWords"))
							&& !execptcought) {
						execptcought = true;
						exceptioncaught = exceptioncaught + ":::" + ("" + exceptionstack[i].getClassName() + " Method:"
								+ exceptionstack[i].getMethodName() + " Line:" + exceptionstack[i].getLineNumber());
					}
				}
				// fw.addLog(LogStatus.FAIL, "Exception", e.getMessage()+tempexception, true);
				fw.addLog(LogStatus.FAIL, "Exception", exceptioncaught, true);

				if (e.toString().contains("com.experitest.client")) {
					String temp = "";
					StackTraceElement[] str = e.getStackTrace();
					for (int i = 0; i < str.length; i++)
						temp = str[i].toString();
					fw.addLogofSeeTest(LogStatus.FAIL, "Exception", temp, true);
				}

				else if (e.toString().contains("selenium") || e.toString().contains("Selenium")) {
					System.out.println("e message testing" + e.toString());
					String temp = "";
					StackTraceElement[] str = e.getStackTrace();
					for (int i = 0; i < str.length; i++)
						temp = str[i].toString();
					try {
						fw.addLogofWebDriver(LogStatus.FAIL, "Exception", temp, true);
					} catch (Exception e1) {
						fw.addLog(LogStatus.FAIL, "Exception", temp, true);
					}
				} else {
					String temp = "";
					StackTraceElement[] str = e.getStackTrace();
					for (int i = 0; i < str.length; i++)
						temp = str[i].toString();
					fw.addLog(LogStatus.FAIL, "Exception", temp, true);
				}

				if (e.toString().contains("")) {
					if (e.toString().contains("selenium") || e.toString().contains("Selenium")) {
						if (fw.driver.getPageSource().contains(
								"A problem has occurred.  Your session may have expired requiring you to log in again.  Please ")) {
							fw.failurereason = fw.failurereason + ":::APP page loading issue";
						}
					} else if (e.toString().contains("Timed out after 60 seconds")) {
						String str = e.toString();
						str = str.substring(str.indexOf("xpath"), str.indexOf("Build info")).trim();
						fw.failurereason = fw.failurereason + ":::Element Not Found - " + str;
					} else if (e.toString().contains(" no such element: Unable to locate element:")) {
						String str = e.toString();
						str = str.substring(str.indexOf("xpath"), str.indexOf("Build info")).trim();
						fw.failurereason = fw.failurereason + ":::Element Not Found - " + str;
					}
					fw.failurereason = exceptioncaught + ":::" + fw.failurereason;
				}

				fw.endTest();
			}
			try {
				System.out.println("Connecting to MySQL database...");
				if (tcstatusconnection == null || tcstatusconnection.isClosed()) {
					try {
						tcstatusconnection = DriverManager.getConnection(url, username, password);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println("Test Execcution Duration: " + fw.ET2.getTest().getRunDuration());
				String exetime[] = fw.ET2.getTest().getRunDuration().split(" ");
				int hrs = Integer.parseInt(exetime[0].replaceAll("h", "").replaceAll("H", ""));
				int mins = Integer.parseInt(exetime[1].replaceAll("m", "").replaceAll("M", ""));
				int secs = Integer.parseInt(exetime[2].split("s")[0]);
				int exeseconds = (hrs * 60 * 60) + (mins * 60) + secs;

				System.out.println("Test Execcution Duration in Seconds: " + exeseconds);

				wsCalls.ADO_errorMessage = exceptioncaught;
				System.out.println("ADO_errorMessage: " + wsCalls.ADO_errorMessage);

				String tcResultStatus = fw.ET2.getTest().getStatus().toString();
				System.out.println("tcResultStatus: " + tcResultStatus);

				if (tcResultStatus.contains("pass")) {
					RunClass.PassTCCount = RunClass.PassTCCount + 1;
				}
				if (tcResultStatus.contains("fail")) {
					RunClass.FailTCCount = RunClass.FailTCCount + 1;
				}

				if (wsCalls.ADO_integration) {
					wsCalls.ADO_lastResultId = wsCalls.getLastResultIdByADO_TCID(ADO_TCID);
					wsCalls.updateTestResult_ADO(tcResultStatus);
				}

				String str = "INSERT INTO tcexecution_status(projid, tcnum, tcname, status, entrytime, executiontime, ExceptionCaught) VALUES('"
						+ projID + "', '" + TCID + "', '" + TCNAME + "', '" + fw.ET2.getTest().getStatus().toString()
						+ "', now(), " + exeseconds + ",'" + (exceptioncaught.replaceAll("'", "\"")) + "')";
				System.out.println("dbQuery: " + str);
				tcstatusconnection.createStatement().executeUpdate(str);
			}

			catch (Exception e) {
				e.printStackTrace();
				fw.addLog(LogStatus.WARNING, "Track", "Failed to update status into Execution Status DB", false);
			}

			fw2 = fw;
			ExtRep.flush();
			fw.flushExtRep();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterTest
	public void afterTest() throws Exception {
		if (wsCalls.ADO_integration) {
			if ((PassTCCount + FailTCCount) >= wsCalls.tcCount) {
				System.out.println("************All test cases in Test Run got executed ***********");
				wsCalls.updateTestRun_ADO();
			} else {
				System.out.println(
						"*********Not all test cases in Test Run executed, test run status is In Progress, NOT Completed ************");
			}
		}

		fw2.overallExecutiondetailsinEMAIL(PassTCCount, FailTCCount);
		ExtReportFW.sendEmailReport(environment, Reportfolder,
				Reportfolder + "ConsolidatedReport" + "//ConsolidatedReport.html");
		Files.delete(Paths.get(Reportfolder + "ConsolidatedReport" + "//Index.html"));
		if (Boolean.parseBoolean(fw2.GetValueFromCredentialProps("AutomationDashboardDBIntegration"))) {
			fw2.SendExecutionDetailstoDashBoard(ExecnStarttime);
		}
		tcstatusconnection.close();
	}

	public Map<String, String> ORreturn(Recordset rs, String colname) throws FilloException {
		Map<String, String> or = new HashMap<String, String>();
		while (rs.hasNext()) {
			rs.next();
			or.put(rs.getField("objectName"), rs.getField(colname));
		}
		return or;
	}

	public String typeofFailure() {
		if (data.sqlData().contains(simpleName)) {
			typeofFailure = "databaseRelatedFailure";
		} else if (data.scriptData().contains(simpleName)) {
			typeofFailure = "scriptRelatedFailure";
		} else if (data.sqlData().contains(simpleName)) {
			typeofFailure = "apiRelatedfailure";
		} else if (data.scriptData().contains(simpleName)) {
			typeofFailure = "testDataRelatedFailure";
		} else if (data.sqlData().contains(simpleName)) {
			typeofFailure = "applicationRelatedFailure";
		} else if (data.scriptData().contains(simpleName)) {
			typeofFailure = "loggingReportingRelatedFailure";
		} else if (data.sqlData().contains(simpleName)) {
			typeofFailure = "networkRelatedfailure";
		} else {
			typeofFailure = "otherFailure";
		}

		return typeofFailure;

	}

	public boolean typeofFailureCount(String typeofFailureName) {

		switch (typeofFailureName) {
		case "databaseRelatedFailure":
			databaserelatedfailurescount++;
			break;
		case "scriptRelatedFailure":
			scriptrelatedfailurescount++;
			break;
		case "apiRelatedfailure":
			apirelatedfailurescount++;
			break;
		case "applicationRelatedFailure":
			applicationrelatedfailurescount++;
			break;
		case "loggingReportingRelatedFailure":
			loggingreportingrelatedfailurescount++;
			break;
		case "networkRelatedfailure":
			networkrelatedfailurescount++;
			break;
		case "testDataRelatedFailure":
			testdatarelatedfailurescount++;
			break;
		default:
			otherfailurescount++;
		}

		return true;

	}

}

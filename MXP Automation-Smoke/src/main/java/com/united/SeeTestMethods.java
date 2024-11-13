package com.united;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.Logs;

import com.beust.jcommander.Parameter;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.experitest.client.Client;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;



public class SeeTestMethods extends WebServiceCalls{

	Map<String, String> wdor;
	Map<String, String> stiosor;
	Map<String, String> standroidor;
	ExtReportFW fw;
	String AppName = "";
	String DeviceName = "";
	String firstlogin = "";
	String Nextlogin = "";
	String way= "";
	String zone = "NATIVE";
	int index = 0, clickCount = 1;
	public SeeTestMethods(ExtReportFW fw,Map<String, String> stiosor, Map<String, String> standroidor, Map<String, String> wdor, String DeviceName, String AppName) {
		// TODO Auto-generated constructor stub
		this.standroidor = standroidor;
		this.stiosor = stiosor;
		this.wdor = wdor;
		this.fw = fw;
		super.fw = fw;
		this.DeviceName = DeviceName;
		this.AppName = AppName;
	}
	
	public void SeeTestMethodDemo() throws Exception
	{
		
		
		fw.stInitSeeTestClient(DeviceName);
//		fw.client.install("cloud:"+AppName, false);
//		fw.client.launch(AppName, true, true);
//		fw.stClick(zone, AppName, index, clickCount);
		fw.client.launch("safari:http://google.com", true, false);	    
	    fw.stElementSendText("WEB", "name=q", 0, "experitest");
	    fw.stClick("WEB", "name=q", 0, 1);
	    fw.client.sendText("{Enter}");
		
		fw.addLog(LogStatus.PASS, "SeeTest", "Hi This is from SeeTestMethods.java", false);
	}

	public void INSTALLIPAFromJFROGINSTALL() throws Exception {
		// TODO Auto-generated method stub
		String finalbuildnum = "";
		try {
			String jsonreq = "{\r\n"
					+ "    \"type\": \""+fw.GetValueFromCredentialProps("JFROGtype")+"\",\r\n"
					+ "    \"repoType\": \""+fw.GetValueFromCredentialProps("JFROGrepoType")+"\",\r\n"
					+ "    \"repoKey\": \""+fw.GetValueFromCredentialProps("JFROGrepoKey")+"\",\r\n"
					+ "    \"path\": \""+fw.GetValueFromCredentialProps("JFROGpath")+"\",\r\n"
					+ "    \"text\": \""+fw.GetValueFromCredentialProps("JFROGtext")+"\",\r\n"
					+ "    \"trashcan\": false\r\n"
					+ "}";
			
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Server", "Artifactory/6.2.0");
			headers.put("X-Artifactory-Id", "eadd1bbb41f6dbf7568016915b31bb758a4f80b0");
			headers.put("X-Artifactory-Node-Id", "vcld14gpcnjap15.global.ual.com");
			headers.put("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");
			headers.put("Content-Type", "application/json");
			fw.addLog(LogStatus.PASS, " jnefijdw", "fed", false);
			Response resp = fw.wsRestPost(fw.GetValueFromCredentialProps("JFROGENDPTURL"), headers, jsonreq);
			String jsonres = resp.asString();			
			jsonres = fw.wsGetNodeValueFromJSON(jsonres, "$.*.text");
			String[] buildnums = jsonres.split(",");
			finalbuildnum = buildnums[buildnums.length-1].replaceAll("\"", "").replaceAll("]", "");
			System.out.println(finalbuildnum);
			
			String downloadlink = fw.GetValueFromCredentialProps("BuildDownloadURLTemplate").replaceAll("TEMPBUILDNUM", finalbuildnum);
			fw.logs(downloadlink);
			fw.stInitSeeTestClient(DeviceName);
			fw.client.install(downloadlink, true,false);
			fw.client.launch(AppName, true, true);
			if(fw.client.isElementFound(zone, "xpath=//*[@text='Don't Allow' and @enabled='true']"))
				fw.stClick(zone, "xpath=//*[@text='Don't Allow' and @enabled='true']", index, clickCount);
			fw.addLogofSeeTest(LogStatus.PASS, "Installation of Build", "Build:"+finalbuildnum+" installed and launched successfully", true);
		}
		catch(Exception e)
		{
			fw.addLogofSeeTest(LogStatus.FAIL, "Installation of Build", "Failed to install or launch Build:"+finalbuildnum, false);	
		}
	}
	
}





package com.united;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import com.relevantcodes.extentreports.LogStatus;

import WSKeys.KeyWordsLocal;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class WebServiceCalls {
	
	static Proxy proxyServerOnLocalNetwork = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("gateway2ndc.ual.com", 80));
	ExtReportFW fw;
	Map<String, String> testCaseMap = new HashMap<String, String>();
	public static int tcCount=0;
	public static Boolean ADO_integration=true;
	public static Boolean ADO_createNewTestRun=true;
	public static String ADO_baseUrl="";
	public static String ADO_username="";
	public static String ADO_accessToken="";
	public static String ADO_planId="";
	public static String ADO_suiteId="";
	public static String ADO_suiteName="";
	public static String ADO_pointIds="";
	public static String ADO_testRunId="";
	public static String ADO_lastResultId="";
	
	public static String ADO_errorMessage="";
	public static String ADO_testResultStatus="";


public WebServiceCalls(ExtReportFW fw) throws FileNotFoundException, IOException {
		
		this.fw = fw;
		ADO_integration=Boolean.parseBoolean(fw.GetValueFromCredentialProps("ADO_integration"));
		ADO_createNewTestRun=Boolean.parseBoolean(fw.GetValueFromCredentialProps("ADO_createNewTestRun"));
		ADO_baseUrl=fw.GetValueFromCredentialProps("ADO_baseUrl");
		ADO_username=fw.GetValueFromCredentialProps("ADO_username");
		ADO_accessToken=fw.GetValueFromCredentialProps("ADO_password");
		ADO_planId=fw.GetValueFromCredentialProps("ADO_planId");
		ADO_suiteId=fw.GetValueFromCredentialProps("ADO_suiteId");
		ADO_suiteName=fw.GetValueFromCredentialProps("ADO_suiteName");
		
		System.out.println("ADO_integration: "+ ADO_integration);
		System.out.println("ADO_createNewTestRun: "+ ADO_createNewTestRun);
		System.out.println("ADO_baseUrl: "+ ADO_baseUrl);
		System.out.println("ADO_username: "+ ADO_username);
		System.out.println("ADO_accessToken: "+ ADO_accessToken);
		System.out.println("ADO_planId: "+ ADO_planId);
		System.out.println("ADO_suiteId: "+ ADO_suiteId);
		System.out.println("ADO_suiteName: "+ ADO_suiteName);
		System.out.println("ADO_lastResultId: "+ ADO_lastResultId);
		
		if(!ADO_createNewTestRun) 
		{
			ADO_testRunId=fw.GetValueFromCredentialProps("ADO_testRunId");
			System.out.println("ADO_testRunId: "+ ADO_testRunId);
		}
}

public WebServiceCalls() {
	// TODO Auto-generated constructor stub
}

private static String createBasicAuthHeader(String ADO_username, String ADO_accessToken) {
    String credentials = ADO_username + ":" + ADO_accessToken;
    byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
    String base64Credentials = Base64.getEncoder().encodeToString(credentialsBytes);
    return "Basic " + base64Credentials;
}

private static String sendPostRequest(String url, String basicAuthHeader, String payload) throws IOException {
    
	URL urlObject = new URL(url);
    HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection(proxyServerOnLocalNetwork);

    // Set up the request method and headers
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Authorization", basicAuthHeader);
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setDoOutput(true);

    // Send the payload
    try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
        byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);
        wr.write(payloadBytes);
    }

    // Get the response
    int responseCode = connection.getResponseCode();
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    StringBuilder response = new StringBuilder();
    String line;
    while ((line = in.readLine()) != null) {
        response.append(line);
    }
    in.close();

    connection.disconnect();

    return response.toString();
}

    	
public String getPointIds_ADO() {
	// System.out.println("***************** getting pointIds from ADO **************************");
			String pointIds="";
            String methodName="points";
            String apiVersion="api-version=6.0-preview.2";
            String queryParams = "?"+apiVersion;            
            String url_getPointIds = ADO_baseUrl+"plans/"+ADO_planId+"/suites/"+ADO_suiteId+"/"+methodName+queryParams;            
           //System.out.println("url_getPointIds: " + url_getPointIds);            
            try {
                // Encode the username and password in Base64
                String basicAuth = getBasicAuthHeader(ADO_username, ADO_accessToken);

                // Create the URL object
                URL url = new URL(url_getPointIds);

                // Open the connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxyServerOnLocalNetwork);

                // Set the request method to GET
                conn.setRequestMethod("GET");

                // Set the Authorization header with the encoded credentials
                conn.setRequestProperty("Authorization", basicAuth);

                // Get the response code
                int responseCode = conn.getResponseCode();
              //  System.out.println("getPointIds_responseCode: " + responseCode);                
                String jsonResponse = conn.getResponseMessage();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response content
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
                    );

                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();
                    // Process the response
                  //  System.out.println("getPointIds_Response: " + response.toString());                    
                                   
                    JsonPath js = new JsonPath(response.toString());
                    tcCount=js.get("count");
                   // System.out.println("tcCount in test suite: " + tcCount);
                    pointIds = js.get("value.id").toString();                   
                    //System.out.println("pointIds: " + pointIds);
                    ADO_pointIds=pointIds;                    
                    return ADO_pointIds;
                    
                    
                } else {
                    System.out.println("HTTP Request failed with response code: " + responseCode);
                }

                // Close the connection
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
			return ADO_pointIds;	
        
        }

public String getPointIdsResponse_ADO() {
	JsonPath js = new JsonPath("");
	String pointIds="";
    String methodName="points";
    String apiVersion="api-version=6.0-preview.2";
    String queryParams = "?"+apiVersion;            
    String url_getPointIds = ADO_baseUrl+"plans/"+ADO_planId+"/suites/"+ADO_suiteId+"/"+methodName+queryParams;            
    //System.out.println("url_getPointIds: " + url_getPointIds);            
    try {
        // Encode the username and password in Base64
        String basicAuth = getBasicAuthHeader(ADO_username, ADO_accessToken);
        // Create the URL object
        URL url = new URL(url_getPointIds);
        // Open the connection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxyServerOnLocalNetwork);
        // Set the request method to GET
        conn.setRequestMethod("GET");
        // Set the Authorization header with the encoded credentials
        conn.setRequestProperty("Authorization", basicAuth);
        // Get the response code
        int responseCode = conn.getResponseCode();
       // System.out.println("getPointIds_responseCode: " + responseCode);                
        String jsonResponse = conn.getResponseMessage();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response content
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            // Process the response
           // System.out.println("getPointIds_Response: " + response.toString());                    
                           
            js = new JsonPath(response.toString());
            return js.toString();            
            
        } else {
            System.out.println("HTTP Request failed with response code: " + responseCode);
        }

        // Close the connection
        conn.disconnect();
    } catch (IOException e) {
        e.printStackTrace();
    }
	
    return js.toString(); 

}


public String getTestCaseNames_ADO() {
    String pointIds="";
    String methodName="points";
    String apiVersion="api-version=6.0-preview.2";
    String queryParams = "?"+apiVersion;            
    String url_getPointIds = ADO_baseUrl+"plans/"+ADO_planId+"/suites/"+ADO_suiteId+"/"+methodName+queryParams;            
 //   System.out.println("url_getPointIds: " + url_getPointIds);            
    try {
        // Encode the username and password in Base64
        String basicAuth = getBasicAuthHeader(ADO_username, ADO_accessToken);

        // Create the URL object
        URL url = new URL(url_getPointIds);

        // Open the connection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxyServerOnLocalNetwork);

        // Set the request method to GET
        conn.setRequestMethod("GET");

        // Set the Authorization header with the encoded credentials
        conn.setRequestProperty("Authorization", basicAuth);

        // Get the response code
        int responseCode = conn.getResponseCode();
        //System.out.println("getPointIds_responseCode: " + responseCode);                
        String jsonResponse = conn.getResponseMessage();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response content
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            // Process the response
           // System.out.println("getPointIds_Response: " + response.toString());                    
                           
            JsonPath js = new JsonPath(response.toString());
            tcCount=js.get("count");
            System.out.println("tcCount: " + tcCount);
            pointIds = js.get("value.id").toString();                   
            //System.out.println("pointIds: " + pointIds);
            ADO_pointIds=pointIds;                    
            return ADO_pointIds;
            
            
        } else {
            System.out.println("HTTP Request failed with response code: " + responseCode);
        }

        // Close the connection
        conn.disconnect();
    } catch (IOException e) {
        e.printStackTrace();
    }
	return ADO_pointIds;	

}

        
        public static String getBasicAuthHeader(String username, String password) {
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            
            return "Basic " + new String(encodedAuth);
        }
        
        
        public void createTestRun_ADO() {        
        	System.out.println("***************** Creating New TestRun in ADO **************************");
        	System.out.println("ADO_baseUrl: "+ ADO_baseUrl);
        	String apiVersion="api-version=6.0";
            String queryParams = "?"+apiVersion;
                        
            String planId=ADO_planId;           
            String pointIds=getPointIds_ADO();
            String methodName="runs";            
            //String testRunId="";             
            String url_createTestRun = ADO_baseUrl+methodName+queryParams;            
            System.out.println("url_createTestRun: " + url_createTestRun);            
            String currentTimeStamp = java.time.LocalDateTime.now().toString();          
            try {
                String basicAuthHeader = createBasicAuthHeader(ADO_username, ADO_accessToken);
               String request = "{\"name\":\""+ADO_suiteName+"_"+currentTimeStamp+"\",\"automated\":true,\"plan\":{\"id\":"+planId+"},\"pointIds\":"+pointIds+"}";
               System.out.println("createTestRun_request: " + request);  
               String response = sendPostRequest(url_createTestRun, basicAuthHeader, request);      
               System.out.println("createTestRun_Response:\n" + response);
               
               JsonPath js = new JsonPath(response.toString());
               ADO_testRunId = js.get("id").toString();                   
               System.out.println("ADO_testRunId: " + ADO_testRunId);               
               
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("***************** New TestRun created in ADO **************************");
        }

                
        public void updateTestRun_ADO() {
        	System.out.println("***************** updating TestRun in ADO **************************");
        	String methodName="runs";
            String apiVersion="api-version=6.0";
            String queryParams = "?"+apiVersion;            
            String url_updateTestRun = ADO_baseUrl+methodName+"/"+ADO_testRunId+queryParams;                       
            //System.out.println("url_updateTestRun: " + url_updateTestRun);  	
            String currentTimeStamp = java.time.LocalDateTime.now().toString();
            try {                
            	String basicAuthHeader = createBasicAuthHeader(ADO_username, ADO_accessToken);            	 
            	URL url = new URL(url_updateTestRun);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxyServerOnLocalNetwork);

                // Set up the connection for the HTTP PATCH method
                conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                conn.setRequestProperty("Authorization", basicAuthHeader);
                //JSON data to be sent in the request body
                String request = "{\"name\":\""+ADO_suiteName+"_"+currentTimeStamp+"\",\"comment\":\"testcomments\",\"state\":\"Completed\"}";
                
                // Write the JSON data to the request body
                try (OutputStream outputStream = conn.getOutputStream()) {
                    byte[] input = request.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }

                // Get the HTTP response code
                int responseCode = conn.getResponseCode();
                System.out.println("updateTestRun_Response Code: " + responseCode);

                // Read and print the response from the server
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("updateTestRun_Response: " + response.toString());
                }

                // Close the connection
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public void updateTestResult_ADO(String tcResultStatus) {   
        	System.out.println("***************** updating TestResult in ADO **************************");        	 
        	String testRunMethodName="runs";
            String testResultsMethodName="results";
            String apiVersion="api-version=6.0";
            String queryParams = "?"+apiVersion;
            String url_updateTestResult = ADO_baseUrl+testRunMethodName+"/"+ADO_testRunId+"/"+testResultsMethodName+queryParams;
            //System.out.println("url_updateTestResult: " + url_updateTestResult);            
            double currentexecutionmins=RunClass.actualcurexecntime;          
            try {                
            	String basicAuthHeader = createBasicAuthHeader(ADO_username, ADO_accessToken);            	 
            	URL url = new URL(url_updateTestResult);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxyServerOnLocalNetwork);

                // Set up the connection for the HTTP PATCH method
                conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setRequestProperty("Authorization", basicAuthHeader);           
	 
                	 // Sample JSON data to be sent in the request body
                	// System.out.println("ADO_errorMessage: "+ ADO_errorMessage);
                	 if(tcResultStatus.contains("pass")) {
                	     tcResultStatus = "Passed"; }
                 	 if(tcResultStatus.contains("fail")) {
                	     tcResultStatus = "Failed"; }
                	 String request = "[{\"id\":"+ADO_lastResultId+",\"state\":\"Completed\",\"comment\":"+"\""+ADO_errorMessage+"\""+",\"outcome\":\""+tcResultStatus+"\""+",\"durationInMs\":"+currentexecutionmins+"}]";
                	// System.out.println("updateTestResult_request: " +  request);
                 
                // Write the JSON data to the request body
                try (OutputStream outputStream = conn.getOutputStream()) {
                    byte[] input = request.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                    }        
                 

                // Get the HTTP response code
                int responseCode = conn.getResponseCode();
                System.out.println("updateTestResult_Response Code: " + responseCode);

                // Read and print the response from the server
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("updateTestResult_Response: " + response.toString());
                }

                // Close the connection
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        
        public void getLastResultIds_ADO() {        
            String pointIds="";
            String methodName="points";
            String apiVersion="api-version=6.0-preview.2";
            String queryParams = "?"+apiVersion;            
            String url_getPointIds = ADO_baseUrl+"plans/"+ADO_planId+"/suites/"+ADO_suiteId+"/"+methodName+queryParams;            
            System.out.println("url_getPointIds: " + url_getPointIds);            
            try {
                // Encode the username and password in Base64
                String basicAuth = getBasicAuthHeader(ADO_username, ADO_accessToken);

                // Create the URL object
                URL url = new URL(url_getPointIds);

                // Open the connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxyServerOnLocalNetwork);

                // Set the request method to GET
                conn.setRequestMethod("GET");

                // Set the Authorization header with the encoded credentials
                conn.setRequestProperty("Authorization", basicAuth);

                // Get the response code
                int responseCode = conn.getResponseCode();
                System.out.println("getPointIds_responseCode: " + responseCode);                
                String jsonResponse = conn.getResponseMessage();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response content
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
                    );

                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();

                    // Process the response
                    System.out.println("getPointIds_Response: " + response.toString());                    
                                   
                    JsonPath js = new JsonPath(response.toString());
                    tcCount=js.get("count");
                    System.out.println("tcCount in test suite: " + tcCount);
                    pointIds = js.get("value.id").toString();                   
                    System.out.println("pointIds: " + pointIds);
                                       
                    String lastResultIds="";
                    lastResultIds=js.get("value.lastResult.id").toString();
                    lastResultIds = lastResultIds.substring(1, lastResultIds.length()-1);
                    String[] lastResultIdsArray = lastResultIds.split(",");   
                    for(int i=0; i<lastResultIdsArray.length; i++) {
                    	lastResultIdsArray[i]=lastResultIdsArray[i].trim();
                    	//System.out.println("lastResultIdsArray["+i+"]: " + lastResultIdsArray[i]); 
                    	}    
         
                    String testCaseNames="";
                    testCaseNames=js.get("value.testCase.name").toString();                             
                    testCaseNames = testCaseNames.substring(1, testCaseNames.length()-1);                    
                    String[] testCaseNamesArray = testCaseNames.split(",");   
                    for(int i=0; i<testCaseNamesArray.length; i++) {     
                    	testCaseNamesArray[i]=testCaseNamesArray[i].trim();
                    	//System.out.println("testCaseNamesArray["+i+"]: " + testCaseNamesArray[i]); 
                    	}  

                    for(int i=0; i<testCaseNamesArray.length; i++) {              
                		testCaseMap.put(testCaseNamesArray[i].trim(), lastResultIdsArray[i].trim());
                		}
                	
                	for(int i=0; i<testCaseNamesArray.length; i++) {              
                		//System.out.println("lastResultId["+i+"]: " + testCaseMap.get(testCaseNamesArray[i]));
                		}              	
              
                    
                    String ADO_TCIDs="";
                    ADO_TCIDs=js.get("value.testCase.id").toString();
                   // System.out.println("ADO_TCIDs: " + ADO_TCIDs);                                
                    ADO_TCIDs = ADO_TCIDs.substring(1, ADO_TCIDs.length()-1);
                    //System.out.println("ADO_TCIDs: " + ADO_TCIDs); 
                    
                    String[] ADO_TCIDsArray = ADO_TCIDs.split(",");   
                    for(int i=0; i<ADO_TCIDsArray.length; i++) {     
                    	ADO_TCIDsArray[i]=ADO_TCIDsArray[i].trim();
                    	//System.out.println("ADO_TCIDsArray["+i+"]: " + ADO_TCIDsArray[i]); 
                    	}  
                    
                    for(int i=0; i<ADO_TCIDsArray.length; i++) {              
                		testCaseMap.put(ADO_TCIDsArray[i].trim(), lastResultIdsArray[i].trim()); 
                		}                	
                    
                	for(int i=0; i<ADO_TCIDsArray.length; i++) {              
                		//System.out.println("lastResultId["+i+"]: " + testCaseMap.get(ADO_TCIDsArray[i])); 
                		}                                                    
                            	 	                		                    
                } else {
                    System.out.println("HTTP Request failed with rlastResultIdsesponse code: " + responseCode);
                }

                // Close the connection
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();               
                
            }	
              }         
    
        public String getLastResultIdByTCName(String tcName) {           
        	getLastResultIds_ADO();
        	ADO_lastResultId = testCaseMap.get(tcName);	
        	//System.out.println("TC Name: "+tcName+"  lastRunId: " + testCaseMap.get(tcName));
        	return ADO_lastResultId;
        }
        
        public String getLastResultIdByADO_TCID(String ADO_TCID) {           
        	getLastResultIds_ADO();
        	ADO_lastResultId = testCaseMap.get(ADO_TCID);	
        	//System.out.println("ADO_TCID: "+ADO_TCID+"  lastRunId: " + testCaseMap.get(ADO_TCID));
        	return ADO_lastResultId;
        }
   }
package com.united;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class KeyWords {

	ExtReportFW fw;
	Map<String, String> wdor;
	Map<String, String> stiosor;
	Map<String, String> standroidor;
	SeeTestMethods stmethods;
	WebDriverMethods wdmethods;
	WebServiceCalls wsCalls;
	String DeviceName = "";
	String AppName = "";
	String zone = "NATIVE";
	int index = 0, clickCount = 1;

	public KeyWords(ExtReportFW fwobj, WebServiceCalls wsCalls, Map<String, String> wdor, Map<String, String> stiosor,
			Map<String, String> standroidor, String Devicename, String Appname)
			throws FileNotFoundException, IOException {
		this.fw = fwobj;
		this.wdor = wdor;
		this.stiosor = stiosor;
		this.standroidor = standroidor;
		this.DeviceName = Devicename;
		this.AppName = Appname;
		this.stmethods = new SeeTestMethods(this.fw, stiosor, standroidor, wdor, Devicename, AppName);
		this.wdmethods = new WebDriverMethods(this.fw, wdor);
		this.wsCalls = new WebServiceCalls(this.fw);
	}

	public void ExecuteKeyWordMethod(String keywordmethod, HashMap<String, String> Parameters) throws Exception {
		if (fw.client != null)
			fw.client.setProperty("ios.native.nonInstrumented", "false");

		switch (keywordmethod) {

//Pandian----------------------------------------------------------------------------------------------------------//

		case "MxPLogin":
			wdmethods.launchMxP();
			fw.driver.manage().window().maximize();
//			Robot robot = new Robot();
//			System.out.println("About to zoom out");
//			for (int i = 0; i < 4; i++) {
//				robot.keyPress(KeyEvent.VK_CONTROL);
//				robot.keyPress(KeyEvent.VK_SUBTRACT);
//				robot.keyRelease(KeyEvent.VK_SUBTRACT);
//				robot.keyRelease(KeyEvent.VK_CONTROL);
//			}
			break;

		case "View_Positional_signoff_screen":
			wdmethods.View_Positional_signoff_screen();
			break;

		case "Requirement_advanced_search":
			wdmethods.Requirement_advanced_search();
			break;

		case "Taskcard_advanced_search":
			wdmethods.Taskcard_advanced_search(Parameters);
			break;

		case "Manage_BOM_screen":
			wdmethods.Manage_BOM_screen();
			break;

		case "view_Taskcard_screen":
			wdmethods.view_Taskcard_screen();
			break;
		case "view_requirement_screen":
			wdmethods.view_requirement_screen(Parameters);
			break;

		case "open_close_panel_sort_edit":
			wdmethods.openClosePanelSortEdit();
			break;

		case "commit_to_wcb":
			wdmethods.commitToWCB();
			break;

		case "delete_panel_record":
			wdmethods.deletePanelRecord();
			break;
		case "scroll_down_edit_30th_row":
			wdmethods.scroll_and_edit_30th_row();
			break;
		case "verify_zoneDesc_sameModel_Zone":
			wdmethods.verify_zoneDesc_sameModel_Zone();
			break;
		case "verify_invalid_and_valid_illustrations":
			wdmethods.verify_invalid_and_valid_illustrations(Parameters);
			break;
		case "verify_duplicate_illustration_error":
			wdmethods.verify_duplicate_illustration_error();
			break;
		case "verif_illustration_count":
			wdmethods.verif_illustration_count(Parameters);
			break;

		case "enter_packaging_screen":
			wdmethods.enter_packaging_screen();
			break;
		case "open_create_letter_check":
			wdmethods.open_create_letter_check();
			break;
		case "create_P_Project_verify_manage_screen":
			wdmethods.create_P_Project_verify_manage_screen();
			break;
		case "create_MRA_Project_verify_manage_screen":
			wdmethods.create_MRA_Project_verify_manage_screen();
			break;
		case "create_NR_Project_verify_manage_screen":
			wdmethods.create_NR_Project_verify_manage_screen();
			break;
			
		case "create_Letter_check_verify_manage_screen":
			wdmethods.create_Letter_check_verify_manage_screen();
			break;
			
		case "add_MnE_to_lettercheck_verify":
			wdmethods.add_MnE_to_lettercheck_verify(Parameters);
			break;
			
		case "add_MnE_to_BOM_verify":
			wdmethods.add_MnE_to_BOM_verify(Parameters);
			break;

		case "openClosePanel":
			wdmethods.openClosePanel();
			break;

		case "maintenanceProgramsOpen":
			wdmethods.maintenancePrograms();
			break;

		case "selectManageDropdown":
			wdmethods.manageDropdown();
			break;

		case "selectmraProjectDropdown":
			wdmethods.mraProjectDropdown();
			break;

		case "selectpProjectDropdown":
			wdmethods.pProjectDropdown();
			break;
		case "selectnrProjectDropdown":
			wdmethods.nrProjectDropdown();
			break;

		case "openClosePanelFilterAddNewItem":
			wdmethods.openClosePanelFilterAddNewItem();
			break;

		case "Navigate_Openclose":
			wdmethods.navigateopenclose();
			break;

		case "Navigate_MxPs":
			wdmethods.navigatemaintenanceprogramsitems();
			break;

		case "Req_Wildcardsearch":
			wdmethods.reqrmntwildcard();
			break;

		case "Tc_Wildcardsearch":
			wdmethods.tcwildcard();
			break;

		case "Mandatoryfields_OpenClose":
			wdmethods.openclosemandatory();
			break;

		case "LCSearch":
			wdmethods.LC_advanced_search(Parameters);
			break;

		case "MneSearch":
			wdmethods.MnE_advanced_search(Parameters);
			break;

		case "Audit_smoke":
			wdmethods.Audit_Summary_Smoke(Parameters);
			break;
		case "Audit_summary":
			wdmethods.Audit_Summary();
			break;

		case "Manage_version":
			wdmethods.Manage_Revision();
			break;

		case "Taskcard_lookup":
			wdmethods.Taskcard_Lookup();
			break;

		case "Search_lettercheck":
			wdmethods.Search_lettercheck();
			break;

		case "Lettercheck_Audittrial":
			wdmethods.Lettercheck_Audittrial();
			break;

		case "BOM_Audittrial":
			wdmethods.BOM_Audittrial();
			break;

		case "View_PDF":
			wdmethods.View_PDF();
			break;

		case "Inactivated_versions":
			wdmethods.Inactivated_versions();
			break;

		case "MRA_Add_Req_Screen":
			wdmethods.MRA_Project_Add_Req_Screen();
			break;

		case "NR_Add_tc_Screen":
			wdmethods.NR_Project_Add_Tc_Screen(Parameters);
			break;

		case "P_Add_Req_Screen":
			wdmethods.P_Project_Add_Req_Screen();
			break;

		case "export_requirement":
			wdmethods.export_requirement();
			break;

		case "Taskcard_versions":
			wdmethods.Taskcard_versions();
			break;

		case "preview_draft_optimizer":
			wdmethods.preview_draft_optimizer();
			break;
			
		case "Publishing_trasactions":
			wdmethods.Publishing_trasactions();
			break;
			
		case "WCP_WorkFlow":
			wdmethods.WCP_WorkFlow();
			break;
			
		case "Panel_Locator":
			wdmethods.Panel_Locator();
			break;
			
		case "T_Illustrations":
			wdmethods.T_Illustrations();
			break;
	
		}
		
		

	}
	@SuppressWarnings("resource")
	public static String getDataFromTechOpsDataHandler(String keyword, String Parameters, String token,
			boolean wantJSON) throws Exception {
		String url = "jdbc:mysql://10.164.81.222:3306/techops_datahander?useSSL=false";
		String username = "nikhil";
		String password = "nikhil";
		System.out.println("Connecting database...");
		String returnstr = "";
		int ptr = 100;
		String reqID = ("" + new Date()).replaceAll(" ", "").replaceAll(":", "");
		try (Connection connection = DriverManager.getConnection(url, username, password)) {

			System.out.println("Database connected!");
			connection.createStatement();

			connection.createStatement()
					.executeUpdate("INSERT INTO reqprocresp(reqID,keyword,parameters,token) VALUES ('" + reqID + "', '"
							+ keyword + "', '" + Parameters + "','" + token + "')");
			Thread.sleep(2000);
			ResultSet rs = connection.createStatement()
					.executeQuery("Select status,value from reqprocresp where reqid='" + reqID + "'");
			rs.next();
			boolean flg = true;
			while (flg && ptr > 0) {
				ptr--;
				System.out.println(rs.getString("status"));
				rs = connection.createStatement()
						.executeQuery("Select status,value,response from reqprocresp where reqid='" + reqID + "'");
				rs.next();
				String status = rs.getString("status");
				if (status.equalsIgnoreCase("Completed Processing")) {
					flg = false;
					if (wantJSON)
						System.out.println(returnstr = rs.getString("response"));
					else
						System.out.println(returnstr = rs.getString("value"));
					break;
				} else {
					System.out.println(status);
					Thread.sleep(1000);
				}

			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (returnstr.equalsIgnoreCase("Fail")) {
			throw new Exception("Failed to fetch data for passed keyword:" + keyword + ", with request Id:" + reqID
					+ ". Hint check if you have permissions to perform this action or check for parameters, else contact TechOps Data Handler Tool admin with Req ID:"
					+ reqID + " for Logs");
		}
		return returnstr;
	}

}

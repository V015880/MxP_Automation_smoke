package com.united;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.MapUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.relevantcodes.extentreports.LogStatus;
//import com.sun.net.ssl.SSLContext;
//import com.sun.net.ssl.TrustManager;
//import com.sun.net.ssl.X509TrustManager;

//import sun.security.provider.SecureRandom;

public class WebDriverMethods extends WebServiceCalls {
	WebDriver driver;
	ExtReportFW fw;
	Map<String, String> wdor;
	public String Str;
	public static String projectNumber = null;
	public static String projectnumber = null;
	public static String projectNR = null;
	public static String mraprojectURL = null;
	public String nrprojectURL = null;
	public String pprojectURL = null;
	public static String fleet = null;
	public static String lc_url = null;
	public static String subfleet = null;
	public static String Lettercheck = null;
	public static String view_req_url = null;
	public static String viewTcScreen = null;
	public static Map<String, String> fleetList = Map.ofEntries(Map.entry("787", "B787"), Map.entry("767", "B767"),
			Map.entry("757", "B757"), Map.entry("A320FAM", "A3NB"), Map.entry("777", "B777"),
			Map.entry("737MAX", "B73M"), Map.entry("737NG", "B737"));

	public WebDriverMethods(ExtReportFW fw, Map<String, String> wdor) {
		this.fw = fw;
		super.fw = fw;
		this.wdor = wdor;
		
	}

	// ---------------------------------------------------------------------------------------------//
	// TODO
	// Pandian__________________________________________________________________________//
	private void browserSetup() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
	}

	private void loginMxPApp() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.get(fw.GetValueFromCredentialProps("URLMxP"));
		Thread.sleep(5000);
		fw.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		if (fw.wdIsElementFound(wdor.get("fld_Username"))) {
			fw.wdElementSendText(wdor.get("fld_Username"), fw.GetValueFromCredentialProps("usr"));
			fw.wdElementSendText(wdor.get("fId_Password"), fw.GetValueFromCredentialProps("pwd"));
			fw.wdClick(wdor.get("fId_Login"));
		} else if (fw.wdIsElementFound(wdor.get("hrd_mxp_landing_page"))) {

		}

		Thread.sleep(1500);
	}

	public void launchMxP() throws Exception {
		loginMxPApp();
		Thread.sleep(1500);
		fw.wdVerifyElementFound(wdor.get("hrd_mxp_landing_page"));
		Thread.sleep(1500);
	}

	public void View_Positional_signoff_screen() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "configurationSignoffManagement");
		Thread.sleep(1000);
		fw.wdVerifyElementFound(wdor.get("hdr_configuration_signoff"));
		fw.wdClickbyJSexecutor(wdor.get("OCPM_Edit"));
		fw.wdClickbyJSexecutor(wdor.get("cso_addnewitem"));
		System.out.println("Clicked on Edit butto");
		// fw.wdClick(wdor.get("opn_cls_addnew"));
		System.out.println("Clicked on Add New Item");
		fw.wdSelectionList_SelectbyValue(wdor.get("Select_Model"), "B757");
		fw.SelectRandomDropDownValue(wdor.get("cso_equipment"));
		String equipment = fw.selected_Value;
		System.out.println("equipment  is:" + equipment);
		fw.SelectRandomDropDownValue(wdor.get("cso_equipment_code"));
		String equip_code = fw.selected_Value;
		System.out.println("equipment code is:" + equip_code);
		fw.wdElementSendKeys(wdor.get("cso_position"), "test");
		fw.wdClick(wdor.get("checkbox_Configsignoff_first_record_mech_signoff_required"));
		fw.wdClickbyJSexecutor(wdor.get("opn_cls_save"));
		fw.wdVerifyElementFound(wdor.get("cso_success"));
		fw.wdClickbyJSexecutor(wdor.get("OCPM_Edit"));
		fw.wdMouseHoverElement(wdor.get("table_fleet_comlumn_hdr"));
		fw.wdStaticClick(wdor.get("cso_fleet_filter"));
		fw.driver.findElement(By.xpath(wdor.get("filter_input_box"))).sendKeys("B757");
		Thread.sleep(500);
		fw.wdClick(wdor.get("hdr_epic"));
		fw.wdMouseHoverElement(wdor.get("cso_equip"));
		fw.wdClickbyJSexecutor(wdor.get("cso_equip"));
		Thread.sleep(100);
		fw.wdStaticClick(wdor.get("equip_filter"));
		Thread.sleep(100);
		fw.driver.findElement(By.xpath(wdor.get("filter_input_box"))).sendKeys(equipment);
		Thread.sleep(100);
		fw.wdClick(wdor.get("maintenance_program_hdr"));
		fw.wdMouseHoverElement(wdor.get("cso_equipcode"));
		fw.wdClick(wdor.get("cso_equipcode"));
		fw.wdClick(wdor.get("Equipment_code_hdr_filter_option"));
		Thread.sleep(100);
		fw.wdElementSendKeys(wdor.get("filter_input_box"), equip_code);
		Thread.sleep(100);
		fw.wdClick(wdor.get("maintenance_program_hdr"));
		fw.wdClick(wdor.get("configsignoff_del_option_first record"));
	}

	public void Requirement_advanced_search() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "MxProgram");
		fw.wdVerifyElementFound(wdor.get("hdr_maintenance_programs_items"));
		fw.wdSelectionList_SelectbyVisibleText(wdor.get("slct_drpdwn_item_type"), "Requirement");
		Thread.sleep(400);
		fw.wdClick(wdor.get("slct_drpdwn_fleet"));
		fw.SelectRandomDropDownValue(wdor.get("select_fleet_dropdown_list"));
		if (fw.driver.findElement(By.xpath(wdor.get("drpdwn_subfleet_req_advsrch"))).isEnabled()) {
			fw.wdClick(wdor.get("btn_advanced_search"));
		}
		if (fw.driver.findElement(By.xpath(wdor.get("drpdwn_subfleet_req_advsrch"))).isEnabled())
			fw.wdClick(wdor.get("drpdwn_subfleet_req_advsrch"));
		fw.SelectRandomDropDownValue(wdor.get("drpdwn_subfleet_list_req_advsrch"));
		// verify the fields in req advanced search
		List<WebElement> Req1 = fw.driver
				.findElements(By.xpath("(//div[contains(@class,'Requirement_subtitletext')])"));
		for (int i = 1; i <= Req1.size(); i++) {
			fw.driver.findElement(By.xpath("(//div[contains(@class,'Requirement_subtitletext')])[" + i + "]"))
					.isDisplayed();
			String text = Req1.get(i - 1).getText();
			System.out.println("element found: " + text);
		}
		fw.wdClick(wdor.get("btn_srch_req_advsrch"));
		Thread.sleep(5000);
		if (fw.wdIsElementFound(wdor.get("srch_result_requirement"))) {
			fw.wdClick(wdor.get("first_requirement_result_rightarrow"));
			fw.wdVerifyElementFound(wdor.get("view_requirement_lable"));
		} else {
			fw.wdVerifyElementFound(wdor.get("No_result_found"));
		}

	}

	public void Taskcard_advanced_search(HashMap<String, String> parameters) throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "MxProgram");
		fw.wdVerifyElementFound(wdor.get("hdr_maintenance_programs_items"));
		Thread.sleep(1000);
		fw.wdSelectionList_SelectbyVisibleText(wdor.get("slct_drpdwn_item_type"), parameters.get("itemtype"));
		Thread.sleep(400);
		fw.wdClick(wdor.get("slct_drpdwn_fleet"));
		fw.SelectRandomDropDownValue(wdor.get("select_fleet_dropdown_list"));
		if (fw.driver.findElement(By.xpath(wdor.get("drpdwn_subfleet_req_advsrch"))).isEnabled()) {
			fw.wdClick(wdor.get("btn_advanced_search"));
		}
		fw.wdSelectionList_SelectbyVisibleText(wdor.get("taskcard_type_tcadvsrch"), parameters.get("taskcardtype"));

		List<WebElement> tc = fw.driver.findElements(By.xpath("(//div[contains(@class,'TaskCard_subtitletext')])"));
		for (int i = 1; i <= tc.size(); i++) {
			fw.driver.findElement(By.xpath("(//div[contains(@class,'TaskCard_subtitletext')])[" + i + "]"))
					.isDisplayed();
			String tc1 = tc.get(i - 1).getText();
			System.out.println("Element found:" + tc1);
		}
		fw.wdClick(wdor.get("btn_srch_tc_advsrch"));
		fw.wdClick(wdor.get("first_taskcard_result_rightarrow"));
		fw.wdVerifyElementFound(wdor.get("view_taskcard_lable"));

	}

	public void Manage_BOM_screen() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		fw.driver.get(viewTcScreen);
		fw.wdClick(wdor.get("btn_view_bom"));
		fw.wdVerifyElementFound(wdor.get("hrd_manage_bom"));
	}

	public void view_Taskcard_screen() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "MxProgram");
		fw.wdVerifyElementFound(wdor.get("hdr_maintenance_programs_items"));
		fw.wdSelectionList_SelectbyVisibleText(wdor.get("slct_drpdwn_item_type"), "TaskCard");
		Thread.sleep(400);
		fw.wdClick(wdor.get("slct_drpdwn_fleet"));
		String selectedRandomDropDownValue = fw.SelectRandomDropDownValue(wdor.get("select_fleet_dropdown_list"));
		String fleet = fleetList.get(selectedRandomDropDownValue);
		fw.wdElementSendText(wdor.get("inpt_taskcard_number"), fleet);
		String tc = fw.driver.findElement(By.xpath("//input[@class='SearchForm_reffNum__LU2pY']"))
				.getAttribute("value");
		if (fw.driver.findElement(By.xpath(wdor.get("btn_load"))).isEnabled()) {
			fw.wdClick(wdor.get("btn_load"));
		}
		List<WebElement> results = fw.driver.findElements(By.xpath("//div[@class='MxTaskcard_reqNum__GuWmY']"));
		for (WebElement e : results) {
			String s = e.getText();
			if (!(s.contains(tc))) {
				fw.addLog(LogStatus.FAIL, "Both are", " not equals", true);
			}
		}
		List<WebElement> options = fw.driver.findElements(By.xpath("//div[@class='MxTaskcard_right-arrow__8g38k']"));
		Random rand = new Random();
		int index = rand.nextInt(options.size());
		options.get(index).click();
		System.out.println(index);
		fw.wdVerifyElementFound(wdor.get("view_taskcard_lable"));
		viewTcScreen = fw.driver.getCurrentUrl();
	}

	public void view_requirement_screen(HashMap<String, String> parameters) throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "MxProgram");
		fw.wdVerifyElementFound(wdor.get("hdr_maintenance_programs_items"));
		fw.wdSelectionList_SelectbyVisibleText(wdor.get("slct_drpdwn_item_type"), "Requirement");
		// System.out.println("requirement is:" + parameters.get("Req"));
		fw.SelectRandomDropDownValue(wdor.get("select_fleet_dropdown_list"));
		fw.wdElementSendText(wdor.get("inpt_requirement_number"), "0-0");

		fw.wdClick(wdor.get("btn_load"));
		List<WebElement> results = fw.driver.findElements(By.xpath("//div[@class='MxRequirement_reqNum__zH18Y']"));
		for (WebElement e : results) {
			String s = e.getText();
			if (!(s.contains("0-0"))) {
				fw.addLog(LogStatus.FAIL, "Both are", " not equals", true);
			}
		}
		fw.wdClick(wdor.get("first_requirement_result_rightarrow"));
		fw.wdVerifyElementFound(wdor.get("view_requirement_lable"));
		view_req_url = fw.driver.getCurrentUrl();
	}

	public void openClosePanelSortEdit() throws Exception {
		loginMxPApp();
		fw.wdClick(wdor.get("LP_OpenClose"));
		fw.wdWaitforElementToBeClickable(wdor.get("OCPM_Edit"));
		fw.wdMouseHoverElement(wdor.get("openclose_model_column_header"));
		fw.wdMouseHoverElement(wdor.get("openclose_sort_modal"));
		fw.wdStaticClick(wdor.get("openclose_sort_modal"));
		fw.driver.findElement(By.xpath(wdor.get("OCPM_Edit"))).click();
		String textinput = "B787-9 (79)";
		fw.wdSelectionList_SelectbyVisibleText(wdor.get("edit_modal_drpdwn_openclose"), textinput);
		String zoneinputvalue = fw.wdRandomAlphaString(6);
		fw.wdStaticClick(wdor.get("input_zone_openclose"));
		fw.wdElementSendText(wdor.get("input_zone_openclose"), zoneinputvalue);
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		Thread.sleep(1000);
		fw.wdClickbyJSexecutor(wdor.get("save_btn_openclose"));
		String zonevalue = fw.driver.findElement(By.xpath(wdor.get("zone_value_openclose"))).getText();
		System.out.println(zonevalue);
		String text = fw.wdFetchText(wdor.get("openclose_modal_first_data"));
		System.out.println("text is" + text);
		if (text.equals(textinput)) {
			fw.addLog(LogStatus.PASS, "Both are", "equals", true);
		}

	}

	public void commitToWCB() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "openClosePanel");
		fw.wdWaitforElementToBeClickable(wdor.get("OCPM_Edit"));
		fw.wdStaticClick(wdor.get("openclose_fleet_to_commit"));
		fw.wdMouseHoverElement(wdor.get("airbusfleet_option_to_commit"));
		fw.wdStaticClick(wdor.get("airbusfleet_option_to_commit"));
		fw.wdIsElementClickable(wdor.get("btn_commit_to_WCB"));
		fw.wdStaticClick(wdor.get("btn_commit_to_WCB"));
		fw.wdVerifyElementFound(wdor.get("commit_success_msg_openclose"));
	}

	public void deletePanelRecord() throws Exception {
		loginMxPApp();
		fw.wdClick(wdor.get("LP_OpenClose"));
		String firstRecordValue_bfr_delete = fw.driver.findElement(By.xpath(wdor.get("first_panel_record"))).getText();
		fw.wdClickbyJSexecutor(wdor.get("OCPM_Edit"));
		fw.wdStaticClick(wdor.get("btn_delete_first_panel_record"));
		fw.wdClick(wdor.get("confirm_delete_panel_record"));
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.wdStaticClick(wdor.get("save_btn_openclose"));
		String firstRecordValue_After_deleteAndSave = fw.driver.findElement(By.xpath(wdor.get("first_panel_record")))
				.getText();
		if (firstRecordValue_After_deleteAndSave.equalsIgnoreCase(firstRecordValue_bfr_delete)) {
			System.out.println("fail: soft delete happened");
			fw.wdVerifyElementNotFound(wdor.get("OCPM_Edit"));
		} else {
			System.out.println("pass : record Hard deleted confirmed");
			Thread.sleep(1000);
			fw.wdVerifyElementFound(wdor.get("OCPM_Edit"));
		}
		Thread.sleep(2000);
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		Thread.sleep(1000);
		String firstRecordValue_before_delete = fw.driver.findElement(By.xpath(wdor.get("first_panel_record")))
				.getText();
		System.out.println(firstRecordValue_before_delete);
		fw.wdClickbyJSexecutor(wdor.get("OCPM_Edit"));
		fw.wdStaticClick(wdor.get("btn_delete_first_panel_record"));
		fw.wdClick(wdor.get("confirm_delete_panel_record"));
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.wdStaticClick(wdor.get("btn_discard_changes"));
		fw.wdClick(wdor.get("btn_yes_discard_changes"));
		String firstRecordValue_After_deleteAndDiscard = fw.driver.findElement(By.xpath(wdor.get("first_panel_record")))
				.getText();
		System.out.println(firstRecordValue_After_deleteAndDiscard);
		if (firstRecordValue_before_delete.equalsIgnoreCase(firstRecordValue_After_deleteAndDiscard)) {
			System.out.println("pass : soft delete confirmed");
			fw.wdVerifyElementFound(wdor.get("OCPM_Edit"));
		} else {
			System.out.println("fail: hard delete happened");
			fw.wdVerifyElementNotFound(wdor.get("OCPM_Edit"));
		}
	}

	public void scroll_and_edit_30th_row() throws Exception {
		loginMxPApp();
		fw.wdClick(wdor.get("LP_OpenClose"));
		fw.wdStaticClick(wdor.get("OCPM_Edit"));
		fw.wdInfiniteScroll(wdor.get("panel_record_openclose"), 30);
		String random = fw.wdRandomAlphaNumericString(5);
		fw.wdElementSendText(wdor.get("30th_record_zone_field_edit"), random);
		fw.wdElementSendText(wdor.get("31st_record_zone_field_edit"), random);
		System.out.println(random);
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.wdStaticClick(wdor.get("save_btn_openclose"));
		Thread.sleep(1000);
		fw.wdInfiniteScroll(wdor.get("panel_record_openclose"), 30);
		Thread.sleep(1000);
		String zoneValueOf30thRecord = fw.driver.findElement(By.xpath(wdor.get("30th_record_zone_field"))).getText();
		System.out.println(zoneValueOf30thRecord);
		String zoneValueOf31stRecord = fw.driver.findElement(By.xpath(wdor.get("31st_record_zone_field"))).getText();
		System.out.println(zoneValueOf31stRecord);
		// if (zoneValueOf30thRecord==random && zoneValueOf31stRecord==random) {
		Thread.sleep(1000);
		if (zoneValueOf30thRecord.equals(random) && zoneValueOf31stRecord.equals(random)) {
			System.out.println("pass : scroll and edit is success");
			fw.movetoElementforActions(wdor.get("breadcrumb_link"));
			fw.wdVerifyElementFound(wdor.get("breadcrumb_link"));
		}
	}

	public void verify_zoneDesc_sameModel_Zone() throws Exception {
		// login to MxP app
		loginMxPApp();
		// click on open close panel management
		fw.wdClick(wdor.get("LP_OpenClose"));
		// fetch zone value of first record
		String zone = fw.wdFetchText(wdor.get("zone_first_record"));
		// fetch modal value of same record
		String model = fw.wdFetchText(wdor.get("openclose_modal_first_data"));
		// click on zone number
		fw.wdStaticClick(wdor.get("zone_first_record"));
		// fetch zone description value
		String zoneDesc1 = fw.wdFetchText(wdor.get("zone_desc_openclose"));
		// close manage zone pop up
		fw.wdStaticClick(wdor.get("Cancel_mng_zone"));
		// mouse hover on zone header
		fw.wdMouseHoverElement(wdor.get("Zone_column_hdr_openclose"));
		// fw.movetoElementforActions(wdor.get("Zone_column_hdr_openclose"));
		// click on filter icon of zone
		fw.wdStaticClick(wdor.get("Filter_icon_zone"));
		// enter value in filter box
		fw.wdClickbyJSexecutor(wdor.get("Filter_input_box_zone"));
		fw.wdElementSendText(wdor.get("Filter_input_box_zone"), zone);
		// move to top
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		// click on filter icon to close filter option
		fw.wdStaticClick(wdor.get("Filter_icon_zone"));
		// apply filter for model
		fw.wdMouseHoverElement(wdor.get("openclose_model_column_header"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdElementSendText(wdor.get("filter_input_box"), model);
		// close filter input box
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		// click on zone second record
		fw.driver.navigate().refresh();
		fw.wdClickbyJSexecutor(wdor.get("second_zone_record"));
		// fetch zone description
		String zoneDesc2 = fw.wdFetchText(wdor.get("zone_desc_openclose"));
		// close manage zone pop up
		fw.wdStaticClick(wdor.get("Cancel_mng_zone"));
		// verify both zone description are same
		System.out.println(zoneDesc1);
		System.out.println(zoneDesc2);
		if (zoneDesc1.equalsIgnoreCase(zoneDesc2)) {
			fw.wdVerifyElementFound(wdor.get("OCPM_Edit"));
		}

	}

	public void verify_invalid_and_valid_illustrations(HashMap<String, String> parameters) throws Exception {
		// login into MxP
		loginMxPApp();
		// click on open close panel management tab
		fw.wdClick(wdor.get("LP_OpenClose"));
		// wait for edit button to be visible
		fw.wdWaitforElementToBeClickable(wdor.get("OCPM_Edit"));
		// click on edit button
		fw.wdStaticClick(wdor.get("OCPM_Edit"));
		Thread.sleep(1000);
		// click on first record's illustrations edit icon
		fw.wdStaticClick(wdor.get("edit_illustration_icon"));
		// enter invalid illustration
		String invalid_illustration = fw.wdRandomAlphaNumericString(10);
		fw.wdElementSendKeys(wdor.get("input_illustration_box"), invalid_illustration);
		// click on add to panel
		fw.wdStaticClick(wdor.get("add_to_panel_button"));
		// click on save edits
		fw.wdClick(wdor.get("save_illustration_button"));
		// verify error message
		fw.wdVerifyElementFound(wdor.get("error_msg_illustration"));
		// delete the illustration
		int illustration_record_count = fw.driver.findElements(By.xpath(wdor.get("delete_illustration_icon"))).size();

		for (; illustration_record_count > 0; illustration_record_count--) {
			fw.wdClick(wdor.get("delete_illustration_icon"));
			Thread.sleep(1000);
		}
		// enter valid illustration
		String valid_illustration = parameters.get("valid_illustration");
		Thread.sleep(2000);
		fw.wdElementSendKeys(wdor.get("input_illustration_box"), valid_illustration);
		// click on add to panel
		fw.wdStaticClick(wdor.get("add_to_panel_button"));
		// click on save edits
		Thread.sleep(1000);
		fw.wdClick(wdor.get("save_illustration_button"));
		// click on save
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.wdStaticClick(wdor.get("save_btn_openclose"));
		// verify the added illustrations in the view illustration modal
		fw.wdStaticClick(wdor.get("illustrate_view_icon"));
		String illustration = fw.wdFetchText(wdor.get("illustrations_list"));
		if (illustration.equals(valid_illustration))
			fw.logs("pass");
		// pass the test case

	}

	public void verify_duplicate_illustration_error() throws Exception {
		// login into MxP
		loginMxPApp();
		// click on open close panel management tab
		fw.wdClick(wdor.get("LP_OpenClose"));
		// wait for edit button to be visible
		fw.wdWaitforElementToBeClickable(wdor.get("OCPM_Edit"));
		// get illustration value of first record
		fw.wdStaticClick(wdor.get("illustrate_view_icon"));
		String illustration = fw.wdFetchText(wdor.get("illustrations_list"));
		fw.wdStaticClick(wdor.get("close_view_illustraion"));
		// wait for edit button to be visible
		fw.wdWaitforElementToBeClickable(wdor.get("OCPM_Edit"));
		// click on edit button
		fw.wdStaticClick(wdor.get("OCPM_Edit"));
		// click on first record's illustrations edit icon
		fw.wdStaticClick(wdor.get("edit_illustration_icon"));
		// paste the illustration number
		fw.wdElementSendText(wdor.get("input_illustration_box"), illustration);
		// verify error message
		if (fw.wdFetchText(wdor.get("duplicate_illustration_error_msg")).contains("already"))
			fw.logs("pass");
	}

	public void verif_illustration_count(HashMap<String, String> parameters) throws Exception {
		// login into MxP
		loginMxPApp();
		// click on open close panel management tab
		fw.wdClick(wdor.get("LP_OpenClose"));
		// wait for edit button to be visible
		fw.wdWaitforElementToBeClickable(wdor.get("OCPM_Edit"));
		// fetch first record's illustration count
		String illustrationcount = fw.wdFetchText(wdor.get("illustration_count_firstRecord"));
		int tempcount = Integer.parseInt(illustrationcount);
		int oldIllustrationCount = tempcount;
		// click on edit button
		fw.wdStaticClick(wdor.get("OCPM_Edit"));
		Thread.sleep(1000);
		// click on first record's illustrations edit icon
		fw.wdStaticClick(wdor.get("edit_illustration_icon"));
		// enter valid illustration
		String illustration1 = parameters.get("illustration1");
		String illustration2 = parameters.get("illustration2");
		// click on add to panel
		Thread.sleep(2000);
		fw.wdElementSendKeys(wdor.get("input_illustration_box"), illustration1);
		if (fw.driver.findElement(By.xpath(wdor.get("add_to_panel_button"))).isEnabled()) {
			fw.wdStaticClick(wdor.get("add_to_panel_button"));
			tempcount++;
			fw.wdElementSendText(wdor.get("input_illustration_box"), illustration2);

			if (fw.driver.findElement(By.xpath(wdor.get("add_to_panel_button"))).isEnabled()) {
				fw.wdStaticClick(wdor.get("add_to_panel_button"));
				tempcount++;
			}
			// else fw.wdClick(wdor.get("save_illustration_button"));

		} else {
			fw.wdElementSendText(wdor.get("input_illustration_box"), illustration2);
			if (fw.driver.findElement(By.xpath(wdor.get("add_to_panel_button"))).isEnabled()) {
				fw.wdStaticClick(wdor.get("add_to_panel_button"));
				tempcount++;
			} else {
				while (fw.driver.findElements(By.xpath(wdor.get("delete_illustration_icon"))).size() > 0) {
					fw.wdClick(wdor.get("delete_illustration_icon"));
					tempcount--;
					Thread.sleep(1000);
				}
				if (tempcount == 0)
					fw.logs("pass: Temp count= " + tempcount);

			}
		}
		if (oldIllustrationCount < tempcount)
			fw.logs("pass: old Illustration Count " + oldIllustrationCount + " tempcount " + tempcount);
		// click on save edits
		fw.wdClick(wdor.get("save_illustration_button"));
		// click on save
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.wdClickbyJSexecutor(wdor.get("save_btn_openclose"));
		// fetch illustration count
		String illustrationcount2 = fw.wdFetchText(wdor.get("illustration_count_firstRecord"));
		int newIllustrationCount = Integer.parseInt(illustrationcount2);
		// verify count is increased by 1 and pass the test case
		System.out.println("old illustration cound:" + oldIllustrationCount);
		System.out.println("new illustration cound:" + newIllustrationCount);
		if (oldIllustrationCount != newIllustrationCount)
			fw.logs("pass: old Illustration Count " + oldIllustrationCount + " new Illustration Count "
					+ newIllustrationCount);
	}

	public void enter_packaging_screen() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "MxProgram");
		fw.wdClick(wdor.get("manage_Dropdown"));
		fw.wdClick(wdor.get("packaging_DropdownOption"));
		fw.wdVerifyElementFound(wdor.get("packaging_Title"));
		System.out.println(fw.wdGetWebElement(wdor.get("packaging_Title")).getText());
		fw.wdVerifyElementFound(wdor.get("packaging_tab"));
	}

	public void open_create_letter_check() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "packaging");
		fw.wdStaticClick(wdor.get("lettercheck_tab_packaging"));
		fw.wdStaticClick(wdor.get("Create_new_lettercheck_button"));
		fw.wdVerifyElementFound(wdor.get("Create_new_letter_check_popup"));
	}

	public void create_P_Project_verify_manage_screen() throws Exception {
		pProjectDropdown();
		projectNumber = fw.wdGetValue(wdor.get("project_number_input_box"));
		System.out.println("project number is:" + projectNumber);
		String title = fw.wdRandomCapitalAlphaString(20);
		fw.wdElementSendText(wdor.get("title_input"), title);
		if (fw.driver.findElement(By.xpath(wdor.get("create_new_project_button_modal"))).isEnabled()) {
			fw.wdClick(wdor.get("create_new_project_button_modal"));
		}
		fw.wdVerifyElementFound(wdor.get("manage_project_header"));
		String wdFetchprojectNumber = fw.wdFetchText(wdor.get("projectnumber"));
		if (wdFetchprojectNumber.contains(projectNumber))
			fw.logs("Pass : project number " + projectNumber);
		else
			fw.logs("Fail : ");
		String wdFetchTitle = fw.wdFetchText(wdor.get("Manage_project_title"));
		pprojectURL = fw.driver.getCurrentUrl();
		if (wdFetchTitle.equals(title))
			fw.logs("pass : project title " + title);

		else
			fw.logs("Fail : project title " + wdFetchTitle);

	}

	public void create_MRA_Project_verify_manage_screen() throws Exception {
		mraProjectDropdown();
		String title = fw.wdRandomCapitalAlphaString(20);
		fw.wdElementSendText(wdor.get("title_input"), title);
		projectnumber = fw.wdRandomAlphaNumericString(10);
		fw.wdElementSendKeys(wdor.get("project_number_input_box"), projectnumber);
		if (fw.driver.findElement(By.xpath(wdor.get("create_new_project_button_modal"))).isEnabled())
			fw.wdClick(wdor.get("create_new_project_button_modal"));
		fw.wdVerifyElementFound(wdor.get("manage_project_header"));
		String wdFetchTitle = fw.wdFetchText(wdor.get("Manage_project_title"));
		mraprojectURL = fw.driver.getCurrentUrl();
		System.out.println("project url is:" + mraprojectURL);
		if (wdFetchTitle.equals(title))
			fw.logs("pass : project title " + title);
		else
			fw.logs("Fail : project title " + wdFetchTitle);
	}

	public void create_NR_Project_verify_manage_screen() throws Exception {
		nrProjectDropdown();
		projectNR = fw.wdGetValue(wdor.get("project_number_input_box"));
		System.out.println("project nr is:" + projectNR);
		String title = fw.wdRandomCapitalAlphaString(20);
		fw.wdElementSendText(wdor.get("title_input"), title);
		if (fw.driver.findElement(By.xpath(wdor.get("create_new_project_button_modal"))).isEnabled()) {
			fw.wdClick(wdor.get("create_new_project_button_modal"));
		}
		Thread.sleep(2000);
		fw.wdVerifyElementFound(wdor.get("manage_project_header"));
		String wdFetchprojectNumber = fw.wdFetchText(wdor.get("projectnumber"));
		if (wdFetchprojectNumber.contains(projectNR))
			fw.logs("Pass : project number " + projectNR);
		else
			fw.logs("Fail : ");
		String wdFetchTitle = fw.wdFetchText(wdor.get("Manage_project_title"));
		nrprojectURL = fw.driver.getCurrentUrl();
		if (wdFetchTitle.equals(title))
			fw.logs("pass : project title " + title);
		else
			fw.logs("Fail : project title " + wdFetchTitle);
	}

	public void create_Letter_check_verify_manage_screen() throws Exception {
		open_create_letter_check();
		Lettercheck = "Automation" + fw.wdRandomAlphaNumericString(5);
		fw.wdElementSendKeys(wdor.get("Enter_lettercheck"), Lettercheck);
		// select fleet and sub-fleet
		fw.wdClick(wdor.get("Select_fleet_lc"));
		fw.SelectRandomDropDownValue(wdor.get("dropdown_list_fleet_lettercheck"));
		fleet = fw.driver.findElement(By.xpath("//input[@placeholder='Fleet']")).getAttribute("value");
		System.out.println(fleet);
		fw.wdClick(wdor.get("Select_subfleet"));
		fw.SelectRandomDropDownValue(wdor.get("dropdown_list_subfleet_lettercheck"));
		subfleet = fw.driver.findElement(By.xpath("//input[@placeholder='Subfleet']")).getAttribute("value");
		System.out.println(subfleet);
		String SLM = fw.wdRandomNumber(1, 999) + "";
		fw.wdElementSendKeys(wdor.get("Enter_SLM_lettercheck"), SLM);
		fw.wdClick(wdor.get("Dropdown_SLM"));
		fw.SelectRandomDropDownValue(wdor.get("Dropdown_SLM_Unit_lettercheck"));
		fw.wdIsElementClickable(wdor.get("create_lettercheck_button_popup"));
		fw.wdClick(wdor.get("create_lettercheck_button_popup"));
		// verify manage letter check header and number
		fw.wdVerifyElementFound(wdor.get("hdr_manage_lettercheck"));
		fw.wdverifyElementWithSpecifiedText(Lettercheck);
	}

	public void add_MnE_to_lettercheck_verify(HashMap<String, String> mne) throws Exception {
		create_Letter_check_verify_manage_screen();
		fw.wdClick(wdor.get("Add_item_to_lettercheck"));
		String MnE1 = mne.get("mne1");
		fw.wdElementSendKeys(wdor.get("M&E_Manage_lettercheck"), MnE1);
		fw.wdClick(wdor.get("Add_item_to_lettercheck"));
		String MnE2 = mne.get("mne2");
		fw.wdElementSendKeys(wdor.get("M&E_Manage_lettercheck"), MnE2);
		fw.wdClick(wdor.get("Save_M&Es"));
		fw.wdVerifyElementFound(wdor.get("Confirm_transaction_title"));
		String s = fw.wdRandomAlphaNumericString(5);
		fw.wdElementSendKeys(wdor.get("Confirm_popup"), s);
		fw.wdClick(wdor.get("Add_lettercheck_button"));
		fw.wdverifyElementWithSpecifiedText(MnE1);
		fw.wdverifyElementWithSpecifiedText(MnE2);
	}

	public void add_MnE_to_BOM_verify(HashMap<String, String> parameters) throws Exception {
		Manage_BOM_screen();
		// edit mode
		fw.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		if (fw.wdIsElementFound(wdor.get("bom_review_banner"))) {
			fw.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			view_Taskcard_screen();
			fw.wdClick(wdor.get("btn_view_bom"));
		}
		fw.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		int bomcount = fw.driver.findElements(By.xpath(wdor.get("bom_records_row"))).size();
		String warning = "Task Card Publishing inprogess. Please wait and try again after some time.";
		String element = "//*[contains(text(),'" + warning + "')]";
		if (bomcount > 0) {
			fw.wdClick(wdor.get("edit_btn_manage_bom"));
			// delete existing records
			Thread.sleep(1000);
			if (fw.wdIsElementFound(element)) {
				fw.addLog(LogStatus.PASS, warning, "test is Skipped", false);
			} else {
				for (; bomcount > 0; bomcount--) {
					fw.wdClick(wdor.get("del_bom_record"));
					Thread.sleep(500);
					fw.wdClick(wdor.get("yes_opt_del_confirm_modal"));
				}
				fw.wdClickbyJSexecutor(wdor.get("save_btn_manage_bom"));
				// fw.wdClick(wdor.get("refresh_MxP_icon"));
			}
		}
		fw.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		fw.wdClick(wdor.get("edit_btn_manage_bom"));

		Thread.sleep(1000);
		if (fw.wdIsElementFound(element)) {
			fw.addLog(LogStatus.PASS, warning, "test is Skipped", false);
		} else {
			// add M&E pn
			fw.wdClick(wdor.get("add _new_item_btn_manage_bom"));
			fw.wdClick(wdor.get("add_mne_PN_btn_manage_bom"));
			// enter number
			String validmneNumber = "19-3221-9-0013";
			fw.wdElementSendKeys(wdor.get("mnepn_input_box"), validmneNumber);
			String qty = "" + fw.wdRandomNumber(1, 999);
			fw.wdElementSendKeys(wdor.get("qty_input_field"), qty);
			// load
			fw.wdClick(wdor.get("partactions_drpdwn"));
			Thread.sleep(1000);
			fw.SelectRandomDropDownValue(wdor.get("partactions_drpdwn_list"));
			// save
			Thread.sleep(1000);
			fw.wdClickbyJSexecutor(wdor.get("save_btn_manage_bom"));
			// reload and verify
			fw.driver.navigate().refresh();
			if (fw.wdIsElementFound(wdor.get("bom_active")) || fw.wdIsElementFound(wdor.get("bom_live"))) {
				if (fw.driver.findElement(By.xpath(wdor.get("bom_approval_btn"))).isEnabled()) {
					fw.addLog(LogStatus.PASS, "submit for approval button", "is enabled", false);
				}
			} else {
				fw.addLog(LogStatus.PASS, "submit for approval button", "is disabled for other status except active",
						false);
			}
			Thread.sleep(3000);
			fw.wdverifyElementWithSpecifiedText(validmneNumber);
			Thread.sleep(1000);
			fw.wdClick(wdor.get("edit_btn_manage_bom"));
			fw.wdClick(wdor.get("bom_del_row"));
			fw.wdClick(wdor.get("confirm_delete_panel_record"));
			Thread.sleep(500);
			// fw.wdVerifyElementNotFound(validmneNumber);
		}
	}

	public void Preview_taskcard_etaskcard() throws Exception {
		// TODO Auto-generated method stub
		view_Taskcard_screen();
		String sceptre_mne = fw.wdFetchText(wdor.get("Sceptre_M&E_value"));
		// click on preview taskcard
		fw.wdStaticClick(wdor.get("Preview_etaskcard"));
		// switch window
		ArrayList<String> tabs = new ArrayList<String>(fw.driver.getWindowHandles());
		fw.driver.switchTo().window(tabs.get(0));
		Thread.sleep(1000);
		fw.driver.switchTo().window(tabs.get(1));
		fw.wdVerifyElementFound(wdor.get("etask_title"));
		if (fw.wdIsElementFound(wdor.get("etaskcard_mne"))) {
			fw.addLog(LogStatus.PASS, sceptre_mne, sceptre_mne, false);
		} else {
			fw.driver.findElement(By.xpath("//span[text()='Card search']")).click();
			fw.driver.findElement(By.xpath("//input[@placeholder='Search Task Cards']")).sendKeys(sceptre_mne);
			fw.driver.findElement(By.xpath("//div[@class='RadioButton draft-preview']/input")).click();
			fw.driver.findElement(By.xpath("//button[text()='Search']")).click();
		}

		// verify dash 1 on screen
		if (!(fw.driver.findElement(By.xpath("//label[text()='Card No:']//following-sibling::span"))).getText()
				.equals(sceptre_mne)) {
			fw.addLog(LogStatus.FAIL, "Dash 1", "not matches", true);
		}
		// verify some data from epic to etaskcard
	}

	public void maintenancePrograms() throws Exception {
		loginMxPApp();
		Thread.sleep(1000);
		fw.wdClickbyJSexecutor(wdor.get("maintenance_Program"));
		fw.wdVerifyElementFound(wdor.get("manage_Dropdown"));
	}

	public void openClosePanel() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "openClosePanel");
		fw.wdVerifyElementFound(wdor.get("open_close_panel_management_text"));
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		Thread.sleep(2000);
		fw.wdWaitforElementToBeClickable(wdor.get("OCPM_Edit"));
		fw.wdStaticClick(wdor.get("OCPM_Edit"));
		String modelvalue = "A319 (17)";
		fw.driver.findElement(By.xpath(wdor.get("Select_Model"))).click();
		fw.wdSelectionList_SelectbyValue(wdor.get("Select_Model"), modelvalue);
		String panelValue = fw.wdRandomAlphaNumericString(8);
		String ZoneValue = fw.wdRandomAlphaNumericString(5);
		fw.wdElementSendText(wdor.get("Enter_Panel"), panelValue);
		fw.wdElementSendText(wdor.get("Enter_Zone"), "767A");
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.driver.findElement(By.xpath(wdor.get("opn_cls_save"))).click();
		fw.wdVerifyElementFound(wdor.get("Toast_Success"));
		// fw.wdClick(wdor.get("refresh_MxP_icon"));
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		Thread.sleep(2000);
		fw.wdWaitforElementToBeClickable(wdor.get("OCPM_Edit"));
		fw.wdStaticClick(wdor.get("OCPM_Edit"));
		fw.wdMouseHoverElement(wdor.get("openclose_model_column_header"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdElementSendText(wdor.get("filter_input_box"), modelvalue);
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdMouseHoverElement(wdor.get("hdr_panel_open_close"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdElementSendText(wdor.get("filter_input_box"), panelValue);
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdMouseHoverElement(wdor.get("Zone_column_hdr_openclose"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdElementSendText(wdor.get("filter_input_box"), "767A");
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdVerifyElementFound(wdor.get("openclose_data_grid"));
		// delete record
		fw.driver.findElement(By.xpath("(//div[@class='PanelGrid_delRow__lLz0u '])[1]")).click();
		fw.driver.findElement(By.xpath("//div[@class='Button_innerBtnContainer__UATAd' and text()='Yes']")).click();
	}

	public void openClosePanelFilterAddNewItem() throws Exception {
		loginMxPApp();
		fw.wdClick(wdor.get("LP_OpenClose"));
		Thread.sleep(1000);
		// fw.wdWaitforElementToBeClickable(wdor.get("OCPM_Edit"));
		fw.wdMouseHoverElement(wdor.get("openclose_model_column_header"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdElementSendText(wdor.get("filter_input_box"), "B737");
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.wdWaitforElementToBeClickable(wdor.get("OCPM_Edit"));
		fw.wdStaticClick(wdor.get("OCPM_Edit"));
		// fw.driver.findElement(By.xpath(wdor.get("opn_cls_addnew"))).click();
		String modelvalue = "A319 (17)";
		fw.wdSelectionList_SelectbyValue(wdor.get("Select_Model"), modelvalue);
		String panelValue = fw.wdRandomAlphaNumericString(8);
		String ZoneValue = fw.wdRandomAlphaNumericString(5);
		fw.wdElementSendText(wdor.get("Enter_Panel"), panelValue);
		fw.wdElementSendText(wdor.get("Enter_Zone"), ZoneValue);
		fw.movetoElementforActions(wdor.get("Brd_Crmb"));
		fw.driver.findElement(By.xpath(wdor.get("opn_cls_save"))).click();
		fw.wdVerifyElementFound(wdor.get("Toast_Success"));
		// fw.wdClick(wdor.get("refresh_MxP_icon"));
		
		fw.wdWaitforElementToBeClickable(wdor.get("OCPM_Edit"));
		Thread.sleep(1000);
		fw.wdMouseHoverElement(wdor.get("openclose_model_column_header"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdElementSendText(wdor.get("filter_input_box"), modelvalue);
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdMouseHoverElement(wdor.get("hdr_panel_open_close"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdElementSendText(wdor.get("filter_input_box"), panelValue);
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdMouseHoverElement(wdor.get("Zone_column_hdr_openclose"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdElementSendText(wdor.get("filter_input_box"), ZoneValue);
		fw.movetoElementforActions(wdor.get("breadcrumb_link"));
		fw.wdStaticClick(wdor.get("filter_icon"));
		fw.wdVerifyElementFound(wdor.get("openclose_data_grid"));

	}

	public void manageDropdown() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "packaging");
		fw.wdVerifyElementFound(wdor.get("packaging_Title"));
		System.out.println(fw.wdGetWebElement(wdor.get("packaging_Title")).getText());
		fw.wdVerifyElementFound(wdor.get("packaging_tab"));
		System.out.println(fw.wdGetWebElement(wdor.get("packaging_tab")).getText());
		fw.wdVerifyElementFound(wdor.get("lettercheck_tab"));
		System.out.println(fw.wdGetWebElement(wdor.get("lettercheck_tab")).getText());
	}

	public void mraProjectDropdown() throws Exception {
		manageDropdown();
		fw.wdStaticClick(wdor.get("create_NewDropdown"));
		fw.wdStaticClick(wdor.get("MRAProject_dropdown"));
		fw.wdVerifyElementFound(wdor.get("new_mraProject_modal_window"));
	}

	public void pProjectDropdown() throws Exception {
		manageDropdown();
		fw.wdStaticClick(wdor.get("create_NewDropdown"));
		fw.wdStaticClick(wdor.get("pProject_dropdown"));
		fw.wdVerifyElementFound(wdor.get("new_peProject_modal_window"));
		System.out.println(fw.wdGetWebElement(wdor.get("new_peProject_modal_window")).getText());
	}

	public void nrProjectDropdown() throws Exception {
		manageDropdown();
		fw.wdClickbyJSexecutor(wdor.get("create_NewDropdown"));
		fw.wdStaticClick(wdor.get("nrProject_dropdown"));
		fw.wdVerifyElementFound(wdor.get("nrProject_modal_window"));
		System.out.println(fw.wdGetWebElement(wdor.get("nrProject_modal_window")).getText());
	}

	public void navigateopenclose() throws Exception {
		loginMxPApp();
		fw.wdWaitforElementToBeVisible(wdor.get("opn_close_lnk"));
		fw.wdClick(wdor.get("opn_close_lnk"));
		fw.wdWaitforElementToBeVisible(wdor.get("hdr_text_opn_cls"));
		String n = fw.wdGetWebElement(wdor.get("opn_close_brdcrmb_txt")).getText();
		System.out.println(n);
		fw.wdVerifyElementFound(wdor.get("edit_btn_opn_cls"));
	}

	public void navigatemaintenanceprogramsitems() throws Exception {
		loginMxPApp();
		fw.wdWaitforElementToBeVisible(wdor.get("mxp_lnk"));
		fw.wdClick(wdor.get("mxp_lnk"));
		fw.wdWaitforElementToBeVisible(wdor.get("hdr_text_mxp"));
		String n = fw.wdGetWebElement(wdor.get("mxp_brdcrmb_txt")).getText();
		System.out.println(n);
		fw.wdVerifyElementFound(wdor.get("mxp_default_txt"));
	}

	public void reqrmntwildcard() throws Exception {
		loginMxPApp();
		fw.wdWaitforElementToBeVisible(wdor.get("mxp_lnk"));
		fw.wdClick(wdor.get("mxp_lnk"));
		fw.wdWaitforElementToBeVisible(wdor.get("hdr_text_mxp"));
		fw.wdVerifyElementFound(wdor.get("hdr_text_mxp"));
		fw.wdSelectionList_SelectbyValue(wdor.get("select_item_dropdown"), "Requirement");
		fw.wdSelectionList_SelectbyIndex(wdor.get("select_fleet_dropdown"), 6);
		fw.wdElementSendText(wdor.get("enter_search_wild"), "787");
		fw.wdClick(wdor.get("load_btn"));
		fw.wdClick(wdor.get("click_side_arrow"));
		fw.wdVerifyElementFound(wdor.get("Req_value"));
		String req = fw.wdFetchText(wdor.get("Req_value"));
		fw.wdBrowserBack();
		fw.wdElementSendText(wdor.get("enter_search_wild"), req);
		// Thread.sleep(10000);
		fw.wdClick(wdor.get("load_btn"));
		fw.wdClick(wdor.get("click_side_arrow"));
		fw.wdVerifyElementFound(wdor.get("Req_value"));
	}

	public void tcwildcard() throws Exception {
		loginMxPApp();
		fw.wdWaitforElementToBeVisible(wdor.get("mxp_lnk"));
		fw.wdClick(wdor.get("mxp_lnk"));
		fw.wdWaitforElementToBeVisible(wdor.get("hdr_text_mxp"));
		fw.wdVerifyElementFound(wdor.get("hdr_text_mxp"));
		fw.wdSelectionList_SelectbyValue(wdor.get("select_item_dropdown"), "TaskCard");
		fw.wdSelectionList_SelectbyIndex(wdor.get("select_fleet_dropdown"), 6);
		fw.wdElementSendText(wdor.get("enter_search_wild"), "B787-MP-1000");
		fw.wdClick(wdor.get("load_btn"));
		fw.wdClick(wdor.get("Tc_side_arrow"));
		fw.wdVerifyElementFound(wdor.get("Tc_value"));
		fw.wdBrowserBack();
		fw.wdElementSendText(wdor.get("enter_search_wild"), "B787-MP-1000-20-615-00");
		// Thread.sleep(10000);
		fw.wdClick(wdor.get("load_btn"));
		fw.wdClick(wdor.get("Tc_side_arrow"));
		fw.wdVerifyElementFound(wdor.get("Tc_value"));
	}

	public void openclosemandatory() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "openClosePanel");
		fw.wdWaitforElementToBeClickable(wdor.get("OCPM_Edit"));
		fw.driver.findElement(By.xpath(wdor.get("OCPM_Edit"))).click();
		fw.wdClickbyJSexecutor(wdor.get("opn_cls_addnew"));
		System.out.println("Clicked on Edit butto");
		// fw.wdClick(wdor.get("opn_cls_addnew"));
		System.out.println("Clicked on Add New Item");
		fw.wdWaitforElementToBeClickable(wdor.get("opn_cls_save"));
		fw.driver.findElement(By.xpath(wdor.get("opn_cls_save"))).click();
		fw.wdVerifyElementFound(wdor.get("Toast_error"));
		fw.wdSelectionList_SelectbyValue(wdor.get("Select_Model"), "A319 (17)");
		fw.driver.findElement(By.xpath(wdor.get("opn_cls_save"))).click();
		fw.wdVerifyElementFound(wdor.get("Toast_error"));
		fw.wdElementSendText(wdor.get("Enter_Panel"), fw.wdRandomAlphaNumericString(8));
		fw.movetoElementforActions(wdor.get("Brd_Crmb"));
		fw.driver.findElement(By.xpath(wdor.get("opn_cls_save"))).click();
		fw.wdVerifyElementFound(wdor.get("Toast_error"));
		fw.wdElementSendText(wdor.get("Enter_Zone"), fw.wdRandomAlphaNumericString(5));
		fw.movetoElementforActions(wdor.get("Brd_Crmb"));
		fw.driver.findElement(By.xpath(wdor.get("opn_cls_save"))).click();
		fw.wdVerifyElementFound(wdor.get("Toast_Success"));

	}

	public void RequirementAdvanceSearch(HashMap<String, String> parameters) throws Exception {
		loginMxPApp();
		fw.wdClick(wdor.get("btn_maintenence_program"));
		fw.wdVerifyElementFound(wdor.get("hdr_maintenance_programs_items"));
		fw.wdClick(wdor.get("Select_ItemType"));
		fw.wdClick(wdor.get("optn_requirement"));
		fw.wdSelectionList_SelectbyVisibleText(wdor.get("Select_drpdown_fleetlist"), parameters.get("fleet"));
		fw.driver.findElement(By.xpath("//input[@placeholder='Search by Requirement number']")).clear();
		fw.driver.findElement(By.xpath("//input[@placeholder='Search by Requirement number']")).sendKeys("08-795-09");
		fw.driver.findElement(By.xpath("//button[text()='Load']")).click();
		List<WebElement> search_results = fw.driver
				.findElements(By.xpath("//div[@class='MxRequirement_requirementBox__5iDAQ']"));
		int total_values = search_results.size();
		for (int i = 0; i < total_values; i++) {
			System.out.println(search_results.get(i).getText());
		}

	}

	public void LC_advanced_search(HashMap<String, String> parameters) throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "MxProgram");
		fw.wdVerifyElementFound(wdor.get("hdr_maintenance_programs_items"));
		fw.wdSelectionList_SelectbyVisibleText(wdor.get("slct_drpdwn_item_type"), "Letter-Check");
		fw.wdClick(wdor.get("slct_drpdwn_fleet"));
		fw.SelectRandomDropDownValue(wdor.get("select_fleet_dropdown_list"));
		if (fw.driver.findElement(By.xpath(wdor.get("drpdwn_subfleet_req_advsrch"))).isEnabled()) {
			fw.wdClick(wdor.get("btn_advanced_search"));
		}
		fw.SelectRandomDropDownValue(wdor.get("drpdwn_subfleet_list_lc_advsrch"));
		fw.wdClick(wdor.get("m&e_search_button"));
		if (fw.driver.findElement(By.xpath(wdor.get("srch_result_lettercheck"))).isDisplayed()) {
			fw.addLog(LogStatus.PASS, "Lettercheck results", "found", false);
		} else {
			fw.wdVerifyElementFound(wdor.get("No_result_found"));
		}
		fw.wdClick(wdor.get("btn_advanced_search"));
		// verify fields in advance search section
		String l[] = { "Subfleet", "M&E number", "Requirement", "Task card" };
		ArrayList<String> lc = new ArrayList<String>(l.length);
		for (String lc1 : l) {
			lc.add(lc1); // Expected
		}
		ArrayList<String> Actuallc = new ArrayList<String>();
		List<WebElement> Actlc1 = fw.driver.findElements(
				By.xpath("//div[@class='LetterCheck_contentitem__HQNZE LetterCheck_linecontentlarge__l6ZRN']/div"));
		for (WebElement e : Actlc1) {
			Actuallc.add(e.getText()); // actual
		}
		if (!lc.equals(Actuallc)) {
			fw.addLogofWebDriver(LogStatus.FAIL, "Expected value Before checking" + lc,
					"Actual value After checking" + Actuallc, true);
		}
	}

	public void MnE_advanced_search(HashMap<String, String> parameters) throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "MxProgram");
		fw.wdVerifyElementFound(wdor.get("hdr_maintenance_programs_items"));
		fw.wdSelectionList_SelectbyVisibleText(wdor.get("slct_drpdwn_item_type"), parameters.get("itemtype"));
		if (fw.driver.findElement(By.xpath(wdor.get("drpdwn_subfleet_req_advsrch"))).isEnabled()) {
			fw.wdClick(wdor.get("btn_advanced_search"));
		}
		fw.driver.findElement(By.xpath("(//div[@class='ME_subtitletext__ltrJp'])[2]//following-sibling::input"))
				.sendKeys("te");
		fw.wdClickbyJSexecutor(wdor.get("search2"));
		if (fw.driver.findElement(By.xpath(wdor.get("srch_result_MnE"))).isDisplayed()) {
			fw.addLog(LogStatus.PASS, "MnE results", "found", false);
		} else {
			fw.wdVerifyElementFound(wdor.get("No_result_found"));
		}
		fw.wdClick(wdor.get("btn_advanced_search"));
		// verify fields in advance search section
		String m[] = { "MFG PN", "Description", "Requirement", "Task card", "Letter-check num" };
		ArrayList<String> mne = new ArrayList<String>(m.length);
		for (String mne2 : m) {
			mne.add(mne2); // Expected
			System.out.println(mne);
		}
		ArrayList<String> Actualmne = new ArrayList<String>();
		List<WebElement> Actmne = fw.driver.findElements(By.xpath("//div[@class='ME_subtitletext__ltrJp']"));
		for (WebElement e1 : Actmne) {
			Actualmne.add(e1.getText()); // actual
		}
		if (!mne.equals(Actualmne)) {
			fw.addLogofWebDriver(LogStatus.FAIL, "Expected value Before checking" + mne,
					"Actual value After checking" + Actualmne, true);
		}
	}

	public void Audit_Summary() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "auditSummary");
		fw.wdVerifyElementFound(wdor.get("Audit_Summary_title"));
	}

	public void Audit_Summary_Smoke(HashMap<String, String> parameters) throws Exception {
		Audit_Summary();
		if (fw.driver.findElement(By.xpath(wdor.get("export_button_Audit_summary"))).isEnabled()) {
			fw.addLog(LogStatus.FAIL, "Export button", "button is enabled by default", true);
		}
		fw.wdStaticClick(wdor.get("Audit_itemtype_dropdown"));
		fw.wdClick(wdor.get("Audit_itemtype_project"));
		fw.wdStaticClick(wdor.get("Audit_subtype_dropdown"));
		fw.wdClick(wdor.get("Audit_subtytype_CR&P"));
		fw.wdElementSendKeys(wdor.get("Audit_project_number"), "mra-test");
		if (fw.driver.findElement(By.xpath(wdor.get("Audit_search_button"))).isEnabled()) {
			fw.wdClick(wdor.get("Audit_search_button"));
		}
		if (!(fw.driver.findElement(By.xpath("(//label[@class='ProjectResults_row-value__pR03G'])[1]")).getText()
				.contains("MRA-test"))) {
			fw.addLog(LogStatus.FAIL, "Project_search results", "Project number not matches", true);
		}
		if (fw.wdIsElementFound(wdor.get("Clear_search"))) {
			fw.wdClickbyJSexecutor(wdor.get("Clear_search"));
		}
		fw.wdStaticClick(wdor.get("Audit_itemtype_dropdown"));
		List<WebElement> ele = fw.driver.findElements(By.xpath("//div[text()='Item Type']/div/ul/li"));
		if (ele.size() != 4) {
			fw.addLog(LogStatus.FAIL, "Status size not equals", "Actual size", true);
		}
		String[] ele1 = { "Project", "Requirement", "Letter-Check", "M&E" };
		ArrayList<String> eles = new ArrayList<String>(ele1.length);
		for (String e : ele1) {
			eles.add(e);
		}
		ArrayList<String> actualst = new ArrayList<String>();
		for (WebElement e1 : ele) {
			actualst.add(e1.getText());
		}
		if (!eles.equals(actualst)) {
			fw.addLogofWebDriver(LogStatus.FAIL, "Expected value Before checking" + eles,
					"Actual value After checking" + actualst, false);
		}
	}

	public void Manage_Revision() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "MxProgram");
		fw.wdSelectionList_SelectbyVisibleText(wdor.get("slct_drpdwn_item_type"), "Requirement");
		fw.wdSelectionList_SelectbyVisibleText(wdor.get("slct_drpdwn_fleet"), "787");
		fw.wdClick(wdor.get("btn_advanced_search"));
		fw.SelectRandomDropDownValue(wdor.get("Req_subfleet_list"));
		fw.wdClickbyJSexecutor(wdor.get("req_search_button"));
		fw.wdClick(wdor.get("first_requirement_result_rightarrow"));
		if (fw.wdIsElementFound(wdor.get("Manage_Versions"))) {
			fw.wdStaticClick(wdor.get("Manage_Versions"));
			fw.wdVerifyElementFound(wdor.get("Versions_header"));
			fw.wdClick(wdor.get("manage_revision_cancel_button"));
		}
		fw.wdBrowserBack();
		fw.wdClick(wdor.get("btn_advanced_search"));
		fw.SelectRandomDropDownValue(wdor.get("Req_subfleet_list"));
		fw.wdClick(wdor.get("requirement_status_dropdown"));
		fw.wdClick(wdor.get("requirement_status_dropdown_any"));
		fw.wdClickbyJSexecutor(wdor.get("Terminated"));
		fw.wdClickbyJSexecutor(wdor.get("req_search_button"));
		try {
			fw.wdClick(wdor.get("first_requirement_result_rightarrow"));
		} catch (Exception e) {
			System.out.println("exception");
		}
		fw.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		fw.wdWaitforElementNotVisible(wdor.get("Manage_Versions"));

	}

	public void Taskcard_Lookup() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		fw.driver.get(viewTcScreen);
		String tc_number = fw.wdFetchText(wdor.get("Tc_value"));
		String sceptre_mne = fw.wdFetchText(wdor.get("Sceptre_M&E_value"));
		Thread.sleep(500);
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "MxProgram");
		fw.wdVerifyElementFound(wdor.get("hdr_maintenance_programs_items"));
		fw.wdSelectionList_SelectbyVisibleText(wdor.get("slct_drpdwn_item_type"), "TaskCard");
		String trimmedValue = tc_number.substring(0, 4);
		String key = fleetList.entrySet().stream().filter(entry -> entry.getValue().equals(trimmedValue))
				.map(Map.Entry::getKey).findFirst().orElse(null);
		fw.wdClick(wdor.get("slct_drpdwn_fleet"));
		fw.wdSelectionList_SelectbyVisibleText(wdor.get("fleet_dropdown_option"), key);
		fw.wdElementSendText(wdor.get("inpt_taskcard_number"), sceptre_mne);
		if (fw.driver.findElement(By.xpath(wdor.get("btn_load"))).isEnabled()) {
			fw.wdClick(wdor.get("btn_load"));
		}
		fw.wdClick(wdor.get("first_taskcard_result_rightarrow"));
		fw.wdverifyElementWithSpecifiedText(tc_number);
		fw.wdverifyElementWithSpecifiedText(sceptre_mne);
	}

	public void Search_lettercheck() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "packaging");
		fw.wdStaticClick(wdor.get("lettercheck_tab_packaging"));
		fw.wdElementSendKeys(wdor.get("Search_lettercheck"), Lettercheck);
		Thread.sleep(500);
		fw.wdElementSendKeys(wdor.get("Search_fleet"), fleet);
		fw.wdElementSendKeys(wdor.get("Search_subfleet"), subfleet);
		if (fw.driver.findElement(By.xpath(wdor.get("Lettercheck_search_button"))).isEnabled()) {
			fw.wdStaticClick(wdor.get("Lettercheck_search_button"));
		}
		fw.wdStaticClick(wdor.get("Search_result_arrow"));
		fw.wdStaticClick(wdor.get("Search_result_subarrow"));
		fw.wdStaticClick(wdor.get("Lettercheck_searchresult"));
		fw.wdVerifyElementFound(wdor.get("hdr_manage_lettercheck"));
		lc_url = fw.driver.getCurrentUrl();
	}

	public void Lettercheck_Audittrial() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		fw.driver.get(lc_url);
		fw.wdStaticClick(wdor.get("Lettercheck_Audittrial"));
		fw.wdVerifyElementFound(wdor.get("Audittrial_title"));
		String[] a = { "Field Updated", "Action", "Comment", "Edited by", "Date and Time" };
		ArrayList<String> expectelem = new ArrayList<String>(a.length);
		for (String tc2 : a) {
			expectelem.add(tc2);
		} // Expected
		ArrayList<String> actualelem = new ArrayList<String>();
		List<WebElement> tc4 = fw.driver.findElements(By.xpath(wdor.get("Elements")));
		for (WebElement e : tc4) {
			actualelem.add(e.getText()); // actual
		}
		if (!expectelem.equals(actualelem)) {
			fw.addLogofWebDriver(LogStatus.FAIL, "Expected value Before checking" + expectelem,
					"Actual value After checking" + actualelem, true);
		}
		fw.driver.findElement(By.xpath("//span[text()='Manage Letter-Check']")).click();
		fw.wdClick(wdor.get("delete_lc"));
		fw.wdVerifyElementFound(wdor.get("delete_remove_title"));
		fw.wdClick(wdor.get("delete_lc_yes"));
		fw.wdStaticClick(wdor.get("lettercheck_tab_packaging"));
		fw.wdElementSendKeys(wdor.get("Search_lettercheck"), Lettercheck);
		Thread.sleep(500);
		fw.wdElementSendKeys(wdor.get("Search_fleet"), fleet);
		fw.wdElementSendKeys(wdor.get("Search_subfleet"), subfleet);
		if (fw.driver.findElement(By.xpath(wdor.get("Lettercheck_search_button"))).isEnabled()) {
			fw.wdStaticClick(wdor.get("Lettercheck_search_button"));
		}
		fw.wdVerifyElementNotFound(Lettercheck);
	}

	public void BOM_Audittrial() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		fw.driver.get(viewTcScreen);
		// verify title & header elements in Audit trial
		fw.wdClick(wdor.get("BOM_Audittrial"));
		fw.wdVerifyElementFound(wdor.get("BOM_Audit_title"));
		List<WebElement> Aud = fw.driver.findElements(By.xpath(wdor.get("Audittrial_elements")));
		for (WebElement e : Aud) {
			e.isDisplayed();
			System.out.println(e.getText());
		}
		// verify the results found
		if (!(fw.wdIsElementFound(wdor.get("Audit_no_results")))) {
			fw.wdStaticClick(wdor.get("Audit_sort"));
		}
	}

	public void View_PDF() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		fw.driver.get(viewTcScreen);
		String tc_number = fw.wdFetchText(wdor.get("Tc_value"));
		String tc_title = fw.wdFetchText(wdor.get("Searchresult_title1"));
		int oldWindowCount = fw.driver.getWindowHandles().size();
		System.out.println(oldWindowCount);
		// click on view pdf
		fw.wdStaticClick(wdor.get("Viewpdf_button"));
		fw.wdStaticClick(wdor.get("Master_Pdf"));
		Thread.sleep(3000);
		int newWindowCount = fw.driver.getWindowHandles().size();
		System.out.println(newWindowCount);
		// Assert.assertEquals(1, newWindowCount - oldWindowCount);
		Thread.sleep(2000);
		if (oldWindowCount < newWindowCount) {
			fw.addLog(LogStatus.PASS, "Pdf generated", "for the taskcard", true);
		} else {
			fw.addLog(LogStatus.FAIL, "Pdf not generated", "for the taskcard", true);
		}
		String newWindow = fw.driver.getWindowHandle();
		fw.driver.switchTo().window(newWindow);
		String newWindow1 = fw.driver.getWindowHandle();
		fw.driver.switchTo().window(newWindow1);
		System.out.println(fw.driver.getCurrentUrl());
	}

	public void MRA_Project_Add_Req_Screen() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		fw.driver.get(mraprojectURL);
		fw.wdStaticClick(wdor.get("Add_requirements"));
		fw.wdVerifyElementFound(wdor.get("Add_req_title"));
		fw.wdStaticClick(wdor.get("CR_fleetarrow"));
		List<WebElement> options = fw.driver
				.findElements(By.xpath("//ul[@class='select_items__npYSj select_items-tiny__scwVB']/li"));
		Random rand = new Random();
		int index = rand.nextInt(options.size());
		options.get(index).click();
		fw.wdClick(wdor.get("add_req_status_dropdown"));
		fw.wdClick(wdor.get("add_req_status_dropdown_active"));
		fw.wdClick(wdor.get("Search_button"));
		List<WebElement> options1 = fw.driver.findElements(By.xpath(wdor.get("Select_checkbox2")));
		Random rand1 = new Random();
		int index1 = rand1.nextInt(options1.size());
		options1.get(index1).click();
		System.out.println(index1);
		if (fw.driver.findElement(By.xpath(wdor.get("AddtoSelection"))).isEnabled()) {
			fw.wdClickbyJSexecutor(wdor.get("AddtoSelection"));
		}
		if (fw.driver.findElement(By.xpath(wdor.get("Add_Requirement"))).isEnabled())
			;
		fw.wdClick(wdor.get("Add_Requirement"));
//override_conflict
		Thread.sleep(2000);
		if (fw.wdIsElementFound(wdor.get("Override_conflict1"))) {
			fw.wdClick(wdor.get("Override_conflict1"));
		} else {
		}
		Thread.sleep(500);
		fw.wdClickbyJSexecutor(wdor.get("Select_requirement"));
		if (fw.wdIsElementFound(wdor.get("cr_supersede"))) {
			fw.wdClick(wdor.get("CR_Selectcheckbox"));
			if (fw.driver.findElement(By.xpath(wdor.get("Validate_selected"))).isEnabled()) {
				fw.addLog(LogStatus.PASS, "Validate_selected button is", "enabled", true);
			}
		else { Thread.sleep(1000);
			fw.wdClickbyJSexecutor(wdor.get("Validate_selected"));
		}
			if (fw.driver.findElement(By.xpath(wdor.get("publish_selected_button"))).isEnabled()) {
				fw.addLog(LogStatus.PASS, "publish selected button is", "enabled", true);
			}
		} else {
			Thread.sleep(500);
			fw.wdClickbyJSexecutor(wdor.get("Add_actions"));
			Thread.sleep(500);
			fw.wdClickbyJSexecutor(wdor.get("Select_action"));
			fw.wdClickbyJSexecutor(wdor.get("Add_lettercheck"));
			fw.wdClickbyJSexecutor(wdor.get("Select_lettercheckdropdown"));
			fw.driver.findElement(By.xpath("//ul[@class='select_items__npYSj select_items-medium__69RMw']//li[2]"))
					.click();
			fw.wdVerifyElementFound(wdor.get("View M&Es"));
			if (fw.driver.findElement(By.xpath(wdor.get("Save_button"))).isEnabled()) {
				fw.wdClickbyJSexecutor(wdor.get("Save_button"));
			}
			// Live_conflict
			if (fw.driver.getPageSource().contains("Live Conflict")) {
				fw.wdClickbyJSexecutor(wdor.get("Live_conflict"));
			}
			// Validating
			fw.wdClick(wdor.get("CR_Selectcheckbox"));
			fw.wdClickbyJSexecutor(wdor.get("Validate_selected"));
			Thread.sleep(1000);
			if (fw.wdIsElementFound(wdor.get("older_revision"))) {
				fw.addLog(LogStatus.PASS, "older revision", "", false);
			} else if (fw.wdIsElementFound(wdor.get("parent_child"))) {
				fw.addLog(LogStatus.PASS, "parent/child error", "", false);
			} else if (fw.wdIsElementFound(wdor.get("no_active_taskcard"))) {
				fw.addLog(LogStatus.PASS, "no active taskcards", "", false);
			} else if (fw.driver.findElement(By.xpath(wdor.get("publish_selected_button"))).isEnabled()) {
				fw.addLog(LogStatus.PASS, "publish selected button is", "enabled", true);
			}
		}
		fw.driver.findElement(By.xpath("//div[@class='ProjectInfoHeader_down-arrow__yrmfc']")).click();
		fw.wdClick(wdor.get("cancel_project"));
		fw.driver.findElement(By.xpath("//div[@class='Button_innerBtnContainer__UATAd' and text()='Cancel Project']"))
				.click();
		Thread.sleep(1000);
		fw.wdClickbyJSexecutor(wdor.get("Select_requirement"));
		Thread.sleep(1000);
		if (fw.driver.findElement(By.xpath("(//div[@class='ProjectInfoHeader_projectInfo-item__El56s'])[5]/div"))
				.getText().equalsIgnoreCase("Cancelled")) {
			fw.addLog(LogStatus.PASS, "mra project is cancelled", "pass", false);
		} else {
			fw.addLog(LogStatus.FAIL, "mra project is not cancelled", "fail", true);
		}
		fw.wdVerifyElementNotFound(wdor.get("Add_Requirement"));

	}

	public void NR_Project_Add_Tc_Screen(HashMap<String, String> parameters) throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		fw.driver.get(nrprojectURL);
		// fw.driver.get("https://epicmxp.stg.cwg.aws.ual.com/epic/project/923");
		fw.wdStaticClick(wdor.get("Add_TaskCard"));
		fw.wdVerifyElementFound(wdor.get("NR_task_title"));
		fw.wdClick(wdor.get("Select_Fleet_Field"));
		List<WebElement> options = fw.driver
				.findElements(By.xpath("//ul[@class='select_items__npYSj select_items-tiny__scwVB']/li"));
		Random rand = new Random();
		int index = rand.nextInt(options.size());
		options.get(index).click();
		Thread.sleep(1000);
		fw.wdClickbyJSexecutor(wdor.get("nr_status"));
		Thread.sleep(1000);
		fw.driver.findElement(By.xpath("//li/div[text()='Active']")).click();
		Thread.sleep(1000);
		fw.wdClickbyJSexecutor(wdor.get("Search_button"));
		fw.wdClick(wdor.get("Select_TaskCard1"));
		if (fw.driver.findElement(By.xpath(wdor.get("NR_AddtoSelection"))).isEnabled()) {
			fw.wdClick(wdor.get("NR_AddtoSelection"));
		}
		if (fw.driver.findElement(By.xpath(wdor.get("Add_taskcard_to_project"))).isEnabled()) {
			fw.wdClick(wdor.get("Add_taskcard_to_project"));
		}
//override conflict
		Thread.sleep(1000);
		if (fw.wdIsElementFound(wdor.get("NR_conflict_association_modal"))) {
			fw.wdClickbyJSexecutor(wdor.get("Override_conflict1"));
		}
		Thread.sleep(1000);
		fw.wdVerifyElementFound(wdor.get("Add_taskcard_success_msg"));
//Adding M&E
		Thread.sleep(500);
		fw.wdStaticClick(wdor.get("Select_taskcard_to_add_m&e"));
		fw.wdClickbyJSexecutor(wdor.get("add_m&e1"));
		fw.wdStaticClick(wdor.get("enter_m&e"));
		fw.wdElementSendKeys(wdor.get("enter_m&e"), parameters.get("m&e"));
		fw.wdStaticClick(wdor.get("load_m&e"));
		if (fw.driver.findElement(By.xpath(wdor.get("Save_tc"))).isEnabled()) {
			fw.wdClickbyJSexecutor(wdor.get("Save_tc"));
			fw.addLog(LogStatus.PASS, "save is enabled", "", false);
		}
		fw.wdStaticClick(wdor.get("select_taskcard_to_publish"));
		if (fw.driver.findElement(By.xpath(wdor.get("publish_selected_tc"))).isEnabled()) {
			fw.addLog(LogStatus.PASS, "publish selected button is", "enabled", false);
		}
		fw.driver.findElement(By.xpath("//div[@class='ProjectInfoHeader_down-arrow__yrmfc']")).click();
		fw.driver.findElement(By.xpath("//div[text()='Cancel Project']")).click();
		fw.driver.findElement(By.xpath("//div[@class='Button_innerBtnContainer__UATAd']")).click();
		String add_mne = fw.driver.findElement(By.xpath(
				"(//div[@class='ProjectTaskcardDetails_taskcard-action__F7k3y ProjectTaskcardDetails_disabled__7au47'])[2]"))
				.getAttribute("class");
		if (add_mne.contains("disabled")) {
			fw.addLog(LogStatus.PASS, "add mne button is", "disabled", false);
		} else {
			fw.addLog(LogStatus.FAIL, "add mne button is", "enabled", true);
		}
		if (fw.driver.findElement(By.xpath("(//div[@class='ProjectInfoHeader_projectInfo-item__El56s'])[5]")).getText()
				.equalsIgnoreCase("Cancelled")) {
			fw.addLog(LogStatus.PASS, "NR project is cancelled", "pass", false);
		} else {
			fw.addLog(LogStatus.FAIL, "NR project is not cancelled", "fail", true);
		}
		fw.wdVerifyElementNotFound(wdor.get("Add_TaskCard"));

	}

	public void P_Project_Add_Req_Screen() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		fw.driver.get(pprojectURL);
		fw.wdStaticClick(wdor.get("Add_requirements"));
		fw.wdVerifyElementFound(wdor.get("Add_req_title"));
		fw.wdClick(wdor.get("CR_fleetarrow"));
		List<WebElement> options = fw.driver
				.findElements(By.xpath("//ul[@class='select_items__npYSj select_items-tiny__scwVB']/li"));
		Random rand = new Random();
		int index = rand.nextInt(options.size());
		options.get(index).click();
		fw.wdStaticClick(wdor.get("Search_button"));
		Thread.sleep(1000);
		List<WebElement> results = fw.driver.findElements(By.xpath(wdor.get("Select_checkbox2")));
		Random rando = new Random();
		int inde = rando.nextInt(results.size());
		results.get(inde).click();
		// fw.wdClick(wdor.get("Select_checkbox"));
		if (fw.driver.findElement(By.xpath(wdor.get("AddtoSelection"))).isEnabled()) {
			fw.wdClick(wdor.get("AddtoSelection"));
		}
		if (fw.driver.findElement(By.xpath(wdor.get("Add_Requirement"))).isEnabled()) {
			fw.wdClick(wdor.get("Add_Requirement"));
		}
		// override_conflict
		Thread.sleep(1000);
		if (fw.wdIsElementFound(wdor.get("PE_conflict_association_modal"))) {
			fw.wdClickbyJSexecutor(wdor.get("Override_conflict1"));
		}
		// Adding actions to requirement
		fw.wdClickbyJSexecutor(wdor.get("Select_requirement"));
		if (fw.wdIsElementFound(wdor.get("cr_supersede"))) {
			fw.wdClick(wdor.get("CR_Selectcheckbox"));
			if (fw.driver.findElement(By.xpath(wdor.get("publish_selected_button"))).isEnabled()) {
				fw.addLog(LogStatus.PASS, "publish selected button is", "enabled", true);
			}
		} else {
			Thread.sleep(500);
			fw.wdClick(wdor.get("Add_actions"));
			fw.wdClickbyJSexecutor(wdor.get("Select_action"));
			fw.wdClickbyJSexecutor(wdor.get("Add_lettercheck"));
			fw.wdClickbyJSexecutor(wdor.get("Select_lettercheckdropdown"));
			fw.wdClick(wdor.get("Select_lettercheck"));
			fw.driver.findElement(By.xpath("//div[@class='select_select__q0kKD select_select-medium__YrSBH']//div[1]"));
			fw.wdVerifyElementFound(wdor.get("View M&Es"));
			if (fw.driver.findElement(By.xpath(wdor.get("Save_button"))).isEnabled()) {
				fw.wdClickbyJSexecutor(wdor.get("Save_button"));
			}
			// Live_conflict
			if (fw.driver.getPageSource().contains("Live Conflict")) {
				fw.driver.findElement(By.xpath("//div[text()='Override Conflict']")).click();
			}
			fw.wdClickbyJSexecutor(wdor.get("Action_select"));
			if (fw.driver.findElement(By.xpath(wdor.get("publish_selected_button"))).isEnabled()) {
				fw.addLog(LogStatus.PASS, "publish selected button is", "enabled", true);
			}
		}
		fw.driver.findElement(By.xpath("//div[@class='ProjectInfoHeader_down-arrow__yrmfc']")).click();
		fw.driver.findElement(By.xpath("//div[text()='Cancel Project']")).click();
		fw.driver.findElement(By.xpath("//div[@class='Button_innerBtnContainer__UATAd' and text()='Cancel Project']"))
				.click();
		Thread.sleep(1000);
		fw.wdClickbyJSexecutor(wdor.get("Select_requirement"));
		Thread.sleep(1000);
		if (fw.driver.findElement(By.xpath("(//div[@class='ProjectInfoHeader_projectInfo-item__El56s'])[5]/div"))
				.getText().equalsIgnoreCase("Cancelled")) {
			fw.addLog(LogStatus.PASS, "Pe project is cancelled", "pass", false);
		} else {
			fw.addLog(LogStatus.FAIL, "Pe project is not cancelled", "fail", true);
		}
		fw.wdVerifyElementNotFound(wdor.get("Add_Requirement"));
	}

	public void export_requirement() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "MxProgram");
		fw.wdVerifyElementFound(wdor.get("hdr_maintenance_programs_items"));
		fw.wdClick("//div[text()='Export']//preceding-sibling::div");
		fw.wdClick("//a[text()='Export Program']");
		fw.wdClick("//input[@placeholder='Select <Fleet>']");
		List<WebElement> options = fw.driver
				.findElements(By.xpath("//ul[@class='select_items__npYSj select_items-full__gSxTF']/li"));
		Random rand = new Random();
		int index = rand.nextInt(options.size());
			options.get(index).click();
			System.out.println(index);
		fw.wdClick("//button[text()='Export']");
		Thread.sleep(5000);
		File downloadDirectory = new File(System.getProperty("user.home") + "\\Downloads\\");
		File[] downloadFiles = downloadDirectory.listFiles();
		Arrays.sort(downloadFiles, Comparator.comparingLong(File::lastModified));
		File latestFile = downloadFiles[downloadFiles.length - 1];
		String specifiedText = "Export Program";

		if (latestFile.getName().contains(specifiedText)) {
			System.out.println("The file name contains the specified text." + latestFile.getName());
			fw.addLog(LogStatus.PASS, "file exist", "", false);

		} else {
			System.out.println("The file name does not contain the specified text." + latestFile.getName());
			fw.addLog(LogStatus.FAIL, "file not exist", "", false);
		}
	}

	public void Taskcard_versions() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		fw.driver.get(viewTcScreen);
		fw.wdClick(wdor.get("taskcard_revisions_button"));
		fw.wdVerifyElementFound(wdor.get("Taskcard_versions_title"));
		String[] a = { "Task card", "MP Rev", "TC Version", "eTask", "View PDF", "XML", "Status", "Date Archived" };
		ArrayList<String> expectelem = new ArrayList<String>(a.length);
		for (String tc2 : a) {
			expectelem.add(tc2);
		} // Expected
		ArrayList<String> actualelem = new ArrayList<String>();
		List<WebElement> tc4 = fw.driver.findElements(
				By.xpath("//div[@class='TaskcardRevisions_taskCardRevModalContainer__header-col__eQQSR']"));
		for (WebElement e : tc4) {
			actualelem.add(e.getText()); // actual
		}
		if (!expectelem.equals(actualelem)) {
			fw.addLogofWebDriver(LogStatus.FAIL, "Expected value Before checking" + expectelem,
					"Actual value After checking" + actualelem, true);
		}
	}

	public void Inactivated_versions() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		if (view_req_url != null) {
			fw.driver.get(view_req_url);
		} else {
			Requirement_advanced_search();
		}
		fw.driver.findElement(By.xpath("//div[text()='Inactivated Revisions']")).click();

		fw.wdVerifyElementFound(wdor.get("Inactivated_revisions_title"));
		String[] a = { "Requirement Number", "Fleet", "Subfleet", "MP Revision", "Date/Time",
				"Revision inactivated by" };
		ArrayList<String> expectelem = new ArrayList<String>(a.length);
		for (String tc2 : a) {
			expectelem.add(tc2);
		} // Expected
		ArrayList<String> actualelem = new ArrayList<String>();
		List<WebElement> tc4 = fw.driver.findElements(
				By.xpath("//div[@class='InActManageRevisionModal_inActRevModalContainer__header-col__FZZEB']"));
		for (WebElement e : tc4) {
			actualelem.add(e.getText()); // actual
		}
		if (!expectelem.equals(actualelem)) {
			fw.addLogofWebDriver(LogStatus.FAIL, "Expected value Before checking" + expectelem,
					"Actual value After checking" + actualelem, true);
		}
	}

	public void preview_draft_optimizer() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "optimizerPreview");
		fw.wdVerifyElementFound(wdor.get("Preview_optimizer_title"));
		fw.wdVerifyElementFound(wdor.get("Preview_optimizer_subtitle"));
		if (fw.driver.findElement(By.xpath("//input[@value='published']")).isSelected()) {
			fw.addLog(LogStatus.PASS, "Published button", "is selected by default", false);
		}
		String preview_button = fw.driver.findElement(By.xpath(
				"//div[@class='Button_root__ds1vO undefined false PackageBuilder_actionButton__A85IS Button_disabled__brl6u ']"))
				.getAttribute("class");
		if (preview_button.contains("disabled")) {
			fw.addLog(LogStatus.PASS, "generate preview button is disabled", "by default", true);
		}

		fw.wdElementSendKeys(wdor.get("Aircraft_number"), "6669"); // 6447
		Thread.sleep(1500);
		fw.wdElementSendKeys(wdor.get("mne_number"), "58-2581-9-0001"); // 64-2520-9-0026
		fw.driver.findElement(By.xpath("//div[@class='PackageBuilder_plusIcon__vAiQx']")).click();
		Thread.sleep(2000);
		if (fw.wdIsElementClickable(wdor.get("Generate_preview")))
			fw.wdClick(wdor.get("Generate_preview"));
		Thread.sleep(2000);
		fw.wdVerifyElementFound(wdor.get("Generate_preview_modal"));
		fw.wdVerifyElementFound(wdor.get("negative_wcn"));
		String wcn = fw.wdFetchText(wdor.get("negative_wcn"));
		if (fw.driver.getPageSource().contains("Regenerate")) {
			fw.wdClick(wdor.get("regenerate"));
			Thread.sleep(2000);
			String wcn1 = fw.wdFetchText(wdor.get("negative_wcn"));
			if (wcn.equals(wcn1)) {
				fw.addLog(LogStatus.FAIL, "old & regenated wcn's are", "equal", true);
			}
		}
		fw.driver.navigate().refresh();
		fw.wdElementSendKeys(wdor.get("Aircraft_number"), "1005");
		Thread.sleep(1000);
		fw.wdElementSendKeys(wdor.get("mne_number"), "84-2572-8-1453");
		fw.driver.findElement(By.xpath("//div[@class='PackageBuilder_plusIcon__vAiQx']")).click();
		Thread.sleep(500);
		WebElement box = fw.driver.findElement(By.xpath("//div[@class='WorkItems_workItem_err__3FDdx']"));
		String boxcolor = box.getCssValue("border-color");
		System.out.println("box color is:" + boxcolor);
		if (boxcolor.equals("rgb(255, 0, 0)")) {
			fw.addLog(LogStatus.PASS, "Box color is red", "red", true);
		}
		if (preview_button.contains("disabled")) {
			fw.addLog(LogStatus.PASS, "generate preview button is disabled", "for invalid mne", true);
		}
		fw.driver.navigate().refresh();
		fw.wdClick(wdor.get("unpublished"));
		fw.wdElementSendKeys(wdor.get("Aircraft_number"), "6669");
		fw.wdElementSendKeys(wdor.get("mne_number"), "58-2581-9-0001");
		fw.driver.findElement(By.xpath("//div[@class='PackageBuilder_plusIcon__vAiQx']")).click();
		Thread.sleep(500);
		fw.driver.findElement(By.xpath("//span[text()='select project']")).click();
		fw.driver.findElement(By.xpath("(//div[@class='radiobuttongroup_radioButton__sNcCi'])[3]/input")).click();
		Thread.sleep(1500);
		fw.wdClickbyJSexecutor(wdor.get("Generate_preview"));
		Thread.sleep(1000);
		fw.wdVerifyElementFound(wdor.get("Generate_preview_modal"));
		fw.wdVerifyElementFound(wdor.get("negative_wcn"));
		String wcn2 = fw.wdFetchText(wdor.get("negative_wcn"));
		if (fw.driver.getPageSource().contains("Regenerate")) {
			fw.wdClick(wdor.get("regenerate"));
			Thread.sleep(2000);
			String wcn1 = fw.wdFetchText(wdor.get("negative_wcn"));
			if (wcn2.equals(wcn1)) {
				fw.addLog(LogStatus.FAIL, "old & regenated wcn's are", "equal", true);
			}
		}
	}

	public void Publishing_trasactions() throws Exception {
		fw.wdInitConfiguredWebDriver();
		fw.driver.manage().window().maximize();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "MxProgram");
		fw.wdClick(wdor.get("manage_Dropdown"));
		fw.wdClick(wdor.get("Publishing_transactions"));
		Thread.sleep(1000);	
		fw.wdVerifyElementFound(wdor.get("publishing_transactions_title"));
		fw.wdVerifyElementFound(wdor.get("Taskcard_approval"));
		fw.wdVerifyElementFound(wdor.get("Requirement_updates"));
	}

	public void WCP_WorkFlow() throws Exception {
		fw.wdInitConfiguredWebDriver();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "MxProgram");
		fw.wdVerifyElementFound(wdor.get("hdr_maintenance_programs_items"));
		fw.wdClick(wdor.get("manage_Dropdown"));
		fw.wdClick(wdor.get("option_WCP_workflow"));
		Thread.sleep(1000);	
		fw.wdVerifyElementFound(wdor.get("WCP_header_title"));
		fw.wdVerifyElementFound(wdor.get("WCP_workFlow_breadcrumb"));
		fw.wdVerifyElementFound(wdor.get("wcp_requests_tab"));
	}

	public void Panel_Locator() throws Exception {
		fw.wdInitConfiguredWebDriver();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url);
		fw.wdClick(wdor.get("panel_locator_tab"));
		Thread.sleep(1000);		
		fw.wdVerifyElementFound(wdor.get("Panel_locator_header_title"));
		fw.wdVerifyElementFound(wdor.get("panel_locator_breadcrumb"));
		fw.wdVerifyElementFound(wdor.get("panel_locator_tail_dropdown"));
		fw.wdVerifyElementFound(wdor.get("panel_locator_fleet_dropdown"));
		fw.wdVerifyElementFound(wdor.get("panel_locator_zone_dropdown"));
		fw.wdVerifyElementFound(wdor.get("panel_locator_panelNumber_input"));
		fw.wdVerifyElementFound(wdor.get("panel_locator_load_button"));
	}

	public void T_Illustrations() throws Exception {
		fw.wdInitConfiguredWebDriver();
		String url = fw.GetValueFromCredentialProps("URLMxP");
		fw.driver.get(url + "MxProgram");
		fw.wdVerifyElementFound(wdor.get("hdr_maintenance_programs_items"));
		fw.wdClick(wdor.get("manage_Dropdown"));
		fw.wdClick(wdor.get("option_illustration"));
		Thread.sleep(1000);	
		fw.wdVerifyElementFound(wdor.get("T_illustration_header_title"));
		fw.wdVerifyElementFound(wdor.get("T_illustration_breadcrumb"));
		fw.wdVerifyElementFound(wdor.get("dropdown_fleet_illustration"));

	}

}

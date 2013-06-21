package com.ltxc.google.csms.shared;

public class SharedConstants {
	
	//App Level
	public static String COMPANY_NAME = "LTX-CREDENCE";
	public static String SITE_TITLE = "LTX-Credence Customer Service Mobile";
	public static String COMPANY_EMAIL_DOMAIN = "@ltxc.com";
	public static String SITE_NAME = "ltxc_csmobile";
	public static String SITE_EMAIL = "ltxc.csmobile@ltxc.com";
	public static String COOKIE_EMAILADDRESS = "emailadress";
	public static String LOGGED_OUT = "logged out";
	public static int MAX_RESULTS = 8000;
	public static long DATE_SECOND = 1000; //1000 second, 1000*60 minute
	public static long RESET_INTERVAL = 30; //second, minute
	public static String DATEFORMAT = "yyyy.MM.dd'T'HH:mm:ss z";
	public static String APIDATEFORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String FORMAT_PROCESSINGMESSAGE = "Process %s transaction with type:%s and ipad_id:%s at server time %s. Status:%s. Message:%s.";
	//Messaging
	//Email
	public static final String SystemAdmin_Email = "jinsong_lu@ltxc.com";
	public static final String System_Submitter = "System";
	public static final String Mail_Host = "mailhost.ltx-credence.com";
	public static final String Mail_From = "LTXC_PROGRAM_GENERATOR@ltxc.com";

	//Google Voice
	public static String SYSTEM_GOOGLE_EMAIL_FROM = "ltxc.applink@gmail.com";
	public static String SYSTEM_GOOGLE_VOICE_EMAIL = "ltxc.applink@gmail.com";
	public static String SYSTEM_GOOGLE_VOICE_PASSWORD = "credence";
	public static int MAXSIZE_GOOGLEVOICE_MESSAGING_TEXT = 468;//156;

	
	// Web
	public static final String PATTERN_EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String PATTERN_PHONE = "^([\\+][0-9]{1,3}([ \\.\\-])?)?([\\(]{1}[0-9]{3}[\\)])?([0-9A-Z \\.\\-]{1,32})((x|ext|extension)?[0-9]{1,4}?)$";// "^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$";
	public static final String SEPARATOR_LIST = "\\|";
	public static final String SESSION_ATTRIBUTE_ISLOGGEDIN = "loggedin";
	public static final String SESSION_ATTRIBUTE_USER = "userobject";
	public static final String SESSION_ATTRIBUTE_USERID = "userid";
	public static final String SESSION_ATTRIBUTE_AUTHENTICATOR = "authenticator";
	

	public static boolean IsLoadInitParam = false;
	public static String INIT_PARAM_URL_AsteaAlliance_API = "URL_AsteaAlliance_API";
	public static String INIT_PARAM_AA_UserName = "AA_UserName";
	public static String INIT_PARAM_AA_Password = "AA_Password";
	public static String INIT_PARAM_AA_Profile = "AA_Profile";
	public static String INIT_PARAM_ActiveDirectory_LADPURL = "ActiveDirectory_LADPURL";
	public static String INIT_PARAM_ActiveDirectory_DOMAIN = "ActiveDirectory_DOMAIN";
	public static String INIT_PARAM_ActiveDirectory_DefaultSearchBase = "ActiveDirectory_DefaultSearchBase";
	public static String INIT_PARAM_ActiveDirectory_GroupDistinguishName = "ActiveDirectory_GroupDistinguishName";

	/************************** Default Parameters modified when security filter is called. Please refer the web.xml *******************************/
	//AA 
	//public static String URL_AsteaAlliance_API = "http://dev-sqlssd-ma.ltx-credence.com/AsteaAlliance100_APP_Framework/Astea.AO.API.asmx";
	//public static String URL_AsteaAlliance_API = "http://srv-psal-dev.ltx-credence.com/AsteaAlliance100_APP_Framework/Astea.AO.API.asmx";
	public static String URL_AsteaAlliance_API = "http://srv-aaui-nor.ltx-credence.com/AsteaAlliance100_APP_Framework/Astea.AO.API.asmx";
	public static String AA_UserName = "bu_Mobile";//"seal";
	public static String AA_Password = "X1234";//"X1234";
	public static String AA_Profile = "SEAL";//"IMPORT1";//"SEAL68";	
	/************************** End of Default Parameters *******************************/
	public static String Pattern_SessionID = "^[\\d\\w]{8}\\-[\\d\\w]{4}\\-[\\d\\w]{4}\\-[\\d\\w]{4}\\-[\\d\\w]+$";

	public static String AA_Result_XPATH_SUCCESS = "root/Succeeded";
	public static String AA_Result_XPATH_ERROR = "root/Error/Description";
	public static String AA_Result_XPATH_RESULT = "root/Results";
	
	/************************** Default Parameters modified when security filter is called. Please refer the web.xml *******************************/
	//Active Directory
	public static String ActiveDirectory_LADPURL = "ldap://159.75.16.52:389";
	public static String ActiveDirectory_DOMAIN = "ltxc";
	public static String ActiveDirectory_DefaultSearchBase = "DC=ltx-credence,DC=com";
	public static String ActiveDirectory_GroupDistinguishName = "CN=LTXC_CSMobile,OU=SecurityGroups,OU=USA,OU=LTXCResources,DC=ltx-credence,DC=com";
	/************************** End of Default Parameters *******************************/
	public static String ActiveDirectory_Attribute_AAAcount = "primaryInternationalISDNNumber";

	//Cycle Count
	public static final String CYCLE_TYPE_PART = "PART";
	public static final String CYCLE_TYPE_BIN= "BIN";
	public static final String CYCLE_TYPE_SOURCE = "IPAD";
	
	//Mapping Attribute
	public static String Attribute_Printer = "printer";
	public static String Attribute_IsPrinting = "isprinting";
	public static String Attribute_CreatedBy = "created_by";
	public static String Attribute_CreatedDate = "created_date";
	//receiving header
	public static String Attribute_Inventory_Carrier = "carrier_id";
	public static String Attribute_Inventory_Carrier_Refno = "carrier_refno";
	public static String Attribute_Inventory_FR_Warehouse_ID = "fr_warehouse_id";
	public static String Attribute_Inventory_No_Of_Package = "no_of_packages";
	public static String Attribute_Inventory_Our_Refno = "our_refno";
	public static String Attribute_Inventory_Weight = "weight";
	public static String Attribute_Inventory_Ship_Instructions = "ship_instructions";
	public static String Attribute_Inventory_Sender_Refno = "sender_refno";
	public static String Attribute_Inventory_Ref_Doc_Type_Id = "ref_doc_type_id";
	public static String Attribute_Inventory_To_Warehouse_ID = "to_warehouse_id";
	public static String Attribute_Inventory_To_Company_ID = "to_company_id";
	public static String Attribute_Inventory_Fr_Company_ID = "fr_company_id";
	public static String Attribute_Inventory_Shipped_By = "shipped_by";
	public static String Attribute_Inventory_Received_By = "received_by";
	public static String Attribute_Inventory_PDF_Attachment = "pdf_attachment";
	public static String Attribute_Inventory_LineItems = "lineitems";
	//receiving line item
	public static String Attribute_Inventory_LineItemNo = "lineitemno";
	public static String Attribute_Inventory_Bin_Code_ID = "bin_code_id";
	public static String Attribute_Inventory_BPart_ID = "bpart_id";
	public static String Attribute_Inventory_Inv_Type_ID = "inv_type_id";
	public static String Attribute_Inventory_Man_ADJ_Reason_ID = "man_adj_reason_id";
	public static String Attribute_Inventory_QTY = "qty";
	public static String Attribute_Inventory_Serial_No = "serial_no";
	public static String Attribute_Inventory_Dest_Warehouse_ID = "dest_warehouse_id"; //Repair Warehouse
	public static String Attribute_Inventory_War_Type_ID = "war_type_id"; //Warranty
	public static String Attribute_Inventory_Cconth_ID = "cconth_id"; //contract
	public static String Attribute_Inventory_Node_ID = "node_id"; //domain node
	public static String Attribute_Inventory_PO_ID = "po_id";//P.O.
	public static String Attribute_Inventory_Priority = "priority";//priority
	public static String Attribute_Inventory_Pcode_ID = "pcode_id";//problem code

	
	
	public static String Attribute_Inventory_INDEX = "index";
	//validation fields
	public static String Attribute_Inventory_DRCCLOSE = "drc_close";
	public static String Attribute_Inventory_DRCCLOSEITEM = "drc_close_item";
	public static String Attribute_Inventory_ORIGDOCLINEID = "origdoclineid";
	public static String Attribute_Inventory_ITEMID = "itemid";
	public static String Attribute_Inventory_CUSTOMERCOMPANYID = "customercompanyid";
	public static String Attribute_Inventory_OUTPUTREFNO = "outputrefno";
	public static String Attribute_Inventory_OUTPUTRP = "outputrp";
	public static String Attribute_Inventory_RFR2ACTION = "rfr2_action";
	public static String Attribute_Inventory_NONDRCFULLRECEIVEL = "nondrc_full_receivel";
	public static String Attribute_Inventory_DRCFULLRECEIVEL = "drc_full_receivel";
	public static String Attribute_Inventory_DESTINVTYPEID = "dest_inv_type_id";
	

	//public static String Attribute_Inventory_
	
	//Shipping
	public static String Attribute_Shipping_NonListAction = "nonlistaction";
	public static String Attribute_Shipping_NoShipLines = "noshiplines";
	
	//header
	public static String Attribute_Shipping_Carrier = "carrier_id";
	public static String Attribute_Shipping_Carrier_Refno = "carrier_refno";
	public static String Attribute_Shipping_To_Warehouse_ID = "to_warehouse_id";
	public static String Attribute_Shipping_To_Company_ID = "to_company_id";
	public static String Attribute_Shipping_Fr_Warehouse_ID = "fr_warehouse_id";
	public static String Attribute_Shipping_No_Of_Packages = "no_of_packages";
	public static String Attribute_Shipping_Weight = "weight";
	public static String Attribute_Shipping_Ship_Instructions = "ship_instructions";
	public static String Attribute_Shipping_Shipped_By = "shipped_by";
	public static String Attribute_Shipping_ORIG_DOC_ID = "orig_doc_id";
	public static String Attribute_Shipping_DOC_TYPE_ID = "doc_type_id";
	public static String Attribute_Shipping_Transaction_Type = "transaction_type";
	public static String Attribute_Shipping_Target = "target";
	public static String Attribute_Shipping_Target_Value = "target_value";
	public static String Attribute_Shipping_Demand_Id_List = "demand_id_list";
	public static String Attribute_Shipping_Doc_Type_Id_List = "doc_type_id_list";
	public static String Attribute_Shipping_Search_Orig_Doc_ID = "search_orig_doc_id";
	
	public static String Attribute_Shipping_Lines = "lines";
	public static String Attribute_Shipping_VendorAction = "vendoraction";
	
	//line item
	public static String Attribute_Shipping_LineItemNo = "lineitemno";
	public static String Attribute_Shipping_BPart_ID = "bpart_id";
	public static String Attribute_Shipping_QTY = "qty";
	public static String Attribute_Shipping_Serial_No = "serial_no";
	public static String Attribute_Shipping_Shipped_QTY = "shipped_qty";
	public static String Attribute_Shipping_LDMND_STAT_ID = "ldmnd_stat_id";
	public static String Attribute_Shipping_DEMAND_ID = "demand_id";
	public static String Attribute_Shipping_FR_INV_TYPE_ID = "fr_inv_type_id";
	public static String Attribute_Shipping_Item_ID = "item_id";
	public static String Attribute_Shipping_Search_Key = "search_key";
	public static String Attribute_Shipping_Search_Value = "search_value";

	
	//Exception Message
	public static String Exception_AD_AuthenticationFailed = "It has failed to authenticate your username and password against the AD.";
	public static String Exception_AD_Authorization = "You are not authorized to access the Custom Service Mobile. Please contact the administrator.";
	public static String Exception_Login_RequestNull = "Restful webservice Context HttpServletRequest injection failed.";
	public static String Exception_Login_SessionExpire =   "Your Session has expired. Please login again.";
	public static String Exception_ValidationText_NotExist = "No extra information has been returned from Validation Stored Procedure...";
	
	public static String Exception_CycleCount_NoCount = "No Cycle Count Items was available in the payload...";
	
	
	//Messages
	//DRC Receiving
	public static String Message_DRC_ReceivingSuccess = "All submitted line items have been received successfully.";
	public static String Message_DRC_ReceivingWarning = "Warning!!! Not all submitted line items have been received successfully";
}

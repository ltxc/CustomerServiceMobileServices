package com.ltxc.google.csms.aa;

import static org.junit.Assert.*;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;

import com.ibm.icu.util.Calendar;
import com.ltxc.google.csms.server.domain.BinPart;
import com.ltxc.google.csms.server.domain.Carrier;
import com.ltxc.google.csms.server.domain.Company;
import com.ltxc.google.csms.server.domain.InventoryLineItem;
import com.ltxc.google.csms.server.domain.InventoryTransaction;
import com.ltxc.google.csms.server.domain.ProcessResult;
import com.ltxc.google.csms.server.domain.Warehouse;
import com.ltxc.google.csms.server.service.restful.DateParam;
import com.ltxc.google.csms.shared.StringHelper;
import com.ltxc.google.csms.shared.TransactionTypeEnum;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class TestRestfulWS {
	private static final String urlTemplate = "http://127.0.0.1:8888/csmobile/rest/%s/%s";

	//@Test
	public void testUserService() {

		String username = "jlu";
		String password = "Firewood";

		String url = urlTemplate.format(urlTemplate, "user", "myaccount");
		String message = "";

		try {
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(username, password));
			WebResource wr_getuser = client.resource(url);

			ClientResponse cr = wr_getuser.accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);

			if (cr != null) {
				message = cr.getEntity(String.class);
			}
		} catch (Exception ex) {
			assertTrue(ex.getMessage(), false);
		}
	}
	//@Test
	public void testWarehouseSync() {

		String urlTemplate = "http://127.0.0.1:8888/csmobile/rest/%s/%s";
		String username = "jlu";
		String password = "Firewood&10";
		String classpath = "synch";
		String methodpath = "warehouse";
		String param_1_key = "";
		String param_1_value = "";
		String url = urlTemplate.format(urlTemplate, classpath, methodpath);
		String expected_contenttype = MediaType.APPLICATION_JSON;
		List<Warehouse> list = null;


		try {
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(username, password));
			WebResource wr_getuser = client.resource(url);

			ClientResponse cr = wr_getuser.accept(expected_contenttype).get(
					ClientResponse.class);

			if (cr != null) {
				list = cr.getEntity(new   
						GenericType<List<Warehouse>>() {});
				assertTrue(list.size()>0);
			}
		} catch (Exception ex) {
			assertTrue(ex.getMessage(), false);
		}
		
		

	}
	//@Test
	public void testBinPartSync()
	{
		String urlTemplate = "http://127.0.0.1:8888/csmobile/rest/%s/%s";
		String username = "jlu";
		String password = "Firewood&10";
		String classpath = "synch";
		String methodpath = "binpart";
		String param_1_key = "warehouseid";
		String param_1_value = "A-SAN-JOSE";
		String param_2_key = "lastdate";
		String param_2_value = "";
		String param_3_key = "maxresult";
		String param_3_value = "20";
		String url = urlTemplate.format(urlTemplate, classpath, methodpath);
		String expected_contenttype = MediaType.APPLICATION_JSON;
		BinPart binPart = null;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		DateParam dateParam = new DateParam(calendar.getTime());
		
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add(param_1_key, param_1_value);
		queryParams.add(param_2_key, dateParam.toString());
		queryParams.add(param_3_key, param_3_value);
		List<BinPart> list = null;
		try {
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(username, password));
			WebResource wr_getuser = client.resource(url).queryParams(
					queryParams);

			ClientResponse cr = wr_getuser.accept(expected_contenttype).get(
					ClientResponse.class);

			if (cr != null) {
				list = cr.getEntity(new   
						GenericType<List<BinPart>>() {});
				assertNotNull(list);
			}
		} catch (Exception ex) {
			assertTrue(ex.getMessage(), false);
		}
		
	}
	
	@Test
	public void testInventoryTransactionMiscReceive()
	{
		String urlTemplate = "http://127.0.0.1:8888/csmobile/rest/%s/%s";
		String username = "jlu";
		String password = "Firewood&10";
		String classpath = "inventory";
		String methodpath = "load";
		
		String ipad_id = "jasperipad_1";
		String transaction_type = TransactionTypeEnum.MRC.getTransactionTypeName();
		
		String to_warehouse_id = "A-SAN-JOSE";
		String submitter = "BMORENO";
		String fr_company_id = "LTXSJ";
		String sender_refno = "PO8397";
		String carrier_id = "DHL";
		String carrier_refno = "987654321";
		
		String out_bpart_id = "956-0133-00";
		float qty_received = 5;
		String man_adj_reason_id = "MISC";
		String inv_type_id = "good";
		String bin_code_id = "43F";
		
		String url = urlTemplate.format(urlTemplate, classpath, methodpath);
		String expected_contenttype = MediaType.APPLICATION_JSON;
		InventoryTransaction inv = null;
		
		

		try {
			
			//create InventoryTransaction
			Warehouse to_warehouse = Warehouse.findWarehouseByWarehouseID(to_warehouse_id);
			Carrier carrier = Carrier.findCarrierByCarrierID(carrier_id);
			Company company = Company.findCompanyByCompanyID(fr_company_id);
			
			inv  = new InventoryTransaction();
			inv.setTransaction_type(transaction_type);
			inv.setIpad_id(ipad_id);
			inv.setCarrier(carrier);
			inv.setCarrier_refno(carrier_refno);
			inv.setCompany(company);
			inv.setCreated_by(submitter);
			inv.setCreated_date(Calendar.getInstance().getTime());
			inv.setTo_warehouse(to_warehouse);
			inv.setSender_refno(sender_refno);
			
			InventoryLineItem lineItem = new InventoryLineItem();
			lineItem.setBin_code_id(bin_code_id);
			lineItem.setBpart_id(out_bpart_id);
			lineItem.setQty(qty_received);
			lineItem.setMan_adj_reason_id(man_adj_reason_id);
			lineItem.setInv_type_id(inv_type_id);
			
			
			inv.getInventoryLineItems().add(lineItem);
			
			
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(username, password));
			WebResource wr_getuser = client.resource(url);

			ClientResponse cr = wr_getuser.accept(expected_contenttype).post(
					ClientResponse.class, inv);

			if (cr != null) {
				ProcessResult result = cr.getEntity(ProcessResult.class); //it should be 
				assertNotNull(result);
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			assertTrue(ex.getMessage(), false);
		}
	}
}

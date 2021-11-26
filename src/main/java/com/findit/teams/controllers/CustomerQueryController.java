package com.findit.teams.controllers;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.findit.teams.business.service.CustomerQueryService;
import com.findit.teams.domain.CustomerQuery;
import com.findit.teams.domain.Status;

@RestController
@RequestMapping("/api")
public class CustomerQueryController {
	@Autowired
	CustomerQueryService customerQueryService;
	private static final Logger logger = LoggerFactory.getLogger(CustomerQueryController.class);
	
	
	@PostMapping("/addCustomerQuery")
	public ResponseEntity<Status> addCustomerQuery(@RequestBody ObjectNode obj) throws JSONException {
		logger.info("Request to add a customer query");
		Status st = new Status();
		try {
			CustomerQuery customerQuery = customerQueryService.addCustomerQuery(obj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Customer query added successfully");
			st.setObject(customerQuery);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Add customer query failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Add customer query failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	@PostMapping("/updatecustomerQuery")
	public ResponseEntity<Status> updatecustomerQuery(@RequestBody ObjectNode obj) throws JSONException, URISyntaxException {
		logger.info("Request to update a customer query");
		Status st = new Status();
		try {
			CustomerQuery customerQuery = customerQueryService.updateCustomerQuery(obj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Customer query updated successfully");
			st.setObject(customerQuery);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Updating customer query failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Updating customer query failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}

	@GetMapping("/searchCustomerQuery")
	public ResponseEntity<Status> searchCustomerQuery(@RequestParam Map<String, String> requestObj) {
		logger.info("Request to search srvice on given filter criteria");
		Status st = new Status();
		try {
			List<CustomerQuery> list = customerQueryService.searchCustomerQuery(requestObj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("search data get SUCCESS : " + list);
			st.setObject(list);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Search customer query failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Search customer query failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}

	@DeleteMapping("/deleteCustomerQuery/{id}")
	public ResponseEntity<Status> deleteCustomer(@PathVariable Long id) {
		logger.info("Request to delete a customer query");
		Status st = new Status();
		try {
			customerQueryService.deleteCustomerQuery(id);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Customer query deleted successfully");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Throwable e) {
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Delete customer query failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}

	@GetMapping("/getAllCustomerQuery")
	private ResponseEntity<Status> getAllCustomerQuery() {
		logger.info("Request to get all customer query");
		Status st = new Status();
		try {
			List<CustomerQuery> list = customerQueryService.getAllCustomerQuery();
			if (list == null) {
				logger.error("Search all customer failed");
				st.setCode(HttpStatus.EXPECTATION_FAILED.value());
				st.setType("ERROR");
				st.setMessage("Search all customer failed");
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
			}
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Search all customer query successful");
			st.setObject(list);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Search all customer query failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Search all customer query failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
}

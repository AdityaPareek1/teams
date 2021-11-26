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
import com.findit.teams.business.service.CustomerService;
import com.findit.teams.domain.Customer;
import com.findit.teams.domain.Status;

@RestController
@RequestMapping("/api")
public class CustomController {
	@Autowired
	CustomerService customerService;
	private static final Logger logger = LoggerFactory.getLogger(CustomController.class);
	
	
	@PostMapping("/addCustomer")
	public ResponseEntity<Status> addCustomer(@RequestBody ObjectNode obj) throws JSONException {
		logger.info("Request to add a custom");
		Status st = new Status();
		try {
			Customer customer = customerService.addCustomer(obj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Customer added successfully");
			st.setObject(customer);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Add customer failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Add customer failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	@PostMapping("/updateCustomer")
	public ResponseEntity<Status> updateCustomer(@RequestBody ObjectNode obj) throws JSONException, URISyntaxException {
		logger.info("Request to update a customer");
		Status st = new Status();
		try {
			Customer customer = customerService.updateCustomer(obj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Customer updated successfully");
			st.setObject(customer);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Updating customer failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Updating customer failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}

	@GetMapping("/searchCustomer")
	public ResponseEntity<Status> searchCustomer(@RequestParam Map<String, String> requestObj) {
		logger.info("Request to search srvice on given filter criteria");
		Status st = new Status();
		try {
			List<Customer> list = customerService.searchCustomer(requestObj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("search data get SUCCESS : " + list);
			st.setObject(list);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Search customer failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Search customer failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}

	@DeleteMapping("/deleteCustomer/{id}")
	public ResponseEntity<Status> deleteCustomer(@PathVariable Long id) {
		logger.info("Request to delete a customer");
		Status st = new Status();
		try {
			customerService.deleteCustomer(id);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Customer deleted successfully");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Throwable e) {
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Delete customer failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}

	@GetMapping("/getAllCustomer")
	private ResponseEntity<Status> getAllCustomer() {
		logger.info("Request to get all service");
		Status st = new Status();
		try {
			List<Customer> list = customerService.getAllCustomer();
			if (list == null) {
				logger.error("Search all customer failed");
				st.setCode(HttpStatus.EXPECTATION_FAILED.value());
				st.setType("ERROR");
				st.setMessage("Search all customer failed");
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
			}
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Search all customer successful");
			st.setObject(list);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Search all customer failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Search all customer failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
}

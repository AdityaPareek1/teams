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
import com.findit.teams.business.service.CustomerProfileService;
import com.findit.teams.business.service.CustomerService;
import com.findit.teams.domain.Customer;
import com.findit.teams.domain.CustomerProfile;
import com.findit.teams.domain.Status;
import com.findit.teams.repository.CustomerProfileRepository;

@RestController
@RequestMapping("/api")
public class CustomerProfileController {
	@Autowired
	private CustomerProfileService customerProfileService;
	private static final Logger logger = LoggerFactory.getLogger(CustomerProfileController.class);
	
	
	@PostMapping("/addCustomerProfile")
	public ResponseEntity<Status> addCustomerProfile(@RequestBody ObjectNode obj) throws JSONException {
		logger.info("Request to add a custom");
		Status st = new Status();
		try {
			CustomerProfile customerProfile = customerProfileService.addCustomerProfile(obj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Customer profile added successfully");
			st.setObject(customerProfile);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Add customer profile failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Add  custome profiler failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	@PostMapping("/updateCustomerProfile")
	public ResponseEntity<Status> updateCustomerProfile(@RequestBody ObjectNode obj) throws JSONException, URISyntaxException {
		logger.info("Request to update a customer");
		Status st = new Status();
		try {
			CustomerProfile customerProfile = customerProfileService.updateCustomerProfile(obj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Customer profile updated successfully");
			st.setObject(customerProfile);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Updating customer profile failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Updating customer profile failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}

	@GetMapping("/searchCustomerProfile")
	public ResponseEntity<Status> searchCustomerProfile(@RequestParam Map<String, String> requestObj) {
		logger.info("Request to search srvice on given filter criteria");
		Status st = new Status();
		try {
			List<CustomerProfile> list = customerProfileService.searchCustomerProfile(requestObj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("search data get SUCCESS : " + list);
			st.setObject(list);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Search customer profile failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Search customer profile failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}

	@DeleteMapping("/deleteCustomerProfile/{id}")
	public ResponseEntity<Status> deleteCustomerProfile(@PathVariable Long id) {
		logger.info("Request to delete a customer");
		Status st = new Status();
		try {
			customerProfileService.deleteCustomerProfile(id);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Customer profile deleted successfully");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Throwable e) {
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Delete customer profile failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}

	@GetMapping("/getAllCustomerProfile")
	private ResponseEntity<Status> getAllCustomerProfile() {
		logger.info("Request to get all service");
		Status st = new Status();
		try {
			List<CustomerProfile> list = customerProfileService.getAllCustomerProfile();
			if (list == null) {
				logger.error("Search all customer profile failed");
				st.setCode(HttpStatus.EXPECTATION_FAILED.value());
				st.setType("ERROR");
				st.setMessage("Search all customer profile failed");
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
			}
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Search all customer profile successful");
			st.setObject(list);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Search all customer profile failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Search all customer profile failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
}

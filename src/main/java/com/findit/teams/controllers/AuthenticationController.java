/**
 * 
 */
package com.findit.teams.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.findit.teams.business.service.CustomerService;
import com.findit.teams.domain.Customer;
import com.findit.teams.domain.Status;
import com.findit.teams.repository.CustomerRepository;
import com.findit.teams.web.rest.errors.CustomerNotResigterException;

/**
 * @author Aditya
 */
@RestController
@RequestMapping("/api")
public class AuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	private static final String SUC_MSG = "{\"message\": \"SUCCESS\"}";

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	CustomerService customerService;
	@PostMapping("/userAuthentication")
	public ResponseEntity<Status> userAuthentication(@RequestBody ObjectNode obj) throws JSONException {
		logger.info("Request to add a custom");
		Status st = new Status();
		try {
			Customer User = customerService.userAuthentication(obj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Customer added successfully");
			st.setObject(User);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		}
		catch (Exception e) {
			logger.error("Add customer failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Add customer failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
}

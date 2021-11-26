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
import com.findit.teams.business.service.TaxService;
import com.findit.teams.domain.Status;
import com.findit.teams.domain.TaxDoamin;

@RestController
@RequestMapping("/api")
public class TaxServiceController {
	@Autowired
	TaxService taxService;
	private static final Logger logger = LoggerFactory.getLogger(TaxServiceController.class);
	
	
	@PostMapping("/addService")
	public ResponseEntity<Status> addService(@RequestBody ObjectNode obj) throws JSONException {
		logger.info("Request to add a service");
		Status st = new Status();
		try {
			TaxDoamin service = taxService.addService(obj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Service added successfully");
			st.setObject(service);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Add service failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Add service failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	@PostMapping("/updateService")
	public ResponseEntity<Status> updateTaxDoamin(@RequestBody ObjectNode obj) throws JSONException, URISyntaxException {
		logger.info("Request to update a service");
		Status st = new Status();
		try {
			TaxDoamin service = taxService.updateService(obj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Service updated successfully");
			st.setObject(service);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Updating service failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Updating service failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}

	@GetMapping("/searchService")
	public ResponseEntity<Status> searchTaxDoamin(@RequestParam Map<String, String> requestObj) {
		logger.info("Request to search srvice on given filter criteria");
		Status st = new Status();
		try {
			List<TaxDoamin> list = taxService.searchService(requestObj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("search data get SUCCESS : " + list);
			st.setObject(list);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Search service failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Search service failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}

	@DeleteMapping("/deleteService/{id}")
	public ResponseEntity<Status> deleteService(@PathVariable Long id) {
		logger.info("Request to delete a service");
		Status st = new Status();
		try {
			taxService.deleteService(id);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Service deleted successfully");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Throwable e) {
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Delete service failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}

	@GetMapping("/getAllService")
	private ResponseEntity<Status> getAllService() {
		logger.info("Request to get all service");
		Status st = new Status();
		try {
			List<TaxDoamin> list = taxService.getAllService();
			if (list == null) {
				logger.error("Search all service failed");
				st.setCode(HttpStatus.EXPECTATION_FAILED.value());
				st.setType("ERROR");
				st.setMessage("Search all department failed");
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
			}
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Search all service successful");
			st.setObject(list);
			return ResponseEntity.status(HttpStatus.OK).body(st);
		} catch (Exception e) {
			logger.error("Search all service failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Search all service failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
//	@GetMapping("/getAllActiveServices")
//	private ResponseEntity<Status> getAllActiveServices() {
//		logger.info("Request to get all active services");
//		Status st = new Status();
//		try {
//			List<TaxDoamin> list = taxService.getAllActiveServices();
//			if (list == null) {
//				logger.error("Search all active services failed");
//				st.setCode(HttpStatus.EXPECTATION_FAILED.value());
//				st.setType("ERROR");
//				st.setMessage("Search all services failed");
//				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
//			}
//			st.setCode(HttpStatus.OK.value());
//			st.setType("SUCCESS");
//			st.setMessage("Search all active service successful");
//			st.setObject(list);
//			return ResponseEntity.status(HttpStatus.OK).body(st);
//		} catch (Exception e) {
//			logger.error("Search all active services failed. Exception: ", e);
//			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
//			st.setType("ERROR");
//			st.setMessage("Search all active services failed");
//			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
//		}
//	}
	
}

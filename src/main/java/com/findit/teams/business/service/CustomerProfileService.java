package com.findit.teams.business.service;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.findit.teams.config.Constants;
import com.findit.teams.domain.CustomerProfile;
import com.findit.teams.repository.CustomerProfileRepository;

@Service
public class CustomerProfileService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerProfileService.class);

	@Autowired
	private CustomerProfileRepository customerProfileRepository;
	
	public CustomerProfile addCustomerProfile(ObjectNode obj) throws JSONException {
		CustomerProfile customerProfile= new CustomerProfile();

		if (obj.get("firstName") != null) {
			customerProfile.setFirstName(obj.get("firstName").asText());
		}
		if (obj.get("middleName") != null) {
			customerProfile.setMiddleName(obj.get("middleName").asText());
		}
		if (obj.get("lastName") != null) {
			customerProfile.setLastName(obj.get("lastName").asText());
		}
		if (obj.get("url") != null) {
			customerProfile.setUrl(obj.get("url").asText());
		}
		if (obj.get("aadhar") != null) {
			customerProfile.setAadhar(obj.get("aadhar").asText());
		}
		if (obj.get("address") != null) {
			customerProfile.setAddress(obj.get("address").asText());
		}
		if (obj.get("status") != null) {
			customerProfile.setStatus(obj.get("status").asText());
		}
		if (obj.get("user") != null) {
			customerProfile.setCreatedBy(obj.get("user").asText());
			customerProfile.setUpdatedBy(obj.get("user").asText());
		} else {
			customerProfile.setCreatedBy(Constants.SYSTEM_ACCOUNT);
			customerProfile.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
		}

		Instant now = Instant.now();
		customerProfile.setCreatedOn(now);
		customerProfile.setUpdatedOn(now);
		customerProfile = customerProfileRepository.save(customerProfile);
		logger.info("Customer profile added successfully: " + customerProfile.toString());
		return customerProfile;
	}

	public CustomerProfile updateCustomerProfile(ObjectNode obj) throws JSONException, URISyntaxException {
		Optional<CustomerProfile> cup = customerProfileRepository.findById(Long.parseLong(obj.get("id").asText()));
		if (!cup.isPresent()) {
			logger.error("Customer profile not found");
			return null;
		}
		CustomerProfile customerProfile = cup.get();
		if (obj.get("firstName") != null) {
			customerProfile.setFirstName(obj.get("firstName").asText());
		}
		if (obj.get("middleName") != null) {
			customerProfile.setMiddleName(obj.get("middleName").asText());
		}
		if (obj.get("lastName") != null) {
			customerProfile.setLastName(obj.get("lastName").asText());
		}
		if (obj.get("url") != null) {
			customerProfile.setUrl(obj.get("url").asText());
		}
		if (obj.get("aadhar") != null) {
			customerProfile.setAadhar(obj.get("aadhar").asText());
		}
		if (obj.get("address") != null) {
			customerProfile.setAddress(obj.get("address").asText());
		}
		if (obj.get("status") != null) {
			customerProfile.setStatus(obj.get("status").asText());
		}
		if (obj.get("user") != null) {
			customerProfile.setCreatedBy(obj.get("user").asText());
			customerProfile.setUpdatedBy(obj.get("user").asText());
		} else {
			customerProfile.setCreatedBy(Constants.SYSTEM_ACCOUNT);
			customerProfile.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
		}

		Instant now = Instant.now();
		customerProfile.setCreatedOn(now);
		customerProfile.setUpdatedOn(now);
		customerProfile = customerProfileRepository.save(customerProfile);
		logger.info("Customer profile upadte successfully: " + customerProfile.toString());
		return customerProfile;
	}

	public List<CustomerProfile> searchCustomerProfile(@RequestParam Map<String, String> requestObj) {
		CustomerProfile customerProfile= new CustomerProfile();
		boolean isFilter = false;
		if (requestObj.get("id") != null) {
			customerProfile.setId(Long.parseLong(requestObj.get("id")));
			isFilter = true;
		}

		if (requestObj.get("firstName") != null) {
			customerProfile.setFirstName(requestObj.get("firstName"));
		}
		if (requestObj.get("middleName") != null) {
			customerProfile.setMiddleName(requestObj.get("middleName"));
		}
	
		if (requestObj.get("lastName") != null) {
			customerProfile.setLastName(requestObj.get("lastName"));
		}
		if (requestObj.get("url") != null) {
			customerProfile.setUrl(requestObj.get("url"));
		}
		if (requestObj.get("aadhar") != null) {
			customerProfile.setAadhar(requestObj.get("aadhar"));
		}
		if (requestObj.get("address") != null) {
			customerProfile.setAddress(requestObj.get("address"));
		}
		if (requestObj.get("status") != null) {
			customerProfile.setStatus(requestObj.get("status"));
		}
		if (requestObj.get("createdOn") != null) {
			Instant inst = Instant.parse(requestObj.get("createdOn"));
			customerProfile.setCreatedOn(inst);
			isFilter = true;
		}
		if (requestObj.get("createdBy") != null) {
			customerProfile.setCreatedBy(requestObj.get("createdBy"));
			isFilter = true;
		}
		if (requestObj.get("updatedOn") != null) {
			Instant inst = Instant.parse(requestObj.get("updatedOn"));
			customerProfile.setUpdatedOn(inst);
			isFilter = true;
		}
		if (requestObj.get("updatedBy") != null) {
			customerProfile.setUpdatedBy(requestObj.get("updatedBy"));
			isFilter = true;
		}
		List<CustomerProfile> list = null;
		if (isFilter) {
			list = this.customerProfileRepository.findAll(Example.of(customerProfile), Sort.by(Direction.DESC, "id"));
		} else {
			list = this.customerProfileRepository.findAll(Sort.by(Direction.DESC, "id"));
		}

		logger.info("Customer profile search completed. Total records: " + list.size());

		return list;
	}
	public List<CustomerProfile> getAllCustomerProfile() {
		List<CustomerProfile> list = customerProfileRepository.findAll(Sort.by(Direction.ASC, "id"));
		logger.info("All customer profile. Total records: " + list.size());
		return list;
	}

	public void deleteCustomerProfile(Long id) {
		customerProfileRepository.deleteById(id);
		logger.info("Customer profile deleted successfully");
	}
}

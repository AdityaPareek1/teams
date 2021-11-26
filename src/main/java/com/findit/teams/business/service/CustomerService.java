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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.findit.teams.config.Constants;
import com.findit.teams.domain.Customer;
import com.findit.teams.repository.CustomerRepository;
import com.findit.teams.web.rest.errors.CustomerNotResigterException;
import com.findit.teams.web.rest.errors.UniqueConstraintException;


@Service
public class CustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer addCustomer(ObjectNode obj) throws JSONException {
		Customer customer= new Customer();

		if (obj.get("pan") != null) {
			customer.setPan(obj.get("pan").asText());
		}
		if (obj.get("aadhar") != null) {
			customer.setAadhar(obj.get("aadhar").asText());
		}
		if (obj.get("email") != null) {
			customer.setEmail(obj.get("email").asText());
		}
		if (obj.get("address") != null) {
			customer.setAddress(obj.get("address").asText());
		}
		if (obj.get("password") != null) {
			customer.setPassword(obj.get("password").asText());
		}
		if (obj.get("phoneNumber") != null) {
			customer.setPhoneNumber(obj.get("phoneNumber").asText());
		}
		if (obj.get("status") != null) {
			customer.setStatus(obj.get("status").asText());
		}
		if (obj.get("user") != null) {
			customer.setCreatedBy(obj.get("user").asText());
			customer.setUpdatedBy(obj.get("user").asText());
		} else {
			customer.setCreatedBy(Constants.SYSTEM_ACCOUNT);
			customer.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
		}

		Instant now = Instant.now();
		customer.setCreatedOn(now);
		customer.setUpdatedOn(now);
		customer = customerRepository.save(customer);
		logger.info("Service added successfully: " + customer.toString());
		return customer;
	}

	public Customer updateCustomer(ObjectNode obj) throws JSONException, URISyntaxException {
		Optional<Customer> cu = customerRepository.findById(Long.parseLong(obj.get("id").asText()));
		if (!cu.isPresent()) {
			logger.error("Customer not found");
			return null;
		}
		Customer customer = cu.get();
		if (obj.get("pan") != null) {
			customer.setPan(obj.get("pan").asText());
		}
		if (obj.get("aadhar") != null) {
			customer.setAadhar(obj.get("aadhar").asText());
		}
		if (obj.get("email") != null) {
			customer.setEmail(obj.get("email").asText());
		}
		if (obj.get("address") != null) {
			customer.setAddress(obj.get("address").asText());
		}
		if (obj.get("password") != null) {
			customer.setPassword(obj.get("password").asText());
		}
		if (obj.get("phoneNumber") != null) {
			customer.setPhoneNumber(obj.get("phoneNumber").asText());
		}
		if (obj.get("status") != null) {
			customer.setStatus(obj.get("status").asText());
		}
		if (obj.get("user") != null) {
			customer.setCreatedBy(obj.get("user").asText());
			customer.setUpdatedBy(obj.get("user").asText());
		} else {
			customer.setCreatedBy(Constants.SYSTEM_ACCOUNT);
			customer.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
		}

		Instant now = Instant.now();
		customer.setCreatedOn(now);
		customer.setUpdatedOn(now);
		customer = customerRepository.save(customer);
		logger.info("Customer added successfully: " + customer.toString());
		return customer;
	}

	public List<Customer> searchCustomer(@RequestParam Map<String, String> requestObj) {
		Customer customer= new Customer();
		boolean isFilter = false;
		if (requestObj.get("id") != null) {
			customer.setId(Long.parseLong(requestObj.get("id")));
			isFilter = true;
		}

		if (requestObj.get("pan") != null) {
			customer.setPan(requestObj.get("pan"));
		}
		if (requestObj.get("aadhar") != null) {
			customer.setAadhar(requestObj.get("aadhar"));
		}
		if (requestObj.get("status") != null) {
			customer.setStatus(requestObj.get("status"));
		}
		if (requestObj.get("phoneNumber") != null) {
			customer.setPhoneNumber(requestObj.get("phoneNumber"));
		}
		if (requestObj.get("email") != null) {
			customer.setEmail(requestObj.get("email"));
		}
		if (requestObj.get("address") != null) {
			customer.setAddress(requestObj.get("address"));
		}
		if (requestObj.get("password") != null) {
			customer.setPassword(requestObj.get("password"));
		}
		if (requestObj.get("createdOn") != null) {
			Instant inst = Instant.parse(requestObj.get("createdOn"));
			customer.setCreatedOn(inst);
			isFilter = true;
		}

		if (requestObj.get("createdBy") != null) {
			customer.setCreatedBy(requestObj.get("createdBy"));
			isFilter = true;
		}
		if (requestObj.get("updatedOn") != null) {
			Instant inst = Instant.parse(requestObj.get("updatedOn"));
			customer.setUpdatedOn(inst);
			isFilter = true;
		}
		if (requestObj.get("updatedBy") != null) {
			customer.setUpdatedBy(requestObj.get("updatedBy"));
			isFilter = true;
		}
		List<Customer> list = null;
		if (isFilter) {
			list = this.customerRepository.findAll(Example.of(customer), Sort.by(Direction.DESC, "id"));
		} else {
			list = this.customerRepository.findAll(Sort.by(Direction.DESC, "id"));
		}

		logger.info("Customer search completed. Total records: " + list.size());

		return list;
	}
	public List<Customer> getAllCustomer() {
		List<Customer> list = customerRepository.findAll(Sort.by(Direction.ASC, "id"));
		logger.info("All customer. Total records: " + list.size());
		return list;
	}

	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
		logger.info("Customer deleted successfully");
	}
	public Customer userAuthentication(ObjectNode obj) throws Exception {
		List<Customer> users = customerRepository.findAll(Sort.by(Direction.ASC, "id"));
		Customer customer= new Customer();
		List<Customer> List = null;
//		for (Customer cust : users) {
//			String phoneNumberByenv= obj.get("phone").asText();
//			if(cust.getPhoneNumber().equalsIgnoreCase(phoneNumberByenv)) {	
//				if(cust.getPassword().equalsIgnoreCase(obj.get("password").asText())) {
//					 System.out.println("password okk");
//				}else {
//					UniqueConstraintException ex = new UniqueConstraintException("password not found");
//		        	throw ex;
//				}
//				
//			}else {
//				
//				try {
//					UniqueConstraintException ex = new UniqueConstraintException("Customer not register");
//					throw ex;
//				} catch (Exception e) {
//					// TODO: handle exception
//					logger.info(e.getMessage());
//			}
//	}
//		}
		return customer;
	}
}

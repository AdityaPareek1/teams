package com.findit.teams.business.service;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.findit.teams.config.Constants;
import com.findit.teams.domain.CustomerQuery;
import com.findit.teams.domain.CustomerQueryActivity;
import com.findit.teams.domain.TaxDoamin;
import com.findit.teams.domain.TaxServiceActivity;
import com.findit.teams.repository.CustomerQueryActivityRepository;
import com.findit.teams.repository.CustomerQueryRepository;

@Service
public class CustomerQueryService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerQueryService.class);

	@Autowired
	private CustomerQueryRepository customerQueryRepository;
	@Autowired
	private CustomerQueryActivityRepository customerQueryActivityRepository;
	
	public CustomerQuery addCustomerQuery(ObjectNode obj) throws JSONException {
		CustomerQuery customerQuery= new CustomerQuery();

		if (obj.get("phoneNumber") != null) {
			customerQuery.setPhoneNumber(obj.get("phoneNumber").asText());
		}
		if (obj.get("email") != null) {
			customerQuery.setEmail(obj.get("email").asText());
		}
		if (obj.get("category") != null) {
			customerQuery.setCategory(obj.get("category").asText());
		}
		if (obj.get("serviceType") != null) {
			customerQuery.setServiceType(obj.get("serviceType").asText());
		}
		if (obj.get("comments") != null) {
			customerQuery.setComments(obj.get("comments").asText());
		}
		if (obj.get("message") != null) {
			customerQuery.setMessage(obj.get("message").asText());
		}
		customerQuery.setStatus(Constants.STATUS_DRAFT);
		
		if (obj.get("user") != null) {
			customerQuery.setCreatedBy(obj.get("user").asText());
			customerQuery.setUpdatedBy(obj.get("user").asText());
		} else {
			customerQuery.setCreatedBy(Constants.SYSTEM_ACCOUNT);
			customerQuery.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
		}

		Instant now = Instant.now();
		customerQuery.setCreatedOn(now);
		customerQuery.setUpdatedOn(now);
		customerQuery = customerQueryRepository.save(customerQuery);
		if (customerQuery != null) {
			CustomerQueryActivity customerQueryActivity = new CustomerQueryActivity();
			BeanUtils.copyProperties(customerQuery, customerQueryActivity);
			customerQueryActivity.setCustomerQueryId(customerQuery.getId());;
			customerQueryActivity = customerQueryActivityRepository.save(customerQueryActivity);
			logger.info("Service activity added successfully");
		}
		
		logger.info("Customer query added successfully: " + customerQuery.toString());
		return customerQuery;
	}

	public CustomerQuery updateCustomerQuery(ObjectNode obj) throws JSONException, URISyntaxException {
		Optional<CustomerQuery> cup = customerQueryRepository.findById(Long.parseLong(obj.get("id").asText()));
		if (!cup.isPresent()) {
			logger.error("Customer query not found");
			return null;
		}
		CustomerQuery customerQuery = cup.get();
		if (obj.get("phoneNumber") != null) {
			customerQuery.setPhoneNumber(obj.get("phoneNumber").asText());
		}
		if (obj.get("email") != null) {
			customerQuery.setEmail(obj.get("email").asText());
		}
		if (obj.get("category") != null) {
			customerQuery.setCategory(obj.get("category").asText());
		}
		if (obj.get("serviceType") != null) {
			customerQuery.setServiceType(obj.get("serviceType").asText());
		}
		if (obj.get("comments") != null) {
			customerQuery.setComments(obj.get("comments").asText());
		}
		if (obj.get("message") != null) {
			customerQuery.setMessage(obj.get("message").asText());
		}
		if (obj.get("status") != null) {
			customerQuery.setStatus(obj.get("status").asText());
		}
		if (obj.get("user") != null) {
			customerQuery.setCreatedBy(obj.get("user").asText());
			customerQuery.setUpdatedBy(obj.get("user").asText());
		} else {
			customerQuery.setCreatedBy(Constants.SYSTEM_ACCOUNT);
			customerQuery.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
		}

		Instant now = Instant.now();
		customerQuery.setCreatedOn(now);
		customerQuery.setUpdatedOn(now);
		customerQuery = customerQueryRepository.save(customerQuery);
		if (customerQuery != null) {
			CustomerQueryActivity customerQueryActivity = new CustomerQueryActivity();
			BeanUtils.copyProperties(customerQuery, customerQueryActivity);
			customerQueryActivity.setCustomerQueryId(customerQuery.getId());;
			customerQueryActivity = customerQueryActivityRepository.save(customerQueryActivity);
			logger.info("Service activity added successfully");
		}
		logger.info("Customer query upadte successfully: " + customerQuery.toString());
		return customerQuery;
	}

	public List<CustomerQuery> searchCustomerQuery(@RequestParam Map<String, String> requestObj) {
		CustomerQuery customerQuery= new CustomerQuery();
		boolean isFilter = false;
		if (requestObj.get("id") != null) {
			customerQuery.setId(Long.parseLong(requestObj.get("id")));
			isFilter = true;
		}

		if (requestObj.get("phoneNumber") != null) {
			customerQuery.setPhoneNumber(requestObj.get("phoneNumber"));
		}
		if (requestObj.get("email") != null) {
			customerQuery.setEmail(requestObj.get("email"));
		}
	
		if (requestObj.get("category") != null) {
			customerQuery.setCategory(requestObj.get("category"));
		}
		if (requestObj.get("serviceType") != null) {
			customerQuery.setServiceType(requestObj.get("serviceType"));
		}
		if (requestObj.get("comments") != null) {
			customerQuery.setComments(requestObj.get("comments"));
		}
		if (requestObj.get("message") != null) {
			customerQuery.setMessage(requestObj.get("message"));
		}
		if (requestObj.get("status") != null) {
			customerQuery.setStatus(requestObj.get("status"));
		}
		if (requestObj.get("createdOn") != null) {
			Instant inst = Instant.parse(requestObj.get("createdOn"));
			customerQuery.setCreatedOn(inst);
			isFilter = true;
		}
		if (requestObj.get("createdBy") != null) {
			customerQuery.setCreatedBy(requestObj.get("createdBy"));
			isFilter = true;
		}
		if (requestObj.get("updatedOn") != null) {
			Instant inst = Instant.parse(requestObj.get("updatedOn"));
			customerQuery.setUpdatedOn(inst);
			isFilter = true;
		}
		if (requestObj.get("updatedBy") != null) {
			customerQuery.setUpdatedBy(requestObj.get("updatedBy"));
			isFilter = true;
		}
		List<CustomerQuery> list = null;
		if (isFilter) {
			list = this.customerQueryRepository.findAll(Example.of(customerQuery), Sort.by(Direction.DESC, "id"));
		} else {
			list = this.customerQueryRepository.findAll(Sort.by(Direction.DESC, "id"));
		}

		logger.info("Customer query search completed. Total records: " + list.size());

		return list;
	}
	public List<CustomerQuery> getAllCustomerQuery() {
		List<CustomerQuery> list = customerQueryRepository.findAll(Sort.by(Direction.ASC, "id"));
		logger.info("All customer query. Total records: " + list.size());
		return list;
	}

	public void deleteCustomerQuery(Long id) {
		customerQueryRepository.deleteById(id);
		logger.info("Customer query deleted successfully");
	}
}

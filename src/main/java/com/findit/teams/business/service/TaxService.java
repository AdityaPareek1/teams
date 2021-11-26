package com.findit.teams.business.service;

import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.findit.teams.domain.TaxDoamin;
import com.findit.teams.domain.TaxServiceActivity;
import com.findit.teams.repository.TaxServiceActivityRepository;
import com.findit.teams.repository.TaxServiceRepository;

@Service
public class TaxService {
	private static final Logger logger = LoggerFactory.getLogger(TaxService.class);

	@Autowired
	private TaxServiceRepository taxServiceRepository;
	
	@Autowired
	private TaxServiceActivityRepository taxServiceActivityRepository;


	public TaxDoamin addService(ObjectNode obj) throws JSONException {
		TaxDoamin service = new TaxDoamin();

		if (obj.get("name") != null) {
			service.setName(obj.get("name").asText());
		}
		if (obj.get("description") != null) {
			service.setDescription(obj.get("description").asText());
		}
		if (obj.get("serviceUrl") != null) {
			service.setServiceUrl(obj.get("serviceUrl").asText());
		}else {
			service.setServiceUrl(Constants.DEFAULT_URL);
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_FORMAT);
		if (obj.get("startDate") != null) {
			LocalDate localDate = LocalDate.parse(obj.get("startDate").asText(), formatter);
			service.setStartDate(localDate);
		} 
		if (obj.get("endDate") != null) {
			LocalDate localDate = LocalDate.parse(obj.get("endDate").asText(), formatter);
			service.setEndDate(localDate);
		} 
		if (obj.get("comments") != null) {
			service.setComments(obj.get("comments").asText());
		}
		
		service.setStatus(Constants.STATUS_DRAFT);
		
		if (obj.get("user") != null) {
			service.setCreatedBy(obj.get("user").asText());
			service.setUpdatedBy(obj.get("user").asText());
		} else {
			service.setCreatedBy(Constants.SYSTEM_ACCOUNT);
			service.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
		}

		Instant now = Instant.now();
		service.setCreatedOn(now);
		service.setUpdatedOn(now);
		service = taxServiceRepository.save(service);
		
		if (service != null) {
			TaxServiceActivity taxserviceActivity = new TaxServiceActivity();
			BeanUtils.copyProperties(service, taxserviceActivity);
			taxserviceActivity.setTaxServiceId(service.getId());;
			taxserviceActivity = taxServiceActivityRepository.save(taxserviceActivity);
			logger.info("Service activity added successfully");
		}
		logger.info("Service added successfully: " + service.toString());
		

		return service;
	}

	public TaxDoamin updateService(ObjectNode obj) throws JSONException, URISyntaxException {
		Optional<TaxDoamin> bu = taxServiceRepository.findById(Long.parseLong(obj.get("id").asText()));
		if (!bu.isPresent()) {
			logger.error("Service not found");
			return null;
		}
		TaxDoamin service = bu.get();
		if (obj.get("name") != null) {
			service.setName(obj.get("name").asText());
		}
		if (obj.get("description") != null) {
			service.setDescription(obj.get("description").asText());
		}
		if (obj.get("serviceUrl") != null) {
			service.setServiceUrl(obj.get("serviceUrl").asText());
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_FORMAT);
		if (obj.get("startDate") != null) {
			LocalDate localDate = LocalDate.parse(obj.get("startDate").asText(), formatter);
			service.setStartDate(localDate);
		} 
		if (obj.get("endDate") != null) {
			LocalDate localDate = LocalDate.parse(obj.get("endDate").asText(), formatter);
			service.setEndDate(localDate);
		} 
		if (obj.get("comments") != null) {
			service.setComments(obj.get("comments").asText());
		}
		if (obj.get("status") != null) {
			service.setStatus(obj.get("status").asText().toUpperCase());
		}
		if (obj.get("user") != null) {
			service.setUpdatedBy(obj.get("user").asText());
		} else {
			service.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
		}

		Instant now = Instant.now();
		service.setUpdatedOn(now);
		service = taxServiceRepository.save(service);

		if (service != null) {
			TaxServiceActivity taxserviceActivity = new TaxServiceActivity();
			BeanUtils.copyProperties(service, taxserviceActivity);
			taxserviceActivity.setTaxServiceId(service.getId());;
			taxserviceActivity = taxServiceActivityRepository.save(taxserviceActivity);
			logger.info("Service activity added successfully");
		}
		logger.info("Service update successfully" + service.toString());
		
		return service;
	}

	public List<TaxDoamin> searchService(@RequestParam Map<String, String> requestObj) {
		TaxDoamin service = new TaxDoamin();
		boolean isFilter = false;
		if (requestObj.get("id") != null) {
			service.setId(Long.parseLong(requestObj.get("id")));
			isFilter = true;
		}

		if (requestObj.get("serviceName") != null) {
			service.setName(requestObj.get("serviceName"));
		}
		if (requestObj.get("serviceUrl") != null) {
			service.setServiceUrl(requestObj.get("serviceUrl"));
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_FORMAT);
		if (requestObj.get("startDate") != null) {
			LocalDate localDate = LocalDate.parse(requestObj.get("startDate"), formatter);
			service.setStartDate(localDate);
		} 
		if (requestObj.get("endDate") != null) {
			LocalDate localDate = LocalDate.parse(requestObj.get("endDate"), formatter);
			service.setEndDate(localDate);
		} 
		
		if (requestObj.get("comments") != null) {
			service.setComments(requestObj.get("comments"));
		}
		if (requestObj.get("status") != null) {
			service.setStatus(requestObj.get("status").toUpperCase());
		}
		if (requestObj.get("createdOn") != null) {
			Instant inst = Instant.parse(requestObj.get("createdOn"));
			service.setCreatedOn(inst);
			isFilter = true;
		}

		if (requestObj.get("createdBy") != null) {
			service.setCreatedBy(requestObj.get("createdBy"));
			isFilter = true;
		}
		if (requestObj.get("updatedOn") != null) {
			Instant inst = Instant.parse(requestObj.get("updatedOn"));
			service.setUpdatedOn(inst);
			isFilter = true;
		}
		if (requestObj.get("updatedBy") != null) {
			service.setUpdatedBy(requestObj.get("updatedBy"));
			isFilter = true;
		}
		List<TaxDoamin> list = null;
		if (isFilter) {
			list = this.taxServiceRepository.findAll(Example.of(service), Sort.by(Direction.DESC, "id"));
		} else {
			list = this.taxServiceRepository.findAll(Sort.by(Direction.DESC, "id"));
		}

		logger.info("Service search completed. Total records: " + list.size());

		return list;
	}
	public List<TaxDoamin> getAllService() {
		List<TaxDoamin> list = taxServiceRepository.findAll(Sort.by(Direction.ASC, "id"));
		logger.info("All service. Total records: " + list.size());
		return list;
	}

//	public List<TaxDoamin> getAllActiveServices() {
//		List<TaxDoamin> list = taxServiceRepository.getAllActiveServices();
//		logger.info("All active service. Total records: " + list.size());
//		return list;
//	}
	
	public void deleteService(Long id) {
		taxServiceRepository.deleteById(id);
		logger.info("Service deleted successfully");
	}
}

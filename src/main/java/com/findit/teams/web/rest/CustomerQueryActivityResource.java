package com.findit.teams.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.findit.teams.domain.CustomerQueryActivity;
import com.findit.teams.repository.CustomerQueryActivityRepository;
import com.findit.teams.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.findit.teams.domain.CustomerQueryActivity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerQueryActivityResource {

    private final Logger log = LoggerFactory.getLogger(CustomerQueryActivityResource.class);

    private static final String ENTITY_NAME = "taxfileserviceCustomerQueryActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerQueryActivityRepository customerQueryActivityRepository;

    public CustomerQueryActivityResource(CustomerQueryActivityRepository customerQueryActivityRepository) {
        this.customerQueryActivityRepository = customerQueryActivityRepository;
    }

    /**
     * {@code POST  /customer-query-activities} : Create a new customerQueryActivity.
     *
     * @param customerQueryActivity the customerQueryActivity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerQueryActivity, or with status {@code 400 (Bad Request)} if the customerQueryActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-query-activities")
    public ResponseEntity<CustomerQueryActivity> createCustomerQueryActivity(@RequestBody CustomerQueryActivity customerQueryActivity)
        throws URISyntaxException {
        log.debug("REST request to save CustomerQueryActivity : {}", customerQueryActivity);
        if (customerQueryActivity.getId() != null) {
            throw new BadRequestAlertException("A new customerQueryActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerQueryActivity result = customerQueryActivityRepository.save(customerQueryActivity);
        return ResponseEntity
            .created(new URI("/api/customer-query-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-query-activities/:id} : Updates an existing customerQueryActivity.
     *
     * @param id the id of the customerQueryActivity to save.
     * @param customerQueryActivity the customerQueryActivity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerQueryActivity,
     * or with status {@code 400 (Bad Request)} if the customerQueryActivity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerQueryActivity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-query-activities/{id}")
    public ResponseEntity<CustomerQueryActivity> updateCustomerQueryActivity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomerQueryActivity customerQueryActivity
    ) throws URISyntaxException {
        log.debug("REST request to update CustomerQueryActivity : {}, {}", id, customerQueryActivity);
        if (customerQueryActivity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerQueryActivity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerQueryActivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomerQueryActivity result = customerQueryActivityRepository.save(customerQueryActivity);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customerQueryActivity.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customer-query-activities/:id} : Partial updates given fields of an existing customerQueryActivity, field will ignore if it is null
     *
     * @param id the id of the customerQueryActivity to save.
     * @param customerQueryActivity the customerQueryActivity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerQueryActivity,
     * or with status {@code 400 (Bad Request)} if the customerQueryActivity is not valid,
     * or with status {@code 404 (Not Found)} if the customerQueryActivity is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerQueryActivity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customer-query-activities/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustomerQueryActivity> partialUpdateCustomerQueryActivity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomerQueryActivity customerQueryActivity
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomerQueryActivity partially : {}, {}", id, customerQueryActivity);
        if (customerQueryActivity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerQueryActivity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerQueryActivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomerQueryActivity> result = customerQueryActivityRepository
            .findById(customerQueryActivity.getId())
            .map(
                existingCustomerQueryActivity -> {
                    if (customerQueryActivity.getPhoneNumber() != null) {
                        existingCustomerQueryActivity.setPhoneNumber(customerQueryActivity.getPhoneNumber());
                    }
                    if (customerQueryActivity.getEmail() != null) {
                        existingCustomerQueryActivity.setEmail(customerQueryActivity.getEmail());
                    }
                    if (customerQueryActivity.getCategory() != null) {
                        existingCustomerQueryActivity.setCategory(customerQueryActivity.getCategory());
                    }
                    if (customerQueryActivity.getServiceType() != null) {
                        existingCustomerQueryActivity.setServiceType(customerQueryActivity.getServiceType());
                    }
                    if (customerQueryActivity.getMessage() != null) {
                        existingCustomerQueryActivity.setMessage(customerQueryActivity.getMessage());
                    }
                    if (customerQueryActivity.getComments() != null) {
                        existingCustomerQueryActivity.setComments(customerQueryActivity.getComments());
                    }
                    if (customerQueryActivity.getStatus() != null) {
                        existingCustomerQueryActivity.setStatus(customerQueryActivity.getStatus());
                    }
                    if (customerQueryActivity.getCreatedOn() != null) {
                        existingCustomerQueryActivity.setCreatedOn(customerQueryActivity.getCreatedOn());
                    }
                    if (customerQueryActivity.getCreatedBy() != null) {
                        existingCustomerQueryActivity.setCreatedBy(customerQueryActivity.getCreatedBy());
                    }
                    if (customerQueryActivity.getUpdatedOn() != null) {
                        existingCustomerQueryActivity.setUpdatedOn(customerQueryActivity.getUpdatedOn());
                    }
                    if (customerQueryActivity.getUpdatedBy() != null) {
                        existingCustomerQueryActivity.setUpdatedBy(customerQueryActivity.getUpdatedBy());
                    }
                    if (customerQueryActivity.getCustomerQueryId() != null) {
                        existingCustomerQueryActivity.setCustomerQueryId(customerQueryActivity.getCustomerQueryId());
                    }

                    return existingCustomerQueryActivity;
                }
            )
            .map(customerQueryActivityRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customerQueryActivity.getId().toString())
        );
    }

    /**
     * {@code GET  /customer-query-activities} : get all the customerQueryActivities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerQueryActivities in body.
     */
    @GetMapping("/customer-query-activities")
    public List<CustomerQueryActivity> getAllCustomerQueryActivities() {
        log.debug("REST request to get all CustomerQueryActivities");
        return customerQueryActivityRepository.findAll();
    }

    /**
     * {@code GET  /customer-query-activities/:id} : get the "id" customerQueryActivity.
     *
     * @param id the id of the customerQueryActivity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerQueryActivity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-query-activities/{id}")
    public ResponseEntity<CustomerQueryActivity> getCustomerQueryActivity(@PathVariable Long id) {
        log.debug("REST request to get CustomerQueryActivity : {}", id);
        Optional<CustomerQueryActivity> customerQueryActivity = customerQueryActivityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerQueryActivity);
    }

    /**
     * {@code DELETE  /customer-query-activities/:id} : delete the "id" customerQueryActivity.
     *
     * @param id the id of the customerQueryActivity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-query-activities/{id}")
    public ResponseEntity<Void> deleteCustomerQueryActivity(@PathVariable Long id) {
        log.debug("REST request to delete CustomerQueryActivity : {}", id);
        customerQueryActivityRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

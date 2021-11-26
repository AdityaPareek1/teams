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

import com.findit.teams.domain.CustomerQuery;
import com.findit.teams.repository.CustomerQueryRepository;
import com.findit.teams.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.findit.teams.domain.CustomerQuery}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerQueryResource {

    private final Logger log = LoggerFactory.getLogger(CustomerQueryResource.class);

    private static final String ENTITY_NAME = "taxfileserviceCustomerQuery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerQueryRepository customerQueryRepository;

    public CustomerQueryResource(CustomerQueryRepository customerQueryRepository) {
        this.customerQueryRepository = customerQueryRepository;
    }

    /**
     * {@code POST  /customer-queries} : Create a new customerQuery.
     *
     * @param customerQuery the customerQuery to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerQuery, or with status {@code 400 (Bad Request)} if the customerQuery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-queries")
    public ResponseEntity<CustomerQuery> createCustomerQuery(@RequestBody CustomerQuery customerQuery) throws URISyntaxException {
        log.debug("REST request to save CustomerQuery : {}", customerQuery);
        if (customerQuery.getId() != null) {
            throw new BadRequestAlertException("A new customerQuery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerQuery result = customerQueryRepository.save(customerQuery);
        return ResponseEntity
            .created(new URI("/api/customer-queries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-queries/:id} : Updates an existing customerQuery.
     *
     * @param id the id of the customerQuery to save.
     * @param customerQuery the customerQuery to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerQuery,
     * or with status {@code 400 (Bad Request)} if the customerQuery is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerQuery couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-queries/{id}")
    public ResponseEntity<CustomerQuery> updateCustomerQuery(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomerQuery customerQuery
    ) throws URISyntaxException {
        log.debug("REST request to update CustomerQuery : {}, {}", id, customerQuery);
        if (customerQuery.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerQuery.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerQueryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomerQuery result = customerQueryRepository.save(customerQuery);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customerQuery.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customer-queries/:id} : Partial updates given fields of an existing customerQuery, field will ignore if it is null
     *
     * @param id the id of the customerQuery to save.
     * @param customerQuery the customerQuery to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerQuery,
     * or with status {@code 400 (Bad Request)} if the customerQuery is not valid,
     * or with status {@code 404 (Not Found)} if the customerQuery is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerQuery couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customer-queries/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustomerQuery> partialUpdateCustomerQuery(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomerQuery customerQuery
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomerQuery partially : {}, {}", id, customerQuery);
        if (customerQuery.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerQuery.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerQueryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomerQuery> result = customerQueryRepository
            .findById(customerQuery.getId())
            .map(
                existingCustomerQuery -> {
                    if (customerQuery.getPhoneNumber() != null) {
                        existingCustomerQuery.setPhoneNumber(customerQuery.getPhoneNumber());
                    }
                    if (customerQuery.getEmail() != null) {
                        existingCustomerQuery.setEmail(customerQuery.getEmail());
                    }
                    if (customerQuery.getCategory() != null) {
                        existingCustomerQuery.setCategory(customerQuery.getCategory());
                    }
                    if (customerQuery.getServiceType() != null) {
                        existingCustomerQuery.setServiceType(customerQuery.getServiceType());
                    }
                    if (customerQuery.getMessage() != null) {
                        existingCustomerQuery.setMessage(customerQuery.getMessage());
                    }
                    if (customerQuery.getComments() != null) {
                        existingCustomerQuery.setComments(customerQuery.getComments());
                    }
                    if (customerQuery.getStatus() != null) {
                        existingCustomerQuery.setStatus(customerQuery.getStatus());
                    }
                    if (customerQuery.getCreatedOn() != null) {
                        existingCustomerQuery.setCreatedOn(customerQuery.getCreatedOn());
                    }
                    if (customerQuery.getCreatedBy() != null) {
                        existingCustomerQuery.setCreatedBy(customerQuery.getCreatedBy());
                    }
                    if (customerQuery.getUpdatedOn() != null) {
                        existingCustomerQuery.setUpdatedOn(customerQuery.getUpdatedOn());
                    }
                    if (customerQuery.getUpdatedBy() != null) {
                        existingCustomerQuery.setUpdatedBy(customerQuery.getUpdatedBy());
                    }

                    return existingCustomerQuery;
                }
            )
            .map(customerQueryRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customerQuery.getId().toString())
        );
    }

    /**
     * {@code GET  /customer-queries} : get all the customerQueries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerQueries in body.
     */
    @GetMapping("/customer-queries")
    public List<CustomerQuery> getAllCustomerQueries() {
        log.debug("REST request to get all CustomerQueries");
        return customerQueryRepository.findAll();
    }

    /**
     * {@code GET  /customer-queries/:id} : get the "id" customerQuery.
     *
     * @param id the id of the customerQuery to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerQuery, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-queries/{id}")
    public ResponseEntity<CustomerQuery> getCustomerQuery(@PathVariable Long id) {
        log.debug("REST request to get CustomerQuery : {}", id);
        Optional<CustomerQuery> customerQuery = customerQueryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerQuery);
    }

    /**
     * {@code DELETE  /customer-queries/:id} : delete the "id" customerQuery.
     *
     * @param id the id of the customerQuery to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-queries/{id}")
    public ResponseEntity<Void> deleteCustomerQuery(@PathVariable Long id) {
        log.debug("REST request to delete CustomerQuery : {}", id);
        customerQueryRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

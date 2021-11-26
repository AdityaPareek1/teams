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

import com.findit.teams.domain.CustomerProfile;
import com.findit.teams.repository.CustomerProfileRepository;
import com.findit.teams.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;


/**
 * REST controller for managing {@link com.findit.teams.domain.CustomerProfile}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerProfileResource {

    private final Logger log = LoggerFactory.getLogger(CustomerProfileResource.class);

    private static final String ENTITY_NAME = "taxfileserviceCustomerProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerProfileRepository customerProfileRepository;

    public CustomerProfileResource(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }

    /**
     * {@code POST  /customer-profiles} : Create a new customerProfile.
     *
     * @param customerProfile the customerProfile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerProfile, or with status {@code 400 (Bad Request)} if the customerProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-profiles")
    public ResponseEntity<CustomerProfile> createCustomerProfile(@RequestBody CustomerProfile customerProfile) throws URISyntaxException {
        log.debug("REST request to save CustomerProfile : {}", customerProfile);
        if (customerProfile.getId() != null) {
            throw new BadRequestAlertException("A new customerProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerProfile result = customerProfileRepository.save(customerProfile);
        return ResponseEntity
            .created(new URI("/api/customer-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-profiles/:id} : Updates an existing customerProfile.
     *
     * @param id the id of the customerProfile to save.
     * @param customerProfile the customerProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerProfile,
     * or with status {@code 400 (Bad Request)} if the customerProfile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-profiles/{id}")
    public ResponseEntity<CustomerProfile> updateCustomerProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomerProfile customerProfile
    ) throws URISyntaxException {
        log.debug("REST request to update CustomerProfile : {}, {}", id, customerProfile);
        if (customerProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomerProfile result = customerProfileRepository.save(customerProfile);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customerProfile.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customer-profiles/:id} : Partial updates given fields of an existing customerProfile, field will ignore if it is null
     *
     * @param id the id of the customerProfile to save.
     * @param customerProfile the customerProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerProfile,
     * or with status {@code 400 (Bad Request)} if the customerProfile is not valid,
     * or with status {@code 404 (Not Found)} if the customerProfile is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customer-profiles/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustomerProfile> partialUpdateCustomerProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomerProfile customerProfile
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomerProfile partially : {}, {}", id, customerProfile);
        if (customerProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomerProfile> result = customerProfileRepository
            .findById(customerProfile.getId())
            .map(
                existingCustomerProfile -> {
                    if (customerProfile.getFirstName() != null) {
                        existingCustomerProfile.setFirstName(customerProfile.getFirstName());
                    }
                    if (customerProfile.getMiddleName() != null) {
                        existingCustomerProfile.setMiddleName(customerProfile.getMiddleName());
                    }
                    if (customerProfile.getLastName() != null) {
                        existingCustomerProfile.setLastName(customerProfile.getLastName());
                    }
                    if (customerProfile.getUrl() != null) {
                        existingCustomerProfile.setUrl(customerProfile.getUrl());
                    }
                    if (customerProfile.getAadhar() != null) {
                        existingCustomerProfile.setAadhar(customerProfile.getAadhar());
                    }
                    if (customerProfile.getAddress() != null) {
                        existingCustomerProfile.setAddress(customerProfile.getAddress());
                    }
                    if (customerProfile.getStatus() != null) {
                        existingCustomerProfile.setStatus(customerProfile.getStatus());
                    }
                    if (customerProfile.getCreatedOn() != null) {
                        existingCustomerProfile.setCreatedOn(customerProfile.getCreatedOn());
                    }
                    if (customerProfile.getCreatedBy() != null) {
                        existingCustomerProfile.setCreatedBy(customerProfile.getCreatedBy());
                    }
                    if (customerProfile.getUpdatedOn() != null) {
                        existingCustomerProfile.setUpdatedOn(customerProfile.getUpdatedOn());
                    }
                    if (customerProfile.getUpdatedBy() != null) {
                        existingCustomerProfile.setUpdatedBy(customerProfile.getUpdatedBy());
                    }

                    return existingCustomerProfile;
                }
            )
            .map(customerProfileRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customerProfile.getId().toString())
        );
    }

    /**
     * {@code GET  /customer-profiles} : get all the customerProfiles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerProfiles in body.
     */
    @GetMapping("/customer-profiles")
    public List<CustomerProfile> getAllCustomerProfiles() {
        log.debug("REST request to get all CustomerProfiles");
        return customerProfileRepository.findAll();
    }

    /**
     * {@code GET  /customer-profiles/:id} : get the "id" customerProfile.
     *
     * @param id the id of the customerProfile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerProfile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-profiles/{id}")
    public ResponseEntity<CustomerProfile> getCustomerProfile(@PathVariable Long id) {
        log.debug("REST request to get CustomerProfile : {}", id);
        Optional<CustomerProfile> customerProfile = customerProfileRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerProfile);
    }

    /**
     * {@code DELETE  /customer-profiles/:id} : delete the "id" customerProfile.
     *
     * @param id the id of the customerProfile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-profiles/{id}")
    public ResponseEntity<Void> deleteCustomerProfile(@PathVariable Long id) {
        log.debug("REST request to delete CustomerProfile : {}", id);
        customerProfileRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

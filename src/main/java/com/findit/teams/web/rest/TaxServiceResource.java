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

import com.findit.teams.domain.TaxService;
import com.findit.teams.repository.TaxServiceRepository;
import com.findit.teams.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.findit.teams.domain.TaxService}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TaxServiceResource {

    private final Logger log = LoggerFactory.getLogger(TaxServiceResource.class);

    private static final String ENTITY_NAME = "taxfileserviceTaxService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxServiceRepository taxServiceRepository;

    public TaxServiceResource(TaxServiceRepository taxServiceRepository) {
        this.taxServiceRepository = taxServiceRepository;
    }

    /**
     * {@code POST  /tax-services} : Create a new taxService.
     *
     * @param taxService the taxService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxService, or with status {@code 400 (Bad Request)} if the taxService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tax-services")
    public ResponseEntity<TaxService> createTaxService(@RequestBody TaxService taxService) throws URISyntaxException {
        log.debug("REST request to save TaxService : {}", taxService);
        if (taxService.getId() != null) {
            throw new BadRequestAlertException("A new taxService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxService result = taxServiceRepository.save(taxService);
        return ResponseEntity
            .created(new URI("/api/tax-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tax-services/:id} : Updates an existing taxService.
     *
     * @param id the id of the taxService to save.
     * @param taxService the taxService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxService,
     * or with status {@code 400 (Bad Request)} if the taxService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tax-services/{id}")
    public ResponseEntity<TaxService> updateTaxService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxService taxService
    ) throws URISyntaxException {
        log.debug("REST request to update TaxService : {}, {}", id, taxService);
        if (taxService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaxService result = taxServiceRepository.save(taxService);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxService.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tax-services/:id} : Partial updates given fields of an existing taxService, field will ignore if it is null
     *
     * @param id the id of the taxService to save.
     * @param taxService the taxService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxService,
     * or with status {@code 400 (Bad Request)} if the taxService is not valid,
     * or with status {@code 404 (Not Found)} if the taxService is not found,
     * or with status {@code 500 (Internal Server Error)} if the taxService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tax-services/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaxService> partialUpdateTaxService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxService taxService
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaxService partially : {}, {}", id, taxService);
        if (taxService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaxService> result = taxServiceRepository
            .findById(taxService.getId())
            .map(
                existingTaxService -> {
                    if (taxService.getName() != null) {
                        existingTaxService.setName(taxService.getName());
                    }
                    if (taxService.getServiceUrl() != null) {
                        existingTaxService.setServiceUrl(taxService.getServiceUrl());
                    }
                    if (taxService.getStartDate() != null) {
                        existingTaxService.setStartDate(taxService.getStartDate());
                    }
                    if (taxService.getEndDate() != null) {
                        existingTaxService.setEndDate(taxService.getEndDate());
                    }
                    if (taxService.getDescription() != null) {
                        existingTaxService.setDescription(taxService.getDescription());
                    }
                    if (taxService.getCreatedOn() != null) {
                        existingTaxService.setCreatedOn(taxService.getCreatedOn());
                    }
                    if (taxService.getCreatedBy() != null) {
                        existingTaxService.setCreatedBy(taxService.getCreatedBy());
                    }
                    if (taxService.getUpdatedOn() != null) {
                        existingTaxService.setUpdatedOn(taxService.getUpdatedOn());
                    }
                    if (taxService.getUpdatedBy() != null) {
                        existingTaxService.setUpdatedBy(taxService.getUpdatedBy());
                    }
                    if (taxService.getStatus() != null) {
                        existingTaxService.setStatus(taxService.getStatus());
                    }
                    if (taxService.getComments() != null) {
                        existingTaxService.setComments(taxService.getComments());
                    }

                    return existingTaxService;
                }
            )
            .map(taxServiceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxService.getId().toString())
        );
    }

    /**
     * {@code GET  /tax-services} : get all the taxServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxServices in body.
     */
    @GetMapping("/tax-services")
    public List<TaxService> getAllTaxServices() {
        log.debug("REST request to get all TaxServices");
        return taxServiceRepository.findAll();
    }

    /**
     * {@code GET  /tax-services/:id} : get the "id" taxService.
     *
     * @param id the id of the taxService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tax-services/{id}")
    public ResponseEntity<TaxService> getTaxService(@PathVariable Long id) {
        log.debug("REST request to get TaxService : {}", id);
        Optional<TaxService> taxService = taxServiceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taxService);
    }

    /**
     * {@code DELETE  /tax-services/:id} : delete the "id" taxService.
     *
     * @param id the id of the taxService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tax-services/{id}")
    public ResponseEntity<Void> deleteTaxService(@PathVariable Long id) {
        log.debug("REST request to delete TaxService : {}", id);
        taxServiceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

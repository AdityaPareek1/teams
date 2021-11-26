package com.findit.teams.web.rest;
//package com.findit.teams.web.rest;
//
//import com.findit.teams.domain.TaxServiceActivity;
//import com.findit.teams.repository.TaxServiceActivityRepository;
//import com.findit.teams.web.rest.errors.BadRequestAlertException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//import tech.jhipster.web.util.HeaderUtil;
//import tech.jhipster.web.util.ResponseUtil;
//
///**
// * REST controller for managing {@link com.findit.teams.domain.TaxServiceActivity}.
// */
//@RestController
//@RequestMapping("/api")
//@Transactional
//public class TaxServiceActivityResource {
//
//    private final Logger log = LoggerFactory.getLogger(TaxServiceActivityResource.class);
//
//    private static final String ENTITY_NAME = "taxfileserviceTaxServiceActivity";
//
//    @Value("${jhipster.clientApp.name}")
//    private String applicationName;
//
//    private final TaxServiceActivityRepository taxServiceActivityRepository;
//
//    public TaxServiceActivityResource(TaxServiceActivityRepository taxServiceActivityRepository) {
//        this.taxServiceActivityRepository = taxServiceActivityRepository;
//    }
//
//    /**
//     * {@code POST  /tax-service-activities} : Create a new taxServiceActivity.
//     *
//     * @param taxServiceActivity the taxServiceActivity to create.
//     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxServiceActivity, or with status {@code 400 (Bad Request)} if the taxServiceActivity has already an ID.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PostMapping("/tax-service-activities")
//    public ResponseEntity<TaxServiceActivity> createTaxServiceActivity(@RequestBody TaxServiceActivity taxServiceActivity)
//        throws URISyntaxException {
//        log.debug("REST request to save TaxServiceActivity : {}", taxServiceActivity);
//        if (taxServiceActivity.getId() != null) {
//            throw new BadRequestAlertException("A new taxServiceActivity cannot already have an ID", ENTITY_NAME, "idexists");
//        }
//        TaxServiceActivity result = taxServiceActivityRepository.save(taxServiceActivity);
//        return ResponseEntity
//            .created(new URI("/api/tax-service-activities/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * {@code PUT  /tax-service-activities/:id} : Updates an existing taxServiceActivity.
//     *
//     * @param id the id of the taxServiceActivity to save.
//     * @param taxServiceActivity the taxServiceActivity to update.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxServiceActivity,
//     * or with status {@code 400 (Bad Request)} if the taxServiceActivity is not valid,
//     * or with status {@code 500 (Internal Server Error)} if the taxServiceActivity couldn't be updated.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PutMapping("/tax-service-activities/{id}")
//    public ResponseEntity<TaxServiceActivity> updateTaxServiceActivity(
//        @PathVariable(value = "id", required = false) final Long id,
//        @RequestBody TaxServiceActivity taxServiceActivity
//    ) throws URISyntaxException {
//        log.debug("REST request to update TaxServiceActivity : {}, {}", id, taxServiceActivity);
//        if (taxServiceActivity.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        if (!Objects.equals(id, taxServiceActivity.getId())) {
//            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
//        }
//
//        if (!taxServiceActivityRepository.existsById(id)) {
//            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
//        }
//
//        TaxServiceActivity result = taxServiceActivityRepository.save(taxServiceActivity);
//        return ResponseEntity
//            .ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxServiceActivity.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * {@code PATCH  /tax-service-activities/:id} : Partial updates given fields of an existing taxServiceActivity, field will ignore if it is null
//     *
//     * @param id the id of the taxServiceActivity to save.
//     * @param taxServiceActivity the taxServiceActivity to update.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxServiceActivity,
//     * or with status {@code 400 (Bad Request)} if the taxServiceActivity is not valid,
//     * or with status {@code 404 (Not Found)} if the taxServiceActivity is not found,
//     * or with status {@code 500 (Internal Server Error)} if the taxServiceActivity couldn't be updated.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PatchMapping(value = "/tax-service-activities/{id}", consumes = "application/merge-patch+json")
//    public ResponseEntity<TaxServiceActivity> partialUpdateTaxServiceActivity(
//        @PathVariable(value = "id", required = false) final Long id,
//        @RequestBody TaxServiceActivity taxServiceActivity
//    ) throws URISyntaxException {
//        log.debug("REST request to partial update TaxServiceActivity partially : {}, {}", id, taxServiceActivity);
//        if (taxServiceActivity.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        if (!Objects.equals(id, taxServiceActivity.getId())) {
//            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
//        }
//
//        if (!taxServiceActivityRepository.existsById(id)) {
//            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
//        }
//
//        Optional<TaxServiceActivity> result = taxServiceActivityRepository
//            .findById(taxServiceActivity.getId())
//            .map(
//                existingTaxServiceActivity -> {
//                    if (taxServiceActivity.getName() != null) {
//                        existingTaxServiceActivity.setName(taxServiceActivity.getName());
//                    }
//                    if (taxServiceActivity.getServiceUrl() != null) {
//                        existingTaxServiceActivity.setServiceUrl(taxServiceActivity.getServiceUrl());
//                    }
//                    if (taxServiceActivity.getStartDate() != null) {
//                        existingTaxServiceActivity.setStartDate(taxServiceActivity.getStartDate());
//                    }
//                    if (taxServiceActivity.getEndDate() != null) {
//                        existingTaxServiceActivity.setEndDate(taxServiceActivity.getEndDate());
//                    }
//                    if (taxServiceActivity.getDescription() != null) {
//                        existingTaxServiceActivity.setDescription(taxServiceActivity.getDescription());
//                    }
//                    if (taxServiceActivity.getCreatedOn() != null) {
//                        existingTaxServiceActivity.setCreatedOn(taxServiceActivity.getCreatedOn());
//                    }
//                    if (taxServiceActivity.getCreatedBy() != null) {
//                        existingTaxServiceActivity.setCreatedBy(taxServiceActivity.getCreatedBy());
//                    }
//                    if (taxServiceActivity.getUpdatedOn() != null) {
//                        existingTaxServiceActivity.setUpdatedOn(taxServiceActivity.getUpdatedOn());
//                    }
//                    if (taxServiceActivity.getUpdatedBy() != null) {
//                        existingTaxServiceActivity.setUpdatedBy(taxServiceActivity.getUpdatedBy());
//                    }
//                    if (taxServiceActivity.getStatus() != null) {
//                        existingTaxServiceActivity.setStatus(taxServiceActivity.getStatus());
//                    }
//                    if (taxServiceActivity.getComments() != null) {
//                        existingTaxServiceActivity.setComments(taxServiceActivity.getComments());
//                    }
//                    if (taxServiceActivity.getTaxServiceId() != null) {
//                        existingTaxServiceActivity.setTaxServiceId(taxServiceActivity.getTaxServiceId());
//                    }
//
//                    return existingTaxServiceActivity;
//                }
//            )
//            .map(taxServiceActivityRepository::save);
//
//        return ResponseUtil.wrapOrNotFound(
//            result,
//            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxServiceActivity.getId().toString())
//        );
//    }
//
//    /**
//     * {@code GET  /tax-service-activities} : get all the taxServiceActivities.
//     *
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxServiceActivities in body.
//     */
//    @GetMapping("/tax-service-activities")
//    public List<TaxServiceActivity> getAllTaxServiceActivities() {
//        log.debug("REST request to get all TaxServiceActivities");
//        return taxServiceActivityRepository.findAll();
//    }
//
//    /**
//     * {@code GET  /tax-service-activities/:id} : get the "id" taxServiceActivity.
//     *
//     * @param id the id of the taxServiceActivity to retrieve.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxServiceActivity, or with status {@code 404 (Not Found)}.
//     */
//    @GetMapping("/tax-service-activities/{id}")
//    public ResponseEntity<TaxServiceActivity> getTaxServiceActivity(@PathVariable Long id) {
//        log.debug("REST request to get TaxServiceActivity : {}", id);
//        Optional<TaxServiceActivity> taxServiceActivity = taxServiceActivityRepository.findById(id);
//        return ResponseUtil.wrapOrNotFound(taxServiceActivity);
//    }
//
//    /**
//     * {@code DELETE  /tax-service-activities/:id} : delete the "id" taxServiceActivity.
//     *
//     * @param id the id of the taxServiceActivity to delete.
//     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//     */
//    @DeleteMapping("/tax-service-activities/{id}")
//    public ResponseEntity<Void> deleteTaxServiceActivity(@PathVariable Long id) {
//        log.debug("REST request to delete TaxServiceActivity : {}", id);
//        taxServiceActivityRepository.deleteById(id);
//        return ResponseEntity
//            .noContent()
//            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
//            .build();
//    }
//}

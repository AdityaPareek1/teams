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

import com.findit.teams.domain.Companies;
import com.findit.teams.repository.CompaniesRepository;
import com.findit.teams.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;


/**
 * REST controller for managing {@link com.findit.teams.domain.Companies}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CompaniesResource {

    private final Logger log = LoggerFactory.getLogger(CompaniesResource.class);

    private static final String ENTITY_NAME = "finditteamsCompanies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompaniesRepository companiesRepository;

    public CompaniesResource(CompaniesRepository companiesRepository) {
        this.companiesRepository = companiesRepository;
    }

    /**
     * {@code POST  /companies} : Create a new companies.
     *
     * @param companies the companies to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companies, or with status {@code 400 (Bad Request)} if the companies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/companies")
    public ResponseEntity<Companies> createCompanies(@RequestBody Companies companies) throws URISyntaxException {
        log.debug("REST request to save Companies : {}", companies);
        if (companies.getId() != null) {
            throw new BadRequestAlertException("A new companies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Companies result = companiesRepository.save(companies);
        return ResponseEntity
            .created(new URI("/api/companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /companies/:id} : Updates an existing companies.
     *
     * @param id the id of the companies to save.
     * @param companies the companies to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companies,
     * or with status {@code 400 (Bad Request)} if the companies is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companies couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/companies/{id}")
    public ResponseEntity<Companies> updateCompanies(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Companies companies
    ) throws URISyntaxException {
        log.debug("REST request to update Companies : {}, {}", id, companies);
        if (companies.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companies.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Companies result = companiesRepository.save(companies);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companies.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /companies/:id} : Partial updates given fields of an existing companies, field will ignore if it is null
     *
     * @param id the id of the companies to save.
     * @param companies the companies to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companies,
     * or with status {@code 400 (Bad Request)} if the companies is not valid,
     * or with status {@code 404 (Not Found)} if the companies is not found,
     * or with status {@code 500 (Internal Server Error)} if the companies couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/companies/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Companies> partialUpdateCompanies(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Companies companies
    ) throws URISyntaxException {
        log.debug("REST request to partial update Companies partially : {}, {}", id, companies);
        if (companies.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companies.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Companies> result = companiesRepository
            .findById(companies.getId())
            .map(
                existingCompanies -> {
                    if (companies.getName() != null) {
                        existingCompanies.setName(companies.getName());
                    }
                    if (companies.getEmail() != null) {
                        existingCompanies.setEmail(companies.getEmail());
                    }
                    if (companies.getDomainName() != null) {
                        existingCompanies.setDomainName(companies.getDomainName());
                    }
                    if (companies.getOwnerName() != null) {
                        existingCompanies.setOwnerName(companies.getOwnerName());
                    }
                    if (companies.getMessage() != null) {
                        existingCompanies.setMessage(companies.getMessage());
                    }
                    if (companies.getWorkLanguage() != null) {
                        existingCompanies.setWorkLanguage(companies.getWorkLanguage());
                    }
                    if (companies.getStatus() != null) {
                        existingCompanies.setStatus(companies.getStatus());
                    }
                    if (companies.getCreatedOn() != null) {
                        existingCompanies.setCreatedOn(companies.getCreatedOn());
                    }
                    if (companies.getCreatedBy() != null) {
                        existingCompanies.setCreatedBy(companies.getCreatedBy());
                    }
                    if (companies.getUpdatedOn() != null) {
                        existingCompanies.setUpdatedOn(companies.getUpdatedOn());
                    }
                    if (companies.getUpdatedBy() != null) {
                        existingCompanies.setUpdatedBy(companies.getUpdatedBy());
                    }

                    return existingCompanies;
                }
            )
            .map(companiesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companies.getId().toString())
        );
    }

    /**
     * {@code GET  /companies} : get all the companies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companies in body.
     */
    @GetMapping("/companies")
    public List<Companies> getAllCompanies() {
        log.debug("REST request to get all Companies");
        return companiesRepository.findAll();
    }

    /**
     * {@code GET  /companies/:id} : get the "id" companies.
     *
     * @param id the id of the companies to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companies, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/companies/{id}")
    public ResponseEntity<Companies> getCompanies(@PathVariable Long id) {
        log.debug("REST request to get Companies : {}", id);
        Optional<Companies> companies = companiesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(companies);
    }

    /**
     * {@code DELETE  /companies/:id} : delete the "id" companies.
     *
     * @param id the id of the companies to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompanies(@PathVariable Long id) {
        log.debug("REST request to delete Companies : {}", id);
        companiesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

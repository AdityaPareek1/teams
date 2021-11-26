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

import com.findit.teams.domain.Developers;
import com.findit.teams.repository.DevelopersRepository;
import com.findit.teams.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.findit.teams.domain.Developers}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DevelopersResource {

    private final Logger log = LoggerFactory.getLogger(DevelopersResource.class);

    private static final String ENTITY_NAME = "taxfileserviceDevelopers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DevelopersRepository developersRepository;

    public DevelopersResource(DevelopersRepository developersRepository) {
        this.developersRepository = developersRepository;
    }

    /**
     * {@code POST  /developers} : Create a new developers.
     *
     * @param developers the developers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new developers, or with status {@code 400 (Bad Request)} if the developers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/developers")
    public ResponseEntity<Developers> createDevelopers(@RequestBody Developers developers) throws URISyntaxException {
        log.debug("REST request to save Developers : {}", developers);
        if (developers.getId() != null) {
            throw new BadRequestAlertException("A new developers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Developers result = developersRepository.save(developers);
        return ResponseEntity
            .created(new URI("/api/developers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /developers/:id} : Updates an existing developers.
     *
     * @param id the id of the developers to save.
     * @param developers the developers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated developers,
     * or with status {@code 400 (Bad Request)} if the developers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the developers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/developers/{id}")
    public ResponseEntity<Developers> updateDevelopers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Developers developers
    ) throws URISyntaxException {
        log.debug("REST request to update Developers : {}, {}", id, developers);
        if (developers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, developers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!developersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Developers result = developersRepository.save(developers);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, developers.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /developers/:id} : Partial updates given fields of an existing developers, field will ignore if it is null
     *
     * @param id the id of the developers to save.
     * @param developers the developers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated developers,
     * or with status {@code 400 (Bad Request)} if the developers is not valid,
     * or with status {@code 404 (Not Found)} if the developers is not found,
     * or with status {@code 500 (Internal Server Error)} if the developers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/developers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Developers> partialUpdateDevelopers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Developers developers
    ) throws URISyntaxException {
        log.debug("REST request to partial update Developers partially : {}, {}", id, developers);
        if (developers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, developers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!developersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Developers> result = developersRepository
            .findById(developers.getId())
            .map(
                existingDevelopers -> {
                    if (developers.getFirstName() != null) {
                        existingDevelopers.setFirstName(developers.getFirstName());
                    }
                    if (developers.getLastName() != null) {
                        existingDevelopers.setLastName(developers.getLastName());
                    }
                    if (developers.getEmail() != null) {
                        existingDevelopers.setEmail(developers.getEmail());
                    }
                    if (developers.getPassword() != null) {
                        existingDevelopers.setPassword(developers.getPassword());
                    }
                    if (developers.getUsername() != null) {
                        existingDevelopers.setUsername(developers.getUsername());
                    }
                    if (developers.getSkills() != null) {
                        existingDevelopers.setSkills(developers.getSkills());
                    }
                    if (developers.getLanguage() != null) {
                        existingDevelopers.setLanguage(developers.getLanguage());
                    }
                    if (developers.getProfileUrl() != null) {
                        existingDevelopers.setProfileUrl(developers.getProfileUrl());
                    }
                    if (developers.getOneLineDescription() != null) {
                        existingDevelopers.setOneLineDescription(developers.getOneLineDescription());
                    }
                    if (developers.getDescribeYourself() != null) {
                        existingDevelopers.setDescribeYourself(developers.getDescribeYourself());
                    }
                    if (developers.getSpeakLanguage() != null) {
                        existingDevelopers.setSpeakLanguage(developers.getSpeakLanguage());
                    }
                    if (developers.getBorn() != null) {
                        existingDevelopers.setBorn(developers.getBorn());
                    }
                    if (developers.getAddress() != null) {
                        existingDevelopers.setAddress(developers.getAddress());
                    }
                    if (developers.getCity() != null) {
                        existingDevelopers.setCity(developers.getCity());
                    }
                    if (developers.getState() != null) {
                        existingDevelopers.setState(developers.getState());
                    }
                    if (developers.getZipPostCode() != null) {
                        existingDevelopers.setZipPostCode(developers.getZipPostCode());
                    }
                    if (developers.getCountry() != null) {
                        existingDevelopers.setCountry(developers.getCountry());
                    }
                    if (developers.getExpriace() != null) {
                        existingDevelopers.setExpriace(developers.getExpriace());
                    }
                    if (developers.getWorkType() != null) {
                        existingDevelopers.setWorkType(developers.getWorkType());
                    }
                    if (developers.getCreatedOn() != null) {
                        existingDevelopers.setCreatedOn(developers.getCreatedOn());
                    }
                    if (developers.getCreatedBy() != null) {
                        existingDevelopers.setCreatedBy(developers.getCreatedBy());
                    }
                    if (developers.getUpdatedOn() != null) {
                        existingDevelopers.setUpdatedOn(developers.getUpdatedOn());
                    }
                    if (developers.getUpdatedBy() != null) {
                        existingDevelopers.setUpdatedBy(developers.getUpdatedBy());
                    }
                    if (developers.getStatus() != null) {
                        existingDevelopers.setStatus(developers.getStatus());
                    }

                    return existingDevelopers;
                }
            )
            .map(developersRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, developers.getId().toString())
        );
    }

    /**
     * {@code GET  /developers} : get all the developers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of developers in body.
     */
    @GetMapping("/developers")
    public List<Developers> getAllDevelopers() {
        log.debug("REST request to get all Developers");
        return developersRepository.findAll();
    }

    /**
     * {@code GET  /developers/:id} : get the "id" developers.
     *
     * @param id the id of the developers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the developers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/developers/{id}")
    public ResponseEntity<Developers> getDevelopers(@PathVariable Long id) {
        log.debug("REST request to get Developers : {}", id);
        Optional<Developers> developers = developersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(developers);
    }

    /**
     * {@code DELETE  /developers/:id} : delete the "id" developers.
     *
     * @param id the id of the developers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/developers/{id}")
    public ResponseEntity<Void> deleteDevelopers(@PathVariable Long id) {
        log.debug("REST request to delete Developers : {}", id);
        developersRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

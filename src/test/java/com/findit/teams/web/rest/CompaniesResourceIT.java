package com.findit.teams.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.findit.teams.IntegrationTest;
import com.findit.teams.domain.Companies;
import com.findit.teams.repository.CompaniesRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CompaniesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompaniesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_WORK_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompaniesRepository companiesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompaniesMockMvc;

    private Companies companies;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Companies createEntity(EntityManager em) {
        Companies companies = new Companies()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .domainName(DEFAULT_DOMAIN_NAME)
            .ownerName(DEFAULT_OWNER_NAME)
            .message(DEFAULT_MESSAGE)
            .workLanguage(DEFAULT_WORK_LANGUAGE)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return companies;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Companies createUpdatedEntity(EntityManager em) {
        Companies companies = new Companies()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .domainName(UPDATED_DOMAIN_NAME)
            .ownerName(UPDATED_OWNER_NAME)
            .message(UPDATED_MESSAGE)
            .workLanguage(UPDATED_WORK_LANGUAGE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return companies;
    }

    @BeforeEach
    public void initTest() {
        companies = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanies() throws Exception {
        int databaseSizeBeforeCreate = companiesRepository.findAll().size();
        // Create the Companies
        restCompaniesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companies)))
            .andExpect(status().isCreated());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeCreate + 1);
        Companies testCompanies = companiesList.get(companiesList.size() - 1);
        assertThat(testCompanies.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanies.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompanies.getDomainName()).isEqualTo(DEFAULT_DOMAIN_NAME);
        assertThat(testCompanies.getOwnerName()).isEqualTo(DEFAULT_OWNER_NAME);
        assertThat(testCompanies.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testCompanies.getWorkLanguage()).isEqualTo(DEFAULT_WORK_LANGUAGE);
        assertThat(testCompanies.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCompanies.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCompanies.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCompanies.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testCompanies.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createCompaniesWithExistingId() throws Exception {
        // Create the Companies with an existing ID
        companies.setId(1L);

        int databaseSizeBeforeCreate = companiesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompaniesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companies)))
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanies() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companies.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].domainName").value(hasItem(DEFAULT_DOMAIN_NAME)))
            .andExpect(jsonPath("$.[*].ownerName").value(hasItem(DEFAULT_OWNER_NAME)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].workLanguage").value(hasItem(DEFAULT_WORK_LANGUAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getCompanies() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get the companies
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL_ID, companies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companies.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.domainName").value(DEFAULT_DOMAIN_NAME))
            .andExpect(jsonPath("$.ownerName").value(DEFAULT_OWNER_NAME))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.workLanguage").value(DEFAULT_WORK_LANGUAGE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingCompanies() throws Exception {
        // Get the companies
        restCompaniesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompanies() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();

        // Update the companies
        Companies updatedCompanies = companiesRepository.findById(companies.getId()).get();
        // Disconnect from session so that the updates on updatedCompanies are not directly saved in db
        em.detach(updatedCompanies);
        updatedCompanies
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .domainName(UPDATED_DOMAIN_NAME)
            .ownerName(UPDATED_OWNER_NAME)
            .message(UPDATED_MESSAGE)
            .workLanguage(UPDATED_WORK_LANGUAGE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restCompaniesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompanies.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompanies))
            )
            .andExpect(status().isOk());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
        Companies testCompanies = companiesList.get(companiesList.size() - 1);
        assertThat(testCompanies.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanies.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompanies.getDomainName()).isEqualTo(UPDATED_DOMAIN_NAME);
        assertThat(testCompanies.getOwnerName()).isEqualTo(UPDATED_OWNER_NAME);
        assertThat(testCompanies.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testCompanies.getWorkLanguage()).isEqualTo(UPDATED_WORK_LANGUAGE);
        assertThat(testCompanies.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCompanies.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCompanies.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCompanies.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testCompanies.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companies.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companies))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companies))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companies)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompaniesWithPatch() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();

        // Update the companies using partial update
        Companies partialUpdatedCompanies = new Companies();
        partialUpdatedCompanies.setId(companies.getId());

        partialUpdatedCompanies
            .email(UPDATED_EMAIL)
            .domainName(UPDATED_DOMAIN_NAME)
            .message(UPDATED_MESSAGE)
            .workLanguage(UPDATED_WORK_LANGUAGE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanies.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanies))
            )
            .andExpect(status().isOk());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
        Companies testCompanies = companiesList.get(companiesList.size() - 1);
        assertThat(testCompanies.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanies.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompanies.getDomainName()).isEqualTo(UPDATED_DOMAIN_NAME);
        assertThat(testCompanies.getOwnerName()).isEqualTo(DEFAULT_OWNER_NAME);
        assertThat(testCompanies.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testCompanies.getWorkLanguage()).isEqualTo(UPDATED_WORK_LANGUAGE);
        assertThat(testCompanies.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCompanies.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCompanies.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCompanies.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testCompanies.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateCompaniesWithPatch() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();

        // Update the companies using partial update
        Companies partialUpdatedCompanies = new Companies();
        partialUpdatedCompanies.setId(companies.getId());

        partialUpdatedCompanies
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .domainName(UPDATED_DOMAIN_NAME)
            .ownerName(UPDATED_OWNER_NAME)
            .message(UPDATED_MESSAGE)
            .workLanguage(UPDATED_WORK_LANGUAGE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanies.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanies))
            )
            .andExpect(status().isOk());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
        Companies testCompanies = companiesList.get(companiesList.size() - 1);
        assertThat(testCompanies.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanies.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompanies.getDomainName()).isEqualTo(UPDATED_DOMAIN_NAME);
        assertThat(testCompanies.getOwnerName()).isEqualTo(UPDATED_OWNER_NAME);
        assertThat(testCompanies.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testCompanies.getWorkLanguage()).isEqualTo(UPDATED_WORK_LANGUAGE);
        assertThat(testCompanies.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCompanies.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCompanies.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCompanies.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testCompanies.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companies.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companies))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companies))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companies))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanies() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        int databaseSizeBeforeDelete = companiesRepository.findAll().size();

        // Delete the companies
        restCompaniesMockMvc
            .perform(delete(ENTITY_API_URL_ID, companies.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

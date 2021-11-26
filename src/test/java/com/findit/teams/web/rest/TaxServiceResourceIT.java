package com.findit.teams.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.findit.teams.IntegrationTest;
import com.findit.teams.domain.TaxService;
import com.findit.teams.repository.TaxServiceRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link TaxServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaxServiceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_URL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tax-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaxServiceRepository taxServiceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxServiceMockMvc;

    private TaxService taxService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxService createEntity(EntityManager em) {
        TaxService taxService = new TaxService()
            .name(DEFAULT_NAME)
            .serviceUrl(DEFAULT_SERVICE_URL)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .description(DEFAULT_DESCRIPTION)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .status(DEFAULT_STATUS)
            .comments(DEFAULT_COMMENTS);
        return taxService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxService createUpdatedEntity(EntityManager em) {
        TaxService taxService = new TaxService()
            .name(UPDATED_NAME)
            .serviceUrl(UPDATED_SERVICE_URL)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .status(UPDATED_STATUS)
            .comments(UPDATED_COMMENTS);
        return taxService;
    }

    @BeforeEach
    public void initTest() {
        taxService = createEntity(em);
    }

    @Test
    @Transactional
    void createTaxService() throws Exception {
        int databaseSizeBeforeCreate = taxServiceRepository.findAll().size();
        // Create the TaxService
        restTaxServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taxService)))
            .andExpect(status().isCreated());

        // Validate the TaxService in the database
        List<TaxService> taxServiceList = taxServiceRepository.findAll();
        assertThat(taxServiceList).hasSize(databaseSizeBeforeCreate + 1);
        TaxService testTaxService = taxServiceList.get(taxServiceList.size() - 1);
        assertThat(testTaxService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaxService.getServiceUrl()).isEqualTo(DEFAULT_SERVICE_URL);
        assertThat(testTaxService.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTaxService.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTaxService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaxService.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testTaxService.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTaxService.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testTaxService.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTaxService.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTaxService.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void createTaxServiceWithExistingId() throws Exception {
        // Create the TaxService with an existing ID
        taxService.setId(1L);

        int databaseSizeBeforeCreate = taxServiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taxService)))
            .andExpect(status().isBadRequest());

        // Validate the TaxService in the database
        List<TaxService> taxServiceList = taxServiceRepository.findAll();
        assertThat(taxServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaxServices() throws Exception {
        // Initialize the database
        taxServiceRepository.saveAndFlush(taxService);

        // Get all the taxServiceList
        restTaxServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].serviceUrl").value(hasItem(DEFAULT_SERVICE_URL)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    @Test
    @Transactional
    void getTaxService() throws Exception {
        // Initialize the database
        taxServiceRepository.saveAndFlush(taxService);

        // Get the taxService
        restTaxServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, taxService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.serviceUrl").value(DEFAULT_SERVICE_URL))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    void getNonExistingTaxService() throws Exception {
        // Get the taxService
        restTaxServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaxService() throws Exception {
        // Initialize the database
        taxServiceRepository.saveAndFlush(taxService);

        int databaseSizeBeforeUpdate = taxServiceRepository.findAll().size();

        // Update the taxService
        TaxService updatedTaxService = taxServiceRepository.findById(taxService.getId()).get();
        // Disconnect from session so that the updates on updatedTaxService are not directly saved in db
        em.detach(updatedTaxService);
        updatedTaxService
            .name(UPDATED_NAME)
            .serviceUrl(UPDATED_SERVICE_URL)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .status(UPDATED_STATUS)
            .comments(UPDATED_COMMENTS);

        restTaxServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaxService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTaxService))
            )
            .andExpect(status().isOk());

        // Validate the TaxService in the database
        List<TaxService> taxServiceList = taxServiceRepository.findAll();
        assertThat(taxServiceList).hasSize(databaseSizeBeforeUpdate);
        TaxService testTaxService = taxServiceList.get(taxServiceList.size() - 1);
        assertThat(testTaxService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaxService.getServiceUrl()).isEqualTo(UPDATED_SERVICE_URL);
        assertThat(testTaxService.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTaxService.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTaxService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaxService.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTaxService.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTaxService.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testTaxService.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTaxService.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaxService.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void putNonExistingTaxService() throws Exception {
        int databaseSizeBeforeUpdate = taxServiceRepository.findAll().size();
        taxService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taxService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taxService))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxService in the database
        List<TaxService> taxServiceList = taxServiceRepository.findAll();
        assertThat(taxServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaxService() throws Exception {
        int databaseSizeBeforeUpdate = taxServiceRepository.findAll().size();
        taxService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taxService))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxService in the database
        List<TaxService> taxServiceList = taxServiceRepository.findAll();
        assertThat(taxServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaxService() throws Exception {
        int databaseSizeBeforeUpdate = taxServiceRepository.findAll().size();
        taxService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxServiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taxService)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxService in the database
        List<TaxService> taxServiceList = taxServiceRepository.findAll();
        assertThat(taxServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaxServiceWithPatch() throws Exception {
        // Initialize the database
        taxServiceRepository.saveAndFlush(taxService);

        int databaseSizeBeforeUpdate = taxServiceRepository.findAll().size();

        // Update the taxService using partial update
        TaxService partialUpdatedTaxService = new TaxService();
        partialUpdatedTaxService.setId(taxService.getId());

        partialUpdatedTaxService
            .name(UPDATED_NAME)
            .serviceUrl(UPDATED_SERVICE_URL)
            .startDate(UPDATED_START_DATE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restTaxServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaxService))
            )
            .andExpect(status().isOk());

        // Validate the TaxService in the database
        List<TaxService> taxServiceList = taxServiceRepository.findAll();
        assertThat(taxServiceList).hasSize(databaseSizeBeforeUpdate);
        TaxService testTaxService = taxServiceList.get(taxServiceList.size() - 1);
        assertThat(testTaxService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaxService.getServiceUrl()).isEqualTo(UPDATED_SERVICE_URL);
        assertThat(testTaxService.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTaxService.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTaxService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaxService.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTaxService.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTaxService.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testTaxService.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTaxService.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTaxService.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void fullUpdateTaxServiceWithPatch() throws Exception {
        // Initialize the database
        taxServiceRepository.saveAndFlush(taxService);

        int databaseSizeBeforeUpdate = taxServiceRepository.findAll().size();

        // Update the taxService using partial update
        TaxService partialUpdatedTaxService = new TaxService();
        partialUpdatedTaxService.setId(taxService.getId());

        partialUpdatedTaxService
            .name(UPDATED_NAME)
            .serviceUrl(UPDATED_SERVICE_URL)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .status(UPDATED_STATUS)
            .comments(UPDATED_COMMENTS);

        restTaxServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaxService))
            )
            .andExpect(status().isOk());

        // Validate the TaxService in the database
        List<TaxService> taxServiceList = taxServiceRepository.findAll();
        assertThat(taxServiceList).hasSize(databaseSizeBeforeUpdate);
        TaxService testTaxService = taxServiceList.get(taxServiceList.size() - 1);
        assertThat(testTaxService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaxService.getServiceUrl()).isEqualTo(UPDATED_SERVICE_URL);
        assertThat(testTaxService.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTaxService.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTaxService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaxService.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTaxService.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTaxService.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testTaxService.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTaxService.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaxService.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void patchNonExistingTaxService() throws Exception {
        int databaseSizeBeforeUpdate = taxServiceRepository.findAll().size();
        taxService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taxService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taxService))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxService in the database
        List<TaxService> taxServiceList = taxServiceRepository.findAll();
        assertThat(taxServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaxService() throws Exception {
        int databaseSizeBeforeUpdate = taxServiceRepository.findAll().size();
        taxService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taxService))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxService in the database
        List<TaxService> taxServiceList = taxServiceRepository.findAll();
        assertThat(taxServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaxService() throws Exception {
        int databaseSizeBeforeUpdate = taxServiceRepository.findAll().size();
        taxService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxServiceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(taxService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxService in the database
        List<TaxService> taxServiceList = taxServiceRepository.findAll();
        assertThat(taxServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaxService() throws Exception {
        // Initialize the database
        taxServiceRepository.saveAndFlush(taxService);

        int databaseSizeBeforeDelete = taxServiceRepository.findAll().size();

        // Delete the taxService
        restTaxServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, taxService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaxService> taxServiceList = taxServiceRepository.findAll();
        assertThat(taxServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

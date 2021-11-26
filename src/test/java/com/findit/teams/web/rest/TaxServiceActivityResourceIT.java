package com.findit.teams.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.findit.teams.IntegrationTest;
import com.findit.teams.domain.TaxServiceActivity;
import com.findit.teams.repository.TaxServiceActivityRepository;
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
 * Integration tests for the {@link TaxServiceActivityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaxServiceActivityResourceIT {

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

    private static final Long DEFAULT_TAX_SERVICE_ID = 1L;
    private static final Long UPDATED_TAX_SERVICE_ID = 2L;

    private static final String ENTITY_API_URL = "/api/tax-service-activities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaxServiceActivityRepository taxServiceActivityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxServiceActivityMockMvc;

    private TaxServiceActivity taxServiceActivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxServiceActivity createEntity(EntityManager em) {
        TaxServiceActivity taxServiceActivity = new TaxServiceActivity()
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
            .comments(DEFAULT_COMMENTS)
            .taxServiceId(DEFAULT_TAX_SERVICE_ID);
        return taxServiceActivity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxServiceActivity createUpdatedEntity(EntityManager em) {
        TaxServiceActivity taxServiceActivity = new TaxServiceActivity()
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
            .comments(UPDATED_COMMENTS)
            .taxServiceId(UPDATED_TAX_SERVICE_ID);
        return taxServiceActivity;
    }

    @BeforeEach
    public void initTest() {
        taxServiceActivity = createEntity(em);
    }

    @Test
    @Transactional
    void createTaxServiceActivity() throws Exception {
        int databaseSizeBeforeCreate = taxServiceActivityRepository.findAll().size();
        // Create the TaxServiceActivity
        restTaxServiceActivityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taxServiceActivity))
            )
            .andExpect(status().isCreated());

        // Validate the TaxServiceActivity in the database
        List<TaxServiceActivity> taxServiceActivityList = taxServiceActivityRepository.findAll();
        assertThat(taxServiceActivityList).hasSize(databaseSizeBeforeCreate + 1);
        TaxServiceActivity testTaxServiceActivity = taxServiceActivityList.get(taxServiceActivityList.size() - 1);
        assertThat(testTaxServiceActivity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaxServiceActivity.getServiceUrl()).isEqualTo(DEFAULT_SERVICE_URL);
        assertThat(testTaxServiceActivity.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTaxServiceActivity.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTaxServiceActivity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaxServiceActivity.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testTaxServiceActivity.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTaxServiceActivity.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testTaxServiceActivity.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTaxServiceActivity.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTaxServiceActivity.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testTaxServiceActivity.getTaxServiceId()).isEqualTo(DEFAULT_TAX_SERVICE_ID);
    }

    @Test
    @Transactional
    void createTaxServiceActivityWithExistingId() throws Exception {
        // Create the TaxServiceActivity with an existing ID
        taxServiceActivity.setId(1L);

        int databaseSizeBeforeCreate = taxServiceActivityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxServiceActivityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taxServiceActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxServiceActivity in the database
        List<TaxServiceActivity> taxServiceActivityList = taxServiceActivityRepository.findAll();
        assertThat(taxServiceActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaxServiceActivities() throws Exception {
        // Initialize the database
        taxServiceActivityRepository.saveAndFlush(taxServiceActivity);

        // Get all the taxServiceActivityList
        restTaxServiceActivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxServiceActivity.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].taxServiceId").value(hasItem(DEFAULT_TAX_SERVICE_ID.intValue())));
    }

    @Test
    @Transactional
    void getTaxServiceActivity() throws Exception {
        // Initialize the database
        taxServiceActivityRepository.saveAndFlush(taxServiceActivity);

        // Get the taxServiceActivity
        restTaxServiceActivityMockMvc
            .perform(get(ENTITY_API_URL_ID, taxServiceActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxServiceActivity.getId().intValue()))
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
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.taxServiceId").value(DEFAULT_TAX_SERVICE_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTaxServiceActivity() throws Exception {
        // Get the taxServiceActivity
        restTaxServiceActivityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaxServiceActivity() throws Exception {
        // Initialize the database
        taxServiceActivityRepository.saveAndFlush(taxServiceActivity);

        int databaseSizeBeforeUpdate = taxServiceActivityRepository.findAll().size();

        // Update the taxServiceActivity
        TaxServiceActivity updatedTaxServiceActivity = taxServiceActivityRepository.findById(taxServiceActivity.getId()).get();
        // Disconnect from session so that the updates on updatedTaxServiceActivity are not directly saved in db
        em.detach(updatedTaxServiceActivity);
        updatedTaxServiceActivity
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
            .comments(UPDATED_COMMENTS)
            .taxServiceId(UPDATED_TAX_SERVICE_ID);

        restTaxServiceActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaxServiceActivity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTaxServiceActivity))
            )
            .andExpect(status().isOk());

        // Validate the TaxServiceActivity in the database
        List<TaxServiceActivity> taxServiceActivityList = taxServiceActivityRepository.findAll();
        assertThat(taxServiceActivityList).hasSize(databaseSizeBeforeUpdate);
        TaxServiceActivity testTaxServiceActivity = taxServiceActivityList.get(taxServiceActivityList.size() - 1);
        assertThat(testTaxServiceActivity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaxServiceActivity.getServiceUrl()).isEqualTo(UPDATED_SERVICE_URL);
        assertThat(testTaxServiceActivity.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTaxServiceActivity.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTaxServiceActivity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaxServiceActivity.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTaxServiceActivity.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTaxServiceActivity.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testTaxServiceActivity.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTaxServiceActivity.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaxServiceActivity.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testTaxServiceActivity.getTaxServiceId()).isEqualTo(UPDATED_TAX_SERVICE_ID);
    }

    @Test
    @Transactional
    void putNonExistingTaxServiceActivity() throws Exception {
        int databaseSizeBeforeUpdate = taxServiceActivityRepository.findAll().size();
        taxServiceActivity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxServiceActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taxServiceActivity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taxServiceActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxServiceActivity in the database
        List<TaxServiceActivity> taxServiceActivityList = taxServiceActivityRepository.findAll();
        assertThat(taxServiceActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaxServiceActivity() throws Exception {
        int databaseSizeBeforeUpdate = taxServiceActivityRepository.findAll().size();
        taxServiceActivity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxServiceActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taxServiceActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxServiceActivity in the database
        List<TaxServiceActivity> taxServiceActivityList = taxServiceActivityRepository.findAll();
        assertThat(taxServiceActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaxServiceActivity() throws Exception {
        int databaseSizeBeforeUpdate = taxServiceActivityRepository.findAll().size();
        taxServiceActivity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxServiceActivityMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taxServiceActivity))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxServiceActivity in the database
        List<TaxServiceActivity> taxServiceActivityList = taxServiceActivityRepository.findAll();
        assertThat(taxServiceActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaxServiceActivityWithPatch() throws Exception {
        // Initialize the database
        taxServiceActivityRepository.saveAndFlush(taxServiceActivity);

        int databaseSizeBeforeUpdate = taxServiceActivityRepository.findAll().size();

        // Update the taxServiceActivity using partial update
        TaxServiceActivity partialUpdatedTaxServiceActivity = new TaxServiceActivity();
        partialUpdatedTaxServiceActivity.setId(taxServiceActivity.getId());

        partialUpdatedTaxServiceActivity
            .name(UPDATED_NAME)
            .serviceUrl(UPDATED_SERVICE_URL)
            .endDate(UPDATED_END_DATE)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .status(UPDATED_STATUS)
            .taxServiceId(UPDATED_TAX_SERVICE_ID);

        restTaxServiceActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxServiceActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaxServiceActivity))
            )
            .andExpect(status().isOk());

        // Validate the TaxServiceActivity in the database
        List<TaxServiceActivity> taxServiceActivityList = taxServiceActivityRepository.findAll();
        assertThat(taxServiceActivityList).hasSize(databaseSizeBeforeUpdate);
        TaxServiceActivity testTaxServiceActivity = taxServiceActivityList.get(taxServiceActivityList.size() - 1);
        assertThat(testTaxServiceActivity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaxServiceActivity.getServiceUrl()).isEqualTo(UPDATED_SERVICE_URL);
        assertThat(testTaxServiceActivity.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTaxServiceActivity.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTaxServiceActivity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaxServiceActivity.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTaxServiceActivity.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTaxServiceActivity.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testTaxServiceActivity.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTaxServiceActivity.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaxServiceActivity.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testTaxServiceActivity.getTaxServiceId()).isEqualTo(UPDATED_TAX_SERVICE_ID);
    }

    @Test
    @Transactional
    void fullUpdateTaxServiceActivityWithPatch() throws Exception {
        // Initialize the database
        taxServiceActivityRepository.saveAndFlush(taxServiceActivity);

        int databaseSizeBeforeUpdate = taxServiceActivityRepository.findAll().size();

        // Update the taxServiceActivity using partial update
        TaxServiceActivity partialUpdatedTaxServiceActivity = new TaxServiceActivity();
        partialUpdatedTaxServiceActivity.setId(taxServiceActivity.getId());

        partialUpdatedTaxServiceActivity
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
            .comments(UPDATED_COMMENTS)
            .taxServiceId(UPDATED_TAX_SERVICE_ID);

        restTaxServiceActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxServiceActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaxServiceActivity))
            )
            .andExpect(status().isOk());

        // Validate the TaxServiceActivity in the database
        List<TaxServiceActivity> taxServiceActivityList = taxServiceActivityRepository.findAll();
        assertThat(taxServiceActivityList).hasSize(databaseSizeBeforeUpdate);
        TaxServiceActivity testTaxServiceActivity = taxServiceActivityList.get(taxServiceActivityList.size() - 1);
        assertThat(testTaxServiceActivity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaxServiceActivity.getServiceUrl()).isEqualTo(UPDATED_SERVICE_URL);
        assertThat(testTaxServiceActivity.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTaxServiceActivity.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTaxServiceActivity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaxServiceActivity.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTaxServiceActivity.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTaxServiceActivity.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testTaxServiceActivity.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTaxServiceActivity.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaxServiceActivity.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testTaxServiceActivity.getTaxServiceId()).isEqualTo(UPDATED_TAX_SERVICE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingTaxServiceActivity() throws Exception {
        int databaseSizeBeforeUpdate = taxServiceActivityRepository.findAll().size();
        taxServiceActivity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxServiceActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taxServiceActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taxServiceActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxServiceActivity in the database
        List<TaxServiceActivity> taxServiceActivityList = taxServiceActivityRepository.findAll();
        assertThat(taxServiceActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaxServiceActivity() throws Exception {
        int databaseSizeBeforeUpdate = taxServiceActivityRepository.findAll().size();
        taxServiceActivity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxServiceActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taxServiceActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxServiceActivity in the database
        List<TaxServiceActivity> taxServiceActivityList = taxServiceActivityRepository.findAll();
        assertThat(taxServiceActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaxServiceActivity() throws Exception {
        int databaseSizeBeforeUpdate = taxServiceActivityRepository.findAll().size();
        taxServiceActivity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxServiceActivityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taxServiceActivity))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxServiceActivity in the database
        List<TaxServiceActivity> taxServiceActivityList = taxServiceActivityRepository.findAll();
        assertThat(taxServiceActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaxServiceActivity() throws Exception {
        // Initialize the database
        taxServiceActivityRepository.saveAndFlush(taxServiceActivity);

        int databaseSizeBeforeDelete = taxServiceActivityRepository.findAll().size();

        // Delete the taxServiceActivity
        restTaxServiceActivityMockMvc
            .perform(delete(ENTITY_API_URL_ID, taxServiceActivity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaxServiceActivity> taxServiceActivityList = taxServiceActivityRepository.findAll();
        assertThat(taxServiceActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

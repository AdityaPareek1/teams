package com.findit.teams.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.findit.teams.IntegrationTest;
import com.findit.teams.domain.CustomerProfile;
import com.findit.teams.repository.CustomerProfileRepository;
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
 * Integration tests for the {@link CustomerProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomerProfileResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/customer-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerProfileMockMvc;

    private CustomerProfile customerProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerProfile createEntity(EntityManager em) {
        CustomerProfile customerProfile = new CustomerProfile()
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .url(DEFAULT_URL)
            .aadhar(DEFAULT_AADHAR)
            .address(DEFAULT_ADDRESS)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return customerProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerProfile createUpdatedEntity(EntityManager em) {
        CustomerProfile customerProfile = new CustomerProfile()
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .url(UPDATED_URL)
            .aadhar(UPDATED_AADHAR)
            .address(UPDATED_ADDRESS)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return customerProfile;
    }

    @BeforeEach
    public void initTest() {
        customerProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomerProfile() throws Exception {
        int databaseSizeBeforeCreate = customerProfileRepository.findAll().size();
        // Create the CustomerProfile
        restCustomerProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerProfile))
            )
            .andExpect(status().isCreated());

        // Validate the CustomerProfile in the database
        List<CustomerProfile> customerProfileList = customerProfileRepository.findAll();
        assertThat(customerProfileList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerProfile testCustomerProfile = customerProfileList.get(customerProfileList.size() - 1);
        assertThat(testCustomerProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomerProfile.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testCustomerProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomerProfile.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testCustomerProfile.getAadhar()).isEqualTo(DEFAULT_AADHAR);
        assertThat(testCustomerProfile.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCustomerProfile.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustomerProfile.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCustomerProfile.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCustomerProfile.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testCustomerProfile.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createCustomerProfileWithExistingId() throws Exception {
        // Create the CustomerProfile with an existing ID
        customerProfile.setId(1L);

        int databaseSizeBeforeCreate = customerProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerProfile in the database
        List<CustomerProfile> customerProfileList = customerProfileRepository.findAll();
        assertThat(customerProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustomerProfiles() throws Exception {
        // Initialize the database
        customerProfileRepository.saveAndFlush(customerProfile);

        // Get all the customerProfileList
        restCustomerProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].aadhar").value(hasItem(DEFAULT_AADHAR)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getCustomerProfile() throws Exception {
        // Initialize the database
        customerProfileRepository.saveAndFlush(customerProfile);

        // Get the customerProfile
        restCustomerProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, customerProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerProfile.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.aadhar").value(DEFAULT_AADHAR))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingCustomerProfile() throws Exception {
        // Get the customerProfile
        restCustomerProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomerProfile() throws Exception {
        // Initialize the database
        customerProfileRepository.saveAndFlush(customerProfile);

        int databaseSizeBeforeUpdate = customerProfileRepository.findAll().size();

        // Update the customerProfile
        CustomerProfile updatedCustomerProfile = customerProfileRepository.findById(customerProfile.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerProfile are not directly saved in db
        em.detach(updatedCustomerProfile);
        updatedCustomerProfile
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .url(UPDATED_URL)
            .aadhar(UPDATED_AADHAR)
            .address(UPDATED_ADDRESS)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restCustomerProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomerProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomerProfile))
            )
            .andExpect(status().isOk());

        // Validate the CustomerProfile in the database
        List<CustomerProfile> customerProfileList = customerProfileRepository.findAll();
        assertThat(customerProfileList).hasSize(databaseSizeBeforeUpdate);
        CustomerProfile testCustomerProfile = customerProfileList.get(customerProfileList.size() - 1);
        assertThat(testCustomerProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomerProfile.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testCustomerProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomerProfile.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testCustomerProfile.getAadhar()).isEqualTo(UPDATED_AADHAR);
        assertThat(testCustomerProfile.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCustomerProfile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomerProfile.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCustomerProfile.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomerProfile.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testCustomerProfile.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingCustomerProfile() throws Exception {
        int databaseSizeBeforeUpdate = customerProfileRepository.findAll().size();
        customerProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerProfile in the database
        List<CustomerProfile> customerProfileList = customerProfileRepository.findAll();
        assertThat(customerProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomerProfile() throws Exception {
        int databaseSizeBeforeUpdate = customerProfileRepository.findAll().size();
        customerProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerProfile in the database
        List<CustomerProfile> customerProfileList = customerProfileRepository.findAll();
        assertThat(customerProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomerProfile() throws Exception {
        int databaseSizeBeforeUpdate = customerProfileRepository.findAll().size();
        customerProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerProfileMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerProfile in the database
        List<CustomerProfile> customerProfileList = customerProfileRepository.findAll();
        assertThat(customerProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomerProfileWithPatch() throws Exception {
        // Initialize the database
        customerProfileRepository.saveAndFlush(customerProfile);

        int databaseSizeBeforeUpdate = customerProfileRepository.findAll().size();

        // Update the customerProfile using partial update
        CustomerProfile partialUpdatedCustomerProfile = new CustomerProfile();
        partialUpdatedCustomerProfile.setId(customerProfile.getId());

        partialUpdatedCustomerProfile
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restCustomerProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerProfile))
            )
            .andExpect(status().isOk());

        // Validate the CustomerProfile in the database
        List<CustomerProfile> customerProfileList = customerProfileRepository.findAll();
        assertThat(customerProfileList).hasSize(databaseSizeBeforeUpdate);
        CustomerProfile testCustomerProfile = customerProfileList.get(customerProfileList.size() - 1);
        assertThat(testCustomerProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomerProfile.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testCustomerProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomerProfile.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testCustomerProfile.getAadhar()).isEqualTo(DEFAULT_AADHAR);
        assertThat(testCustomerProfile.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCustomerProfile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomerProfile.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCustomerProfile.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomerProfile.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testCustomerProfile.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateCustomerProfileWithPatch() throws Exception {
        // Initialize the database
        customerProfileRepository.saveAndFlush(customerProfile);

        int databaseSizeBeforeUpdate = customerProfileRepository.findAll().size();

        // Update the customerProfile using partial update
        CustomerProfile partialUpdatedCustomerProfile = new CustomerProfile();
        partialUpdatedCustomerProfile.setId(customerProfile.getId());

        partialUpdatedCustomerProfile
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .url(UPDATED_URL)
            .aadhar(UPDATED_AADHAR)
            .address(UPDATED_ADDRESS)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restCustomerProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerProfile))
            )
            .andExpect(status().isOk());

        // Validate the CustomerProfile in the database
        List<CustomerProfile> customerProfileList = customerProfileRepository.findAll();
        assertThat(customerProfileList).hasSize(databaseSizeBeforeUpdate);
        CustomerProfile testCustomerProfile = customerProfileList.get(customerProfileList.size() - 1);
        assertThat(testCustomerProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomerProfile.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testCustomerProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomerProfile.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testCustomerProfile.getAadhar()).isEqualTo(UPDATED_AADHAR);
        assertThat(testCustomerProfile.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCustomerProfile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomerProfile.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCustomerProfile.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomerProfile.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testCustomerProfile.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingCustomerProfile() throws Exception {
        int databaseSizeBeforeUpdate = customerProfileRepository.findAll().size();
        customerProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customerProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerProfile in the database
        List<CustomerProfile> customerProfileList = customerProfileRepository.findAll();
        assertThat(customerProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomerProfile() throws Exception {
        int databaseSizeBeforeUpdate = customerProfileRepository.findAll().size();
        customerProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerProfile in the database
        List<CustomerProfile> customerProfileList = customerProfileRepository.findAll();
        assertThat(customerProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomerProfile() throws Exception {
        int databaseSizeBeforeUpdate = customerProfileRepository.findAll().size();
        customerProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerProfileMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerProfile in the database
        List<CustomerProfile> customerProfileList = customerProfileRepository.findAll();
        assertThat(customerProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomerProfile() throws Exception {
        // Initialize the database
        customerProfileRepository.saveAndFlush(customerProfile);

        int databaseSizeBeforeDelete = customerProfileRepository.findAll().size();

        // Delete the customerProfile
        restCustomerProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, customerProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerProfile> customerProfileList = customerProfileRepository.findAll();
        assertThat(customerProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.findit.teams.web.rest;
//package com.findit.teams.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.findit.teams.IntegrationTest;
//import com.findit.teams.domain.CustomerQueryActivity;
//import com.findit.teams.repository.CustomerQueryActivityRepository;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//import javax.persistence.EntityManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Integration tests for the {@link CustomerQueryActivityResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class CustomerQueryActivityResourceIT {
//
//    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
//    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";
//
//    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
//    private static final String UPDATED_EMAIL = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
//    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";
//
//    private static final String DEFAULT_SERVICE_TYPE = "AAAAAAAAAA";
//    private static final String UPDATED_SERVICE_TYPE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
//    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
//    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";
//
//    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
//    private static final String UPDATED_STATUS = "BBBBBBBBBB";
//
//    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
//    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";
//
//    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
//    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";
//
//    private static final Long DEFAULT_CUSTOMER_QUERY_ID = 1L;
//    private static final Long UPDATED_CUSTOMER_QUERY_ID = 2L;
//
//    private static final String ENTITY_API_URL = "/api/customer-query-activities";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private CustomerQueryActivityRepository customerQueryActivityRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restCustomerQueryActivityMockMvc;
//
//    private CustomerQueryActivity customerQueryActivity;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static CustomerQueryActivity createEntity(EntityManager em) {
//        CustomerQueryActivity customerQueryActivity = new CustomerQueryActivity()
//            .phoneNumber(DEFAULT_PHONE_NUMBER)
//            .email(DEFAULT_EMAIL)
//            .category(DEFAULT_CATEGORY)
//            .serviceType(DEFAULT_SERVICE_TYPE)
//            .message(DEFAULT_MESSAGE)
//            .comments(DEFAULT_COMMENTS)
//            .status(DEFAULT_STATUS)
//            .createdOn(DEFAULT_CREATED_ON)
//            .createdBy(DEFAULT_CREATED_BY)
//            .updatedOn(DEFAULT_UPDATED_ON)
//            .updatedBy(DEFAULT_UPDATED_BY)
//            .customerQueryId(DEFAULT_CUSTOMER_QUERY_ID);
//        return customerQueryActivity;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static CustomerQueryActivity createUpdatedEntity(EntityManager em) {
//        CustomerQueryActivity customerQueryActivity = new CustomerQueryActivity()
//            .phoneNumber(UPDATED_PHONE_NUMBER)
//            .email(UPDATED_EMAIL)
//            .category(UPDATED_CATEGORY)
//            .serviceType(UPDATED_SERVICE_TYPE)
//            .message(UPDATED_MESSAGE)
//            .comments(UPDATED_COMMENTS)
//            .status(UPDATED_STATUS)
//            .createdOn(UPDATED_CREATED_ON)
//            .createdBy(UPDATED_CREATED_BY)
//            .updatedOn(UPDATED_UPDATED_ON)
//            .updatedBy(UPDATED_UPDATED_BY)
//            .customerQueryId(UPDATED_CUSTOMER_QUERY_ID);
//        return customerQueryActivity;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        customerQueryActivity = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createCustomerQueryActivity() throws Exception {
//        int databaseSizeBeforeCreate = customerQueryActivityRepository.findAll().size();
//        // Create the CustomerQueryActivity
//        restCustomerQueryActivityMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(customerQueryActivity))
//            )
//            .andExpect(status().isCreated());
//
//        // Validate the CustomerQueryActivity in the database
//        List<CustomerQueryActivity> customerQueryActivityList = customerQueryActivityRepository.findAll();
//        assertThat(customerQueryActivityList).hasSize(databaseSizeBeforeCreate + 1);
//        CustomerQueryActivity testCustomerQueryActivity = customerQueryActivityList.get(customerQueryActivityList.size() - 1);
//        assertThat(testCustomerQueryActivity.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
//        assertThat(testCustomerQueryActivity.getEmail()).isEqualTo(DEFAULT_EMAIL);
//        assertThat(testCustomerQueryActivity.getCategory()).isEqualTo(DEFAULT_CATEGORY);
//        assertThat(testCustomerQueryActivity.getServiceType()).isEqualTo(DEFAULT_SERVICE_TYPE);
//        assertThat(testCustomerQueryActivity.getMessage()).isEqualTo(DEFAULT_MESSAGE);
//        assertThat(testCustomerQueryActivity.getComments()).isEqualTo(DEFAULT_COMMENTS);
//        assertThat(testCustomerQueryActivity.getStatus()).isEqualTo(DEFAULT_STATUS);
//        assertThat(testCustomerQueryActivity.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
//        assertThat(testCustomerQueryActivity.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
//        assertThat(testCustomerQueryActivity.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
//        assertThat(testCustomerQueryActivity.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
//        assertThat(testCustomerQueryActivity.getCustomerQueryId()).isEqualTo(DEFAULT_CUSTOMER_QUERY_ID);
//    }
//
//    @Test
//    @Transactional
//    void createCustomerQueryActivityWithExistingId() throws Exception {
//        // Create the CustomerQueryActivity with an existing ID
//        customerQueryActivity.setId(1L);
//
//        int databaseSizeBeforeCreate = customerQueryActivityRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restCustomerQueryActivityMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(customerQueryActivity))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the CustomerQueryActivity in the database
//        List<CustomerQueryActivity> customerQueryActivityList = customerQueryActivityRepository.findAll();
//        assertThat(customerQueryActivityList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void getAllCustomerQueryActivities() throws Exception {
//        // Initialize the database
//        customerQueryActivityRepository.saveAndFlush(customerQueryActivity);
//
//        // Get all the customerQueryActivityList
//        restCustomerQueryActivityMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(customerQueryActivity.getId().intValue())))
//            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
//            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
//            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
//            .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE)))
//            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
//            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
//            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
//            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
//            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
//            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
//            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
//            .andExpect(jsonPath("$.[*].customerQueryId").value(hasItem(DEFAULT_CUSTOMER_QUERY_ID.intValue())));
//    }
//
//    @Test
//    @Transactional
//    void getCustomerQueryActivity() throws Exception {
//        // Initialize the database
//        customerQueryActivityRepository.saveAndFlush(customerQueryActivity);
//
//        // Get the customerQueryActivity
//        restCustomerQueryActivityMockMvc
//            .perform(get(ENTITY_API_URL_ID, customerQueryActivity.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(customerQueryActivity.getId().intValue()))
//            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
//            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
//            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
//            .andExpect(jsonPath("$.serviceType").value(DEFAULT_SERVICE_TYPE))
//            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
//            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
//            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
//            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
//            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
//            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
//            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
//            .andExpect(jsonPath("$.customerQueryId").value(DEFAULT_CUSTOMER_QUERY_ID.intValue()));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingCustomerQueryActivity() throws Exception {
//        // Get the customerQueryActivity
//        restCustomerQueryActivityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putNewCustomerQueryActivity() throws Exception {
//        // Initialize the database
//        customerQueryActivityRepository.saveAndFlush(customerQueryActivity);
//
//        int databaseSizeBeforeUpdate = customerQueryActivityRepository.findAll().size();
//
//        // Update the customerQueryActivity
//        CustomerQueryActivity updatedCustomerQueryActivity = customerQueryActivityRepository.findById(customerQueryActivity.getId()).get();
//        // Disconnect from session so that the updates on updatedCustomerQueryActivity are not directly saved in db
//        em.detach(updatedCustomerQueryActivity);
//        updatedCustomerQueryActivity
//            .phoneNumber(UPDATED_PHONE_NUMBER)
//            .email(UPDATED_EMAIL)
//            .category(UPDATED_CATEGORY)
//            .serviceType(UPDATED_SERVICE_TYPE)
//            .message(UPDATED_MESSAGE)
//            .comments(UPDATED_COMMENTS)
//            .status(UPDATED_STATUS)
//            .createdOn(UPDATED_CREATED_ON)
//            .createdBy(UPDATED_CREATED_BY)
//            .updatedOn(UPDATED_UPDATED_ON)
//            .updatedBy(UPDATED_UPDATED_BY)
//            .customerQueryId(UPDATED_CUSTOMER_QUERY_ID);
//
//        restCustomerQueryActivityMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedCustomerQueryActivity.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomerQueryActivity))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the CustomerQueryActivity in the database
//        List<CustomerQueryActivity> customerQueryActivityList = customerQueryActivityRepository.findAll();
//        assertThat(customerQueryActivityList).hasSize(databaseSizeBeforeUpdate);
//        CustomerQueryActivity testCustomerQueryActivity = customerQueryActivityList.get(customerQueryActivityList.size() - 1);
//        assertThat(testCustomerQueryActivity.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
//        assertThat(testCustomerQueryActivity.getEmail()).isEqualTo(UPDATED_EMAIL);
//        assertThat(testCustomerQueryActivity.getCategory()).isEqualTo(UPDATED_CATEGORY);
//        assertThat(testCustomerQueryActivity.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
//        assertThat(testCustomerQueryActivity.getMessage()).isEqualTo(UPDATED_MESSAGE);
//        assertThat(testCustomerQueryActivity.getComments()).isEqualTo(UPDATED_COMMENTS);
//        assertThat(testCustomerQueryActivity.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testCustomerQueryActivity.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
//        assertThat(testCustomerQueryActivity.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
//        assertThat(testCustomerQueryActivity.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
//        assertThat(testCustomerQueryActivity.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
//        assertThat(testCustomerQueryActivity.getCustomerQueryId()).isEqualTo(UPDATED_CUSTOMER_QUERY_ID);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingCustomerQueryActivity() throws Exception {
//        int databaseSizeBeforeUpdate = customerQueryActivityRepository.findAll().size();
//        customerQueryActivity.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restCustomerQueryActivityMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, customerQueryActivity.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(customerQueryActivity))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the CustomerQueryActivity in the database
//        List<CustomerQueryActivity> customerQueryActivityList = customerQueryActivityRepository.findAll();
//        assertThat(customerQueryActivityList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchCustomerQueryActivity() throws Exception {
//        int databaseSizeBeforeUpdate = customerQueryActivityRepository.findAll().size();
//        customerQueryActivity.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCustomerQueryActivityMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(customerQueryActivity))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the CustomerQueryActivity in the database
//        List<CustomerQueryActivity> customerQueryActivityList = customerQueryActivityRepository.findAll();
//        assertThat(customerQueryActivityList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamCustomerQueryActivity() throws Exception {
//        int databaseSizeBeforeUpdate = customerQueryActivityRepository.findAll().size();
//        customerQueryActivity.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCustomerQueryActivityMockMvc
//            .perform(
//                put(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(customerQueryActivity))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the CustomerQueryActivity in the database
//        List<CustomerQueryActivity> customerQueryActivityList = customerQueryActivityRepository.findAll();
//        assertThat(customerQueryActivityList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateCustomerQueryActivityWithPatch() throws Exception {
//        // Initialize the database
//        customerQueryActivityRepository.saveAndFlush(customerQueryActivity);
//
//        int databaseSizeBeforeUpdate = customerQueryActivityRepository.findAll().size();
//
//        // Update the customerQueryActivity using partial update
//        CustomerQueryActivity partialUpdatedCustomerQueryActivity = new CustomerQueryActivity();
//        partialUpdatedCustomerQueryActivity.setId(customerQueryActivity.getId());
//
//        partialUpdatedCustomerQueryActivity
//            .email(UPDATED_EMAIL)
//            .serviceType(UPDATED_SERVICE_TYPE)
//            .createdOn(UPDATED_CREATED_ON)
//            .createdBy(UPDATED_CREATED_BY);
//
//        restCustomerQueryActivityMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedCustomerQueryActivity.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerQueryActivity))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the CustomerQueryActivity in the database
//        List<CustomerQueryActivity> customerQueryActivityList = customerQueryActivityRepository.findAll();
//        assertThat(customerQueryActivityList).hasSize(databaseSizeBeforeUpdate);
//        CustomerQueryActivity testCustomerQueryActivity = customerQueryActivityList.get(customerQueryActivityList.size() - 1);
//        assertThat(testCustomerQueryActivity.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
//        assertThat(testCustomerQueryActivity.getEmail()).isEqualTo(UPDATED_EMAIL);
//        assertThat(testCustomerQueryActivity.getCategory()).isEqualTo(DEFAULT_CATEGORY);
//        assertThat(testCustomerQueryActivity.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
//        assertThat(testCustomerQueryActivity.getMessage()).isEqualTo(DEFAULT_MESSAGE);
//        assertThat(testCustomerQueryActivity.getComments()).isEqualTo(DEFAULT_COMMENTS);
//        assertThat(testCustomerQueryActivity.getStatus()).isEqualTo(DEFAULT_STATUS);
//        assertThat(testCustomerQueryActivity.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
//        assertThat(testCustomerQueryActivity.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
//        assertThat(testCustomerQueryActivity.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
//        assertThat(testCustomerQueryActivity.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
//        assertThat(testCustomerQueryActivity.getCustomerQueryId()).isEqualTo(DEFAULT_CUSTOMER_QUERY_ID);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateCustomerQueryActivityWithPatch() throws Exception {
//        // Initialize the database
//        customerQueryActivityRepository.saveAndFlush(customerQueryActivity);
//
//        int databaseSizeBeforeUpdate = customerQueryActivityRepository.findAll().size();
//
//        // Update the customerQueryActivity using partial update
//        CustomerQueryActivity partialUpdatedCustomerQueryActivity = new CustomerQueryActivity();
//        partialUpdatedCustomerQueryActivity.setId(customerQueryActivity.getId());
//
//        partialUpdatedCustomerQueryActivity
//            .phoneNumber(UPDATED_PHONE_NUMBER)
//            .email(UPDATED_EMAIL)
//            .category(UPDATED_CATEGORY)
//            .serviceType(UPDATED_SERVICE_TYPE)
//            .message(UPDATED_MESSAGE)
//            .comments(UPDATED_COMMENTS)
//            .status(UPDATED_STATUS)
//            .createdOn(UPDATED_CREATED_ON)
//            .createdBy(UPDATED_CREATED_BY)
//            .updatedOn(UPDATED_UPDATED_ON)
//            .updatedBy(UPDATED_UPDATED_BY)
//            .customerQueryId(UPDATED_CUSTOMER_QUERY_ID);
//
//        restCustomerQueryActivityMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedCustomerQueryActivity.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerQueryActivity))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the CustomerQueryActivity in the database
//        List<CustomerQueryActivity> customerQueryActivityList = customerQueryActivityRepository.findAll();
//        assertThat(customerQueryActivityList).hasSize(databaseSizeBeforeUpdate);
//        CustomerQueryActivity testCustomerQueryActivity = customerQueryActivityList.get(customerQueryActivityList.size() - 1);
//        assertThat(testCustomerQueryActivity.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
//        assertThat(testCustomerQueryActivity.getEmail()).isEqualTo(UPDATED_EMAIL);
//        assertThat(testCustomerQueryActivity.getCategory()).isEqualTo(UPDATED_CATEGORY);
//        assertThat(testCustomerQueryActivity.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
//        assertThat(testCustomerQueryActivity.getMessage()).isEqualTo(UPDATED_MESSAGE);
//        assertThat(testCustomerQueryActivity.getComments()).isEqualTo(UPDATED_COMMENTS);
//        assertThat(testCustomerQueryActivity.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testCustomerQueryActivity.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
//        assertThat(testCustomerQueryActivity.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
//        assertThat(testCustomerQueryActivity.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
//        assertThat(testCustomerQueryActivity.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
//        assertThat(testCustomerQueryActivity.getCustomerQueryId()).isEqualTo(UPDATED_CUSTOMER_QUERY_ID);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingCustomerQueryActivity() throws Exception {
//        int databaseSizeBeforeUpdate = customerQueryActivityRepository.findAll().size();
//        customerQueryActivity.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restCustomerQueryActivityMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, customerQueryActivity.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(customerQueryActivity))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the CustomerQueryActivity in the database
//        List<CustomerQueryActivity> customerQueryActivityList = customerQueryActivityRepository.findAll();
//        assertThat(customerQueryActivityList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchCustomerQueryActivity() throws Exception {
//        int databaseSizeBeforeUpdate = customerQueryActivityRepository.findAll().size();
//        customerQueryActivity.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCustomerQueryActivityMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(customerQueryActivity))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the CustomerQueryActivity in the database
//        List<CustomerQueryActivity> customerQueryActivityList = customerQueryActivityRepository.findAll();
//        assertThat(customerQueryActivityList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamCustomerQueryActivity() throws Exception {
//        int databaseSizeBeforeUpdate = customerQueryActivityRepository.findAll().size();
//        customerQueryActivity.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCustomerQueryActivityMockMvc
//            .perform(
//                patch(ENTITY_API_URL)
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(customerQueryActivity))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the CustomerQueryActivity in the database
//        List<CustomerQueryActivity> customerQueryActivityList = customerQueryActivityRepository.findAll();
//        assertThat(customerQueryActivityList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteCustomerQueryActivity() throws Exception {
//        // Initialize the database
//        customerQueryActivityRepository.saveAndFlush(customerQueryActivity);
//
//        int databaseSizeBeforeDelete = customerQueryActivityRepository.findAll().size();
//
//        // Delete the customerQueryActivity
//        restCustomerQueryActivityMockMvc
//            .perform(delete(ENTITY_API_URL_ID, customerQueryActivity.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<CustomerQueryActivity> customerQueryActivityList = customerQueryActivityRepository.findAll();
//        assertThat(customerQueryActivityList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}

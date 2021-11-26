package com.findit.teams.web.rest;
//package com.findit.teams.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.findit.teams.IntegrationTest;
//import com.findit.teams.domain.CustomerQuery;
//import com.findit.teams.repository.CustomerQueryRepository;
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
// * Integration tests for the {@link CustomerQueryResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class CustomerQueryResourceIT {
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
//    private static final String ENTITY_API_URL = "/api/customer-queries";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private CustomerQueryRepository customerQueryRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restCustomerQueryMockMvc;
//
//    private CustomerQuery customerQuery;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static CustomerQuery createEntity(EntityManager em) {
//        CustomerQuery customerQuery = new CustomerQuery()
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
//            .updatedBy(DEFAULT_UPDATED_BY);
//        return customerQuery;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static CustomerQuery createUpdatedEntity(EntityManager em) {
//        CustomerQuery customerQuery = new CustomerQuery()
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
//            .updatedBy(UPDATED_UPDATED_BY);
//        return customerQuery;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        customerQuery = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createCustomerQuery() throws Exception {
//        int databaseSizeBeforeCreate = customerQueryRepository.findAll().size();
//        // Create the CustomerQuery
//        restCustomerQueryMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerQuery)))
//            .andExpect(status().isCreated());
//
//        // Validate the CustomerQuery in the database
//        List<CustomerQuery> customerQueryList = customerQueryRepository.findAll();
//        assertThat(customerQueryList).hasSize(databaseSizeBeforeCreate + 1);
//        CustomerQuery testCustomerQuery = customerQueryList.get(customerQueryList.size() - 1);
//        assertThat(testCustomerQuery.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
//        assertThat(testCustomerQuery.getEmail()).isEqualTo(DEFAULT_EMAIL);
//        assertThat(testCustomerQuery.getCategory()).isEqualTo(DEFAULT_CATEGORY);
//        assertThat(testCustomerQuery.getServiceType()).isEqualTo(DEFAULT_SERVICE_TYPE);
//        assertThat(testCustomerQuery.getMessage()).isEqualTo(DEFAULT_MESSAGE);
//        assertThat(testCustomerQuery.getComments()).isEqualTo(DEFAULT_COMMENTS);
//        assertThat(testCustomerQuery.getStatus()).isEqualTo(DEFAULT_STATUS);
//        assertThat(testCustomerQuery.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
//        assertThat(testCustomerQuery.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
//        assertThat(testCustomerQuery.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
//        assertThat(testCustomerQuery.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
//    }
//
//    @Test
//    @Transactional
//    void createCustomerQueryWithExistingId() throws Exception {
//        // Create the CustomerQuery with an existing ID
//        customerQuery.setId(1L);
//
//        int databaseSizeBeforeCreate = customerQueryRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restCustomerQueryMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerQuery)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the CustomerQuery in the database
//        List<CustomerQuery> customerQueryList = customerQueryRepository.findAll();
//        assertThat(customerQueryList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void getAllCustomerQueries() throws Exception {
//        // Initialize the database
//        customerQueryRepository.saveAndFlush(customerQuery);
//
//        // Get all the customerQueryList
//        restCustomerQueryMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(customerQuery.getId().intValue())))
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
//            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
//    }
//
//    @Test
//    @Transactional
//    void getCustomerQuery() throws Exception {
//        // Initialize the database
//        customerQueryRepository.saveAndFlush(customerQuery);
//
//        // Get the customerQuery
//        restCustomerQueryMockMvc
//            .perform(get(ENTITY_API_URL_ID, customerQuery.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(customerQuery.getId().intValue()))
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
//            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingCustomerQuery() throws Exception {
//        // Get the customerQuery
//        restCustomerQueryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putNewCustomerQuery() throws Exception {
//        // Initialize the database
//        customerQueryRepository.saveAndFlush(customerQuery);
//
//        int databaseSizeBeforeUpdate = customerQueryRepository.findAll().size();
//
//        // Update the customerQuery
//        CustomerQuery updatedCustomerQuery = customerQueryRepository.findById(customerQuery.getId()).get();
//        // Disconnect from session so that the updates on updatedCustomerQuery are not directly saved in db
//        em.detach(updatedCustomerQuery);
//        updatedCustomerQuery
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
//            .updatedBy(UPDATED_UPDATED_BY);
//
//        restCustomerQueryMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedCustomerQuery.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomerQuery))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the CustomerQuery in the database
//        List<CustomerQuery> customerQueryList = customerQueryRepository.findAll();
//        assertThat(customerQueryList).hasSize(databaseSizeBeforeUpdate);
//        CustomerQuery testCustomerQuery = customerQueryList.get(customerQueryList.size() - 1);
//        assertThat(testCustomerQuery.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
//        assertThat(testCustomerQuery.getEmail()).isEqualTo(UPDATED_EMAIL);
//        assertThat(testCustomerQuery.getCategory()).isEqualTo(UPDATED_CATEGORY);
//        assertThat(testCustomerQuery.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
//        assertThat(testCustomerQuery.getMessage()).isEqualTo(UPDATED_MESSAGE);
//        assertThat(testCustomerQuery.getComments()).isEqualTo(UPDATED_COMMENTS);
//        assertThat(testCustomerQuery.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testCustomerQuery.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
//        assertThat(testCustomerQuery.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
//        assertThat(testCustomerQuery.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
//        assertThat(testCustomerQuery.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingCustomerQuery() throws Exception {
//        int databaseSizeBeforeUpdate = customerQueryRepository.findAll().size();
//        customerQuery.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restCustomerQueryMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, customerQuery.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(customerQuery))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the CustomerQuery in the database
//        List<CustomerQuery> customerQueryList = customerQueryRepository.findAll();
//        assertThat(customerQueryList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchCustomerQuery() throws Exception {
//        int databaseSizeBeforeUpdate = customerQueryRepository.findAll().size();
//        customerQuery.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCustomerQueryMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(customerQuery))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the CustomerQuery in the database
//        List<CustomerQuery> customerQueryList = customerQueryRepository.findAll();
//        assertThat(customerQueryList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamCustomerQuery() throws Exception {
//        int databaseSizeBeforeUpdate = customerQueryRepository.findAll().size();
//        customerQuery.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCustomerQueryMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerQuery)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the CustomerQuery in the database
//        List<CustomerQuery> customerQueryList = customerQueryRepository.findAll();
//        assertThat(customerQueryList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateCustomerQueryWithPatch() throws Exception {
//        // Initialize the database
//        customerQueryRepository.saveAndFlush(customerQuery);
//
//        int databaseSizeBeforeUpdate = customerQueryRepository.findAll().size();
//
//        // Update the customerQuery using partial update
//        CustomerQuery partialUpdatedCustomerQuery = new CustomerQuery();
//        partialUpdatedCustomerQuery.setId(customerQuery.getId());
//
//        partialUpdatedCustomerQuery
//            .phoneNumber(UPDATED_PHONE_NUMBER)
//            .email(UPDATED_EMAIL)
//            .serviceType(UPDATED_SERVICE_TYPE)
//            .status(UPDATED_STATUS)
//            .createdBy(UPDATED_CREATED_BY)
//            .updatedBy(UPDATED_UPDATED_BY);
//
//        restCustomerQueryMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedCustomerQuery.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerQuery))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the CustomerQuery in the database
//        List<CustomerQuery> customerQueryList = customerQueryRepository.findAll();
//        assertThat(customerQueryList).hasSize(databaseSizeBeforeUpdate);
//        CustomerQuery testCustomerQuery = customerQueryList.get(customerQueryList.size() - 1);
//        assertThat(testCustomerQuery.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
//        assertThat(testCustomerQuery.getEmail()).isEqualTo(UPDATED_EMAIL);
//        assertThat(testCustomerQuery.getCategory()).isEqualTo(DEFAULT_CATEGORY);
//        assertThat(testCustomerQuery.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
//        assertThat(testCustomerQuery.getMessage()).isEqualTo(DEFAULT_MESSAGE);
//        assertThat(testCustomerQuery.getComments()).isEqualTo(DEFAULT_COMMENTS);
//        assertThat(testCustomerQuery.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testCustomerQuery.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
//        assertThat(testCustomerQuery.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
//        assertThat(testCustomerQuery.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
//        assertThat(testCustomerQuery.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateCustomerQueryWithPatch() throws Exception {
//        // Initialize the database
//        customerQueryRepository.saveAndFlush(customerQuery);
//
//        int databaseSizeBeforeUpdate = customerQueryRepository.findAll().size();
//
//        // Update the customerQuery using partial update
//        CustomerQuery partialUpdatedCustomerQuery = new CustomerQuery();
//        partialUpdatedCustomerQuery.setId(customerQuery.getId());
//
//        partialUpdatedCustomerQuery
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
//            .updatedBy(UPDATED_UPDATED_BY);
//
//        restCustomerQueryMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedCustomerQuery.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerQuery))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the CustomerQuery in the database
//        List<CustomerQuery> customerQueryList = customerQueryRepository.findAll();
//        assertThat(customerQueryList).hasSize(databaseSizeBeforeUpdate);
//        CustomerQuery testCustomerQuery = customerQueryList.get(customerQueryList.size() - 1);
//        assertThat(testCustomerQuery.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
//        assertThat(testCustomerQuery.getEmail()).isEqualTo(UPDATED_EMAIL);
//        assertThat(testCustomerQuery.getCategory()).isEqualTo(UPDATED_CATEGORY);
//        assertThat(testCustomerQuery.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
//        assertThat(testCustomerQuery.getMessage()).isEqualTo(UPDATED_MESSAGE);
//        assertThat(testCustomerQuery.getComments()).isEqualTo(UPDATED_COMMENTS);
//        assertThat(testCustomerQuery.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testCustomerQuery.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
//        assertThat(testCustomerQuery.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
//        assertThat(testCustomerQuery.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
//        assertThat(testCustomerQuery.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingCustomerQuery() throws Exception {
//        int databaseSizeBeforeUpdate = customerQueryRepository.findAll().size();
//        customerQuery.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restCustomerQueryMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, customerQuery.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(customerQuery))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the CustomerQuery in the database
//        List<CustomerQuery> customerQueryList = customerQueryRepository.findAll();
//        assertThat(customerQueryList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchCustomerQuery() throws Exception {
//        int databaseSizeBeforeUpdate = customerQueryRepository.findAll().size();
//        customerQuery.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCustomerQueryMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(customerQuery))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the CustomerQuery in the database
//        List<CustomerQuery> customerQueryList = customerQueryRepository.findAll();
//        assertThat(customerQueryList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamCustomerQuery() throws Exception {
//        int databaseSizeBeforeUpdate = customerQueryRepository.findAll().size();
//        customerQuery.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCustomerQueryMockMvc
//            .perform(
//                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(customerQuery))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the CustomerQuery in the database
//        List<CustomerQuery> customerQueryList = customerQueryRepository.findAll();
//        assertThat(customerQueryList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteCustomerQuery() throws Exception {
//        // Initialize the database
//        customerQueryRepository.saveAndFlush(customerQuery);
//
//        int databaseSizeBeforeDelete = customerQueryRepository.findAll().size();
//
//        // Delete the customerQuery
//        restCustomerQueryMockMvc
//            .perform(delete(ENTITY_API_URL_ID, customerQuery.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<CustomerQuery> customerQueryList = customerQueryRepository.findAll();
//        assertThat(customerQueryList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}

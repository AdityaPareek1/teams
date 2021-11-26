package com.findit.teams.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.findit.teams.IntegrationTest;
import com.findit.teams.domain.Developers;
import com.findit.teams.repository.DevelopersRepository;
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
 * Integration tests for the {@link DevelopersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DevelopersResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_SKILLS = "AAAAAAAAAA";
    private static final String UPDATED_SKILLS = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ONE_LINE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ONE_LINE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIBE_YOURSELF = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIBE_YOURSELF = "BBBBBBBBBB";

    private static final String DEFAULT_SPEAK_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_SPEAK_LANGUAGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BORN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BORN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_POST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_POST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_EXPRIACE = "AAAAAAAAAA";
    private static final String UPDATED_EXPRIACE = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_WORK_TYPE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/developers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DevelopersRepository developersRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDevelopersMockMvc;

    private Developers developers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Developers createEntity(EntityManager em) {
        Developers developers = new Developers()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD)
            .username(DEFAULT_USERNAME)
            .skills(DEFAULT_SKILLS)
            .language(DEFAULT_LANGUAGE)
            .profileUrl(DEFAULT_PROFILE_URL)
            .oneLineDescription(DEFAULT_ONE_LINE_DESCRIPTION)
            .describeYourself(DEFAULT_DESCRIBE_YOURSELF)
            .speakLanguage(DEFAULT_SPEAK_LANGUAGE)
            .born(DEFAULT_BORN)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .zipPostCode(DEFAULT_ZIP_POST_CODE)
            .country(DEFAULT_COUNTRY)
            .expriace(DEFAULT_EXPRIACE)
            .workType(DEFAULT_WORK_TYPE)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .status(DEFAULT_STATUS);
        return developers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Developers createUpdatedEntity(EntityManager em) {
        Developers developers = new Developers()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .username(UPDATED_USERNAME)
            .skills(UPDATED_SKILLS)
            .language(UPDATED_LANGUAGE)
            .profileUrl(UPDATED_PROFILE_URL)
            .oneLineDescription(UPDATED_ONE_LINE_DESCRIPTION)
            .describeYourself(UPDATED_DESCRIBE_YOURSELF)
            .speakLanguage(UPDATED_SPEAK_LANGUAGE)
            .born(UPDATED_BORN)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipPostCode(UPDATED_ZIP_POST_CODE)
            .country(UPDATED_COUNTRY)
            .expriace(UPDATED_EXPRIACE)
            .workType(UPDATED_WORK_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .status(UPDATED_STATUS);
        return developers;
    }

    @BeforeEach
    public void initTest() {
        developers = createEntity(em);
    }

    @Test
    @Transactional
    void createDevelopers() throws Exception {
        int databaseSizeBeforeCreate = developersRepository.findAll().size();
        // Create the Developers
        restDevelopersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(developers)))
            .andExpect(status().isCreated());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeCreate + 1);
        Developers testDevelopers = developersList.get(developersList.size() - 1);
        assertThat(testDevelopers.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testDevelopers.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testDevelopers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDevelopers.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testDevelopers.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testDevelopers.getSkills()).isEqualTo(DEFAULT_SKILLS);
        assertThat(testDevelopers.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testDevelopers.getProfileUrl()).isEqualTo(DEFAULT_PROFILE_URL);
        assertThat(testDevelopers.getOneLineDescription()).isEqualTo(DEFAULT_ONE_LINE_DESCRIPTION);
        assertThat(testDevelopers.getDescribeYourself()).isEqualTo(DEFAULT_DESCRIBE_YOURSELF);
        assertThat(testDevelopers.getSpeakLanguage()).isEqualTo(DEFAULT_SPEAK_LANGUAGE);
        assertThat(testDevelopers.getBorn()).isEqualTo(DEFAULT_BORN);
        assertThat(testDevelopers.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testDevelopers.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testDevelopers.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testDevelopers.getZipPostCode()).isEqualTo(DEFAULT_ZIP_POST_CODE);
        assertThat(testDevelopers.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testDevelopers.getExpriace()).isEqualTo(DEFAULT_EXPRIACE);
        assertThat(testDevelopers.getWorkType()).isEqualTo(DEFAULT_WORK_TYPE);
        assertThat(testDevelopers.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDevelopers.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDevelopers.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testDevelopers.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDevelopers.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createDevelopersWithExistingId() throws Exception {
        // Create the Developers with an existing ID
        developers.setId(1L);

        int databaseSizeBeforeCreate = developersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevelopersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(developers)))
            .andExpect(status().isBadRequest());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDevelopers() throws Exception {
        // Initialize the database
        developersRepository.saveAndFlush(developers);

        // Get all the developersList
        restDevelopersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(developers.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].skills").value(hasItem(DEFAULT_SKILLS)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].profileUrl").value(hasItem(DEFAULT_PROFILE_URL)))
            .andExpect(jsonPath("$.[*].oneLineDescription").value(hasItem(DEFAULT_ONE_LINE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].describeYourself").value(hasItem(DEFAULT_DESCRIBE_YOURSELF)))
            .andExpect(jsonPath("$.[*].speakLanguage").value(hasItem(DEFAULT_SPEAK_LANGUAGE)))
            .andExpect(jsonPath("$.[*].born").value(hasItem(DEFAULT_BORN.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].zipPostCode").value(hasItem(DEFAULT_ZIP_POST_CODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].expriace").value(hasItem(DEFAULT_EXPRIACE)))
            .andExpect(jsonPath("$.[*].workType").value(hasItem(DEFAULT_WORK_TYPE)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getDevelopers() throws Exception {
        // Initialize the database
        developersRepository.saveAndFlush(developers);

        // Get the developers
        restDevelopersMockMvc
            .perform(get(ENTITY_API_URL_ID, developers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(developers.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.skills").value(DEFAULT_SKILLS))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.profileUrl").value(DEFAULT_PROFILE_URL))
            .andExpect(jsonPath("$.oneLineDescription").value(DEFAULT_ONE_LINE_DESCRIPTION))
            .andExpect(jsonPath("$.describeYourself").value(DEFAULT_DESCRIBE_YOURSELF))
            .andExpect(jsonPath("$.speakLanguage").value(DEFAULT_SPEAK_LANGUAGE))
            .andExpect(jsonPath("$.born").value(DEFAULT_BORN.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.zipPostCode").value(DEFAULT_ZIP_POST_CODE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.expriace").value(DEFAULT_EXPRIACE))
            .andExpect(jsonPath("$.workType").value(DEFAULT_WORK_TYPE))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingDevelopers() throws Exception {
        // Get the developers
        restDevelopersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDevelopers() throws Exception {
        // Initialize the database
        developersRepository.saveAndFlush(developers);

        int databaseSizeBeforeUpdate = developersRepository.findAll().size();

        // Update the developers
        Developers updatedDevelopers = developersRepository.findById(developers.getId()).get();
        // Disconnect from session so that the updates on updatedDevelopers are not directly saved in db
        em.detach(updatedDevelopers);
        updatedDevelopers
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .username(UPDATED_USERNAME)
            .skills(UPDATED_SKILLS)
            .language(UPDATED_LANGUAGE)
            .profileUrl(UPDATED_PROFILE_URL)
            .oneLineDescription(UPDATED_ONE_LINE_DESCRIPTION)
            .describeYourself(UPDATED_DESCRIBE_YOURSELF)
            .speakLanguage(UPDATED_SPEAK_LANGUAGE)
            .born(UPDATED_BORN)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipPostCode(UPDATED_ZIP_POST_CODE)
            .country(UPDATED_COUNTRY)
            .expriace(UPDATED_EXPRIACE)
            .workType(UPDATED_WORK_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .status(UPDATED_STATUS);

        restDevelopersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDevelopers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDevelopers))
            )
            .andExpect(status().isOk());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeUpdate);
        Developers testDevelopers = developersList.get(developersList.size() - 1);
        assertThat(testDevelopers.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testDevelopers.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testDevelopers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDevelopers.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDevelopers.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testDevelopers.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testDevelopers.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testDevelopers.getProfileUrl()).isEqualTo(UPDATED_PROFILE_URL);
        assertThat(testDevelopers.getOneLineDescription()).isEqualTo(UPDATED_ONE_LINE_DESCRIPTION);
        assertThat(testDevelopers.getDescribeYourself()).isEqualTo(UPDATED_DESCRIBE_YOURSELF);
        assertThat(testDevelopers.getSpeakLanguage()).isEqualTo(UPDATED_SPEAK_LANGUAGE);
        assertThat(testDevelopers.getBorn()).isEqualTo(UPDATED_BORN);
        assertThat(testDevelopers.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDevelopers.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDevelopers.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testDevelopers.getZipPostCode()).isEqualTo(UPDATED_ZIP_POST_CODE);
        assertThat(testDevelopers.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDevelopers.getExpriace()).isEqualTo(UPDATED_EXPRIACE);
        assertThat(testDevelopers.getWorkType()).isEqualTo(UPDATED_WORK_TYPE);
        assertThat(testDevelopers.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDevelopers.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDevelopers.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testDevelopers.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDevelopers.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingDevelopers() throws Exception {
        int databaseSizeBeforeUpdate = developersRepository.findAll().size();
        developers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevelopersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, developers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(developers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDevelopers() throws Exception {
        int databaseSizeBeforeUpdate = developersRepository.findAll().size();
        developers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevelopersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(developers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDevelopers() throws Exception {
        int databaseSizeBeforeUpdate = developersRepository.findAll().size();
        developers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevelopersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(developers)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDevelopersWithPatch() throws Exception {
        // Initialize the database
        developersRepository.saveAndFlush(developers);

        int databaseSizeBeforeUpdate = developersRepository.findAll().size();

        // Update the developers using partial update
        Developers partialUpdatedDevelopers = new Developers();
        partialUpdatedDevelopers.setId(developers.getId());

        partialUpdatedDevelopers
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .username(UPDATED_USERNAME)
            .language(UPDATED_LANGUAGE)
            .oneLineDescription(UPDATED_ONE_LINE_DESCRIPTION)
            .describeYourself(UPDATED_DESCRIBE_YOURSELF)
            .speakLanguage(UPDATED_SPEAK_LANGUAGE)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .workType(UPDATED_WORK_TYPE)
            .updatedOn(UPDATED_UPDATED_ON)
            .status(UPDATED_STATUS);

        restDevelopersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevelopers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevelopers))
            )
            .andExpect(status().isOk());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeUpdate);
        Developers testDevelopers = developersList.get(developersList.size() - 1);
        assertThat(testDevelopers.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testDevelopers.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testDevelopers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDevelopers.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDevelopers.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testDevelopers.getSkills()).isEqualTo(DEFAULT_SKILLS);
        assertThat(testDevelopers.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testDevelopers.getProfileUrl()).isEqualTo(DEFAULT_PROFILE_URL);
        assertThat(testDevelopers.getOneLineDescription()).isEqualTo(UPDATED_ONE_LINE_DESCRIPTION);
        assertThat(testDevelopers.getDescribeYourself()).isEqualTo(UPDATED_DESCRIBE_YOURSELF);
        assertThat(testDevelopers.getSpeakLanguage()).isEqualTo(UPDATED_SPEAK_LANGUAGE);
        assertThat(testDevelopers.getBorn()).isEqualTo(DEFAULT_BORN);
        assertThat(testDevelopers.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testDevelopers.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testDevelopers.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testDevelopers.getZipPostCode()).isEqualTo(DEFAULT_ZIP_POST_CODE);
        assertThat(testDevelopers.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDevelopers.getExpriace()).isEqualTo(DEFAULT_EXPRIACE);
        assertThat(testDevelopers.getWorkType()).isEqualTo(UPDATED_WORK_TYPE);
        assertThat(testDevelopers.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDevelopers.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDevelopers.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testDevelopers.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDevelopers.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateDevelopersWithPatch() throws Exception {
        // Initialize the database
        developersRepository.saveAndFlush(developers);

        int databaseSizeBeforeUpdate = developersRepository.findAll().size();

        // Update the developers using partial update
        Developers partialUpdatedDevelopers = new Developers();
        partialUpdatedDevelopers.setId(developers.getId());

        partialUpdatedDevelopers
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .username(UPDATED_USERNAME)
            .skills(UPDATED_SKILLS)
            .language(UPDATED_LANGUAGE)
            .profileUrl(UPDATED_PROFILE_URL)
            .oneLineDescription(UPDATED_ONE_LINE_DESCRIPTION)
            .describeYourself(UPDATED_DESCRIBE_YOURSELF)
            .speakLanguage(UPDATED_SPEAK_LANGUAGE)
            .born(UPDATED_BORN)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipPostCode(UPDATED_ZIP_POST_CODE)
            .country(UPDATED_COUNTRY)
            .expriace(UPDATED_EXPRIACE)
            .workType(UPDATED_WORK_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .status(UPDATED_STATUS);

        restDevelopersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevelopers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevelopers))
            )
            .andExpect(status().isOk());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeUpdate);
        Developers testDevelopers = developersList.get(developersList.size() - 1);
        assertThat(testDevelopers.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testDevelopers.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testDevelopers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDevelopers.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDevelopers.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testDevelopers.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testDevelopers.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testDevelopers.getProfileUrl()).isEqualTo(UPDATED_PROFILE_URL);
        assertThat(testDevelopers.getOneLineDescription()).isEqualTo(UPDATED_ONE_LINE_DESCRIPTION);
        assertThat(testDevelopers.getDescribeYourself()).isEqualTo(UPDATED_DESCRIBE_YOURSELF);
        assertThat(testDevelopers.getSpeakLanguage()).isEqualTo(UPDATED_SPEAK_LANGUAGE);
        assertThat(testDevelopers.getBorn()).isEqualTo(UPDATED_BORN);
        assertThat(testDevelopers.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDevelopers.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDevelopers.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testDevelopers.getZipPostCode()).isEqualTo(UPDATED_ZIP_POST_CODE);
        assertThat(testDevelopers.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDevelopers.getExpriace()).isEqualTo(UPDATED_EXPRIACE);
        assertThat(testDevelopers.getWorkType()).isEqualTo(UPDATED_WORK_TYPE);
        assertThat(testDevelopers.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDevelopers.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDevelopers.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testDevelopers.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDevelopers.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingDevelopers() throws Exception {
        int databaseSizeBeforeUpdate = developersRepository.findAll().size();
        developers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevelopersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, developers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(developers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDevelopers() throws Exception {
        int databaseSizeBeforeUpdate = developersRepository.findAll().size();
        developers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevelopersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(developers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDevelopers() throws Exception {
        int databaseSizeBeforeUpdate = developersRepository.findAll().size();
        developers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevelopersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(developers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDevelopers() throws Exception {
        // Initialize the database
        developersRepository.saveAndFlush(developers);

        int databaseSizeBeforeDelete = developersRepository.findAll().size();

        // Delete the developers
        restDevelopersMockMvc
            .perform(delete(ENTITY_API_URL_ID, developers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

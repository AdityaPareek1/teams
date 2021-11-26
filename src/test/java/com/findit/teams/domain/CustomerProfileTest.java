package com.findit.teams.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.findit.teams.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerProfileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerProfile.class);
        CustomerProfile customerProfile1 = new CustomerProfile();
        customerProfile1.setId(1L);
        CustomerProfile customerProfile2 = new CustomerProfile();
        customerProfile2.setId(customerProfile1.getId());
        assertThat(customerProfile1).isEqualTo(customerProfile2);
        customerProfile2.setId(2L);
        assertThat(customerProfile1).isNotEqualTo(customerProfile2);
        customerProfile1.setId(null);
        assertThat(customerProfile1).isNotEqualTo(customerProfile2);
    }
}

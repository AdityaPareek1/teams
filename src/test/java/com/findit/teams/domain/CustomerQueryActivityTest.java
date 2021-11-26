package com.findit.teams.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.findit.teams.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerQueryActivityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerQueryActivity.class);
        CustomerQueryActivity customerQueryActivity1 = new CustomerQueryActivity();
        customerQueryActivity1.setId(1L);
        CustomerQueryActivity customerQueryActivity2 = new CustomerQueryActivity();
        customerQueryActivity2.setId(customerQueryActivity1.getId());
        assertThat(customerQueryActivity1).isEqualTo(customerQueryActivity2);
        customerQueryActivity2.setId(2L);
        assertThat(customerQueryActivity1).isNotEqualTo(customerQueryActivity2);
        customerQueryActivity1.setId(null);
        assertThat(customerQueryActivity1).isNotEqualTo(customerQueryActivity2);
    }
}

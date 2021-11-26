package com.findit.teams.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.findit.teams.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DevelopersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Developers.class);
        Developers developers1 = new Developers();
        developers1.setId(1L);
        Developers developers2 = new Developers();
        developers2.setId(developers1.getId());
        assertThat(developers1).isEqualTo(developers2);
        developers2.setId(2L);
        assertThat(developers1).isNotEqualTo(developers2);
        developers1.setId(null);
        assertThat(developers1).isNotEqualTo(developers2);
    }
}

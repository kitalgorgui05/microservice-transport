package com.memoire.kital.raph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class ZoneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zone.class);
        Zone zone1 = new Zone();
        zone1.setId(null);
        Zone zone2 = new Zone();
        zone2.setId(zone1.getId());
        assertThat(zone1).isEqualTo(zone2);
        zone2.setId(null);
        assertThat(zone1).isNotEqualTo(zone2);
        zone1.setId(null);
        assertThat(zone1).isNotEqualTo(zone2);
    }
}

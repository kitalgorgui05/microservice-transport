package com.memoire.kital.raph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class BusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bus.class);
        Bus bus1 = new Bus();
        bus1.setId(null);
        Bus bus2 = new Bus();
        bus2.setId(bus1.getId());
        assertThat(bus1).isEqualTo(bus2);
        bus2.setId(null);
        assertThat(bus1).isNotEqualTo(bus2);
        bus1.setId(null);
        assertThat(bus1).isNotEqualTo(bus2);
    }
}

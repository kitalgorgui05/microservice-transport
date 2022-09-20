package com.memoire.kital.raph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class ProgrammeTransportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgrammeTransport.class);
        ProgrammeTransport programmeTransport1 = new ProgrammeTransport();
        programmeTransport1.setId(1L);
        ProgrammeTransport programmeTransport2 = new ProgrammeTransport();
        programmeTransport2.setId(programmeTransport1.getId());
        assertThat(programmeTransport1).isEqualTo(programmeTransport2);
        programmeTransport2.setId(2L);
        assertThat(programmeTransport1).isNotEqualTo(programmeTransport2);
        programmeTransport1.setId(null);
        assertThat(programmeTransport1).isNotEqualTo(programmeTransport2);
    }
}

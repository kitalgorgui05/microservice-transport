package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class ProgrammeTransportDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgrammeTransportDTO.class);
        ProgrammeTransportDTO programmeTransportDTO1 = new ProgrammeTransportDTO();
        programmeTransportDTO1.setId(1L);
        ProgrammeTransportDTO programmeTransportDTO2 = new ProgrammeTransportDTO();
        assertThat(programmeTransportDTO1).isNotEqualTo(programmeTransportDTO2);
        programmeTransportDTO2.setId(programmeTransportDTO1.getId());
        assertThat(programmeTransportDTO1).isEqualTo(programmeTransportDTO2);
        programmeTransportDTO2.setId(2L);
        assertThat(programmeTransportDTO1).isNotEqualTo(programmeTransportDTO2);
        programmeTransportDTO1.setId(null);
        assertThat(programmeTransportDTO1).isNotEqualTo(programmeTransportDTO2);
    }
}

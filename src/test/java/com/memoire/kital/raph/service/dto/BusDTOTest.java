package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class BusDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusDTO.class);
        BusDTO busDTO1 = new BusDTO();
        busDTO1.setId(null);
        BusDTO busDTO2 = new BusDTO();
        assertThat(busDTO1).isNotEqualTo(busDTO2);
        busDTO2.setId(busDTO1.getId());
        assertThat(busDTO1).isEqualTo(busDTO2);
        busDTO2.setId(null);
        assertThat(busDTO1).isNotEqualTo(busDTO2);
        busDTO1.setId(null);
        assertThat(busDTO1).isNotEqualTo(busDTO2);
    }
}

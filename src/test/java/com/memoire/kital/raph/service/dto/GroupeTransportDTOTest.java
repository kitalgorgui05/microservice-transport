package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class GroupeTransportDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupeTransportDTO.class);
        GroupeTransportDTO groupeTransportDTO1 = new GroupeTransportDTO();
        groupeTransportDTO1.setId(1L);
        GroupeTransportDTO groupeTransportDTO2 = new GroupeTransportDTO();
        assertThat(groupeTransportDTO1).isNotEqualTo(groupeTransportDTO2);
        groupeTransportDTO2.setId(groupeTransportDTO1.getId());
        assertThat(groupeTransportDTO1).isEqualTo(groupeTransportDTO2);
        groupeTransportDTO2.setId(2L);
        assertThat(groupeTransportDTO1).isNotEqualTo(groupeTransportDTO2);
        groupeTransportDTO1.setId(null);
        assertThat(groupeTransportDTO1).isNotEqualTo(groupeTransportDTO2);
    }
}

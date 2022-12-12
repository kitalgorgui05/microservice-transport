package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class ChauffeurDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChauffeurDTO.class);
        ChauffeurDTO chauffeurDTO1 = new ChauffeurDTO();
        chauffeurDTO1.setId(null);
        ChauffeurDTO chauffeurDTO2 = new ChauffeurDTO();
        assertThat(chauffeurDTO1).isNotEqualTo(chauffeurDTO2);
        chauffeurDTO2.setId(chauffeurDTO1.getId());
        assertThat(chauffeurDTO1).isEqualTo(chauffeurDTO2);
        chauffeurDTO2.setId(null);
        assertThat(chauffeurDTO1).isNotEqualTo(chauffeurDTO2);
        chauffeurDTO1.setId(null);
        assertThat(chauffeurDTO1).isNotEqualTo(chauffeurDTO2);
    }
}

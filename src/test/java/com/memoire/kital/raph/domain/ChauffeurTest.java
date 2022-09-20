package com.memoire.kital.raph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class ChauffeurTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chauffeur.class);
        Chauffeur chauffeur1 = new Chauffeur();
        chauffeur1.setId(1L);
        Chauffeur chauffeur2 = new Chauffeur();
        chauffeur2.setId(chauffeur1.getId());
        assertThat(chauffeur1).isEqualTo(chauffeur2);
        chauffeur2.setId(2L);
        assertThat(chauffeur1).isNotEqualTo(chauffeur2);
        chauffeur1.setId(null);
        assertThat(chauffeur1).isNotEqualTo(chauffeur2);
    }
}

package com.memoire.kital.raph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class GroupeTransportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupeTransport.class);
        GroupeTransport groupeTransport1 = new GroupeTransport();
        groupeTransport1.setId(null);
        GroupeTransport groupeTransport2 = new GroupeTransport();
        groupeTransport2.setId(groupeTransport1.getId());
        assertThat(groupeTransport1).isEqualTo(groupeTransport2);
        groupeTransport2.setId(null);
        assertThat(groupeTransport1).isNotEqualTo(groupeTransport2);
        groupeTransport1.setId(null);
        assertThat(groupeTransport1).isNotEqualTo(groupeTransport2);
    }
}

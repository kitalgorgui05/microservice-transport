package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupeTransportMapperTest {

    private GroupeTransportMapper groupeTransportMapper;

    @BeforeEach
    public void setUp() {
        groupeTransportMapper = new GroupeTransportMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(groupeTransportMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(groupeTransportMapper.fromId(null)).isNull();
    }
}

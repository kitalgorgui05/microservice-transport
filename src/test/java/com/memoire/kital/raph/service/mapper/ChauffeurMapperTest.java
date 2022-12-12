package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ChauffeurMapperTest {

    private ChauffeurMapper chauffeurMapper;

    @BeforeEach
    public void setUp() {
        chauffeurMapper = new ChauffeurMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = null;
        assertThat(chauffeurMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(chauffeurMapper.fromId(null)).isNull();
    }
}

package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProgrammeTransportMapperTest {

    private ProgrammeTransportMapper programmeTransportMapper;

    @BeforeEach
    public void setUp() {
        programmeTransportMapper = new ProgrammeTransportMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(programmeTransportMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(programmeTransportMapper.fromId(null)).isNull();
    }
}

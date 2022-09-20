package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BusMapperTest {

    private BusMapper busMapper;

    @BeforeEach
    public void setUp() {
        busMapper = new BusMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(busMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(busMapper.fromId(null)).isNull();
    }
}

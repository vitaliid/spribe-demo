package com.spribe.demo.service;

import com.spribe.demo.dto.RatesTakeResponse;
import com.spribe.demo.entity.RatesTake;
import com.spribe.demo.mapper.RatesTakeMapper;
import com.spribe.demo.mapper.RatesTakeMapperImpl;
import com.spribe.demo.repository.RatesTakeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatesTakeServiceTest {

    private static AutoCloseable openedMocks;

    @Mock
    private InMemoryService inMemoryService;

    @Mock
    private RatesTakeRepository ratesTakeRepository;

    @Spy
    private RatesTakeMapper ratesTakeMapper = new RatesTakeMapperImpl();

    @InjectMocks
    private RatesTakeService ratesTakeService;

    @BeforeAll
    public static void beforeAll() {
        openedMocks = MockitoAnnotations.openMocks(RatesTakeServiceTest.class);
    }

    @Test
    @DisplayName("Get rates returns it from in-memory")
    void getRates_GetsItFromInMemory() {
        // given
        RatesTake ratesTake = new RatesTake();
        ratesTake.setTimestamp(123);
        ratesTake.setRates(Map.of("EUR", "1.0"));
        when(inMemoryService.getRates(any())).thenReturn(ratesTake);

        // when
        RatesTakeResponse rateTakes = ratesTakeService.getRates("USD");

        // then
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(inMemoryService).getRates(stringArgumentCaptor.capture());
        assertEquals("USD", stringArgumentCaptor.getValue());

        verify(ratesTakeMapper).fromEntity(any());

        assertEquals(123, rateTakes.getTimestamp());
        assertEquals(Map.of("EUR", "1.0"), rateTakes.getRates());
    }

    @Test
    @DisplayName("Save rates puts it to DB ")
    void saveRates_PutsItToDb() {
        // given
        RatesTake ratesTake = new RatesTake();
        ratesTake.setId("someId");
        ratesTake.setTimestamp(123);
        ratesTake.setRates(Map.of("EUR", "1.0"));
        when(ratesTakeRepository.save(any())).thenReturn(ratesTake);

        // when
        RatesTake rateTakes = ratesTakeService.saveRates(ratesTake);

        // then
        verify(ratesTakeRepository).save(any());

        assertEquals(123, rateTakes.getTimestamp());
        assertEquals(Map.of("EUR", "1.0"), rateTakes.getRates());
        assertEquals("someId", rateTakes.getId());
    }

    @AfterAll
    public static void afterAll() throws Exception {
        openedMocks.close();
    }
}

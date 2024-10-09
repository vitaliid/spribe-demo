package com.spribe.demo.service;

import com.spribe.demo.entity.Currency;
import com.spribe.demo.repository.CurrencyRepository;
import com.spribe.demo.service.provider.CurrencyProvider;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    private static AutoCloseable openedMocks;

    @Mock
    private InMemoryService inMemoryService;

    @Mock
    private CurrencyProvider currencyProvider;

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeAll
    public static void beforeAll() {
        openedMocks = MockitoAnnotations.openMocks(CurrencyServiceTest.class);
    }

    @Test
    @DisplayName("Get symbols returns it from in-memory")
    void getSymbols_GetsSymbolsFromInMemory() {
        // given
        when(inMemoryService.getSymbols()).thenReturn(Set.of("USD", "EUR"));

        // when
        Set<String> symbols = currencyService.getSymbols();

        // then
        verify(inMemoryService).getSymbols();
        assertEquals(Set.of("USD", "EUR"), symbols);
    }

    @Test
    @DisplayName("Get currencies returns it from DB")
    void getCurrencies_GetsCurrenciesFromDB() {
        // given
        Currency currency = new Currency();
        currency.setSymbol("USD");
        when(currencyRepository.findAll()).thenReturn(List.of(currency));

        // when
        List<Currency> currencies = currencyService.getCurrencies();

        // then
        verify(currencyRepository).findAll();
        assertEquals(List.of(currency), currencies);
    }

    @Test
    @DisplayName("Add currency saves it DB")
    void addCurrency_SavesItToDb() {
        // given
        when(currencyProvider.availableSymbols()).thenReturn(Set.of("USD"));

        // when
        currencyService.addCurrency("USD");

        // then
        ArgumentCaptor<Currency> currencyArgumentCaptor = ArgumentCaptor.forClass(Currency.class);
        verify(currencyRepository).save(currencyArgumentCaptor.capture());
        assertEquals("USD", currencyArgumentCaptor.getValue().getSymbol());
    }

    @Test
    @DisplayName("Add currency saves it in-memory")
    void addCurrency_SavesItToInMemory() {
        // given
        when(currencyProvider.availableSymbols()).thenReturn(Set.of("USD"));

        // when
        currencyService.addCurrency("USD");

        // then
        ArgumentCaptor<String> currencyArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(inMemoryService).registerSymbol(currencyArgumentCaptor.capture());
        assertEquals("USD", currencyArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Add currency fails if it is not available in provider")
    void addCurrency_FailsIfNotAvailable() {
        // given
        when(currencyProvider.availableSymbols()).thenReturn(Set.of());

        // when
        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> currencyService.addCurrency("USD")
        );

        // then
        assertEquals("Symbol USD is not available", thrown.getMessage());
        verify(currencyProvider).availableSymbols();
    }

    @AfterAll
    public static void afterAll() throws Exception {
        openedMocks.close();
    }
}

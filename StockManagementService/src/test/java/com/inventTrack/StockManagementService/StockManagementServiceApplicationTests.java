package com.inventTrack.StockManagementService;

import com.inventTrack.StockManagementService.Model.Stock;
import com.inventTrack.StockManagementService.Repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StockManagementServiceApplicationTests {

	@Mock
	private StockRepository stockRepository;

	@InjectMocks
	private Stock stock;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		stock = new Stock();
		stock.setProductId(1L);
		stock.setQuantity(50);
	}

	@Test
	public void testStockSavedCorrectly() {
		when(stockRepository.save(any(Stock.class))).thenReturn(stock);

		Stock savedStock = stockRepository.save(stock);

		assertNotNull(savedStock);
		assertEquals(stock.getProductId(), savedStock.getProductId());
		assertEquals(stock.getQuantity(), savedStock.getQuantity());
	}

	@Test
	public void testFindStockById() {
		when(stockRepository.findById(stock.getProductId())).thenReturn(Optional.of(stock));

		Optional<Stock> foundStock = stockRepository.findById(stock.getProductId());

		assertTrue(foundStock.isPresent());
		assertEquals(stock.getProductId(), foundStock.get().getProductId());
	}

	@Test
	public void testDeleteStockById() {
		doNothing().when(stockRepository).deleteById(stock.getProductId());

		stockRepository.deleteById(stock.getProductId());

		verify(stockRepository, times(1)).deleteById(stock.getProductId());
	}
}

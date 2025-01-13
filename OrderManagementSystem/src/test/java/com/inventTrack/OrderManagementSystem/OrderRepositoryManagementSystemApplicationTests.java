package com.inventTrack.OrderManagementSystem;

import com.inventTrack.OrderManagementSystem.Controller.OrderController;
import com.inventTrack.OrderManagementSystem.Model.Order;
import com.inventTrack.OrderManagementSystem.Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderRepositoryManagementSystemApplicationTests {

	@MockBean
	private OrderService orderService;

	private OrderController orderController;

	private Order order;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		orderController = new OrderController();
		orderController.orderService = orderService;

		order = new Order();
		order.setId(1L);
		order.setProductId(1L);
		order.setQuantity(5);
		order.setPrice(100.0);
		order.setOrderDate(new Date());
	}

	@Test
	public void testGetAllOrders() {
		when(orderService.getAllOrders()).thenReturn(List.of(order));

		ResponseEntity<List<Order>> response = orderController.getAllOrders();

		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().contains(order));
	}

	@Test
	public void testGetOrderByIdFromController() {
		when(orderService.getOrderById(order.getId())).thenReturn(order);

		ResponseEntity<Order> response = orderController.getOrderById(order.getId());

		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertEquals(order.getId(), response.getBody().getId());
	}
}

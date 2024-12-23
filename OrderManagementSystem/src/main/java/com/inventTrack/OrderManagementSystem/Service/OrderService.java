package com.inventTrack.OrderManagementSystem.Service;

import com.inventTrack.OrderManagementSystem.Model.Order;
import com.inventTrack.OrderManagementSystem.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public Order createOrder(Order order) {
        String stockServiceUrl = "http://localhost:8082/stocks/decrease/" + order.getProductId() + "?amount=" + order.getQuantity();
        restTemplate.patchForObject(stockServiceUrl, null, Void.class);

        order.setOrderDate(new Date());
        return orderRepository.save(order);
    }

    // TODO: update order?

    public void deleteOrder(Long id){
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order with id " + id + " not found");
        }
        orderRepository.deleteById(id);
    }

}

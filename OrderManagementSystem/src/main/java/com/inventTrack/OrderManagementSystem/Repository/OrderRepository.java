package com.inventTrack.OrderManagementSystem.Repository;

import com.inventTrack.OrderManagementSystem.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}

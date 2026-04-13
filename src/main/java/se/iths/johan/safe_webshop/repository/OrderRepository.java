package se.iths.johan.safe_webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.johan.safe_webshop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

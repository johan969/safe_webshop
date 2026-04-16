package se.iths.johan.safe_webshop.service;

import org.springframework.stereotype.Service;
import se.iths.johan.safe_webshop.model.Order;
import se.iths.johan.safe_webshop.model.OrderItem;

@Service
public class EmailService {

    public void sendOrderConfirmation(Order order) {
        System.out.println("--ORDER CONFIRMATION EMAIL--");
        System.out.println("To: " + order.getUsername());
        System.out.println("Date: " + order.getOrderDate());

        for (OrderItem item : order.getItems()) {
            System.out.println(
                    item.getProductName() +
                            " x" +
                            item.getQuantity() +
                            " = " +
                            item.getPrice()
            );
        }

        System.out.println("Total: " + order.getTotalPrice());

    }
}

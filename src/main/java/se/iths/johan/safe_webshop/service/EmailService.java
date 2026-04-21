package se.iths.johan.safe_webshop.service;

import org.springframework.stereotype.Service;
import se.iths.johan.safe_webshop.model.Order;
import se.iths.johan.safe_webshop.model.OrderItem;
import se.iths.johan.springmessenger.model.Email;
import se.iths.johan.springmessenger.service.MessageService;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private final MessageService messageService;

    public EmailService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void sendOrderConfirmation(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String date = order.getOrderDate().format(formatter);


        StringBuilder message = new StringBuilder();

        message.append("Order Confirmation").append("\n");
        message.append("Order Number: " + order.getId()).append("\n\n");
        message.append("To: "+ order.getUsername()).append("\n\n");
        message.append("Date: "+ date).append("\n\n");

        for (OrderItem item : order.getItems()) {
            message.append("Product: " + item.getProductName()).append("\n");
            message.append("Quantity: " + item.getQuantity()).append("\n");
            message.append("Price: " + item.getPrice()+ "kr").append("\n");
            message.append("===========================").append("\n\n\n");
        }

        message.append("Total Price:" +order.getTotalPrice()+ "kr").append("\n");

        Email email = new Email();
        email.setRecipient(order.getUsername());
        email.setSubject("Order Confirmation");
        email.setMessage(message.toString());
        messageService.send(email);
    }
}

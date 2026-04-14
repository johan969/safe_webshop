package se.iths.johan.safe_webshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    private int quantity;

    private double price;

    @ManyToOne
    private Order order;

}

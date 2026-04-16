package se.iths.johan.safe_webshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private LocalDateTime orderDate;

    private double totalPrice;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

}

package se.iths.johan.safe_webshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private double price;
    private String category;
    private int stock;
    private String imageUrl;

}

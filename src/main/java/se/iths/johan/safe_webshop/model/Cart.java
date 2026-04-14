package se.iths.johan.safe_webshop.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Long, Integer> items = new HashMap<>();

    public void addProduct(Long productId) {
        items.put(productId, items.getOrDefault(productId, 0) + 1);
    }

    public void removeProduct(Long productId) {
        items.remove(productId);
    }

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}

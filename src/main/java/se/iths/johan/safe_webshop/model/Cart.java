package se.iths.johan.safe_webshop.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Long, Integer> items = new HashMap<>();

    public void addProduct(Long productId, int quantity) {

        items.put(productId, items.getOrDefault(productId, 0) + quantity);
    }

    public void removeProduct(Long productId) {
        if (!items.containsKey(productId)) return;

        int quantity = items.get(productId);

        if (quantity > 1) {
            items.put(productId, quantity - 1);
        } else {
            items.remove(productId);
        }
    }

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}

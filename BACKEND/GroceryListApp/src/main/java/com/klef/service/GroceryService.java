package com.klef.service;

import com.klef.model.Grocery;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GroceryService {
    private final Map<String, Grocery> groceries = new ConcurrentHashMap<>();

    public GroceryService() {
        // preload some groceries
        Grocery g1 = new Grocery("1", "Apples", 4);
        Grocery g2 = new Grocery("2", "Bread", 1);
        Grocery g3 = new Grocery("3", "Milk", 2);
        groceries.put(g1.getId(), g1);
        groceries.put(g2.getId(), g2);
        groceries.put(g3.getId(), g3);
    }

    public List<Grocery> getAll(String search) {
        if (search == null || search.isBlank()) {
            return new ArrayList<>(groceries.values());
        }
        String q = search.toLowerCase();
        List<Grocery> filtered = new ArrayList<>();
        for (Grocery g : groceries.values()) {
            if (g.getName().toLowerCase().contains(q)) {
                filtered.add(g);
            }
        }
        return filtered;
    }

    public Grocery add(Grocery g) {
        String id = UUID.randomUUID().toString();
        g.setId(id);
        groceries.put(id, g);
        return g;
    }

    public Grocery update(String id, Grocery g) {
        Grocery existing = groceries.get(id);
        if (existing == null) return null;
        if (g.getName() != null) existing.setName(g.getName());
        if (g.getQuantity() > 0) existing.setQuantity(g.getQuantity());
        return existing;
    }

    public boolean delete(String id) {
        return groceries.remove(id) != null;
    }
}

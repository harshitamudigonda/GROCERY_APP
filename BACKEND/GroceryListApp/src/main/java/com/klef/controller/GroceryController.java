package com.klef.controller;

import com.klef.model.Grocery;
import com.klef.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groceries")
@CrossOrigin(origins = "*")  // allow frontend React app
public class GroceryController {

    @Autowired
    private GroceryService service;

    @GetMapping
    public List<Grocery> list(@RequestParam(required = false) String search) {
        return service.getAll(search);
    }

    @PostMapping
    public Grocery add(@RequestBody Grocery grocery) {
        return service.add(grocery);
    }

    @PutMapping("/{id}")
    public Grocery update(@PathVariable String id, @RequestBody Grocery grocery) {
        return service.update(id, grocery);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        boolean ok = service.delete(id);
        return ok ? "Deleted" : "Not Found";
    }
}

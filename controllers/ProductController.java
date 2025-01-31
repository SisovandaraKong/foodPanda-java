package foodPanda.controllers;

import foodPanda.models.Product;
import foodPanda.exceptions.ProductNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private List<Product> products;

    public ProductController() {
        products = new ArrayList<>();
        // Add some dummy drink products
        products.add(new Product(1, "Coca Cola", 2.99, "Classic Coke 330ml"));
        products.add(new Product(2, "Iced Latte", 4.99, "Cold Brewed Coffee with Milk"));
        products.add(new Product(3, "Orange Juice", 3.99, "Freshly Squeezed Orange Juice"));
        products.add(new Product(4, "Green Tea", 3.49, "Japanese Green Tea"));
        products.add(new Product(5, "Smoothie", 5.99, "Mixed Berry Smoothie"));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(int id) throws ProductNotFoundException {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }
}
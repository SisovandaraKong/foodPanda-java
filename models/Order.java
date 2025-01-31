package foodPanda.models;

import foodPanda.models.CartItem;
import foodPanda.models.User;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private User user;
    private List<CartItem> items;
    private LocalDateTime orderDate;
    private double totalAmount;

    public Order(int id, User user, List<CartItem> items) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.orderDate = LocalDateTime.now();
        this.totalAmount = calculateTotal();
    }

    private double calculateTotal() {
        return items.stream()
                .mapToDouble(CartItem::getTotal)
                .sum();
    }

    public int getId() { return id; }
    public User getUser() { return user; }
    public List<CartItem> getItems() { return items; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public double getTotalAmount() { return totalAmount; }
}
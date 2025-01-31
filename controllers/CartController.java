package foodPanda.controllers;

import foodPanda.models.CartItem;
import foodPanda.models.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartController {
    private List<CartItem> cartItems;

    public CartController() {
        cartItems = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        Optional<CartItem> existingItem = cartItems.stream()
                .filter(item -> item.getProduct().getId() == product.getId())
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            cartItems.add(new CartItem(product, quantity));
        }
    }

    public void removeItem(int productId) {
        cartItems.removeIf(item -> item.getProduct().getId() == productId);
    }

    public List<CartItem> getItems() {
        return cartItems;
    }

    public void clear() {
        cartItems.clear();
    }

    public double getTotal() {
        return cartItems.stream()
                .mapToDouble(CartItem::getTotal)
                .sum();
    }
}

package foodPanda.view;

import foodPanda.controllers.CartController;
import foodPanda.controllers.ProductController;
import foodPanda.controllers.UserController;
import foodPanda.exceptions.InvalidLoginException;
import foodPanda.exceptions.ProductNotFoundException;
import foodPanda.models.CartItem;
import foodPanda.models.Order;
import foodPanda.models.Product;
import foodPanda.models.User;

import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoodPandaView {
    private Scanner scanner;
    private UserController userController;
    private ProductController productController;
    private CartController cartController;
    private User currentUser;

    public FoodPandaView() {
        scanner = new Scanner(System.in);
        userController = new UserController();
        productController = new ProductController();
        cartController = new CartController();
    }

    public void start() {
        while (true) {
            try {
                if (currentUser == null) {
                    showLoginMenu();
                } else {
                    showMainMenu();
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n=== FoodPanda Login ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            currentUser = userController.login(username, password);
            System.out.println("Login successful! Welcome, " + currentUser.getUsername());
            playSound("login.wav");
        } catch (InvalidLoginException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== FoodPanda Menu ===");
        System.out.println("1. View Products");
        System.out.println("2. View Cart");
        System.out.println("3. Place Order");
        System.out.println("4. Logout");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    showProducts();
                    break;
                case 2:
                    showCart();
                    break;
                case 3:
                    placeOrder();
                    break;
                case 4:
                    logout();
                    break;
                case 5:
                    System.out.println("Thank you for using FoodPanda!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
        }
    }

    private void showProducts() {
        System.out.println("\n=== Products ===");
        System.out.println("ID  Name            Price     Description");
        System.out.println("----------------------------------------");

        for (Product product : productController.getAllProducts()) {
            System.out.println(product.toString());
        }

        System.out.print("\nEnter product ID to add to cart (0 to go back): ");
        try {
            int productId = Integer.parseInt(scanner.nextLine());
            if (productId != 0) {
                System.out.print("Enter quantity: ");
                int quantity = Integer.parseInt(scanner.nextLine());

                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0!");
                    return;
                }

                Product product = productController.getProductById(productId);
                cartController.addItem(product, quantity);
                System.out.println("Product added to cart!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
        } catch (ProductNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showCart() {
        List<CartItem> items = cartController.getItems();
        if (items.isEmpty()) {
            System.out.println("\nCart is empty!");
            return;
        }

        System.out.println("\n=== Shopping Cart ===");
        System.out.println("ID  Name            Price     Quantity    Total");
        System.out.println("------------------------------------------------");

        for (CartItem item : items) {
            Product product = item.getProduct();
            System.out.printf("%-3d %-15s $%-8.2f %-10d $%.2f%n",
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    item.getQuantity(),
                    item.getTotal());
        }
        System.out.printf("\nTotal Amount: $%.2f%n", cartController.getTotal());

        System.out.print("\nEnter product ID to remove (0 to go back): ");
        try {
            int productId = Integer.parseInt(scanner.nextLine());
            if (productId != 0) {
                cartController.removeItem(productId);
                System.out.println("Product removed from cart!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
        }
    }

    private void placeOrder() {
        List<CartItem> items = cartController.getItems();
        if (items.isEmpty()) {
            System.out.println("\nCannot place order: Cart is empty!");
            return;
        }

        playSound("order.wav");
        Order order = new Order(1, currentUser, new ArrayList<>(items));

        System.out.println("\n=== Order Confirmation ===");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Date: " + order.getOrderDate());
        System.out.println("Customer: " + order.getUser().getUsername());
        System.out.println("\nOrder Details:");
        System.out.println("----------------------------------------");

        for (CartItem item : order.getItems()) {
            Product product = item.getProduct();
            System.out.printf("%-15s x%d\t$%.2f%n",
                    product.getName(),
                    item.getQuantity(),
                    item.getTotal());
        }
        System.out.println("----------------------------------------");
        System.out.printf("Total Amount: $%.2f%n", order.getTotalAmount());

        cartController.clear();
        System.out.println("\nOrder placed successfully!");
    }

    private void logout() {
        currentUser = null;
        cartController.clear();
        System.out.println("Logged out successfully!");
    }

    private void playSound(String soundFile) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("sounds/" + soundFile));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
}
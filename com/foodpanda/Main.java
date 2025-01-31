package foodPanda.com.foodpanda;

import foodPanda.view.FoodPandaView;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting FoodPanda Application...");
        System.out.println("=================================");

        try {
            FoodPandaView foodPandaView = new FoodPandaView();
            foodPandaView.start();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
package foodPanda.models;

import org.nocrala.tools.texttablefmt.Table;
import org.nocrala.tools.texttablefmt.BorderStyle;

public class Product {
    private int id;
    private String name;
    private double price;
    private String description;

    public Product(int id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("%-3d %-15s $%-8.2f %s", id, name, price, description);
    }

    public Table toTable() {
        Table table = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER);

        // Add headers
        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Price");
        table.addCell("Description");

        // Add product data
        table.addCell(String.valueOf(id));
        table.addCell(name);
        table.addCell(String.format("$%.2f", price));
        table.addCell(description);

        return table;
    }


    public static Table createProductsTable(Product[] products) {
        Table table = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER);

        // Add headers
        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Price");
        table.addCell("Description");


        for (Product product : products) {
            table.addCell(String.valueOf(product.getId()));
            table.addCell(product.getName());
            table.addCell(String.format("$%.2f", product.getPrice()));
            table.addCell(product.getDescription());
        }

        return table;
    }
}
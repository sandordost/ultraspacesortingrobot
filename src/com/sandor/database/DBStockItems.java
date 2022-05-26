package com.sandor.database;

import com.sandor.models.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBStockItems {

    public static String[] getStockItems() {
        ResultSet result = DBConnection.performQuerry("SELECT StockItemID, StockItemName, ColorName, TypicalWeightPerUnit, Brand FROM stockitems s INNER JOIN colors c ON c.ColorID = s.ColorID");

        ArrayList<String> finalResult = new ArrayList<>();
        try {
            while (result.next()) {
                String stockItemId = result.getString("StockItemID");
                String stockItemName = result.getString("StockItemName");
                String colorName = result.getString("ColorName");
                String typicalWeightPerUnit = result.getString("TypicalWeightPerUnit");
                String brand = result.getString("Brand");
                finalResult.add(String.format("%s] %s | Merk: %s | Kleur: %s | Gewicht: %s", stockItemId, stockItemName, brand, colorName, typicalWeightPerUnit));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return finalResult.toArray(new String[0]);
    }

    public static Product getStockItem(int stockItemId){
        ResultSet rs = DBConnection.performQuerry("SELECT StockItemName, Brand, TypicalWeightPerUnit, ColorName FROM stockitems s INNER JOIN colors c ON s.ColorID = c.ColorID WHERE StockItemID = " + stockItemId);
        Product p = new Product();

        try{
            if(rs.next()){
                p.setName(rs.getString("StockItemName"));
                p.setBrand(rs.getString("Brand"));
                p.setWeight((int)rs.getDouble("TypicalWeightPerUnit"));
                p.setColor(rs.getString("ColorName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }
}

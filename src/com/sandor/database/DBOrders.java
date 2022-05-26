package com.sandor.database;
import com.sandor.models.Order;
import com.sandor.models.OrderLine;
import com.sandor.models.Product;

import java.sql.*;
import java.util.ArrayList;

public class DBOrders {

    public static Integer[] getOrderIds(){
        ArrayList<Integer> orderIDs = new ArrayList<>();

        ResultSet resultSet = DBConnection.performQuerry("SELECT * FROM orders");

        try {
            //Proces result set
            while (resultSet.next()) {
                orderIDs.add(resultSet.getInt("orderID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderIDs.toArray(new Integer[0]);
    }

    public static String getCustomerNameFromOrder(int orderId){
        ResultSet result = DBConnection.performQuerry("SELECT CustomerName FROM customers " +
                "INNER JOIN orders ON orders.CustomerID = customers.CustomerID " +
                "WHERE OrderID = " + orderId);
        try {
            if(result.next()){
                return result.getString("CustomerName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getOrderDateFromOrder(int orderId){
        ResultSet result = DBConnection.performQuerry("SELECT OrderDate FROM orders WHERE OrderID = " + orderId);

        try{
            if(result.next()){
                return result.getString("OrderDate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Two dimensional array consists of: [productCount] [column]
    //[0][0] = Product name, [0][1] = amount, [0][2] = description, [0][3] = OrderLineID
    public static String[][] getProductsFromOrder(int orderId){
        String[][] orderlines;
        int count = 0;
        ResultSet result = DBConnection.performQuerry("SELECT Count(*) as count FROM orderlines OL INNER JOIN orders O ON OL.OrderID = O.OrderID INNER JOIN stockitems S ON S.StockItemID = OL.StockItemID WHERE O.OrderID = " + orderId);
        try {
            if(result.next()) {
                count = Integer.parseInt(result.getString("count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        result = DBConnection.performQuerry("SELECT OL.OrderLineID, StockItemName, OL.Quantity, OL.Description FROM orderlines OL INNER JOIN orders O ON OL.OrderID = O.OrderID INNER JOIN stockitems S ON S.StockItemID = OL.StockItemID WHERE O.OrderID = " + orderId);

        orderlines = new String[count][4];
        try {
            int i = 0;
            while (result.next()) {
                orderlines[i][0] = result.getString("StockItemName");
                orderlines[i][1] = result.getString("Quantity");
                orderlines[i][2] = result.getString("Description");
                orderlines[i][3] = result.getString("OrderLineID");
                i++;
            }
            return orderlines;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Creates an empty order and returns its ID
    public static int createEmptyOrder(){
        DBConnection.performUpdate("INSERT INTO `orders` (CustomerID) VALUES (2)");
        return 0;
    }

    public static void deleteOrder(int orderId){
        DBConnection.performUpdate("DELETE FROM `orders` WHERE OrderID = " + orderId);
    }

    public static void addProductToOrder(int productId, int orderId, int amount){
        DBConnection.performUpdate(String.format("INSERT INTO orderlines (OrderID, StockItemID, Quantity) VALUES (%s, %s, %s)", orderId, productId, amount));
    }

    public static void deleteOrderLine(int orderLineId) {
        DBConnection.performUpdate(String.format("DELETE FROM `orderlines` WHERE OrderLineID = %s", orderLineId));
    }

    public static Order getOrder(int orderId){
        Order o = new Order();
        ResultSet resultS = DBConnection.performQuerry("SELECT CustomerID, OrderDate FROM orders WHERE OrderID = " + orderId);

        try{
            if(resultS.next()){
                o.setCustomer(DBCustomers.getCustomer(resultS.getInt("CustomerID")));
                o.setId(orderId);
                o.setOrderDate(resultS.getString("OrderDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<OrderLine> oLines = new ArrayList<>();
        resultS = DBConnection.performQuerry("SELECT StockItemID, Quantity FROM orderlines WHERE OrderID = " + orderId);

        try {
            while (resultS.next()) {
                Product p = DBStockItems.getStockItem(resultS.getInt("StockItemID"));

                OrderLine ol = new OrderLine();
                ol.setProduct(p);
                ol.setQuantity(Integer.parseInt(resultS.getString("Quantity")));

                oLines.add(ol);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        o.setOrderLines(oLines);
        return o;
    }
}

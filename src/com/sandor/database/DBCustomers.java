package com.sandor.database;

import com.sandor.models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomers {
    public static Customer getCustomer(int customerId){
        Customer c = new Customer();
        ResultSet result = DBConnection.performQuerry("SELECT CustomerName FROM customers WHERE CustomerID = " + customerId);

        try{
            if(result.next()) {
                c.setName(result.getString("CustomerName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
}

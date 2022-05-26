package com.sandor.dialogs;

import com.sandor.database.DBOrders;
import com.sandor.database.DBStockItems;
import com.sandor.database.DB_Global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class AddProductDialog extends JDialog implements ActionListener {
    private JButton btnSubmit;
    private JButton btnCancel;
    private JList jlAvailableProducts;
    private TextField tfAmount;
    private int orderId;

    public AddProductDialog(int orderId){
        //Config dialog
        setLayout(null);
        setBounds(200, 200, 700, 310);
        setModal(true);
        this.orderId = orderId;

        //// Add fields to dialog
        //Adding title
        JLabel jlTitle = new JLabel("Voeg product toe");
        jlTitle.setBounds(10, 11, 180, 14);
        add(jlTitle);

        //Adding productlist
        jlAvailableProducts = new JList(DBStockItems.getStockItems());
        jlAvailableProducts.setBounds(10, 36, 676, 175);
        add(jlAvailableProducts);

        //Adding label for amount
        JLabel jlAmount = new JLabel("Hoeveelheid:");
        jlAmount.setBounds(10, 222, 77, 14);
        add(jlAmount);

        //Adding textfield for amount
        tfAmount = new TextField();
        tfAmount.setBounds(110, 217, 118, 21);
        add(tfAmount);

        //Adding submit button
        btnSubmit = new JButton("Toevoegen");
        btnSubmit.setBounds(10, 244, 109, 23);
        btnSubmit.addActionListener(this);
        add(btnSubmit);

        //Adding cancel button
        btnCancel = new JButton("Annuleren");
        btnCancel.setBounds(119, 244, 109, 23);
        btnCancel.addActionListener(this);
        add(btnCancel);
    }

    public int getProductSelected(){
        return DB_Global.getIdFromString((String)jlAvailableProducts.getSelectedValue(), ']');
    }

    public int getAmount() throws NumberFormatException{
        return Integer.parseInt(tfAmount.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSubmit){
            try {
                int amnt = getAmount();
                int productId = getProductSelected();
                DBOrders.addProductToOrder(productId, orderId, amnt);
                setVisible(false);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        if(e.getSource() == btnCancel){
            setVisible(false);
        }
    }
}

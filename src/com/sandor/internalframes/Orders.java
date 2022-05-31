package com.sandor.internalframes;

import com.sandor.OrderSorter;
import com.sandor.UltraSpaceSortingMachine;
import com.sandor.database.DBOrders;
import com.sandor.database.DBStockItems;
import com.sandor.database.DB_Global;
import com.sandor.dialogs.AddProductDialog;
import com.sandor.hardware.SorteerRobot;
import com.sandor.models.Container;
import com.sandor.models.Order;
import com.sandor.threads.SorteerProces;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.desktop.SystemSleepEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Orders extends JInternalFrame implements ActionListener {
    private int selectedOrderId;
    private JLabel jlOrderId;
    private JLabel jlCustomer;
    private JLabel jlOrderDate;
    private JList<String> jlProductList;
    private JButton btnVerwerkOrder;
    private JButton btnVerwijderOrder;
    private JButton btnNieuweOrder;
    private JButton btnProductVerwijderen;
    private JButton btnProductToevoegen;
    private JPanel jpOrderInfo;
    private JList jlOrderList;

    public Orders(UltraSpaceSortingMachine ussr) {

        //Setup
        setTitle("Orders");
        setLayout(null);
        setBounds(10, 11, 751, 272);

        //Creating and adding items to orderList
        jlOrderList = new JList();
        updateOrderList();

        //Scroll pane
        JScrollPane jspOrderList = new JScrollPane();
        jspOrderList.setBounds(10, 11, 117, 221);
        add(jspOrderList);

        //Adding orderList to scroll pane
        jspOrderList.setViewportView(jlOrderList);

        //Product info panel
        jpOrderInfo = new JPanel();
        jpOrderInfo.setLayout(null);
        jpOrderInfo.setBounds(136, 11, 597, 221);
        jpOrderInfo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        add(jpOrderInfo);

        //Product info bar
        JPanel jpOrderInfoBar = new JPanel();
        jpOrderInfoBar.setBorder(new LineBorder(Color.GRAY));
        jpOrderInfoBar.setBounds(10, 11, 577, 24);
        FlowLayout fL = new FlowLayout();
        fL.setAlignment(FlowLayout.LEFT);
        jpOrderInfoBar.setLayout(fL);
        jpOrderInfo.add(jpOrderInfoBar);

        //Adding info to product info bar
        JLabel jlOrderIdText = new JLabel("Order ID: ");
        jlOrderIdText.setFont(new Font("Tahoma", Font.BOLD, 11));

        jlOrderId = new JLabel();
        jlOrderId.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JLabel jlCustomerText = new JLabel("Customer: ");
        jlCustomerText.setFont(new Font("Tahoma", Font.BOLD, 11));

        jlCustomer = new JLabel();
        jlCustomer.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JLabel jlOrderDateText = new JLabel("Order date: ");
        jlOrderDateText.setFont(new Font("Tahoma", Font.BOLD, 11));

        jlOrderDate = new JLabel();
        jlOrderDate.setFont(new Font("Tahoma", Font.PLAIN, 11));

        jpOrderInfoBar.add(jlOrderIdText);
        jpOrderInfoBar.add(jlOrderId);

        jpOrderInfoBar.add(jlCustomerText);
        jpOrderInfoBar.add(jlCustomer);

        jpOrderInfoBar.add(jlOrderDateText);
        jpOrderInfoBar.add(jlOrderDate);

        //Product list
        jlProductList = new JList<>();
        jlProductList.setBounds(10, 46, 577, 132);


        jpOrderInfo.add(jlProductList);

        //Product list scrollpane
        JScrollPane jspProductList = new JScrollPane();
        jspProductList.setBounds(10, 46, 577, 132);
        jspProductList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jspProductList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspProductList.setBorder(new LineBorder(Color.GRAY));
        jpOrderInfo.add(jspProductList);
        jspProductList.setViewportView(jlProductList);

        //Update selected order id
        jlOrderList.addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        Object selectedValue = jlOrderList.getSelectedValue();
                        if(selectedValue != null) {
                            selectedOrderId = (int) selectedValue;
                            updateOrderSelection();
                        }
                    }
                }
        );

        //Order verwerken
        btnVerwerkOrder = new JButton("Verwerken");
        btnVerwerkOrder.addActionListener(this);
        btnVerwerkOrder.setForeground(new Color(0, 0, 153));
        btnVerwerkOrder.setBounds(10, 189, 103, 23);
        jpOrderInfo.add(btnVerwerkOrder);

        //Order verwijderen
        btnVerwijderOrder = new JButton("- Order");
        btnVerwijderOrder.addActionListener(this);
        btnVerwijderOrder.setForeground(new Color(153, 0, 0));
        btnVerwijderOrder.setBounds(123, 189, 78, 23);
        jpOrderInfo.add(btnVerwijderOrder);

        //Order toevoegen
        btnNieuweOrder = new JButton("+ Order");
        btnNieuweOrder.setForeground(new Color(0, 102, 0));
        btnNieuweOrder.setBounds(211, 189, 78, 23);
        btnNieuweOrder.addActionListener(this);
        jpOrderInfo.add(btnNieuweOrder);

        //Product verwijderen
        btnProductVerwijderen = new JButton("- Product");
        btnProductVerwijderen.setForeground(new Color(153, 0, 0));
        btnProductVerwijderen.setBounds(299, 189, 91, 23);
        btnProductVerwijderen.addActionListener(this);
        jpOrderInfo.add(btnProductVerwijderen);

        //Producten toevoegen
        btnProductToevoegen = new JButton("+ Product");
        btnProductToevoegen.setForeground(new Color(0, 102, 0));
        btnProductToevoegen.setBounds(400, 189, 91, 23);
        btnProductToevoegen.addActionListener(this);
        jpOrderInfo.add(btnProductToevoegen);
    }

    public void updateOrderList(){
        Integer[] orderIdList = DBOrders.getOrderIds();

        jlOrderList.setModel(new AbstractListModel() {
            @Override
            public int getSize() {
                return orderIdList.length;
            }

            @Override
            public Object getElementAt(int index) {
                return orderIdList[index];
            }
        });
    }

    public void updateOrderSelection() {
        jlOrderId.setText(selectedOrderId + "  ");
        jlCustomer.setText(DBOrders.getCustomerNameFromOrder(selectedOrderId) + "  ");
        jlOrderDate.setText(DBOrders.getOrderDateFromOrder(selectedOrderId) + "  ");
        fillProductList(selectedOrderId);
    }

    public void fillProductList(int orderId){
        String[][] products = DBOrders.getProductsFromOrder(orderId);

        String[] productStrings = new String[products.length];
        for(int i = 0; i < products.length; i++){
            productStrings[i] = products[i][3] + "]  |  " + products[i][0] + "  |  " + products[i][1] + " stuk(s)";
        }

        jlProductList.setModel(new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return productStrings.length;
            }

            @Override
            public String getElementAt(int index) {
                return productStrings[index];
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnVerwerkOrder){
            verwerkOrder(selectedOrderId);
        }
        else if(e.getSource() == btnNieuweOrder){
            addOrder();
        }
        else if(e.getSource() == btnVerwijderOrder){
            deleteOrder(selectedOrderId);
        }
        else if(e.getSource() == btnProductToevoegen){
            addProductToOrder(selectedOrderId);
        }
        else if(e.getSource() == btnProductVerwijderen){
            deleteProductLine();
        }
    }

    public void verwerkOrder(int orderId){
        JDialog dConfirm = new JDialog();
        dConfirm.setBounds(200,200,160,120);
        dConfirm.setVisible(true);
        dConfirm.setLayout(null);

        JLabel jlDialogTitle = new JLabel("Je order is verwerkt");
        jlDialogTitle.setBounds(10,10, 150, 20);
        JButton jbOK = new JButton("OK");
        jbOK.setBounds(10,40, 100, 30);

        dConfirm.add(jlDialogTitle);
        dConfirm.add(jbOK);

        Container[] containers = new Container[3];
        containers[0] = new Container(13);
        containers[1] = new Container(13);
        containers[2] = new Container(13);

        OrderSorter orderSorter = new OrderSorter(DBOrders.getOrder(selectedOrderId), containers);
        UltraSpaceSortingMachine.huidigeOrder = DBOrders.getOrder(selectedOrderId);


        SorteerProces sorteerProces = new SorteerProces();
        sorteerProces.setObjective(containers);
        UltraSpaceSortingMachine.status.refreshScreen(containers, sorteerProces.getCurrentContainers());
        sorteerProces.start();
    }

    //Add an empty order
    public void addOrder(){
        DBOrders.createEmptyOrder();
        updateOrderList();
    }

    //Deletes an order selected by param 'orderId'
    public void deleteOrder(int orderId){
        DBOrders.deleteOrder(orderId);
        updateOrderList();
    }

    public void addProductToOrder(int orderId) {
        AddProductDialog addProductDialog = new AddProductDialog(orderId);
        addProductDialog.setVisible(true);
        fillProductList(selectedOrderId);
    }

    public void deleteProductLine(){
        int orderLineId = DB_Global.getIdFromString(jlProductList.getSelectedValue(), ']');
        DBOrders.deleteOrderLine(orderLineId);
        fillProductList(selectedOrderId);
    }
}

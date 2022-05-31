package com.sandor.internalframes;

import com.sandor.UltraSpaceSortingMachine;
import com.sandor.models.Container;
import com.sandor.models.Product;

import javax.swing.*;
import java.awt.*;

public class Status extends JInternalFrame {
    private JLabel jlHuidigeOrderWeergave;
    private JList[] listContainers;
    private JList[] listContainersCurrent;

    public Status(){
        setTitle("Status");
        setLayout(null);
        setBounds(10, 11, 751, 272);
        listContainers = new JList[3];
        listContainersCurrent = new JList[3];

        JLabel jlHuidigeOrder = new JLabel("Huidige order:");
        jlHuidigeOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
        jlHuidigeOrder.setBounds(10, 12, 105, 14);
        add(jlHuidigeOrder);

        jlHuidigeOrderWeergave = new JLabel("order 22356");
        jlHuidigeOrderWeergave.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jlHuidigeOrderWeergave.setBounds(114, 13, 125, 14);
        add(jlHuidigeOrderWeergave);

        JLabel jlContainer1 = new JLabel("Container 1");
        jlContainer1.setFont(new Font("Tahoma", Font.BOLD, 11));
        jlContainer1.setBounds(10, 38, 85, 14);
        add(jlContainer1);

        JLabel jlContainer2 = new JLabel("Container 2");
        jlContainer2.setFont(new Font("Tahoma", Font.BOLD, 11));
        jlContainer2.setBounds(125, 38, 85, 14);
        add(jlContainer2);

        JLabel jlContainer3 = new JLabel("Container 3");
        jlContainer3.setFont(new Font("Tahoma", Font.BOLD, 11));
        jlContainer3.setBounds(240, 38, 85, 14);
        add(jlContainer3);

        JScrollPane jspContainer1 = new JScrollPane();
        jspContainer1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jspContainer1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspContainer1.setBounds(10, 61, 105, 83);
        add(jspContainer1);

        listContainers[0] = new JList();
        jspContainer1.setViewportView(listContainers[0]);

        JScrollPane jspContainer2 = new JScrollPane();
        jspContainer2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jspContainer2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspContainer2.setBounds(125, 61, 105, 83);
        add(jspContainer2);

        listContainers[1] = new JList();
        jspContainer2.setViewportView(listContainers[1]);

        JScrollPane jspContainer3 = new JScrollPane();
        jspContainer3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jspContainer3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspContainer3.setBounds(240, 61, 105, 83);
        add(jspContainer3);

        listContainers[2] = new JList();
        jspContainer3.setViewportView(listContainers[2]);

        JScrollPane jspContainerCurrent1 = new JScrollPane();
        jspContainerCurrent1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jspContainerCurrent1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspContainerCurrent1.setBounds(365, 61, 105, 83);
        add(jspContainerCurrent1);

        listContainersCurrent[0] = new JList();
        jspContainerCurrent1.setViewportView(listContainersCurrent[0]);

        JScrollPane jspContainerCurrent2 = new JScrollPane();
        jspContainerCurrent2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jspContainerCurrent2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspContainerCurrent2.setBounds(480, 61, 105, 83);
        add(jspContainerCurrent2);

        listContainersCurrent[1] = new JList();
        jspContainerCurrent2.setViewportView(listContainersCurrent[1]);

        JScrollPane jspContainerCurrent3 = new JScrollPane();
        jspContainerCurrent3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jspContainerCurrent3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspContainerCurrent3.setBounds(595, 61, 105, 83);
        add(jspContainerCurrent3);

        listContainersCurrent[2] = new JList();
        jspContainerCurrent3.setViewportView(listContainersCurrent[2]);
    }

    public void refreshScreen(Container[] containers_objective, Container[] containers_current){
        jlHuidigeOrderWeergave.setText("Order " + UltraSpaceSortingMachine.huidigeOrder.getId());
        fillContainerLists(containers_objective, containers_current);
    }

    public void fillContainerLists(Container[] containers, Container[] containers_current){

        if(containers != null) {
            for (int i = 0; i < containers.length; i++) {
                String[] productStrings = new String[containers[i].getProducts().size()];
                Product[] products = containers[i].getProducts().toArray(new Product[0]);
                for (int i2 = 0; i2 < products.length; i2++) {
                    productStrings[i2] = products[i2].toString();
                }
                listContainers[i].setModel(new AbstractListModel() {
                    @Override
                    public int getSize() {
                        return productStrings.length;
                    }

                    @Override
                    public Object getElementAt(int index) {
                        return productStrings[index];
                    }
                });
            }
        }

        if(containers_current != null) {
            for (int i = 0; i < containers_current.length; i++) {
                String[] productStrings = new String[containers_current[i].getProducts().size()];
                Product[] products = containers_current[i].getProducts().toArray(new Product[0]);
                for (int i2 = 0; i2 < products.length; i2++) {
                    productStrings[i2] = products[i2].toString();
                }
                listContainersCurrent[i].setModel(new AbstractListModel() {
                    @Override
                    public int getSize() {
                        return productStrings.length;
                    }

                    @Override
                    public Object getElementAt(int index) {
                        return productStrings[index];
                    }
                });
            }
        }
    }
}

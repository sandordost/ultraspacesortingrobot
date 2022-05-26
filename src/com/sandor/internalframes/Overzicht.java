package com.sandor.internalframes;

import com.sandor.UltraSpaceSortingMachine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Overzicht extends JInternalFrame implements ActionListener {
    JButton b;
    UltraSpaceSortingMachine ussr;

    public Overzicht(UltraSpaceSortingMachine ussr){
        this.ussr = ussr;
        setTitle("Overzicht");
        setLayout(new GridLayout(4,2));
        setBounds(10, 11, 751, 272);
        b = new JButton("Open");
        b.addActionListener(this);
        JLabel l = new JLabel("Open poort 1");
        JButton b2 = new JButton("Sluit");
        JLabel l2 = new JLabel("Open poort 2");
        JButton b3 = new JButton("Sluit");
        JLabel l3 = new JLabel("Open poort 3");
        JButton b4 = new JButton("Open");
        JLabel l4 = new JLabel("Open poort 4");
        add(l);
        add(b);
        add(l2);
        add(b2);
        add(l3);
        add(b3);
        add(l4);
        add(b4);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b){
//            ussr.arduinoConnector.moveServo("80");
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
//            ussr.arduinoConnector.moveServo("10");
        }
    }
}

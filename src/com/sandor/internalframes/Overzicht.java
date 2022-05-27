package com.sandor.internalframes;

import com.sandor.UltraSpaceSortingMachine;
import com.sandor.hardware.SorteerRobot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Overzicht extends JInternalFrame implements ActionListener {
    JButton btnLaden;
    JButton btnBalScannen;
    JButton btnBalDoorlaten;
    UltraSpaceSortingMachine ussr;

    public Overzicht(UltraSpaceSortingMachine ussr){
        this.ussr = ussr;
        setTitle("Overzicht");
        setLayout(new GridLayout(4,2));
        setBounds(10, 11, 751, 272);

        JLabel jlLaden = new JLabel("Gate | Bal in poort laden");
        add(jlLaden);

        btnLaden = new JButton("Laden");
        btnLaden.addActionListener(this);
        add(btnLaden);

        JLabel jlBalScannen = new JLabel("Gate | Bal laten scannen");
        add(jlBalScannen);

        btnBalScannen = new JButton("Scannen");
        btnBalScannen.addActionListener(this);
        add(btnBalScannen);

        JLabel jlBalDoorlaten = new JLabel("Gate | Bal doorlaten");
        add(jlBalDoorlaten);

        btnBalDoorlaten = new JButton("Doorlaten");
        btnBalDoorlaten.addActionListener(this);
        add(btnBalDoorlaten);

        JButton b4 = new JButton("Open");
        JLabel l4 = new JLabel("Open poort 4");

        add(l4);
        add(b4);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnLaden){
            UltraSpaceSortingMachine.sorteerRobot.moveGate(1);
        }
        else if(e.getSource() == btnBalScannen){
            UltraSpaceSortingMachine.sorteerRobot.moveGate(2);
        }
        else if(e.getSource() == btnBalDoorlaten){
            UltraSpaceSortingMachine.sorteerRobot.moveGate(3);
        }
    }
}

package com.sandor.internalframes;

import javax.swing.*;
import java.awt.*;

public class Status extends JInternalFrame {
    public Status(){
        setTitle("Status");
        setLayout(new GridLayout(2,2));
        setBounds(10, 11, 751, 272);
        JLabel jlKleursensor = new JLabel("Kleur sensor: ");
        JPanel jpKleursensorStatus = new JPanel();
        jpKleursensorStatus.setBackground(Color.BLUE);

        JLabel jlHuidigeOrder = new JLabel("Huidige order: ");
        JLabel jlHuidigeOrderStatus = new JLabel("Order 2521");

        add(jlKleursensor);
        add(jpKleursensorStatus);

        add(jlHuidigeOrder);
        add(jlHuidigeOrderStatus);
    }
}
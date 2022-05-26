package com.sandor.internalframes;

import javax.swing.*;
import java.awt.*;

public class Verwerkt extends JInternalFrame {
    public Verwerkt(){
        setTitle("Verwerkte orders");
        setLayout(new GridLayout(1,1));
        setBounds(10, 11, 751, 272);
        JLabel test = new JLabel("Nog geen verwerkte orders");
        add(test);
    }
}

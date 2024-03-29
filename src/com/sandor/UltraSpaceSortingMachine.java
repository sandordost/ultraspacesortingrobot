package com.sandor;

import com.sandor.hardware.InpakRobot;
import com.sandor.hardware.SorteerRobot;
import com.sandor.internalframes.Orders;
import com.sandor.internalframes.Overzicht;
import com.sandor.internalframes.Status;
import com.sandor.internalframes.Verwerkt;
import com.sandor.models.Order;
import com.sandor.threads.SerialReader;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;

public class UltraSpaceSortingMachine implements ActionListener {

	public static ArduinoConnector arduinoConnector;
	public static SorteerRobot sorteerRobot;
	public static SerialReader serialReader;
	public static InpakRobot inpakRobot;
	public static Orders orders;
	public static Verwerkt verwerkt;

	private JFrame frame;
	private final JPanel panel_1 = new JPanel();
	private JPanel panel_2;
	private JPanel panel_3;
	private JButton btnOverzicht;
	private JButton btnStatus;
	private JButton btnOrders;
	private JButton btnVerwerkt;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private Overzicht overzicht;
	public static Status status;
	public static Order huidigeOrder;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UltraSpaceSortingMachine window = new UltraSpaceSortingMachine();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UltraSpaceSortingMachine() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Create instance of SorteerRobot
		arduinoConnector = new ArduinoConnector();
		sorteerRobot = new SorteerRobot();
		serialReader = new SerialReader();
		inpakRobot = new InpakRobot();

		if(arduinoConnector.arduinoIsAvailable()){
			serialReader.start();
		}

		frame = new JFrame();
		frame.setBounds(100, 100, 783, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(12);
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBackground(new Color(20, 20, 20));
		panel.setBounds(0, 0, 771, 40);
		frame.getContentPane().add(panel);
		
		lblNewLabel = new JLabel("Sorteer Robot Applicatie");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setForeground(Color.white);
		panel.add(lblNewLabel);
		panel_1.setBackground(new Color(255, 204, 0));
		panel_1.setBounds(0, 40, 771, 33);
		frame.getContentPane().add(panel_1);
		
		btnOverzicht = new JButton("Overzicht");
		btnOverzicht.setBackground(Color.WHITE);
		btnOverzicht.addActionListener(this);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel_1.add(btnOverzicht);
		
		btnStatus = new JButton("Status");
		btnStatus.setBackground(Color.WHITE);
		btnStatus.addActionListener(this);
		panel_1.add(btnStatus);

		btnOrders = new JButton("Orders");
		btnOrders.setBackground(Color.WHITE);
		btnOrders.addActionListener(this);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel_1.add(btnOrders);

		btnVerwerkt = new JButton("Verwerkt");
		btnVerwerkt.setBackground(Color.WHITE);
		btnVerwerkt.addActionListener(this);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel_1.add(btnVerwerkt);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(100, 0, 0));
		panel_2.setBounds(0, 73, 771, 294);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		//Frames
		overzicht = new Overzicht(this);
		panel_2.add(overzicht);
		overzicht.setVisible(false);

		status = new Status();
		panel_2.add(status);
		status.setVisible(false);

		orders = new Orders(this);
		panel_2.add(orders);
		orders.setVisible(false);

		verwerkt = new Verwerkt(this);
		panel_2.add(verwerkt);
		verwerkt.setVisible(false);
		
		panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setVgap(28);
		flowLayout_1.setHgap(10);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_3.setBackground(new Color(255, 204, 0));
		panel_3.setBounds(0, 367, 771, 48);
		frame.getContentPane().add(panel_3);
		
		lblNewLabel_1 = new JLabel("Deze applicatie is ontwikkeld door groep 4 van de klas ICTM2C");
		lblNewLabel_1.setForeground(new Color(255, 0, 0));
		panel_3.add(lblNewLabel_1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		overzicht.setVisible(false);
		status.setVisible(false);
		orders.setVisible(false);
		verwerkt.setVisible(false);

		if(e.getSource() == btnOverzicht){
			overzicht.setVisible(true);
		}
		if(e.getSource() == btnStatus){
			status.setVisible(true);
		}
		if(e.getSource() == btnOrders){
			orders.setVisible(true);
		}
		if(e.getSource() == btnVerwerkt){
			verwerkt.setVisible(true);
		}
	}
}

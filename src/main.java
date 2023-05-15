import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Desktop;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Toolkit;

public class main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField voltage_textField;
	private JTextField capacitance_textField;
	private JTextField resistor_textField;
	private JPanel results_panel;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public main() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\lz_ro\\eclipse-workspace\\Calculador_resistencias\\ohm-icon.jpg"));
		setTitle("Discharge Resistor Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 349, 325);
		getContentPane().setLayout(null);
		
		JPanel voltage_panel = new JPanel();
		voltage_panel.setToolTipText("");
		voltage_panel.setBounds(10, 11, 304, 38);
		getContentPane().add(voltage_panel);
		voltage_panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Voltage");
		lblNewLabel.setBounds(10, 11, 46, 14);
		voltage_panel.add(lblNewLabel);
		
		Calculate Result = new Calculate();
		
		voltage_textField = new JTextField();
		voltage_textField.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				
			}
		});
		voltage_textField.setText("0");
		voltage_textField.setBounds(96, 8, 100, 20);
		voltage_panel.add(voltage_textField);
		voltage_textField.setColumns(10);
		
		JComboBox voltage_comboBox = new JComboBox();
		voltage_comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Result.Set_Prefix_Voltage((String) voltage_comboBox.getSelectedItem());
			}
		});

		voltage_comboBox.setModel(new DefaultComboBoxModel(new String[] {"uV", "mV", "V", "kV"}));
		voltage_comboBox.setSelectedIndex(2);
		voltage_comboBox.setBounds(206, 7, 88, 22);
		voltage_panel.add(voltage_comboBox);
		
		JPanel capacitance_panel = new JPanel();
		capacitance_panel.setLayout(null);
		capacitance_panel.setToolTipText("");
		capacitance_panel.setBounds(10, 47, 304, 38);
		getContentPane().add(capacitance_panel);
		
		JLabel lblCapacitance = new JLabel("Capacitance");
		lblCapacitance.setBounds(10, 11, 75, 14);
		capacitance_panel.add(lblCapacitance);
		
		capacitance_textField = new JTextField();
		capacitance_textField.setText("0");
		capacitance_textField.setColumns(10);
		capacitance_textField.setBounds(95, 8, 103, 20);
		capacitance_panel.add(capacitance_textField);
		
		JComboBox capacitance_comboBox = new JComboBox();
		capacitance_comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Result.Set_Prefix_Capacitance( (String) capacitance_comboBox.getSelectedItem());
			}
		});

		capacitance_comboBox.setModel(new DefaultComboBoxModel(new String[] {"F", "mF", "uF", "nF", "pF"}));
		capacitance_comboBox.setSelectedIndex(2);
		capacitance_comboBox.setBounds(208, 7, 86, 22);
		capacitance_panel.add(capacitance_comboBox);
		
		JPanel resistor_panel = new JPanel();
		resistor_panel.setLayout(null);
		resistor_panel.setToolTipText("");
		resistor_panel.setBounds(10, 82, 304, 38);
		getContentPane().add(resistor_panel);
		
		JLabel lblResistor = new JLabel("Resistor");
		lblResistor.setBounds(10, 11, 51, 14);
		resistor_panel.add(lblResistor);
		
		resistor_textField = new JTextField();
		resistor_textField.setText("0");
		resistor_textField.setColumns(10);
		resistor_textField.setBounds(95, 8, 105, 20);
		resistor_panel.add(resistor_textField);
		
		JComboBox resistor_comboBox = new JComboBox();
		resistor_comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Result.Set_Prefix_Resistance( (String) resistor_comboBox.getSelectedItem());
			}
		});

		resistor_comboBox.setModel(new DefaultComboBoxModel(new String[] {"MOhms", "kOhms", "Ohms"}));
		resistor_comboBox.setSelectedIndex(2);
		resistor_comboBox.setBounds(210, 7, 84, 22);
		resistor_panel.add(resistor_comboBox);
		
		JPanel buttons_pannel = new JPanel();
		buttons_pannel.setBounds(10, 229, 304, 44);
		getContentPane().add(buttons_pannel);
		buttons_pannel.setLayout(null);

		results_panel = new JPanel();
		results_panel.setBounds(10, 131, 304, 98);
		getContentPane().add(results_panel);
		results_panel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.RED));
		panel.setBounds(106, 40, 188, 58);
		results_panel.add(panel);
		panel.setLayout(null);
		
		JTextField Value_power_Label = new JTextField("0.0000 Watts");
		Value_power_Label.setBackground(Color.WHITE);
		Value_power_Label.setEditable(false);
		Value_power_Label.setBounds(67, 11, 111, 14);
		panel.add(Value_power_Label);
		
		JLabel Value_Energy_Label = new JLabel("0.0000 Joules");
		Value_Energy_Label.setBounds(173, 25, 121, 14);
		results_panel.add(Value_Energy_Label);
		
		JLabel Value_Tau_Label = new JLabel("0.0000 seconds");
		Value_Tau_Label.setBounds(173, 0, 121, 14);
		results_panel.add(Value_Tau_Label);
		
		JTextField Value_Tau5_Label = new JTextField("0.0000 seconds");
		Value_Tau5_Label.setBackground(Color.WHITE);
		Value_Tau5_Label.setEditable(false);
		Value_Tau5_Label.setBounds(67, 36, 111, 14);
		panel.add(Value_Tau5_Label);
		
		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				voltage_textField.setSelectionStart(1);
				
				Result.Calculate_All(Float.parseFloat(voltage_textField.getText()),
						Float.parseFloat(capacitance_textField.getText()),
						Float.parseFloat(resistor_textField.getText())						
						);
				Value_power_Label.setText(Result.Get_Value("W"));
				Value_Energy_Label.setText(Result.Get_Value("J"));
				Value_Tau_Label.setText(Result.Get_Value("T"));
				Value_Tau5_Label.setText(Result.Get_Value("T5"));
				
			    
			}
		});
		btnCalculate.setBounds(10, 11, 89, 23);
		buttons_pannel.add(btnCalculate);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(205, 11, 89, 23);
		buttons_pannel.add(btnExit);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String Resistor_String = String.valueOf(resistor_textField.getText()) + "+" + resistor_comboBox.getSelectedItem() + "+" + Result.Get_Value_Power() + Result.Get_Prefix_Power();
					String URL_Search = "https://www.google.com/search?biw=1920&bih=937&sxsrf=ALeKk039Y2wmwMi9pIyREFu5d4CFlWpiYg%3A1599421711956&ei=Dz1VX83TOYLcsAX9kpnQAw&q=resistor+"+ Resistor_String + "+&oq=resistor+" + Resistor_String + "+&gs_lcp=CgZwc3ktYWIQAxgAMgYIABAWEB4yBggAEBYQHjIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjIICAAQFhAKEB46BAgAEEc6BQgAEMsBUJThAli6xgNg9tMDaABwAXgAgAG_AYgBjwSSAQMwLjOYAQCgAQGqAQdnd3Mtd2l6wAEB&sclient=psy-ab";
					Desktop.getDesktop().browse(new URI(URL_Search));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(106, 11, 89, 23);
		buttons_pannel.add(btnSearch);
		
		
		JLabel lblNewLabel_1 = new JLabel("Tau");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(113, 0, 67, 14);
		results_panel.add(lblNewLabel_1);
		
		
		JLabel lblNewLabel_1_1 = new JLabel("Energy");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(113, 22, 89, 14);
		results_panel.add(lblNewLabel_1_1);
		
		
		
		JLabel lblNewLabel_1_2 = new JLabel("Power");
		lblNewLabel_1_2.setBounds(10, 11, 67, 14);
		panel.add(lblNewLabel_1_2);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		

		JLabel lblNewLabel_1_3 = new JLabel("5 Tau");
		lblNewLabel_1_3.setBounds(10, 36, 67, 14);
		panel.add(lblNewLabel_1_3);
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		

	}
	public JPanel getResults_panel() {
		return results_panel;
	}
}

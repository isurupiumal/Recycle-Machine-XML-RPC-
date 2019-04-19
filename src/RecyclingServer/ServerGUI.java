package RecyclingServer;

import java.awt.EventQueue;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.UIManager;

import org.apache.xmlrpc.WebServer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * 
 * @author Isuru Piumal,1722289
 * GUI interface of the server side and initiate the server
 *
 */
public class ServerGUI implements ActionListener, ItemListener  {

	private JFrame frmRecyclingServer;
	

	String str;
	JLabel clock = new JLabel("");
	static JTextArea txtMachine = new JTextArea();
	static JTextArea txtitem = new JTextArea();
	static JTextArea txtengineer = new JTextArea();
	static JTextArea txttransaction = new JTextArea();
	
	private static JTextField txtcan = new JTextField();
	private static JTextField txtcrate = new JTextField();
	private static JTextField txtpbottle = new JTextField();
	private static JTextField txtgbottle = new JTextField();
	private static JTextField txtpbag = new JTextField();
	
	JTextField username = new JTextField();
	JTextField password = new JTextField();
	JButton btnLogin = new JButton("Login");
	JLabel lbllogin = new JLabel("Log In");
	
	JButton btnUpdate = new JButton("Update");
	JButton btnSummary = new JButton("Summary");
	JFreeChart pieChart = null;
	ChartPanel chartPanel = null;
	JPanel pnlSummary = new JPanel();
	
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
	
	static JComboBox cmbSummary = new JComboBox();
	static JComboBox cmbUpdate = new JComboBox();
	
	static RecyclingService service = new RecyclingService(txtMachine,txtitem,txtengineer,txttransaction,
			txtcan,txtcrate,txtpbottle,txtgbottle,txtpbag,cmbSummary,cmbUpdate); 
	/**
     * set the current date and time to the clock label
     */
	public void tickTock() {
        clock.setText(DateFormat.getDateTimeInstance().format(new Date()));
        
    }
	
	/**
     * change the font type and size of the label clock
     *  And it continuously set the date and time after a second 
     */
	public void Time()
	{
        clock.setHorizontalAlignment(JLabel.CENTER);
        clock.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 12f));
        
        tickTock();
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tickTock();
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();
       
	}
	DefaultPieDataset summaryDataSet = new DefaultPieDataset();
	
	JFreeChart chart = ChartFactory.createPieChart("Summary", summaryDataSet,true,true,false);
	
	
	
	/**
	 * Constructor.
	 */
	public ServerGUI() {
		
		Time();
		
		str = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		str = str + String.format("%-35s%-18s%-9s%-9s%-18s%-18s%-15s","  IP Address","Machine Name","Can","Crate","Plastic Bottle","Glass Bottle","Paper Bags");
		str = str + "\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		txtitem.setEditable(false);
		txtitem.setText(str);
		
		str = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		str = str + String.format("%-15s%-45s%-45s%-45s","  No","IP Address","Machine Name","Connected Time");
		str = str + "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		txtMachine.setEditable(false);
		txtMachine.setText(str);
		
		str= "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		str= str+String.format("%-40s%-30s%-35s%-40s","           IP Address","Machine Name","Activity","Date/Time");
		str= str+"\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		txtengineer.setEditable(false);
		txtengineer.setText(str);
		
		str = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		str = str + String.format("%-35s%-9s%-9s%-18s%-18s%-15s%-9s%-30s","  IP Address","Can","Crate","Plastic Bottle","Glass Bottle","Paper Bags","Total","Time");
		str = str + "\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		txttransaction.setText(str);
		
		initialize();
	}

	
	public void displayItems(String str)
	{
		txtitem.setText(str);
	}
	
	public void displayEngineerLog(String str)
	{
		txtengineer.setText(str);
	}
	
	/**
	 * Performs actions
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(btnSummary)) {
			// create the pie chart
			DefaultPieDataset pieDataSet = new DefaultPieDataset();
			pieDataSet = null;
			
			pnlSummary.removeAll();
			chartPanel.removeAll();
			pnlSummary.setBounds(45, 68, 545, 366);
			pnlSummary.setLayout(null);
			chartPanel = new ChartPanel(pieChart);
			pnlSummary.add(chartPanel);
			pieDataSet = service.DataSet(cmbSummary.getSelectedItem().toString());
			pieChart = ChartFactory.createPieChart("Summary",pieDataSet, true, true, false);
			PiePlot piePlot = (PiePlot)pieChart.getPlot();
			piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})") );
			chartPanel = new ChartPanel(pieChart);
			chartPanel.setBounds(10, 10, 525, 346);
			chartPanel.setPreferredSize(new Dimension(500,340));
			pnlSummary.add(chartPanel);
			pnlSummary.repaint();
			
		}else if(e.getSource().equals(btnUpdate)) {
			
			service.SetValues(cmbUpdate.getSelectedItem().toString());	
			JOptionPane.showMessageDialog(null,"Values Updated");
		}else if(e.getSource().equals(btnLogin)) {
			//login to the server interface
			if(username.getText().equals("admin")&&password.getText().equals("123")) {
				username.setVisible(false);
				password.setVisible(false);
				btnLogin.setVisible(false);
				lbllogin.setVisible(false);
				clock.setVisible(true);
				tabbedPane.setVisible(true);
			}
		}
	}
	
	/**
	 * select the item in the combo box
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource().equals(cmbUpdate)) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
		          Object item = e.getItem();
		          System.out.println(item.toString());
		          service.Values(item.toString());
		       }
			
		}
		
	}
	/**
	 * Initialize the components
	 */
	private void initialize() {
		frmRecyclingServer = new JFrame();
		frmRecyclingServer.setTitle("Recycling Server");
		frmRecyclingServer.setResizable(false);
		frmRecyclingServer.setBounds(100, 100, 800, 600);
		frmRecyclingServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRecyclingServer.getContentPane().setLayout(null);
		
		
		lbllogin.setBounds(375, 150, 50, 40);
		lbllogin.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 14f));   
		frmRecyclingServer.add(lbllogin);
		
		username.setBounds(300, 210, 200, 30);
		frmRecyclingServer.add(username);
		
		password.setBounds(300, 260, 200, 30);
		frmRecyclingServer.add(password);
		
		btnLogin.setBounds(300, 310, 200, 30);
		btnLogin.addActionListener(this);
		frmRecyclingServer.add(btnLogin);
		
		
		tabbedPane.setBorder(null);
		tabbedPane.setBounds(10, 100, 774, 450);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tabbedPane.setBackground(Color.WHITE);
		frmRecyclingServer.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Connected Machines", null, panel, null);
		panel.setLayout(null);
		
		
		txtMachine.setBackground(Color.BLACK);
		txtMachine.setForeground(Color.WHITE);
		txtMachine.setBounds(10, 11, 616, 423);
		panel.add(txtMachine);
		
		
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("Machine Details", null, panel_5, null);
		panel_5.setLayout(null);
		
		
		txtitem.setForeground(Color.WHITE);
		txtitem.setBackground(Color.BLACK);
		txtitem.setBounds(10, 11, 616, 423);
		panel_5.add(txtitem);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Transaction Details", null, panel_1, null);
		panel_1.setLayout(null);
		txttransaction.setEditable(false);
		
		txttransaction.setForeground(Color.WHITE);
		txttransaction.setBackground(Color.BLACK);
		txttransaction.setBounds(10, 11, 616, 423);
		panel_1.add(txttransaction);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Engineers' Log", null, panel_2, null);
		panel_2.setLayout(null);
		
		txtengineer.setBackground(Color.BLACK);
		txtengineer.setForeground(Color.WHITE);
		txtengineer.setBounds(10, 11, 616, 423);
		panel_2.add(txtengineer);
		
		
		
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Update Values", null, panel_3, null);
		panel_3.setLayout(null);
		
		btnUpdate.setBackground(Color.WHITE);
		try {
			BufferedImage image = ImageIO.read(new File("Images/update.png"));
			btnUpdate.setIcon(new ImageIcon(image));			
		} catch (IOException e) {
		}
		btnUpdate.setBounds(420, 376, 109, 35);
		btnUpdate.addActionListener(this);
		panel_3.add(btnUpdate);
		
		JLabel lblMachine = new JLabel("Machine");
		lblMachine.setBounds(154, 35, 70, 14);
		panel_3.add(lblMachine);
		
		cmbUpdate.setBackground(Color.WHITE);
		cmbUpdate.setBounds(245, 32, 200, 20);
		panel_3.add(cmbUpdate);
		cmbUpdate.addItemListener(this);
		
		JLabel lblCan = new JLabel("Can");
		lblCan.setBounds(185, 116, 91, 14);
		panel_3.add(lblCan);
		
		JLabel lblCrate = new JLabel("Crate");
		lblCrate.setBounds(185, 156, 91, 14);
		panel_3.add(lblCrate);
		
		JLabel lblPlasticBottle = new JLabel("Plastic Bottle");
		lblPlasticBottle.setBounds(185, 196, 91, 14);
		panel_3.add(lblPlasticBottle);
		
		JLabel lblGlassBottle = new JLabel("Glass Bottle");
		lblGlassBottle.setBounds(185, 236, 91, 14);
		panel_3.add(lblGlassBottle);
		
		JLabel lblPaperBag = new JLabel("Paper Bag");
		lblPaperBag.setBounds(185, 276, 91, 14);
		panel_3.add(lblPaperBag);
		
		
		txtcan.setBounds(313, 113, 86, 20);
		panel_3.add(txtcan);
		txtcan.setColumns(10);
		
		txtcrate.setBounds(313, 153, 86, 20);
		panel_3.add(txtcrate);
		txtcrate.setColumns(10);
		
		txtpbottle.setBounds(313, 193, 86, 20);
		panel_3.add(txtpbottle);
		txtpbottle.setColumns(10);
		
		txtgbottle.setBounds(313, 233, 86, 20);
		panel_3.add(txtgbottle);
		txtgbottle.setColumns(10);
		
		txtpbag.setBounds(313, 273, 86, 20);
		panel_3.add(txtpbag);
		txtpbag.setColumns(10);
		
		
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(null);
		tabbedPane.addTab("Summary", null, panel_4, null);
		panel_4.setLayout(null);
		
		
		cmbSummary.setBackground(Color.WHITE);
		cmbSummary.setBounds(109, 28, 191, 20);
		panel_4.add(cmbSummary);
		
		
		btnSummary.setBackground(Color.WHITE);
		btnSummary.setBounds(360, 28, 127, 21);
		panel_4.add(btnSummary);
		btnSummary.addActionListener(this);
		
		pnlSummary.setBounds(45, 68, 545, 366);
		panel_4.add(pnlSummary);
		pnlSummary.setLayout(null);
		chartPanel = new ChartPanel(pieChart);
		pnlSummary.add(chartPanel);
		
		
		
		JLabel logo = new JLabel("");
		logo.setBounds(58, 25, 73, 64);
		try {
			BufferedImage image = ImageIO.read(new File("Images/recycle.png"));
			logo.setIcon(new ImageIcon(image));
			frmRecyclingServer.getContentPane().add(logo);
			
		} catch (IOException e) {
			
		}
		frmRecyclingServer.getContentPane().add(logo);
		
		
		clock.setBounds(611, 50, 173, 22);
		frmRecyclingServer.getContentPane().add(clock);
		
		JLabel title = new JLabel("Recycling Server");
		title.setFont(new Font("Tahoma", Font.BOLD, 23));
		title.setForeground(new Color(50, 205, 50));
		title.setBounds(149, 25, 272, 64);
		frmRecyclingServer.getContentPane().add(title);
		tabbedPane.setVisible(false);
		clock.setVisible(false);
		
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI window = new ServerGUI();
					window.frmRecyclingServer.setVisible(true);
					window.frmRecyclingServer.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		try {
			//create the server
			WebServer server = new WebServer(81);
 			server.addHandler("recyclingServer", service);
			server.start();
			
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}

	

	
}


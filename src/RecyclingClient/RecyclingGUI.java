package RecyclingClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.xmlrpc.WebServer;

import RecyclingClient.Bottle;
import RecyclingClient.Can;
import RecyclingServer.RecyclingService;


/**
 * Graphical User Interface for the Recycling Machine.
 *
 *@author Isuru Piumal,1722289
 *
 *
 */
public class RecyclingGUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8983566102382596839L;
	
	
	Can ca = new Can();
	Crate cra = new Crate();
	Bottle bottle = new Bottle();
	PaperBag pbags = new PaperBag();
	GlassBottle glbottle = new GlassBottle();
	
	int weight = 0;
	
	JLabel clock = new JLabel();
	JLabel txt = new JLabel();
	static JTextArea receiptArea =new JTextArea();
	JProgressBar progressbar = new JProgressBar();
	
	JButton slot1 = new JButton(); 
	JButton slot2 = new JButton();
	JButton slot3 = new JButton(); 
	JButton slot4 = new JButton();
	JButton slot5 = new JButton();
	
	
	JButton receipt = new JButton("Receipt"); 
	JButton clear = new JButton("Clear");
	JButton print = new JButton("Print");
	JButton connect = new JButton("Connect to Server");
	JButton login = new JButton("Login");
	JButton logout = new JButton("Logout");
	JButton reset = new JButton("Reset");
	
	JLabel can = new JLabel("Can");
	JLabel pbottle = new JLabel("Plastic Bottle");
	JLabel crate = new JLabel("Crate");
	JLabel pbag = new JLabel("Paper Bag");
	JLabel gbottle = new JLabel("Glass Bottle");
	JLabel status = new JLabel("Status");
	JLabel lblenginer =new JLabel("Engineer Login");
	
	
	
	
	
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
		setLayout(new BorderLayout());
        
        clock.setHorizontalAlignment(JLabel.CENTER);
        clock.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 13f));
        
        tickTock();
        add(clock);

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
	
	
	
	
	

	/**
	 * get images to the buttons from Images folder and set the images
	 */
	public void setIcons()
	{
		try {
			BufferedImage image = ImageIO.read(new File("Images/Can.png"));
			slot1.setIcon(new ImageIcon(image));
			
		}catch(Exception e)
		{
			
		}
		
		try {
			BufferedImage image = ImageIO.read(new File("Images/PlasticBottle.png"));
			slot2.setIcon(new ImageIcon(image));
		}catch(Exception e)
		{
			
		}
		
		try {
			BufferedImage image = ImageIO.read(new File("Images/Crate.png"));
			slot3.setIcon(new ImageIcon(image));
		}catch(Exception e)
		{
			
		}
		
		try {
			BufferedImage image = ImageIO.read(new File("Images/PaperBag.png"));
			slot4.setIcon(new ImageIcon(image));
		}catch(Exception e)
		{
			
		}
		
		try {
			BufferedImage image = ImageIO.read(new File("Images/GlassBottle.png"));
			slot5.setIcon(new ImageIcon(image));
			
		}catch(Exception e)
		{
			
		}
		
	}
	
	Display mydisplay = new Display();
	CustomerPanel myCustomerPanel = new CustomerPanel(mydisplay);
	
	
	public void print(String str) { 
		receiptArea.setText(str);
	}
	
	ActionListener action = new ActionControl(weight,slot1,slot2,slot3,slot4,slot5,receipt,clear,print,connect,login,logout,reset,progressbar,receiptArea);
	
	/**
	 * Initialize components
	 */
	public RecyclingGUI() {
		super();
		setSize(1150, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setTitle("Recycling Machine");
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		
		Time();
		setIcons();
		
		try {
			BufferedImage image = ImageIO.read(new File("Images/recyclingimage.png"));
			
			JLabel logo = new JLabel();
			logo.setIcon(new ImageIcon(image));
			logo.setBounds(10, 10, 300, 110);
			panel.add(logo);
			
		} catch (IOException e) {
			
		}
		
		// connect button
		connect.setBounds(450, 40, 200, 40);
		connect.setBackground(Color.WHITE);
		panel.add(connect);
		connect.addActionListener(action);
		
		// add the clock label and set location and a size
		clock.setBounds(900, 40, 200, 40);
		panel.add(clock);
		
		//Item slots
		slot1.setBounds(12, 130, 150, 150);
		slot2.setBounds(164, 130, 150, 150);
		slot3.setBounds(316, 130, 150, 150);
		slot4.setBounds(468, 130, 150, 150);
		slot5.setBounds(620, 130, 150, 150);
		
		slot1.setEnabled(false);
		slot2.setEnabled(false);
		slot3.setEnabled(false);
		slot4.setEnabled(false);
		slot5.setEnabled(false);
		receipt.setEnabled(false);
		print.setEnabled(false);
		login.setEnabled(false);
		
		panel.add(slot1); 
		panel.add(slot2);
		panel.add(slot3);
		panel.add(slot4);
		panel.add(slot5);
		
		
		can.setBounds(75, 290, 50, 30);
		pbottle.setBounds(200, 290, 100, 30);
		crate.setBounds(375, 290, 50, 30);
		pbag.setBounds(515, 290, 100, 30);
		gbottle.setBounds(660, 290, 100, 30);
		txt.setBounds(400,10,100,10);
		panel.add(txt);
		
		panel.add(can);
		panel.add(pbottle);
		panel.add(crate);
		panel.add(pbag);
		panel.add(gbottle);
		
		
		slot1.addActionListener(action); 
		slot2.addActionListener(action); 
		slot3.addActionListener(action); 
		slot4.addActionListener(action);
		slot5.addActionListener(action);
		
		
		receipt.setBounds(250, 350, 300, 40);
		receipt.setBackground(Color.WHITE);
		panel.add(receipt); 
		receipt.addActionListener(action);
		
		//receipt area
		receiptArea.setText("");
		receiptArea.setBounds(790, 120, 330, 400);
		receiptArea.setBorder(BorderFactory.createLineBorder(Color.black));
		receiptArea.setEditable(false);
		panel.add(receiptArea);
		
		print.setBounds(1010, 540, 100, 30);
		panel.add(print);
		print.addActionListener(action);
		
		status.setBounds(170, 430, 80, 20);
		panel.add(status);
		
		//progressbar
		progressbar.setMinimum(0);
		progressbar.setMaximum(2000);
		progressbar.setBounds(250, 430, 400, 20);
		progressbar.setForeground(new Color(50, 205, 50));
		progressbar.setBackground(Color.WHITE);
		progressbar.setBorderPainted(true);
		progressbar.setStringPainted(true);
		progressbar.setToolTipText("Weight : "+weight);
		panel.add(progressbar);
		
		lblenginer.setBounds(170, 500, 200, 20);
		panel.add(lblenginer);
		login.setBounds(300, 540, 100, 30);
		panel.add(login);
		login.addActionListener(action);
		
		logout.setBounds(300, 540, 100, 30);
		logout.setVisible(false);
		panel.add(logout);
		logout.addActionListener(action);
		
		reset.setBounds(300, 590, 100, 30);
		reset.setEnabled(false);
		panel.add(reset);
		reset.addActionListener(action);
		
		getContentPane().add(panel);
		panel.repaint();
	
	}
	/**
	 * Main method
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String [] args ) throws InterruptedException { 
		RecyclingGUI myGUI = new RecyclingGUI(); 
		myGUI.setVisible(true); 
		myGUI.setLocationRelativeTo(null);
		myGUI.setResizable(false);
		
	}

}

package RecyclingClient;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;



import RecyclingClient.Bottle;
import RecyclingClient.Can;

/**
 * Refactor class of the GUI
 * @author Isuru Piumal, 1722289
 *
 */
public class ActionControl implements ActionListener, PrinterInterface  {
	
	Can ca = new Can();
	Crate cra = new Crate();
	Bottle bottle = new Bottle();
	PaperBag pbags = new PaperBag();
	GlassBottle glbottle = new GlassBottle();
	Display mydisplay = new Display();
	CustomerPanel myCustomerPanel = new CustomerPanel(mydisplay);
	
	Client client = new Client();
	
	//dialogbox to get username and password of engineers login
	JTextField username = new JTextField();
	JTextField password = new JPasswordField();
	Object[] message = {
	    "Username:", username,
	    "Password:", password
	};
	
	//dialogbox to get ip address and port no of server
	JTextField Ipaddress = new JTextField();
	JTextField port = new JTextField();
	Object[] url = {
	    "IP address:", Ipaddress,
	    "Port No:", port
	};
	
	

	
	int weight = 0;
	String ServerIp;
	String ServerPort;
	String ServerURL;
	
	JButton slot1; 
	JButton slot2;
	JButton slot3; 
	JButton slot4;
	JButton slot5 ;
	JButton receipt;
	JButton clear;
	JButton print;
	JButton connect;
	JButton login;
	JButton logout;
	JButton reset;
	
	JProgressBar progressbar = new JProgressBar();;
	static JTextArea outputWindow = new JTextArea();
	JLabel txt = new JLabel();
	
	InetAddress localhost = null;
    String hostname = null;
    String ip = null;
    
    
	
    /**
     * Constructor of the class
     * @param weight
     * @param slot1
     * @param slot2
     * @param slot3
     * @param slot4
     * @param slot5
     * @param receipt
     * @param clear
     * @param print
     * @param connect
     * @param login
     * @param logout
     * @param reset
     * @param progressbar
     * @param outputWindow
     */
	public ActionControl(int weight,JButton slot1,JButton slot2,JButton slot3,JButton slot4,JButton slot5,
			JButton receipt,JButton clear,JButton print,JButton connect,JButton login,JButton logout,JButton reset,
			JProgressBar progressbar,JTextArea outputWindow) 
	{
		this.slot1 = slot1;
		this.slot2 = slot2;
		this.slot3 = slot3;
		this.slot4 = slot4;
		this.slot5 = slot5;
		this.receipt= receipt;
		this.clear = clear;
		this.print = print;		
		this.progressbar = progressbar;
		this.outputWindow = outputWindow;
		this.weight = weight;
		this.connect = connect;
		this.login = login;
		this.logout = logout;
		this.reset = reset;
		
	}
	
	/**
	 * timeout when user user inactive
	 */
	public void timeout()
	{
		Timer timer =  new Timer(30000,new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				progressbar.setValue(0);
				JOptionPane.showMessageDialog(null,"Session TimeOut");
				
				
			}
		});
		
		timer.isRepeats();
		timer.start();
	}


	/**
	 * check is machine full
	 * @param num
	 */
	public void checkfull(int num)
	{
		if(weight>2000)
		{
			JOptionPane.showMessageDialog(null, "Machine is full");	
			
			if(num==1)
			{
				weight = weight - ca.getWeight();
				
				
			}else if(num==2)
			{
				weight = weight - bottle.getWeight();
				
			}else if(num==3)
			{
				weight = weight - cra.getWeight();
				
			}else if(num==4)
			{
				weight = weight - pbags.getWeight();
				
			}else if(num==5)
			{
				weight = weight - glbottle.getWeight();
				
				
			}
		}else
		{
			
			myCustomerPanel.itemReceived(num);
		}
		
	}
	
	
	
	@Override
	public void print(String str) {
		outputWindow.setText(str);
		
	}

	/**
	 * Perform actions
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource().equals(slot1) ) 
		{ 
			weight = weight + ca.getWeight();
			checkfull(1);
			
			
		}else if( e.getSource().equals(slot2) )
		{
			weight = weight + bottle.getWeight();
			checkfull(2);
			
		}else if( e.getSource().equals(slot3) )
		{
			weight = weight + cra.getWeight();
			checkfull(3);
			
		}else if( e.getSource().equals(slot4) )
		{
			weight = weight + pbags.getWeight();
			checkfull(4);
			
		}else if( e.getSource().equals(slot5) )
		{
			weight = weight + glbottle.getWeight();
			checkfull(5);
			
		}else if( e.getSource().equals(receipt) )
		{
			try {
				
				outputWindow.setText(myCustomerPanel.printReceipt());
				
				
			}catch(Exception error)
			{
				
				JOptionPane.showMessageDialog(null, "Please add items");	
			}
			
		}if( e.getSource().equals(print)) {
			try {
				 outputWindow.print();
				 
			}catch(PrinterException err) {
				JOptionPane.showMessageDialog(null,err);
			}
		}else if(e.getSource().equals(connect)) {
			
			
			int option = JOptionPane.showConfirmDialog(null, url, "Connect", JOptionPane.OK_CANCEL_OPTION);
			
			if (option == JOptionPane.OK_OPTION) {
				ServerIp = Ipaddress.getText();
				ServerPort = port.getText();
				client.serveraddress(ServerIp,ServerPort);
				
				client.connectServer();
				client.getValue();
				
				slot1.setEnabled(true);
				slot2.setEnabled(true);
				slot3.setEnabled(true);
				slot4.setEnabled(true);
				slot5.setEnabled(true);
				receipt.setEnabled(true);
				print.setEnabled(true);
				login.setEnabled(true);
				connect.setEnabled(false);
				
			}
			
			
		}else if( e.getSource().equals(login) )
		{
			int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
			    if (username.getText().equals(client.getUserName()) && password.getText().equals(client.getPassword())) {
			        
			        logout.setVisible(true);
			        reset.setEnabled(true);
			        login.setVisible(false);
			        client.engineerActivity("Loged In");
			        client.setValue();
			        outputWindow.setText(getValue());
			    	
			    } else {
			        System.out.println("login failed");
			    }
			}
			
		}else if( e.getSource().equals(logout) )
		{
	        reset.setEnabled(false);
	        login.setVisible(true);
	        logout.setVisible(false);
	        client.engineerActivity("Loged Out");
	        outputWindow.setText("");
	        
		}else if( e.getSource().equals(reset) )
		{
			weight = 0;
			progressbar.setValue(weight);
			client.engineerActivity("Reset");
			JOptionPane.showMessageDialog(null,"Machine has been reset");
		}
		
			progressbar.setValue(weight);
			timeout();
		
		
	}
	
	/**
	 * Display valuue of the items
	 * @return String
	 */
	public String getValue() {
		String str = "";
		
		str = str +"\n-----------------------------------------------------------------------------------\n "+ "\t\tValue "; 
		
		str = str +"\n-----------------------------------------------------------------------------------";
		str = str +"\n\tCan\t\t "+ca.getValue()+"\n";
		str = str +"\n\tCrate\t\t "+cra.getValue()+"\n";
		str = str +"\n\tPlastic Bottle\t\t "+bottle.getValue()+"\n";
		str = str +"\n\tGlass Bottle\t\t "+glbottle.getValue()+"\n";
		str = str +"\n\tPaper Bag\t\t "+pbags.getValue()+"\n";
		
		
		return str;
	}
	

}

package RecyclingServer;

import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.xmlrpc.XmlRpcClient;
import org.jfree.data.general.DefaultPieDataset;

/**
 * 
 * @author Isuru Piumal, 1722289
 * 
 * Class which contains methods that communicates with client
 *
 */
public class RecyclingService {

	private Vector<Value> valuesInMachines = new Vector<Value>();
	private Vector<item> recyclingItems = new Vector<item>();
	private Vector<RecycleMachine> recyclingMachines = new Vector<RecycleMachine>();;
	private Vector<Engineer> engineerlog = new Vector<Engineer>();
	private Vector<transaction> transactions = new Vector<transaction>();
	
	
	Printer print;

	private Value value = null;
	private item items = null;
	private MachineDetails machine = null;
	private Engineer engineeractivity = null;
	private transaction activity = null;
	
	int cant = 0;
	int cratet = 0;
	int pbottlet = 0;
	int gbottlet = 0;
	int pbagt = 0;
	
	JTextField txtcan;
	JTextField txtcrate;
	JTextField txtpbottle;
	JTextField txtgbottle;
	JTextField txtpbag;
	
	JComboBox cmbSummary;
	JComboBox cmbUpdate;
	
	
	
	private String sessionCookie = "NotSet"+Math.random();
	
	/**
	 * Constructor
	 * @param txtMachine
	 * @param txtItem
	 * @param txtEngineer
	 * @param txtTransaction
	 * @param txtcan
	 * @param txtcrate
	 * @param txtpbottle
	 * @param txtgbottle
	 * @param txtpbag
	 * @param cmbSummary
	 * @param cmbUpdate
	 */
	public RecyclingService(JTextArea txtMachine,JTextArea txtItem,JTextArea txtEngineer,JTextArea txtTransaction,
			JTextField txtcan,JTextField txtcrate,JTextField txtpbottle,JTextField txtgbottle,JTextField txtpbag,
			JComboBox cmbSummary,JComboBox cmbUpdate)
	{
		this.cmbSummary=cmbSummary;
		this.cmbUpdate = cmbUpdate;
		this.txtcan = txtcan;
		this.txtcrate = txtcrate;
		this.txtpbottle = txtpbottle;
		this.txtgbottle = txtgbottle;
		this.txtpbag = txtpbag;
		
		
		
		print = new Printer(txtMachine, txtItem, txtEngineer, txtTransaction, recyclingItems, recyclingMachines, engineerlog, transactions);
	}

	 
	
	/**
	 * method that client connect with server and store that details in the vector
	 * @param ip
	 * @param name
	 * @return String sessioncookie
	 */
	public String ConnectMachine(String ip, String name) { 
			sessionCookie = "Cookie"+Math.random();
			String currentTime = DateFormat.getDateTimeInstance().format(new Date());			
			machine = new  RecycleMachine();
			machine.ip = ip;
			machine.name = name;
			machine.time = currentTime;
			
			recyclingMachines.addElement((RecycleMachine) machine);
			print.printMachines();
			Items(ip,name,0,0,0,0,0);
			getIP();
			
			return sessionCookie;
						
	}
	
	
	/**
	 * method that item count are stored in the vector
	 * @param ip
	 * @param name
	 * @param cant
	 * @param cratet
	 * @param pbottlet
	 * @param pbagt
	 * @param gbottlet
	 */
	public void Items(String ip,String name,int cant,int cratet,int pbottlet,int pbagt,int gbottlet) {
		
		boolean find = false;
		
		for(int i = 0; i< recyclingItems.size();i++) {	
			item item = recyclingItems.get(i);
			if(item.ip.equals(ip)) {
				
				item.can += cant;
				item.crate += cratet;
				item.pbottle += pbottlet;
				item.gbottle += gbottlet;
				item.pbag += pbagt;
				
				find = true;
				break;
			}
				
		}
		
		if(!find) {
			items = new item();
			
			
			
			items.ip = ip;
			items.name = name;
			items.can = cant;
			items.crate = cratet;
			items.pbottle = pbottlet;
			items.gbottle = gbottlet;
			items.pbag = pbagt;
			
			recyclingItems.add(items);
		}
		
		print.printItems();
		
	}
	
	/**
	 * method that client sends transaction details and store it in the vector
	 * @param ip
	 * @param name
	 * @param cant
	 * @param cratet
	 * @param pbottlet
	 * @param pbagt
	 * @param gbottlet
	 * @return String
	 */
	public String Transaction(String ip,String name,int cant,int cratet,int pbottlet,int pbagt,int gbottlet) {
		
		activity = new transaction();
		String currentTime = DateFormat.getDateTimeInstance().format(new Date());
		
		
		activity.ip = ip;
		activity.name = name;
		activity.time = currentTime;
		activity.can = cant;
		activity.crate = cratet;
		activity.pbottle = pbottlet;
		activity.gbottle = gbottlet;
		activity.pbag = pbagt;
		activity.total = (cant+cratet+gbottlet+pbottlet+pbagt);
		
		transactions.add(activity);
		print.printTransaction();
		Items(ip,name,cant,cratet,pbottlet,pbagt,gbottlet);
		
		return "Transaction updated";
		
	}
	
	/**
	 * method that client sends engineers' activities to server and store it in the vector
	 * @param ip
	 * @param name
	 * @param activity
	 * @return String
	 */
	public String EngineerActivity(String ip,String name,String activity) {
		
		
		String currentTime = DateFormat.getDateTimeInstance().format(new Date());
		
		engineeractivity = new Engineer();
		engineeractivity.ip = ip;
		engineeractivity.name = name;
		engineeractivity.activity = activity;
		engineeractivity.time = currentTime;
		engineerlog.addElement(engineeractivity);
		
		print.printEngineerActivity();
		
		if(activity.equals("Reset")) {
			for(int i = 0; i< recyclingItems.size();i++) {	
				item item = recyclingItems.get(i);
				if(item.ip.equals(ip)) {
					
					item.can = 0;
					item.crate = 0;
					item.pbottle = 0;
					item.gbottle = 0;
					item.pbag = 0;
					
					break;
				}
					
			}
			print.printItems();
		}
		
		return "Activity updated";
		
	}
	
	/**
	 * method that client sends values of the item to server and store it in the vector
	 * @param ip
	 * @param can
	 * @param crate
	 * @param pbottle
	 * @param gbottle
	 * @param pbag
	 * @return String
	 */
	public String getValues(String ip,int can,int crate,int pbottle,int gbottle, int pbag ) {
		
		value = new Value();
		value.ip = ip;
		value.can = can;
		value.crate= crate;
		value.pbottle = pbottle;
		value.gbottle = gbottle;
		value.pbag = pbag;
		valuesInMachines.addElement(value);
		Values(ip);
		return "Reseaved";
	}
	
	/**
	 * method to set value in the textboxes
	 * @param ip
	 */
	public void Values(String ip) {
		for(int i = 0; i< valuesInMachines.size();i++) {	
			Value value = valuesInMachines.get(i);
			if(value.ip.equals(ip)) {
				
				txtcan.setText(""+value.can);
				txtcrate.setText(""+value.crate);
				txtpbottle.setText(""+value.pbottle);
				txtgbottle.setText(""+value.gbottle);
				txtpbag.setText(""+value.pbag);
			}
				
		}
	}
	
	/**
	 * create dataset to display pie chart
	 * @param ip
	 * @return DefaultPieDataset
	 */
	public DefaultPieDataset DataSet(String ip) {
		
		cant = 0;
		cratet= 0;
		pbottlet =0;
		gbottlet=0;
		pbagt = 0;
		
		for(int i = 0; i< recyclingItems.size();i++) {	
			item item = recyclingItems.get(i);
			if(item.ip.equals(ip)) {
				
				cant =item.can;
				cratet = item.crate;
				pbottlet = item.pbottle;
				gbottlet = item.gbottle;
				pbagt = item.pbag;
				
				break;
			}
				
		}
		
		DefaultPieDataset piedataset = new DefaultPieDataset();
		piedataset.setValue("Can",cant);
		piedataset.setValue("Crate", cratet);
		piedataset.setValue("Plastic Bottle", pbottlet);
		piedataset.setValue("Glass Bottle", gbottlet);
		piedataset.setValue("Paper Bottle", pbagt);
		
		return piedataset;
	}
	
	/**
	 * method to display ip addresses of machines in the combobox
	 */
	public void getIP(){
		String ip ="";
		for(int i = 0; i< recyclingItems.size();i++) {	
			item item = recyclingItems.get(i);
			
			if(ip.equals("")||ip.equals(item.ip)) {
				ip = item.ip;
				cmbSummary.addItem(item.ip);
				cmbUpdate.addItem(item.ip);
			}
				
		}
	}
	

	/**
	 * method to send engineers'login username to client
	 * @param ip
	 * @return String username
	 */
	public String getUserName(String ip) {
		
		return "admin";
		
	}
	
	/**
	 * method to send engineers'login password to client
	 * @param ip
	 * @return String password
	 */
	public String getPassword(String ip) {
		
		return "123";
		
	}
	
	/**
	 * Strore updated values in the vector
	 * @param ip
	 */
	public void SetValues(String ip) {
		for(int i = 0; i< valuesInMachines.size();i++) {	
			Value value = valuesInMachines.get(i);
			if(value.ip.equals(ip)) {
				if(!(txtcan.getText().equals(""))) {
					value.can = Integer.parseInt(txtcan.getText());
				}
				if(!(txtcan.getText().equals(""))) {
					value.crate = Integer.parseInt(txtcrate.getText());
				}
				if(!(txtcan.getText().equals(""))) {
					value.pbottle = Integer.parseInt(txtpbottle.getText());
				}
				if(!(txtcan.getText().equals(""))) {
					value.gbottle = Integer.parseInt(txtgbottle.getText());
				}
				if(!(txtcan.getText().equals(""))) {
					value.pbag = Integer.parseInt(txtpbag.getText());
				}
				
			}
				
		}
	}
	
	/**
	 * method to send updated values to the client
	 * @param ip
	 * @return String updated values
	 */
	public String updateValues(String ip) {
		String str = "";
		for(int i = 0; i< valuesInMachines.size();i++) 
		{	
			Value value = valuesInMachines.get(i);
			if(value.ip.equals(ip)) {
				
				str = ""+value.can+"-"+value.crate+"-"+value.pbottle+"-"+value.gbottle+"-"+value.pbag;
				
			}
			
		
		}
		return str;
	}
	
	
	
}


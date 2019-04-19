package RecyclingServer;

import java.util.Vector;
import javax.swing.JTextArea;

/**
 * 
 * @author Isuru Piumal, 1722289
 * class to print the details in textareas 
 *
 */
public class Printer {
	
	private Vector<item> recyclingItems;
	private Vector<RecycleMachine> recyclingMachines;
	private Vector<Engineer> engineerlog;
	private Vector<transaction> transactions;
	
	JTextArea txtMachine = new JTextArea();
	JTextArea txtItem = new JTextArea();
	JTextArea txtEngineer = new JTextArea();
	JTextArea txtTransaction = new JTextArea();
	
	/**
	 * Constructor
	 * @param txtMachine
	 * @param txtItem
	 * @param txtEngineer
	 * @param txtTransaction
	 * @param recyclingItems
	 * @param recyclingMachines
	 * @param engineerlog
	 * @param transactions
	 */
	public Printer(JTextArea txtMachine,JTextArea txtItem,JTextArea txtEngineer,JTextArea txtTransaction,Vector<item> recyclingItems,
			Vector<RecycleMachine> recyclingMachines,Vector<Engineer> engineerlog,Vector<transaction> transactions) {
		
		this.txtMachine = txtMachine;
		this.txtItem = txtItem;
		this.txtEngineer = txtEngineer;
		this.txtTransaction = txtTransaction;
		this.engineerlog = engineerlog;
		this.recyclingItems = recyclingItems;
		this.recyclingMachines = recyclingMachines;
		this.transactions = transactions;
		
	}
	
	/**
	 * Print connected machines
	 */
	public void printMachines() {
		
		
		String str ="";
		int number = 1;

		str = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		str = str + String.format("%1$-15s%2$-45s%3$-45s%4$-45s","   No","IP Address","Machine Name","Connected Time");
		str = str + "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		
		for(int i=0;i<recyclingMachines.size();i++) {
			
			
			RecycleMachine machine =recyclingMachines.get(i);
			str = str + String.format("%1$-15s%2$-45s%3$-45s%4$-45s","    "+number,"    "+machine.ip,"    "+machine.name,"    "+machine.time);
			str = str + System.getProperty("line.separator");
			number++;
			
		}
		txtMachine.setText(str);
	}
	
	/**
	 * print transactions
	 */
	public void printTransaction() {
			
			String str ="";
			
			str = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
			str = str + String.format("%-16s%-9s%-9s%-18s%-18s%-15s%-9s%-30s","  IP Address","Can","Crate","Plastic Bottle","Glass Bottle","Paper Bags","Total","Time");
			str = str + "\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
			
			for(int i = 0; i< transactions.size();i++) {
				
				transaction item = transactions.get(i);
				str = str + String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s",item.ip,item.can,item.crate,item.pbottle,item.gbottle,item.pbag,item.total,item.time);
				str = str + System.getProperty("line.separator");
			}
			txtTransaction.setText(str);
			
	}
	
	
	/**
	 * print items
	 */
	public void printItems() {
			
			String str ="";
			
			str = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
			str = str + String.format("%-20s%-24s%-20s%-10s%-15s%-15s%-13s","  IP Address","Machine Name"," Can","Crate","Plastic Bottle","Glass Bottle","Paper Bags");
			str = str + "\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
			
			for(int i = 0; i< recyclingItems.size();i++) {
				
				item item = recyclingItems.get(i);
				str = str + String.format("%-20s%-24s%-20s%-20s%-20s%-20s%-20s",item.ip,item.name,item.can,item.crate,item.pbottle,item.gbottle,item.pbag);
				str = str + System.getProperty("line.separator");
			}
			txtItem.setText(str);
			
	}
	
	
	/**
	 * print engineers activity
	 */
	public void printEngineerActivity() {
		
		String str ="";
		
		str= "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		str= str+String.format("%-40s%-30s%-35s%-40s","           IP Address","Machine Name","Activity","Date/Time");
		str= str+"\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		
		for(int i=0;i<engineerlog.size();i++) {
			Engineer engineer = engineerlog.get(i);
			str= str+String.format("%-35s%-30s%-35s%-40s",engineer.ip,engineer.name,engineer.activity,engineer.time);
			str = str +"\n";
		}
		txtEngineer.setText(str);
	}

}

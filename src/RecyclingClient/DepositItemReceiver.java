package RecyclingClient;

import javax.swing.JOptionPane;

import RecyclingClient.Bottle;
import RecyclingClient.Can;

/**
 * This class represents the overall system; it handles the 'return item' use case by... 
 * Can be seen as the main controller of the system. 
 * @author Isuru Piumal, 1722289
 * @theReceiptBasis  ReceiptBasis object 
 * @printer PrinterInterface object
 */
public class DepositItemReceiver {
	ReceiptBasis theReceiptBasis = null; 
	PrinterInterface printer = null;  
	int countItems = 0; 
	
	public DepositItemReceiver(PrinterInterface printer) {
		super();
		this.printer = printer;
	}
	
	/**
	 * create ReceiptBasis object
	 */
	public void createReceiptBasis() { 
		theReceiptBasis = new ReceiptBasis(); 
	}
	/**
	 * classify item according to slot number and generate receipt
	 * @param slot
	 */
	public void classifyItem(int slot) { 
		DepositItem item = null; 
		if( slot == 1 ) { 
			item = new Can(); 
		} else if( slot == 2 ) { 
			item = new Bottle(); 
		} else if ( slot == 3 ) { 
			item = new Crate(); 
		} else if (slot == 4 ) { 
			item = new PaperBag(); 
		} else if (slot == 5 ) {
			item = new GlassBottle();
		} else 
		{
			JOptionPane.showMessageDialog(null, "Invalid entry");		
		}
		if( theReceiptBasis == null ) { 
			createReceiptBasis(); 
		}
		theReceiptBasis.addItem(item);
		countItems = countItems + 1; 
	}
	/**
	 * print the receipt from the string get from the receiptBasis object's cumputesum method
	 */
	public String printReceipt() { 
		String str = theReceiptBasis.computeSum(); 
		printer.print(str); 
		theReceiptBasis = null; 
		return str;
	}

	
}

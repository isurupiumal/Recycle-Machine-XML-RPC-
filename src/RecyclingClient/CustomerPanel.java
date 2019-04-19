package RecyclingClient;

/**
 * This represents the interface of the recycling machine. The customer interacts with 
 * the recycling machine via this class. 
 * @author Isuru Piumal, 1722289
 *
 */
public class CustomerPanel {
	public CustomerPanel(PrinterInterface printer) {
		super();
		this.receiver = new DepositItemReceiver(printer); 
	}
	DepositItemReceiver receiver = null; 
	/**
	 * An item is received in a slot (one physical input in the recycling machine) and 
	 * send for further analysis to the DepositItemReceiver. 
	 * @param slot the slot where item was inserted. 
	 */
	public void itemReceived(int slot) { 
		receiver.classifyItem(slot); 
	}
	/**
	 * Request received ... 
	 */
	public String printReceipt() { 
		return receiver.printReceipt();
	}
	
}

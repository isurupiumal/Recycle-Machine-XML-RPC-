package RecyclingClient;


/**
 * This class represents output... Interface to the real world... 
 * @author Isuru Piumal, 1722289
 *
 */
public class ReceiptPrinter implements PrinterInterface {
	/**
	 * The printer prints there. 
	 * @param str input to be printed.
	 */
	public void print(String str) { 
		System.out.println(str); 
	}
}

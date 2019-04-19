package RecyclingClient;

import java.net.InetAddress;
import java.util.Vector;
import RecyclingClient.Bottle;
import RecyclingClient.Can;


/**
 * Keeps track of the items. Represents the 'database' of the recycling machine
 * @author Isuru Piumal, 1722289
 * 
 */
public class ReceiptBasis {
	private Vector<DepositItem> myItems = new Vector<DepositItem>();
	Client client = new Client();
	/**
	 * An item is inserted into the database. 
	 * @param item
	 */
	public void addItem(DepositItem item) { 
		myItems.add(item); 
		item.number = myItems.indexOf(item); 
	}
	
	
	
	InetAddress localhost = null;
    String hostname = null;
    String ip = null;
    
    
    
    
    public ReceiptBasis() {
    	
    }
	/**
	 * @receipt string to store details of receipt
	 * @sum sum of values
	 * @return String
	 * @cantot total number of cans
	 * @cratetot total number of crates
	 * @bottletot total number of plastic bottles
	 * @papertot total number of paper bags
	 * @glossbottletot total number of  glass bottles
	 * @can Can object
	 * @crate Crate object
	 * @bottle Bottle object
	 * @pbags PaperBags object
	 * @gbottle GlassBottle object
	 * 
	 * add items to receipt and for each item add total of that item and calculate sum
	 * if each item got 1 or more item it add receipt summary with value and count of item
	 */
	public String computeSum() { 
		String receipt = ""; 
		
		int sum = 0; 
		
		
		int cantot = 0;
		int cratetot = 0;
		int bottletot = 0;
		int papertot = 0;
		int glassbottletot = 0;
		
		
		Can can = new Can();
		Crate crate = new Crate();
		Bottle bottle = new Bottle();
		PaperBag pbags = new PaperBag();
		GlassBottle gbottle = new GlassBottle();
		
		receipt = receipt +"\t                   Receipt\n-----------------------------------------------------------------------------------\n ";
		for(int i=0; i < myItems.size(); i++ ) {
			DepositItem item = myItems.get(i); 
			receipt = receipt + "\t"+(item.number+1) + "  "+item.name +"\t : "; 
			if(item.name.equals("Can"))
			{
					receipt = receipt +can.getValue();
					
					cantot = cantot +1;
					sum = sum + can.getValue();
					
					
			}else if(item.name.equals("Crate"))
			{
				receipt = receipt +crate.getValue();
				
				cratetot = cratetot +1;
				sum = sum + crate.getValue();
					
			}else if(item.name.equals("Plastic Bottle"))
			{
				receipt = receipt +bottle.getValue();
				
				bottletot = bottletot +1;
				sum = sum + bottle.getValue();
				
			}else if(item.name.equals("Paper Bags"))
			{
				receipt = receipt +pbags.getValue();
				
				papertot = papertot +1;
				sum = sum + pbags.getValue();
				
			}else if(item.name.equals("Glass Bottle"))
			{
				receipt = receipt +gbottle.getValue();
				
				glassbottletot = glassbottletot +1;
				sum = sum + gbottle.getValue();
					
			}
			receipt = receipt + System.getProperty("line.separator"); 
		}
		receipt = receipt +" \n\t                Summary\n-----------------------------------------------------------------------------------\n  Item\t\tAmount\tValue";
		receipt = receipt +"\n-----------------------------------------------------------------------------------";
		if(bottletot>0)
		{
			receipt = receipt +"\n|   Bottle\t\t"+bottletot+"\t " +(bottletot*bottle.getValue());
		}
		if(cantot>0)
		{
			receipt = receipt +"\n|   Can\t\t"+cantot+"\t " +(cantot*can.getValue());
		}
		if(cratetot>0)
		{
			receipt = receipt +"\n|   Crate\t\t"+cratetot+"\t " +(cratetot*crate.getValue());
		}
		if(papertot>0)
		{
			receipt = receipt +"\n|   Paper Bags\t\t"+papertot+"\t " +(papertot*pbags.getValue());
		}
		if(glassbottletot>0)
		{
			receipt = receipt +"\n|   Glass Bottle\t\t"+glassbottletot+"\t " +(glassbottletot*gbottle.getValue());
		}
		
		receipt = receipt +"\n-----------------------------------------------------------------------------------\n "+ "\tTotal\t\t "+sum ; 
		
		receipt = receipt +"\n-----------------------------------------------------------------------------------";
		
		
		client.Transaction(cantot, cratetot, bottletot, glassbottletot, papertot);
		client.setValue();
		
		return receipt; 
	}
}

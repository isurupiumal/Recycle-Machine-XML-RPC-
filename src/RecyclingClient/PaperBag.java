package RecyclingClient;

/**
 * 
 * @author Isuru Piumal, 1722289
 *PaperBag sub class which is inherited to DepositItem
 *Represents a PaperBag that is inserted into the Recycling Machine and then processed further. 
 */
public class PaperBag extends DepositItem {
	/**
	 * Initialize name, value, weight and size of the plastic bottle
	 */
	public PaperBag() { 
		name = "Paper Bags";
		value = 9;
		weight = 10;
		size = 8;
	}
	
	/**
	 * @return String
	 * getter method to get the name of item
	 */
	public String getName()
	{
		return name;
		
	}
	
	/**
	 * @return integer
	 * getter method to get the value of item
	 */
	public int getValue()
	{
		return value;
		
	}
	
	/**
	 * @return integer
	 * getter method to get the weight of item
	 */
	public int getWeight()
	{
		return weight;
		
	}
	
	/**
	 * @return integer
	 * getter method to get the size of item
	 */
	public int getSize()
	{
		return size;
		
	}
	
	/**
	 * setter method to set the value of the item
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}
}

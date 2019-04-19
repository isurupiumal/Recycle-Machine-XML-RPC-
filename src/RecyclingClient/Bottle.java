package RecyclingClient;


/**
 * Represents a bottle that is inserted into the Recycling Machine and then processed further. 
 * @author Isuru Piumal, 1722289
 *Bottle sub class which is inherited to DepositItem
 */
public class Bottle extends DepositItem {
	
	public Bottle() { 
		name = "Plastic Bottle";
		value = 18;
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

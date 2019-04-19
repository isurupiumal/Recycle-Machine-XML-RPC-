package RecyclingClient;

import java.awt.Color;
import javax.swing.*;

/**
 * 
 * @author Isuru Piumal, 1722289
 * /
 * Displays text in a frame.
 *
 */
public class Display extends JFrame implements PrinterInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8505887234618184162L;
	private JTextArea outputWindow; 
	
	/**
	 * when constructed the display will be directly visible. 
	 */	
	public Display() {
		super();
		setSize(350, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);			
		outputWindow = new JTextArea();
		outputWindow.setForeground(Color.BLACK);
		getContentPane().add(outputWindow);
		setVisible(false);
		
		}
	/**
	 * Prints the text str to the screen. Any previous text will be overwritten. 
	 * @see com.perisic.beds.PrinterInterface#print(java.lang.String)
	 */
	public void print(String str) { 
		outputWindow.setText(str); 
		outputWindow.repaint();
	}

}
package RecyclingClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;
import javax.swing.JOptionPane;
import org.apache.xmlrpc.XmlRpcClient;

/**
 * Class to communicate with the server
 * @author Isuru Piumal, 1722289
 * 
 * 
 */
public class Client {

	InetAddress localhost = null;
    String hostname = null;
    String ip = null;
    String ServerURL = null;
    
    Can ca = new Can();
	Crate cra = new Crate();
	Bottle bottle = new Bottle();
	PaperBag pbag = new PaperBag();
	GlassBottle glbottle = new GlassBottle();
    
    
	/**
	 * Constructor
	 */
    public Client() {
    	
    	getipaddress();
    }
    
    /**
     * Method wich creates serverurl
     * @param ServerIP
     * @param ServerPort
     */
    public void serveraddress(String ServerIP,String ServerPort) {

    	
		if(ServerIP.equals("")) {
			if(ServerPort.equals("")) {
				ServerURL = "http://localhost:81/RPC2";
			}else {
				ServerURL = "http://localhost:"+ServerPort+"/RPC2";
			}
			
		}else {
			if(ServerPort.equals("")) {
				ServerURL = "http://"+ServerIP+":81/RPC2";
			}else {
				ServerURL = "http://"+ServerIP+":"+ServerPort+"/RPC2";
			}
		}
	}
    
    /**
     * get machine's ip address name
     */
    public void getipaddress(){
	    try {
	    	localhost = InetAddress.getLocalHost();
	        hostname = localhost.getHostName();
	        ip = localhost.getHostAddress();
	        
	
	    } catch (UnknownHostException err) {
	
	        err.printStackTrace();
	    }
    }
	
    /**
     * Connect with the server
     */
    public void connectServer() {
    	try {
			Vector parameters = new Vector();
			parameters.add(ip); 
			parameters.add(hostname); 
			XmlRpcClient server;
			server = new XmlRpcClient(ServerURL);
			server.execute("recyclingServer.ConnectMachine", parameters );
			
		}catch(Exception err) {
			JOptionPane.showMessageDialog(null,"Unable to connect the server");
		}
    }
    
    /**
     * Method to communicate engineer activity
     * @param activity
     */
    public void engineerActivity(String activity) {
    	
    	try {
			Vector parameters = new Vector();
			parameters.add(ip); 
			parameters.add(hostname); 
			parameters.add(activity);
			XmlRpcClient server;
			server = new XmlRpcClient(ServerURL);
			server.execute("recyclingServer.EngineerActivity", parameters );
			
		}catch(Exception err) {
			JOptionPane.showMessageDialog(null,"Can not communicate with server");
		}
    	
    }
    
    /**
     * 
     *  Method to communicate transactions
     * @param cantot
     * @param cratetot
     * @param bottletot
     * @param glassbottletot
     * @param papertot
     */
    public void Transaction(int cantot,int cratetot,int bottletot,int glassbottletot,int papertot)
    {
    	try {
			Vector parameters = new Vector();
			parameters.add(ip); 
			parameters.add(hostname); 
			parameters.add(cantot);
			parameters.add(cratetot);
			parameters.add(bottletot);
			parameters.add(papertot);
			parameters.add(glassbottletot);
			XmlRpcClient server;
			server = new XmlRpcClient("http://localhost:81/RPC2");
			server.execute("recyclingServer.Transaction",parameters);
			
		}catch(Exception err) {
			JOptionPane.showMessageDialog(null,"Can not communicate with server");
		}
    }
    
    /**
     *  Method to send values of the items
     */
    public void getValue() {
    	try {
			Vector parameters = new Vector();
			parameters.add(ip); 
			parameters.add(ca.value); 
			parameters.add(cra.value);
			parameters.add(bottle.value);
			parameters.add(glbottle.value);
			parameters.add(pbag.value);
			XmlRpcClient server;
			server = new XmlRpcClient(ServerURL);
			server.execute("recyclingServer.getValues",parameters);
			
		}catch(Exception err) {
			JOptionPane.showMessageDialog(null,err/*"Can not communicate with server"*/);
		}
    }
    
    /**
     *  Method to g=receive update values of the items
     */
    public void setValue() {
	   
	   try {
		   Vector parameters = new Vector();
			parameters.add(ip); 
			XmlRpcClient server;
			server = new XmlRpcClient("http://localhost:81/RPC2");
			Object obj = server.execute("recyclingServer.updateValues", parameters );
			String str = (String) obj;
			
			ca.setValue(Integer.parseInt(str.split("-")[0]));
			cra.setValue(Integer.parseInt(str.split("-")[1]));
			bottle.setValue(Integer.parseInt(str.split("-")[2]));
			glbottle.setValue(Integer.parseInt(str.split("-")[3]));
			pbag.setValue(Integer.parseInt(str.split("-")[4]));
			
	   }catch(Exception err) {
			JOptionPane.showMessageDialog(null,err/*"Can not communicate with server"*/);
		}
   }
   
 
   /**
    *  Method to get engineers' username
    * @return String
    */
   public String getUserName() {
	   String username = "";
	   try {
		   	Vector parameters = new Vector();
			parameters.add(ip); 
			XmlRpcClient server;
			server = new XmlRpcClient(ServerURL);
			Object obj = server.execute("recyclingServer.getUserName", parameters );
			username =(String) obj;
			
	   }catch(Exception err) {
			JOptionPane.showMessageDialog(null,err/*"Can not communicate with server"*/);
		}
	   return username;
   }
   
   /**
    * Method to get engineers' password
    * @return String
    */
   public String getPassword() {
	   String password = "";
	   try {
		   	Vector parameters = new Vector();
			parameters.add(ip); 
			XmlRpcClient server;
			server = new XmlRpcClient(ServerURL);
			Object obj = server.execute("recyclingServer.getPassword", parameters );
			password =(String) obj;
			
	   }catch(Exception err) {
			JOptionPane.showMessageDialog(null,err/*"Can not communicate with server"*/);
		}
	   return password;
   }

	
    
    
}
	


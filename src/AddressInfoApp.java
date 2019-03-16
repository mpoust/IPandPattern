/* Name: Michael Poust
 * Date: 3/28/16
 * Purpose: This simple GUI allows the user to enter an IP address.  
 * If it is a valid IP address, it will then return the class, whether it is private or not, and the 
 * binary equivalent.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddressInfoApp extends JFrame{
	private final int WINDOW_WIDTH = 300;	// Window width in pixels 
	private final int WINDOW_HEIGHT = 300; 	// Window height in pixels
	private Pattern p;						// For determining if the IP address is really an IP address
	private Matcher m;
	private String addressClass; // Used to store what is returned by each method - Modified to String to store special address cases
	private boolean isAddressPrivate;
	private String addressBinary;
	private String subnetMask;	// added information - subnet mask
	
	private JLabel ipLabel;
	private JTextField ipTextField;
	private JButton submitButton;
	private JLabel infoLabel;
	private JLabel classLabel;
	private JTextField classTextField;
	private JLabel privateLabel;
	private JCheckBox privateCheckBox;
	private JLabel subnetMaskLabel;		 // added information - subnet mask
	private JTextField subnetTextField;  // added information - subnet mask
	private JLabel binaryLabel;
	private JTextField binaryTextField;
	
	// Do not change the following line
	private String ip;
	
	public AddressInfoApp(){
		super("IP Address Info");		// Sets title of the window
		
		setLayout(new GridLayout(7,2));	// Uses a grid layout, 6 rows with 2 columns
		
		// Setup pattern for valid IP Address
		p = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]).){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
		
		ipLabel = new JLabel("IP Address");
		ipTextField = new JTextField();
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {	// Is invoked when the user clicks the button to get the information
				ip = ipTextField.getText();
				m = p.matcher(ip);
				
				if(!m.find()){ 			// Not a valid IP address
					Frame frame = (JFrame) JFrame.getFrames()[0];
					ipTextField.setBackground(Color.red); // highlight ipTextField red to indicate error
					JOptionPane.showMessageDialog(frame, "Not a valid IP Address -- Please enter a valid IP address.");	
				}
				else{	// Is a valid IP address
					ipTextField.setBackground(Color.white); // change background to white if valid address is entered
					ip = ipTextField.getText();
					addressClass = getAddressClass();
					isAddressPrivate = getPrivate();
					subnetMask = getSubnet();  // added information - subnet mask
					addressBinary = getBinary();
					
					classTextField.setText(""+addressClass);
					privateCheckBox.setSelected(isAddressPrivate);
					subnetTextField.setText(subnetMask); // added information - subnet mask
					binaryTextField.setText(addressBinary);
				}
			}
			
		});
		
		infoLabel = new JLabel("Info");
		infoLabel.setFont(new Font("Arial", Font.PLAIN, 24));
		classLabel = new JLabel("Class");
		classTextField = new JTextField();
		privateLabel = new JLabel("Private");
		privateCheckBox = new JCheckBox();
		subnetMaskLabel = new JLabel("Subnet Mask"); // added information - subnet mask
		subnetTextField = new JTextField();      // added information - subnet mask
		binaryLabel = new JLabel("Binary");
		binaryTextField = new JTextField();
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT); 			// Sets the window size
		add(ipLabel);
		add(ipTextField);
		add(new JLabel(""));
		add(submitButton);
		add(infoLabel);
		add(new JLabel(""));
		add(classLabel);
		add(classTextField);
		add(privateLabel);
		add(privateCheckBox);
		add(subnetMaskLabel);	// added information - subnet mask
		add(subnetTextField);	// added information - subnet mask
		add(binaryLabel);
		add(binaryTextField);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Specify what happens when the close button is clicked. 
		setVisible(true);								// Display the window
	}
	
	/* The following method is a setter that I used solely for writing my JUnit tests.	 */
	public void setIP(String ip){
		this.ip = ip;
	}

	/*
	 * Method to take the entered ip address and return it as
	 * an array of integers to be used in later methods.
	 */
	public int[] returnTokens(){
		StringTokenizer token = new StringTokenizer(ip, ".");
		int numToken = token.countTokens();
		String[] tokens = {" ", " ", " ", " "}; // empty octets
		int[] octets = {-1, -1, -1, -1};
		for(int i = 0; i<numToken; i++){
			tokens[i] = token.nextToken();
			octets[i] = Integer.parseInt(tokens[i]);
		}
		return octets;
	}
	
	/* 
	 * This method determines the address class (see http://www.vlsm-calc.net/ipclasses.php)
	 * It then returns the appropriate letter (A - E) in uppercase.
	 */
	public String getAddressClass(){
		String ipClass = "X"; // String(instead of char to store special address cases) to assign the address class in uppercase
		int[] octets = returnTokens(); // integer array of ip address octets
		if(octets[0] < 128){ // A range: 1.0.0.0  -  127.255.255.255
			ipClass = "A";
			if(octets[0] == 127) // Special Address - Loopback range: 127.0.0.0 - 127.255.255.255
				ipClass = "A - Loopback Address";
		}
		else if(octets[0] < 192){ // B range: 127.0.0.0  -  191.255.255.255
			ipClass = "B";
			if((octets[0] == 169) && (octets[1] == 254)) // Special Address - APIPA range: 169.254.0.0 - 169.254.255.255s
				ipClass = "B - APIPA Address";
		}
		else if(octets[0] < 224){ // C range: 192.0.0.0  -  223.255.255.255
			ipClass = "C";
		}
		else if(octets[0] < 240){ // D range: 224.0.0.0  -  239.255.255.255
			ipClass = "D";
		}
		else{ // E range:  240.0.0.0  -  255.255.255.255
			ipClass = "E";
		}
		return ipClass; //should be in uppercase!
	}
	
	/* 
	 * If the IP address is a private address, it should return true, otherwise it will return false.
	 */
	public boolean getPrivate(){
		boolean result = false; // true if private, false if public
		int[] octets = returnTokens(); // integer array of ip address octets
		 // A private range: 10.0.0.0  -  10.255.255.255
		if(octets[0] == 10){
			result = true;
		} 
		// B private range: 172.16.0.0 - 172.31.255.255
		else if((octets[0] == 172) && ((octets[1] >= 16) && (octets[1] <= 31))){
			result = true;
		} 
		// C private range: 192.168.0.0 - 192.168.255.255
		else if ((octets[0] == 192) && (octets[1] == 168)){
			result = true;
		}
		return result;
	}
	
	// added information - subnet mask
	public String getSubnet(){
		return "0.0.0.0";
	}
	
	/* 
	 * This method takes the IP address and creates a binary equivalent of that IP address.
	 */
	public String getBinary(){
		int[] octets = returnTokens(); // integer array of ip address octets
		int n = -1; // value used for each octet's conversion to binary
		final int zero = 0; // value used to append leading zeros for binary octet shorter than 8
		StringBuilder binary = new StringBuilder(); // StringBuilder to store binary conversions
		
		// iterate through ip address one octet at a time
		for(int i = 0; i < octets.length; i++){ 
			StringBuilder str = new StringBuilder(); // temporary StringBuilder for each octet
			n = octets[i]; // get value of current octet
			while(n > 0){ 
				int rem = n % 2;
				str.append(rem);
				n = n / 2;
			}
			
			// append additional zeros if the length is shorter than 8
			for(int z = str.length(); z < 8; z++){
				str.append(zero);
			}
			// reverse the temporary StringBuilder and append to binary StringBuilder
			binary.append(str.reverse());
			
			// append period octet separator for first three octets
			if(i!=3)
				binary.append('.');
		}
		return binary.toString(); // Return address in this format, with all leading zeros (e.g. don't return 1 as "1" but "00000001")
	}
	
	public static void main(String[] args){
		AddressInfoApp gui = new AddressInfoApp();
	}

}

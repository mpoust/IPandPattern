/* Name: Michael Poust
 * Date: 3/28/16
 * Purpose: This simple GUI allows the user to enter two strings: a search string 
 * and a string of data to be searched.  It will then provide the number of matches.
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PatternMatcher extends JFrame{
	
	private final int WINDOW_WIDTH = 350;	// Window width in pixels 
	private final int WINDOW_HEIGHT = 250; 	// Window height in pixels
	private JPanel findPanel;				// Creating components needed
	private JPanel inputPanel;
	private JButton submitButton;
	private JLabel findLabel;
	private JTextField findTextField;
	private JLabel inputLabel;
	private JTextArea inputTextArea;
	private String defaultMsg = "Enter text to search here!";
	
	public PatternMatcher(){
		super("Find My Pattern");		// Sets title of the window
		
		findPanel = new JPanel();		// Creating search panel
		findPanel.setLayout(new GridLayout(1,2));
		findLabel = new JLabel("Enter Pattern to Find");
		findTextField = new JTextField();
		findPanel.add(findLabel);		// Add components to panel
		findPanel.add(findTextField);
		
		inputTextArea = new JTextArea(defaultMsg);	// Setting up larger text area
		inputTextArea.setLineWrap(true);
		inputTextArea.setWrapStyleWord(true);
		inputTextArea.addFocusListener( new FocusListener() {

			        @Override
			        public void focusLost(FocusEvent arg0) {
			        }
			        
			        // Removes default, placeholder message from text area
			        @Override
			        public void focusGained(FocusEvent arg0) {
			            if (inputTextArea.getText().equals(defaultMsg))
			            	inputTextArea.setText("");
			        }
		});
		
		submitButton = new JButton("Search");
		submitButton.addActionListener(new ActionListener(){

			// Is invoked when the user click the button to find the number of matches
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int numOfMatches = getMatches();
				int numOfWords = getNumOfWords();
				JFrame frame = (JFrame) JFrame.getFrames()[0];
				JOptionPane.showMessageDialog(frame, "Number of matches found: " + numOfMatches + "\n (Found " + numOfWords + " words)");			
			}
			
		});
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT); 			// Sets the window size
		setLayout(new BorderLayout());					// Apply layout for form controls
		add(findPanel, BorderLayout.NORTH);
		add(inputTextArea, BorderLayout.CENTER);
		add(submitButton, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Specify what happens when the close button is clicked. 
		setVisible(true);								// Display the window
	}
	
	/* The following methods are setters and getters that I used solely for writing my JUnit tests.	 */
	public void setInputTextArea(String text){
		inputTextArea.setText(text);
	}
	
	public String getInputTextArea(){
		return inputTextArea.getText();
	}
	
	public String getFindTextField() {
		return findTextField.getText();
	}

	public void setFindTextField(String text) {
		findTextField.setText(text);
	}

	/* This method gets both strings entered by the user. Use the strings to 
	 * determine how many matches there are of the given pattern and then return the number as an int.
	 * You might want to refer to section 5 of http://www.vogella.com/tutorials/JavaRegularExpressions/article.html 
	 * for ideas.
	 */
	public int getMatches(){
		String patternText = findTextField.getText(); // the pattern entered by the user
		String inputText = inputTextArea.getText(); // the text to search (also entered by the user)
		
		Pattern pattern = Pattern.compile(patternText); // create the pattern from text entered by user
		Matcher matcher = pattern.matcher(inputText); // create the matcher from text entered by user
		
		int count = 0; // count number of words
		// increment count as long as the matcher finds matches
		while(matcher.find()){
			count++;
		}
		return count;
	}
	
	/* This method gets the textarea string entered by the user. Use the string to 
	 * count the number of words and then return the number as an int.
	 */
	public int getNumOfWords(){
		String inputText = inputTextArea.getText(); // the textarea text entered by the user
		StringTokenizer tokens = new StringTokenizer(inputText); // create tokens from user's inputText
		int count = tokens.countTokens(); // count number of found tokens
		return count;
	}
	
	// Creates an instance of our Frame, no need to modify this
	public static void main(String[] args){
		PatternMatcher gui = new PatternMatcher();
	}
}

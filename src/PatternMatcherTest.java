/* Purpose: Test class for Assignment 3 PatternMatcher code
 * Date: 3/9/16
 * This class contains some test classes that you can use to test your code.
 * You do not need to modify this class.
 * To run this, right-click on the file in the Package Explorer and choose to run the code as a JUnit Test.
 */
import static org.junit.Assert.*;

import org.junit.Test;

public class PatternMatcherTest {

	PatternMatcher test = new PatternMatcher();
	@Test
	public void test() {

		test.setInputTextArea("This is a test");
		test.setFindTextField("test");
		assertEquals("Must find 'test' 1 time in 'This is a test'", 1, test.getMatches());  
		assertEquals("Must find 4 words in 'This is a test'", 4, test.getNumOfWords()); 

		test.setInputTextArea("A bunch of random text to test out this simple application to see if it is working.");
		test.setFindTextField(" t[^ ]* "); 
		assertEquals("Must find 4 words that start with t in 'A bunch of random text to test out this simple application to see if it is working.'", 4, test.getMatches());  
		assertEquals("Must find 17 words in 'A bunch of random text to test out this simple application to see if it is working.'", 17, test.getNumOfWords()); 
		
		
	}
}
/* Purpose: Test class for Assignment 3 IP code
 * Date: 3/9/16
 * This class contains some test classes that you can use to test your code.
 */

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class AddressInfoAppTest {

	AddressInfoApp test = new AddressInfoApp();

	@Test
	public void testMethods() {	 
		test.setIP("8.0.0.1");
		assertEquals("Starts with '8', must be class A", 'A', test.getAddressClass());  
		test.setIP("129.0.0.1");
		assertEquals("Starts with '129', must be class B", 'B', test.getAddressClass());
		test.setIP("192.0.0.1");
		assertEquals("Starts with '192', must be class C", 'C', test.getAddressClass());
		test.setIP("225.0.0.1");
		assertEquals("Starts with '225', must be class D", 'D', test.getAddressClass());
		test.setIP("250.0.0.1");
		assertEquals("Starts with '250', must be class E", 'E', test.getAddressClass());  
	
		test.setIP("10.10.0.1");
		assertEquals("Starts with '10', must be Private", true, test.getPrivate());  
		test.setIP("172.16.0.1");
		assertEquals("Starts with '172.16', must be Private", true, test.getPrivate()); 
		test.setIP("192.168.0.1");
		assertEquals("Starts with '192.168', must be Private", true, test.getPrivate());
		test.setIP("1.10.0.1");
		assertEquals("Starts with '1', must not be Private", false, test.getPrivate());  
	
		test.setIP("10.10.0.1");
		assertEquals("10.10.0.1 binary equivalent is not correct", "00001010.00001010.00000000.00000001", test.getBinary());
		test.setIP("1.0.0.1");
		assertEquals("1.0.0.1 binary equivalent is not correct", "00000001.00000000.00000000.00000001", test.getBinary());
	}
}

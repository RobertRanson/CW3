/**
 * 
 */
package auctionhouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * @author pbj
 *
 */
public class MoneyTest {

    @Test    
    public void testAdd() {
        Money val1 = new Money("12.34");
        Money val2 = new Money("0.66");
        Money result = val1.add(val2);
        assertEquals("13.00", result.toString());
    }

    /*
     ***********************************************************************
     * BEGIN MODIFICATION AREA
     ***********************************************************************
     * Add all your JUnit tests for the Money class below.
     */
    @Test
    public void testSubtract() {
    	Money val1 = new Money("13.66");
        Money val2 = new Money("0.66");
        Money result = val1.subtract(val2);
        assertEquals("13.00", result.toString());
    }
    
    @Test
    public void testAddPercent() {
    	Money val1 = new Money("10.00");
        double percent = 50.0;
        Money result = val1.addPercent(percent);
        assertEquals("15.00",result.toString());
    }
    
    @Test
    public void testCompareTo() {
        
    	
    }
    
    @Test 
    public void testLessEqual() {
        Money val1 = new Money("9.98");
        Money val2 = new Money("10.0");
        Money val3 = new Money("10.0");
        boolean result1 = val1.lessEqual(val2);
        boolean result2 = val2.lessEqual(val3);
        boolean result3 = val2.lessEqual(val1);
        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
    	
    }
    
    @Test
    public void testEqual() {
    	Money val1 = new Money("9.99");
        Money val2 = new Money("9.99");
        Money val3 = new Money("10.0");
        boolean result = val1.equals(val2);
        assertTrue(result);
        assertFalse(val1.equals(val3));
    }

    /*
     * Put all class modifications above.
     ***********************************************************************
     * END MODIFICATION AREA
     ***********************************************************************
     */


}

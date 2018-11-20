/**
 * 
 */
package auctionhouse;

/**
 * @author pbj
 */
public class Money implements Comparable<Money> {
/**
 * The value of money
 */
    private double value;
    
    private static long getNearestPence(double pounds) {
        return Math.round(pounds * 100.0);
    }
 
    private static double normalise(double pounds) {
        return getNearestPence(pounds)/100.0;
        
    }
 
    public Money(String pounds) {
        value = normalise(Double.parseDouble(pounds));
    }
    
    private Money(double pounds) {
        value = pounds;
    }
/**
 * Adds certain money to the initial Money.  
 * 
 * @param m        the amount of money to add
 * @return         the new money after added
 */
    public Money add(Money m) {
        return new Money(value + m.value);
    }
/**
 * Subtracts certain money from the initial Money.  
 *    
 * @param m        the amount of money to subtract
 * @return         the new money after subtracted 
 */
    public Money subtract(Money m) {
        return new Money(value - m.value);
    }
/**
 * Adds money by percentage. 
 * 
 * @param percent    the percentage to add
 * @return           the new money after add percentage
 */
    public Money addPercent(double percent) {
        return new Money(normalise(value * (1 + percent/100.0)));
    }
     
    @Override
    public String toString() {
        return String.format("%.2f", value);
        
    }

/**
 * Compares with two value of money. 
 * 
 * @param m          the money used to compare 
 * @return           if m is smaller, return 1
 *                   if m is larger, return -1
 *                   if two values are equal, return 0
 */
    public int compareTo(Money m) {
        return Long.compare(getNearestPence(value),  getNearestPence(m.value)); 
    }
/**
 * Checks whether the money less or equal the given value(m). 
 *     
 * @param m          the given money used to compare
 * @return           <code>true</code> if the money is less or equal to the given money(m)
 */
    public Boolean lessEqual(Money m) {
        return compareTo(m) <= 0;
    }

/**
 * Checks whether the given object is a Money type, if it is then checks if it is equal to the instance money.  
 * 
 * @param o           the given object
 * @return            <code>true</code> if the object is a Money type and equals to the instance 
 */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Money)) return false;
        Money oM = (Money) o;
        return compareTo(oM) == 0;       
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(getNearestPence(value));
    }
      

}

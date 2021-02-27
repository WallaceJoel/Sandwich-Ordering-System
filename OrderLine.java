package proj4;

import java.text.DecimalFormat;

/**
 * This class holds a line of the order.
 *
 * @author Joel Wallace, Nicholas McConnell jtw91 ncm78
 */

public class OrderLine {
   private int lineNumber;
   private Sandwich sandwich;
   private double price;

   public OrderLine(int number, Sandwich sandwich) {
      lineNumber = number;
      this.sandwich = sandwich;
      price = sandwich.price();
   }

   /**
    * converts double to string in currency format
    * 
    * @param d a double the price
    * @return a string of the price
    */
   private String currencyFormat(Double d) {
      DecimalFormat format = new DecimalFormat("#,###,##0.00");
      return format.format(d);
   }

   /**
    * Creates a duplicate of sandwich with a new line number
    */
   public OrderLine makeDuplicate() {
      return new OrderLine(Order.lineNumber++, this.sandwich);
   }

   /**
    * Getter method which returns the price.
    *
    * @return the price
    */
   public double getPrice() {
      return price;
   }

   /**
    * for when an orderline is removed from order, causes the orderlines behind to
    * move up one; decreases the linenumber by 1;
    */
   public void moveUp() {
      this.lineNumber--;
   }

   @Override
   /**
    * Gives string representation of the order line.
    *
    * @return the String representation of the order line
    */
   public String toString() {
      return "#" + lineNumber + " - " + sandwich.toString() + " - $" + currencyFormat(price);
   }

   /**
    * Tests if this order line is equal to another one.
    *
    * @param obj, another object for the testing
    * @return boolean of whether it is equal
    */
   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof OrderLine))
         return false;
      if (((OrderLine) obj).lineNumber != lineNumber)
         return false;
      if (!((OrderLine) obj).sandwich.equals(sandwich))
         return false;
      return true;
   }
}

package proj4;

import java.util.ArrayList;

/**
 * This class holds an order, which can have various order lines.
 *
 * @author Joel Wallace, Nicholas McConnell jtw91 ncm78
 */

public class Order implements Customizable {
   public static int lineNumber;
   private ArrayList<OrderLine> orderlines;

   public Order() {
      orderlines = new ArrayList<OrderLine>();
   }

   /**
    * Adds the order line to the order.
    *
    * @return boolean of whether it was successful
    */
   @Override
   public boolean add(Object obj) {
      if (obj instanceof OrderLine) {
         orderlines.add((OrderLine) obj);
         return true;
      } else
         return false;
   }

   /**
    * Get the list of orderlines
    * 
    * @return orderlines
    */
   public ArrayList<OrderLine> getList() {
      return orderlines;
   }

   /**
    * Removes the order line form the order.
    *
    * @return boolean of whether it was successful
    */
   @Override
   public boolean remove(Object obj) {
      int index = orderlines.indexOf(obj);
      if (index >= 0) {
         orderlines.remove(index);
      } else
         return false;
      for (int i = index; i < orderlines.size(); i++) {
         orderlines.get(i).moveUp();
      }
      lineNumber--;

      return true;

   }

   /**
    * get the combined price of all orderlines
    * 
    * @return double the cost
    */
   public double totalPrice() {
      double total = 0.0;
      for (OrderLine o : orderlines) {
         total += o.getPrice();
      }
      return total;
   }

   /**
    * Gets rid of all orderlines and sets the linenumber back to zero;
    */
   public void clearOrders() {
      this.orderlines.clear();
      lineNumber = 0;
   }
}

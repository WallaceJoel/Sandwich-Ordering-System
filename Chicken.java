package proj4;

/**
 * This is the class for a chicken sandwich.
 *
 * @author Joel Wallace, Nicholas McConnell jtw91 ncm78
 */

public class Chicken extends Sandwich {

   static final double PRICE = 8.99;

   /**
    * Returns the price of the chicken sandwich. It's $8.99 for the sandwich plus
    * $1.99 for each extra ingredient.
    *
    * @return double, with the price
    */
   @Override
   public double price() {
      return PRICE + extras.size() * PER_EXTRA;
   }

   /**
    * Gives string representation of the chicken sandwich.
    *
    * @return the String representation of the chicken sandwich
    */
   @Override
   public String toString() {
      String ret = "Chicken Sandwich";
      if (extras.size() > 0) {
         ret += " with ";
         boolean gotSome = false;
         for (Extra e : extras) {
            if (gotSome)
               ret += ", ";
            ret += e.toString();
            gotSome = true;
         }
      } else
         ret = "Plain " + ret;
      return ret;
   }

}

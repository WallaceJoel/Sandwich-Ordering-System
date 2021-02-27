package proj4;

/**
 * This is the class for a beef sandwich.
 *
 * @author Joel Wallace, Nicholas McConnell jtw91 ncm78
 */

public class Beef extends Sandwich {

   static final double PRICE = 10.99;

   /**
    * Returns the price of the beef sandwich. It's $10.99 for the sandwich plus
    * $1.99 for each extra ingredient.
    *
    * @return double, with the price
    */
   @Override
   public double price() {
      return PRICE + extras.size() * PER_EXTRA;
   }

   /**
    * Gives string representation of the beef sandwich.
    *
    * @return the String representation of the beef sandwich
    */
   @Override
   public String toString() {
      String ret = "Beef Sandwich";
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

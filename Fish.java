package proj4;

/**
 * This is the class for a fish sandwich.
 *
 * @author Joel Wallace, Nicholas McConnell jtw91 ncm78
 */

public class Fish extends Sandwich {

   static final double PRICE = 12.99;

   /**
    * Returns the price of the fish sandwich. It's $12.99 for the sandwich plus
    * $1.99 for each extra ingredient.
    *
    * @return double, with the price
    */
   @Override
   public double price() {
      return PRICE + extras.size() * PER_EXTRA;
   }

   /**
    * Returns the type of sandwich, for the sake of a method in the parent.
    *
    * @return String, the type
    */
   public String typeOf() {
      return "Fish";
   }

   /**
    * Gives string representation of the fish sandwich.
    *
    * @return the String representation of the fish sandwich
    */
   @Override
   public String toString() {
      String ret = "Fish Sandwich";
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

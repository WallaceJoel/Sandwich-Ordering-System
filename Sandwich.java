package proj4;

import java.util.ArrayList;

/**
 * This is the abstract class for a sandwich. Someone must order one of the
 * three types of sandwich.
 *
 * @author Joel Wallace, Nicholas McConnell jtw91 ncm78
 */

public abstract class Sandwich implements Customizable {
   static final int MAX_EXTRAS = 6;
   static final double PER_EXTRA = 1.99;
   protected ArrayList<Extra> extras = new ArrayList<Extra>();

   public abstract double price();

   /**
    * Gives string representation of the sandwich.
    *
    * @return the String representation of the sandwich
    */
   @Override
   public String toString() {
      String ret = "Sandwich";
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

   /**
    * Adds an extra ingredient to the sandwich.
    *
    * @param e
    * @return boolean of whether it was successful
    */
   @Override
   public boolean add(Object e) {
      if (!(e instanceof Extra))
         return false;
      if (extras.contains(e))
         return false;
      if (extras.size() == MAX_EXTRAS)
         return false;
      extras.add((Extra) e);
      return true;
   }

   /**
    * Removes an extra ingredient from the sandwich. We also have a return value
    * depending on whether it succeeded.
    *
    * @param e
    * @return boolean of whether it was successful
    */
   @Override
   public boolean remove(Object e) {
      if (!(e instanceof Extra))
         return false;
      int index = extras.indexOf(e);
      if (index >= 0) {
         extras.remove(index);
         return true;
      } else
         return false;
   }
}

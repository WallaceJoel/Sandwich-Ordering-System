package proj4;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {

   @BeforeEach
   public void setUp() throws Exception {
      System.out.println("void setUp() throws Exception");
      TimeUnit.SECONDS.sleep(4);
   }

   @AfterEach
   public void tearDown() throws Exception {
      System.out.println("void tearDown() throws Exception");
      TimeUnit.SECONDS.sleep(1);
   }

   /**
    * For comparing doubles. If two doubles are supposed to be equal, we can't
    * expect == to flawlessly get it, due to floating point errors.
    *
    * @param r1 the first double
    * @param r2 the second double
    * @return true if their difference is within the error of tolerance.
    */
   public boolean sufEqual(double r1, double r2) {
      final double errTolerance = 0.000001;
      if (r1 - r2 > errTolerance)
         return false;
      return !(r2 - r1 > errTolerance);
   }

   @Test
   public void testIsValid() {
      Order testOrd = new Order();
      // First we test that it initializes as an empty array list
      assertEquals(testOrd.getList().size(), 0);
      assertFalse(testOrd.add(17)); // because 17 isn't an order line
      assertEquals(testOrd.getList().size(), 0);
      Chicken sChi = new Chicken();
      assertTrue(sChi.add(Extra.BACON));
      assertTrue(sChi.add(Extra.LETTUCE));
      assertTrue(sChi.add(Extra.TOMATOES));
      // We add a chicken BLT sandwich
      assertTrue(testOrd.add(new OrderLine(3, sChi)));
      assertEquals(testOrd.getList().size(), 1);
      assertTrue(testOrd.add(new OrderLine(5, new Beef())));
      assertTrue(testOrd.add(new OrderLine(7, new Fish())));
      assertEquals(testOrd.getList().size(), 3);
      // Static lineNumber should still be zero since only the order
      // *controller* would increment it.
      assertEquals(Order.lineNumber, 0);
      assertFalse(testOrd.remove("This is totally not an extra!"));
      assertFalse(testOrd.remove(new Chicken()));
      assertTrue(testOrd.remove(testOrd.getList().get(1)));
      // Since the remove method itself decrements lineNumber, the line
      // number should equal -1 now...
      assertEquals(Order.lineNumber, -1);
      // sChi is a chicken sandwich with three extras
      // so its price should be $14.96
      assertTrue(sufEqual(sChi.price(), 14.96));
      // Since this sandwich doesn't have ranch dressing, trying to
      // remove it should return false
      assertFalse(sChi.remove(Extra.RANCH));
      assertTrue(sChi.remove(Extra.TOMATOES));
      assertTrue(sufEqual(sChi.price(), 12.97));
      // Since it already has bacon
      assertFalse(sChi.add(Extra.BACON));
      assertTrue(sChi.add(Extra.JALAPENOS));
      assertTrue(sChi.add(Extra.ONIONS));
      assertTrue(sChi.add(Extra.KETCHUP));
      assertTrue(sChi.add(Extra.MUSTARD));
      // Since you can only add 6 extras, and this is a 7th
      assertFalse(sChi.add(Extra.MAYONNAISE));
      assertTrue(sufEqual(sChi.price(), 20.93));
      // We recall that the second sandwich was removed
      assertEquals(testOrd.getList().size(), 2);
      // And now to clear it
      testOrd.clearOrders();
      assertEquals(Order.lineNumber, 0);
      assertEquals(testOrd.getList().size(), 0);
   }
}

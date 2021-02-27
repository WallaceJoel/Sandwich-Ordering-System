
package proj4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class CheckoutController {
   private Stage window;
   private Stage previousWindow;

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

   private Order sandwichOrders;

   private ObservableList<OrderLine> orderList;
   @FXML
   private ListView<OrderLine> orders;

   @FXML
   private TextField finalPrice;

   public void setData(Order order, Stage stage, Stage previous) {
      sandwichOrders = order;
      orderList = FXCollections.observableArrayList(sandwichOrders.getList());
      double cost = 0;
      for (OrderLine element : orderList) {
         cost += element.getPrice();
      }
      finalPrice.setText("$" + currencyFormat(cost));
      orders.setItems(orderList);
      window = stage;
      previousWindow = previous;
   }

   @FXML
   /**
    * used for fxml file initialize method, what happens as soon as the project is
    * ran
    */
   private void initialize() {

   }

   @FXML
   /**
    * Used for back button goes back to previous window;
    */
   private void back(ActionEvent e) {
      window.close();
      previousWindow.show();
   }

   @FXML
   /**
    * Remove an order line from order
    * 
    * @param e an action event
    */
   private void remove(ActionEvent e) {
      OrderLine orderline = orders.getSelectionModel().getSelectedItem();
      orderList.remove(orderline);
      sandwichOrders.remove(orderline);
      finalPrice.setText("$" + currencyFormat(sandwichOrders.totalPrice()));
   }

   @FXML
   /**
    * add an duplicate sandwich
    * 
    * @param e an action event
    */
   private void addDuplicate(ActionEvent e) {
      OrderLine orderline = orders.getSelectionModel().getSelectedItem().makeDuplicate();
      sandwichOrders.add(orderline);
      orderList.add(orderline);
      finalPrice.setText("$" + currencyFormat(sandwichOrders.totalPrice()));
   }

   @FXML
   /**
    * clears all orders
    * 
    * @param e an action event
    */
   private void clearOrders(ActionEvent e) {
      sandwichOrders.clearOrders();
      orderList.clear();
      finalPrice.setText("$0.00");
   }

   public boolean exportFile(File file) {
      try {
         FileWriter fileWriter = new FileWriter(file, false);
         for (int i = 0; i < orderList.size(); i++) {
            fileWriter.write(orderList.get(i).toString() + "\n");
         }
         fileWriter.close();
      } catch (IOException e1) {
         return false;
      }
      return true;
   }

   @FXML
   /**
    * Saving orders to a file, exporting
    * 
    * @param e an action event
    */
   private void checkout(ActionEvent e) {
      try {
         FileChooser fileChooser = new FileChooser();
         fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));

         File exportingFile = fileChooser.showSaveDialog(null).getAbsoluteFile();
         exportFile(exportingFile);
         Alert exportSuccess = new Alert(AlertType.CONFIRMATION, "Order successfully exported", ButtonType.CLOSE);
         exportSuccess.showAndWait();
      } catch (Exception exception) {
         return;
      }
   }

}

package proj4;

import java.io.IOException;
import java.text.DecimalFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class OrderController {

   Stage window;

   public void setData(Stage stage) {
      window = stage;
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

   Sandwich sandwich;
   private Order guiOrders;
   private ObservableList<Extra> unselectedToppings;
   private ObservableList<Extra> selectedToppings = FXCollections.observableArrayList();
   @FXML
   private ComboBox<String> sandwiches;
   @FXML
   private ListView<Extra> unselected;
   @FXML
   private ListView<Extra> selected;

   @FXML
   private ListView<String> basicIngredients;

   private Popup popup;
   private Label popupLabel;

   @FXML
   /**
    * initialize method, what happens as soon as the project is ran
    */
   private void initialize() {
      // Popup
      popup = new Popup();
      popupLabel = new Label("");
      popupLabel.setStyle(" -fx-background-color: blue;");
      popupLabel.setStyle(" -fx-border-color: red;");
      popup.getContent().add(popupLabel);
      popup.setAutoHide(true);

      // regular
      guiOrders = new Order();
      Order.lineNumber = 0;

      // sandwich selection
      sandwiches.setItems(FXCollections.observableArrayList("Chicken", "Fish", "Beef"));
      sandwiches.setValue("Chicken");
      sandwich = new Chicken();
      image.setImage(new Image("chicken.jpg"));
      basicIngredients.setItems(FXCollections.observableArrayList("Fried Chicken", "Spicy Sauce", "Pickles"));

      // Toppings
      unselectedToppings = FXCollections.observableArrayList(Extra.LETTUCE, Extra.TOMATOES, Extra.BACON, Extra.ONIONS,
            Extra.KETCHUP, Extra.MUSTARD, Extra.RANCH, Extra.MAYONNAISE, Extra.JALAPENOS, Extra.MUSHROOMS);
      unselected.setItems(unselectedToppings);
      selected.setItems(selectedToppings);
   }

   @FXML
   private TextField price;
   @FXML
   private ImageView image;

   @FXML
   /**
    * What happens when a sandwich is selected basic ingredients is changed and
    * starting price is changed.
    * 
    * This also does the job of the clear button
    * 
    * @param e
    */
   private void sandwichSelection(ActionEvent e) {
      if (sandwiches.getValue().equals("Beef")) {
         basicIngredients.setItems(FXCollections.observableArrayList("Roast Beef", "Provolone Cheese", "Mustard"));
         price.setText("$10.99");
         sandwich = new Beef();
         image.setImage(new Image("roastBeef.jpg"));
      } else if (sandwiches.getValue().equals("Fish")) {
         basicIngredients.setItems(FXCollections.observableArrayList("Grilled Snapper", "Cilantro", "Lime"));
         price.setText("$12.99");
         sandwich = new Fish();
         image.setImage(new Image("grilledSnapper.jpg"));

      } else {
         basicIngredients.setItems(FXCollections.observableArrayList("Fried Chicken", "Spicy Sauce", "Pickles"));
         price.setText("$8.99");
         sandwich = new Chicken();
         image.setImage(new Image("chicken.jpg"));
      }
      while (!selectedToppings.isEmpty()) {
         unselectedToppings.add(selectedToppings.get(0));
         selectedToppings.remove(0);
      }
   }

   @FXML
   /**
    * move a unselected topping back to the selected toppings
    * 
    * @param e an action event
    */
   private void selectTopping(ActionEvent e) {
      if (selectedToppings.size() == Sandwich.MAX_EXTRAS) {
         Alert tooMuchToppings = new Alert(AlertType.INFORMATION, "You cannot add more than 6 additional toppings!",
               ButtonType.CLOSE);
         tooMuchToppings.showAndWait();
         return;
      }
      try {
         int toppingIndex = unselected.getSelectionModel().getSelectedIndex();
         selectedToppings.add(unselectedToppings.get(toppingIndex));
         sandwich.add(unselectedToppings.get(toppingIndex));
         unselectedToppings.remove(toppingIndex);
         double sandwichPrice = Double.parseDouble(price.getText().substring(1).replaceAll(",", ""));
         sandwichPrice += Sandwich.PER_EXTRA;
         price.setText("$" + currencyFormat(sandwichPrice));
      } catch (Exception exception) {
         return;
      }
   }

   @FXML
   /**
    * move a selected topping back to the unselected toppings
    * 
    * @param e an action event
    */
   private void unselectTopping(ActionEvent e) {
      try {
         int toppingIndex = selected.getSelectionModel().getSelectedIndex();
         unselectedToppings.add(selectedToppings.get(toppingIndex));
         sandwich.remove(selectedToppings.get(toppingIndex));
         selectedToppings.remove(toppingIndex);
         double sandwichPrice = Double.parseDouble(price.getText().substring(1).replaceAll(",", ""));
         sandwichPrice -= Sandwich.PER_EXTRA;
         price.setText("$" + currencyFormat(sandwichPrice));
      } catch (Exception exception) {
         return;
      }
   }

   @FXML
   /**
    * Unselects all toppings
    * 
    * @param e an action event
    */
   private void clearToppings(ActionEvent e) {
      while (!selectedToppings.isEmpty()) {
         unselectedToppings.add(selectedToppings.get(0));
         selectedToppings.remove(0);
      }
   }

   @FXML
   /**
    * Shows all the sandwiches in the current order
    * 
    * @param e an action event
    */
   private void showOrder(ActionEvent e) throws IOException {
      if (guiOrders.getList().isEmpty()) {
         Alert noOrders = new Alert(AlertType.INFORMATION, "You haven't added a sandwich", ButtonType.CLOSE);
         noOrders.showAndWait();
         return;
      }
      Parent root;
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("proj4Checkout.fxml"));
      root = loader.load();
      CheckoutController checkout = loader.getController();
      Stage stage = new Stage();
      stage.setTitle("Checking Out the Order");
      stage.setScene(new Scene(root, 643, 536));
      checkout.setData(guiOrders, stage, window);
      stage.show();
      window.close();

   }

   @FXML
   /**
    * Add sandwich to the list of orders
    * 
    * @param e an action event
    */
   private void addToOrder(ActionEvent e) {
      OrderLine orderline = new OrderLine(Order.lineNumber++, sandwich);
      guiOrders.add(orderline);
      popupLabel.setText("Sandwich added to order");
      popup.show(window);
      sandwichSelection(e);

   }

}

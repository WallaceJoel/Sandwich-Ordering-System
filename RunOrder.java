package proj4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RunOrder extends Application {

   /**
    * The main class which runs the project.
    * 
    * @author Nicholas McConnell, Joel Wallace ncm78 jtw91
    */
   public Stage window;

   @Override
   public void start(Stage stage) throws Exception {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("proj4Order.fxml"));
      Parent root;
      root = loader.load();
      Scene scene = new Scene(root, 903, 628);
      stage.setTitle("So You Want to Buy a Sandwich?");
      stage.setScene(scene);
      window = stage;
      OrderController controller = loader.getController();
      controller.setData(stage);
      window.show();
   }

   public static void main(String[] args) {
      launch(args);
   }
}

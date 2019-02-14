/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author desire
 */
public class Main extends Application {
    
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/mainscreen/MainScreen.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Scheduler App");
        stage.show();
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {

        launch(args);
    }
    

    
}

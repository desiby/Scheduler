/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller.dashboard;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author desire
 */
public class DashboardController implements Initializable {
    
    @FXML
    private AnchorPane dashbPane;
    
    @FXML
    private Button btnLogout;
    
    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnAppointments;

    @FXML
    void btnLogout_clicked(ActionEvent event) throws IOException, SQLException {
        //back to login screen
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/mainscreen/MainScreen.fxml"));
        AnchorPane pane = loader.load();
        dashbPane.getChildren().setAll(pane);   
    }
    
    @FXML
    void btnAppointments_Clicked(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/View_Controller/appointments/Appointment.fxml"));
       AnchorPane pane = loader.load();
       dashbPane.getChildren().setAll(pane);
    }

    @FXML
    void btnCustomers_Clicked(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/View_Controller/customers/Customers.fxml"));
       AnchorPane pane = loader.load();
       dashbPane.getChildren().setAll(pane);
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}

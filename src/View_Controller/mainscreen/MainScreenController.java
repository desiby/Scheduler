package View_Controller.mainscreen;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import scheduler.Inavigation;
import scheduler.Utils;
import static scheduler.Utils.closeConnection;
import static scheduler.Utils.connectToDB;
import static scheduler.Utils.resultSet;
import static scheduler.Utils.selectSQL;

public class MainScreenController {
    
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblUsername;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label lblPassword;

    @FXML
    private TextField txtPassword;
       
       boolean found  = false;
       Locale locale = Locale.getDefault();
       ResourceBundle rb = ResourceBundle.getBundle("resources/login", locale);
       
       String fileName = "src/resources/loggin.txt";
       
       
    
    
    @FXML
    void handleButtonAction(ActionEvent event) throws SQLException, IOException {
     String username = "";
     FileWriter logWriter = new FileWriter(fileName, true);
     PrintWriter outputLog = new PrintWriter(logWriter);
        
        connectToDB();
        
        selectSQL("SELECT userName, password FROM U04FGv.user;");
        
        while(resultSet.next()){
          String fname = resultSet.getString("userName");
          String pwd = resultSet.getString("password");
          if(fname.equals(txtUsername.getText()) && pwd.equals(txtPassword.getText()))
            found = true;            
          username = txtUsername.getText();
          Utils.userSession = username;
        }
        //if user credentials NOT found in db
        if (!found){
          displayErrorMsg();
          closeConnection();
        }else{
          //otherwise success!
          displaySuccessMsg();
          
          //write to log file
          outputLog.println(username + " logged in at" + LocalDateTime.now() );
          System.out.println(username + " logged in at" + LocalDateTime.now());
          outputLog.close();
          
          // then NAVIGATE TO DASHBOARD
            Utils utils = new Utils();
            utils.navigate.navigateTo(rootPane, "/View_Controller/dashboard/Dashboard.fxml");
        }
      
    }

    @FXML
    public void initialize() throws ClassNotFoundException{
     //translate Login form in coresponding language depending on locale(en,fr,es) 
       lblUsername.setText(rb.getString("Username"));
       lblPassword.setText(rb.getString("Password"));
       btnLogin.setText(rb.getString("Login"));
    }
    //display default language success alert
    public void displaySuccessMsg(){
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(rb.getString("MsgSuccessTitle"));
      alert.setHeaderText(rb.getString("MsgHeaderSuccessText"));
      alert.setContentText(rb.getString("MsgContentSuccess"));
      alert.showAndWait();
    }
    //display default language error alert
    public void displayErrorMsg(){
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle(rb.getString("MsgErrorTitle"));
      alert.setHeaderText(rb.getString("MsgHeaderErrorText"));
      alert.setContentText(rb.getString("MsgContentError"));
      alert.showAndWait();
    }
}

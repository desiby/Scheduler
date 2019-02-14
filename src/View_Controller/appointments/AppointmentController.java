/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller.appointments;

import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import static scheduler.Utils.conn;
import static scheduler.Utils.resultSet;
import static scheduler.Utils.selectSQL;
import static scheduler.Utils.userSession;


/**
 * FXML Controller class
 *
 * @author desib
 */
public class AppointmentController implements Initializable {
    
    @FXML
    private AnchorPane appointmentPane;
    
    //buttons
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSave;
    
    @FXML
    private Button btnEdit;

    @FXML
    private Button btnClear;
    
    
    //tablecolumns
    @FXML
    private TableColumn<Appointment, LocalDate> col_start;

    @FXML
    private TableColumn<Appointment, LocalDate> col_end;

    @FXML
    private TableColumn<Appointment, LocalDate> col_createDate;
    
    @FXML
    private TableColumn<Appointment, String> col_type;

    @FXML
    private TableColumn<Appointment, LocalDate> col_lastUpdate;
    
    //tableview
     @FXML
    private TableView<Appointment> appointmentTableView;

    //datepicker
    @FXML
    private DatePicker appointmentDatePicker;
    
    //combo & choice box boxes
    @FXML
    private ComboBox<String> cbxCustomerName;
    
    @FXML
    private ComboBox<String> cbxStartTime;
    
    @FXML
    private ComboBox<String> cbxEndTime;
    
    @FXML
    private ChoiceBox<String> chbxType;
    
    

    
    @FXML
    void save_clicked(ActionEvent event) throws SQLException {
        //Values from input form initialized
        String customerName = cbxCustomerName.getValue();
        String type = chbxType.getValue();
        LocalDate appoitmentDate = appointmentDatePicker.getValue();
        String startTime = cbxStartTime.getValue();
        String endTime = cbxEndTime.getValue();
        
        int resultAppointment = 0;
        int customerId = 0;
        int userId = 0;
        
        //grab customerId for insertion
        if(customerName != null){
            selectSQL("select customerId from U04FGv.customer where customerName = "+"'"+customerName+"'"+"");
            try {
                while(resultSet.next()){
                   int id = resultSet.getInt("customerId");
                   customerId = id;
                }
            } catch (SQLException ex) {
                System.out.println("Error loading customerId from DB "+ ex);
            }
        }
        //-----grab userId for insertion
        selectSQL("select userId from U04FGv.user where userName = "+"'"+userSession+"'"+"");
           try {
                while(resultSet.next()){
                   int id = resultSet.getInt("userId");
                   userId = id;
                }
            } catch (SQLException ex) {
                System.out.println("Error loading userId from DB "+ ex);
            }
       //------Grab date and time, concatenate then convert to UTC-------------
       //define formatters
       DateTimeFormatter dateTimeF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
       DateTimeFormatter timeF = DateTimeFormatter.ofPattern("HH:mm:ss");
       DateTimeFormatter ampmTimeF = DateTimeFormatter.ofPattern("h:mm a");
       DateTimeFormatter dateF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       //grab date and time from form and parse them
       LocalTime sTime = LocalTime.parse(startTime, ampmTimeF);
       LocalTime eTime = LocalTime.parse(endTime, ampmTimeF);
       String startDateTimeStr = appoitmentDate.format(dateF)+" "+sTime.format(timeF);
       String endDatetTimeStr = appoitmentDate.format(dateF)+" "+eTime.format(timeF);
       //convert to utc
       LocalDateTime dftStartDatetime = LocalDateTime.parse(startDateTimeStr, dateTimeF);
       LocalDateTime dftEndDatetime = LocalDateTime.parse(endDatetTimeStr, dateTimeF);
       ZonedDateTime zdtStartDefault = ZonedDateTime.of(dftStartDatetime, TimeZone.getDefault().toZoneId());
       ZonedDateTime zdtEndDefault = ZonedDateTime.of(dftEndDatetime, TimeZone.getDefault().toZoneId());
       ZonedDateTime zdtStartUTC = zdtStartDefault.withZoneSameInstant(ZoneId.of("UTC"));
       ZonedDateTime zdtEndUTC = zdtEndDefault.withZoneSameInstant(ZoneId.of("UTC"));
       //utc format to go in mysql
       String utcSDateTimeStr = zdtStartUTC.format(dateTimeF);
       String utcEDateTimeStr = zdtEndUTC.format(dateTimeF);
       //--------------------------------------------------------------------------
       //insert all values into database
       PreparedStatement prepStatement = conn.prepareStatement("insert into U04FGv.appointment "
        + "(customerId, title, description, location, contact, url, start, end, createDate, "
               + "createdBy, lastUpdate, lastUpdateBy, type, userId) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
       prepStatement.setInt(1, customerId);
       prepStatement.setString(2, "N/A");
       prepStatement.setString(3, "N/A");
       prepStatement.setString(4, "N/A");
       prepStatement.setString(5, "N/A");
       prepStatement.setString(6, "N/A");
       prepStatement.setString(7, utcSDateTimeStr);
       prepStatement.setString(8, utcEDateTimeStr);
       prepStatement.setString(9, LocalDate.now().format(dateF));
       prepStatement.setString(10, userSession);
       prepStatement.setString(11, LocalDate.now().format(dateF));
       prepStatement.setString(12, userSession);
       prepStatement.setString(13, type);
       prepStatement.setInt(14, userId);
       
       resultAppointment = prepStatement.executeUpdate();
       System.out.println(resultAppointment + " row successfuly inserted");
    }

    @FXML
    void edit_clicked(ActionEvent event) {

    }

    @FXML
    void clear_clicked(ActionEvent event) {

    }

    @FXML
    void delete_click(ActionEvent event) {
    }

     @FXML
    void back_clicked(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/View_Controller/dashboard/Dashboard.fxml"));
       AnchorPane pane = loader.load();
       appointmentPane.getChildren().setAll(pane);
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
      public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> customerNameList = FXCollections.observableArrayList();
        ObservableList<String> type = FXCollections.observableArrayList("A", "B", "C", "D");
        ObservableList<String> startTimeList = FXCollections.observableArrayList("9:00 AM","10:00 AM","11:00 AM","12:00 PM","1:00 PM","2:00 PM","3:00 PM","4:00 PM");
        ObservableList<String> endTimeList = FXCollections.observableArrayList("10:00 AM","11:00 AM","12:00 PM","1:00 PM","2:00 PM","3:00 PM","4:00 PM","5:00 PM");

       //No Scheduling outside of business days
        appointmentDatePicker.setValue(LocalDate.now());
        appointmentDatePicker.setOnAction(new EventHandler() {
                @Override
                public void handle(Event t) {
                   LocalDate date = appointmentDatePicker.getValue();
                   DayOfWeek day = date.getDayOfWeek();
                        if(date.isBefore(LocalDate.now()) || day.equals(day.SATURDAY) || day.equals(day.SUNDAY)){ 
                            btnSave.setDisable(true);
                           System.err.println("no weekends or date before today please!");
                           //TODO dialogs
                        }else{
                          btnSave.setDisable(false);
                        }
                }
        });
         
        //Loadind Customer from database
        selectSQL("select customerName from U04FGv.customer");
        try{
            while(resultSet.next()){
              String name = resultSet.getString("customerName");
              customerNameList.add(name);
            }
        } catch (SQLException ex) {
           System.out.println("Error! cannot get load customer " + ex);
        }
        cbxCustomerName.setItems(customerNameList);
        cbxCustomerName.setValue(customerNameList.get(0));
        //loading boxes
        chbxType.setItems(type);
        chbxType.setValue(type.get(0));
        cbxStartTime.setItems(startTimeList);
        cbxStartTime.setValue(startTimeList.get(0));
        cbxEndTime.setItems(endTimeList);
        cbxEndTime.setValue(endTimeList.get(0));
        
        
        
    } 
          
}

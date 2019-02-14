package View_Controller.customers;

import Model.CustomerDetails;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import scheduler.Utils;
import static scheduler.Utils.insertOrUpdateOrDeleteSQL;
import static scheduler.Utils.resultSet;
import static scheduler.Utils.selectSQL;
import static scheduler.Utils.userSession;

public class CustomersController implements Initializable{

    @FXML
    private AnchorPane customerPane;
   
//tableview and tcol declaration
@FXML
private TableView<CustomerDetails> customerDataTableView;
@FXML
private TableColumn<CustomerDetails, String> col_addr;
@FXML
private TableColumn<CustomerDetails, String> col_postalCode;
@FXML
private TableColumn<CustomerDetails, LocalDate> col_createDate;
@FXML
private TableColumn<CustomerDetails, LocalDate> col_lastUpdate;
@FXML
private TableColumn<CustomerDetails, Integer> col_active;
@FXML
private TableColumn<CustomerDetails, Integer> col_custId;
@FXML
private TableColumn<CustomerDetails, String> col_city;
@FXML
private TableColumn<CustomerDetails, String> col_phone;
@FXML
private TableColumn<CustomerDetails, String> col_addr2;
@FXML
private TableColumn<CustomerDetails, String> col_name;

//textfields
   @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtAddressId;


    @FXML
    private TextField txtLastUpdateBy; 

    @FXML
    private TextField txtCreateBy;
    
    @FXML
    private TextField txtCreateDate;
    
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAddress2;

    @FXML
    private TextField txtPostalCode;

    @FXML
    private TextField txtPhoneNumber;
    
    //choice boxes
    @FXML
    private ChoiceBox<String> cbxCity;

    @FXML
    private ComboBox<String> cbxActive;

   //buttons    
    @FXML
    private Button btnSaveCustomer;

    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Button btnClearCustomer;

    @FXML
    private Button btnBack;
    

    private ObservableList<CustomerDetails> customerList = FXCollections.observableArrayList();
    
    CustomerDetails customer = null;
    
    int selectedCustomerId = 0;
    int selectedAddressId = 0;
    
    
  /**
   * Back to Dashboard method
   * @param event
   * @throws IOException 
   */  
    @FXML
    void btnBack_clicked(ActionEvent event) throws IOException, SQLException {
       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/dashboard/Dashboard.fxml"));
        AnchorPane pane = loader.load();
        customerPane.getChildren().setAll(pane);
    }
    
    /**
     * clear form
     * @param event 
     */
    @FXML
    void btnClearCustomer_clicked(ActionEvent event) {
        clearForm();
    }
    
    /**
     * Delete customer
     * @param event 
     */
    @FXML
    void btnDeleteCustomer_clicked(ActionEvent event) {
     int selAddrId = 0;
     customer = customerDataTableView.getSelectionModel().getSelectedItem();
     if (customer !=null){
        selectSQL("SELECT addressId from U04FGv.customer WHERE customerName = "+"'"+customer.getCustomerName()+"'"+"" ); 
            try {
                while(resultSet.next()){
                  int addressId = resultSet.getInt("addressId");
                  selAddrId = addressId;
                }
            } catch (SQLException ex) {
                System.out.println("Error! cannot get addressId of selected customer "+ ex);
            }
        
        insertOrUpdateOrDeleteSQL("delete from U04FGv.customer where customerName = " + "'"+customer.getCustomerName()+"'"+""); 
        insertOrUpdateOrDeleteSQL("delete from U04FGv.address where addressId =" +selAddrId+"");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete");
        alert.setContentText("Customer details successfuly deleted!");
        alert.showAndWait(); 
     }    
    } 
    /**
     * Add/Save customer
     * @param event 
     */
    @FXML
    void btnSaveCustomer_clicked(ActionEvent event) throws SQLException{
      
      int resultCustomer;
      int resultAddress;
     
        
      String name = txtCustomerName.getText();
      String address = txtAddress.getText();
      String address2 = txtAddress2.getText();
      String postalCode = txtPostalCode.getText();
      String phone = txtPhoneNumber.getText();
      int active = Integer.parseInt(cbxActive.getValue());
      String city = cbxCity.getValue();
      
      int addrId = 0;
      int cityId = 0;
      
      if (city != null){
         selectSQL("SELECT cityId from U04FGv.city Where city = "+"'"+city+"';" );
        try {
            while(resultSet.next()){
              int id = resultSet.getInt("cityId");
              cityId = id;
            }
        } catch (SQLException ex) {
            System.out.println("Error loading cityId from DB "+ ex);
        }
      }
      
      resultAddress = insertOrUpdateOrDeleteSQL("INSERT INTO U04FGv.address "
                  + "(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) "
                  + "VALUES("
                  +"'"+address+"',"
                  +"'"+address2+"',"
                  +"'"+cityId+"',"
                  +"'"+postalCode+"',"
                  +"'"+phone+"',"
                  +"'"+LocalDate.now()+"',"
                  +"'"+userSession+"',"
                  +"'"+LocalDate.now()+"',"+"'"
                  +userSession+"')");
          
          selectSQL("select last_insert_id() as lastID;");
         
          while(resultSet.next()){
             int id = resultSet.getInt("lastID");
             addrId = id; //global variable to grab the address id
             System.out.println("id is "+ addrId);
          }
          
          
          resultCustomer = insertOrUpdateOrDeleteSQL("INSERT INTO U04FGv.customer (customerName, addressId, "
                  + "active, createDate, createdBy, lastUpdateBy) "
                  + "VALUES("
                  +"'"+name+"',"
                  +addrId+","
                  +active+","
                  +"'"+LocalDate.now()+"',"
                  +"'"+userSession+"',"
                  +"'"+userSession+"')");
                    
           System.out.println(resultCustomer+","+resultAddress+" rows modified");
           clearForm();

}
    /**
    * initialize method
    * @param location
    * @param resources 
    */ 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
  
        Tooltip editToolTip = new Tooltip();
        editToolTip.setText("Double-click on row to edit");
        customerDataTableView.setTooltip(editToolTip);
        
        ObservableList<String> cityOptions = FXCollections.observableArrayList();
        ObservableList<String> activeOptions = FXCollections.observableArrayList("0","1");
        cbxActive.setItems(activeOptions);
        
        col_name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        col_custId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        col_addr.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_addr2.setCellValueFactory(new PropertyValueFactory<>("address2"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        col_city.setCellValueFactory(new PropertyValueFactory<>("city"));
        col_postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        col_active.setCellValueFactory(new PropertyValueFactory<>("active"));
        col_createDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        col_lastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
          
        selectSQL("select customer.customerId, customer.customerName, customer.addressId, customer.active, \n" +
"	   address.addressId, address.address, address.address2, \n" +
"       city.cityId, address.cityId, city.city,  \n" +
"       address.postalCode, address.phone,customer.createDate, customer.lastUpdate\n" +
"from U04FGv.customer\n" +
"inner join U04FGv.address\n" +
"on customer.addressId = address.addressId\n" +
"inner join U04FGv.city\n" +
"on city.cityId = address.addressId;");
    
        try {
            while(resultSet.next()){
              int customerId = resultSet.getInt("CustomerId");
              String customerName = resultSet.getString("CustomerName");
              String address = resultSet.getString("address");
              String address2 = resultSet.getString("address2");
              String city = resultSet.getString("City");
              String phoneNumber = resultSet.getString("phone");
              String postalCode = resultSet.getString("postalCode");
              int active = resultSet.getInt("active");
              Date createDate = resultSet.getDate("createDate");
              Date lastUpdate = resultSet.getDate("lastUpdate");
              
              customer = new CustomerDetails(customerId,customerName,address,address2,
                      city,phoneNumber,postalCode,active,LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(createDate)),
                      LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(lastUpdate)));
              
              customerList.add(customer);
             }
        } catch (SQLException ex) {
            System.out.println("Error loading data from DB "+ ex);
        }
        
        customerDataTableView.setItems(customerList);
        
   
      //Loading cities from database inside choiceBox
        selectSQL("SELECT city from U04FGv.city");
        try{
          while(resultSet.next()){
            String city = resultSet.getString("city");
            cityOptions.add(city);
          }  
        }catch(Exception ex){
          System.out.println("Error while loading cities "+ex);
        }
        
        //set city choicebox to the first value in db
        cbxCity.setItems(cityOptions);
        cbxCity.setValue(cityOptions.get(0));    
      
        //
        //Pre-populate the form field upon double-clicking cell row for editing
        customerDataTableView.setRowFactory(tv -> {
            TableRow<CustomerDetails> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    CustomerDetails rowData = row.getItem();
                    //get id of selected customer
                    selectedCustomerId = rowData.getCustomerId();
                    btnSaveCustomer.setDisable(true);
                    txtCustomerName.setText(rowData.getCustomerName());
                    txtAddress.setText(rowData.getAddress());
                    txtAddress2.setText(rowData.getAddress2());
                    cbxCity.setValue(rowData.getCity());
                    txtPhoneNumber.setText(rowData.getPhoneNumber());
                    txtPostalCode.setText(rowData.getPostalCode());
                    cbxActive.setValue(String.valueOf(rowData.getActive()));
                    
                    System.out.println("Double click on: "+rowData.getCustomerName());
                }
            });
            return row ;
        });
    }
   
     @FXML
    void btnEditCustomer_clicked(ActionEvent event) throws SQLException {
         
         String name = txtCustomerName.getText();
         String address = txtAddress.getText();
         String address2 = txtAddress2.getText();
         String postalCode = txtPostalCode.getText();
         String phone = txtPhoneNumber.getText();
         int active = Integer.parseInt(cbxActive.getValue());
         String city = cbxCity.getValue();
         
         int cityId = 0;
         
         ///grab cityId from from fields
         if (city != null){
         selectSQL("SELECT cityId from U04FGv.city Where city = "+"'"+city+"';" );
            try {
                while(resultSet.next()){
                  int id = resultSet.getInt("cityId");
                  cityId = id;
                }
            } catch (SQLException ex) {
                System.out.println("Error loading cityId from DB <from Edit Method> "+ ex);
            }
         }
        //Grab addressId from selectedCustomer where customerId is the one from customer selected
        selectSQL("SELECT addressId from U04FGv.customer WHERE customerId = "+selectedCustomerId+";" ); 
            try {
                while(resultSet.next()){
                  selectedAddressId = resultSet.getInt("addressId");
                }
            } catch (SQLException ex) {
                System.out.println("Error! cannot get addressId of selected customer "+ ex);
            }
 
            PreparedStatement myStatement = Utils.conn.prepareStatement("update U04FGv.customer set customerName = ?, "
                    + "addressId =?, active =?,"
                    +"lastUpdate =?,lastUpdateBy = ? where customerId = ?");
            DateTimeFormatter myDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            myStatement.setString(1, name );
            myStatement.setInt(2,selectedAddressId);
            myStatement.setInt(3,active);
            myStatement.setString(4,LocalDateTime.now().format(myDate));
            myStatement.setString(5,userSession);
            myStatement.setInt(6, selectedCustomerId);
            
            System.out.println(myStatement.executeUpdate()+ " customer updated");
            
            PreparedStatement addrStatement = Utils.conn.prepareStatement("update U04FGv.address set address = ?, address2 = ?,"
                    + "cityId = ?,"+"phone = ?,"
                    + "postalCode = ?,"
                    + "lastUpdate =?, lastUpdateBy = ? where addressId = ? ");
            DateTimeFormatter addrDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            addrStatement.setString(1, address);
            addrStatement.setString(2, address2);
            addrStatement.setInt(3, cityId);
            addrStatement.setString(4, phone);
            addrStatement.setString(5, postalCode);
            addrStatement.setString(6,LocalDateTime.now().format(addrDate));
            addrStatement.setString(7, userSession);
            addrStatement.setInt(8,selectedAddressId );
            
            System.out.println(addrStatement.executeUpdate()+ " address updated");
            
            clearForm();
        
    }
    
    public void clearForm(){
      selectedCustomerId = 0;
                    btnSaveCustomer.setDisable(false);
                    txtCustomerName.setText("");
                    txtAddress.setText("");
                    txtAddress2.setText("");
                    cbxCity.setValue("");
                    txtPhoneNumber.setText("");
                    txtPostalCode.setText("");
                    cbxActive.setValue(String.valueOf(0));
    }
}

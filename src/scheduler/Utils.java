/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author desire
 */
public class Utils {
    
    //Connection String
       public static Connection conn = null;
       private  static final String DRIVER = "com.mysql.cj.jdbc.Driver"; 
       private static final String DB     = "U04FGv"; 
       private static final String URL    = "jdbc:mysql://52.206.157.109"; 
       private static final String USER   = "U04FGv"; 
       private static final String PASS = "53688223157";
       
       public static ResultSet resultSet = null;
       public static int QueryResult = 0;
       
       //keeps track of user
       public static String userSession;

       
       
       /**
        * Create a connection
     * @throws java.sql.SQLException
        */
       public static void connectToDB() throws SQLException{
         if(conn == null){
           try{
               Class.forName(DRIVER);
               conn = DriverManager.getConnection(URL,USER,PASS);
               System.out.println("Connected to database : " + DB);
           }catch(Exception ex){
               System.out.println("There was an error while conecting to DB "+ ex);
           }
         }
       }
        /**
        * Connect to database and load data from it.
        * @param query 
        */
       public static void selectSQL(String query){
           try {
                Statement statement = conn.createStatement();
                resultSet = statement.executeQuery(query);
           }catch(Exception ex) { 
                System.out.println("There was an Execption while excuting SELECT statement: "+ex.getMessage()); 
           }
         } 
       /**
        * Connect to database and execute
        * data manipulation language queries
        * @param query
        * @return 
        */
       public static int insertOrUpdateOrDeleteSQL(String query){
           try {
                Statement statement = conn.createStatement();
                QueryResult = statement.executeUpdate(query);
           }catch (Exception e) { 
                System.out.println("Error while inserting/updating or deleting data "+e.getMessage()); 
           }
           
           return QueryResult;
       }
       
       
       /**
        * Close connection to database
        * @throws SQLException 
        */
       public static void closeConnection() throws SQLException{
         if(conn != null){
             conn.close();
         }
         conn = null;
         System.out.println("Connection successfuly closed!");
       }
    
}

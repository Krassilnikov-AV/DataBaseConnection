package databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DataBaseConnection {

    Connection connection = null;
    String url = "jdbc:postgresql://localhost:5432/northwind";
    String user = "postgres";
    String password = "postgres";      // password  is wrong

     public Connection dbConnection()  {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            DriverManager.getConnection(url, user, password);
           JOptionPane.showMessageDialog(null, "Connected");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed To Connect");
        }
          return connection;
     }
     
    public static void main(String[] args) {
      DataBaseConnection db = new DataBaseConnection();
      db.dbConnection();        
    }
}

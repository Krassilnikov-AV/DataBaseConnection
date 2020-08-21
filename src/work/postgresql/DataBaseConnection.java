package work.postgresql;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
// класс создания соединения ....
public class DataBaseConnection {

    private static final String CONFIG = "src\\resources\\config.properties";
    Connection connection = null;


    private static String getUrl() {
        String url = "jdbc:postgresql://localhost:5432/raspisanie";
        System.out.println("this is the url... connected...");
        return url;
    }

    private static Properties getProperties() throws IOException {
        Properties configs = new Properties();
        try (FileInputStream file = new FileInputStream(CONFIG)) {
            configs.load(file);
        } catch (IOException ex) {
            System.out.println("we didn't get the data..." + ex);
        }
        return configs;
    }

    private static Connection getConnection() throws IOException {
     Connection connect = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = getUrl();
        Properties usnamePas = getProperties();

      String user = usnamePas.getProperty("database.user").trim();
      String password = usnamePas.getProperty("database.password").trim();

        try {
            DriverManager.getConnection(url, user, password);
            // creating a dialog box
            JOptionPane.showMessageDialog(null, "Connected");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed To Connect");
        }
        return connect;
    }

    public static void main(String[] args) throws IOException {
        DataBaseConnection db = new DataBaseConnection();
        db.getConnection();
    }
}

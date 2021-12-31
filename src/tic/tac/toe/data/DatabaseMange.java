
package tic.tac.toe.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

public class DatabaseMange {
    public static Connection connection;
    public static Connection getConn(){
        if(connection == null){ //create one object only
            try {
                DriverManager.registerDriver(new ClientDriver()); //use derby driver
                Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Alex42Phonebook", "root", "root");
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseMange.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }
    
    
    
}

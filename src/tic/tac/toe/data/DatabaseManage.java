/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;


public class DatabaseManage {
    
    public static Connection connection;
    public static Connection getConn(){
        if(connection ==null){
        try {
            DriverManager.registerDriver(new ClientDriver());
             connection=DriverManager.getConnection("jdbc:derby://localhost:1527/TicTacToeDB","root","root");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        return connection;
    }

    
   
    
}

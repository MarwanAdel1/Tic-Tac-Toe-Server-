/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Marwan Adel
 */
public class DatabaseManage {

    public static Connection CONNECTION_STATE = null;

    public static Connection getConnection() {
        if (CONNECTION_STATE == null) {
            try {
                DriverManager.registerDriver(new ClientDriver());
                CONNECTION_STATE = DriverManager.getConnection("jdbc:derby://localhost:1527/phoneBook", "root", "root");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return CONNECTION_STATE;
    }

    public int addNewPlayer(JSONObject jSONObject) {
        int id = -1;

        if (CONNECTION_STATE == null) {
            CONNECTION_STATE = getConnection();
        }

        try {
            PreparedStatement preparedStatement = CONNECTION_STATE.prepareStatement("INSERT INTO Players (Username,Password) VALUES(?,?)");

            preparedStatement.setString(1, jSONObject.getString("Username"));
            preparedStatement.setString(2, jSONObject.getString("Password"));

            int insertionOperation = preparedStatement.executeUpdate();
            if (insertionOperation != 0) {
                id = loginPlayer(jSONObject, 0);

                addNewPlayerStatus(id);
                addNewPlayerScore(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(DatabaseManage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    public int addNewPlayerStatus(int id) {
        int insertionOperation = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION_STATE.prepareStatement("INSERT INTO Player_Status (ID) VALUES(?)");

            preparedStatement.setInt(1, id);
            insertionOperation = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return insertionOperation;
    }

    public int addNewPlayerScore(int id) {
        int insertionOperation = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION_STATE.prepareStatement("INSERT INTO Player_Scores (ID) VALUES(?)");

            preparedStatement.setInt(1, id);
            insertionOperation = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return insertionOperation;
    }

    public int loginPlayer(JSONObject jSONObject, int status) {
        int id = -1;

        if (CONNECTION_STATE == null) {
            CONNECTION_STATE = getConnection();
        }

        try {
            PreparedStatement preparedStatement = CONNECTION_STATE.prepareStatement("SELECT ID from Players WHERE Username=? and Password=?");

            preparedStatement.setString(1, jSONObject.getString("Username"));
            preparedStatement.setString(2, jSONObject.getString("Password"));

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                id = result.getInt("ID");
            }

            if (id != -1) {
                updateStatus(id, status);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(DatabaseManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public int updateStatus(int id, int status) {
        int value=0;
        try {
            PreparedStatement preparedStatement = CONNECTION_STATE.prepareStatement("UPDATE Player_Status set Status=? WHERE ID=?");
            
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, id);
            
            value = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
}

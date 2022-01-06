/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import data.DatabaseManage;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Marwan Adel
 */
public class ServerRequestHandling { /// Demo

    DataInputStream dataInputStream;
    PrintStream printStream;

    public ServerRequestHandling(Socket socket) {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            while (true) {
                String messageFromClient = dataInputStream.readLine();
                requestHandling(messageFromClient);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerRequestHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void requestHandling(String messageFromClient) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(messageFromClient);//
            String header = jSONObject.getString("Header");

            if (header.equalsIgnoreCase("Database")) { /// to/from Database
                DatabaseManage databaseManage = new DatabaseManage();
                if (jSONObject.getString("SubHeader").equalsIgnoreCase("Register")) {
                    int id = databaseManage.addNewPlayer(jSONObject);

                    printStream.println(JsonConverter.convertRegisterIdMessageToJson(id));

                } else if (jSONObject.getString("SubHeader").equalsIgnoreCase("Login")) {
                    int id = databaseManage.loginPlayer(jSONObject, 1);

                    printStream.println(JsonConverter.convertLoginIdMessageToJson(id));

                }
            } else if (header.equalsIgnoreCase("Invite") || header.equalsIgnoreCase("Invite_Response")) { /// send to another client

            } else if (header.equalsIgnoreCase("Game")) {

            }
        } catch (JSONException ex) {
            Logger.getLogger(ServerRequestHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

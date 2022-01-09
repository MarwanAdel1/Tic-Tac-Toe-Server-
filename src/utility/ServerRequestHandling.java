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
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.JSONException;
import org.json.JSONObject;
import pojo.Score;

/**
 *
 * @author Marwan Adel
 */
public class ServerRequestHandling extends Thread { /// Demo

    Socket socket;
    DataInputStream dataInputStream;
    PrintStream printStream;
    String username;
    int id;

    public static Vector<ServerRequestHandling> clientData = new Vector<>();

    public ServerRequestHandling(Socket socket, Stage stage) {
        this.socket = socket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());

            start();

        } catch (IOException ex) {
            Logger.getLogger(ServerRequestHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {   //// mwgoda fel serverhandeler
            @Override
            public void handle(WindowEvent event) {
                try {

                    DatabaseManage databaseManage = new DatabaseManage();
                    databaseManage.updateAllStatus();

                    sayByeToAll();

                    dataInputStream.close();
                    printStream.close();
                    socket.close();

                    Platform.exit();
                    System.exit(0);
                } catch (IOException ex) {
                    Logger.getLogger(ServerRequestHandling.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @Override
    public void run() {
        while (true) {
            try {
                String messageFromClient = dataInputStream.readLine();
                System.out.println(messageFromClient);
                if (messageFromClient != null) {////////////////////////////////////hnstop l thread
                    requestHandling(messageFromClient);
                } else {
                    stop();
                }
            } catch (SocketException se) {
                try {
                    DatabaseManage databaseManage = new DatabaseManage();
                    databaseManage.updateStatus(id, 0);

                    clientData.remove(this);
                    dataInputStream.close();
                    printStream.close();
                    socket.close();

                    stop();

                } catch (IOException ex) {
                    Logger.getLogger(ServerRequestHandling.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerRequestHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void requestHandling(String messageFromClient) {
        try {
            JSONObject jSONObject = new JSONObject(messageFromClient);//
            String header = jSONObject.getString("Header");

            if (header.equalsIgnoreCase("Database")) { /// to/from Database
                DatabaseManage databaseManage = new DatabaseManage();
                if (jSONObject.getString("SubHeader").equalsIgnoreCase("Register")) {
                    id = databaseManage.addNewPlayer(jSONObject);

                    printStream.println(JsonConverter.convertRegisterIdMessageToJson(id));

                } else if (jSONObject.getString("SubHeader").equalsIgnoreCase("Login")) {
                    id = databaseManage.loginPlayer(jSONObject, 1);
                    if (id != -1 && id != -2) {
                        username = databaseManage.getUsername(id);

                        clientData.add(this);

                        Score score = databaseManage.fetchPlayerScore(id);

                        printStream.println(JsonConverter.convertScoreToJson(score));
                        printStream.println(JsonConverter.convertOnlineUsernameVectorToJson(username));
                        printStream.println(JsonConverter.convertLoginIdMessageToJson(id, username));
                    } else if (id == -2 || id == -1) {
                        printStream.println(JsonConverter.convertLoginIdMessageToJson(id, ""));
                    }
                } else if (jSONObject.getString("SubHeader").equalsIgnoreCase("GoOffline")) {
                    databaseManage.updateStatus(id, 0);
                    clientData.remove(this);
                }
            } else if (header.equalsIgnoreCase("Invite") || header.equalsIgnoreCase("Invite_Response")) { /// send to another client
                for (int i = 0; i < clientData.size(); i++) {
                    ServerRequestHandling serverRequestHandling = clientData.get(i);
                    if (serverRequestHandling.username.equals(jSONObject.getString("OpponentReciever"))) {
                        serverRequestHandling.printStream.println(jSONObject);
                    }
                }
            } else if (header.equalsIgnoreCase("Ready")) {
                for (int i = 0; i < clientData.size(); i++) {
                    ServerRequestHandling serverRequestHandling = clientData.get(i);
                    if (serverRequestHandling.username.equals(jSONObject.getString("ReadyReciever"))) {
                        serverRequestHandling.printStream.println(jSONObject);
                    }
                }
            } else if (header.equalsIgnoreCase("Game")) {

            }
        } catch (JSONException ex) {
            Logger.getLogger(ServerRequestHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sayByeToAll() {
        for (ServerRequestHandling serverRequestHandling : clientData) {
            serverRequestHandling.printStream.println(JsonConverter.convertSayByeToJson());
        }
    }
}

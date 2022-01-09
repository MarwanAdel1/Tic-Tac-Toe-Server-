/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.*;
import utility.ServerRequestHandling;
import static utility.ServerRequestHandling.clientData;

/**
 *
 * @author Marwan Adel
 */
public class ServerRequestsHandler extends Thread {

    private static ServerSocket serverSocket;
    private static Socket socket;
    private static InetAddress address;

    static Stage stage;

    public ServerRequestsHandler(Stage stage) {
        this.stage = stage;
        try {
            address = InetAddress.getLocalHost();
            System.out.println(address.getHostAddress());
            serverSocket = new ServerSocket(11114, 10, address);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerRequestsHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerRequestsHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        start();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    DatabaseManage databaseManage = new DatabaseManage();
                    databaseManage.updateAllStatus();

                    serverSocket.close();

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
                socket = serverSocket.accept();

                new ServerRequestHandling(socket, stage);
            } catch (IOException ex) {
                Logger.getLogger(ServerRequestsHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     * @return the address
     */
    public InetAddress getAddress() {
        return address;
    }

}

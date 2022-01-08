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
public class ServerRequestsHandler{

    private ServerSocket serverSocket;
    private Socket socket;
    private InetAddress address;

    private boolean flag = false;

    private Thread th;

    static Stage stage;

    public static ServerRequestsHandler serverRequestsHandler = null;

    private ServerRequestsHandler(Stage stage) {
        this.stage = stage;

        try {
            address = InetAddress.getLocalHost();
            System.out.println(address.getHostAddress());
        } catch (IOException ex) {
            Logger.getLogger(ServerRequestsHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    if (!flag) {
                        DatabaseManage databaseManage = new DatabaseManage();
                        databaseManage.updateAllStatus();

                        serverSocket.close();
                    }
                    Platform.exit();
                    System.exit(0);
                } catch (IOException ex) {
                    Logger.getLogger(ServerRequestHandling.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public static ServerRequestsHandler createInstance(Stage stage) {
        if (serverRequestsHandler == null) {
            serverRequestsHandler = new ServerRequestsHandler(stage);
        }
        return serverRequestsHandler;
    }

    public void startServer() {
        if (!flag) {
            try {
                serverSocket = new ServerSocket(11114, 10, address);
            } catch (IOException ex) {
                Logger.getLogger(ServerRequestsHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

            th = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            socket = serverSocket.accept();

                            new ServerRequestHandling(socket, stage);
                        } catch (IOException ex) {
                            try {
                                socket.close();
                            } catch (IOException ex1) {
                                Logger.getLogger(ServerRequestsHandler.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        }
                    }
                }
            });
            th.start();

            flag = true;
        }
    }

    public void stopServer() {
        if (flag) {
            th.suspend();
            flag = false;
            try {
                if (socket != null) {
                    socket.close();
                }
                serverSocket.close();

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

    public boolean getFlag() {
        return flag;
    }

}

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
import org.json.*;
import utility.ServerRequestHandling;

/**
 *
 * @author Marwan Adel
 */
public class ServerRequestsHandler extends Thread {

    private ServerSocket serverSocket;
    private Socket socket;
    private InetAddress address;

    public ServerRequestsHandler() {

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
    }

    @Override
    public void run() {
        
            while (true) {
                try {
                    socket = serverSocket.accept();
                    
                    new ServerRequestHandling(socket);
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

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

/**
 *
 * @author Marwan Adel
 */
public class ServerRequestsHandler extends Thread {

    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dataInputStream;
    private PrintStream printStream;
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
        try {
            while (true) {
                socket = serverSocket.accept();
                dataInputStream = new DataInputStream(socket.getInputStream());
                printStream = new PrintStream(socket.getOutputStream());
                String messageFromClient = dataInputStream.readLine();
                System.out.println("Client Message : " + messageFromClient);
                printStream.println("The Message Recieved");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerRequestsHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the address
     */
    public InetAddress getAddress() {
        return address;
    }
}

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

    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    PrintStream printStream;

    public ServerRequestsHandler() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address.getHostAddress());

            serverSocket = new ServerSocket(11114, 10, address);

            while (true) {
                socket = serverSocket.accept();
                dataInputStream = new DataInputStream(socket.getInputStream());
                printStream = new PrintStream(socket.getOutputStream());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                String messageFromClient = dataInputStream.readLine();
                                System.out.println("Client Message : " + messageFromClient);
                                printStream.println("The Message Recieved");
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(ServerRequestsHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();
            }

        } catch (UnknownHostException ex) {

        } catch (IOException ex) {
            Logger.getLogger(ServerRequestsHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
   

    public static void main(String[] args) {
        new ServerRequestsHandler();
    }
}

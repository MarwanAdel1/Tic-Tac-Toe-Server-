/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class ServerHandler extends Thread {

    ServerSocket serverSocket;
    Socket server;
    PrintStream printStream;
    DataInputStream inputStream;

    public static void main(String[] args) {
        new ServerHandler();
    }

    public ServerHandler() {
        try {
            serverSocket = new ServerSocket(6666);
            Thread th = new Thread(new Runnable() {
                public void run() {
                    while (true)
                    {
                        {
                            try {
                                server = serverSocket.accept();
                                System.out.println("client accepted");
                                inputStream = new DataInputStream(server.getInputStream());
                                printStream = new PrintStream(server.getOutputStream());
                                System.out.println("Thread active");

                                System.out.println("waiting for message from client");
                                String message = inputStream.readLine();
                                printStream.println(message);
                                System.out.println("Server Recieved a message");
                            } catch (IOException ex) {
                                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }
                }
            });
            th.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

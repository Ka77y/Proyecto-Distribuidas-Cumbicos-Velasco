/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mycompany.serverproyect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nathy
 */
public class SocketServer {
    
    int portNumber = 8000;
    ServerSocket serverSocket;
    
    public SocketServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void atendercliente(){
        while(true){
            try {
                Socket s = serverSocket.accept();
                Thread c = new Thread(new ServerThread(s));
                c.start();
            } catch (IOException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mycompany.clienteproyecto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nathy
 */
public class SocketCliente {
    String hostName;
    int portNumber;
    
    Socket socketclient ;
    PrintWriter out ;
    BufferedReader in ;

    public SocketCliente(String hostname, int port) {
        try {
            socketclient = new Socket(hostname, port);
            out = new PrintWriter(socketclient.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(socketclient.getInputStream()));
        } catch (IOException ex) {
             System.err.println("Couldn't get I/O for the connection");
            System.exit(1);
        }
    }
    
    public String recibemensaje(){
       
       String mensaje = null;
        try {
            mensaje = in.readLine();
            //System.out.println(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return mensaje;
          
        
    }
    
   /* public void recibemensaje(){
       
       String mensaje = null;
        try {
            mensaje = in.readLine();
            System.out.println(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }*/
    
    
    public void enviarmensaje(String output){
         out.println(output);
    }
}

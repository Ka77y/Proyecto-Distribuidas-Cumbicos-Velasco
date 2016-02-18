/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproyect;

/**
 *
 * @author nathy
 */
public class app {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        SocketServer socketsito = new SocketServer(6667);
        socketsito.atendercliente();
    }
    
}

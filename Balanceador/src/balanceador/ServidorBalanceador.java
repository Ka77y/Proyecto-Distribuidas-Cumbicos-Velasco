/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author nathy
 */

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorBalanceador
{
    static String hostName = "localhost";
    static    int portNumberClient = 8888;

    public static void main(String args[]) throws IOException
    {

        System.out.print("Inicializando balanceador... ");

        try
        {
            ServerSocket serverSocket = new ServerSocket(portNumberClient);

            System.out.println("\t[OK]");
            
            Socket socketCliente;
            while (true)
            {
                socketCliente = serverSocket.accept();
                System.out.println("Nueva conexion entrante: "+socketCliente);
                ((ServHiloBalanc) new ServHiloBalanc(socketCliente)).start();
                
                System.out.println("Termine: ");
            
            }
        }
        catch (IOException ex) {
            Logger.getLogger(ServidorBalanceador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

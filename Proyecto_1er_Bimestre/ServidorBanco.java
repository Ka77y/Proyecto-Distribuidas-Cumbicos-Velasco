
import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorBanco
{
    public static void main(String args[]) throws IOException
    {

        int portNumberBalanc = 6655;

        System.out.print("Inicializando servidor... ");

        try
        {
            ServerSocket serverSocket = new ServerSocket(portNumberBalanc);
            System.out.println("\t[OK]");
            int idSession = 0;

            while (true)
            {
                Socket socket;
                socket = serverSocket.accept();
                System.out.println("Nueva conexión entrante: "+socket);

                ((ServHiloBanco) new ServHiloBanco(socket, idSession)).start();
                idSession++;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(ServidorBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

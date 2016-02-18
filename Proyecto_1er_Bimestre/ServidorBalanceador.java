
import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorBalanceador
{
    static String hostName = "localhost";
    static    int portNumberClient = 55666;

    public static void main(String args[]) throws IOException
    {

        System.out.print("Inicializando balanceador... ");

        try
        {
            ServerSocket serverSocket = new ServerSocket(portNumberClient);

            System.out.println("\t[OK]");
            int idSession = 0;

            Socket socketCliente;
            while (true)
            {
                socketCliente = serverSocket.accept();
                System.out.println("Nueva conexión entrante: "+socketCliente);
                ((ServHiloBalanc) new ServHiloBalanc(socketCliente, idSession)).start();
                System.out.println("Termine: ");
                idSession++;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(ServidorBalanceador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

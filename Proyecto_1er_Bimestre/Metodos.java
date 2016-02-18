
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class Metodos
{
    static String cedulaEnv="1234567890";
    static String contraseñaEnv="patito";
    static String tipoEnv="D";
    static String valorEnv= "123,45";

    static String cadenaEnv= "";
    static String cadenaRecv= "";

    static String cedulaRecv="";
    static String contraseñaRecv="";
    static String tipoRecv="";
    static String valorRecv= "";

    static String [] cadena=new String [4] ;


    public static void Codificando()
    {
        cadenaEnv=cedulaEnv+" "+contraseñaEnv+" "+tipoEnv+" "+valorEnv;
    }

    public static void DeCodificando()
    {
        
        StringTokenizer tokens = new StringTokenizer(cadenaEnv);

        int n=0;
        while(tokens.hasMoreTokens())
        {
            cadena[n]=tokens.nextToken();
            n++;
        }

        cedulaRecv=cadena[0];
        contraseñaRecv=cadena[1];
        tipoRecv=cadena[2];
        valorRecv=cadena[3];
       
    }

    public static void main(String[] args) throws IOException
    {
        

                System.out.println("Corriendo " );
                
                Codificando();
                System.out.println("Cadena-Codificada =  "+cadenaEnv);
                DeCodificando();
                System.out.println("Cadena-Decodificada =  "+"cedula: "+cedulaRecv+" contraseña: "+contraseñaRecv+" tipo:"+tipoRecv+" valor: "+valorRecv);


                
    }
}


import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.StringTokenizer;

public class ServHiloBanco extends Thread
{
    private Socket socket;
    private int idSessio;
    PrintWriter    outC;
    BufferedReader inC;

    public ServHiloBanco(Socket socketHilo, int idHilo)
    {
        this.socket = socketHilo;
        this.idSessio = idHilo;

        try {
            outC  = new PrintWriter( socketHilo.getOutputStream(), true);
            inC   = new BufferedReader( new InputStreamReader(socketHilo.getInputStream()));
        }
        catch (IOException ex) {
            Logger.getLogger(ServHiloBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ProbeA probeI = new ProbeA();
    Sonda parametros = new Sonda();

    static String cedulaRecv="";
    static String contraseñaRecv="";
    static String tipoRecv="";
    static String valorRecv= "";
    static String [] cadena=new String [4] ;
    static String mensaje="";
    static String mensajeSonda="";


    public static void DeCodificando()
    {

        StringTokenizer tokens = new StringTokenizer(mensaje);

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

    @Override
    public void run()
    {
        try {

            while ((mensaje = inC.readLine()) != null)
                {
                    System.out.println("aca toy"+mensaje);
                    if(mensaje.equals("sonda"))
                    {
                        System.out.println("paso");
                        parametros.datoSonda();
                        mensajeSonda=parametros.getI()+" "+parametros.getJ();
                        outC.println(mensajeSonda);
                        System.out.println("**El balanceador con idSesion "+this.idSessio+" envio : "+ mensaje);
                        System.out.println("--Le envio al balanceador "+mensajeSonda+"La idSesion es "+this.idSessio+" recibi : "+ mensaje);
                    }
                    else
                    {
                        DeCodificando();
                        //realizo transacciones
                        outC.println(mensaje);
                        System.out.println("++El balanceador con idSesion "+this.idSessio+" envio : "+ mensaje);
                        System.out.println("__Le envio al balanceador "+mensajeSonda+"La idSesion es "+this.idSessio+" recibi : "+ mensaje);
                    }



                }

            }
        catch (IOException ex) {
            Logger.getLogger(ServHiloBanco.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
        }
        catch (IOException ex) {
            Logger.getLogger(ServHiloBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
